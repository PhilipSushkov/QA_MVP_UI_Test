package specs.Modules.Search;

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
import pageobjects.Modules.Search.Search;
import pageobjects.Modules.Search.SearchResults;
import pageobjects.PageAdmin.WorkflowState;
import specs.AbstractSpec;
import specs.Modules.util.ModuleFunctions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by andyp on 2017-07-18.
 */
public class CheckSearchResults extends AbstractSpec {
    /*
This test requires an image file to be in the file explorer. As of right now, there is no way to do this automatically.
QAQ-502 is the related ticket for the file explorer automation

NOTE: THIS TEST DEPENDS ON PRE-EXISTING CONTENT ON THE TESTING SITE - USE CreateContent.java TO SET UP CONTENT

 */
    private static By pageAdminMenuButton, siteAdminMenuButton, linkToPageMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static PageForModules pageForModules;
    private static SearchResults searchResults;
    private static ModuleBase moduleBase;

    private static String sPathToFile, sDataFileJson, sPathToModuleFile, sFileModuleJson;
    private static JSONParser parser;

    private final String MODULE_DATA="moduleData", MODULE_NAME="search_results", PAGE_DATA = "pageData", PAGE_NAME = "search_modules", LINK_TO_PAGE = "- search_results", KEY_NAME = "LinkToSearchResults";



