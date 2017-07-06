package specs.Modules.Feed;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.*;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Modules.Feed.StockHistorical2_375;
import pageobjects.Modules.PageForModules;
import pageobjects.PageAdmin.WorkflowState;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by philipsushkov on 2017-06-12.
 */

public class CheckStockHistorical2_375 extends AbstractSpec {
    private static By pageAdminMenuButton;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static PageForModules pageForModules;
    private static StockHistorical2_375 stockHistorical2_375;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String MODULE_DATA="moduleData", MODULE_NAME="stock_historical2_375";

    @BeforeTest
    public void setUp() throws Exception {
        pageAdminMenuButton = By.xpath(propUIModulesFeed.getProperty("btnMenu_PageAdmin"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        pageForModules = new PageForModules(driver);
        stockHistorical2_375 = new StockHistorical2_375(driver);

        sPathToFile = System.getProperty("user.dir") + propUIModulesFeed.getProperty("dataPath_Feed");
        sDataFileJson = propUIModulesFeed.getProperty("json_StockHistorical2_375Data");

        parser = new JSONParser();

        loginPage.loginUser();
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        dashboard.openPageFromCommonTasks(pageAdminMenuButton);
    }

    @Test(dataProvider=MODULE_DATA, priority=1, enabled=false)
    public void createStockHistorical2_375Page(JSONObject module) throws InterruptedException {
        Assert.assertEquals(pageForModules.savePage(module, MODULE_NAME), WorkflowState.IN_PROGRESS.state(), "New "+MODULE_NAME+" Page didn't save properly");
        Assert.assertEquals(pageForModules.saveAndSubmitPage(module, MODULE_NAME), WorkflowState.FOR_APPROVAL.state(), "Couldn't submit New "+MODULE_NAME+" Page properly");
        Assert.assertEquals(pageForModules.publishPage(MODULE_NAME), WorkflowState.LIVE.state(), "Couldn't publish New "+MODULE_NAME+" Page properly");
    }

    @Test(dataProvider=MODULE_DATA, priority=2, enabled=false)
    public void createStockHistorical2_375Module(JSONObject module) throws InterruptedException {
        String sModuleNameSet = module.get("module_title").toString();
        Assert.assertEquals(stockHistorical2_375.saveModule(module, MODULE_NAME), WorkflowState.IN_PROGRESS.state(), "New "+sModuleNameSet+" Module didn't save properly");
        Assert.assertEquals(stockHistorical2_375.saveAndSubmitModule(module, sModuleNameSet), WorkflowState.FOR_APPROVAL.state(), "Couldn't submit New "+sModuleNameSet+" Module properly");
        Assert.assertEquals(stockHistorical2_375.publishModule(sModuleNameSet), WorkflowState.LIVE.state(), "Couldn't publish New "+sModuleNameSet+" Module properly");
    }

    @Test(dataProvider=MODULE_DATA, priority=3, enabled=true)
    public void removeStockHistorical2_375Module(JSONObject module) throws Exception {
        String sModuleNameSet = module.get("module_title").toString();
        Assert.assertEquals(stockHistorical2_375.setupAsDeletedModule(sModuleNameSet), WorkflowState.DELETE_PENDING.state(), "New "+sModuleNameSet+" Module didn't setup as Deleted properly");
        Assert.assertEquals(stockHistorical2_375.removeModule(module, sModuleNameSet), WorkflowState.NEW_ITEM.state(), "Couldn't remove "+sModuleNameSet+" Module. Something went wrong.");
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
