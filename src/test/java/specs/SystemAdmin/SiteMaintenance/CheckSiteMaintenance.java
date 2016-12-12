package specs.SystemAdmin.SiteMaintenance;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
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

    @Before
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

        Assert.assertEquals("Actual PDF Template Edit page Title doesn't match to expected", expectedTitle, siteMaintenance.getTitle());

        //System.out.println(new SiteMaintenance(driver).getGoLiveButton().getText() );
        Assert.assertNotNull("Go Live Button doesn't exist", siteMaintenance.getGoLiveButton() );
        Assert.assertNotNull("One Touch Button doesn't exist", siteMaintenance.getOneTouchButton() );
        Assert.assertNotNull("Two Factor Authentication Button doesn't exist", siteMaintenance.getTwoFactorAuthenticationButton() );

    }

    @After
    public void tearDown() {
        dashboard.logout();
        //driver.quit();
    }

}
