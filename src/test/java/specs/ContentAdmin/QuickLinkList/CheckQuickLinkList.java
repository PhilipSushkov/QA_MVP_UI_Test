package specs.ContentAdmin.QuickLinkList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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

    @Before
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
        Assert.assertEquals("Actual Quick Link List page Title doesn't match to expected", expectedTitle, quickLinks.getTitle());

        //System.out.println(new FinancialReports(driver).getTitleQuantity().toString());
        Assert.assertTrue("Actual Quick Link Description Quantity is less than expected: "+expectedQuantity, expectedQuantity <= quickLinks.getTitleQuantity() );
        Assert.assertNotNull("Quick Link Pagination doesn't exist", quickLinks.getQuickLinksPagination() );
        Assert.assertNotNull("Filter By Tag field doesn't exist", quickLinks.getFilterByTag() );

    }

    @After
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
