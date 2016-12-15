package specs.SystemAdmin.AlertFilterList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;
import pageobjects.SystemAdmin.AlertFilterList.AlertFilterList;

/**
 * Created by philipsushkov on 2016-11-11.
 */

public class CheckAlertFilterList extends AbstractSpec {
    private static By systemAdminMenuButton, alertFilterListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static AlertFilterList alertFilterList;


    @BeforeTest
    public void setUp() throws Exception {
        systemAdminMenuButton = By.xpath(propUISystemAdmin.getProperty("btnMenu_SystemAdmin"));
        alertFilterListMenuItem = By.xpath(propUISystemAdmin.getProperty("itemMenu_AlertFilterList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        alertFilterList = new AlertFilterList(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkAlertFilterList() throws Exception {
        final String expectedTitle = "Alert Filter List";
        final Integer expectedQuantity = 4;

        dashboard.openPageFromMenu(systemAdminMenuButton, alertFilterListMenuItem);

        Assert.assertNotNull(alertFilterList.getUrl());
        Assert.assertEquals(alertFilterList.getTitle(), expectedTitle, "Actual Alert Filter List page Title doesn't match to expected");

        //System.out.println(new AlertFilterList(driver).getFilterNameQuantity().toString());
        Assert.assertTrue(expectedQuantity <= alertFilterList.getFilterNameQuantity(), "Actual Filter Name Quantity is less than expected: "+expectedQuantity);
    }

    @Test
    public void checkAlertFilterList2() throws Exception {
        final String expectedTitle = "Alert Filter List";
        final Integer expectedQuantity = 4;

        dashboard.openPageFromMenu(systemAdminMenuButton, alertFilterListMenuItem);

        Assert.assertNotNull(alertFilterList.getUrl());
        Assert.assertEquals(alertFilterList.getTitle(), expectedTitle, "Actual Alert Filter List page Title doesn't match to expected");

        //System.out.println(new AlertFilterList(driver).getFilterNameQuantity().toString());
        Assert.assertTrue(expectedQuantity <= alertFilterList.getFilterNameQuantity(), "Actual Filter Name Quantity is less than expected: "+expectedQuantity);
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }
}
