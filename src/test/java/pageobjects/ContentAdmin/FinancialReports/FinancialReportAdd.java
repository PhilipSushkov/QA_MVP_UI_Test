package pageobjects.ContentAdmin.FinancialReports;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import pageobjects.AbstractPageObject;

import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pageobjects.PageAdmin.WorkflowState;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import static specs.AbstractSpec.desktopUrl;
import static specs.AbstractSpec.propUIContentAdmin;

/**
 * Created by philipsushkov on 2017-06-07.
 */

public class FinancialReportAdd extends AbstractPageObject {
    private static By moduleTitle, reportYearSelect, reportTypeSelect, coverImageInput;
    private static By filingDateInput, tagsInput, publishBtn;
    private static By documentTitleSpan, documentPathSpan, currentContentSpan;
    private static By activeChk, saveBtn, revertBtn, cancelBtn, deleteBtn, addNewLink;
    private static By workflowStateSpan, commentsTxt, successMsg, saveAndSubmitBtn;
    private static String sPathToFile, sFileJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String PAGE_NAME="Financial Report", RELATED_DOC="related_document";

    public FinancialReportAdd(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("spanModule_Title"));
        reportYearSelect = By.xpath(propUIContentAdmin.getProperty("select_ReportYear"));
        reportTypeSelect = By.xpath(propUIContentAdmin.getProperty("select_ReportType"));
        coverImageInput = By.xpath(propUIContentAdmin.getProperty("input_CoverImage"));
        filingDateInput = By.xpath(propUIContentAdmin.getProperty("input_FilingDate"));
        tagsInput = By.xpath(propUIContentAdmin.getProperty("input_Tags"));
        activeChk = By.xpath(propUIContentAdmin.getProperty("chk_Active"));
        saveBtn = By.xpath(propUIContentAdmin.getProperty("btn_Save"));
        cancelBtn = By.xpath(propUIContentAdmin.getProperty("btn_Cancel"));
        deleteBtn = By.xpath(propUIContentAdmin.getProperty("btn_Delete"));
        publishBtn = By.xpath(propUIContentAdmin.getProperty("btn_Publish"));
        addNewLink = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));
        revertBtn = By.xpath(propUIContentAdmin.getProperty("btn_Revert"));
        workflowStateSpan = By.xpath(propUIContentAdmin.getProperty("select_WorkflowState"));
        commentsTxt = By.xpath(propUIContentAdmin.getProperty("txtarea_Comments"));
        successMsg = By.xpath(propUIContentAdmin.getProperty("msg_Success"));
        saveAndSubmitBtn = By.xpath(propUIContentAdmin.getProperty("btn_SaveAndSubmit"));
        currentContentSpan = By.xpath(propUIContentAdmin.getProperty("span_CurrentContent"));
        documentTitleSpan = By.xpath(propUIContentAdmin.getProperty("span_DocumentTitle"));
        documentPathSpan = By.xpath(propUIContentAdmin.getProperty("span_DocumentPath"));

        parser = new JSONParser();

        sPathToFile = System.getProperty("user.dir") + propUIContentAdmin.getProperty("dataPath_FinancialReportList");
        sFileJson = propUIContentAdmin.getProperty("json_FinancialReport");
    }

    public String getTitle() {
        findElement(addNewLink).click();
        waitForElement(moduleTitle);
        String sTitle = getText(moduleTitle);
        findElement(cancelBtn).click();
        return sTitle;
    }

    public String saveFinancialReport(JSONObject data, String name) {
        String report_year, report_type, cover_image, filing_date, tags;
        Boolean active;
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonMain = new JSONObject();

        waitForElement(addNewLink);
        findElement(addNewLink).click();
        waitForElement(saveBtn);

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            report_year = data.get("report_year").toString();
            findElement(reportYearSelect).sendKeys(report_year);
            jsonObj.put("report_year", report_year);

            report_type = data.get("report_type").toString();
            findElement(reportTypeSelect).sendKeys(report_type);
            jsonObj.put("report_type", report_type);

            cover_image = data.get("cover_image").toString();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement elemSrc =  driver.findElement(coverImageInput);
            js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", elemSrc, "value", cover_image);
            jsonObj.put("cover_image", cover_image);

            filing_date = data.get("filing_date").toString();
            findElement(filingDateInput).clear();
            findElement(filingDateInput).sendKeys(filing_date);
            jsonObj.put("filing_date", filing_date);

            tags = data.get("tags").toString();
            findElement(tagsInput).clear();
            findElement(tagsInput).sendKeys(tags);
            jsonObj.put("tags", tags);

            // Save Active checkbox
            active = Boolean.parseBoolean(data.get("active").toString());
            jsonObj.put("active", Boolean.parseBoolean(data.get("active").toString()));
            if (active) {
                if (!Boolean.parseBoolean(findElement(activeChk).getAttribute("checked"))) {
                    findElement(activeChk).click();
                } else {
                }
            } else {
                if (!Boolean.parseBoolean(findElement(activeChk).getAttribute("checked"))) {
                } else {
                    findElement(activeChk).click();
                }
            }

            URL url = new URL(getUrl());
            String[] params = url.getQuery().split("&");
            JSONObject jsonURLQuery = new JSONObject();
            for (String param:params) {
                jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);
            }
            jsonObj.put("url_query", jsonURLQuery);

            findElement(saveBtn).click();
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(successMsg);

            jsonObj.put("workflow_state", WorkflowState.IN_PROGRESS.state());

            jsonMain.put(name, jsonObj);

            try {
                FileWriter writeFile = new FileWriter(sPathToFile + sFileJson);
                writeFile.write(jsonMain.toJSONString().replace("\\", ""));
                writeFile.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(name + ": "+PAGE_NAME+" has been created");
            return findElement(workflowStateSpan).getText();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String saveAndSubmitFinancialReport(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        By editBtn = By.xpath("//td[(text()='"+name+"')]/parent::tr/td/input[contains(@id, 'imgEdit')][contains(@id, 'AnnualQuarterlyReports')]");

        try {
            waitForElement(moduleTitle);

            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(commentsTxt);

            findElement(commentsTxt).sendKeys(data.get("comment").toString());
            findElement(saveAndSubmitBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(editBtn);
            findElement(editBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            JSONObject jsonObj = (JSONObject) jsonMain.get(name);

            jsonObj.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            jsonObj.put("deleted", "false");

            FileWriter file = new FileWriter(sPathToFile + sFileJson);
            file.write(jsonMain.toJSONString().replace("\\", ""));
            file.flush();

            System.out.println(name+ ": "+PAGE_NAME+" has been sumbitted");
            return findElement(workflowStateSpan).getText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Boolean checkFinancialReport(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonRelatedDoc = (JSONObject) data.get(RELATED_DOC);

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(commentsTxt);

            // Compare field values with entry data
            try {
                if (!new Select(findElement(reportYearSelect)).getFirstSelectedOption().getText().trim().equals(data.get("report_year").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!new Select(findElement(reportTypeSelect)).getFirstSelectedOption().getText().trim().equals(data.get("report_type").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(coverImageInput).getAttribute("value").equals(data.get("cover_image").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(filingDateInput).getAttribute("value").equals(data.get("filing_date").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(tagsInput).getAttribute("value").trim().equals(data.get("tags").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(documentTitleSpan).getAttribute("value").equals(jsonRelatedDoc.get("document_title").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(documentPathSpan).getAttribute("value").equals(jsonRelatedDoc.get("document_path").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(activeChk).getAttribute("checked").equals(data.get("active").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            System.out.println(name+ ": New "+PAGE_NAME+" has been checked");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String publishFinancialReport(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
                jsonObj = (JSONObject) jsonMain.get(name);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(publishBtn);
            findElement(publishBtn).click();
            Thread.sleep(DEFAULT_PAUSE*2);

            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE*2);
            driver.navigate().refresh();
            Thread.sleep(DEFAULT_PAUSE);

            jsonObj.put("workflow_state", WorkflowState.LIVE.state());
            jsonObj.put("deleted", "false");

            jsonMain.put(name, jsonObj);

            FileWriter file = new FileWriter(sPathToFile + sFileJson);
            file.write(jsonMain.toJSONString().replace("\\", ""));
            file.flush();

            Thread.sleep(DEFAULT_PAUSE*2);
            driver.navigate().refresh();

            System.out.println(name+ ": New "+PAGE_NAME+" has been published");
            return findElement(workflowStateSpan).getText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String changeAndSubmitFinancialReport(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
                jsonObj = (JSONObject) jsonMain.get(name);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(saveAndSubmitBtn);

            try {
                if (!data.get("report_type_ch").toString().isEmpty()) {
                    findElement(reportTypeSelect).sendKeys(data.get("report_type_ch").toString());
                    jsonObj.put("report_type", data.get("report_type_ch").toString());
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!data.get("tags_ch").toString().isEmpty()) {
                    findElement(tagsInput).clear();
                    findElement(tagsInput).sendKeys(data.get("tags_ch").toString());
                    jsonObj.put("tags", data.get("tags_ch").toString());
                }
            } catch (NullPointerException e) {
            }

            jsonObj.put("active", Boolean.parseBoolean(data.get("active").toString()));
            try {
                // Save Active checkbox
                if (Boolean.parseBoolean(data.get("active_ch").toString())) {
                    if (!Boolean.parseBoolean(findElement(activeChk).getAttribute("checked"))) {
                        findElement(activeChk).click();
                        jsonObj.put("active", true);
                    } else {
                    }
                } else {
                    if (!Boolean.parseBoolean(findElement(activeChk).getAttribute("checked"))) {
                    } else {
                        findElement(activeChk).click();
                        jsonObj.put("active", false);
                    }
                }
            } catch (NullPointerException e) {
            }

            try {
                findElement(commentsTxt).clear();
                findElement(commentsTxt).sendKeys(data.get("comment_ch").toString());
            } catch (NullPointerException e) {
            }

            findElement(saveAndSubmitBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            jsonObj.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            jsonObj.put("deleted", "false");

            jsonMain.put(name, jsonObj);

            FileWriter file = new FileWriter(sPathToFile + sFileJson);
            file.write(jsonMain.toJSONString().replace("\\", ""));
            file.flush();

            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(workflowStateSpan);

            System.out.println(name+ ": New "+PAGE_NAME+" changes have been submitted");
            return findElement(workflowStateSpan).getText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String revertToLiveFinancialReport(String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
                jsonObj = (JSONObject) jsonMain.get(name);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(revertBtn);

            if (jsonObj.get("workflow_state").toString().equals(WorkflowState.FOR_APPROVAL.state())) {
                findElement(revertBtn).click();
                Thread.sleep(DEFAULT_PAUSE);

                jsonObj.put("workflow_state", WorkflowState.LIVE.state());
                jsonObj.put("deleted", "false");

                jsonMain.put(name, jsonObj);

                FileWriter file = new FileWriter(sPathToFile + sFileJson);
                file.write(jsonMain.toJSONString().replace("\\", ""));
                file.flush();

                driver.get(pageUrl);
                Thread.sleep(DEFAULT_PAUSE);
                waitForElement(workflowStateSpan);

                System.out.println(name+ ": "+PAGE_NAME+" has been reverted to Live");
                return findElement(workflowStateSpan).getText();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Boolean checkFinancialReportCh(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonRelatedDoc = (JSONObject) data.get(RELATED_DOC);

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(commentsTxt);

            // Compare field values with entry data
            try {
                if (!new Select(findElement(reportYearSelect)).getFirstSelectedOption().getText().trim().equals(data.get("report_year").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!new Select(findElement(reportTypeSelect)).getFirstSelectedOption().getText().trim().equals(data.get("report_type_ch").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(coverImageInput).getAttribute("value").equals(data.get("cover_image").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(filingDateInput).getAttribute("value").equals(data.get("filing_date").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(tagsInput).getAttribute("value").trim().equals(data.get("tags_ch").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(documentTitleSpan).getAttribute("value").equals(jsonRelatedDoc.get("document_title").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(documentPathSpan).getAttribute("value").equals(jsonRelatedDoc.get("document_path").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(activeChk).getAttribute("checked").equals(data.get("active_ch").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            System.out.println(name+ ": New "+PAGE_NAME+" has been checked");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getPageUrl(JSONObject obj, String name) {
        String sItemID = JsonPath.read(obj, "$.['"+name+"'].url_query.ItemID");
        String sLanguageId = JsonPath.read(obj, "$.['"+name+"'].url_query.LanguageId");
        String sSectionId = JsonPath.read(obj, "$.['"+name+"'].url_query.SectionId");
        return desktopUrl.toString()+"default.aspx?ItemID="+sItemID+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }

}
