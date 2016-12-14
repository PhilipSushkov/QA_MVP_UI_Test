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
    private static By siteAdminMenuButton, domainListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static DomainList domainList;

    @Before
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        domainListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_DomainList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        domainList = new DomainList(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkDomainList() throws Exception {
        final String expectedTitle = "Domain List";
        final Integer expectedQuantity = 2;

        dashboard.openPageFromMenu(siteAdminMenuButton, domainListMenuItem);

        Assert.assertNotNull(domainList.getUrl());
        Assert.assertEquals("Actual Domain List page Title doesn't match to expected", expectedTitle, domainList.getTitle());

        //System.out.println(new DomainList(driver).getDomainQuantity().toString());
        Assert.assertTrue("Actual Domain Quantity is less than expected: "+expectedQuantity, expectedQuantity <= domainList.getDomainQuantity() );
        Assert.assertNotNull("Public Site Link doesn't exist", domainList.getHrefPublicSite() );

    }

    @After
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
