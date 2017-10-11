package pageobjects.ContentAdmin.Region;

import com.jayway.jsonpath.JsonPath;
import org.apache.commons.lang.ObjectUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import pageobjects.AbstractPageObject;
import pageobjects.PageAdmin.WorkflowState;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import static specs.AbstractSpec.desktopUrl;
import static specs.AbstractSpec.propUIContentAdmin;

/**
 * Created by victorlam 2017-09-27.
 */

public class RegionAdd extends AbstractPageObject {
    private static By moduleTitle, regionGlobalOperationField, regionNameField, regionBodyField, tagsField, regionGlobalOperationSelect, textArea;
    private static By hideReportsChk, activeChk, saveBtn, revertBtn, cancelBtn, deleteBtn, addNewLink, publishBtn;
    private static By switchToHtml, workflowStateSpan, commentsTxt, successMsg, saveAndSubmitBtn, currentContentSpan;
    private static By body, bodyContent;
    private static String sPathToFile, sFileJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String PAGE_NAME="Region";

    public RegionAdd(WebDriver driver) {
        super(driver);
        switchToHtml = By.className(propUIContentAdmin.getProperty("html_SwitchTo"));
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("span_Title"));
        regionGlobalOperationField = By.xpath(propUIContentAdmin.getProperty("input_RegionGlobalOperations"));
        regionNameField = By.xpath(propUIContentAdmin.getProperty("input_RegionName"));
        //regionBodyField = By.xpath(propUIContentAdmin.getProperty("input_RegionBody"));
        tagsField = By.xpath(propUIContentAdmin.getProperty("input_Tags"));
        regionGlobalOperationSelect = By.xpath(propUIContentAdmin.getProperty("select_GlobalOperation"));
        hideReportsChk = By.xpath(propUIContentAdmin.getProperty("chk_HideReports"));
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
        textArea = By.tagName(propUIContentAdmin.getProperty("frame_Textarea"));

        parser = new JSONParser();

        sPathToFile = System.getProperty("user.dir") + propUIContentAdmin.getProperty("dataPath_RegionList");
        sFileJson = propUIContentAdmin.getProperty("json_Region");

