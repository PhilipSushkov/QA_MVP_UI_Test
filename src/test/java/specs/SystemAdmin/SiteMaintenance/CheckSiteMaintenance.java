package specs.SystemAdmin.SiteMaintenance;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.SystemAdmin.SiteMaintenance.SiteMaintenance;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2016-11-14.
 */

public class CheckSiteMaintenance extends AbstractSpec {
    private static By systemAdminMenuButton, siteMaintenanceMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static SiteMaintenance siteMaintenance;
    private static final long DEFAULT_PAUSE = 7500;

    @BeforeTest
    public void setUp() throws Exception {
        systemAdminMenuButton = By.xpath(propUISystemAdmin.getProperty("btnMenu_SystemAdmin"));
        siteMaintenanceMenuItem = By.xpath(propUISystemAdmin.getProperty("itemMenu_SiteMaintenance"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        siteMaintenance = new SiteMaintenance(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkSiteMaintenance() throws Exception {
        final String expectedTitle = "Site Maintenance";

        dashboard.openPageFromMenu(systemAdminMenuButton, siteMaintenanceMenuItem);

        Thread.sleep(DEFAULT_PAUSE);

        Assert.assertNotNull(siteMaintenance.getUrl());

        Assert.assertEquals(siteMaintenance.getTitle(), expectedTitle, "Actual PDF Template Edit page Title doesn't match to expected");

        //System.out.println(new SiteMaintenance(driver).getGoLiveButton().getText() );

        //Website Status
        Assert.assertNotNull(siteMaintenance.getGoLiveButton(), "Go Live Button doesn't exist");

        // Press Release Publishing via Login
        Assert.assertNotNull(siteMaintenance.getOneTouchButton(), "One Touch Button doesn't exist");
        Assert.assertTrue(siteMaintenance.getPressReleasePublishingStatus(), "Press Release Publishing status value doesn't correct");

        // Two-Factor Authentication
        Assert.assertNotNull(siteMaintenance.getTwoFactorAuthenticationButton(), "Two Factor Authentication Button doesn't exist");
        Assert.assertTrue(siteMaintenance.getTwoFactorAuthenticationStatus(), "Two Factor Authentication status value doesn't correct");

        // Site Security Settings
        Assert.assertNotNull(siteMaintenance.getIFramesButton(), "IFrames Button doesn't exist");
        Assert.assertTrue(siteMaintenance.getIFramesStatus(), "iFrames status value doesn't correct");

        // Password Settings
        Assert.assertNotNull(siteMaintenance.getPasswordReuseLimitList(), "Password Reuse Limit drop down list doesn't exist");
        Assert.assertNotNull(siteMaintenance.getPasswordLimitUpdateBtn(), "Password Limit Update button doesn't exist");
        Assert.assertTrue(siteMaintenance.getPasswordReuseLimit(), "Password Reuse Limit value doesn't correct");

        // Mail: SendGrid
        Assert.assertTrue(siteMaintenance.getSendGridStatus(), "SendGrid status value doesn't correct");
        Assert.assertNotNull(siteMaintenance.getSendGridBtn(), "SendGrid status button doesn't exist");
        Assert.assertNotNull(siteMaintenance.getSendGridAPIKeyInp(), "SendGrid API Key input doesn't exist");
        Assert.assertNotNull(siteMaintenance.getUpdateApiBtn(), "Update API Key button doesn't exist");

        // PR Newswire image resize
        Assert.assertTrue(siteMaintenance.getNewswireImageResizeStatus(), "Newswire Image Resize status value doesn't correct");
        Assert.assertNotNull(siteMaintenance.getNewswireImageResizeBtn(), "Newswire Image Resize status button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
