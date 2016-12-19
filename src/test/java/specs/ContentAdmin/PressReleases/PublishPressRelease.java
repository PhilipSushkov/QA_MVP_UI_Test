package specs.ContentAdmin.PressReleases;

import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import pageobjects.ContentAdmin.PressReleases.EditPressRelease;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;
import pageobjects.ContentAdmin.PressReleases.PressReleases;
import pageobjects.LiveSite.LivePressReleases;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PublishPressRelease extends AbstractSpec {
    private static By btn_AddPressRelease;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static PressReleases pressReleases;
    private static EditPressRelease editPressRelease;
    private static LivePressReleases livePressRelease;

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

    @BeforeTest
    public void setUp() throws Exception {
        btn_AddPressRelease = By.xpath(propUICommon.getProperty("btn_AddPressRelease"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        pressReleases = new PressReleases(driver);
        editPressRelease = new EditPressRelease(driver);
        livePressRelease = new LivePressReleases(driver);

        loginPage.loginUser();
    }

    @Test
    public void canAddNewPressRelease() throws Exception {
        dashboardURL = dashboard.getUrl();
        String[] filenames = new String[2];

        dashboard.openPageFromCommonTasks(btn_AddPressRelease);

        // adding new press release
        String newsPageURL = editPressRelease.addNewPressRelease(headline, date, hour, min, AMPM, filenames);
        Assert.assertNotNull(newsPageURL);

        // publishing press release
        pressReleases.publishPressRelease(headline);

        // checking press release on live site
        System.out.println("Looking for headline: " + headline);
        boolean headlineFound = pressReleases.livePressReleases(newsPageURL).canFindNewHeadline(headline, true, filenames);
        Assert.assertTrue(headlineFound);

        // changing headline on an existing press release
        livePressRelease.dashboard(dashboardURL)
                .pressReleases()
                .editPressRelease(headline)
                .changeHeadlineTo(headlineV2);

        // publishing and checking updated press release
        pressReleases.publishPressRelease(headlineV2);
        System.out.println("Looking for headline: " + headlineV2);
        headlineFound = pressReleases.livePressReleases(newsPageURL).canFindNewHeadline(headlineV2, true, filenames);
        Assert.assertTrue(headlineFound);

        // deleting press release, and verifying it is gone
        livePressRelease
                .dashboard(dashboardURL)
                .pressReleases()
                .editPressRelease(headlineV2)
                .deletePressRelease();
        pressReleases.publishPressRelease(headlineV2);
        headlineFound = pressReleases
                .livePressReleases(newsPageURL)
                .canFindNewHeadline(headlineV2, false, filenames);
        Assert.assertFalse(headlineFound);
    }

    @AfterTest
    public void tearDown() {
        livePressRelease.dashboard(dashboardURL);
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
