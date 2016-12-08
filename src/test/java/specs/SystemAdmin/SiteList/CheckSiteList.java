package specs.SystemAdmin.SiteList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.openqa.selenium.By;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;
import pageobjects.SystemAdmin.SiteList.SiteList;

/**
 * Created by philipsushkov on 2016-11-14.
 */

public class CheckSiteList extends AbstractSpec {
    final By systemAdminMenuButton = By.xpath("//span[contains(text(),'System Admin')]");
    final By siteListMenuItem = By.xpath("//a[contains(text(),'Site List')]/parent::li");

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkSiteList() throws Exception {
        final String expectedTitle = "Site List";
        final Integer expectedQuantity = 1;

        new Dashboard(driver).openPageFromMenu(systemAdminMenuButton, siteListMenuItem);

        Assert.assertNotNull(new SiteList(driver).getUrl());
        Assert.assertEquals("Actual Site List page Title doesn't match to expected", expectedTitle, new SiteList(driver).getTitle());

        //System.out.println(new SiteList(driver).getSiteListHeader().toString());
        Assert.assertTrue("Actual Site Quantity is less than expected: "+expectedQuantity, expectedQuantity <= new SiteList(driver).getSiteListQuantity() );
    }

    @After
    public void tearDown() {
        new Dashboard(driver).logout();
        //driver.quit();
    }

}
