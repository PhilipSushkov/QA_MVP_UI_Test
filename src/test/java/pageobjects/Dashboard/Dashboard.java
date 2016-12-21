package pageobjects.Dashboard;


import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;
import pageobjects.PreviewSite.PreviewSiteHome;
import pageobjects.SocialMedia.SocialMediaSummary;

import java.util.ArrayList;

import static org.junit.Assert.fail;
import static specs.AbstractSpec.propUICommon;


public class Dashboard extends AbstractPageObject {
    private static By socialMediaDashboard, previewSiteButton, invalidateCacheButton;
    private static By invalidateCacheMessage, selectAnActionDropdown, changePasswordButton, oldPasswordField;
    private static By newPasswordField, confirmNewPasswordField, resetPasswordButton, passwordChangeError, closePasswordChangeDialog;

    public Dashboard(WebDriver driver) {
        super(driver);

        previewSiteButton = By.linkText(propUICommon.getProperty("btn_PreviewSite"));
        socialMediaDashboard = By.linkText(propUICommon.getProperty("btnMenu_SocialMedia"));

        invalidateCacheButton = By.xpath(propUICommon.getProperty("btn_InvalidateCache"));
        invalidateCacheMessage = By.xpath(propUICommon.getProperty("msg_InvalidateCache"));

        selectAnActionDropdown = By.className(propUICommon.getProperty("select_AnAction"));
        changePasswordButton = By.className(propUICommon.getProperty("btn_ChangePassword"));

        // Within the change password popup
        oldPasswordField = By.xpath(propUICommon.getProperty("input_OldPassword"));
        newPasswordField = By.xpath(propUICommon.getProperty("input_NewPassword"));
        confirmNewPasswordField = By.xpath(propUICommon.getProperty("input_ConfirmNewPassword"));
        resetPasswordButton = By.cssSelector(propUICommon.getProperty("btn_ResetPassword"));
        passwordChangeError = By.xpath(propUICommon.getProperty("msg_PasswordChange"));
        closePasswordChangeDialog = By.xpath(propUICommon.getProperty("btn_ClosePasswordChangeDialog"));

    }

    public PreviewSiteHome previewSite() {
        waitForElement(previewSiteButton);
        findElement(previewSiteButton).click();
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        return new PreviewSiteHome(getDriver());
    }

    public SocialMediaSummary openSocialMedia(){
        waitForElement(socialMediaDashboard);
        findElement(socialMediaDashboard).click();
        return new SocialMediaSummary(getDriver());
    }

    public Dashboard invalidateCache(){
        waitForElement(invalidateCacheButton);
        findElement(invalidateCacheButton).click();
        pause(5000);
        return this;
    }

    public String getInvalidateCacheMessage(){
        if (!doesElementExist(invalidateCacheMessage)){
            return "";
        }
        return findElement(invalidateCacheMessage).getText();
    }

    public Dashboard changePassword(String current, String changeTo){
        // opening the change password dialog box
        waitForElement(selectAnActionDropdown);
        findElement(selectAnActionDropdown).click();
        waitForElement(changePasswordButton);
        findElement(changePasswordButton).click();
        // entering old password and twice entering new password
        waitForElement(oldPasswordField);
        findElement(oldPasswordField).sendKeys(current);
        findElement(newPasswordField).sendKeys(changeTo);
        findElement(confirmNewPasswordField).sendKeys(changeTo);
        pause(2000);
        // submitting password change
        retryClick(findElement(resetPasswordButton));
        pause(2000);
        return this;
    }

    // Used for a test in which the "Confirm New Password" is different from the "New Password"
    public Dashboard changePassword(String oldPassword, String newPassword, String confirmNewPassword){
        waitForElement(selectAnActionDropdown);
        findElement(selectAnActionDropdown).click();
        waitForElement(changePasswordButton);
        findElement(changePasswordButton).click();
        waitForElement(oldPasswordField);
        findElement(oldPasswordField).sendKeys(oldPassword);
        findElement(newPasswordField).sendKeys(newPassword);
        findElement(confirmNewPasswordField).sendKeys(confirmNewPassword);
        pause(2000);
        retryClick(findElement(resetPasswordButton));
        pause(2000);
        return this;
    }

    // returns the message shown in the password change confirmation alert and closes the alert
    // fails the test if no alert is present
    public String getPasswordChangeConfirmationAndAccept(){
        try {
            Alert confirmation = driver.switchTo().alert();
            String confirmationText = confirmation.getText();
            confirmation.accept(); // closes the alert
            driver.switchTo().defaultContent();
            pause(2000);
            return confirmationText;
        }catch (NoAlertPresentException e){
            fail("Password change confirmation dialog does not appear.");
            return "";
        }
    }

    // returns the password change error message and closes the password change dialog box
    // fails the test if no error message is present
    public String getPasswordChangeErrorMessageAndClose(){
        pause(2000);
        waitForElement(passwordChangeError);
        if (!doesElementExist(passwordChangeError)){
            fail("Password change error message does not appear.");
        }
        String message = findElement(passwordChangeError).getText();
        //findElement(closePasswordChangeDialog).click();
        retryClick(findElement(closePasswordChangeDialog));
        pause(2000);
        return message;
    }


}
