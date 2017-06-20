package specs.ContentAdmin.QuickLinkList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.ContentAdmin.Glossary.GlossaryAdd;
import pageobjects.ContentAdmin.QuickLinkList.QuickLinkAdd;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.PageAdmin.WorkflowState;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by andyp on 2017-06-19.
 */
public class CheckQuickLinkAdd extends AbstractSpec {
    private static By contentAdminMenuButton, quickLinkListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static QuickLinkAdd quickLinkAdd;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String DATA="getData", QUICKLINK_DESCRIPTION="description", PAGE_NAME="Quick Links";

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        quickLinkListMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_QuickLinks"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        quickLinkAdd = new QuickLinkAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUIContentAdmin.getProperty("dataPath_quickLinkListData");
        sDataFileJson = propUIContentAdmin.getProperty("json_quickLinkListData");

        parser = new JSONParser();

        loginPage.loginUser();
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        dashboard.openPageFromMenu(contentAdminMenuButton, quickLinkListMenuItem);
    }

    @Test(dataProvider=DATA, priority=1)
    public void saveQuickLink(JSONObject data) {
        String sQuickLinkName = data.get(QUICKLINK_DESCRIPTION).toString();
        String expectedTitleEdit = "Quick Link Edit";

        Assert.assertEquals(quickLinkAdd.getTitle(), expectedTitleEdit, "Actual "+PAGE_NAME+" Edit page Title doesn't match to expected");
        Assert.assertEquals(quickLinkAdd.saveQuickLink(data, sQuickLinkName), WorkflowState.IN_PROGRESS.state(), "New "+PAGE_NAME+" didn't save properly");
    }

    @Test(dataProvider=DATA, priority=2)
    public void saveAndSubmitQuickLink(JSONObject data) throws InterruptedException {
        String sQuickLinkName = data.get(QUICKLINK_DESCRIPTION).toString();

        Assert.assertEquals(quickLinkAdd.saveAndSubmitQuickLink(data, sQuickLinkName), WorkflowState.FOR_APPROVAL.state(), "New " + PAGE_NAME + " doesn't submit properly (after Save And Submit)");
        Assert.assertTrue(quickLinkAdd.checkQuickLink(data, sQuickLinkName), "Submitted New "+ PAGE_NAME +" data doesn't fit well to entry data (after Save and Submit)");
    }

    @Test(dataProvider=DATA, priority=3)
    public void publishQuickLink(JSONObject data) throws InterruptedException {
        String sQuickLinkName = data.get(QUICKLINK_DESCRIPTION).toString();
        Assert.assertEquals(quickLinkAdd.publishQuickLink(data, sQuickLinkName), WorkflowState.LIVE.state(), "New "+ PAGE_NAME +" doesn't publish properly (after Publish)");
    }

    @Test(dataProvider=DATA, priority=4)
    public void revertQuickLink(JSONObject data) throws InterruptedException {
        String sQuickLinkName = data.get(QUICKLINK_DESCRIPTION).toString();

        Assert.assertEquals(quickLinkAdd.changeAndSubmitQuickLink(data, sQuickLinkName), WorkflowState.FOR_APPROVAL.state(), "Some fields of New "+ PAGE_NAME +" didn't change properly (after Save and Submit)");
        Assert.assertEquals(quickLinkAdd.revertToLiveQuickLink(sQuickLinkName), WorkflowState.LIVE.state(), "Couldn't revert to Live changes for New "+ PAGE_NAME);
        Assert.assertTrue(quickLinkAdd.checkQuickLink(data, sQuickLinkName), "Submitted New "+ PAGE_NAME +" data doesn't fit well to entry data (after Revert To Live)");
    }

    @Test(dataProvider=DATA, priority=5)
    public void changeAndSubmitQuickLink(JSONObject data) throws Exception {
        String sQuickLinkName = data.get(QUICKLINK_DESCRIPTION).toString();

        Assert.assertEquals(quickLinkAdd.changeAndSubmitQuickLink(data, sQuickLinkName), WorkflowState.FOR_APPROVAL.state(), "Some fields of New "+ PAGE_NAME +" didn't change properly (after Save and Submit)");
        Assert.assertTrue(quickLinkAdd.checkQuickLinkCh(data, sQuickLinkName), "Submitted New "+ PAGE_NAME +" changes don't fit well to change data (after Change And Submit)");
    }

    @Test(dataProvider=DATA, priority=6)
    public void publishEditQuickLink(JSONObject data) throws InterruptedException {
        String sQuickLinkName = data.get(QUICKLINK_DESCRIPTION).toString();
        Assert.assertEquals(quickLinkAdd.publishQuickLink(data, sQuickLinkName), WorkflowState.LIVE.state(), "New "+ PAGE_NAME +" doesn't publish properly (after Publish)");
    }

    @Test(dataProvider=DATA, priority=7)
    public void deleteQuickLink(JSONObject data) throws Exception {
        String sQuickLinkName = data.get(QUICKLINK_DESCRIPTION).toString();
        Assert.assertEquals(quickLinkAdd.setupAsDeletedQuickLink(sQuickLinkName), WorkflowState.DELETE_PENDING.state(), "New "+ PAGE_NAME +" didn't setup as Deleted properly");
    }

    @Test(dataProvider=DATA, priority=8)
    public void removeQuickLink(JSONObject data) throws Exception {
        String sQuickLinkName = data.get(QUICKLINK_DESCRIPTION).toString();
        Assert.assertEquals(quickLinkAdd.removeQuickLink(data, sQuickLinkName), WorkflowState.NEW_ITEM.state(), "Couldn't remove New "+ PAGE_NAME +". Something went wrong.");
    }

    @DataProvider
    public Object[][] getData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray jsonArray = (JSONArray) jsonObject.get("quick_link");
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
    
    
}
