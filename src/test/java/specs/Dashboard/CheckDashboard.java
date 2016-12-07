package specs.Dashboard;


import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SystemAdmin.UserList.UserList;
import specs.AbstractSpec;

import java.util.Date;

public class CheckDashboard extends AbstractSpec{

    @Test
    public void canInvalidateCache() throws Exception {
        new LoginPage(driver).loginUser();
        Assert.assertThat("Invalidate cache message is not displayed",
                new Dashboard(driver).invalidateCache().getInvalidateCacheMessage(),
                CoreMatchers.containsString("Cache is invalidated successfully"));
    }

    @Test
    public void canChangePassword() throws Exception {
        String username = "quick-test1";
        String originalPassword = "q4pass1234!";
        String newPassword = "Password"+(new Date().getTime()/1000)+"!";
        System.out.println("Changing to password: "+newPassword);
        new LoginPage(driver).loginUser(username, originalPassword);
        new Dashboard(driver).changePassword(originalPassword, newPassword);
        Assert.assertThat("Password change confirmation message is not correct",
                new Dashboard(driver).getPasswordChangeConfirmationAndAccept(),
                CoreMatchers.containsString("Your password has been changed and will take effect the next time you log in."));
        Assert.assertFalse("Old password still works.", new Dashboard(driver).logoutFromAdmin().credentialsWork(username, originalPassword));
        Assert.assertTrue("New password "+newPassword+" doesn't work.", new LoginPage(driver).credentialsWork(username, newPassword));

        //manually restoring account to original password
        new LoginPage(driver).loginUser().openUserListPage();
        new UserList(driver).editUser(new UserList(driver).getIndexOfUsername(username)).changePasswordTo(originalPassword).saveUser();
        Assert.assertTrue("Error restoring password to original value.", new UserList(driver).logoutFromAdmin().credentialsWork(username, originalPassword));
    }

    @Test
    //If this test is run many times in a short time span, the quick-test2 account that it uses will become temporarily locked-out.
    public void tryChangingPasswordWithWrongCurrentPassword() throws Exception {
        String username = "quick-test2";
        String oldPassword = "q4pass1234!";
        String newPassword = "q4pass1234@";
        new LoginPage(driver).loginUser(username, oldPassword);
        new Dashboard(driver).changePassword("wrongPassword", newPassword);
        Assert.assertThat("Password change error message is not correct",
                new Dashboard(driver).getPasswordChangeErrorMessageAndClose(),
                CoreMatchers.containsString("Failed to change password at this moment."));

        //checking that the new password doesn't work and old password still does
        Assert.assertFalse("Incorrectly changed password works.", new Dashboard(driver).logoutFromAdmin().credentialsWork(username, newPassword));
        Assert.assertTrue("Original password no longer works.", new LoginPage(driver).credentialsWork(username, oldPassword));
    }

    @Test
    //If this test is run many times in a short time span, the quick-test3 account that it uses will become temporarily locked-out.
    public void tryChangingPasswordWithDifferentNewPasswords() throws Exception {
        String username = "quick-test3";
        String oldPassword = "q4pass1234!";
        String newPassword1 = "q4pass1234@";
        String newPassword2 = "q4pass1234#";
        new LoginPage(driver).loginUser(username, oldPassword);
        new Dashboard(driver).changePassword(oldPassword, newPassword1, newPassword2);
        Assert.assertThat("Password change error message is not correct",
                new Dashboard(driver).getPasswordChangeErrorMessageAndClose(),
                CoreMatchers.containsString("New Password and Confirm New Password do not match"));

        //checking that the new passwords don't work and old password still does
        Assert.assertFalse("Incorrectly changed password 1 works.", new Dashboard(driver).logoutFromAdmin().credentialsWork(username, newPassword1));
        Assert.assertFalse("Incorrectly changed password 2 works.", new LoginPage(driver).credentialsWork(username, newPassword2));
        Assert.assertTrue("Original password no longer works.", new LoginPage(driver).credentialsWork(username, oldPassword));
    }

    @After
    public void tearDown() {
        new Dashboard(driver).logout();
        //driver.quit();
    }
}
