package specs.SystemAdmin.UserGroupList;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.SystemAdmin.UserGroupList.UserGroupEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2016-12-19.
 */

public class CheckUserGroupEdit extends AbstractSpec {
    private static By systemAdminMenuButton, userGroupListMenuItem, userAddNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static UserGroupEdit userGroupEdit;

    @BeforeTest
    public void setUp() throws Exception {
        systemAdminMenuButton = By.xpath(propUISystemAdmin.getProperty("btnMenu_SystemAdmin"));
        userGroupListMenuItem = By.xpath(propUISystemAdmin.getProperty("itemMenu_UserGroupList"));
        userAddNewLink = By.xpath(propUISystemAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        userGroupEdit = new UserGroupEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(systemAdminMenuButton, userGroupListMenuItem, userAddNewLink);
    }

    @Test
    public void checkUserGroupEdit() throws Exception {
        final String expectedTitle = "User Group Edit";

        Assert.assertNotNull(userGroupEdit.getUrl());
        Assert.assertEquals(userGroupEdit.getTitle(), expectedTitle, "Actual User Group Edit page Title doesn't match to expected");

        Assert.assertNotNull(userGroupEdit.getUserGroupNameInput(), "User Group Name field doesn't exist");
        Assert.assertNotNull(userGroupEdit.getCopyPermissionsFromSelect(), "Copy Permissions From select doesn't exist");
        Assert.assertNotNull(userGroupEdit.getSaveButton(), "Save Button doesn't exist");

    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }
}
