package specs.SiteAdmin.ModuleDefinitionList;

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
import pageobjects.SiteAdmin.ModuleDefinitionList.ModuleDefinitionAdd;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by philipsushkov on 2017-04-25.
 */

public class CheckModuleDefinitionAdd extends AbstractSpec {
    private static By siteAdminMenuButton, moduleDefinitionListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static ModuleDefinitionAdd moduleDefinitionAdd;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String DATA="getData", MODULE_DEFINITION_NAME="friendly_name", PAGE_NAME="Module Definition";

    @BeforeTest
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        moduleDefinitionListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_ModuleDefinitionList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        moduleDefinitionAdd = new ModuleDefinitionAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUISiteAdmin.getProperty("dataPath_ModuleDefinitionList");
        sDataFileJson = propUISiteAdmin.getProperty("json_ModuleDefinitionData");

        parser = new JSONParser();

        loginPage.loginUser();
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        dashboard.openPageFromMenu(siteAdminMenuButton, moduleDefinitionListMenuItem);
    }

    @Test(dataProvider=DATA, priority=1)
    public void saveModuleDefinition(JSONObject data) {
        String sModuleDefinitionName = data.get(MODULE_DEFINITION_NAME).toString();
        String expectedTitleEdit = "Module Definition Edit";

        Assert.assertEquals(moduleDefinitionAdd.getTitle(), expectedTitleEdit, "Actual "+PAGE_NAME+" Edit page Title doesn't match to expected");
        Assert.assertEquals(moduleDefinitionAdd.saveModuleDefinition(data, sModuleDefinitionName), WorkflowState.IN_PROGRESS.state(), "New "+PAGE_NAME+" didn't save properly");
    }

    @Test(dataProvider=DATA, priority=2)
    public void saveAndSubmitModuleDefinition(JSONObject data) throws InterruptedException {
        String sModuleDefinitionName = data.get(MODULE_DEFINITION_NAME).toString();

        Assert.assertEquals(moduleDefinitionAdd.saveAndSubmitModuleDefinition(data, sModuleDefinitionName), WorkflowState.FOR_APPROVAL.state(), "New " + PAGE_NAME + " doesn't submit properly (after Save And Submit)");
        Assert.assertTrue(moduleDefinitionAdd.checkModuleDefinition(data, sModuleDefinitionName), "Submitted New "+ PAGE_NAME +" data doesn't fit well to entry data (after Save and Submit)");
    }

    @Test(dataProvider=DATA, priority=3)
    public void publishModuleDefinition(JSONObject data) throws InterruptedException {
        String sModuleDefinitionName = data.get(MODULE_DEFINITION_NAME).toString();
        Assert.assertEquals(moduleDefinitionAdd.publishModuleDefinition(data, sModuleDefinitionName), WorkflowState.LIVE.state(), "New "+ PAGE_NAME +" doesn't publish properly (after Publish)");
    }

    @Test(dataProvider=DATA, priority=4)
    public void revertModuleDefinition(JSONObject data) throws InterruptedException {
        String sModuleDefinitionName = data.get(MODULE_DEFINITION_NAME).toString();

        Assert.assertEquals(moduleDefinitionAdd.changeAndSubmitModuleDefinition(data, sModuleDefinitionName), WorkflowState.FOR_APPROVAL.state(), "Some fields of New "+ PAGE_NAME +" didn't change properly (after Save and Submit)");
        Assert.assertEquals(moduleDefinitionAdd.revertToLiveModuleDefinition(data, sModuleDefinitionName), WorkflowState.LIVE.state(), "Couldn't revert to Live changes for New "+ PAGE_NAME);
        Assert.assertTrue(moduleDefinitionAdd.checkModuleDefinition(data, sModuleDefinitionName), "Submitted New "+ PAGE_NAME +" data doesn't fit well to entry data (after Revert To Live)");
    }

    @Test(dataProvider=DATA, priority=5)
    public void changeAndSubmitEditModuleDefinition(JSONObject data) throws Exception {
        String sModuleDefinitionName = data.get(MODULE_DEFINITION_NAME).toString();

        Assert.assertEquals(moduleDefinitionAdd.changeAndSubmitModuleDefinition(data, sModuleDefinitionName), WorkflowState.FOR_APPROVAL.state(), "Some fields of New "+ PAGE_NAME +" didn't change properly (after Save and Submit)");
        Assert.assertTrue(moduleDefinitionAdd.checkModuleDefinitionCh(data, sModuleDefinitionName), "Submitted New "+ PAGE_NAME +" changes don't fit well to change data (after Change And Submit)");
    }

    @Test(dataProvider=DATA, priority=6)
    public void publishEditModuleDefinition(JSONObject data) throws InterruptedException {
        String sModuleDefinitionName = data.get(MODULE_DEFINITION_NAME).toString();
        Assert.assertEquals(moduleDefinitionAdd.publishModuleDefinition(data, sModuleDefinitionName), WorkflowState.LIVE.state(), "New "+ PAGE_NAME +" doesn't publish properly (after Publish)");
    }

    @Test(dataProvider=DATA, priority=7)
    public void deleteModuleDefinition(JSONObject data) throws Exception {
        String sModuleDefinitionName = data.get(MODULE_DEFINITION_NAME).toString();
        Assert.assertEquals(moduleDefinitionAdd.setupAsDeletedModuleDefinition(sModuleDefinitionName), WorkflowState.DELETE_PENDING.state(), "New "+ PAGE_NAME +" didn't setup as Deleted properly");
    }

    @Test(dataProvider=DATA, priority=8)
    public void removeModuleDefinition(JSONObject data) throws Exception {
        String sModuleDefinitionName = data.get(MODULE_DEFINITION_NAME).toString();
        Assert.assertEquals(moduleDefinitionAdd.removeModuleDefinition(data, sModuleDefinitionName), WorkflowState.NEW_ITEM.state(), "Couldn't remove New "+ PAGE_NAME +". Something went wrong.");
    }

    @DataProvider
    public Object[][] getData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray jsonArray = (JSONArray) jsonObject.get("module_definition");
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
    }

}
