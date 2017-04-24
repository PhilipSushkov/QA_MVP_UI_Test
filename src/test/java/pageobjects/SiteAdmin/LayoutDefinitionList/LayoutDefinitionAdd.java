package pageobjects.SiteAdmin.LayoutDefinitionList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.support.ui.Select;
import pageobjects.PageAdmin.WorkflowState;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import static specs.AbstractSpec.desktopUrl;
import static specs.AbstractSpec.propUISiteAdmin;

/**
 * Created by philipsushkov on 2017-04-21.
 */

public class LayoutDefinitionAdd extends AbstractPageObject {
    private static By moduleTitle, friendlyNameField, descriptionField, defaultLayoutSelect;
    private static By layoutPathRb, userControlContentRb, activeChk, isAdminChk, viewStateChk;
    private static By saveBtn, cancelBtn, deleteBtn, addNewLink, publishBtn, backBtn;
    private static By revertBtn, workflowStateSpan, commentsTxt, successMsg, saveAndSubmitBtn, currentContentSpan;
    private static String sPathToFile, sFileJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String PAGE_NAME="Layout Definition";

    public LayoutDefinitionAdd(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISiteAdmin.getProperty("spanModule_Title"));
        friendlyNameField = By.xpath(propUISiteAdmin.getProperty("input_FriendlyName"));
        descriptionField = By.xpath(propUISiteAdmin.getProperty("input_Description"));
        defaultLayoutSelect = By.xpath(propUISiteAdmin.getProperty("select_DefaultLayout"));
        isAdminChk = By.xpath(propUISiteAdmin.getProperty("chk_IsAdmin"));
        viewStateChk = By.xpath(propUISiteAdmin.getProperty("chk_ViewState"));
        layoutPathRb = By.xpath(propUISiteAdmin.getProperty("rb_LayoutPath"));
        userControlContentRb = By.xpath(propUISiteAdmin.getProperty("rb_UserControlContent"));
        backBtn = By.xpath(propUISiteAdmin.getProperty("btn_Back"));
        saveBtn = By.xpath(propUISiteAdmin.getProperty("btn_Save"));
        cancelBtn = By.xpath(propUISiteAdmin.getProperty("btn_Cancel"));
        deleteBtn = By.xpath(propUISiteAdmin.getProperty("btn_Delete"));
        publishBtn = By.xpath(propUISiteAdmin.getProperty("btn_Publish"));
        addNewLink = By.xpath(propUISiteAdmin.getProperty("input_AddNew"));
        revertBtn = By.xpath(propUISiteAdmin.getProperty("btn_Revert"));
        workflowStateSpan = By.xpath(propUISiteAdmin.getProperty("select_WorkflowState"));
        commentsTxt = By.xpath(propUISiteAdmin.getProperty("txtarea_Comments"));
        successMsg = By.xpath(propUISiteAdmin.getProperty("msg_Success"));
        saveAndSubmitBtn = By.xpath(propUISiteAdmin.getProperty("btn_SaveAndSubmit"));
        activeChk = By.xpath(propUISiteAdmin.getProperty("chk_Active"));
        currentContentSpan = By.xpath(propUISiteAdmin.getProperty("span_CurrentContent"));

        parser = new JSONParser();

        sPathToFile = System.getProperty("user.dir") + propUISiteAdmin.getProperty("dataPath_GlobalModuleList");
        sFileJson = propUISiteAdmin.getProperty("json_GlobalModules");
    }

    public String getTitle() {
        findElement(addNewLink).click();
        waitForElement(moduleTitle);
        String sTitle = getText(moduleTitle);
        findElement(cancelBtn).click();
        return sTitle;
    }

    public String saveLayoutDefinition(JSONObject data, String name) {
        String friendly_name, description, layout_type, default_layout;
        Boolean is_admin, active, enable_viewState;
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

            friendly_name = data.get("friendly_name").toString();
            findElement(friendlyNameField).sendKeys(friendly_name);
            jsonObj.put("module_title", friendly_name);

            description = data.get("description").toString();
            findElement(descriptionField).sendKeys(description);
            jsonObj.put("description", description);

            layout_type = data.get("layout_type").toString();
            jsonObj.put("layout_type", layout_type);
            if (layout_type.equals("Default Layout")) {
                findElement(layoutPathRb).click();
            }
            if (layout_type.equals("Custom Layout")) {
                findElement(userControlContentRb).click();
            }

            default_layout = data.get("default_layout").toString();
            findElement(defaultLayoutSelect).sendKeys(default_layout);
            jsonObj.put("default_layout", default_layout);

            is_admin = Boolean.parseBoolean(data.get("is_admin").toString());
            jsonObj.put("is_admin", Boolean.parseBoolean(data.get("is_admin").toString()));
            if (is_admin) {
                if (!Boolean.parseBoolean(findElement(isAdminChk).getAttribute("checked"))) {
                    findElement(isAdminChk).click();
                } else {
                }
            } else {
                if (!Boolean.parseBoolean(findElement(isAdminChk).getAttribute("checked"))) {
                } else {
                    findElement(isAdminChk).click();
                }
            }

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

            enable_viewState = Boolean.parseBoolean(data.get("enable_viewState").toString());
            jsonObj.put("enable_viewState", Boolean.parseBoolean(data.get("enable_viewState").toString()));
            if (enable_viewState) {
                if (!Boolean.parseBoolean(findElement(viewStateChk).getAttribute("checked"))) {
                    findElement(viewStateChk).click();
                } else {
                }
            } else {
                if (!Boolean.parseBoolean(findElement(viewStateChk).getAttribute("checked"))) {
                } else {
                    findElement(viewStateChk).click();
                }
            }


            findElement(saveBtn).click();
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(successMsg);

            // Save LayoutDefinition Url
            URL url = new URL(getUrl());
            String[] params = url.getQuery().split("&");
            JSONObject jsonURLQuery = new JSONObject();
            for (String param:params) {
                jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);
            }
            jsonObj.put("url_query", jsonURLQuery);

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

    public String saveAndSubmitLayoutDefinition(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        By editBtn = By.xpath("//td[(text()='" + data.get("friendly_name").toString() + "')]/parent::tr/td/input[contains(@id, 'imgEdit')][contains(@id, 'LayoutDefinition')]");
        // //td[(text()='Home Page Layout 01')]/parent::tr/td/input[contains(@id, 'imgEdit')][contains(@id, 'LayoutDefinition')]
        System.out.println(findElement(editBtn).getAttribute("innerText"));

        try {
            waitForElement(moduleTitle);

            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            //driver.get(pageUrl);
            findElement(editBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(commentsTxt);
            findElement(commentsTxt).sendKeys(data.get("comment").toString());
            findElement(saveAndSubmitBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(editBtn);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            JSONObject jsonObj = (JSONObject) jsonMain.get(name);

            jsonObj.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            jsonObj.put("deleted", "false");

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

    public String getPageUrl (JSONObject obj, String name) {
        String  sItemID = JsonPath.read(obj, "$.['"+name+"'].url_query.ItemID");
        String  sLanguageId = JsonPath.read(obj, "$.['"+name+"'].url_query.LanguageId");
        String  sSectionId = JsonPath.read(obj, "$.['"+name+"'].url_query.SectionId");
        return desktopUrl.toString()+"default.aspx?ItemID="+sItemID+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }

}
