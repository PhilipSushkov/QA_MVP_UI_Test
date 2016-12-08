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
    private final By contentAdminMenuButton = By.xpath("//span[contains(text(),'Content Admin')]");
    private final By quickLinksMenuItem = By.xpath("//a[contains(text(),'Quick Links')]/parent::li");

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkQuickLinkList() throws Exception {
        final String expectedTitle = "Quick Link List";
        final Integer expectedQuantity = 4;

        new Dashboard(driver).openPageFromMenu(contentAdminMenuButton, quickLinksMenuItem);

        Assert.assertNotNull(new QuickLinks(driver).getUrl());
        Assert.assertEquals("Actual Quick Link List page Title doesn't match to expected", expectedTitle, new QuickLinks(driver).getTitle());

        //System.out.println(new FinancialReports(driver).getTitleQuantity().toString());
        Assert.assertTrue("Actual Quick Link Description Quantity is less than expected: "+expectedQuantity, expectedQuantity <= new QuickLinks(driver).getTitleQuantity() );
        Assert.assertNotNull("Quick Link Pagination doesn't exist", new QuickLinks(driver).getQuickLinksPagination() );
        Assert.assertNotNull("Filter By Tag field doesn't exist", new QuickLinks(driver).getFilterByTag() );

    }

    @After
    public void tearDown() {
        new Dashboard(driver).logout();
        //driver.quit();
    }

}