        body = By.xpath(propUIContentAdmin.getProperty("frame_Body"));
        bodyContent = By.xpath(propUIContentAdmin.getProperty("txtarea_Body"));
    }

    public String getTitle() {
        findElement(addNewLink).click();
        waitForElement(moduleTitle);
        String sTitle = getText(moduleTitle);
        //findElement(cancelBtn).click();
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].scrollIntoView()", findElement(cancelBtn));
        findElement(cancelBtn).click();

        return sTitle;
    }

    public String saveRegion(JSONObject data, String name) {
        String region_global_operation, region_name, region_body, tags;
        Boolean hideReports;
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

            region_global_operation = data.get("region_global_operation").toString();
            //findElement(regionGlobalOperationField).clear();
            findElement(regionGlobalOperationField).sendKeys(region_global_operation);
            jsonObj.put("region_global_operation", region_global_operation);

            region_name = data.get("region_name").toString();
            findElement(regionNameField).clear();
            findElement(regionNameField).sendKeys(region_name);
            jsonObj.put("region_name", region_name);

            /*region_body = data.get("region_body").toString();
            findElement(regionBodyField).clear();
            findElement(regionBodyField).sendKeys(region_body);
            jsonObj.put("region_body", region_body);*/

            findElement(switchToHtml).click();

            region_body = data.get("region_body").toString();
            driver.switchTo().frame(2);
            findElement(textArea).sendKeys(region_body);
            driver.switchTo().defaultContent();
            pause(1000L);
            jsonObj.put("region_body", region_body);

            tags = data.get("tags").toString();
            findElement(tagsField).clear();
            findElement(tagsField).sendKeys(tags);
            jsonObj.put("tags", tags);

            // Save Hide Reports checkbox
            hideReports = Boolean.parseBoolean(data.get("hide_reports").toString());
            jsonObj.put("hide_reports", Boolean.parseBoolean(data.get("hide_reports").toString()));
            if (hideReports) {
                if (!Boolean.parseBoolean(findElement(hideReportsChk).getAttribute("checked"))) {
                    findElement(hideReportsChk).click();
                } else {
                }
            } else {
                if (!Boolean.parseBoolean(findElement(hideReportsChk).getAttribute("checked"))) {
                } else {
                    findElement(hideReportsChk).click();
                }
            }


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

    public String saveAndSubmitRegion(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        By editBtn = By.xpath("//td[(text()='" + data.get("region_name").toString() + "')]/parent::tr/td/input[contains(@id, 'imgEdit')][contains(@id, 'Region')]");

        try {
            waitForElement(moduleTitle);

            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            waitForElement(regionGlobalOperationSelect);
            findElement(regionGlobalOperationSelect).sendKeys(data.get("region_global_operation").toString());
            Thread.sleep(DEFAULT_PAUSE);

            //String pageUrl = getPageUrl(jsonMain, name);
            //driver.get(pageUrl);
            waitForElement(editBtn);
            findElement(editBtn).click();
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

            // Save Link To Page Url
            URL url = new URL(getUrl());
            String[] params = url.getQuery().split("&");
            JSONObject jsonURLQuery = new JSONObject();
            for (String param:params) {
                jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);
            }
            jsonObj.put("url_query", jsonURLQuery);


            jsonMain.put(name, jsonObj);

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

    public Boolean checkRegion(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();

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
                if (!new Select(findElement(regionGlobalOperationField)).getFirstSelectedOption().getText().trim().equals(data.get("region_global_operation").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(regionNameField).getAttribute("value").equals(data.get("region_name").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            /*try {
                if (!findElement(regionBodyField).getAttribute("value").equals(data.get("region_body").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }*/

            try {
                driver.switchTo().frame(findElement(body));
                if (!findElement(bodyContent).getText().equals(data.get("region_body").toString())) {
                    return false;
                }
                driver.switchTo().defaultContent();
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(tagsField).getAttribute("value").equals(data.get("tags").toString() + " ")) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(hideReportsChk).getAttribute("checked").equals(data.get("hide_reports").toString())) {
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

    public String publishRegion(JSONObject data, String name) throws InterruptedException {
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

    public String changeAndSubmitRegion(JSONObject data, String name) throws InterruptedException {
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
                if (!data.get("region_global_operation_ch").toString().isEmpty()) {
                    findElement(regionGlobalOperationField).sendKeys(data.get("region_global_operation_ch").toString());
                    jsonObj.put("region_global_operation", data.get("region_global_operation_ch").toString());
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!data.get("region_name_ch").toString().isEmpty()) {
                    findElement(regionNameField).clear();
                    findElement(regionNameField).sendKeys(data.get("region_name_ch").toString());
                    jsonObj.put("region_name", data.get("region_name_ch").toString());
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!data.get("tags_ch").toString().isEmpty()) {
                    findElement(tagsField).clear();
                    findElement(tagsField).sendKeys(data.get("tags_ch").toString());
                    jsonObj.put("tags", data.get("tags_ch").toString());
                }
            } catch (NullPointerException e) {
            }

            /*try {
                if (!data.get("region_body_ch").toString().isEmpty()) {
                    findElement(regionBodyField).clear();
                    findElement(regionBodyField).sendKeys(data.get("region_body_ch").toString());
                    jsonObj.put("region_body", data.get("region_body_ch").toString());
                }
            } catch (NullPointerException e) {
            }*/

            try {
                if (!data.get("region_body_ch").toString().isEmpty()) {
                    findElement(switchToHtml).click();
                    driver.switchTo().frame(2);
                    findElement(textArea).clear();
                    findElement(textArea).sendKeys(data.get("region_body_ch").toString());
                    driver.switchTo().defaultContent();
                    jsonObj.put("region_body", data.get("region_body_ch").toString());
                }
            } catch (NullPointerException e) {
            }

            jsonObj.put("hide_reports", Boolean.parseBoolean(data.get("hide_reports").toString()));
            try {
                // Save Hide Reports checkbox
                if (Boolean.parseBoolean(data.get("hide_reports_ch").toString())) {
                    if (!Boolean.parseBoolean(findElement(hideReportsChk).getAttribute("checked"))) {
                        findElement(hideReportsChk).click();
                        jsonObj.put("hide_reports", true);
                    } else {
                    }
                } else {
                    if (!Boolean.parseBoolean(findElement(hideReportsChk).getAttribute("checked"))) {
                    } else {
                        findElement(hideReportsChk).click();
                        jsonObj.put("hide_reports", false);
                    }
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

    public String revertToLiveRegion(String name) throws InterruptedException {
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

    public Boolean checkRegionCh(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();

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
                if (!new Select(findElement(regionGlobalOperationField)).getFirstSelectedOption().getText().trim().equals(data.get("region_global_operation_ch").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(regionNameField).getAttribute("value").equals(data.get("region_name_ch").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            /*try {
                if (!findElement(regionBodyField).getAttribute("value").equals(data.get("region_body_ch").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }*/

            try {
                driver.switchTo().frame(findElement(body));
                if (!findElement(bodyContent).getText().equals(data.get("region_body_ch").toString())) {
                    driver.switchTo().defaultContent();
                    return false;
                }
                else {
                    driver.switchTo().defaultContent();
                }
            } catch (NullPointerException e) {
                driver.switchTo().defaultContent();
            }

            try {
                if (!findElement(tagsField).getAttribute("value").contains(data.get("tags_ch").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(hideReportsChk).getAttribute("checked").equals(data.get("hide_reports_ch").toString())) {
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

    public String setupAsDeletedRegion(String name) throws InterruptedException {
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

            waitForElement(commentsTxt);
            findElement(commentsTxt).sendKeys("Removing "+PAGE_NAME);
            findElement(deleteBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(currentContentSpan);

            jsonObj.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            jsonObj.put("deleted", "true");

            jsonMain.put(name, jsonObj);

            FileWriter file = new FileWriter(sPathToFile + sFileJson);
            file.write(jsonMain.toJSONString().replace("\\", ""));
            file.flush();

            System.out.println(name+ ": "+PAGE_NAME+" set up as deleted");
            return findElement(currentContentSpan).getText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String removeRegion(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            if (findElement(currentContentSpan).getText().equals(WorkflowState.DELETE_PENDING.state())) {

                waitForElement(commentsTxt);
                findElement(commentsTxt).sendKeys("Approving "+PAGE_NAME+" removal");
                findElement(publishBtn).click();

                Thread.sleep(DEFAULT_PAUSE*2);

                driver.get(pageUrl);
                Thread.sleep(DEFAULT_PAUSE);

                jsonMain.remove(name);

                FileWriter file = new FileWriter(sPathToFile + sFileJson);
                file.write(jsonMain.toJSONString().replace("\\", ""));
                file.flush();

                Thread.sleep(DEFAULT_PAUSE*2);
                driver.navigate().refresh();

                System.out.println(name+ ": New "+PAGE_NAME+" has been removed");
                return findElement(workflowStateSpan).getText();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getPageUrl(JSONObject obj, String name) {
        String  sItemID = JsonPath.read(obj, "$.['"+name+"'].url_query.ItemID");
        String  sLanguageId = JsonPath.read(obj, "$.['"+name+"'].url_query.LanguageId");
        String  sSectionId = JsonPath.read(obj, "$.['"+name+"'].url_query.SectionId");
        return desktopUrl.toString()+"default.aspx?ItemID="+sItemID+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }

}
