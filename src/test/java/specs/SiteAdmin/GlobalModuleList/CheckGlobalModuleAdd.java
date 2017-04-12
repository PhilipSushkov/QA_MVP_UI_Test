package specs.SiteAdmin.GlobalModuleList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.PageAdmin.WorkflowState;
import pageobjects.SiteAdmin.GlobalModuleList.GlobalModuleAdd;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by philipsushkov on 2017-04-11.
 */

public class CheckGlobalModuleAdd extends AbstractSpec {
    private static By siteAdminMenuButton, globalModuleListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static GlobalModuleAdd globalModuleAdd;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String DATA="getData", GLOBAL_MODULE_NAME="module_title", PAGE_NAME="Global Module";

    @BeforeTest
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        globalModuleListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_GlobalModuleList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        globalModuleAdd = new GlobalModuleAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUISiteAdmin.getProperty("dataPath_GlobalModuleList");
        sDataFileJson = propUISiteAdmin.getProperty("json_GlobalModuleData");

        parser = new JSONParser();

        loginPage.loginUser();
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        dashboard.openPageFromMenu(siteAdminMenuButton, globalModuleListMenuItem);
    }

    @Test(dataProvider=DATA, priority=1)
    public void saveGlobalModule(JSONObject data) {
        String sGlobalModuleName = data.get(GLOBAL_MODULE_NAME).toString();
        String expectedTitleEdit = "Global Module Edit";

        Assert.assertEquals(globalModuleAdd.getTitle(), expectedTitleEdit, "Actual "+PAGE_NAME+" Edit page Title doesn't match to expected");
        Assert.assertEquals(globalModuleAdd.saveGlobalModule(data, sGlobalModuleName), WorkflowState.IN_PROGRESS.state(), "New "+PAGE_NAME+" didn't save properly");
    }

    @Test(dataProvider=DATA, priority=2)
    public void saveAndSubmitGlobalModule(JSONObject data) throws InterruptedException {
        String sGlobalModuleName = data.get(GLOBAL_MODULE_NAME).toString();

        Assert.assertEquals(globalModuleAdd.saveAndSubmitGlobalModule(data, sGlobalModuleName), WorkflowState.FOR_APPROVAL.state(), "New " + PAGE_NAME + " doesn't submit properly (after Save And Submit)");
        Assert.assertTrue(globalModuleAdd.checkGlobalModule(data, sGlobalModuleName), "Submitted New "+ PAGE_NAME +" data doesn't fit well to entry data (after Save and Submit)");
    }

    @Test(dataProvider=DATA, priority=3)
    public void publishGlobalModule(JSONObject data) throws InterruptedException {
        String sGlobalModuleName = data.get(GLOBAL_MODULE_NAME).toString();
        Assert.assertEquals(globalModuleAdd.publishGlobalModule(data, sGlobalModuleName), WorkflowState.LIVE.state(), "New "+ PAGE_NAME +" doesn't publish properly (after Publish)");
    }

    @Test(dataProvider=DATA, priority=4)
    public void revertGlobalModule(JSONObject data) throws InterruptedException {
        String sGlobalModuleName = data.get(GLOBAL_MODULE_NAME).toString();

        Assert.assertEquals(globalModuleAdd.changeAndSubmitGlobalModule(data, sGlobalModuleName), WorkflowState.FOR_APPROVAL.state(), "Some fields of New "+ PAGE_NAME +" didn't change properly (after Save and Submit)");
        Assert.assertEquals(globalModuleAdd.revertToLiveGlobalModule(sGlobalModuleName), WorkflowState.LIVE.state(), "Couldn't revert to Live changes for New "+ PAGE_NAME);
        Assert.assertTrue(globalModuleAdd.checkGlobalModule(data, sGlobalModuleName), "Submitted New "+ PAGE_NAME +" data doesn't fit well to entry data (after Revert To Live)");
    }

    @DataProvider
    public Object[][] getData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray jsonArray = (JSONArray) jsonObject.get("global_module");
            ArrayList<Object> zoom = new ArrayList();

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject pageObj = (JSONObject) jsonArray.get(i);
                if (Boolean.parseBoolean(pageObj.get("do_assertions").toString())) {
                    zoom.add(jsonArray.get(i));
                }
            }

            Object[][] data = new Object[zoom.size()][1];
            for (int i = 0; i < zoom.size(); i++) {
                data[i][0] = zoom.get(i);
            }

            return data;

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
