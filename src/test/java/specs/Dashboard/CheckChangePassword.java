package specs.Dashboard;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SystemAdmin.UserList.UserList;
import specs.AbstractSpec;

import java.util.Date;

public class CheckChangePassword extends AbstractSpec {
    private static By systemAdminMenuButton, userListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static UserList userList;

    @BeforeTest
    public void setUp() throws Exception {
        systemAdminMenuButton = By.xpath(propUISystemAdmin.getProperty("btnMenu_SystemAdmin"));
        userListMenuItem = By.xpath(propUISystemAdmin.getProperty("itemMenu_UserList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        userList = new UserList(driver);

        //loginPage.loginUser();
        //dashboard.openPageFromMenu(systemAdminMenuButton, userListMenuItem);
    }


    @Test
    public void canChangePassword() throws Exception {
        final String expectedMessage = "Your password has been changed and will take effect the next time you log in.";

        String username = "quick-test1";
        String originalPassword = "q4pass1234!";
        String newPassword = "Password"+(new Date().getTime()/1000)+"!";

        System.out.println("Changing to password: "+newPassword);
        loginPage.loginUser(username, originalPassword);
        dashboard.changePassword(originalPassword, newPassword);

        //System.out.println(dashboard.getPasswordChangeConfirmationAndAccept());
        Assert.assertEquals(dashboard.getPasswordChangeConfirmationAndAccept(), expectedMessage, "Password change confirmation message is not correct");

        Assert.assertFalse(dashboard.logoutFromAdmin().credentialsWork(username, originalPassword), "Old password still works.");
        Assert.assertTrue(loginPage.credentialsWork(username, newPassword), "New password "+newPassword+" doesn't work.");

        //manually restoring account to original password
        loginPage.loginUser().openPageFromMenu(systemAdminMenuButton, userListMenuItem);
        userList.editUser(userList.getIndexOfUsername(username)).changePasswordTo(originalPassword).saveUser();
        Assert.assertTrue(userList.logoutFromAdmin().credentialsWork(username, originalPassword), "Error restoring password to original value.");
    }


    @Test
    //If this test is run many times in a short time span, the quick-test2 account that it uses will become temporarily locked-out.
    public void tryChangingPasswordWithWrongCurrentPassword() throws Exception {
        final String expectedMessage = "Failed to change password at this moment.";

        String username = "quick-test2";
        String oldPassword = "q4pass1234!";
        String newPassword = "q4pass1234@";

        loginPage.loginUser(username, oldPassword);
        dashboard.changePassword("wrongPassword", newPassword);

        Assert.assertTrue(dashboard.getPasswordChangeErrorMessageAndClose().contains(expectedMessage), "Password change error message is not correct");

        //checking that the new password doesn't work and old password still does
        Assert.assertFalse(dashboard.logoutFromAdmin().credentialsWork(username, newPassword), "Incorrectly changed password works.");
        Assert.assertTrue(loginPage.credentialsWork(username, oldPassword), "Original password no longer works.");
    }

    @Test
    //If this test is run many times in a short time span, the quick-test3 account that it uses will become temporarily locked-out.
    public void tryChangingPasswordWithDifferentNewPasswords() throws Exception {
        final String expectedMessage = "New Password and Confirm New Password do not match";

        String username = "quick-test3";
        String oldPassword = "q4pass1234!";
        String newPassword1 = "q4pass1234@";
        String newPassword2 = "q4pass1234#";

        loginPage.loginUser(username, oldPassword);
        dashboard.changePassword(oldPassword, newPassword1, newPassword2);

        //System.out.println(dashboard.getPasswordChangeErrorMessageAndClose() + " : " + expectedMessage);
        Assert.assertTrue(dashboard.getPasswordChangeErrorMessageAndClose().contains(expectedMessage), "Password change error message is not correct");

        //checking that the new passwords don't work and old password still does
        Assert.assertFalse(dashboard.logoutFromAdmin().credentialsWork(username, newPassword1), "Incorrectly changed password 1 works.");
        Assert.assertFalse(loginPage.credentialsWork(username, newPassword2), "Incorrectly changed password 2 works.");
        Assert.assertTrue(loginPage.credentialsWork(username, oldPassword), "Original password no longer works.");
    }

    @AfterTest
    public void tearDown() {
        //dashboard.logoutFromAdmin();
        //driver.quit();
    }
}
