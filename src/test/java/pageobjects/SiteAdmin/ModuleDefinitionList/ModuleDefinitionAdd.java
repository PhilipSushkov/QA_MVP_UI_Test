package pageobjects.SiteAdmin.ModuleDefinitionList;

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
 * Created by philipsushkov on 2017-04-25.
 */

public class ModuleDefinitionAdd extends AbstractPageObject {
    private static By moduleTitle, friendlyNameField, sourceFileField, qualifiedPathField, ContentTypeField;
    private static By linkToEditPageSelect, linkToListPageSelect, linkToAdminPageSelect, linkToAdminTextField;
    private static By useDefaultRb, useCustomRb, activeChk, viewStateChk;
    private static By saveBtn, cancelBtn, deleteBtn, addNewLink, publishBtn, backBtn;
    private static By revertBtn, workflowStateSpan, commentsTxt, successMsg, saveAndSubmitBtn, currentContentSpan;
    private static String sPathToFile, sFileJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String PAGE_NAME="Module Definition";

    public ModuleDefinitionAdd(WebDriver driver) {
        super(driver);

        moduleTitle = By.xpath(propUISiteAdmin.getProperty("spanModule_Title"));
        friendlyNameField = By.xpath(propUISiteAdmin.getProperty("input_FriendlyName"));
        sourceFileField = By.xpath(propUISiteAdmin.getProperty("input_SourceFile"));
        qualifiedPathField = By.xpath(propUISiteAdmin.getProperty("input_QualifiedPath"));
        ContentTypeField = By.xpath(propUISiteAdmin.getProperty("input_ContentType"));
        linkToEditPageSelect = By.xpath(propUISiteAdmin.getProperty("select_LinkToEditPage"));
        linkToListPageSelect = By.xpath(propUISiteAdmin.getProperty("select_LinkToListPage"));
        linkToAdminPageSelect = By.xpath(propUISiteAdmin.getProperty("select_LinkToAdminPage"));
        linkToAdminTextField = By.xpath(propUISiteAdmin.getProperty("input_LinkToAdminText"));

        useDefaultRb = By.xpath(propUISiteAdmin.getProperty("rb_UseDefault"));
        useCustomRb = By.xpath(propUISiteAdmin.getProperty("rb_UseCustom"));
        activeChk = By.xpath(propUISiteAdmin.getProperty("chk_Active"));
        viewStateChk = By.xpath(propUISiteAdmin.getProperty("chk_ViewState"));

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
        currentContentSpan = By.xpath(propUISiteAdmin.getProperty("span_CurrentContent"));

        parser = new JSONParser();

        sPathToFile = System.getProperty("user.dir") + propUISiteAdmin.getProperty("dataPath_ModuleDefinitionList");
        sFileJson = propUISiteAdmin.getProperty("json_ModuleDefinition");
    }

    public String getTitle() {
        findElement(addNewLink).click();
        waitForElement(moduleTitle);
        String sTitle = getText(moduleTitle);
        findElement(cancelBtn).click();
        return sTitle;
    }

    public String saveModuleDefinition(JSONObject data, String name) {
        String friendly_name, source_file, qualified_path, content_type, link_to_edit_page;
        String link_to_list_page, link_to_admin_page, link_to_admin_text, user_control;
        Boolean active, enable_viewState;
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

            source_file = data.get("source_file").toString();
            findElement(sourceFileField).sendKeys(source_file);
            jsonObj.put("source_file", source_file);

            qualified_path = data.get("qualified_path").toString();
            findElement(qualifiedPathField).sendKeys(qualified_path);
            jsonObj.put("qualified_path", qualified_path);

            content_type = data.get("content_type").toString();
            if (!content_type.isEmpty()) {
                findElement(ContentTypeField).sendKeys(content_type);
                jsonObj.put("content_type", content_type);
            }

            link_to_edit_page = data.get("link_to_edit_page").toString();
            if (!link_to_edit_page.isEmpty()) {
                findElement(linkToEditPageSelect).sendKeys(link_to_edit_page);
                jsonObj.put("link_to_edit_page", link_to_edit_page);
            }

            link_to_list_page = data.get("link_to_list_page").toString();
            if (!link_to_list_page.isEmpty()) {
                findElement(linkToListPageSelect).sendKeys(link_to_list_page);
                jsonObj.put("link_to_list_page", link_to_list_page);
            }

            link_to_admin_page = data.get("link_to_admin_page").toString();
            if (!link_to_admin_page.isEmpty()) {
                findElement(linkToAdminPageSelect).sendKeys(link_to_admin_page);
                jsonObj.put("link_to_admin_page", link_to_admin_page);
            }

            link_to_admin_text = data.get("link_to_admin_text").toString();
            if (!link_to_admin_text.isEmpty()) {
                findElement(linkToAdminTextField).sendKeys(link_to_admin_text);
                jsonObj.put("link_to_admin_text", link_to_admin_text);
            }

            user_control = data.get("user_control").toString();
            jsonObj.put("user_control", user_control);
            if (user_control.equals("Use Default")) {
                findElement(useDefaultRb).click();
            } else if (user_control.equals("Custom Layout")) {
                findElement(useCustomRb).click();
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

            // Save ModuleDefinition Url
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



    public String getPageUrl(JSONObject obj, String name) {
        String  sItemID = JsonPath.read(obj, "$.['"+name+"'].url_query.ItemID");
        String  sLanguageId = JsonPath.read(obj, "$.['"+name+"'].url_query.LanguageId");
        String  sSectionId = JsonPath.read(obj, "$.['"+name+"'].url_query.SectionId");
        return desktopUrl.toString()+"default.aspx?ItemID="+sItemID+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }
}
