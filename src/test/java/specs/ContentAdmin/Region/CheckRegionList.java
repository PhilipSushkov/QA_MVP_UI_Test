package specs.ContentAdmin.Region;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.ContentAdmin.Region.RegionList;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2017-01-06.
 */

public class CheckRegionList extends AbstractSpec {
    private static By contentAdminMenuButton, regionListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static RegionList regionList;

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        regionListMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_RegionList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        regionList = new RegionList(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkRegionList() throws Exception {
        final String expectedTitle = "Region List";
        final Integer expectedQuantity = 0;

        dashboard.openPageFromMenu(contentAdminMenuButton, regionListMenuItem);

        Assert.assertNotNull(regionList.getUrl());
        Assert.assertEquals(regionList.getTitle(), expectedTitle, "Actual Region List page Title doesn't match to expected");

        //System.out.println(regionList.getTitleQuantity().toString());
        Assert.assertTrue(expectedQuantity <= regionList.getTitleQuantity(), "Actual Region Name Quantity is less than expected: "+expectedQuantity);
        //Assert.assertNotNull(regionList.getRegionListPagination(), "Region List Pagination doesn't exist");
        Assert.assertNotNull(regionList.getFilterByTag(), "Filter By Tag field doesn't exist");

    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
