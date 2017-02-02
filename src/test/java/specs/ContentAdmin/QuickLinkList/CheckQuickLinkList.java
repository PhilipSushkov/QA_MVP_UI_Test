package specs.ContentAdmin.QuickLinkList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.ContentAdmin.QuickLinkList.QuickLinks;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-12-02.
 */

public class CheckQuickLinkList extends AbstractSpec {
    private static By contentAdminMenuButton, quickLinksMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static QuickLinks quickLinks;

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        quickLinksMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_QuickLinks"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        quickLinks = new QuickLinks(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkQuickLinkList() throws Exception {
        final String expectedTitle = "Quick Link List";
        final Integer expectedQuantity = 4;

        dashboard.openPageFromMenu(contentAdminMenuButton, quickLinksMenuItem);

        Assert.assertNotNull(quickLinks.getUrl());
        Assert.assertEquals(quickLinks.getTitle(), expectedTitle, "Actual Quick Link List page Title doesn't match to expected");

        //System.out.println(new FinancialReports(driver).getTitleQuantity().toString());
        Assert.assertTrue(expectedQuantity <= quickLinks.getTitleQuantity(), "Actual Quick Link Description Quantity is less than expected: "+expectedQuantity);
        Assert.assertNotNull(quickLinks.getQuickLinksPagination(), "Quick Link Pagination doesn't exist");
        Assert.assertNotNull(quickLinks.getFilterByTag(), "Filter By Tag field doesn't exist");

    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
