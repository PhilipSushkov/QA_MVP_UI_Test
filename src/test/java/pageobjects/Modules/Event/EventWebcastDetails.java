package pageobjects.Modules.Event;

import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;
import pageobjects.PageAdmin.WorkflowState;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static specs.AbstractSpec.*;
import static specs.AbstractSpec.desktopUrl;

/**
 * Created by dannyl on 2017-07-07.
 */
public class EventWebcastDetails extends AbstractPageObject {
    private static By addNewModuleBtn, backBtn, moduleTitleInput, moduleDefinitionSelect, includeLegacyModulesChk;
    private static By publishBtn, saveBtn, workflowStateSpan, currentContentSpan, propertiesHref, previewLnk;
    private static By livePageTitle, liveModuleTitleSpan, liveEventModulePageMenuBtn;
    private static By commentsTxt, deleteBtn, saveAndSubmitBtn, regionNameSelect;
    private static String sPathToPageFile, sFilePageJson, sPathToModuleFile, sFileModuleJson, sPathToFile;
    private static JSONParser parser;

    private static final long DEFAULT_PAUSE = 2500;

    public EventWebcastDetails(WebDriver driver) {
        super(driver);

        addNewModuleBtn = By.xpath(propUIModules.getProperty("btn_AddNewModule"));
        moduleTitleInput = By.xpath(propUIModules.getProperty("input_ModuleTitle"));
        moduleDefinitionSelect = By.xpath(propUIModules.getProperty("select_ModuleDefinition"));
        includeLegacyModulesChk = By.xpath(propUIModules.getProperty("chk_IncludeLagacyModules"));
        regionNameSelect = By.xpath(propUIModules.getProperty("select_RegionName"));
        workflowStateSpan = By.xpath(propUIPageAdmin.getProperty("select_WorkflowState"));
        commentsTxt = By.xpath(propUIPageAdmin.getProperty("txtarea_Comments"));
        currentContentSpan = By.xpath(propUIPageAdmin.getProperty("span_CurrentContent"));
        propertiesHref = By.xpath(propUIModules.getProperty("href_Properties"));
        previewLnk = By.xpath(propUIModules.getProperty("lnk_Preview"));

        saveBtn = By.xpath(propUIPageAdmin.getProperty("btn_Save"));
        deleteBtn = By.xpath(propUIPageAdmin.getProperty("btn_Delete"));
        publishBtn = By.xpath(propUIPageAdmin.getProperty("btn_Publish"));
        backBtn = By.xpath(propUIPageAdmin.getProperty("btn_Back"));
        saveAndSubmitBtn = By.xpath(propUIPageAdmin.getProperty("btn_SaveAndSubmit"));

        livePageTitle = By.xpath(propUIModules.getProperty("page_Title"));
        liveModuleTitleSpan = By.xpath(propUIModules.getProperty("span_LiveModuleTitle"));

        sPathToPageFile = System.getProperty("user.dir") + propUIModules.getProperty("dataPath_Modules");
        sFilePageJson = propUIModules.getProperty("json_PagesProp");
        sPathToModuleFile = System.getProperty("user.dir") + propUIModulesEvent.getProperty("dataPath_Event");
        sFileModuleJson = propUIModulesEvent.getProperty("json_EventWebcastDetailsProp");

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
                } catch (ElementNotFoundException e) {
                    By propertyDropdownValue = By.xpath("//td[contains(@class, 'DataGridItemBorderLeft')][(text()='" + jsonArrProp.get(i).toString().split(";")[0] + "')]/parent::tr/td/div/select[contains(@id, 'ddlDynamic')]");
                    findElement(propertyDropdownValue).sendKeys(jsonArrProp.get(i).toString().split(";")[1]);
                }
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

    public String openModulePreviewForEvents(String moduleName, String moduleTitle) {
        // Creates the url for the eventWebcastDetails page that contains the Press Release we want
        String sectionId = "";
        String title = "";
        String pressReleaseId = "";
        String languageId = "";
        sPathToFile = System.getProperty("user.dir") + propUIModulesEvent.getProperty("dataPath_Event");
        String contentDataPath = System.getProperty("user.dir") + propUIModules.getProperty("dataPath_Content")+ propUIModules.getProperty("json_ContentData");
        String contentPath = System.getProperty("user.dir") + propUIModules.getProperty("dataPath_Content")+ propUIModules.getProperty("json_ContentProp");
        try
        {   Object obj = parser.parse(new FileReader(contentDataPath));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray jsonArray = (JSONArray) jsonObject.get(moduleName);
            // Must select an event that has reminders set as true in its properties
            jsonObject = (JSONObject) jsonArray.get(4);
            title = (String) jsonObject.get("headline");

            obj = parser.parse(new FileReader(sPathToFile + sFileModuleJson));
            String sectionIdPath = "$['"+ moduleTitle +"'].url_query.ItemWorkflowId";
            sectionId = JsonPath.read(obj, sectionIdPath);

            obj = parser.parse(new FileReader(contentPath));
            pressReleaseId = JsonPath.read(obj, "$['" + moduleName + "'].['" + title + "'].url_query.ItemID");
            languageId = JsonPath.read(obj, "$['" + moduleName + "'].['" + title + "'].url_query.LangugageId");
        }
        catch (Exception e)
        {
            System.out.println("Failed to read data.");
        }
        String baseUrl = desktopUrl.toString();
        baseUrl = baseUrl.replaceAll("/admin/", "");
        String url = baseUrl + "/preview/preview.aspx?EventId=" + pressReleaseId + "&LanguageId=" + languageId + "&SectionId=" + sectionId;
        ((JavascriptExecutor)driver).executeScript("window.open();");

        ArrayList<String> tabs = new ArrayList<> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.get(url);

        return driver.getTitle();
    }

    private String getModuleUrl(JSONObject obj, String moduleName) {
        String  sItemID = JsonPath.read(obj, "$.['"+moduleName+"'].url_query.ItemId");
        String  sLanguageId = JsonPath.read(obj, "$.['"+moduleName+"'].url_query.LanguageId");
        String  sSectionId = JsonPath.read(obj, "$.['"+moduleName+"'].url_query.SectionId");
        return desktopUrl.toString()+"default.aspx?ItemID="+sItemID+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }
}
