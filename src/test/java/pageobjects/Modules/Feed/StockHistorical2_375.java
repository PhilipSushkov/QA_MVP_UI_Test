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
    private static By addNewModuleBtn, backBtn, moduleTitleInput, moduleDefinitionSelect, includeLagacyModulesChk;
    private static By publishBtn, saveBtn, workflowStateSpan, currentContentSpan;
    private static By commentsTxt, deleteBtn, saveAndSubmitBtn;
    private static String sPathToPageFile, sFilePageJson, sPathToModuleFile, sFileModuleJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;

    public StockHistorical2_375(WebDriver driver) {
        super(driver);

        addNewModuleBtn = By.xpath(propUIModules.getProperty("btn_AddNewModule"));
        moduleTitleInput = By.xpath(propUIModules.getProperty("input_ModuleTitle"));
        moduleDefinitionSelect = By.xpath(propUIModules.getProperty("select_ModuleDefinition"));
        includeLagacyModulesChk = By.xpath(propUIModules.getProperty("chk_IncludeLagacyModules"));
        workflowStateSpan = By.xpath(propUIPageAdmin.getProperty("select_WorkflowState"));
        commentsTxt = By.xpath(propUIPageAdmin.getProperty("txtarea_Comments"));
        currentContentSpan = By.xpath(propUIPageAdmin.getProperty("span_CurrentContent"));

        saveBtn = By.xpath(propUIPageAdmin.getProperty("btn_Save"));
        deleteBtn = By.xpath(propUIPageAdmin.getProperty("btn_Delete"));
        publishBtn = By.xpath(propUIPageAdmin.getProperty("btn_Publish"));
        backBtn = By.xpath(propUIPageAdmin.getProperty("btn_Back"));
        saveAndSubmitBtn = By.xpath(propUIPageAdmin.getProperty("btn_SaveAndSubmit"));

        sPathToPageFile = System.getProperty("user.dir") + propUIModules.getProperty("dataPath_Modules");
        sFilePageJson = propUIModules.getProperty("json_PagesProp");
        sPathToModuleFile = System.getProperty("user.dir") + propUIModulesFeed.getProperty("dataPath_Feed");
        sFileModuleJson = propUIModulesFeed.getProperty("json_DtockHistorical2_375Prop");

        parser = new JSONParser();
    }

    public String saveModule(JSONObject modulesDataObj, String moduleName) throws InterruptedException {
        String module_title, module_definition;
        String pageName = moduleName;

        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToPageFile + sFilePageJson));

            String pageUrl = getPageUrl(jsonObj, pageName);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(commentsTxt);

            findElement(addNewModuleBtn).click();
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(includeLagacyModulesChk);

            findElement(includeLagacyModulesChk).click();
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(moduleTitleInput);

            JSONObject module = new JSONObject();

            module_title = modulesDataObj.get("module_title").toString();
            findElement(moduleTitleInput).sendKeys(module_title);
            module.put("module_title", module_title);

            module_definition = modulesDataObj.get("module_definition").toString();
            findElement(moduleDefinitionSelect).sendKeys(module_definition);
            module.put("module_definition", module_definition);




        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        /*
        String your_page_url, parent_page, page_type;

        waitForElement(addNewBtn);
        findElement(addNewBtn).click();
        waitForElement(backBtn);

        try {
            findElement(sectionTitleInput).sendKeys(pageName);

            try {
                page_type = pagesDataObj.get("page_type").toString();
                if (pagesDataObj.get("page_type").toString().equals("Internal")) {
                    findElement(pageTypeInternalRd).click();
                    Thread.sleep(DEFAULT_PAUSE);
                    findElement(pageTemplateSelect).sendKeys(pagesDataObj.get("page_template").toString());
                } else {
                    System.out.println("Page type in not defined. May lead to incorrect test implementation.");
                }
            } catch (NullPointerException e) {
                page_type = "Internal";
                findElement(pageTypeInternalRd).click();
                Thread.sleep(DEFAULT_PAUSE);
                findElement(pageTemplateSelect).sendKeys("Blank Template");
            }

            parent_page = pagesDataObj.get("parent_page").toString();
            findElement(parentPageSelect).sendKeys(parent_page);

            try {
                if (Boolean.parseBoolean(pagesDataObj.get("show_in_navigation").toString())) {
                    if (!Boolean.parseBoolean(findElement(showNavChk).getAttribute("checked"))) {
                        findElement(showNavChk).click();
                    } else {
                    }
                } else {
                    if (!Boolean.parseBoolean(findElement(showNavChk).getAttribute("checked"))) {
                    } else {
                        findElement(showNavChk).click();
                    }
                }
            } catch (NullPointerException e) {
            }

            try {
                if (Boolean.parseBoolean(pagesDataObj.get("open_in_new_window").toString())) {
                    if (!Boolean.parseBoolean(findElement(openNewWindChk).getAttribute("checked"))) {
                        findElement(openNewWindChk).click();
                    } else {
                    }
                } else {
                    if (!Boolean.parseBoolean(findElement(openNewWindChk).getAttribute("checked"))) {
                    } else {
                        findElement(openNewWindChk).click();
                    }
                }
            } catch (NullPointerException e) {
            }


            Thread.sleep(DEFAULT_PAUSE);

            findElement(saveBtn).click();
            waitForElement(deleteBtn);

            Thread.sleep(DEFAULT_PAUSE);

            // Write page parameters to json
            JSONObject jsonObj = new JSONObject();

            try {
                jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            } catch (ParseException e) {
                jsonObj = (JSONObject) parser.parse("{}");
            }

            JSONObject page = new JSONObject();
            your_page_url = findElement(parentUrlSpan).getText() + findElement(seoNameInput).getAttribute("value");
            page.put("your_page_url", your_page_url);
            page.put("parent_page", parent_page);
            page.put("page_type", page_type);

            page.put("workflow_state", WorkflowState.IN_PROGRESS.state());
            page.put("active", "true");
            page.put("show_in_navigation", "true");

            URL pageURL = new URL(getUrl());
            String[] params = pageURL.getQuery().split("&");
            JSONObject jsonURLQuery = new JSONObject();
            for (String param:params) {
                jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);
            }
            page.put("url_query", jsonURLQuery);
            jsonObj.put(pageName, page);

            try {
                FileWriter file = new FileWriter(sPathToFile + sFilePageJson);
                file.write(jsonObj.toJSONString().replace("\\", ""));
                file.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(pageName+ ": New "+pageName+" Page has been created");
            return findElement(workflowStateSpan).getText();

        } catch (Exception e) {
            e.printStackTrace();
        }

        */

        return "In Progress";
    }

    private String getPageUrl(JSONObject obj, String pageName) {
        String  sItemID = JsonPath.read(obj, "$.['"+pageName+"'].url_query.ItemID");
        String  sLanguageId = JsonPath.read(obj, "$.['"+pageName+"'].url_query.LanguageId");
        String  sSectionId = JsonPath.read(obj, "$.['"+pageName+"'].url_query.SectionId");
        return desktopUrl.toString()+"default.aspx?ItemID="+sItemID+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }

}
