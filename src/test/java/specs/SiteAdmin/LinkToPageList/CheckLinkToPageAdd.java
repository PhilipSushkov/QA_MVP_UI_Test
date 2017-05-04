package specs.SiteAdmin.LinkToPageList;

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
import pageobjects.SiteAdmin.LinkToPageList.LinkToPageAdd;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by philipsushkov on 2017-05-03.
 */

public class CheckLinkToPageAdd extends AbstractSpec {
    private static By siteAdminMenuButton, linkToPageListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static LinkToPageAdd linkToPageAdd;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String DATA="getData", KEY_NAME="key_name", PAGE_NAME="Link To Page";

    @BeforeTest
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        linkToPageListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_LinkToPageList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        linkToPageAdd = new LinkToPageAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUISiteAdmin.getProperty("dataPath_LinkToPageList");
        sDataFileJson = propUISiteAdmin.getProperty("json_LinkToPageData");

        parser = new JSONParser();

        loginPage.loginUser();
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        dashboard.openPageFromMenu(siteAdminMenuButton, linkToPageListMenuItem);
    }

    @Test(dataProvider=DATA, priority=1)
    public void saveLinkToPage(JSONObject data) {
        String sLinkToPageName = data.get(KEY_NAME).toString();
        String expectedTitleEdit = "Link To Page Edit";

        Assert.assertEquals(linkToPageAdd.getTitle(), expectedTitleEdit, "Actual "+PAGE_NAME+" Edit page Title doesn't match to expected");
        Assert.assertEquals(linkToPageAdd.saveLinkToPage(data, sLinkToPageName), WorkflowState.IN_PROGRESS.state(), "New "+PAGE_NAME+" didn't save properly");
    }

    @Test(dataProvider=DATA, priority=2)
    public void saveAndSubmitLinkToPage(JSONObject data) throws InterruptedException {
        String sLinkToPageName = data.get(KEY_NAME).toString();

        Assert.assertEquals(linkToPageAdd.saveAndSubmitLinkToPage(data, sLinkToPageName), WorkflowState.FOR_APPROVAL.state(), "New " + PAGE_NAME + " doesn't submit properly (after Save And Submit)");
        Assert.assertTrue(linkToPageAdd.checkLinkToPage(data, sLinkToPageName), "Submitted New "+ PAGE_NAME +" data doesn't fit well to entry data (after Save and Submit)");
    }

    @Test(dataProvider=DATA, priority=3)
    public void publishLinkToPage(JSONObject data) throws InterruptedException {
        String sLinkToPageName = data.get(KEY_NAME).toString();
        Assert.assertEquals(linkToPageAdd.publishLinkToPage(data, sLinkToPageName), WorkflowState.LIVE.state(), "New "+ PAGE_NAME +" doesn't publish properly (after Publish)");
    }

    @Test(dataProvider=DATA, priority=4)
    public void revertLinkToPage(JSONObject data) throws InterruptedException {
        String sLinkToPageName = data.get(KEY_NAME).toString();

        Assert.assertEquals(linkToPageAdd.changeAndSubmitLinkToPage(data, sLinkToPageName), WorkflowState.FOR_APPROVAL.state(), "Some fields of New "+ PAGE_NAME +" didn't change properly (after Save and Submit)");
        Assert.assertEquals(linkToPageAdd.revertToLiveLinkToPage(sLinkToPageName), WorkflowState.LIVE.state(), "Couldn't revert to Live changes for New "+ PAGE_NAME);
        Assert.assertTrue(linkToPageAdd.checkLinkToPage(data, sLinkToPageName), "Submitted New "+ PAGE_NAME +" data doesn't fit well to entry data (after Revert To Live)");
    }

    @Test(dataProvider=DATA, priority=5)
    public void changeAndSubmitLinkToPage(JSONObject data) throws Exception {
        String sLinkToPageName = data.get(KEY_NAME).toString();

        Assert.assertEquals(linkToPageAdd.changeAndSubmitLinkToPage(data, sLinkToPageName), WorkflowState.FOR_APPROVAL.state(), "Some fields of New "+ PAGE_NAME +" didn't change properly (after Save and Submit)");
        Assert.assertTrue(linkToPageAdd.checkLinkToPageCh(data, sLinkToPageName), "Submitted New "+ PAGE_NAME +" changes don't fit well to change data (after Change And Submit)");
    }

    @Test(dataProvider=DATA, priority=6)
    public void publishEditLinkToPage(JSONObject data) throws InterruptedException {
        String sLinkToPageName = data.get(KEY_NAME).toString();
        Assert.assertEquals(linkToPageAdd.publishLinkToPage(data, sLinkToPageName), WorkflowState.LIVE.state(), "New "+ PAGE_NAME +" doesn't publish properly (after Publish)");
    }

    @Test(dataProvider=DATA, priority=7)
    public void deleteLinkToPage(JSONObject data) throws Exception {
        String sLinkToPageName = data.get(KEY_NAME).toString();
        Assert.assertEquals(linkToPageAdd.setupAsDeletedLinkToPage(sLinkToPageName), WorkflowState.DELETE_PENDING.state(), "New "+ PAGE_NAME +" didn't setup as Deleted properly");
    }

    @Test(dataProvider=DATA, priority=8)
    public void removeLinkToPage(JSONObject data) throws Exception {
        String sLinkToPageName = data.get(KEY_NAME).toString();
        Assert.assertEquals(linkToPageAdd.removeLinkToPage(data, sLinkToPageName), WorkflowState.NEW_ITEM.state(), "Couldn't remove New "+ PAGE_NAME +". Something went wrong.");
    }

    @DataProvider
    public Object[][] getData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray jsonArray = (JSONArray) jsonObject.get("link_to_page");
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
