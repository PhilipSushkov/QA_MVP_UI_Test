package specs.SiteAdmin.LayoutDefinitionList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.openqa.selenium.By;
import pageobjects.SiteAdmin.LayoutDefinitionList.LayoutDefinitionList;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2016-11-15.
 */

public class CheckLayoutDefinitionList extends AbstractSpec {
    private final By siteAdminMenuButton = By.xpath("//span[contains(text(),'Site Admin')]");
    private final By layoutDefinitionListMenuItem = By.xpath("//a[contains(text(),'Layout Definition List')]/parent::li");

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkLayoutDefinitionList() throws Exception {
        final String expectedTitle = "Layout Definition List";
        final Integer expectedQuantity = 3;

        new Dashboard(driver).openPageFromMenu(siteAdminMenuButton, layoutDefinitionListMenuItem);

        Assert.assertNotNull(new LayoutDefinitionList(driver).getUrl());
        Assert.assertEquals("Actual Layout Definition List page Title doesn't match to expected", expectedTitle, new LayoutDefinitionList(driver).getTitle());

        //System.out.println(new LayoutDefinitionList(driver).getLayoutDefinitionListQuantity().toString());
        Assert.assertTrue("Actual Layout Definition Quantity is less than expected: "+expectedQuantity, expectedQuantity == new LayoutDefinitionList(driver).getLayoutDefinitionListQuantity() );
        Assert.assertNotNull("Layout Definition Pagination doesn't exist", new LayoutDefinitionList(driver).getLayoutDefinitionPagination() );

    }

    @After
    public void tearDown() {
        new Dashboard(driver).logout();
        //driver.quit();
    }

}
