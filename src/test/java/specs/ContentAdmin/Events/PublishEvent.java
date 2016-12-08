package specs.ContentAdmin.Events;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
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


    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
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

        dashboardURL = new Dashboard(driver).getURL();
        String[] filenames = new String[2];

        String newsPageURL = new Dashboard(driver).newEvent().addNewEvent(headline, today, tommorrow, hour, min, AMPM, timeZone, tags, location, filenames);
        Assert.assertNotNull(newsPageURL);

        System.out.println(newsPageURL);

        // publishing event
        new Events(driver).publishEvent(headline);

        // checking event on live site
        System.out.println("Looking for headline: " + headline);
        boolean headlineFound = new Events(driver).liveEvents(newsPageURL).canFindNewHeadline(headline, true, filenames);
        Assert.assertTrue(headlineFound);

        // changing headline on an existing event
        new LiveEvents(driver).dashboard(dashboardURL).events().clickEditEventButton(headline).changeHeadlineTo(headlineV2);

        // publishing and checking updated event
        new Events(driver).publishEvent(headlineV2);
        System.out.println("Looking for headline: " + headlineV2);
        headlineFound = new Events(driver).liveEvents(newsPageURL).canFindNewHeadline(headlineV2, true, filenames);
        Assert.assertTrue(headlineFound);

        // deleting event, and verifying it is gone
        new LiveEvents(driver).dashboard(dashboardURL).events().clickEditEventButton(headlineV2).deleteEvent();
        new Events(driver).publishEvent(headlineV2);
        headlineFound = new Events(driver).liveEvents(newsPageURL).canFindNewHeadline(headlineV2, false, filenames);
        Assert.assertFalse(headlineFound);
    }

    @After
    public void tearDown() {
        new LiveEvents(driver).dashboard(dashboardURL);
        new Dashboard(driver).logout();
        //driver.quit();
    }

}
