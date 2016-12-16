package pageobjects.Dashboard;


import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.ContentAdmin.PressReleases.EditPressRelease;
import pageobjects.ContentAdmin.PressReleases.PressReleases;
import pageobjects.ContentAdmin.Presentations.EditPresentation;
import pageobjects.ContentAdmin.Presentations.Presentations;
import pageobjects.ContentAdmin.Events.EditEvent;
import pageobjects.ContentAdmin.Events.Events;
import pageobjects.PreviewSite.PreviewSiteHome;
import pageobjects.SocialMedia.SocialMediaSummary;

import java.util.ArrayList;

import static org.junit.Assert.fail;
import static specs.AbstractSpec.propUICommon;
import static specs.AbstractSpec.propUIContentAdmin;


public class Dashboard extends AbstractPageObject {
    Actions action = new Actions(driver);
    private static By addPressReleaseButton, addPresentationButton, addEventButton, contentAdminMenuButton, pressReleasesMenuButton;
    private static By presentationsMenuButton, eventsMenuButton, socialMediaDashboard, previewSiteButton, invalidateCacheButton;
    private static By invalidateCacheMessage, selectAnActionDropdown, changePasswordButton, oldPasswordField;
    private static By newPasswordField, confirmNewPasswordField, resetPasswordButton, passwordChangeError, closePasswordChangeDialog;

    private static final long DEFAULT_PAUSE = 1500;
    private static final int ATTEMPTS = 3;

    public Dashboard(WebDriver driver) {
        super(driver);

        addPressReleaseButton = By.xpath(propUICommon.getProperty("btn_AddPressRelease"));
        addPresentationButton = By.xpath(propUICommon.getProperty("btn_AddPresentation"));
        addEventButton = By.xpath(propUICommon.getProperty("btn_AddEvent"));
        previewSiteButton = By.linkText(propUICommon.getProperty("btn_PreviewSite"));
        socialMediaDashboard = By.linkText(propUICommon.getProperty("btnMenu_SocialMedia"));

        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        pressReleasesMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_PressReleases"));
        presentationsMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_Presentations"));
        eventsMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_Events"));

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

    /*
    public String getURL() throws Exception {
        wait.until(ExpectedConditions.elementToBeClickable(addPressReleaseButton));
        if (AbstractSpec.getSessionID() == null) {
            new LoginPage(driver).sessionID();
        }
        return driver.getCurrentUrl();
    }
    */

    public PreviewSiteHome previewSite() {
        waitForElement(previewSiteButton);
        findElement(previewSiteButton).click();
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        return new PreviewSiteHome(getDriver());
    }

    public EditPressRelease newPressRelease() {
        wait.until(ExpectedConditions.elementToBeClickable(addPressReleaseButton));
        findElement(addPressReleaseButton).click();
        return new EditPressRelease(getDriver());
    }

    public EditPresentation newPresentation() {
        wait.until(ExpectedConditions.elementToBeClickable(addPresentationButton));
        findElement(addPresentationButton).click();
        return new EditPresentation(getDriver());
    }

    public EditEvent newEvent() {
        wait.until(ExpectedConditions.elementToBeClickable(addEventButton));
        findElement(addEventButton).click();
        return new EditEvent(getDriver());
    }

    public void openPageFromMenu(By menuButton, By menuItem) throws Exception {
        wait.until(ExpectedConditions.visibilityOf(findElement(menuButton)));

        for (int i=0; i<ATTEMPTS; i++) {
            try {
                action.moveToElement(findElement(menuButton)).perform();
                wait.until(ExpectedConditions.visibilityOf(findElement(menuItem)));
                Thread.sleep(DEFAULT_PAUSE);
                findElement(menuItem).click();
                break;
            } catch (ElementNotVisibleException e1){
                System.out.println("Attempt #" + i);
            } catch (ElementNotFoundException e2) {
                System.out.println("Attempt #" + i);
            } catch (TimeoutException e3) {
                System.out.println("Attempt #" + i);
            }
        }
    }

    public PressReleases pressReleases() {
        action.moveToElement(findElement(contentAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(pressReleasesMenuButton)));
        findElement(pressReleasesMenuButton).click();

        return new PressReleases(getDriver());
    }

    public Presentations presentations() {
        action.moveToElement(findElement(contentAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(presentationsMenuButton)));
        findElement(presentationsMenuButton).click();
        return new Presentations(getDriver());
    }

    public Events events() {
        action.moveToElement(findElement(contentAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(eventsMenuButton)));
        findElement(eventsMenuButton).click();
        return new Events(getDriver());
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
        waitForElement(selectAnActionDropdown);
        findElement(selectAnActionDropdown).click();
        waitForElement(changePasswordButton);
        findElement(changePasswordButton).click();
        waitForElement(oldPasswordField);
        findElement(oldPasswordField).sendKeys(current);
        findElement(newPasswordField).sendKeys(changeTo);
        findElement(confirmNewPasswordField).sendKeys(changeTo);
        pause(2000);
        //findElement(resetPasswordButton).click();
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
        //findElement(resetPasswordButton).click();
        retryClick(findElement(resetPasswordButton));
        pause(2000);
        return this;
    }

    public String getPasswordChangeConfirmationAndAccept(){
        try {
            Alert confirmation = driver.switchTo().alert();
            String confirmationText = confirmation.getText();
            confirmation.accept();
            driver.switchTo().defaultContent();
            pause(2000);
            return confirmationText;
        }catch (NoAlertPresentException e){
            fail("Password change confirmation dialog does not appear.");
            return "";
        }
    }

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

    /*
    public LoginPage logout() {
        wait.until(ExpectedConditions.visibilityOf(findElement(logoutMenuItem)));
        findElement(logoutMenuItem).click();
        return new LoginPage(getDriver());
    }
    */

}
