package pageobjects.Modules.Core;

import com.jayway.jsonpath.JsonPath;
import org.apache.commons.lang.ObjectUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;
import pageobjects.Dashboard.Dashboard;
import pageobjects.Modules.ModuleBase;
import pageobjects.PageAdmin.WorkflowState;
import pageobjects.PageObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static specs.AbstractSpec.*;

/**
 * Created by dannyl on 2017-07-17.
 */
public class Html extends AbstractPageObject {
    private static By workflowStateSpan, propertiesHref, commentsTxt, saveAndSubmitBtn, publishBtn, htmlContentDropdown, htmlDetailsText, saveBtn, deleteBtn, regionNameSelect;
    private By addNewModuleBtn, moduleTitleInput, moduleDefinitionSelect, includeLagacyModulesChk;
    private String sPathToPageFile, sFilePageJson, sPathToModuleFile, sFileModuleJson;
    private static JSONParser parser;
    private static ModuleBase moduleBase;
    private static final long DEFAULT_PAUSE = 2500;

    public Html(WebDriver driver) {
        super(driver);

        workflowStateSpan = By.xpath(propUIPageAdmin.getProperty("select_WorkflowState"));
        commentsTxt = By.xpath(propUIPageAdmin.getProperty("txtarea_Comments"));
        propertiesHref = By.xpath(propUIModules.getProperty("href_Properties"));
        htmlContentDropdown = By.xpath(propUIModulesCore.getProperty("html_ContentDropdown"));
        htmlDetailsText = By.xpath(propUIModulesCore.getProperty("html_Details"));
        saveAndSubmitBtn = By.xpath(propUIPageAdmin.getProperty("btn_SaveAndSubmit"));

        addNewModuleBtn = By.xpath(propUIModules.getProperty("btn_AddNewModule"));
        moduleTitleInput = By.xpath(propUIModules.getProperty("input_ModuleTitle"));
        moduleDefinitionSelect = By.xpath(propUIModules.getProperty("select_ModuleDefinition"));
        includeLagacyModulesChk = By.xpath(propUIModules.getProperty("chk_IncludeLagacyModules"));

        saveBtn = By.xpath(propUIPageAdmin.getProperty("btn_Save"));
        deleteBtn = By.xpath(propUIPageAdmin.getProperty("btn_Delete"));
        publishBtn = By.xpath(propUIPageAdmin.getProperty("btn_Publish"));
        regionNameSelect = By.xpath(propUIModules.getProperty("select_RegionName"));

        sPathToModuleFile = System.getProperty("user.dir") + propUIModulesCore.getProperty("dataPath_Core");
        sFileModuleJson = propUIModulesCore.getProperty("json_HtmlProp");

        sPathToPageFile = System.getProperty("user.dir") + propUIModules.getProperty("dataPath_Modules");
        sFilePageJson = propUIModules.getProperty("json_PagesProp");

        moduleBase = new ModuleBase(driver, sPathToModuleFile, sFileModuleJson);

        parser = new JSONParser();
    }

