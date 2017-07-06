package specs.Modules.Feed;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Modules.ModuleBase;
import pageobjects.Modules.PageForModules;
import pageobjects.Modules.Feed.SECFiling;
import pageobjects.Modules.Feed.SECFilingDetails;
import pageobjects.PageAdmin.WorkflowState;
import specs.AbstractSpec;
import specs.Modules.util.ModuleFunctions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by zacharyk on 2017-06-26.
 */
public class CheckSECFiling extends AbstractSpec {

    // NOTE: THIS TEST DEPENDS ON PRE-EXISTING CONTENT ON THE TESTING SITE - USE CreateContent.java TO SET UP CONTENT

    // REQUIREMENTS:

        // Lookup with following properties exists:
            // Lookup Type: SECindices
            // Lookup Text: SECindices
            // Lookup Value: CIK:0001326801

    // NOTE: THIS MODULE HAS THE FOLLOWING JIRA ISSUES:

        // WEB-12711 - causes module Feeds/4_2_2/SECFiling.aspx_03, tests element;img_RSSIconTop422;false and element;img_RSSIconBottom422;false
        // to fail (RSS Icon is displayed when it should not be). If the module check fails only on these tests, consider the check to have passed

    private static By pageAdminMenuButton, siteAdminMenuButton, linkToPageMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static PageForModules pageForModules;
    private static SECFiling secFiling;
    private static SECFilingDetails secFilingDetails;
    private static ModuleBase moduleBase;
    private static ModuleBase moduleBaseForDetails;

    private static String sPathToFile, sDataFileJson, sPathToModuleFile, sFileModuleJson;
    private static String sDataFileJsonDetails, sFileModuleJsonDetails;
    private static JSONParser parser;

    private final String PAGE_DATA="pageData", PAGE_NAME="feed_modules", MODULE_DATA="moduleData", MODULE_NAME="sec_filing", MODULE_NAME_FOR_DETAILS="sec_filing_details", MODULE_DATA_FOR_DETAILS="moduleDataForDetails";



