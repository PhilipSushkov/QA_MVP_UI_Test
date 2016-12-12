package specs.SystemAdmin.UserList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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

    @Before
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
        final String username = "quick-test-" + (new Date().getTime()/1000);
        final String password = "q4pass1234!";

        dashboard.openPageFromMenu(systemAdminMenuButton, userListMenuItem);
        Assert.assertEquals("Actual User List page Title doesn't match to expected", expectedTitle, userList.getTitle());

        //check that username quick-test does not exist
        Assert.assertEquals("Username "+username+" already exists", -1, userList.getIndexOfUsername(username));

        //click on "Add New" and check that "User Edit" page opens
        Assert.assertEquals("'User Edit' page is not opened after clicking 'Add New'", expectedEditTitle, userList.addNewUser().getTitle());

        //add quicktest user with email, username, password, "System Administrator" role and save
        userEdit.fillNewUser(username, password).saveUser();

        //check that you are returned to user list
        Assert.assertEquals("'User List' page is not opened after clicking 'Save' when adding new user", expectedTitle, userList.getTitle());

        //check that quick-test appears on user list
        int index = userList.getIndexOfUsername(username);
        Assert.assertNotEquals("Username "+username+" does not exist after being created", -1, index);

        //check that quick-test is identified as being active
        Assert.assertTrue("User is not identified as being active after being created.", userList.userIsActive(index));

        //check that you can login to user
        Assert.assertTrue("Cannot login with newly created user.", userList.logoutFromAdmin().credentialsWork(username, password));
        loginPage.loginUser().openPageFromMenu(systemAdminMenuButton, userListMenuItem);

        //click on edit icon and check that "User Edit" page opens
        Assert.assertEquals("'User Edit' page is not opened after clicking edit button", expectedEditTitle, userList.editUser(index).getTitle());
        //deselect active checkbox and save
        userEdit.deactivateUser().saveUser();
        //check that you are returned to user list
        Assert.assertEquals("'User List' page is not opened after clicking 'Save' when editing user", expectedTitle, userList.getTitle());
        //check that quick-test appears on user list
        index = userList.getIndexOfUsername(username);
        Assert.assertNotEquals("Username "+username+" does not exist after being edited", -1, index);
        //check that quick-test is identified as not being active
        Assert.assertFalse("User is identified as being active after being made inactive.", userList.userIsActive(index));

        //check that you cannot login to user
        Assert.assertFalse("Can login with deactivated user.", userList.logoutFromAdmin().credentialsWork(username, password));
        loginPage.loginUser().openPageFromMenu(systemAdminMenuButton, userListMenuItem);

        //click on edit icon and check that "User Edit" page opens
        Assert.assertEquals("'User Edit' page is not opened after clicking edit button (2nd time editing)", expectedEditTitle, userList.editUser(index).getTitle());
        //reactivate user and save
        userEdit.reactivateUser().saveUser();

        //check that you can login to user
        Assert.assertTrue("Cannot login with reactivated user.", userList.logoutFromAdmin().credentialsWork(username, password));
        loginPage.loginUser().openPageFromMenu(systemAdminMenuButton, userListMenuItem);

        //click on edit icon and check that "User Edit" page opens
        Assert.assertEquals("'User Edit' page is not opened after clicking edit button (2nd time editing)", expectedEditTitle, userList.editUser(index).getTitle());

        //click on delete
        userEdit.deleteUser();

        //check that you are returned to user list
        Assert.assertEquals("'User List' page is not opened after clicking 'Delete'", expectedTitle, userList.getTitle());

        //check that quick-test does not appear on user list
        Assert.assertEquals("Deleted username "+username+" still exists", -1, userList.getIndexOfUsername(username));

        //check that you cannot login to user
        Assert.assertFalse("Known issue - WEB-10649 - Can login with deleted user.", userList.logoutFromAdmin().credentialsWork(username, password));

    }

    @After
    public void tearDown() {
        //dashboard.logout();
        //driver.quit();
    }

}
