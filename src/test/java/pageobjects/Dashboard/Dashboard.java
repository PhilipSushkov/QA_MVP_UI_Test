package pageobjects.Dashboard;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.PressReleases.EditPressRelease;
import pageobjects.PressReleases.PressReleases;
import pageobjects.Presentations.EditPresentation;
import pageobjects.Presentations.Presentations;
import pageobjects.Events.EditEvent;
import pageobjects.Events.Events;
import pageobjects.SystemAdmin.SiteMaintenance.SiteMaintenance;
import pageobjects.SystemAdmin.UserGroupList.UserGroupList;
import pageobjects.SystemAdmin.UserList.UserList;
import pageobjects.SystemAdmin.AlertFilterList.AlertFilterList;
import pageobjects.SystemAdmin.GenericStorageList.GenericStorageList;
import pageobjects.SystemAdmin.WorkflowEmailList.WorkflowEmailList;
import pageobjects.SystemAdmin.PDFTemplateEdit.PDFTemplateEdit;
import pageobjects.SystemAdmin.SiteList.SiteList;

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
    private final By siteAdminMenuButton = By.xpath("//span[contains(text(),'Site Admin')]");
    private final By userListMenuItem = By.xpath("//a[contains(text(),'User List')]/parent::li");
    private final By alertFilterListMenuItem = By.xpath("//a[contains(text(),'Alert Filter List')]/parent::li");
    private final By genericStorageListMenuItem = By.xpath("//a[contains(text(),'Generic Storage List')]/parent::li");
    private final By workflowEmailListMenuItem = By.xpath("//a[contains(text(),'Workflow Email List')]/parent::li");
    private final By pdfTemplateEditMenuItem = By.xpath("//a[contains(text(),'PDF Template Edit')]/parent::li");
    private final By siteMaintenanceMenuItem = By.xpath("//a[contains(text(),'Site Maintenance')]/parent::li");
    private final By siteListMenuItem = By.xpath("//a[contains(text(),'Site List')]/parent::li");
    private final By userGroupListMenuItem = By.xpath("//a[contains(text(),'User Group List')]/parent::li");
    private final By globalModuleListMenuItem = By.xpath("//a[contains(text(),'Global Module List')]/parent::li");

    public Dashboard(WebDriver driver) {

        super(driver);
    }

    public String getURL() throws Exception {
        wait.until(ExpectedConditions.elementToBeClickable(addPressReleaseButton));
        return driver.getCurrentUrl();
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

    public UserList openUserListPage() {
        action.moveToElement(findElement(systemAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(userListMenuItem)));
        pause(1000L);
        findElement(userListMenuItem).click();
        return new UserList(getDriver());
    }

    public AlertFilterList openAlertFilterListPage() {
        action.moveToElement(findElement(systemAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(alertFilterListMenuItem)));
        pause(1000L);
        findElement(alertFilterListMenuItem).click();
        return new AlertFilterList(getDriver());
    }

    public WorkflowEmailList openWorkflowEmailListPage() {
        action.moveToElement(findElement(systemAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(workflowEmailListMenuItem)));
        pause(1000L);
        findElement(workflowEmailListMenuItem).click();
        return new WorkflowEmailList(getDriver());
    }

    public GenericStorageList openGenericStorageListPage() {
        action.moveToElement(findElement(systemAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(genericStorageListMenuItem)));
        pause(1000L);
        findElement(genericStorageListMenuItem).click();
        return new GenericStorageList(getDriver());
    }

    public PDFTemplateEdit openPDFTemplateEditPage() {
        action.moveToElement(findElement(systemAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(pdfTemplateEditMenuItem)));
        pause(1000L);
        findElement(pdfTemplateEditMenuItem).click();
        return new PDFTemplateEdit(getDriver());
    }

    public SiteMaintenance openSiteMaintenancePage() {
        action.moveToElement(findElement(systemAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(siteMaintenanceMenuItem)));
        pause(1000L);
        findElement(siteMaintenanceMenuItem).click();
        return new SiteMaintenance(getDriver());
    }

    public SiteList openSiteListPage() {
        action.moveToElement(findElement(systemAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(siteListMenuItem)));
        pause(1000L);
        findElement(siteListMenuItem).click();
        return new SiteList(getDriver());
    }

    public UserGroupList openUserGroupListPage() {
        action.moveToElement(findElement(systemAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(userGroupListMenuItem)));
        pause(1000L);
        findElement(userGroupListMenuItem).click();
        return new UserGroupList(getDriver());
    }

    public SiteList openGlobalModuleListPage() {
        action.moveToElement(findElement(siteAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(globalModuleListMenuItem)));
        pause(1000L);
        findElement(globalModuleListMenuItem).click();
        return new SiteList(getDriver());
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

}
