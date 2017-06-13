package pageobjects.Modules.Feed;

import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;
import pageobjects.PageAdmin.PageAdminList;
import pageobjects.PageAdmin.WorkflowState;
import util.Functions;

import static specs.AbstractSpec.*;

/**
 * Created by philipsushkov on 2017-06-12.
 */

public class StockHistorical2_375 extends AbstractPageObject {
    private static By addNewBtn, backBtn, sectionTitleInput, pageTypeInternalRd, publishBtn, hasContentChk;
    private static By pageTemplateSelect, parentPageSelect, showNavChk, openNewWindChk, saveBtn, workflowStateSpan, currentContentSpan;
    private static By parentUrlSpan, seoNameInput, previewLnk, breadcrumbDiv, commentsTxt, deleteBtn, addNewInput;
    private static By saveAndSubmitBtn, pageLayoutSelect, globalModulesChk;
    private static String sPathToFile, sFilePageJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private static PageAdminList pageAdminList;

    public StockHistorical2_375(WebDriver driver) {
        super(driver);

        addNewBtn = By.xpath(propUIPageAdmin.getProperty("btn_AddNew"));
        sectionTitleInput = By.xpath(propUIPageAdmin.getProperty("input_SectionTitle"));
        pageTypeInternalRd = By.xpath(propUIPageAdmin.getProperty("rd_PageTypeInt"));
        pageTemplateSelect = By.xpath(propUIPageAdmin.getProperty("select_PageTemplate"));
        parentPageSelect = By.xpath(propUIPageAdmin.getProperty("select_ParentPage"));
        hasContentChk = By.xpath(propUIPageAdmin.getProperty("chk_HasContent"));
        showNavChk = By.xpath(propUIPageAdmin.getProperty("chk_ShowInNav"));
        openNewWindChk = By.xpath(propUIPageAdmin.getProperty("chk_OpenInNewWindow"));
        workflowStateSpan = By.xpath(propUIPageAdmin.getProperty("select_WorkflowState"));
        parentUrlSpan = By.xpath(propUIPageAdmin.getProperty("span_YourPageUrl"));
        seoNameInput = By.xpath(propUIPageAdmin.getProperty("input_SeoName"));
        previewLnk = By.xpath(propUIPageAdmin.getProperty("lnk_Preview"));
        breadcrumbDiv = By.xpath(propUIPageAdmin.getProperty("div_Breadcrumb"));
        commentsTxt = By.xpath(propUIPageAdmin.getProperty("txtarea_Comments"));
        addNewInput = By.xpath(propUIPageAdmin.getProperty("input_AddNew"));
        currentContentSpan = By.xpath(propUIPageAdmin.getProperty("span_CurrentContent"));
        pageLayoutSelect = By.xpath(propUIPageAdmin.getProperty("select_PageLayout"));
        globalModulesChk = By.xpath(propUIPageAdmin.getProperty("chk_GlobalModuleSet"));

        saveBtn = By.xpath(propUIPageAdmin.getProperty("btn_Save"));
        deleteBtn = By.xpath(propUIPageAdmin.getProperty("btn_Delete"));
        publishBtn = By.xpath(propUIPageAdmin.getProperty("btn_Publish"));
        backBtn = By.xpath(propUIPageAdmin.getProperty("btn_Back"));
        saveAndSubmitBtn = By.xpath(propUIPageAdmin.getProperty("btn_SaveAndSubmit"));

        sPathToFile = System.getProperty("user.dir") + propUIModules.getProperty("dataPath_Modules");
        sFilePageJson = propUIModules.getProperty("json_PagesProp");

        parser = new JSONParser();
        pageAdminList = new PageAdminList(driver);;
    }

}
