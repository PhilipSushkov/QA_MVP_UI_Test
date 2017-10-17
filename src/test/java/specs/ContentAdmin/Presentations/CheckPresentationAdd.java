package specs.ContentAdmin.Presentations;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.ContentAdmin.Presentations.PresentationAdd;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.PageAdmin.WorkflowState;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by charleszheng on 2017-09-27.
 */

public class CheckPresentationAdd extends AbstractSpec {
    private static By contentAdminMenuButton, PresentationMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static PresentationAdd presentationAdd;

    private static String sPathToFile, sDataFileJson, sPresentationTitle;
    private static JSONParser parser;

    private final String DATA="getData", PAGE_NAME="Presentation";

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        PresentationMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_Presentations"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        presentationAdd = new PresentationAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUIContentAdmin.getProperty("dataPath_PresentationList");
        sDataFileJson = propUIContentAdmin.getProperty("json_PresentationData");

        parser = new JSONParser();

        loginPage.loginUser();
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        dashboard.openPageFromMenu(contentAdminMenuButton, PresentationMenuItem);
    }

    @Test(dataProvider=DATA, priority=1, enabled=true)
    public void savePresentation(JSONObject data) {
        String expectedTitleEdit = "Presentation Edit";

        sPresentationTitle = data.get("presentation_title").toString();
        Assert.assertEquals(presentationAdd.getTitle(), expectedTitleEdit, "Actual "+PAGE_NAME+" Edit page Title doesn't match to expected");
        Assert.assertEquals(presentationAdd.savePresentation(data, sPresentationTitle), WorkflowState.IN_PROGRESS.state(), "New "+PAGE_NAME+" didn't save properly");
    }

    @Test(dataProvider=DATA, priority=2, enabled=true)
    public void saveAndSubmitPresentation(JSONObject data) throws InterruptedException {
        sPresentationTitle = data.get("presentation_title").toString();
        Assert.assertEquals(presentationAdd.saveAndSubmitPresentation(data, sPresentationTitle), WorkflowState.FOR_APPROVAL.state(), "New " + PAGE_NAME + " doesn't submit properly (after Save And Submit)");
        Assert.assertTrue(presentationAdd.checkPresentation(data, sPresentationTitle), "Submitted New "+ PAGE_NAME +" data doesn't fit well to entry data (after Save and Submit)");
//        Assert.assertTrue(presentationAdd.previewPresentation(data, sPresentationTitle), "Preview of New "+ PAGE_NAME + " didn't work properly (after Save and Submit)");
        Assert.assertFalse(presentationAdd.publicPresentation(data, sPresentationTitle), "New "+ PAGE_NAME + " shouldn't be shown on Public site (after Save and Submit)");

    }

    @Test(dataProvider=DATA, priority=3, enabled=true)
    public void publishPresentation(JSONObject data) throws InterruptedException {
        sPresentationTitle = data.get("presentation_title").toString();
        Assert.assertEquals(presentationAdd.publishPresentation(data, sPresentationTitle), WorkflowState.LIVE.state(), "New "+ PAGE_NAME +" doesn't publish properly (after Publish)");
        Assert.assertTrue(presentationAdd.publicPresentation(data, sPresentationTitle), "New "+ PAGE_NAME + " should be shown on Public site (after Publish)");
    }

    @Test(dataProvider=DATA, priority=4)
    public void revertPresentation(JSONObject data) throws InterruptedException {
        sPresentationTitle = data.get("presentation_title").toString();
        Assert.assertEquals(presentationAdd.changeAndSubmitPresentation(data, sPresentationTitle), WorkflowState.FOR_APPROVAL.state(), "Some fields of New "+ PAGE_NAME +" didn't change properly (after Change and Submit)");
        Assert.assertEquals(presentationAdd.revertToLivePresentation(sPresentationTitle), WorkflowState.LIVE.state(), "Couldn't revert to Live changes for New "+ PAGE_NAME);
        Assert.assertTrue(presentationAdd.checkPresentation(data, sPresentationTitle), "Reverted "+ PAGE_NAME +" data doesn't fit well to entry data (after Revert To Live)");
//      Assert.assertTrue(presentationAdd.previewPressRelease(data, sPresentationTitle), "Preview of Reverted "+ PAGE_NAME + " didn't work properly (after Revert to Live)");
        Assert.assertTrue(presentationAdd.publicPresentation(data, sPresentationTitle), "Reverted"+ PAGE_NAME + " should be shown on Public site (after Revert to Live)");
    }

    @Test(dataProvider=DATA, priority=5)
    public void changeAndSubmitPresentation(JSONObject data) throws Exception {
        sPresentationTitle = data.get("presentation_title").toString();
        Assert.assertEquals(presentationAdd.changeAndSubmitPresentation(data, sPresentationTitle), WorkflowState.FOR_APPROVAL.state(), "Some fields of New "+ PAGE_NAME +" didn't change properly (after Change and Submit)");
        Assert.assertTrue(presentationAdd.checkPresentationCh(data, sPresentationTitle), "Submitted New "+ PAGE_NAME +" changes don't fit well to change data (after Change And Submit)");
        if (data.get("presentation_title_ch") != null){
//            Assert.assertTrue(presentationAdd.previewPressReleaseCh(data, sPresentationTitle), "Preview of Changed "+ PAGE_NAME + "  didn't work properly (after Change and Submit)");
            Assert.assertFalse(presentationAdd.publicPresentationCh(data, sPresentationTitle), "Changed "+ PAGE_NAME + " shouldn't be shown on Public site (after Change and Submit)");
        }
        else
        {
 //          Assert.assertTrue(presentationAdd.previewPressRelease(data, sPresentationTitle), "Preview of Changed "+ PAGE_NAME + "  didn't work properly (after Change and Submit)");
           Assert.assertTrue(presentationAdd.publicPresentation(data, sPresentationTitle), "Changed "+ PAGE_NAME + " with unchanged headline should show on Public site (after Change and Submit)");
        }
    }

    @Test(dataProvider=DATA, priority=6)
    public void publishEditPresentation(JSONObject data) throws InterruptedException {
        sPresentationTitle = data.get("presentation_title").toString();
        Assert.assertEquals(presentationAdd.publishPresentation(data, sPresentationTitle), WorkflowState.LIVE.state(), "Changed "+ PAGE_NAME +" doesn't publish properly (after Publish)");
        if (data.get("presentation_title_ch") != null) {
            Assert.assertTrue(presentationAdd.publicPresentationCh(data, sPresentationTitle), "Changed " + PAGE_NAME + " should be shown on Public site (after Publish Edit)");
        }
        else
        {
            Assert.assertTrue(presentationAdd.publicPresentation(data, sPresentationTitle), "Changed " + PAGE_NAME + " should be shown on Public site (after Publish Edit)");
        }
    }

    @Test(dataProvider=DATA, priority=7)
    public void deletePresentation(JSONObject data) throws Exception {
        sPresentationTitle = data.get("presentation_title").toString();
        Assert.assertEquals(presentationAdd.setupAsDeletedPresentation(sPresentationTitle), WorkflowState.DELETE_PENDING.state(), "Changed "+ PAGE_NAME +" didn't setup as Deleted properly");
        if (data.get("presentation_title_ch") != null) {
//            Assert.assertTrue(presentationAdd.previewPressReleaseCh(data, sPresentationTitle), "Deleted " + PAGE_NAME + " shouldn't be shown in Preview (after Delete)");
            Assert.assertTrue(presentationAdd.publicPresentationCh(data, sPresentationTitle), "Deleted " + PAGE_NAME + " should be shown on Public pages (after Delete)");
        }
        else
        {
//            Assert.assertTrue(presentationAdd.previewPressRelease(data, sPresentationTitle), "Deleted " + PAGE_NAME + " shouldn't be shown in Preview (after Delete)");
            Assert.assertTrue(presentationAdd.publicPresentation(data, sPresentationTitle), "Deleted " + PAGE_NAME + " should be shown on Public pages (after Delete)");
        }
    }

    @Test(dataProvider=DATA, priority=8)
    public void removePresentation(JSONObject data) throws Exception {
        sPresentationTitle = data.get("presentation_title").toString();
        Assert.assertEquals(presentationAdd.removePresentation(data, sPresentationTitle), WorkflowState.NEW_ITEM.state(), "Couldn't remove New "+ PAGE_NAME +". Something went wrong.");
    }

    @DataProvider
    public Object[][] getData() {

        try {
            System.out.println(sPathToFile + sDataFileJson);
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray jsonArray = (JSONArray) jsonObject.get("presentation");
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
