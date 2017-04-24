package specs.SiteAdmin.LayoutDefinitionList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.SiteAdmin.LayoutDefinitionList.LayoutDefinitionList;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2016-11-15.
 */

public class CheckLayoutDefinitionList extends AbstractSpec {
    private static By siteAdminMenuButton, layoutDefinitionListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static LayoutDefinitionList layoutDefinitionList;

    @BeforeTest
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        layoutDefinitionListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_LayoutDefinitionList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        layoutDefinitionList = new LayoutDefinitionList(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkLayoutDefinitionList() throws Exception {
        final String expectedTitle = "Layout Definition List";
        final Integer expectedQuantity = 3;

        dashboard.openPageFromMenu(siteAdminMenuButton, layoutDefinitionListMenuItem);

        Assert.assertNotNull(layoutDefinitionList.getUrl());
        Assert.assertEquals(layoutDefinitionList.getTitle(), expectedTitle, "Actual Layout Definition List page Title doesn't match to expected");

        //System.out.println(new LayoutDefinitionList(driver).getLayoutDefinitionListQuantity().toString());
        Assert.assertTrue(expectedQuantity <= layoutDefinitionList.getLayoutDefinitionListQuantity(), "Actual Layout Definition Quantity is less than expected: "+expectedQuantity);
        Assert.assertNotNull(layoutDefinitionList.getLayoutDefinitionPagination(), "Layout Definition Pagination doesn't exist");

    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
