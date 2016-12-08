package specs.SiteAdmin.IndexContent;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SiteAdmin.IndexContent.IndexContent;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-11-18.
 */

public class CheckIndexContent extends AbstractSpec {
    private final By siteAdminMenuButton = By.xpath("//span[contains(text(),'Site Admin')]");
    private final By indexContentMenuItem = By.xpath("//a[contains(text(),'Index Content')]/parent::li");

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkIndexContent() throws Exception {
        final String expectedTitle = "Index Content";
        final Integer expectedQuantity = 20;

        new Dashboard(driver).openPageFromMenu(siteAdminMenuButton, indexContentMenuItem);

        Assert.assertNotNull(new IndexContent(driver).getUrl());
        Assert.assertEquals("Actual Index Content page Title doesn't match to expected", expectedTitle, new IndexContent(driver).getTitle());

        //System.out.println(new IndexContent(driver).getIndexContentQuantity().toString());
        Assert.assertTrue("Actual Index Quantity is less than expected: "+expectedQuantity, expectedQuantity == new IndexContent(driver).getIndexContentQuantity() );
        Assert.assertNotNull("Index Content Pagination doesn't exist", new IndexContent(driver).getIndexContentPagination() );

    }

    @After
    public void tearDown() {
        new Dashboard(driver).logout();
        //driver.quit();
    }

}