    @BeforeTest
    public void setUp() throws Exception {
        pageAdminMenuButton = By.xpath(propUIModulesFeed.getProperty("btnMenu_PageAdmin"));

        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        linkToPageMenuItem= By.xpath(propUISiteAdmin.getProperty("itemMenu_LinkToPageList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        pageForModules = new PageForModules(driver);
        secFiling = new SECFiling(driver);
        secFilingDetails = new SECFilingDetails(driver);

        sPathToFile = System.getProperty("user.dir") + propUIModulesFeed.getProperty("dataPath_Feed");
        sDataFileJson = propUIModulesFeed.getProperty("json_SECFilingData");
        sDataFileJsonDetails = propUIModulesFeed.getProperty("json_SECFilingDetailsData");
        sPathToModuleFile = System.getProperty("user.dir") + propUIModulesFeed.getProperty("dataPath_Feed");
        sFileModuleJson = propUIModulesFeed.getProperty("json_SECFilingProp");
        sFileModuleJsonDetails = propUIModulesFeed.getProperty("json_SECFilingDetailsProp");


        moduleBase = new ModuleBase(driver, sPathToModuleFile, sFileModuleJson);
        moduleBaseForDetails = new ModuleBase(driver, sPathToModuleFile, sFileModuleJsonDetails);

        parser = new JSONParser();

        loginPage.loginUser();

    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        dashboard.openPageFromCommonTasks(pageAdminMenuButton);
    }

    @Test(dataProvider=PAGE_DATA, priority=1, enabled=true)
    public void createSECFilingPage(JSONObject module) throws InterruptedException {
        Assert.assertEquals(pageForModules.savePage(module, MODULE_NAME), WorkflowState.IN_PROGRESS.state(), "New "+MODULE_NAME+" Page didn't save properly");
        Assert.assertEquals(pageForModules.saveAndSubmitPage(module, MODULE_NAME), WorkflowState.FOR_APPROVAL.state(), "Couldn't submit New "+MODULE_NAME+" Page properly");
        Assert.assertEquals(pageForModules.publishPage(MODULE_NAME), WorkflowState.LIVE.state(), "Couldn't publish New "+MODULE_NAME+" Page properly");
    }

    @Test(dataProvider=MODULE_DATA, priority=2, enabled=true)
    public void createSECFilingModule(JSONObject module) throws InterruptedException {
        String sModuleNameSet = module.get("module_title").toString();
        Assert.assertEquals(moduleBase.saveModule(module, MODULE_NAME), WorkflowState.IN_PROGRESS.state(), "New "+sModuleNameSet+" Module didn't save properly");
        Assert.assertEquals(secFiling.saveAndSubmitModule(module, sModuleNameSet), WorkflowState.FOR_APPROVAL.state(), "Couldn't submit New "+sModuleNameSet+" Module properly");
        Assert.assertEquals(moduleBase.publishModule(sModuleNameSet), WorkflowState.LIVE.state(), "Couldn't publish New "+sModuleNameSet+" Module properly");
    }

    @Test(dataProvider=MODULE_DATA, priority=3, enabled=true)
    public void checkSECFilingPreview(JSONObject module) throws InterruptedException {

        try {
            String sModuleNameSet = module.get("module_title").toString();
            Assert.assertTrue(moduleBase.openModulePreview(sModuleNameSet).contains(MODULE_NAME),"Did not open correct page");

            JSONArray expectedResults = (JSONArray) module.get("expected");
            for (Object expected : expectedResults) {
                String sExpected = expected.toString();
                Assert.assertTrue(ModuleFunctions.checkExpectedValue(driver, sExpected, module, sPathToModuleFile + sFileModuleJson, propUIModulesFeed),
                        "Did not find correct " + sExpected.split(";")[0] + " at item " + sExpected.split(";")[1]);
            }
        } finally {
            moduleBase.closeWindow();
        }
    }

    @Test(dataProvider=MODULE_DATA, priority=4, enabled=true)
    public void checkSECFilingLive(JSONObject module) throws InterruptedException {

        try {
            Assert.assertTrue(moduleBase.openModuleLiveSite(MODULE_NAME).contains(MODULE_NAME),"Did not open correct page");

            JSONArray expectedResults = (JSONArray) module.get("expected");
            for (Object expected : expectedResults) {
                String sExpected = expected.toString();
                Assert.assertTrue(ModuleFunctions.checkExpectedValue(driver, sExpected, module, sPathToModuleFile + sFileModuleJson, propUIModulesFeed),
                        "Did not find correct " + sExpected.split(";")[0] + " at item " + sExpected.split(";")[1]);
            }
        } finally {
            moduleBase.closeWindow();
        }
    }

    @Test(dataProvider=PAGE_DATA, priority=5, enabled=true)
    public void createSECFilingDetailsPage(JSONObject module) throws Exception {
        Assert.assertEquals(pageForModules.savePage(module, MODULE_NAME_FOR_DETAILS), WorkflowState.IN_PROGRESS.state(), "New "+MODULE_NAME_FOR_DETAILS+" Page didn't save properly");
        Assert.assertEquals(pageForModules.saveAndSubmitPage(module, MODULE_NAME_FOR_DETAILS), WorkflowState.FOR_APPROVAL.state(), "Couldn't submit New "+MODULE_NAME_FOR_DETAILS+" Page properly");
        Assert.assertEquals(pageForModules.publishPage(MODULE_NAME_FOR_DETAILS), WorkflowState.LIVE.state(), "Couldn't publish New "+MODULE_NAME_FOR_DETAILS+" Page properly");
        dashboard.openPageFromMenu(siteAdminMenuButton, linkToPageMenuItem);
        Assert.assertEquals(moduleBaseForDetails.linkToPageEdit("- sec_filing_details", "LinkToSECFilingDetails"), WorkflowState.LIVE.state(), "Couldn't publish New "+MODULE_NAME_FOR_DETAILS+" Page properly");
    }

    @Test(dataProvider=MODULE_DATA_FOR_DETAILS, priority=6, enabled=true)
    public void createSECFilingDetailsModule(JSONObject module) throws InterruptedException {
        String sModuleNameSet = module.get("module_title").toString();
        Assert.assertEquals(moduleBaseForDetails.saveModule(module, MODULE_NAME_FOR_DETAILS), WorkflowState.IN_PROGRESS.state(), "New "+sModuleNameSet+" Module didn't save properly");
        Assert.assertEquals(secFilingDetails.saveAndSubmitModule(module, sModuleNameSet), WorkflowState.FOR_APPROVAL.state(), "Couldn't submit New "+sModuleNameSet+" Module properly");
        Assert.assertEquals(moduleBaseForDetails.publishModule(sModuleNameSet), WorkflowState.LIVE.state(), "Couldn't publish New "+sModuleNameSet+" Module properly");
    }

    @Test(dataProvider=MODULE_DATA_FOR_DETAILS, priority=7, enabled=true)
    public void checkSECFilingDetailsPreview(JSONObject module) throws InterruptedException {

        try {
            String sModuleNameSet = module.get("module_title").toString();
            String tempName = sModuleNameSet;
            String SECFilingModuleName = tempName.replaceAll("Details", "");
            Assert.assertTrue(secFilingDetails.openModulePreviewForSECFiling(SECFilingModuleName).contains(sModuleNameSet),"Did not open correct page");

            JSONArray expectedResults = (JSONArray) module.get("expected");
            for (Object expected : expectedResults) {
                String sExpected = expected.toString();
                Assert.assertTrue(ModuleFunctions.checkExpectedValue(driver, sExpected, module, sPathToModuleFile + sFileModuleJsonDetails, propUIModulesFeed),
                        "Did not find correct " + sExpected.split(";")[0] + " at item " + sExpected.split(";")[1]);
            }
        } finally {
            moduleBaseForDetails.closeWindow();
        }
    }

    @Test(dataProvider=MODULE_DATA_FOR_DETAILS, priority=8, enabled=true)
    public void checkSECFilingDetailsLive(JSONObject module) throws InterruptedException {

        try {
            String sModuleNameSet = module.get("module_title").toString();
            String tempName = sModuleNameSet;
            // Removing 'Details' from the original title gives us the path to the sec_filing page
            String SECFilingModuleName = tempName.replaceAll("Details", "");
            Assert.assertTrue(secFilingDetails.openModuleLiveSite(SECFilingModuleName).contains(sModuleNameSet),"Did not open correct page");

            JSONArray expectedResults = (JSONArray) module.get("expected");
            for (Object expected : expectedResults) {
                String sExpected = expected.toString();
                Assert.assertTrue(ModuleFunctions.checkExpectedValue(driver, sExpected, module, sPathToModuleFile + sFileModuleJsonDetails, propUIModulesFeed),
                        "Did not find correct " + sExpected.split(";")[0] + " at item " + sExpected.split(";")[1]);
            }
        } finally {
            moduleBaseForDetails.closeWindow();
        }
    }

    @Test(dataProvider=MODULE_DATA_FOR_DETAILS, priority=9, enabled=true)
    public void removeSECFilingDetailsModule(JSONObject module) throws Exception {
        String sModuleNameSet = module.get("module_title").toString();
        Assert.assertEquals(moduleBaseForDetails.setupAsDeletedModule(sModuleNameSet), WorkflowState.DELETE_PENDING.state(), "New "+sModuleNameSet+" Module didn't setup as Deleted properly");
        Assert.assertEquals(moduleBaseForDetails.removeModule(module, sModuleNameSet), WorkflowState.NEW_ITEM.state(), "Couldn't remove "+sModuleNameSet+" Module. Something went wrong.");
    }

    @Test(dataProvider=PAGE_DATA, priority=10, enabled=true)
    public void removeSECFilingDetailsPage(JSONObject module) throws Exception {
        Assert.assertEquals(pageForModules.setupAsDeletedPage(MODULE_NAME_FOR_DETAILS), WorkflowState.DELETE_PENDING.state(), "New "+MODULE_NAME_FOR_DETAILS+" Page didn't setup as Deleted properly");
        Assert.assertEquals(pageForModules.removePage(module, MODULE_NAME_FOR_DETAILS), WorkflowState.NEW_ITEM.state(), "Couldn't remove "+MODULE_NAME_FOR_DETAILS+" Page. Something went wrong.");
    }

    @Test(dataProvider=MODULE_DATA, priority=11, enabled=true)
    public void removeSECFilingModule(JSONObject module) throws Exception {
        String sModuleNameSet = module.get("module_title").toString();
        Assert.assertEquals(moduleBase.setupAsDeletedModule(sModuleNameSet), WorkflowState.DELETE_PENDING.state(), "New "+sModuleNameSet+" Module didn't setup as Deleted properly");
        Assert.assertEquals(moduleBase.removeModule(module, sModuleNameSet), WorkflowState.NEW_ITEM.state(), "Couldn't remove "+sModuleNameSet+" Module. Something went wrong.");
    }

    @Test(dataProvider=PAGE_DATA, priority=12, enabled=true)
    public void removeSECFilingPage(JSONObject module) throws Exception {
        Assert.assertEquals(pageForModules.setupAsDeletedPage(MODULE_NAME), WorkflowState.DELETE_PENDING.state(), "New "+MODULE_NAME+" Page didn't setup as Deleted properly");
        Assert.assertEquals(pageForModules.removePage(module, MODULE_NAME), WorkflowState.NEW_ITEM.state(), "Couldn't remove "+MODULE_NAME+" Page. Something went wrong.");
    }

    public Object[][] genericProvider(String dataType) {
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray pageData = (JSONArray) jsonObject.get(dataType);
            ArrayList<Object> zoom = new ArrayList();

            for (int i = 0; i < pageData.size(); i++) {
                JSONObject pageObj = (JSONObject) pageData.get(i);
                if (Boolean.parseBoolean(pageObj.get("do_assertions").toString())) {
                    zoom.add(pageData.get(i));
                }
            }

            Object[][] newPages = new Object[zoom.size()][1];
            for (int i = 0; i < zoom.size(); i++) {
                newPages[i][0] = zoom.get(i);
            }

            return newPages;

        }  catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Object[][] detailsProvider(String dataType) {
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray pageData = (JSONArray) jsonObject.get(dataType);
            ArrayList<Object> zoom = new ArrayList();

            for (int i = 0; i < pageData.size(); i++) {
                JSONObject pageObj = (JSONObject) pageData.get(i);
                if (Boolean.parseBoolean(pageObj.get("do_assertions").toString())) {
                    zoom.add(pageData.get(i));
                }
            }

            Object[][] newPages = new Object[zoom.size()][1];
            for (int i = 0; i < zoom.size(); i++) {
                newPages[i][0] = zoom.get(i);
            }

            return newPages;

        }  catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    @DataProvider
    public Object[][] moduleData() {
        return genericProvider(MODULE_NAME);
    }

    @DataProvider
    public Object[][] moduleDataForDetails() {
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJsonDetails));
            JSONArray pageData = (JSONArray) jsonObject.get(MODULE_NAME_FOR_DETAILS);
            ArrayList<Object> zoom = new ArrayList();

            for (int i = 0; i < pageData.size(); i++) {
                JSONObject pageObj = (JSONObject) pageData.get(i);
                if (Boolean.parseBoolean(pageObj.get("do_assertions").toString())) {
                    zoom.add(pageData.get(i));
                }
            }

            Object[][] newPages = new Object[zoom.size()][1];
            for (int i = 0; i < zoom.size(); i++) {
                newPages[i][0] = zoom.get(i);
            }

            return newPages;

        }  catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    @DataProvider
    public Object[][] pageData() {
        return genericProvider(PAGE_NAME);
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }
}
