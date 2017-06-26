package pageobjects.Modules;

import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import pageobjects.AbstractPageObject;
import pageobjects.Dashboard.Dashboard;
import pageobjects.PageAdmin.WorkflowState;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static specs.AbstractSpec.*;
import static specs.AbstractSpec.desktopUrl;

/**
 * Created by zacharyk on 2017-06-21.
 */

public class ModuleBase extends AbstractPageObject {
    private static By addNewModuleBtn, moduleTitleInput, moduleDefinitionSelect, includeLagacyModulesChk;
    private static By publishBtn, saveBtn, workflowStateSpan, currentContentSpan, propertiesHref;
    private static By commentsTxt, deleteBtn, saveAndSubmitBtn, regionNameSelect, previewLnk;
    private static String sPathToPageFile, sFilePageJson, sPathToModuleFile, sFileModuleJson;
    private static JSONParser parser;

    private static final long DEFAULT_PAUSE = 2500;

    public ModuleBase(WebDriver driver, String sPathToSection, String sPathToModuleProp) {
        super(driver);
        this.sPathToModuleFile = sPathToSection;
        this.sFileModuleJson = sPathToModuleProp;

        addNewModuleBtn = By.xpath(propUIModules.getProperty("btn_AddNewModule"));
        moduleTitleInput = By.xpath(propUIModules.getProperty("input_ModuleTitle"));
        moduleDefinitionSelect = By.xpath(propUIModules.getProperty("select_ModuleDefinition"));
        includeLagacyModulesChk = By.xpath(propUIModules.getProperty("chk_IncludeLagacyModules"));
        regionNameSelect = By.xpath(propUIModules.getProperty("select_RegionName"));
        workflowStateSpan = By.xpath(propUIPageAdmin.getProperty("select_WorkflowState"));
        commentsTxt = By.xpath(propUIPageAdmin.getProperty("txtarea_Comments"));
        currentContentSpan = By.xpath(propUIPageAdmin.getProperty("span_CurrentContent"));
        propertiesHref = By.xpath(propUIModules.getProperty("href_Properties"));
        previewLnk = By.xpath(propUIModules.getProperty("lnk_Preview"));

        saveBtn = By.xpath(propUIPageAdmin.getProperty("btn_Save"));
        deleteBtn = By.xpath(propUIPageAdmin.getProperty("btn_Delete"));
        publishBtn = By.xpath(propUIPageAdmin.getProperty("btn_Publish"));
        saveAndSubmitBtn = By.xpath(propUIPageAdmin.getProperty("btn_SaveAndSubmit"));

        sPathToPageFile = System.getProperty("user.dir") + propUIModules.getProperty("dataPath_Modules");
        sFilePageJson = propUIModules.getProperty("json_PagesProp");

        parser = new JSONParser();
    }

    public String saveModule(JSONObject modulesDataObj, String moduleName) throws InterruptedException {
        String module_title, module_definition, region_name, source_file;

        try {

            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToPageFile + sFilePageJson));

            String pageUrl = getPageUrl(jsonObj, moduleName);
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

            module_definition = modulesDataObj.get("module_definition").toString();
            source_file = modulesDataObj.get("source_file").toString();
            Boolean moduleDefinitionExists = checkModuleDefinitionExists(module_definition);

            if (!moduleDefinitionExists) {
                try {
                    String moduleDefinitionState = addModuleDefinition(module_definition, source_file);
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
                //System.out.println(jsonArrProp.get(i).toString());
                //String prop[] = jsonArrProp.get(i).toString().split(";");
                //System.out.println(prop[0]);
                By propertyValue = By.xpath("//td[contains(@class, 'DataGridItemBorderLeft')][(text()='"+jsonArrProp.get(i).toString().split(";")[0]+"')]/parent::tr/td/div/input[contains(@id, 'txtStatic')]");
                findElement(propertyValue).clear();
                findElement(propertyValue).sendKeys(jsonArrProp.get(i).toString().split(";")[1]);
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

    public String publishModule(String moduleName) throws InterruptedException {
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToModuleFile + sFileModuleJson));

            String moduleUrl = getModuleUrl(jsonObj, moduleName);
            driver.get(moduleUrl);
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(publishBtn);
            findElement(publishBtn).click();
            Thread.sleep(DEFAULT_PAUSE*2);

            driver.get(moduleUrl);
            Thread.sleep(DEFAULT_PAUSE);

            JSONObject moduleObj = (JSONObject) jsonObj.get(moduleName);

            moduleObj.put("workflow_state", WorkflowState.LIVE.state());

            jsonObj.put(moduleName, moduleObj);

            FileWriter file = new FileWriter(sPathToModuleFile + sFileModuleJson);
            file.write(jsonObj.toJSONString().replace("\\", ""));
            file.flush();


            Thread.sleep(DEFAULT_PAUSE*2);
            driver.navigate().refresh();

            System.out.println("New "+moduleName+" has been published");
            return findElement(workflowStateSpan).getText();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String setupAsDeletedModule(String moduleName) throws InterruptedException {
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToModuleFile + sFileModuleJson));

