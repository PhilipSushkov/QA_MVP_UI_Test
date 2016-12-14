package specs.SiteAdmin.LinkToPageList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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

    @Before
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
        Assert.assertEquals("Actual Link To Page List page Title doesn't match to expected", expectedTitle, linkToPageList.getTitle());

        //System.out.println(new LinkToPageList(driver).getLinkToPageListQuantity().toString());
        Assert.assertTrue("Actual Link To Page Quantity is less than expected: "+expectedQuantity, expectedQuantity == linkToPageList.getLinkToPageListQuantity() );
        Assert.assertNotNull("Link To Page Pagination doesn't exist", linkToPageList.getLinkToPageListPagination() );

    }

    @After
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