    @BeforeTest
    public void setUp() throws Exception {
        pageAdminMenuButton = By.xpath(propUIModulesSearch.getProperty("btnMenu_PageAdmin"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        pageForModules = new PageForModules(driver);
        searchResults = new SearchResults(driver);

        sPathToFile = System.getProperty("user.dir") + propUIModulesSearch.getProperty("dataPath_Search");
        sDataFileJson = propUIModulesSearch.getProperty("json_SearchResultsData");
        sPathToModuleFile = System.getProperty("user.dir") + propUIModulesSearch.getProperty("dataPath_Search");
        sFileModuleJson = propUIModulesSearch.getProperty("json_SearchResultsProp");

        moduleBase = new ModuleBase(driver, sPathToModuleFile, sFileModuleJson);

        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        linkToPageMenuItem= By.xpath(propUISiteAdmin.getProperty("itemMenu_LinkToPageList"));

        parser = new JSONParser();

        loginPage.loginUser();

    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        dashboard.openPageFromCommonTasks(pageAdminMenuButton);
    }

    @Test(dataProvider=PAGE_DATA, priority=1, enabled=true)
    public void createSearchResultsPage(JSONObject module) throws Exception {
        Assert.assertEquals(pageForModules.savePage(module, MODULE_NAME), WorkflowState.IN_PROGRESS.state(), "New "+MODULE_NAME+" Page didn't save properly");
        Assert.assertEquals(pageForModules.saveAndSubmitPage(module, MODULE_NAME), WorkflowState.FOR_APPROVAL.state(), "Couldn't submit New "+MODULE_NAME+" Page properly");
        Assert.assertEquals(pageForModules.publishPage(MODULE_NAME), WorkflowState.LIVE.state(), "Couldn't publish New "+MODULE_NAME+" Page properly");
        dashboard.openPageFromMenu(siteAdminMenuButton, linkToPageMenuItem);
        Assert.assertEquals(moduleBase.linkToPageEdit(LINK_TO_PAGE, KEY_NAME), WorkflowState.LIVE.state(), "Couldn't link new "+MODULE_NAME+" properly");
    }

    @Test(dataProvider=MODULE_DATA, priority=2, enabled=true)
    public void createSearchResultsModule(JSONObject module) throws InterruptedException {
        String sModuleNameSet = module.get("module_title").toString();
        Assert.assertEquals(moduleBase.saveModule(module, MODULE_NAME), WorkflowState.IN_PROGRESS.state(), "New "+sModuleNameSet+" Module didn't save properly");
        Assert.assertEquals(searchResults.saveAndSubmitModule(module, sModuleNameSet), WorkflowState.FOR_APPROVAL.state(), "Couldn't submit New "+sModuleNameSet+" Module properly");
        Assert.assertEquals(moduleBase.publishModule(sModuleNameSet), WorkflowState.LIVE.state(), "Couldn't publish New "+sModuleNameSet+" Module properly");
    }

    @Test(dataProvider=MODULE_DATA, priority=3, enabled=true)
    public void checkProperties(JSONObject module) throws InterruptedException {
        // Checks that all input properties were saved correctly
        Assert.assertEquals(searchResults.goToModuleEditPage(module.get("module_title").toString()), WorkflowState.LIVE.state());
        JSONArray JSONArrProp = (JSONArray) module.get("properties");
        for (Object property : JSONArrProp) {
            String sProperty = property.toString();
            By propertyTextValue = By.xpath("//td[contains(@class, 'DataGridItemBorderLeft')][(text()='"+sProperty.split(";")[0]+"')]/parent::tr/td/div/input[contains(@id, 'txtStatic')]");
            By propertySelectValue = By.xpath("//td[contains(@class, 'DataGridItemBorderLeft')][(text()='" + sProperty.split(";")[0] + "')]/parent::tr/td/div/select[contains(@id, 'ddlDynamic')]/option[@selected]");
            if (!driver.findElements(propertyTextValue).isEmpty()) {
                Assert.assertEquals(driver.findElement(propertyTextValue).getAttribute("value"), sProperty.split(";")[1],
                        sProperty.split(";")[0] + " property did not save correctly");
            } else if (!driver.findElements(propertySelectValue).isEmpty()) {
                Assert.assertEquals(driver.findElement(propertySelectValue).getText(), sProperty.split(";")[1],
                        sProperty.split(";")[0] + " property did not save correctly");
            }
        }
    }

    @Test(dataProvider=MODULE_DATA, priority=4, enabled=true)
    public void checkSearchResultsPreview(JSONObject module) throws InterruptedException {

        try {
            String sModuleNameSet = module.get("module_title").toString();
            Assert.assertTrue(moduleBase.openModulePreview(sModuleNameSet).contains(MODULE_NAME),"Did not open correct page");

            if (module.get("check_search").toString().equals("true")){
                searchResults.searchItem("Selenium Test Press Release");
            }

            JSONArray expectedResults = (JSONArray) module.get("expected");
            for (Object expected : expectedResults) {
                String sExpected = expected.toString();
                Assert.assertTrue(ModuleFunctions.checkExpectedValue(driver, sExpected, module, sPathToModuleFile + sFileModuleJson, propUIModulesSearch),
                        "Did not find correct " + sExpected.split(";")[0] + " at item " + sExpected.split(";")[1]);
            }
        } finally {
            moduleBase.closeWindow();
        }
    }

    @Test(dataProvider=MODULE_DATA, priority=5, enabled=true)
    public void checkSearchResultsLive(JSONObject module) throws InterruptedException {

        try {
            Assert.assertTrue(moduleBase.openModuleLiveSite(MODULE_NAME).contains(MODULE_NAME),"Did not open correct page");

            if (module.get("check_search").toString().equals("true")){
                searchResults.searchItem("Selenium Test Press Release");
            }

            JSONArray expectedResults = (JSONArray) module.get("expected");
            for (Object expected : expectedResults) {
                String sExpected = expected.toString();
                Assert.assertTrue(ModuleFunctions.checkExpectedValue(driver, sExpected, module, sPathToModuleFile + sFileModuleJson, propUIModulesSearch),
                        "Did not find correct " + sExpected.split(";")[0] + " at item " + sExpected.split(";")[1]);
            }
        } finally {
            moduleBase.closeWindow();
        }
    }

    @Test(dataProvider=MODULE_DATA, priority=6, enabled=true)
    public void removeSearchResultsModule(JSONObject module) throws Exception {
        String sModuleNameSet = module.get("module_title").toString();
        Assert.assertEquals(moduleBase.setupAsDeletedModule(sModuleNameSet), WorkflowState.DELETE_PENDING.state(), "New "+sModuleNameSet+" Module didn't setup as Deleted properly");
        Assert.assertEquals(moduleBase.removeModule(module, sModuleNameSet), WorkflowState.NEW_ITEM.state(), "Couldn't remove "+sModuleNameSet+" Module. Something went wrong.");
    }

    @Test(dataProvider=PAGE_DATA, priority=7, enabled=true)
    public void removeSearchResultsPage(JSONObject module) throws Exception {
        Assert.assertEquals(pageForModules.setupAsDeletedPage(MODULE_NAME), WorkflowState.DELETE_PENDING.state(), "New "+MODULE_NAME+" Page didn't setup as Deleted properly");
        Assert.assertEquals(pageForModules.removePage(module, MODULE_NAME), WorkflowState.NEW_ITEM.state(), "Couldn't remove "+MODULE_NAME+" Page. Something went wrong.");
    }

    private Object[][] genericProvider(String dataType) {
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
    public Object[][] pageData() {
        return genericProvider(PAGE_NAME);
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }
}
