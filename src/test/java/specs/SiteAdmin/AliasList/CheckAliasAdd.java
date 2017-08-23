package specs.SiteAdmin.AliasList;

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
import pageobjects.SiteAdmin.AliasList.AliasAdd;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by andyp on 2017-08-22.
 */
public class CheckAliasAdd extends AbstractSpec {
    private static By siteAdminMenuButton, aliasListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static AliasAdd aliasAdd;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String DATA="getData", ALIAS_NAME="name", PAGE_NAME="Alias";

    @BeforeTest
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        aliasListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_AliasList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        aliasAdd = new AliasAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUISiteAdmin.getProperty("dataPath_AliasList");
        sDataFileJson = propUISiteAdmin.getProperty("json_AliasListData");

        parser = new JSONParser();

        loginPage.loginUser();
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        dashboard.openPageFromMenu(siteAdminMenuButton, aliasListMenuItem);
    }

    @Test(dataProvider=DATA, priority=1, enabled = true)
    public void saveAlias(JSONObject data) {
        String sAliasName = data.get(ALIAS_NAME).toString();
        String expectedTitleEdit = "Alias Edit";

        Assert.assertEquals(aliasAdd.getTitle(), expectedTitleEdit, "Actual "+PAGE_NAME+" Edit page Title doesn't match to expected");
        Assert.assertEquals(aliasAdd.saveAlias(data, sAliasName), WorkflowState.IN_PROGRESS.state(), "New "+PAGE_NAME+" didn't save properly");
    }

    @Test(dataProvider=DATA, priority=2, enabled = true)
    public void saveAndSubmitAlias(JSONObject data) throws InterruptedException {
        String sAliasName = data.get(ALIAS_NAME).toString();

        Assert.assertEquals(aliasAdd.saveAndSubmitAlias(data, sAliasName), WorkflowState.FOR_APPROVAL.state(), "New " + PAGE_NAME + " doesn't submit properly (after Save And Submit)");
        Assert.assertTrue(aliasAdd.checkAlias(data, sAliasName), "Submitted New "+ PAGE_NAME +" data doesn't fit well to entry data (after Save and Submit)");
    }

    @Test(dataProvider=DATA, priority=3, enabled = true)
    public void publishAlias(JSONObject data) throws InterruptedException {
        String sAliasName = data.get(ALIAS_NAME).toString();
        Assert.assertEquals(aliasAdd.publishAlias(data, sAliasName), WorkflowState.LIVE.state(), "New "+ PAGE_NAME +" doesn't publish properly (after Publish)");
    }

    @Test(dataProvider=DATA, priority=4, enabled = true)
    public void revertAlias(JSONObject data) throws InterruptedException {
        String sAliasName = data.get(ALIAS_NAME).toString();

        Assert.assertEquals(aliasAdd.changeAndSubmitAlias(data, sAliasName), WorkflowState.FOR_APPROVAL.state(), "Some fields of New "+ PAGE_NAME +" didn't change properly (after Save and Submit)");
        Assert.assertEquals(aliasAdd.revertToLiveAlias(sAliasName), WorkflowState.LIVE.state(), "Couldn't revert to Live changes for New "+ PAGE_NAME);
        Assert.assertTrue(aliasAdd.checkAlias(data, sAliasName), "Submitted New "+ PAGE_NAME +" data doesn't fit well to entry data (after Revert To Live)");
    }

    @Test(dataProvider=DATA, priority=5, enabled = true)
    public void changeAndSubmitAlias(JSONObject data) throws Exception {
        String sAliasName = data.get(ALIAS_NAME).toString();

        Assert.assertEquals(aliasAdd.changeAndSubmitAlias(data, sAliasName), WorkflowState.FOR_APPROVAL.state(), "Some fields of New "+ PAGE_NAME +" didn't change properly (after Save and Submit)");
        Assert.assertTrue(aliasAdd.checkAliasCh(data, sAliasName), "Submitted New "+ PAGE_NAME +" changes don't fit well to change data (after Change And Submit)");
    }
    //
    @Test(dataProvider=DATA, priority=6, enabled = true)
    public void publishEditAlias(JSONObject data) throws InterruptedException {
        String sAliasName = data.get(ALIAS_NAME).toString();
        Assert.assertEquals(aliasAdd.publishAlias(data, sAliasName), WorkflowState.LIVE.state(), "New "+ PAGE_NAME +" doesn't publish properly (after Publish)");
    }

    @Test(dataProvider=DATA, priority=7, enabled = true)
    public void deleteAlias(JSONObject data) throws Exception {
        String sAliasName = data.get(ALIAS_NAME).toString();
        Assert.assertEquals(aliasAdd.setupAsDeletedAlias(sAliasName), WorkflowState.DELETE_PENDING.state(), "New "+ PAGE_NAME +" didn't setup as Deleted properly");
    }

    @Test(dataProvider=DATA, priority=8, enabled = true)
    public void removeAlias(JSONObject data) throws Exception {
        String sAliasName = data.get(ALIAS_NAME).toString();
        Assert.assertEquals(aliasAdd.removeAlias(data, sAliasName), WorkflowState.NEW_ITEM.state(), "Couldn't remove New "+ PAGE_NAME +". Something went wrong.");
    }

    @DataProvider
    public Object[][] getData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray jsonArray = (JSONArray) jsonObject.get("alias_list");
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
