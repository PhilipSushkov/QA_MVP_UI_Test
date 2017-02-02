package specs.SiteAdmin.MobileLinkList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SiteAdmin.MobileLinkList.MobileLinkList;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-11-21.
 */

public class CheckMobileLinkList extends AbstractSpec {
    private static By siteAdminMenuButton, mobileLinkListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static MobileLinkList mobileLinkList;

    @BeforeTest
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        mobileLinkListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_MobileLinkList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        mobileLinkList = new MobileLinkList(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkMobileLinkList() throws Exception {

        final String expectedTitle = "Mobile Link List";
        final Integer expectedQuantity = 10;

        new Dashboard(driver).openPageFromMenu(siteAdminMenuButton, mobileLinkListMenuItem);

        Assert.assertNotNull(mobileLinkList.getUrl());
        Assert.assertEquals(mobileLinkList.getTitle(), expectedTitle, "Actual Mobile Link List page Title doesn't match to expected");

        //System.out.println(new MobileLinkList(driver).getMobileLinkListQuantity().toString());
        Assert.assertTrue(expectedQuantity <= mobileLinkList.getMobileLinkListQuantity(), "Actual Mobile Link Quantity is less than expected: "+expectedQuantity);
        Assert.assertNotNull(mobileLinkList.getMobileLinkListPagination(), "Mobile View pagination doesn't exist");

    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }
}
