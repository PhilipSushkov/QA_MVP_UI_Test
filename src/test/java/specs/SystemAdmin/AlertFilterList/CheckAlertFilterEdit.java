package specs.SystemAdmin.AlertFilterList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.SystemAdmin.AlertFilterList.AlertFilterEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2016-12-19.
 */

public class CheckAlertFilterEdit extends AbstractSpec {
    private static By systemAdminMenuButton, alertFilterListMenuItem, addNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static AlertFilterEdit alertFilterEdit;

    @BeforeTest
    public void setUp() throws Exception {
        systemAdminMenuButton = By.xpath(propUISystemAdmin.getProperty("btnMenu_SystemAdmin"));
        alertFilterListMenuItem = By.xpath(propUISystemAdmin.getProperty("itemMenu_AlertFilterList"));
        addNewLink = By.xpath(propUISystemAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        alertFilterEdit = new AlertFilterEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(systemAdminMenuButton, alertFilterListMenuItem, addNewLink);
    }

    @Test
    public void checkAlertFilterEdit() throws Exception {
        final String expectedTitle = "Alert Filter Edit";

        Assert.assertNotNull(alertFilterEdit.getUrl());
        Assert.assertEquals(alertFilterEdit.getTitle(), expectedTitle, "Actual Alert Filter Edit page Title doesn't match to expected");

        Assert.assertNotNull(alertFilterEdit.getFilterNameInput(), "Filter Name field doesn't exist");
        Assert.assertNotNull(alertFilterEdit.getSaveButton(), "Save Button doesn't exist");

    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}