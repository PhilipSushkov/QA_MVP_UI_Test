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
import static util.Functions.GetResponseCode;
import static util.Functions.GetResponseCodeSsl;

/**
 * Created by philipsushkov on 2017-06-07.
 */

public class FinancialReportAdd extends AbstractPageObject {
    private static By moduleTitle, reportYearSelect, reportTypeSelect, coverImageInput;
    private static By filingDateInput, tagsInput, addNewRelatedDocLink, documentsTable;
    private static By documentTitleInput, documentCategorySelect, documentPathRbl, documentUrlRbl, relatedDocPathInput;
    private static By documentTitleSpan, documentPathSpan, thumbnailPathImg;
    private static By activeChk, saveBtn, revertBtn, cancelBtn, deleteBtn, addNewLink, publishBtn, generateThumbnailBtn;
    private static By workflowStateSpan, commentsTxt, successMsg, successMsgDoc, saveAndSubmitBtn, currentContentSpan;
    private static String sPathToFile, sFileJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String PAGE_NAME="Financial Report", FILE="File", ONLINE_VERSION="Online Version", RELATED_DOC="related_document";

    public FinancialReportAdd(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("spanModule_Title"));
        reportYearSelect = By.xpath(propUIContentAdmin.getProperty("select_ReportYear"));
        reportTypeSelect = By.xpath(propUIContentAdmin.getProperty("select_ReportType"));
        coverImageInput = By.xpath(propUIContentAdmin.getProperty("input_CoverImage"));
        filingDateInput = By.xpath(propUIContentAdmin.getProperty("input_FilingDate"));
        tagsInput = By.xpath(propUIContentAdmin.getProperty("input_Tags"));
        addNewRelatedDocLink = By.xpath(propUIContentAdmin.getProperty("href_AddNewRelatedDoc"));
        documentsTable = By.xpath(propUIContentAdmin.getProperty("table_Documents"));
        documentTitleInput = By.xpath(propUIContentAdmin.getProperty("input_DocumentTitle"));
        documentCategorySelect = By.xpath(propUIContentAdmin.getProperty("select_DocumentCategory"));
        documentPathRbl = By.xpath(propUIContentAdmin.getProperty("rbl_DocumentPath"));
        documentUrlRbl = By.xpath(propUIContentAdmin.getProperty("rbl_DocumentUrl"));
        relatedDocPathInput = By.xpath(propUIContentAdmin.getProperty("input_RelatedDocPath"));
        activeChk = By.xpath(propUIContentAdmin.getProperty("chk_Active"));
        saveBtn = By.xpath(propUIContentAdmin.getProperty("btn_Save"));
        cancelBtn = By.xpath(propUIContentAdmin.getProperty("btn_Cancel"));
        deleteBtn = By.xpath(propUIContentAdmin.getProperty("btn_Delete"));
        publishBtn = By.xpath(propUIContentAdmin.getProperty("btn_Publish"));
        addNewLink = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));
        revertBtn = By.xpath(propUIContentAdmin.getProperty("btn_Revert"));
        generateThumbnailBtn = By.xpath(propUIContentAdmin.getProperty("btn_GenerateThumbnail"));
        workflowStateSpan = By.xpath(propUIContentAdmin.getProperty("select_WorkflowState"));
        commentsTxt = By.xpath(propUIContentAdmin.getProperty("txtarea_Comments"));
        successMsg = By.xpath(propUIContentAdmin.getProperty("msg_Success"));
        successMsgDoc = By.xpath(propUIContentAdmin.getProperty("msg_SuccessDoc"));
        saveAndSubmitBtn = By.xpath(propUIContentAdmin.getProperty("btn_SaveAndSubmit"));
        currentContentSpan = By.xpath(propUIContentAdmin.getProperty("span_CurrentContent"));
        documentTitleSpan = By.xpath(propUIContentAdmin.getProperty("span_DocumentTitle"));
        documentPathSpan = By.xpath(propUIContentAdmin.getProperty("span_DocumentPath"));
        thumbnailPathImg = By.xpath(propUIContentAdmin.getProperty("img_ThumbnailPath"));

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

    public String saveRelatedDocument(JSONObject data, String name) throws InterruptedException {
        String document_title, document_category, document_type, document_path, thumbnail_path;
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonMainDoc = new JSONObject();
        JSONObject jsonObjDoc = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
                jsonMainDoc = (JSONObject) jsonMain.get(name);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(commentsTxt);

            findElement(addNewRelatedDocLink).click();
            waitForElement(commentsTxt);

            JSONObject jsonRelatedDoc = (JSONObject) data.get(RELATED_DOC);

            document_title = jsonRelatedDoc.get("document_title").toString();
            findElement(documentTitleInput).clear();
            findElement(documentTitleInput).sendKeys(document_title);
            jsonObjDoc.put("document_title", document_title);

            document_category = jsonRelatedDoc.get("document_category").toString();
            findElement(documentCategorySelect).sendKeys(document_category);
            jsonObjDoc.put("document_category", document_category);

            document_type = jsonRelatedDoc.get("document_type").toString();
            switch (document_type) {
                case FILE:
                    findElement(documentPathRbl).click();
                    break;
                case ONLINE_VERSION:
                    findElement(documentUrlRbl).click();
                    break;
                default:
                    findElement(documentPathRbl).click();
            }
            jsonObjDoc.put("document_type", document_type);

            document_path = jsonRelatedDoc.get("document_path").toString();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement elemSrc =  driver.findElement(relatedDocPathInput);
            js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", elemSrc, "value", document_path);
            jsonObjDoc.put("document_path", document_path);

            thumbnail_path = jsonRelatedDoc.get("thumbnail_path").toString();
            jsonObjDoc.put("thumbnail_path", thumbnail_path);

            URL url = new URL(getUrl());
            String[] params = url.getQuery().split("&");
            JSONObject jsonURLQuery = new JSONObject();
            for (String param:params) {
                jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);
            }
            jsonObjDoc.put("url_query", jsonURLQuery);

            findElement(saveBtn).click();
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(successMsg);

            jsonObjDoc.put("workflow_state", WorkflowState.IN_PROGRESS.state());

            jsonMainDoc.put(RELATED_DOC, jsonObjDoc);

            try {
                FileWriter writeFile = new FileWriter(sPathToFile + sFileJson);
                writeFile.write(jsonMain.toJSONString().replace("\\", ""));
                writeFile.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(name+ ": Related Document for "+PAGE_NAME+" has been attached");
            return findElement(workflowStateSpan).getText();
        } catch (IOException e) {
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

    public String saveAndSubmitRelatedDocument(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonMainDoc = new JSONObject();
        JSONObject jsonRelatedDoc = (JSONObject) data.get(RELATED_DOC);
        String docTitle = jsonRelatedDoc.get("document_title").toString();
        By editBtn = By.xpath("//td/span[(text()='"+docTitle+"')]/parent::td/parent::tr/td/input[contains(@id, 'btnEdit')][contains(@id, 'rtrDocuments')]");

        try {
            waitForElement(moduleTitle);

            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
                jsonMainDoc = (JSONObject) jsonMain.get(name);
            } catch (ParseException e) {
            }

            String pageUrl = getPageDocUrl(jsonMainDoc, RELATED_DOC);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(commentsTxt);

            try {
                findElement(generateThumbnailBtn).click();
                Thread.sleep(DEFAULT_PAUSE*2);
            } catch (ElementNotVisibleException e) {
            }

            findElement(commentsTxt).sendKeys(jsonRelatedDoc.get("comment").toString());
            findElement(saveAndSubmitBtn).click();
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(successMsgDoc);

            pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(commentsTxt);

            waitForElement(editBtn);
            findElement(editBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            JSONObject jsonObjDoc = (JSONObject) jsonMainDoc.get(RELATED_DOC);

            jsonObjDoc.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            jsonObjDoc.put("deleted", "false");

            jsonMainDoc.put(RELATED_DOC, jsonObjDoc);

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

    public Boolean checkRelatedDocument(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonMainDoc = new JSONObject();
        JSONObject jsonRelatedDoc = (JSONObject) data.get(RELATED_DOC);

        try {
            waitForElement(moduleTitle);

            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
                jsonMainDoc = (JSONObject) jsonMain.get(name);
            } catch (ParseException e) {
            }

            String pageUrl = getPageDocUrl(jsonMainDoc, RELATED_DOC);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(commentsTxt);

            // Compare field values with entry data
            //System.out.println(findElement(documentTitleInput).getAttribute("value"));
            try {
                if (!findElement(documentTitleInput).getAttribute("value").equals(jsonRelatedDoc.get("document_title").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(documentCategorySelect).getAttribute("value").equals(jsonRelatedDoc.get("document_category").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            switch (jsonRelatedDoc.get("document_type").toString()) {
                case FILE:
                    try {
                        if (!findElement(documentPathRbl).getAttribute("checked").equals("true")) {
                            return false;
                        }
                    } catch (NullPointerException e) {
                    }
                    break;
                case ONLINE_VERSION:
                    try {
                        if (!findElement(documentUrlRbl).getAttribute("checked").equals("true")) {
                            return false;
                        }
                    } catch (NullPointerException e) {
                    }
                    break;
                default:
                    try {
                        if (!findElement(documentPathRbl).getAttribute("checked").equals("true")) {
                            return false;
                        }
                    } catch (NullPointerException e) {
                    }
            }

            try {
                if (!findElement(relatedDocPathInput).getAttribute("value").equals(jsonRelatedDoc.get("document_path").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            String srcUrl = findElement(thumbnailPathImg).getAttribute("src");
            try {
                if (!srcUrl.contains(jsonRelatedDoc.get("thumbnail_path").toString())) {
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

    public String getPageDocUrl(JSONObject obj, String name) {
        String sReportId = JsonPath.read(obj, "$.['"+name+"'].url_query.ReportId");
        String sItemID = JsonPath.read(obj, "$.['"+name+"'].url_query.ItemID");
        String sLanguageId = JsonPath.read(obj, "$.['"+name+"'].url_query.LanguageId");
        String sSectionId = JsonPath.read(obj, "$.['"+name+"'].url_query.SectionId");
        return desktopUrl.toString()+"default.aspx?ReportId="+sReportId+"&ItemID="+sItemID+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }

}
