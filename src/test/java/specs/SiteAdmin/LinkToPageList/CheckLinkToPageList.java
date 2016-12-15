package specs.SiteAdmin.LinkToPageList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SiteAdmin.LinkToPageList.LinkToPageList;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-11-21.
 */

public class CheckLinkToPageList extends AbstractSpec {
    private static By siteAdminMenuButton, linkToPageListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static LinkToPageList linkToPageList;

    @BeforeTest
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        linkToPageListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_LinkToPageList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        linkToPageList = new LinkToPageList(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkLinkToPageList() throws Exception {
        final String expectedTitle = "Link To Page List";
        final Integer expectedQuantity = 15;

        new Dashboard(driver).openPageFromMenu(siteAdminMenuButton, linkToPageListMenuItem);

        Assert.assertNotNull(linkToPageList.getUrl());
        Assert.assertEquals(linkToPageList.getTitle(), expectedTitle, "Actual Link To Page List page Title doesn't match to expected");

        //System.out.println(new LinkToPageList(driver).getLinkToPageListQuantity().toString());
        Assert.assertTrue(expectedQuantity == linkToPageList.getLinkToPageListQuantity(), "Actual Link To Page Quantity is less than expected: "+expectedQuantity);
        Assert.assertNotNull(linkToPageList.getLinkToPageListPagination(), "Link To Page Pagination doesn't exist");

    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