    public String saveAndSubmitModule(JSONObject modulesDataObj, String moduleName) throws InterruptedException {

        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToModuleFile + sFileModuleJson));
            JSONArray jsonArrProp = (JSONArray) modulesDataObj.get("properties");

            String moduleUrl = getModuleUrl(jsonObj, moduleName);
            driver.get(moduleUrl);
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(commentsTxt);

            JSONObject module = (JSONObject) jsonObj.get(moduleName);

            findElement(propertiesHref).click();
            Thread.sleep(DEFAULT_PAUSE);

            for (int i=0; i<jsonArrProp.size(); i++) {
                try {
                    By propertyTextValue = By.xpath("//td[contains(@class, 'DataGridItemBorderLeft')][(text()='" + jsonArrProp.get(i).toString().split(";")[0] + "')]/parent::tr/td/div/input[contains(@id, 'txtStatic')]");
                    findElement(propertyTextValue).clear();
                    findElement(propertyTextValue).sendKeys(jsonArrProp.get(i).toString().split(";")[1]);
                } catch (PageObject.ElementNotFoundException e) {
                    By propertyDropdownValue = By.xpath("//td[contains(@class, 'DataGridItemBorderLeft')][(text()='" + jsonArrProp.get(i).toString().split(";")[0] + "')]/parent::tr/td/div/select[contains(@id, 'ddlDynamic')]");
                    findElement(propertyDropdownValue).sendKeys(jsonArrProp.get(i).toString().split(";")[1]);
                }
            }
            try
            {
            findVisibleElement(htmlDetailsText).sendKeys(modulesDataObj.get("html_details").toString());
            }
            catch(NullPointerException e){

            }

            findElement(commentsTxt).sendKeys(modulesDataObj.get("comment").toString());
            findElement(saveAndSubmitBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            driver.get(moduleUrl);
            Thread.sleep(DEFAULT_PAUSE);

            module.put("properties", jsonArrProp);
            module.put("workflow_state", WorkflowState.FOR_APPROVAL.state());

            jsonObj.put(moduleName, module);

            FileWriter file = new FileWriter(sPathToModuleFile + sFileModuleJson);
            file.write(jsonObj.toJSONString().replace("\\", ""));
            file.flush();

            System.out.println(moduleName+ ": New "+moduleName+" has been submitted");
            return findElement(workflowStateSpan).getText();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String saveModule(JSONObject modulesDataObj, String moduleName) throws InterruptedException {
        String module_title, module_definition, region_name, source_file, qualified_path;

        try {

            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToPageFile + sFilePageJson));

            String pageUrl = moduleBase.getPageUrl(jsonObj, moduleName);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(commentsTxt);

            waitForElementToAppear(addNewModuleBtn);
            scrollToElementAndClick(addNewModuleBtn);
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(includeLagacyModulesChk);

            findElement(includeLagacyModulesChk).click();
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(moduleTitleInput);

            JSONObject module = new JSONObject();

            module_definition = modulesDataObj.get("module_definition").toString();
            source_file = modulesDataObj.get("source_file").toString();
            qualified_path = modulesDataObj.get("path").toString();
            Boolean moduleDefinitionExists = moduleBase.checkModuleDefinitionExists(module_definition);

            if (!moduleDefinitionExists) {
                try {
                    String moduleDefinitionState = addModuleDefinition(module_definition, source_file, qualified_path);
                    if (moduleDefinitionState.equals("Live")) {
                        driver.navigate().refresh();
                        Thread.sleep(DEFAULT_PAUSE);
                        waitForElement(commentsTxt);
                    } else {
                        return null;
                    }
                } catch (Exception e) {
                    return null;
                }
            }

            module_title = modulesDataObj.get("module_title").toString();
            findElement(moduleTitleInput).sendKeys(module_title);

            findElement(moduleDefinitionSelect).sendKeys(module_definition);
            module.put("module_definition", module_definition);

            region_name = modulesDataObj.get("region_name").toString();
            findElement(regionNameSelect).sendKeys(region_name);
            module.put("region_name", region_name);

            Thread.sleep(DEFAULT_PAUSE);

            findElement(saveBtn).click();
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(deleteBtn);


            module.put("workflow_state", WorkflowState.IN_PROGRESS.state());
            module.put("active", "true");

            URL pageURL = new URL(getUrl());
            String[] params = pageURL.getQuery().split("&");
            JSONObject jsonURLQuery = new JSONObject();
            for (String param:params) {
                jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);
            }
            module.put("url_query", jsonURLQuery);

            JSONObject jsonObjSave;
            try {
                jsonObjSave = (JSONObject) parser.parse(new FileReader(sPathToModuleFile + sFileModuleJson));
            } catch (ParseException e) {
                jsonObjSave = (JSONObject) parser.parse("{}");
            }

            jsonObjSave.put(module_title, module);

            try {
                FileWriter file = new FileWriter(sPathToModuleFile + sFileModuleJson);
                file.write(jsonObjSave.toJSONString().replace("\\", ""));
                file.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("New "+moduleName+" has been created");
            return findElement(workflowStateSpan).getText();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String addModuleDefinition(String friendly_name, String source_file, String qualified_path) throws Exception {
        ((JavascriptExecutor)driver).executeScript("window.open();");
        Thread.sleep(DEFAULT_PAUSE);

        ArrayList<String> tabs = new ArrayList<> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        driver.get(desktopUrl.toString());

        Dashboard dashboard = new Dashboard(driver);
        By siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        By moduleDefinitionListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_ModuleDefinitionList"));
        dashboard.openPageFromMenu(siteAdminMenuButton, moduleDefinitionListMenuItem);

        By addNewLink = By.xpath(propUISiteAdmin.getProperty("input_AddNew"));
        waitForElement(addNewLink);
        findElement(addNewLink).click();
        waitForElement(saveAndSubmitBtn);

        URL pageURL = new URL(getUrl());
        By friendlyNameField = By.xpath(propUISiteAdmin.getProperty("input_FriendlyName"));
        By sourceFileField = By.xpath(propUISiteAdmin.getProperty("input_SourceFile"));
        By useDefaultRb = By.xpath(propUISiteAdmin.getProperty("rb_UseDefault"));
        By qualifiedPathField = By.xpath(propUISiteAdmin.getProperty("input_QualifiedPath"));
        findElement(friendlyNameField).sendKeys(friendly_name);
        findElement(sourceFileField).sendKeys(source_file);
        findElement(qualifiedPathField).clear();
        findElement(qualifiedPathField).sendKeys(qualified_path);
        findElement(useDefaultRb).click();
        findElement(commentsTxt).sendKeys("Adding a new Module Definition: " + friendly_name);
        findElement(saveAndSubmitBtn).click();
        try{
            findElement(saveAndSubmitBtn).click();
        }
        catch (Exception e){

        }
        driver.get(pageURL.toString());
        waitForElement(publishBtn);

        findElement(commentsTxt).sendKeys("Publish a new Module Definition: " + friendly_name);
        findElement(publishBtn).click();
        Thread.sleep(DEFAULT_PAUSE);

        driver.get(pageURL.toString());
        String state = findElement(workflowStateSpan).getText();

        driver.switchTo().window(tabs.get(1)).close();
        Thread.sleep(DEFAULT_PAUSE);
        driver.switchTo().window(tabs.get(0));

        return state;
    }


    public String goToModuleEditPage(String moduleName) {
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToModuleFile + sFileModuleJson));
            driver.get(getModuleUrl(jsonObj, moduleName));
            findElement(propertiesHref).click();
            return findElement(workflowStateSpan).getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getModuleUrl(JSONObject obj, String moduleName) {
        String  sItemID = JsonPath.read(obj, "$.['"+moduleName+"'].url_query.ItemId");
        String  sLanguageId = JsonPath.read(obj, "$.['"+moduleName+"'].url_query.LanguageId");
        String  sSectionId = JsonPath.read(obj, "$.['"+moduleName+"'].url_query.SectionId");
        return desktopUrl.toString()+"default.aspx?ItemID="+sItemID+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }
}
