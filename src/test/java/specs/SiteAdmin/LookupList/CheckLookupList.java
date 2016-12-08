package specs.SiteAdmin.LookupList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SiteAdmin.LookupList.LookupList;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-11-21.
 */

public class CheckLookupList extends AbstractSpec {
    private final By siteAdminMenuButton = By.xpath("//span[contains(text(),'Site Admin')]");
    private final By lookupListMenuItem = By.xpath("//a[contains(text(),'Lookup List')]/parent::li");

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkLookupList() throws Exception {
        final String expectedTitle = "Lookup List";
        final Integer expectedQuantity = 150;

        new Dashboard(driver).openPageFromMenu(siteAdminMenuButton, lookupListMenuItem);

        Assert.assertNotNull(new LookupList(driver).getUrl());
        Assert.assertEquals("Actual Lookup List page Title doesn't match to expected", expectedTitle, new LookupList(driver).getTitle());

        Assert.assertNotNull("Lookup Type dropdown list doesn't exist", new LookupList(driver).getLookupListLookupType() );
        //System.out.println(new LookupList(driver).getLookupListQuantity().toString());
        Assert.assertTrue("Actual Lookup Text Quantity is less than expected: "+expectedQuantity, expectedQuantity <= new LookupList(driver).getLookupListQuantity() );

    }

    @After
    public void tearDown() {
        new Dashboard(driver).logout();
        //driver.quit();
    }

}
