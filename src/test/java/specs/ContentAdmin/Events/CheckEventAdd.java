package specs.ContentAdmin.Events;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.ContentAdmin.Events.EventAdd;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.PageAdmin.WorkflowState;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by charleszheng on 2017-10-12.
 */

public class CheckEventAdd extends AbstractSpec {
    private static By contentAdminMenuButton, EventMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static EventAdd eventAdd;

    private static String sPathToFile, sDataFileJson, sEventTitle;
    private static JSONParser parser;

    private final String DATA="getData", PAGE_NAME="Event";

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        EventMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_Events"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        eventAdd = new EventAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUIContentAdmin.getProperty("dataPath_EventList");
        sDataFileJson = propUIContentAdmin.getProperty("json_EventData");

        parser = new JSONParser();

        loginPage.loginUser();
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        dashboard.openPageFromMenu(contentAdminMenuButton, EventMenuItem);
    }

    @Test(dataProvider=DATA, priority=1, enabled=true)
    public void saveEvent(JSONObject data) {
        String expectedTitleEdit = "Event / Webcast Edit";

        sEventTitle = data.get("event_title").toString();
        Assert.assertEquals(eventAdd.getTitle(), expectedTitleEdit, "Actual "+PAGE_NAME+" Edit page Title doesn't match to expected");
        Assert.assertEquals(eventAdd.saveEvent(data, sEventTitle), WorkflowState.IN_PROGRESS.state(), "New "+PAGE_NAME+" didn't save properly");
    }

    @Test(dataProvider=DATA, priority=2, enabled=true)
    public void saveAndSubmitEvent(JSONObject data) throws InterruptedException {
        sEventTitle = data.get("event_title").toString();
        Assert.assertEquals(eventAdd.saveAndSubmitEvent(data, sEventTitle), WorkflowState.FOR_APPROVAL.state(), "New " + PAGE_NAME + " doesn't submit properly (after Save And Submit)");
        Assert.assertTrue(eventAdd.checkEvent(data, sEventTitle), "Submitted New "+ PAGE_NAME +" data doesn't fit well to entry data (after Save and Submit)");
//        Assert.assertTrue(eventAdd.previewEvent(data, sEventTitle), "Preview of New "+ PAGE_NAME + " didn't work properly (after Save and Submit)");
        Assert.assertFalse(eventAdd.publicEvent(data, sEventTitle), "New "+ PAGE_NAME + " shouldn't be shown on Public site (after Save and Submit)");

    }

    @Test(dataProvider=DATA, priority=3, enabled=true)
    public void publishEvent(JSONObject data) throws InterruptedException {
        sEventTitle = data.get("event_title").toString();
        Assert.assertEquals(eventAdd.publishEvent(data, sEventTitle), WorkflowState.LIVE.state(), "New "+ PAGE_NAME +" doesn't publish properly (after Publish)");
        Assert.assertTrue(eventAdd.publicEvent(data, sEventTitle), "New "+ PAGE_NAME + " should be shown on Public site (after Publish)");
    }

    @Test(dataProvider=DATA, priority=4)
    public void revertEvent(JSONObject data) throws InterruptedException {
        sEventTitle = data.get("event_title").toString();
        Assert.assertEquals(eventAdd.changeAndSubmitEvent(data, sEventTitle), WorkflowState.FOR_APPROVAL.state(), "Some fields of New "+ PAGE_NAME +" didn't change properly (after Change and Submit)");
        Assert.assertEquals(eventAdd.revertToLiveEvent(sEventTitle), WorkflowState.LIVE.state(), "Couldn't revert to Live changes for New "+ PAGE_NAME);
        Assert.assertTrue(eventAdd.checkEvent(data, sEventTitle), "Reverted "+ PAGE_NAME +" data doesn't fit well to entry data (after Revert To Live)");
//      Assert.assertTrue(eventAdd.previewPressRelease(data, sEventTitle), "Preview of Reverted "+ PAGE_NAME + " didn't work properly (after Revert to Live)");
        Assert.assertTrue(eventAdd.publicEvent(data, sEventTitle), "Reverted"+ PAGE_NAME + " should be shown on Public site (after Revert to Live)");
    }

    @Test(dataProvider=DATA, priority=5)
    public void changeAndSubmitEvent(JSONObject data) throws Exception {
        sEventTitle = data.get("event_title").toString();
        Assert.assertEquals(eventAdd.changeAndSubmitEvent(data, sEventTitle), WorkflowState.FOR_APPROVAL.state(), "Some fields of New "+ PAGE_NAME +" didn't change properly (after Change and Submit)");
        Assert.assertTrue(eventAdd.checkEventCh(data, sEventTitle), "Submitted New "+ PAGE_NAME +" changes don't fit well to change data (after Change And Submit)");
        if (data.get("event_title_ch") != null){
//            Assert.assertTrue(eventAdd.previewPressReleaseCh(data, sEventTitle), "Preview of Changed "+ PAGE_NAME + "  didn't work properly (after Change and Submit)");
            Assert.assertFalse(eventAdd.publicEventCh(data, sEventTitle), "Changed "+ PAGE_NAME + " shouldn't be shown on Public site (after Change and Submit)");
        }
        else
        {
            // Assert.assertTrue(eventAdd.previewPressRelease(data, sEventTitle), "Preview of Changed "+ PAGE_NAME + "  didn't work properly (after Change and Submit)");
            Assert.assertTrue(eventAdd.publicEvent(data, sEventTitle), "Changed "+ PAGE_NAME + " with unchanged headline should show on Public site (after Change and Submit)");
        }
    }

    @Test(dataProvider=DATA, priority=6)
    public void publishEditEvent(JSONObject data) throws InterruptedException {
        sEventTitle = data.get("event_title").toString();
        Assert.assertEquals(eventAdd.publishEvent(data, sEventTitle), WorkflowState.LIVE.state(), "Changed "+ PAGE_NAME +" doesn't publish properly (after Publish)");
        if (data.get("event_title_ch") != null) {
            Assert.assertTrue(eventAdd.publicEventCh(data, sEventTitle), "Changed " + PAGE_NAME + " should be shown on Public site (after Publish Edit)");
        }
        else
        {
            Assert.assertTrue(eventAdd.publicEvent(data, sEventTitle), "Changed " + PAGE_NAME + " should be shown on Public site (after Publish Edit)");
        }
    }

    @Test(dataProvider=DATA, priority=7)
    public void deleteEvent(JSONObject data) throws Exception {
        sEventTitle = data.get("event_title").toString();
        Assert.assertEquals(eventAdd.setupAsDeletedEvent(sEventTitle), WorkflowState.DELETE_PENDING.state(), "Changed "+ PAGE_NAME +" didn't setup as Deleted properly");
        if (data.get("event_title_ch") != null) {
        // Assert.assertTrue(eventAdd.previewPressReleaseCh(data, sEventTitle), "Deleted " + PAGE_NAME + " shouldn't be shown in Preview (after Delete)");
            Assert.assertTrue(eventAdd.publicEventCh(data, sEventTitle), "Deleted " + PAGE_NAME + " should be shown on Public pages (after Delete)");
        }
        else
        {
        // Assert.assertTrue(eventAdd.previewPressRelease(data, sEventTitle), "Deleted " + PAGE_NAME + " shouldn't be shown in Preview (after Delete)");
            Assert.assertTrue(eventAdd.publicEvent(data, sEventTitle), "Deleted " + PAGE_NAME + " should be shown on Public pages (after Delete)");
        }
    }

    @Test(dataProvider=DATA, priority=8)
    public void removeEvent(JSONObject data) throws Exception {
        sEventTitle = data.get("event_title").toString();
        Assert.assertEquals(eventAdd.removeEvent(data, sEventTitle), WorkflowState.NEW_ITEM.state(), "Couldn't remove New "+ PAGE_NAME +". Something went wrong.");
    }

    @DataProvider
    public Object[][] getData() {

        try {
            System.out.println(sPathToFile + sDataFileJson);
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray jsonArray = (JSONArray) jsonObject.get("event");
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
