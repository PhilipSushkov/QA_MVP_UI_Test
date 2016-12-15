package specs.ContentAdmin.Presentations;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import pageobjects.LiveSite.LivePresentations;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;
import pageobjects.ContentAdmin.Presentations.Presentations;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by philipsushkov on 2016-11-08.
 */
public class PublishPresentation extends AbstractSpec {

    @BeforeTest
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    private Date current = new Date();

    private SimpleDateFormat fullDateF = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
    private SimpleDateFormat dateF = new SimpleDateFormat("MM/dd/yyyy");
    private SimpleDateFormat hourF = new SimpleDateFormat("h");
    private SimpleDateFormat minF = new SimpleDateFormat("mm");
    private SimpleDateFormat AMPMF = new SimpleDateFormat("a");

    private String headline = "Exciting testing presentation! v: " + fullDateF.format(current);
    private String headlineV2 = "Amazing testing presentation! v: " + fullDateF.format(current);
    private String date = dateF.format(current);
    private String hour = hourF.format(current);
    private String min = minF.format(current);
    private String AMPM = AMPMF.format(current);

    private String dashboardURL = null;


    @Test
    public void canAddNewPresentation() throws Exception {

        dashboardURL = new Dashboard(driver).getUrl();
        String[] filenames = new String[2];

        String newsPageURL = new Dashboard(driver).newPresentation().addNewPresentation(headline, date, hour, min, AMPM, filenames);
        Assert.assertNotNull(newsPageURL);

        // publishing presentation
        new Presentations(driver).publishPresentation(headline);

        // checking presentation on live site
        System.out.println("Looking for headline: " + headline);
        boolean headlineFound = new Presentations(driver).livePresentations(newsPageURL).canFindNewHeadline(headline, true, filenames);
        Assert.assertTrue(headlineFound);

        // changing headline on an existing presentation
        new LivePresentations(driver).dashboard(dashboardURL).presentations().editPresentation(headline).changeHeadlineTo(headlineV2);

        // publishing and checking updated presentation
        new Presentations(driver).publishPresentation(headlineV2);
        System.out.println("Looking for headline: " + headlineV2);
        headlineFound = new Presentations(driver).livePresentations(newsPageURL).canFindNewHeadline(headlineV2, true, filenames);
        Assert.assertTrue(headlineFound);

        // deleting presentation, and verifying it is gone
        new LivePresentations(driver).dashboard(dashboardURL).presentations().editPresentation(headlineV2).deletePresentation();
        new Presentations(driver).publishPresentation(headlineV2);
        headlineFound = new Presentations(driver).livePresentations(newsPageURL).canFindNewHeadline(headlineV2, false, filenames);
        Assert.assertFalse(headlineFound);

    }


    @AfterTest
    public void tearDown() {
        new LivePresentations(driver).dashboard(dashboardURL);
        new Dashboard(driver).logoutFromAdmin();
        //driver.quit();
    }

}
