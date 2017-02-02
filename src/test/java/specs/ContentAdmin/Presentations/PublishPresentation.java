package specs.ContentAdmin.Presentations;

import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import pageobjects.ContentAdmin.Presentations.PresentationEdit;
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
    private static By addPresentationButton, contentAdminMenuButton, presentationsMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static Presentations presentations;
    private static PresentationEdit presentationEdit;
    private static LivePresentations livePresentations;

    @BeforeTest
    public void setUp() throws Exception {
        addPresentationButton = By.xpath(propUICommon.getProperty("btn_AddPresentation"));
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        presentationsMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_Presentations"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        presentations = new Presentations(driver);
        presentationEdit = new PresentationEdit(driver);
        livePresentations = new LivePresentations(driver);

        loginPage.loginUser();
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

        dashboard.openPageFromCommonTasks(addPresentationButton);

        String newsPageURL = presentationEdit.addNewPresentation(headline, date, hour, min, AMPM, filenames);
        Assert.assertNotNull(newsPageURL);

        // publishing presentation
        presentations.publishPresentation(headline);

        // checking presentation on live site
        System.out.println("Looking for headline: " + headline);
        boolean headlineFound = presentations.livePresentations(newsPageURL).canFindNewHeadline(headline, true, filenames);
        Assert.assertTrue(headlineFound);

        // changing headline on an existing presentation
        livePresentations.dashboard(dashboardURL).openPageFromMenu(contentAdminMenuButton, presentationsMenuItem);
        presentations.editPresentation(headline).changeHeadlineTo(headlineV2);

        // publishing and checking updated presentation
        presentations.publishPresentation(headlineV2);
        System.out.println("Looking for headline: " + headlineV2);
        headlineFound = presentations.livePresentations(newsPageURL).canFindNewHeadline(headlineV2, true, filenames);
        Assert.assertTrue(headlineFound);

        // deleting presentation, and verifying it is gone
        livePresentations.dashboard(dashboardURL).openPageFromMenu(contentAdminMenuButton, presentationsMenuItem);
        presentations.editPresentation(headlineV2).deletePresentation();
        presentations.publishPresentation(headlineV2);
        headlineFound = presentations.livePresentations(newsPageURL).canFindNewHeadline(headlineV2, false, filenames);
        Assert.assertFalse(headlineFound);

    }

    @AfterTest
    public void tearDown() {
        livePresentations.dashboard(dashboardURL);
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
