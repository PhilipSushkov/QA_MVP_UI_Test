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
    final By systemAdminMenuButton = By.xpath("//span[contains(text(),'System Admin')]");
    final By siteMaintenanceMenuItem = By.xpath("//a[contains(text(),'Site Maintenance')]/parent::li");

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkSiteMaintenance() throws Exception {
        final String expectedTitle = "Site Maintenance";

        new Dashboard(driver).openPage(systemAdminMenuButton, siteMaintenanceMenuItem);

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
