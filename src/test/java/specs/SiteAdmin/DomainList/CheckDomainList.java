package specs.SiteAdmin.DomainList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SiteAdmin.DomainList.DomainList;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-11-23.
 */

public class CheckDomainList extends AbstractSpec {
    private final By siteAdminMenuButton = By.xpath("//span[contains(text(),'Site Admin')]");
    private final By domainListMenuItem = By.xpath("//a[contains(text(),'Domain List')]/parent::li");

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkDomainList() throws Exception {
        final String expectedTitle = "Domain List";
        final Integer expectedQuantity = 2;

        new Dashboard(driver).openPageFromMenu(siteAdminMenuButton, domainListMenuItem);

        Assert.assertNotNull(new DomainList(driver).getUrl());
        Assert.assertEquals("Actual Domain List page Title doesn't match to expected", expectedTitle, new DomainList(driver).getTitle());

        //System.out.println(new DomainList(driver).getDomainQuantity().toString());
        Assert.assertTrue("Actual Domain Quantity is less than expected: "+expectedQuantity, expectedQuantity <= new DomainList(driver).getDomainQuantity() );
        Assert.assertNotNull("Public Site Link doesn't exist", new DomainList(driver).getHrefPublicSite() );

    }

    @After
    public void tearDown() {
        new Dashboard(driver).logout();
        //driver.quit();
    }

}
