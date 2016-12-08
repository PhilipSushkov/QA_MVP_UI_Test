package pageobjects.Dashboard;


import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.ContentAdmin.DepartmentList.DepartmentList;
import pageobjects.ContentAdmin.DownloadList.DownloadList;
import pageobjects.ContentAdmin.FaqList.FaqList;
import pageobjects.ContentAdmin.FinancialReports.FinancialReports;
import pageobjects.ContentAdmin.JobPostingList.JobPostingList;
import pageobjects.ContentAdmin.PersonList.PersonList;
import pageobjects.ContentAdmin.PressReleaseCategories.PressReleaseCategories;
import pageobjects.ContentAdmin.QuickLinkList.QuickLinks;
import pageobjects.EmailAdmin.Compose.Compose;
import pageobjects.EmailAdmin.ManageList.MailingLists;
import pageobjects.EmailAdmin.Subscribers.MailingListUsers;
import pageobjects.LoginPage.LoginPage;
import pageobjects.ContentAdmin.PressReleases.EditPressRelease;
import pageobjects.ContentAdmin.PressReleases.PressReleases;
import pageobjects.ContentAdmin.Presentations.EditPresentation;
import pageobjects.ContentAdmin.Presentations.Presentations;
import pageobjects.ContentAdmin.Events.EditEvent;
import pageobjects.ContentAdmin.Events.Events;
import pageobjects.PreviewSite.PreviewSiteHome;
import pageobjects.SystemAdmin.UserList.UserList;
import pageobjects.SocialMedia.SocialMediaSummary;
import specs.AbstractSpec;

import java.util.ArrayList;

import static org.junit.Assert.fail;

public class Dashboard extends AbstractPageObject {
    Actions action = new Actions(driver);

    private final By addPressReleaseButton = By.xpath("//a[contains(@id,'hrefPressReleases')]");
    private final By addPresentationButton = By.xpath("//a[contains(@id,'hrefPresentations')]");
    private final By addEventButton = By.xpath("//a[contains(@id,'hrefEvents')]");
    private final By contentAdminMenuButton = By.xpath("//span[contains(text(),'Content Admin')]");
    private final By pressReleasesMenuButton = By.xpath("//a[contains(text(),'Press Releases')]/parent::li");
    private final By presentationsMenuButton = By.xpath("//a[contains(text(),'Presentations')]/parent::li");
    private final By eventsMenuButton = By.xpath("//a[contains(text(),'Events')]/parent::li");
    private final By systemAdminMenuButton = By.xpath("//span[contains(text(),'System Admin')]");
    private final By userListMenuItem = By.xpath("//a[contains(text(),'User List')]/parent::li");
    private final By socialMediaDashboard = By.linkText("Social Media Dashboard");
    private final By previewSiteButton = By.linkText("PREVIEW SITE");
    private final By invalidateCacheButton = By.xpath("//a[contains(@id,'hrefInvalidateCache')]");
    private final By invalidateCacheMessage = By.className("MessageContainer");
    private final By logoutMenuItem = By.xpath("//li/a[contains(text(),'Logout')]");

    private final By selectAnActionDropdown = By.className("user-manage-ddl");
    private final By changePasswordButton = By.className("StartChangePassword");

    //within the change password popup
    private final By oldPasswordField = By.xpath("//input[contains(@id,'txtOldPassword')]");
    private final By newPasswordField = By.xpath("//input[contains(@id,'txtNewPassword')]");
    private final By confirmNewPasswordField = By.xpath("//input[contains(@id,'txtConfirmNewPassword')]");
    private final By resetPasswordButton = By.cssSelector("[value='Reset Password']");
    private final By passwordChangeError = By.className("ErrorChangePassword");
    private final By closePasswordChangeDialog = By.className("fancybox-close");

    private static final long DEFAULT_PAUSE = 1500;
    private static final int ATTEMPTS = 3;

    public Dashboard(WebDriver driver) {

        super(driver);
    }

    public String getURL() throws Exception {
        wait.until(ExpectedConditions.elementToBeClickable(addPressReleaseButton));
        if (AbstractSpec.getSessionID() == null) {
            new LoginPage(driver).sessionID();
        }
        return driver.getCurrentUrl();
    }

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

        for (int i=0; i<ATTEMPTS; i++) {
            try {
                action.moveToElement(findElement(menuButton)).perform();
                wait.until(ExpectedConditions.visibilityOf(findElement(menuItem)));
                Thread.sleep(DEFAULT_PAUSE);
                findElement(menuItem).click();
                break;
            } catch (ElementNotVisibleException e1){
                System.out.println("Attempt #: " + i);
            } catch (ElementNotFoundException e2) {
                System.out.println("Attempt #: " + i);
            } catch (TimeoutException e3) {
                System.out.println("Attempt #: " + i);
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
        return this;
    }

    public String getInvalidateCacheMessage(){
        pause(5000);
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
        findElement(resetPasswordButton).click();
        pause(3000);
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
        findElement(resetPasswordButton).click();
        pause(3000);
        return this;
    }

    public String getPasswordChangeConfirmationAndAccept(){
        try {
            Alert confirmation = driver.switchTo().alert();
            String confirmationText = confirmation.getText();
            confirmation.accept();
            driver.switchTo().defaultContent();
            pause(3000);
            return confirmationText;
        }catch (NoAlertPresentException e){
            fail("Password change confirmation dialog does not appear.");
            return "";
        }
    }

    public String getPasswordChangeErrorMessageAndClose(){
        if (!doesElementExist(passwordChangeError)){
            fail("Password change error message does not appear.");
        }
        String message = findElement(passwordChangeError).getText();
        findElement(closePasswordChangeDialog).click();
        pause(3000);
        return message;
    }

    public LoginPage logout() {
        wait.until(ExpectedConditions.visibilityOf(findElement(logoutMenuItem)));
        pause(DEFAULT_PAUSE);
        findElement(logoutMenuItem).click();
        return new LoginPage(getDriver());
    }

}
