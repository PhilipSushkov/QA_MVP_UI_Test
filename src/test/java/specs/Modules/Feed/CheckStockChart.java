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
import pageobjects.Modules.Feed.StockChart;
import pageobjects.Modules.ModuleBase;
import pageobjects.Modules.PageForModules;
import pageobjects.PageAdmin.WorkflowState;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by zacharyk on 2017-06-21.
 */

public class CheckStockChart extends AbstractSpec {
    private static By pageAdminMenuButton;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static PageForModules pageForModules;
    private static StockChart stockChart;
    private static ModuleBase moduleBase;

    private static String sPathToFile, sDataFileJson, sPathToModuleFile, sFileModuleJson;
    private static JSONParser parser;

    private final String PAGE_DATA="pageData", PAGE_NAME="feed_modules", MODULE_DATA="moduleData", MODULE_NAME="stock_chart";

    @BeforeTest
    public void setUp() throws Exception {
        pageAdminMenuButton = By.xpath(propUIModulesFeed.getProperty("btnMenu_PageAdmin"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        pageForModules = new PageForModules(driver);
        stockChart = new StockChart(driver);

        sPathToFile = System.getProperty("user.dir") + propUIModulesFeed.getProperty("dataPath_Feed");
        sDataFileJson = propUIModulesFeed.getProperty("json_StockChartData");

        sPathToModuleFile = System.getProperty("user.dir") + propUIModulesFeed.getProperty("dataPath_Feed");
        sFileModuleJson = propUIModulesFeed.getProperty("json_StockChartProp");

        moduleBase = new ModuleBase(driver, sPathToModuleFile, sFileModuleJson);

        parser = new JSONParser();

        loginPage.loginUser();
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        dashboard.openPageFromCommonTasks(pageAdminMenuButton);
    }

    @Test(dataProvider=PAGE_DATA, priority=1, enabled=false)
    public void createStockChartPage(JSONObject page) throws InterruptedException {
        Assert.assertEquals(pageForModules.savePage(page, MODULE_NAME), WorkflowState.IN_PROGRESS.state(), "New "+MODULE_NAME+" Page didn't save properly");
        Assert.assertEquals(pageForModules.saveAndSubmitPage(page, MODULE_NAME), WorkflowState.FOR_APPROVAL.state(), "Couldn't submit New "+MODULE_NAME+" Page properly");
        Assert.assertEquals(pageForModules.publishPage(MODULE_NAME), WorkflowState.LIVE.state(), "Couldn't publish New "+MODULE_NAME+" Page properly");
    }

    @Test(dataProvider=MODULE_DATA, priority=2, enabled=true)
    public void createStockChartModule(JSONObject module) throws InterruptedException {
        String sModuleNameSet = module.get("module_title").toString();
        Assert.assertEquals(moduleBase.saveModule(module, MODULE_NAME), WorkflowState.IN_PROGRESS.state(), "New "+sModuleNameSet+" Module didn't save properly");
        Assert.assertEquals(moduleBase.saveAndSubmitModule(module, sModuleNameSet), WorkflowState.FOR_APPROVAL.state(), "Couldn't submit New "+sModuleNameSet+" Module properly");
        Assert.assertEquals(moduleBase.publishModule(sModuleNameSet), WorkflowState.LIVE.state(), "Couldn't publish New "+sModuleNameSet+" Module properly");
    }

    // No properties testing; this module is 3rd-party and is not being provided to new clients

    @Test(dataProvider=MODULE_DATA, priority=5, enabled=true)
    public void removeStockChartModule(JSONObject module) throws Exception {
        String sModuleNameSet = module.get("module_title").toString();
        Assert.assertEquals(moduleBase.setupAsDeletedModule(sModuleNameSet), WorkflowState.DELETE_PENDING.state(), "New "+sModuleNameSet+" Module didn't setup as Deleted properly");
        Assert.assertEquals(moduleBase.removeModule(module, sModuleNameSet), WorkflowState.NEW_ITEM.state(), "Couldn't remove "+sModuleNameSet+" Module. Something went wrong.");
    }

    @Test(dataProvider=PAGE_DATA, priority=6, enabled=true)
    public void removeStockChartPage(JSONObject page) throws Exception {
        Assert.assertEquals(pageForModules.setupAsDeletedPage(MODULE_NAME), WorkflowState.DELETE_PENDING.state(), "New "+MODULE_NAME+" Page didn't setup as Deleted properly");
        Assert.assertEquals(pageForModules.removePage(page, MODULE_NAME), WorkflowState.NEW_ITEM.state(), "Couldn't remove "+MODULE_NAME+" Page. Something went wrong.");
    }

    @DataProvider
    public Object[][] moduleData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray moduleData = (JSONArray) jsonObject.get(MODULE_NAME);
            ArrayList<Object> zoom = new ArrayList();

            for (int i = 0; i < moduleData.size(); i++) {
                JSONObject moduleObj = (JSONObject) moduleData.get(i);
                if (Boolean.parseBoolean(moduleObj.get("do_assertions").toString())) {
                    zoom.add(moduleData.get(i));
                }
            }

            Object[][] newModules = new Object[zoom.size()][1];
            for (int i = 0; i < zoom.size(); i++) {
                newModules[i][0] = zoom.get(i);
            }

            return newModules;

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

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray pageData = (JSONArray) jsonObject.get(PAGE_NAME);
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
