package specs.SystemAdmin.SiteMaintenance;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import pageobjects.SystemAdmin.SiteMaintenance.SiteMaintenance;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2016-11-14.
 */

public class CheckSiteMaintenance extends AbstractSpec {

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkSiteMaintenance() throws Exception {
        final String expectedTitle = "Site Maintenance";

        Assert.assertNotNull(new Dashboard(driver).openSiteMaintenancePage().getUrl());

        Assert.assertEquals("Actual PDF Template Edit page Title doesn't match to expected", expectedTitle, new SiteMaintenance(driver).getTitle());

        //System.out.println(new SiteMaintenance(driver).getGoLiveButton().getText() );
        Assert.assertNotNull(new SiteMaintenance(driver).getGoLiveButton() );
        Assert.assertNotNull(new SiteMaintenance(driver).getOneTouchButton() );
        Assert.assertNotNull(new SiteMaintenance(driver).getTwoFactorAuthenticationButton() );

    }

    @After
    public void tearDown() {
        driver.quit();
    }

}
