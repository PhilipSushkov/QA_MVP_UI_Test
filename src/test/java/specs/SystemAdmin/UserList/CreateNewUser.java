package specs.SystemAdmin.UserList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SystemAdmin.UserList.UserEdit;
import pageobjects.SystemAdmin.UserList.UserList;
import specs.AbstractSpec;

import java.util.Date;

public class CreateNewUser extends AbstractSpec {

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void canAddEditAndDeleteUser() throws Exception {
        final String expectedTitle = "User List";
        final String expectedEditTitle = "User Edit";
        final String username = "quick-test-" + (new Date().getTime()/1000);
        final String password = "q4pass1234!";
        Assert.assertEquals("Actual User List page Title doesn't match to expected", expectedTitle, new Dashboard(driver).openUserListPage().getTitle());
        //check that username quick-test does not exist
        Assert.assertEquals("Username "+username+" already exists", -1, new UserList(driver).getIndexOfUsername(username));
        //click on "Add New" and check that "User Edit" page opens
        Assert.assertEquals("'User Edit' page is not opened after clicking 'Add New'", expectedEditTitle, new UserList(driver).addNewUser().getTitle());
        //add quicktest user with email, username, password, "System Administrator" role and save
        new UserEdit(driver).fillNewUser(username, password).saveUser();
        //check that you are returned to user list
        Assert.assertEquals("'User List' page is not opened after clicking 'Save' when adding new user", expectedTitle, new UserList(driver).getTitle());
        //check that quick-test appears on user list
        int index = new UserList(driver).getIndexOfUsername(username);
        Assert.assertNotEquals("Username "+username+" does not exist after being created", -1, index);
        //check that quick-test is identified as being active
        Assert.assertTrue("User is not identified as being active after being created.", new UserList(driver).userIsActive(index));

        //check that you can login to user
        Assert.assertTrue("Cannot login with newly created user.", new UserList(driver).logoutFromAdmin().credentialsWork(username, password));
        new LoginPage(driver).loginUser().openUserListPage();

        //click on edit icon and check that "User Edit" page opens
        Assert.assertEquals("'User Edit' page is not opened after clicking edit button", expectedEditTitle, new UserList(driver).editUser(index).getTitle());
        //deselect active checkbox and save
        new UserEdit(driver).deactivateUser().saveUser();
        //check that you are returned to user list
        Assert.assertEquals("'User List' page is not opened after clicking 'Save' when editing user", expectedTitle, new UserList(driver).getTitle());
        //check that quick-test appears on user list
        index = new UserList(driver).getIndexOfUsername(username);
        Assert.assertNotEquals("Username "+username+" does not exist after being edited", -1, index);
        //check that quick-test is identified as not being active
        Assert.assertFalse("User is identified as being active after being made inactive.", new UserList(driver).userIsActive(index));

        //check that you cannot login to user
        Assert.assertFalse("Can login with deactivated user.", new UserList(driver).logoutFromAdmin().credentialsWork(username, password));
        new LoginPage(driver).loginUser().openUserListPage();

        //click on edit icon and check that "User Edit" page opens
        Assert.assertEquals("'User Edit' page is not opened after clicking edit button (2nd time editing)", expectedEditTitle, new UserList(driver).editUser(index).getTitle());
        //reactivate user and save
        new UserEdit(driver).reactivateUser().saveUser();

        //check that you can login to user
        Assert.assertTrue("Cannot login with reactivated user.", new UserList(driver).logoutFromAdmin().credentialsWork(username, password));
        new LoginPage(driver).loginUser().openUserListPage();

        //click on edit icon and check that "User Edit" page opens
        Assert.assertEquals("'User Edit' page is not opened after clicking edit button (2nd time editing)", expectedEditTitle, new UserList(driver).editUser(index).getTitle());
        //click on delete
        new UserEdit(driver).deleteUser();
        //check that you are returned to user list
        Assert.assertEquals("'User List' page is not opened after clicking 'Delete'", expectedTitle, new UserList(driver).getTitle());
        //check that quick-test does not appear on user list
        Assert.assertEquals("Deleted username "+username+" still exists", -1, new UserList(driver).getIndexOfUsername(username));

        //check that you cannot login to user
        Assert.assertFalse("Known issue - WEB-10649 - Can login with deleted user.", new UserList(driver).logoutFromAdmin().credentialsWork(username, password));
    }

    @After
    public void tearDown() {
        new Dashboard(driver).logout();
        //driver.quit();
    }

}
