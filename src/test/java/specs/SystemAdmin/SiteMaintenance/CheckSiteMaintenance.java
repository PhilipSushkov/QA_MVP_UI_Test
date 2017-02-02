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

        Assert.assertNotNull(siteMaintenance.getUrl());

        Assert.assertEquals(siteMaintenance.getTitle(), expectedTitle, "Actual PDF Template Edit page Title doesn't match to expected");

        //System.out.println(new SiteMaintenance(driver).getGoLiveButton().getText() );
        Assert.assertNotNull(siteMaintenance.getGoLiveButton(), "Go Live Button doesn't exist");
        Assert.assertNotNull(siteMaintenance.getOneTouchButton(), "One Touch Button doesn't exist");
        Assert.assertNotNull(siteMaintenance.getTwoFactorAuthenticationButton(), "Two Factor Authentication Button doesn't exist");

    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
