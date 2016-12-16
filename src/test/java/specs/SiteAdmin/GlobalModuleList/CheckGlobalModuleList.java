package specs.SiteAdmin.GlobalModuleList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.SiteAdmin.GlobalModuleList.GlobalModuleList;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2016-11-15.
 */

public class CheckGlobalModuleList extends AbstractSpec {
    private static By siteAdminMenuButton, globalModuleListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static GlobalModuleList globalModuleList;

    @BeforeTest
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        globalModuleListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_GlobalModuleList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        globalModuleList = new GlobalModuleList(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkGlobalModuleList() throws Exception {
        final String expectedTitle = "Global Module List";
        final Integer expectedQuantity = 10;

        dashboard.openPageFromMenu(siteAdminMenuButton, globalModuleListMenuItem);

        Assert.assertNotNull(globalModuleList.getUrl());
        Assert.assertEquals(globalModuleList.getTitle(), expectedTitle, "Actual Global Module List page Title doesn't match to expected");

        //System.out.println(new GlobalModuleList(driver).getGlobalModuleListQuantity().toString());
        Assert.assertTrue(expectedQuantity <= globalModuleList.getGlobalModuleListQuantity(), "Actual Module Name Quantity is less than expected: "+expectedQuantity);

    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
