package pageobjects.Modules.Core;

import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;
import pageobjects.PageAdmin.WorkflowState;
import pageobjects.PageObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static specs.AbstractSpec.*;

/**
 * Created by zacharyk on 2017-07-10.
 */
public class QuickLink extends AbstractPageObject {
    private static By workflowStateSpan, propertiesHref, commentsTxt, saveAndSubmitBtn;
    private static By quickLinkSectionSpan, moduleInstanceSectionSpan, addNewQuickLinkInput, quickLinkDropDown, quickLinkComments, quickLinkSelectChk, quickLinkPublishBtn;
    private static String sPathToModuleFile, sFileModuleJson, sPathToContentFile, sFileContentJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private static final String CONTENT_TYPE = "quicklink";

    public QuickLink(WebDriver driver) {
        super(driver);

        workflowStateSpan = By.xpath(propUIPageAdmin.getProperty("select_WorkflowState"));
        commentsTxt = By.xpath(propUIPageAdmin.getProperty("txtarea_Comments"));
        propertiesHref = By.xpath(propUIModules.getProperty("href_Properties"));
        saveAndSubmitBtn = By.xpath(propUIPageAdmin.getProperty("btn_SaveAndSubmit"));

        quickLinkSectionSpan = By.xpath(propUIModulesCore.getProperty("span_QuickLinkModuleHeader"));
        moduleInstanceSectionSpan = By.xpath(propUIModulesCore.getProperty("span_ModuleInstanceHeader"));
        addNewQuickLinkInput = By.xpath(propUIModulesCore.getProperty("input_AddNewQuickLink"));
        quickLinkDropDown = By.xpath(propUIModulesCore.getProperty("select_QuickLinkDropDown"));
        quickLinkComments = By.xpath(propUIModulesCore.getProperty("txtarea_QuickLinkComments"));
        quickLinkSelectChk = By.xpath(propUIModulesCore.getProperty("chk_QuickLinkSelect"));
        quickLinkPublishBtn = By.xpath(propUIModulesCore.getProperty("btn_PublishQuickLinks"));

        sPathToModuleFile = System.getProperty("user.dir") + propUIModulesCore.getProperty("dataPath_Core");
        sFileModuleJson = propUIModulesCore.getProperty("json_QuickLinkProp");
        sPathToContentFile = System.getProperty("user.dir") + propUIModules.getProperty("dataPath_Content");
        sFileContentJson = propUIModules.getProperty("json_ContentData");

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

            // add quicklinks to the module
            findElement(quickLinkSectionSpan).click();

            JSONObject contentObj = (JSONObject) parser.parse(new FileReader(sPathToContentFile + sFileContentJson));
            JSONArray quicklinks = (JSONArray) contentObj.get(CONTENT_TYPE);
            JSONObject quicklinkObj;
            for (Object quicklink : quicklinks) {

                quicklinkObj = (JSONObject) quicklink;

                findElement(addNewQuickLinkInput).click();
                waitForElement(saveAndSubmitBtn);

                findElement(quickLinkDropDown).sendKeys(quicklinkObj.get("quicklink_description").toString());
                findElement(quickLinkComments).sendKeys("Adding quicklink to module for Selenium Testing");

                findElement(saveAndSubmitBtn).click();
                waitForElement(addNewQuickLinkInput);
            }

            List<WebElement> quicklinkSelectChks = findElements(quickLinkSelectChk);
            for (WebElement chkBox : quicklinkSelectChks) {
                chkBox.click();
            }

            Thread.sleep(DEFAULT_PAUSE);
            findElement(quickLinkPublishBtn).click();
            Thread.sleep(DEFAULT_PAUSE);
            findElement(moduleInstanceSectionSpan).click();

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
