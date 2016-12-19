package specs.ContentAdmin.Events;

import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import pageobjects.ContentAdmin.Events.EditEvent;
import pageobjects.LiveSite.LiveEvents;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;
import pageobjects.ContentAdmin.Events.Events;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by philipsushkov on 2016-11-09.
 */

public class PublishEvent extends AbstractSpec {
    private static By addEventButton;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static Events events;
    private static EditEvent editEvent;
    private static LiveEvents liveEvens;


    @BeforeTest
    public void setUp() throws Exception {
        addEventButton = By.xpath(propUICommon.getProperty("btn_AddEvent"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        events = new Events(driver);
        editEvent = new EditEvent(driver);
        liveEvens = new LiveEvents(driver);

        loginPage.loginUser();
    }

    private Date current = new Date();

    private SimpleDateFormat fullDateF = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
    private SimpleDateFormat dateF = new SimpleDateFormat("MM/dd/yyyy");
    private SimpleDateFormat hourF = new SimpleDateFormat("h");
    private SimpleDateFormat minF = new SimpleDateFormat("mm");
    private SimpleDateFormat AMPMF = new SimpleDateFormat("a");

    private String headline = "Exciting testing event! v: " + fullDateF.format(current);
    private String headlineV2 = "Amazing testing event! v: " + fullDateF.format(current);
    private String timeZone = "Eastern Standard Time";
    private String tags = "en";
    private String location = "Toronto";
    private String today = dateF.format(current);
    private String tommorrow = dateF.format(current.getTime() + (1000 * 60 * 60 * 24));
    private String hour = hourF.format(current);
    private String min = minF.format(current);
    private String AMPM = AMPMF.format(current);
    private String dashboardURL = null;

    @Test
    public void canAddNewEvent() throws Exception {

        dashboardURL = dashboard.getUrl();
        String[] filenames = new String[2];

        dashboard.openPageFromCommonTasks(addEventButton);

        String newsPageURL = editEvent.addNewEvent(headline, today, tommorrow, hour, min, AMPM, timeZone, tags, location, filenames);
        Assert.assertNotNull(newsPageURL);

        System.out.println(newsPageURL);

        // publishing event
        events.publishEvent(headline);

        // checking event on live site
        System.out.println("Looking for headline: " + headline);
        boolean headlineFound = events.liveEvents(newsPageURL).canFindNewHeadline(headline, true, filenames);
        Assert.assertTrue(headlineFound);

        // changing headline on an existing event
        liveEvens.dashboard(dashboardURL).events().clickEditEventButton(headline).changeHeadlineTo(headlineV2);

        // publishing and checking updated event
        events.publishEvent(headlineV2);
        System.out.println("Looking for headline: " + headlineV2);
        headlineFound = events.liveEvents(newsPageURL).canFindNewHeadline(headlineV2, true, filenames);
        Assert.assertTrue(headlineFound);

        // deleting event, and verifying it is gone
        liveEvens.dashboard(dashboardURL).events().clickEditEventButton(headlineV2).deleteEvent();
        events.publishEvent(headlineV2);
        headlineFound = events.liveEvents(newsPageURL).canFindNewHeadline(headlineV2, false, filenames);
        Assert.assertFalse(headlineFound);
    }

    @AfterTest
    public void tearDown() {
        liveEvens.dashboard(dashboardURL);
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
