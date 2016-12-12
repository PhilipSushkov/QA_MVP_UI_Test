package specs.SystemAdmin.AlertFilterList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
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


    @Before
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
        Assert.assertEquals("Actual Alert Filter List page Title doesn't match to expected", expectedTitle, alertFilterList.getTitle());

        //System.out.println(new AlertFilterList(driver).getFilterNameQuantity().toString());
        Assert.assertTrue("Actual Filter Name Quantity is less than expected: "+expectedQuantity, expectedQuantity <= alertFilterList.getFilterNameQuantity() );
    }

    @After
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }
}
