package specs.PageAdmin;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.PageAdmin.PageAdd;
import pageobjects.PageAdmin.WorkflowState;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by philipsushkov on 2017-01-24.
 */

public class CheckPageAdd extends AbstractSpec {

    private static By pageAdminMenuButton;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static PageAdd pageAdd;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String PAGE_DATA="pageData", SECTION_TITLE="section_title", SECTION_TITLE_CH="section_title_ch";

    @BeforeTest
    public void setUp() throws Exception {
        pageAdminMenuButton = By.xpath(propUIPageAdmin.getProperty("btnMenu_PageAdmin"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        pageAdd = new PageAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUIPageAdmin.getProperty("dataPath_PageAdmin");
        sDataFileJson = propUIPageAdmin.getProperty("json_CreatePageData");

        parser = new JSONParser();

        loginPage.loginUser();
    }


    @Test(dataProvider=PAGE_DATA, priority=1)
    public void savePage(JSONObject page) throws Exception {
        String pageName = page.get(SECTION_TITLE).toString();

        dashboard.openPageFromCommonTasks(pageAdminMenuButton);

        Assert.assertEquals(pageAdd.createPage(page, pageName), WorkflowState.IN_PROGRESS.state(), "New Page didn't create properly");
        Assert.assertTrue(pageAdd.previewPage(pageName), "Preview of New Page didn't work properly (after Save)");
        Assert.assertFalse(pageAdd.publicPage(pageName), "Public site shouldn't show the content of New Page when it is not published yet (after Save)");

        dashboard.openPageFromCommonTasks(pageAdminMenuButton);
        Assert.assertTrue(pageAdd.listPage(pageName), "New Page isn't found on Public Page List");
    }


    @Test(dataProvider=PAGE_DATA, priority=2)
    public void saveAndSubmitPage(JSONObject page) throws Exception {
        String pageName = page.get(SECTION_TITLE).toString();

        Assert.assertEquals(pageAdd.saveAndSubmitPage(page, pageName), WorkflowState.FOR_APPROVAL.state(), "Couldn't submit New Page");
        Assert.assertTrue(pageAdd.checkPageData(page, pageName), "Submitted New Page data doesn't fit well to entry data (after Save and Submit)");
        Assert.assertTrue(pageAdd.previewPage(pageName), "Preview of New Page didn't work properly (after Save and Submit)");
        Assert.assertFalse(pageAdd.publicPage(pageName), "Public site shouldn't show the content of New Page when not published yet (after Save and Submit)");
    }


    @Test(dataProvider=PAGE_DATA, priority=3)
    public void publishPage(JSONObject page) throws Exception {
        String pageName = page.get(SECTION_TITLE).toString();

        Assert.assertEquals(pageAdd.publishPage(pageName), WorkflowState.LIVE.state(), "Couldn't publish New Page properly");
        Assert.assertTrue(pageAdd.previewPage(pageName), "Preview of New Page didn't work properly (after Publish)");
        Assert.assertTrue(pageAdd.publicPage(pageName), "New page should be shown on Public site (after Publish)");
    }


    @Test(dataProvider=PAGE_DATA, priority=4)
    public void revertEditPage(JSONObject page) throws Exception {
        String pageName = page.get(SECTION_TITLE).toString();

        dashboard.openPageFromCommonTasks(pageAdminMenuButton);

        Assert.assertEquals(pageAdd.changeAndSubmitPage(page, pageName), WorkflowState.FOR_APPROVAL.state(), "Some fields of New Page didn't changed properly");

        Assert.assertEquals(pageAdd.revertToLivePage(pageName), WorkflowState.LIVE.state(), "Couldn't revert to Live changes for New Page");
        Assert.assertTrue(pageAdd.checkPageData(page, pageName), "Submitted New Page data doesn't fit well to entry data (after Revert To Live)");
        Assert.assertTrue(pageAdd.previewPage(pageName), "Preview of New Page didn't work properly (after Revert To Live)");
        Assert.assertTrue(pageAdd.publicPage(pageName), "New page should be shown on Public site (after Revert To Live)");
    }


    @Test(dataProvider=PAGE_DATA, priority=5)
    public void changeAndSubmitEditPage(JSONObject page) throws Exception {
        String pageName = page.get(SECTION_TITLE).toString();

        Assert.assertEquals(pageAdd.changeAndSubmitPage(page, pageName), WorkflowState.FOR_APPROVAL.state(), "Some fields of New Page didn't changed properly");
        Assert.assertTrue(pageAdd.checkPageChanges(page, pageName), "Submitted New Page changes don't fit well to entry data (after Change And Submit)");
    }


    @Test(dataProvider=PAGE_DATA, priority=6)
    public void publishEditPage(JSONObject page) throws Exception {
        String pageName = page.get(SECTION_TITLE).toString();

        Assert.assertEquals(pageAdd.publishPage(pageName), WorkflowState.LIVE.state(), "Couldn't publish New Page properly");

        try {
            pageName = page.get(SECTION_TITLE_CH).toString();
        } catch (NullPointerException e) {
        }
        Assert.assertTrue(pageAdd.previewPage(pageName), "Preview of Changed New Page didn't work properly (after Publish)");
        Assert.assertTrue(pageAdd.publicPage(pageName), "Changed New Page should be shown on Public site (after Publish)");
    }


    @Test(dataProvider=PAGE_DATA, priority=7)
    public void deletePage(JSONObject page) throws Exception {
        String pageName = page.get(SECTION_TITLE).toString();

        dashboard.openPageFromCommonTasks(pageAdminMenuButton);

        Assert.assertEquals(pageAdd.setupAsDeletedPage(pageName), WorkflowState.DELETE_PENDING.state(), "New Page didn't setup as Deleted properly");

        try {
            pageName = page.get(SECTION_TITLE_CH).toString();
        } catch (NullPointerException e) {
        }

        Assert.assertFalse(pageAdd.previewPage(pageName), "Changed Page shouldn't be shown in Preview (after Delete)");
        Assert.assertTrue(pageAdd.publicPage(pageName), "Changed Page should be shown on Public pages (after Delete)");
    }


    @Test(dataProvider=PAGE_DATA, priority=8)
    public void removePage(JSONObject page) throws Exception {
        String pageName = page.get(SECTION_TITLE).toString();

        Assert.assertEquals(pageAdd.removePage(page, pageName), WorkflowState.NEW_ITEM.state(), "Couldn't remove New Page. Something went wrong.");
    }


    @DataProvider
    public Object[][] pageData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray pageData = (JSONArray) jsonObject.get("pages");
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
