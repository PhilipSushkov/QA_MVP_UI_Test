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

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;
import pageobjects.PageAdmin.WorkflowState;

import static specs.AbstractSpec.*;

/**
 * Created by philipsushkov on 2017-06-12.
 */

public class StockHistorical extends AbstractPageObject {
    private static By addNewModuleBtn, backBtn, moduleTitleInput, moduleDefinitionSelect, includeLagacyModulesChk;
    private static By publishBtn, saveBtn, workflowStateSpan, currentContentSpan, propertiesHref, previewLnk;
    private static By commentsTxt, deleteBtn, saveAndSubmitBtn, regionNameSelect;
    private static String sPathToPageFile, sFilePageJson, sPathToModuleFile, sFileModuleJson;
    private static JSONParser parser;

    private static final long DEFAULT_PAUSE = 2500;

    // This test checks specific historical data on the following day:
    private static final String lookupMonth = "Jul";
    private static final String lookupDay = "1";
    private static final String lookupYear = "2015";

    public StockHistorical(WebDriver driver) {
        super(driver);

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
        backBtn = By.xpath(propUIPageAdmin.getProperty("btn_Back"));
        saveAndSubmitBtn = By.xpath(propUIPageAdmin.getProperty("btn_SaveAndSubmit"));

        sPathToPageFile = System.getProperty("user.dir") + propUIModules.getProperty("dataPath_Modules");
        sFilePageJson = propUIModules.getProperty("json_PagesProp");
        sPathToModuleFile = System.getProperty("user.dir") + propUIModulesFeed.getProperty("dataPath_Feed");
        sFileModuleJson = propUIModulesFeed.getProperty("json_StockHistoricalProp");

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

    public void lookupHistoricalValue(JSONObject module) {
        By lookupMonthSelect = By.xpath(module.get("module_path").toString() + propUIModulesFeed.getProperty("select_LookupMonth"));
        By lookupDaySelect = By.xpath(module.get("module_path").toString() + propUIModulesFeed.getProperty("select_LookupDay"));
        By lookupYearSelect = By.xpath(module.get("module_path").toString() + propUIModulesFeed.getProperty("select_LookupYear"));
        By lookupBtn = By.xpath(module.get("module_path").toString() + propUIModulesFeed.getProperty("btn_Lookup"));

        findElement(lookupMonthSelect).sendKeys(lookupMonth);
        findElement(lookupDaySelect).sendKeys(lookupDay);
        findElement(lookupYearSelect).sendKeys(lookupYear);
        findElement(lookupBtn).click();
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

}
