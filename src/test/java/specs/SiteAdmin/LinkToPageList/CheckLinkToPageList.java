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
    private final By siteAdminMenuButton = By.xpath("//span[contains(text(),'Site Admin')]");
    private final By linkToPageListMenuItem = By.xpath("//a[contains(text(),'Link To Page List')]/parent::li");

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkLinkToPageList() throws Exception {
        final String expectedTitle = "Link To Page List";
        final Integer expectedQuantity = 15;

        new Dashboard(driver).openPageFromMenu(siteAdminMenuButton, linkToPageListMenuItem);

        Assert.assertNotNull(new LinkToPageList(driver).getUrl());
        Assert.assertEquals("Actual Link To Page List page Title doesn't match to expected", expectedTitle, new LinkToPageList(driver).getTitle());

        //System.out.println(new LinkToPageList(driver).getLinkToPageListQuantity().toString());
        Assert.assertTrue("Actual Link To Page Quantity is less than expected: "+expectedQuantity, expectedQuantity == new LinkToPageList(driver).getLinkToPageListQuantity() );
        Assert.assertNotNull("Link To Page Pagination doesn't exist", new LinkToPageList(driver).getLinkToPageListPagination() );

    }

    @After
    public void tearDown() {
        new Dashboard(driver).logout();
        //driver.quit();
    }

}
