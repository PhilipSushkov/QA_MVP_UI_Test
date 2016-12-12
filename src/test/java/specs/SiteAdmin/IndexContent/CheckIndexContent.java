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
    private static By siteAdminMenuButton, indexContentMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static IndexContent indexContent;

    @Before
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        indexContentMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_IndexContent"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        indexContent = new IndexContent(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkIndexContent() throws Exception {
        final String expectedTitle = "Index Content";
        final Integer expectedQuantity = 20;

        dashboard.openPageFromMenu(siteAdminMenuButton, indexContentMenuItem);

        Assert.assertNotNull(indexContent.getUrl());
        Assert.assertEquals("Actual Index Content page Title doesn't match to expected", expectedTitle, indexContent.getTitle());

        //System.out.println(new IndexContent(driver).getIndexContentQuantity().toString());
        Assert.assertTrue("Actual Index Quantity is less than expected: "+expectedQuantity, expectedQuantity == indexContent.getIndexContentQuantity() );
        Assert.assertNotNull("Index Content Pagination doesn't exist", indexContent.getIndexContentPagination() );

    }

    @After
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