            String moduleUrl = getModuleUrl(jsonObj, moduleName);
            driver.get(moduleUrl);
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(commentsTxt);
            findElement(commentsTxt).sendKeys("Removing the module");
            findElement(deleteBtn).click();

            Thread.sleep(DEFAULT_PAUSE);

            driver.get(moduleUrl);
            waitForElement(currentContentSpan);

            JSONObject moduleObj = (JSONObject) jsonObj.get(moduleName);

            moduleObj.put("workflow_state", WorkflowState.FOR_APPROVAL.state());

            jsonObj.put(moduleName, moduleObj);

            FileWriter file = new FileWriter(sPathToModuleFile + sFileModuleJson);
            file.write(jsonObj.toJSONString().replace("\\", ""));
            file.flush();

            System.out.println(moduleName+ ": Module set up as deleted");
            return findElement(currentContentSpan).getText();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String removeModule(JSONObject modulesDataObj, String moduleName) throws InterruptedException {
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToModuleFile + sFileModuleJson));

            String moduleUrl = getModuleUrl(jsonObj, moduleName);
            driver.get(moduleUrl);
            Thread.sleep(DEFAULT_PAUSE);

            if (findElement(currentContentSpan).getText().equals(WorkflowState.DELETE_PENDING.state())) {

                waitForElement(commentsTxt);
                findElement(commentsTxt).sendKeys("Approving the module removal");
                findElement(publishBtn).click();

                Thread.sleep(DEFAULT_PAUSE*2);

                driver.get(moduleUrl);
                Thread.sleep(DEFAULT_PAUSE);

                jsonObj.remove(moduleName);

                FileWriter file = new FileWriter(sPathToModuleFile + sFileModuleJson);
                file.write(jsonObj.toJSONString().replace("\\", ""));
                file.flush();

                Thread.sleep(DEFAULT_PAUSE*2);
                driver.navigate().refresh();

                System.out.println(moduleName+ ": Module has been removed");
                return findElement(workflowStateSpan).getText();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String openModulePreview(String moduleName) {
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToModuleFile + sFileModuleJson));
            String moduleURL = getModuleUrl(jsonObj, moduleName);
            driver.get(moduleURL);

            findElement(previewLnk).click();

            Thread.sleep(DEFAULT_PAUSE);

            ArrayList<String> tabs = new ArrayList<> (driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return driver.getTitle();
    }

    public String openModuleLiveSite(String moduleName) {
        try {
            ((JavascriptExecutor)driver).executeScript("window.open();");

            Thread.sleep(DEFAULT_PAUSE);

            ArrayList<String> tabs = new ArrayList<> (driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));

            JSONObject jsonObj = (JSONObject)  parser.parse(new FileReader(sPathToPageFile + sFilePageJson));
            String moduleURL = JsonPath.read(jsonObj, "$.['"+moduleName+"'].your_page_url");
            driver.get(moduleURL);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return driver.getTitle();
    }

    public void closeWindow() {
        try {
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());

            driver.switchTo().window(tabs.get(1)).close();
            Thread.sleep(DEFAULT_PAUSE);
            driver.switchTo().window(tabs.get(0));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getPageUrl(JSONObject obj, String moduleName) {
        String  sItemID = JsonPath.read(obj, "$.['"+moduleName+"'].url_query.ItemID");
        String  sLanguageId = JsonPath.read(obj, "$.['"+moduleName+"'].url_query.LanguageId");
        String  sSectionId = JsonPath.read(obj, "$.['"+moduleName+"'].url_query.SectionId");
        return desktopUrl.toString()+"default.aspx?ItemID="+sItemID+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }

    private String getModuleUrl(JSONObject obj, String moduleName) {
        String  sItemID = JsonPath.read(obj, "$.['"+moduleName+"'].url_query.ItemId");
        String  sLanguageId = JsonPath.read(obj, "$.['"+moduleName+"'].url_query.LanguageId");
        String  sSectionId = JsonPath.read(obj, "$.['"+moduleName+"'].url_query.SectionId");
        return desktopUrl.toString()+"default.aspx?ItemID="+sItemID+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }

    private boolean checkModuleDefinitionExists(String sModuleDefinition) {
        Select select = new Select(findElement(moduleDefinitionSelect));
        for (int i=0; i < select.getOptions().size(); i++) {
            //System.out.println(select.getOptions().get(i).getAttribute("text").toString() + " - " + sModuleDefinition);
            if (select.getOptions().get(i).getAttribute("text").toString().trim().equals(sModuleDefinition)) {

                return true;
            }
        }
        return false;
    }

    private String addModuleDefinition(String friendly_name, String source_file) throws Exception {
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
        findElement(friendlyNameField).sendKeys(friendly_name);
        findElement(sourceFileField).sendKeys(source_file);
        findElement(useDefaultRb).click();
        findElement(commentsTxt).sendKeys("Adding a new Module Definition: " + friendly_name);
        findElement(saveAndSubmitBtn).click();

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
}
