package specs.Presentations;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import pageobjects.LiveSite.LivePresentations;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;
import pageobjects.Presentations.Presentations;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by philipsushkov on 2016-11-08.
 */
public class AddNewPresentation extends AbstractSpec {

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

    private String headline = "Exciting testing presentation! v: " + fullDateF.format(current);
    private String headlineV2 = "Amazing testing presentation! v: " + fullDateF.format(current);
    private String date = dateF.format(current);
    private String hour = hourF.format(current);
    private String min = minF.format(current);
    private String AMPM = AMPMF.format(current);


    @Test
    public void canAddNewPresentation() throws Exception {

        String dashboardURL = new Dashboard(driver).getURL();
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


    @After
    public void tearDown() {
        new Dashboard(driver).logout();
        //driver.quit();
    }

}
