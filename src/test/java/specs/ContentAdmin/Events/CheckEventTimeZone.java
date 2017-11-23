package specs.ContentAdmin.Events;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.ContentAdmin.Events.EventTimezone;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.PageAdmin.WorkflowState;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by charleszheng on 2017-11-14.
 */

public class CheckEventTimeZone extends AbstractSpec{
    private static By contentAdminMenuButton, EventMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static EventTimezone eventTimezone;

    private static String sPathToFile, sDataFileJson, sEventTitle;
    private static JSONParser parser;

    private final String DATA="getData", PAGE_NAME="Event";

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        EventMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_Events"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        eventTimezone = new EventTimezone(driver);

        sPathToFile = System.getProperty("user.dir") + propUIContentAdmin.getProperty("dataPath_EventList");
        sDataFileJson = propUIContentAdmin.getProperty("json_TimeZoneData");

        parser = new JSONParser();

        loginPage.loginUser();

        dashboard.openPageFromMenu(contentAdminMenuButton, EventMenuItem);
    }

    @Test(dataProvider=DATA, priority=1, enabled=true)
    public void setUpEvent(JSONObject data) throws InterruptedException{
        sEventTitle = data.get("event_title").toString();
        Assert.assertEquals(eventTimezone.saveAndSubmitEvent(data, sEventTitle), WorkflowState.FOR_APPROVAL.state(), "New " + PAGE_NAME + " doesn't submit properly (after Save And Submit)");
        Assert.assertEquals(eventTimezone.publishEvent(data, sEventTitle), WorkflowState.LIVE.state(), "New "+ PAGE_NAME +" doesn't publish properly (after Publish)");
        Assert.assertTrue(eventTimezone.goToPublicSite(data, sEventTitle), "Failed go to public site");
    }

    @Test(dataProvider=DATA, priority=3, enabled=true)
    public void checkModule(JSONObject data) {
        Assert.assertTrue(eventTimezone.isModuleCorrect(), "Failed checking event module");
    }

    @Test(dataProvider=DATA, priority=4, enabled=true)
    public void checkRSS(JSONObject data) throws InterruptedException{
        sEventTitle = data.get("event_title").toString();
        Assert.assertTrue(eventTimezone.isRSSCorrect(data, sEventTitle), "Failed checking RSS page");
    }

    @Test(dataProvider=DATA, priority=5, enabled=true)
    public void checkCalendar(JSONObject data) throws InterruptedException{
        sEventTitle = data.get("event_title").toString();
        Assert.assertTrue(eventTimezone.isCalendarCorrect(data, sEventTitle),"Failed checking Calendar");
    }

    @Test(dataProvider=DATA, priority=2, enabled=true)
    public void checkFeed(JSONObject data){
        sEventTitle = data.get("event_title").toString();
        Assert.assertTrue(eventTimezone.isFeedCorrect(data, sEventTitle),"Failed checking Feed");
    }

    @Test(dataProvider = DATA, priority=6, enabled = true)
    public void checkPastEvent(JSONObject data) throws InterruptedException{
        sEventTitle = data.get("event_title").toString();
        Assert.assertTrue(eventTimezone.isMovedToPastEvent(data, sEventTitle), "Failed checking moving to past event");
    }

    @Test(dataProvider=DATA, priority=7, enabled=true)
    public void deleteEvent(JSONObject data) throws InterruptedException {
        sEventTitle = data.get("event_title").toString();
        Assert.assertEquals(eventTimezone.setupAsDeletedEvent(sEventTitle), WorkflowState.DELETE_PENDING.state(), PAGE_NAME +" didn't setup as Deleted properly");
        Assert.assertTrue(eventTimezone.removeEvent(data, sEventTitle), "Failed removing event");
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
        driver.quit();
    }


}
