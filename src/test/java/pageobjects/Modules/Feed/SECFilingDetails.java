package pageobjects.Modules.Feed;

import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;
import pageobjects.LoginPage.LoginPage;
import pageobjects.PageAdmin.WorkflowState;
import pageobjects.PageObject;
import pageobjects.Modules.ModuleBase;
import sun.security.pkcs11.Secmod;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static specs.AbstractSpec.*;
import static specs.AbstractSpec.desktopUrl;

/**
 * Created by zacharyk on 2017-06-26.
 */
public class SECFilingDetails extends AbstractPageObject {
    private static By workflowStateSpan, propertiesHref, commentsTxt, saveAndSubmitBtn, SECFilingLink, moduleTitle;
    private static String sPathToModuleFile, sFileModuleJson, sPathToFile;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private static ModuleBase moduleBase;

    public SECFilingDetails(WebDriver driver) {
        super(driver);

        workflowStateSpan = By.xpath(propUIPageAdmin.getProperty("select_WorkflowState"));
        commentsTxt = By.xpath(propUIPageAdmin.getProperty("txtarea_Comments"));
        propertiesHref = By.xpath(propUIModules.getProperty("href_Properties"));
        SECFilingLink = By.xpath(propUIModulesFeed.getProperty("div_DetailsLink"));
        moduleTitle = By.xpath(propUIModulesFeed.getProperty("span_ModuleTitle"));

        saveAndSubmitBtn = By.xpath(propUIPageAdmin.getProperty("btn_SaveAndSubmit"));

        sPathToModuleFile = System.getProperty("user.dir") + propUIModulesFeed.getProperty("dataPath_Feed");
        sFileModuleJson = propUIModulesFeed.getProperty("json_SECFilingDetailsProp");

        parser = new JSONParser();

        moduleBase = new ModuleBase(driver, sPathToModuleFile, sFileModuleJson);
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
    // Must use this method instead of the one in ModuleBase class because in order to open the SECFilingDetails Module you have to click an SEC filing
    public String openModulePreviewForSECFiling(String moduleTitle) throws InterruptedException{
        // Creates the url for the pressReleaseDetails page that contains the Press Release we want
        String sectionId = "";
        String languageId = "";
        sPathToFile = System.getProperty("user.dir") + propUIModulesFeed.getProperty("dataPath_Feed");
        String contentDataPath = sPathToFile + propUIModulesFeed.getProperty("json_SECFilingProp");

        try
        {   Object obj = parser.parse(new FileReader(contentDataPath));
            String sectionIdPath = "$['"+ moduleTitle +"'].url_query.ItemWorkflowId";
            sectionId = JsonPath.read(obj, sectionIdPath);
            String languageIdPath = "$['"+ moduleTitle +"'].url_query.LanguageId";
            languageId = JsonPath.read(obj, languageIdPath);
        }
        catch (Exception e)
        {
            System.out.println("Failed to read data.");
        }
        String baseUrl = desktopUrl.toString();
        baseUrl = baseUrl.replaceAll("/admin/", "");
        String url = baseUrl + "/preview/preview.aspx?SectionId=" + sectionId + "&LanguageId=" + languageId;
        ((JavascriptExecutor)driver).executeScript("window.open();");

        ArrayList<String> tabs = new ArrayList<> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.get(url);

        driver.findElement(SECFilingLink).click();
        Thread.sleep(DEFAULT_PAUSE);
        String title = driver.findElement(this.moduleTitle).getText();
        return title;
    }
    // Must use this method instead of the one in ModuleBase class because in order to open the SECFilingDetails Module you have to click an SEC filing
    public String openModuleLiveSite(String moduleName) throws InterruptedException{
        moduleBase.openModuleLiveSite("sec_filing");
        // Clicking the first SEC filing.
        driver.findElement(SECFilingLink).click();
        Thread.sleep(DEFAULT_PAUSE);
        String title = driver.findElement(this.moduleTitle).getText();
        return title;
    }

    private String getModuleUrl(JSONObject obj, String moduleName) {
        String  sItemID = JsonPath.read(obj, "$.['"+moduleName+"'].url_query.ItemId");
        String  sLanguageId = JsonPath.read(obj, "$.['"+moduleName+"'].url_query.LanguageId");
        String  sSectionId = JsonPath.read(obj, "$.['"+moduleName+"'].url_query.SectionId");
        return desktopUrl.toString()+"default.aspx?ItemID="+sItemID+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }
}
