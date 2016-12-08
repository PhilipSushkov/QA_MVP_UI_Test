package specs.ContentAdmin.PressReleases;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;
import pageobjects.ContentAdmin.PressReleases.PressReleases;
import pageobjects.LiveSite.LivePressReleases;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PublishPressRelease extends AbstractSpec {

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

    private String headline = "Exciting testing news! v: " + fullDateF.format(current);
    private String headlineV2 = "Amazing testing news! v: " + fullDateF.format(current);
    private String date = dateF.format(current);
    private String hour = hourF.format(current);
    private String min = minF.format(current);
    private String AMPM = AMPMF.format(current);

    private String dashboardURL = null;

    @Test
    public void canAddNewPressRelease() throws Exception {
        dashboardURL = new Dashboard(driver).getURL();
        String[] filenames = new String[2];

        // adding new press release
        String newsPageURL = new Dashboard(driver).newPressRelease().addNewPressRelease(headline, date, hour, min, AMPM, filenames);
        Assert.assertNotNull(newsPageURL);

        // publishing press release
        new PressReleases(driver).publishPressRelease(headline);

        // checking press release on live site
        System.out.println("Looking for headline: " + headline);
        boolean headlineFound = new PressReleases(driver).livePressReleases(newsPageURL).canFindNewHeadline(headline, true, filenames);
        Assert.assertTrue(headlineFound);

        // changing headline on an existing press release
        new LivePressReleases(driver).dashboard(dashboardURL).pressReleases().editPressRelease(headline).changeHeadlineTo(headlineV2);

        // publishing and checking updated press release
        new PressReleases(driver).publishPressRelease(headlineV2);
        System.out.println("Looking for headline: " + headlineV2);
        headlineFound = new PressReleases(driver).livePressReleases(newsPageURL).canFindNewHeadline(headlineV2, true, filenames);
        Assert.assertTrue(headlineFound);

        // deleting press release, and verifying it is gone
        new LivePressReleases(driver).dashboard(dashboardURL).pressReleases().editPressRelease(headlineV2).deletePressRelease();
        new PressReleases(driver).publishPressRelease(headlineV2);
        headlineFound = new PressReleases(driver).livePressReleases(newsPageURL).canFindNewHeadline(headlineV2, false, filenames);
        Assert.assertFalse(headlineFound);
    }


    @After
    public void tearDown() {
        new LivePressReleases(driver).dashboard(dashboardURL);
        new Dashboard(driver).logout();
        //driver.quit();
    }

}
