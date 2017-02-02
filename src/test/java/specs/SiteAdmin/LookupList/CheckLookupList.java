package specs.SiteAdmin.LookupList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SiteAdmin.LookupList.LookupList;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-11-21.
 */

public class CheckLookupList extends AbstractSpec {
    private static By siteAdminMenuButton, lookupListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static LookupList lookupList;

    @BeforeTest
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        lookupListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_LookupList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        lookupList = new LookupList(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkLookupList() throws Exception {
        final String expectedTitle = "Lookup List";
        final Integer expectedQuantity = 150;

        dashboard.openPageFromMenu(siteAdminMenuButton, lookupListMenuItem);

        Assert.assertNotNull(lookupList.getUrl());
        Assert.assertEquals(lookupList.getTitle(), expectedTitle, "Actual Lookup List page Title doesn't match to expected");

        Assert.assertNotNull(lookupList.getLookupListLookupType(), "Lookup Type dropdown list doesn't exist");
        //System.out.println(new LookupList(driver).getLookupListQuantity().toString());
        Assert.assertTrue(expectedQuantity <= lookupList.getLookupListQuantity(), "Actual Lookup Text Quantity is less than expected: "+expectedQuantity);

    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
