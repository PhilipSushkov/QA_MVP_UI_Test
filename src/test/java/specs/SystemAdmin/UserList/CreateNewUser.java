package specs.SystemAdmin.UserList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SystemAdmin.UserList.UserEdit;
import pageobjects.SystemAdmin.UserList.UserList;
import specs.AbstractSpec;

import java.util.Date;

public class CreateNewUser extends AbstractSpec {
    private static By systemAdminMenuButton, userListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static UserList userList;
    private static UserEdit userEdit;

    @BeforeTest
    public void setUp() throws Exception {
        systemAdminMenuButton = By.xpath(propUISystemAdmin.getProperty("btnMenu_SystemAdmin"));
        userListMenuItem = By.xpath(propUISystemAdmin.getProperty("itemMenu_UserList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        userList = new UserList(driver);
        userEdit = new UserEdit(driver);

        loginPage.loginUser();
    }

    @Test
    public void canAddEditAndDeleteUser() throws Exception {
        final String expectedTitle = "User List";
        final String expectedEditTitle = "User Edit";
        final String username = "quick-test-" + (new Date().getTime()/1000); //adds Unix timestamp to username
        final String password = "q4pass1234!";

        dashboard.openPageFromMenu(systemAdminMenuButton, userListMenuItem);
        Assert.assertEquals(userList.getTitle(), expectedTitle, "Actual User List page Title doesn't match to expected");

        //check that username does not exist
        Assert.assertEquals(userList.getIndexOfUsername(username), -1, "Username "+username+" already exists");

        //click on "Add New" and check that "User Edit" page opens
        Assert.assertEquals(userList.addNewUser().getTitle(), expectedEditTitle, "'User Edit' page is not opened after clicking 'Add New'");

        //create new user with email, username, password, "System Administrator" role and save
        userEdit.fillNewUser(username, password).saveUser();

        //check that you are returned to user list
        Assert.assertEquals(userList.getTitle(), expectedTitle, "'User List' page is not opened after clicking 'Save' when adding new user");

        //check that quick-test appears on user list
        int index = userList.getIndexOfUsername(username);
        Assert.assertNotEquals(index, -1, "Username "+username+" does not exist after being created");

        //check that quick-test is identified as being active
        Assert.assertTrue(userList.userIsActive(index), "User is not identified as being active after being created.");

        //check that you can login to user
        Assert.assertTrue(userList.logoutFromAdmin().credentialsWork(username, password), "Cannot login with newly created user.");
        loginPage.loginUser().openPageFromMenu(systemAdminMenuButton, userListMenuItem); // returning to user list page

        //click on edit icon and check that "User Edit" page opens
        Assert.assertEquals(userList.editUser(index).getTitle(), expectedEditTitle, "'User Edit' page is not opened after clicking edit button");

        //deselect active checkbox and save
        userEdit.deactivateUser().saveUser();

        //check that you are returned to user list
        Assert.assertEquals(userList.getTitle(), expectedTitle, "'User List' page is not opened after clicking 'Save' when editing user");

        //check that quick-test appears on user list
        index = userList.getIndexOfUsername(username);
        Assert.assertNotEquals(index, -1, "Username "+username+" does not exist after being edited");

        //check that quick-test is identified as not being active
        Assert.assertFalse(userList.userIsActive(index), "User is identified as being active after being made inactive.");

        //check that you cannot login to user
        Assert.assertFalse(userList.logoutFromAdmin().credentialsWork(username, password), "Can login with deactivated user.");
        loginPage.loginUser().openPageFromMenu(systemAdminMenuButton, userListMenuItem);

        //click on edit icon and check that "User Edit" page opens
        Assert.assertEquals(userList.editUser(index).getTitle(), expectedEditTitle, "'User Edit' page is not opened after clicking edit button (2nd time editing)");

        //reactivate user and save
        userEdit.reactivateUser().saveUser();

        //check that you can login to user
        Assert.assertTrue(userList.logoutFromAdmin().credentialsWork(username, password), "Cannot login with reactivated user.");
        loginPage.loginUser().openPageFromMenu(systemAdminMenuButton, userListMenuItem);

        //click on edit icon and check that "User Edit" page opens
        Assert.assertEquals(userList.editUser(index).getTitle(), expectedEditTitle, "'User Edit' page is not opened after clicking edit button (3rd time editing)");

        //click on delete
        userEdit.deleteUser();

        //check that you are returned to user list
        Assert.assertEquals(userList.getTitle(), expectedTitle, "'User List' page is not opened after clicking 'Delete'");

        //check that quick-test does not appear on user list
        Assert.assertEquals(userList.getIndexOfUsername(username), -1, "Deleted username "+username+" still exists");

        //check that you cannot login to user
        Assert.assertFalse(userList.logoutFromAdmin().credentialsWork(username, password), "Known issue - WEB-10649 - Can login with deleted user.");

    }

    @AfterTest
    public void tearDown() {
        //dashboard.logout();
        //driver.quit();
    }

}
