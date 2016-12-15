package specs.SystemAdmin.SiteList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;
import pageobjects.SystemAdmin.SiteList.SiteList;

/**
 * Created by philipsushkov on 2016-11-14.
 */

public class CheckSiteList extends AbstractSpec {
    private static By systemAdminMenuButton, siteListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static SiteList siteList;

    @BeforeTest
    public void setUp() throws Exception {
        systemAdminMenuButton = By.xpath(propUISystemAdmin.getProperty("btnMenu_SystemAdmin"));
        siteListMenuItem = By.xpath(propUISystemAdmin.getProperty("itemMenu_SiteList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        siteList = new SiteList(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkSiteList() throws Exception {
        final String expectedTitle = "Site List";
        final Integer expectedQuantity = 1;

        dashboard.openPageFromMenu(systemAdminMenuButton, siteListMenuItem);

        Assert.assertNotNull(siteList.getUrl());
        Assert.assertEquals(siteList.getTitle(), expectedTitle, "Actual Site List page Title doesn't match to expected");

        //System.out.println(new SiteList(driver).getSiteListHeader().toString());
        Assert.assertTrue(expectedQuantity <= siteList.getSiteListQuantity(), "Actual Site Quantity is less than expected: "+expectedQuantity);
    }

    @Test
    public void checkSiteList2() throws Exception {
        final String expectedTitle = "Site List";
        final Integer expectedQuantity = 1;

        dashboard.openPageFromMenu(systemAdminMenuButton, siteListMenuItem);

        Assert.assertNotNull(siteList.getUrl());
        Assert.assertEquals(siteList.getTitle(), expectedTitle, "Actual Site List page Title doesn't match to expected");

        //System.out.println(new SiteList(driver).getSiteListHeader().toString());
        Assert.assertTrue(expectedQuantity <= siteList.getSiteListQuantity(), "Actual Site Quantity is less than expected: "+expectedQuantity);
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
