package specs.SystemAdmin.UserList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.SystemAdmin.UserList.UserEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2016-12-19.
 */

public class CheckUserEdit extends AbstractSpec {
    private static By systemAdminMenuButton, userListMenuItem, userAddNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static UserEdit userEdit;

    @BeforeTest
    public void setUp() throws Exception {
        systemAdminMenuButton = By.xpath(propUISystemAdmin.getProperty("btnMenu_SystemAdmin"));
        userListMenuItem = By.xpath(propUISystemAdmin.getProperty("itemMenu_UserList"));
        userAddNewLink = By.xpath(propUISystemAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        userEdit = new UserEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(systemAdminMenuButton, userListMenuItem, userAddNewLink);
    }

    @Test
    public void checkUserEdit() throws Exception {
        final String expectedTitle = "User Edit";

        Assert.assertNotNull(userEdit.getUrl());
        Assert.assertEquals(userEdit.getTitle(), expectedTitle, "Actual User Edit page Title doesn't match to expected");

        Assert.assertNotNull(userEdit.getEmailInput(), "Email Input doesn't exist");
        Assert.assertNotNull(userEdit.getSaveButton(), "Save Button doesn't exist");

    }

    @Test(enabled=false)
    public void createUsers() throws Exception {
        Assert.assertNotNull(userEdit.createUsers());
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
