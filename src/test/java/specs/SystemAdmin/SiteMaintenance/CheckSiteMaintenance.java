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

    @Before
    public void setUp() throws Exception {
        systemAdminMenuButton = By.xpath(propUISystemAdmin.getProperty("btnMenu_SystemAdmin"));
        siteMaintenanceMenuItem = By.xpath(propUISystemAdmin.getProperty("itemMenu_SiteMaintenance"));

        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkSiteMaintenance() throws Exception {
        final String expectedTitle = "Site Maintenance";

        new Dashboard(driver).openPageFromMenu(systemAdminMenuButton, siteMaintenanceMenuItem);

        Assert.assertNotNull(new SiteMaintenance(driver).getUrl());

        Assert.assertEquals("Actual PDF Template Edit page Title doesn't match to expected", expectedTitle, new SiteMaintenance(driver).getTitle());

        //System.out.println(new SiteMaintenance(driver).getGoLiveButton().getText() );
        Assert.assertNotNull("Go Live Button doesn't exist", new SiteMaintenance(driver).getGoLiveButton() );
        Assert.assertNotNull("One Touch Button doesn't exist", new SiteMaintenance(driver).getOneTouchButton() );
        Assert.assertNotNull("Two Factor Authentication Button doesn't exist", new SiteMaintenance(driver).getTwoFactorAuthenticationButton() );

    }

    @After
    public void tearDown() {
        new Dashboard(driver).logout();
        //driver.quit();
    }

}
