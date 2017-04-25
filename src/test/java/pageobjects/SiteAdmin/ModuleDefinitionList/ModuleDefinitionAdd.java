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

        sPathToFile = System.getProperty("user.dir") + propUISiteAdmin.getProperty("dataPath_LayoutDefinitionList");
        sFileJson = propUISiteAdmin.getProperty("json_LayoutDefinitions");
    }

    public String getTitle() {
        findElement(addNewLink).click();
        waitForElement(moduleTitle);
        String sTitle = getText(moduleTitle);
        findElement(cancelBtn).click();
        return sTitle;
    }

    public String saveModuleDefinition(JSONObject data, String name) {
        System.out.println(data.get("friendly_name").toString());

        return null;
    }



    public String getPageUrl(JSONObject obj, String name) {
        String  sItemID = JsonPath.read(obj, "$.['"+name+"'].url_query.ItemID");
        String  sLanguageId = JsonPath.read(obj, "$.['"+name+"'].url_query.LanguageId");
        String  sSectionId = JsonPath.read(obj, "$.['"+name+"'].url_query.SectionId");
        return desktopUrl.toString()+"default.aspx?ItemID="+sItemID+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }
}
