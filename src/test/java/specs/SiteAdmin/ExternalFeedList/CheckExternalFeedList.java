package specs.SiteAdmin.ExternalFeedList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.Dashboard.Dashboard;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SiteAdmin.ExternalFeedList.ExternalFeedList;

/**
 * Created by philipsushkov on 2016-11-18.
 */

public class CheckExternalFeedList extends AbstractSpec {
    private static By siteAdminMenuButton, externalFeedListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static ExternalFeedList externalFeedList;

    @BeforeTest
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        externalFeedListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_ExternalFeedList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        externalFeedList = new ExternalFeedList(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkExternalFeedList() throws Exception {

        final String expectedTitle = "External Feed List";
        final Integer expectedQuantity = 3;

        dashboard.openPageFromMenu(siteAdminMenuButton, externalFeedListMenuItem);

        Assert.assertNotNull(externalFeedList.getUrl());
        Assert.assertEquals(expectedTitle, externalFeedList.getTitle(), "Actual External Feed List page Title doesn't match to expected");

        System.out.println(externalFeedList.getExternalFeedListQuantity().toString());
        Assert.assertTrue(expectedQuantity <= externalFeedList.getExternalFeedListQuantity(), "Actual Description Quantity is less than expected: "+expectedQuantity);

    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
