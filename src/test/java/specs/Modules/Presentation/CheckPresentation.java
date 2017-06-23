package specs.Modules.Presentation;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Modules.Presentation.Presentation;
import pageobjects.Modules.PageForModules;
import pageobjects.PageAdmin.WorkflowState;
import specs.AbstractSpec;
import specs.Modules.util.ModuleFunctions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by zacharyk on 2017-06-22.
 */
public class CheckPresentation extends AbstractSpec {

    // This test is heavily dependent on pre-existing content on the testing site

    private static By pageAdminMenuButton;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static PageForModules pageForModules;
    private static Presentation presentation;

    private static String sPathToFile, sDataFileJson, sPathToModuleFile, sFileModuleJson;
    private static JSONParser parser;

    private final String MODULE_DATA="moduleData", MODULE_NAME="presentation";



    @BeforeTest
    public void setUp() throws Exception {
        pageAdminMenuButton = By.xpath(propUIModulesPresentation.getProperty("btnMenu_PageAdmin"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        pageForModules = new PageForModules(driver);
        presentation = new Presentation(driver);

        sPathToFile = System.getProperty("user.dir") + propUIModulesPresentation.getProperty("dataPath_Presentation");
        sDataFileJson = propUIModulesPresentation.getProperty("json_PresentationData");
        sPathToModuleFile = System.getProperty("user.dir") + propUIModulesPresentation.getProperty("dataPath_Presentation");
        sFileModuleJson = propUIModulesPresentation.getProperty("json_PresentationProp");

        parser = new JSONParser();

        loginPage.loginUser();

    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        dashboard.openPageFromCommonTasks(pageAdminMenuButton);
    }

    @Test(dataProvider=MODULE_DATA, priority=1, enabled=false)
    public void createPresentationPage(JSONObject module) throws InterruptedException {
        Assert.assertEquals(pageForModules.savePage(module, MODULE_NAME), WorkflowState.IN_PROGRESS.state(), "New "+MODULE_NAME+" Page didn't save properly");
        Assert.assertEquals(pageForModules.saveAndSubmitPage(module, MODULE_NAME), WorkflowState.FOR_APPROVAL.state(), "Couldn't submit New "+MODULE_NAME+" Page properly");
        Assert.assertEquals(pageForModules.publishPage(MODULE_NAME), WorkflowState.LIVE.state(), "Couldn't publish New "+MODULE_NAME+" Page properly");
    }

    @Test(dataProvider=MODULE_DATA, priority=2, enabled=false)
    public void createPresentationModule(JSONObject module) throws InterruptedException {
        String sModuleNameSet = module.get("module_title").toString();
        Assert.assertEquals(presentation.saveModule(module, MODULE_NAME), WorkflowState.IN_PROGRESS.state(), "New "+sModuleNameSet+" Module didn't save properly");
        Assert.assertEquals(presentation.saveAndSubmitModule(module, sModuleNameSet), WorkflowState.FOR_APPROVAL.state(), "Couldn't submit New "+sModuleNameSet+" Module properly");
        Assert.assertEquals(presentation.publishModule(sModuleNameSet), WorkflowState.LIVE.state(), "Couldn't publish New "+sModuleNameSet+" Module properly");
    }

    @Test(dataProvider=MODULE_DATA, priority=3, enabled=true)
    public void checkPresentationPreview(JSONObject module) throws InterruptedException {

        try {
            String sModuleNameSet = module.get("module_title").toString();
            Assert.assertTrue(presentation.openModulePreview(sModuleNameSet).contains(MODULE_NAME),"Did not open correct page");

            JSONArray expectedResults = (JSONArray) module.get("expected");
            for (Object expected : expectedResults) {
                String sExpected = expected.toString();
                Assert.assertTrue(ModuleFunctions.checkExpectedValue(driver, sExpected, module, sPathToModuleFile + sFileModuleJson, propUIModulesPresentation),
                        "Did not find correct " + sExpected.split(";")[0] + " at item " + sExpected.split(";")[1]);
            }
        } finally {
            presentation.closeWindow();
        }
    }

    @Test(dataProvider=MODULE_DATA, priority=4, enabled=true)
    public void checkPresentationLive(JSONObject module) throws InterruptedException {

        try {
            Assert.assertTrue(presentation.openModuleLiveSite(MODULE_NAME).contains(MODULE_NAME),"Did not open correct page");

            JSONArray expectedResults = (JSONArray) module.get("expected");
            for (Object expected : expectedResults) {
                String sExpected = expected.toString();
                Assert.assertTrue(ModuleFunctions.checkExpectedValue(driver, sExpected, module, sPathToModuleFile + sFileModuleJson, propUIModulesPresentation),
                        "Did not find correct " + sExpected.split(";")[0] + " at item " + sExpected.split(";")[1]);
            }
        } finally {
            presentation.closeWindow();
        }
    }

    @Test(dataProvider=MODULE_DATA, priority=5, enabled=false)
    public void removePresentationModule(JSONObject module) throws Exception {
        String sModuleNameSet = module.get("module_title").toString();
        Assert.assertEquals(presentation.setupAsDeletedModule(sModuleNameSet), WorkflowState.DELETE_PENDING.state(), "New "+sModuleNameSet+" Module didn't setup as Deleted properly");
        Assert.assertEquals(presentation.removeModule(module, sModuleNameSet), WorkflowState.NEW_ITEM.state(), "Couldn't remove "+sModuleNameSet+" Module. Something went wrong.");
    }

    @Test(dataProvider=MODULE_DATA, priority=6, enabled=false)
    public void removePresentationPage(JSONObject module) throws Exception {
        Assert.assertEquals(pageForModules.setupAsDeletedPage(MODULE_NAME), WorkflowState.DELETE_PENDING.state(), "New "+MODULE_NAME+" Page didn't setup as Deleted properly");
        Assert.assertEquals(pageForModules.removePage(module, MODULE_NAME), WorkflowState.NEW_ITEM.state(), "Couldn't remove "+MODULE_NAME+" Page. Something went wrong.");
    }

    @DataProvider
    public Object[][] moduleData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray pageData = (JSONArray) jsonObject.get(MODULE_NAME);
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

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }
}
