package pageobjects.Dashboard;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
import pageobjects.LoginPage.LoginPage;
import pageobjects.PressReleases.EditPressRelease;
import pageobjects.PressReleases.PressReleases;
import pageobjects.Presentations.EditPresentation;
import pageobjects.Presentations.Presentations;
import pageobjects.Events.EditEvent;
import pageobjects.Events.Events;
import pageobjects.PreviewSite.PreviewSiteHome;
import pageobjects.SiteAdmin.AliasList.AliasList;
import pageobjects.SiteAdmin.CssFileList.CssFileList;
import pageobjects.SiteAdmin.DomainList.DomainList;
import pageobjects.SiteAdmin.EditContentAdminPages.EditContentAdminPages;
import pageobjects.SiteAdmin.ExternalFeedList.ExternalFeedList;
import pageobjects.SiteAdmin.GlobalModuleList.GlobalModuleList;
import pageobjects.SiteAdmin.IndexContent.IndexContent;
import pageobjects.SiteAdmin.LayoutDefinitionList.LayoutDefinitionList;
import pageobjects.SiteAdmin.MobileLinkList.MobileLinkList;
import pageobjects.SiteAdmin.ModuleDefinitionList.ModuleDefinitionList;
import pageobjects.SystemAdmin.SiteMaintenance.SiteMaintenance;
import pageobjects.SystemAdmin.UserGroupList.UserGroupList;
import pageobjects.SystemAdmin.UserList.UserList;
import pageobjects.SystemAdmin.AlertFilterList.AlertFilterList;
import pageobjects.SystemAdmin.GenericStorageList.GenericStorageList;
import pageobjects.SystemAdmin.WorkflowEmailList.WorkflowEmailList;
import pageobjects.SystemAdmin.PDFTemplateEdit.PDFTemplateEdit;
import pageobjects.SystemAdmin.SiteList.SiteList;
import pageobjects.SiteAdmin.LinkToPageList.LinkToPageList;
import pageobjects.SiteAdmin.LookupList.LookupList;
import specs.AbstractSpec;

import java.util.ArrayList;

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
    private final By layoutDefinitionListMenuItem = By.xpath("//a[contains(text(),'Layout Definition List')]/parent::li");
    private final By moduleDefinitionListMenuItem = By.xpath("//a[contains(text(),'Module Definition List')]/parent::li");
    private final By cssFileListMenuItem = By.xpath("//a[contains(text(),'Css File List')]/parent::li");
    private final By externalFeedListMenuItem = By.xpath("//a[contains(text(),'External Feed List')]/parent::li");
    private final By indexContentMenuItem = By.xpath("//a[contains(text(),'Index Content')]/parent::li");
    private final By linkToPageListMenuItem = By.xpath("//a[contains(text(),'Link To Page List')]/parent::li");
    private final By lookupListMenuItem = By.xpath("//a[contains(text(),'Lookup List')]/parent::li");
    private final By aliasListMenuItem = By.xpath("//a[contains(text(),'Alias List')]/parent::li");
    private final By mobileLinkListMenuItem = By.xpath("//a[contains(text(),'Mobile Link List')]/parent::li");
    private final By domainListMenuItem = By.xpath("//a[contains(text(),'Domain List')]/parent::li");
    private final By editContentAdminPagesMenuItem = By.xpath("//a[contains(text(),'Content Admin Edit')]/parent::li");
    private final By financialReportsMenuItem = By.xpath("//a[contains(text(),'Financial Reports')]/parent::li");
    private final By pressReleaseCategoriesMenuItem = By.xpath("//a[contains(text(),'Press Release Categories')]/parent::li");
    private final By quickLinksMenuItem = By.xpath("//a[contains(text(),'Quick Links')]/parent::li");
    private final By downloadListMenuItem = By.xpath("//a[contains(text(),'Download List')]/parent::li");
    private final By personListMenuItem = By.xpath("//a[contains(text(),'Person List')]/parent::li");
    private final By departmentListMenuItem = By.xpath("//a[contains(text(),'Department List')]/parent::li");
    private final By faqListMenuItem = By.xpath("//a[contains(text(),'Faq List')]/parent::li");
    private final By jobPostingMenuItem = By.xpath("//a[contains(text(),'Job Posting List')]/parent::li");
    private final By previewSiteButton = By.linkText("PREVIEW SITE");

    public static final long DEFAULT_PAUSE = 2000;

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

    public UserList openUserListPage() {
        action.moveToElement(findElement(systemAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(userListMenuItem)));
        pause(DEFAULT_PAUSE);
        findElement(userListMenuItem).click();
        return new UserList(getDriver());
    }

    public AlertFilterList openAlertFilterListPage() {
        action.moveToElement(findElement(systemAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(alertFilterListMenuItem)));
        pause(DEFAULT_PAUSE);
        findElement(alertFilterListMenuItem).click();
        return new AlertFilterList(getDriver());
    }

    public WorkflowEmailList openWorkflowEmailListPage() {
        action.moveToElement(findElement(systemAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(workflowEmailListMenuItem)));
        pause(DEFAULT_PAUSE);
        findElement(workflowEmailListMenuItem).click();
        return new WorkflowEmailList(getDriver());
    }

    public GenericStorageList openGenericStorageListPage() {
        action.moveToElement(findElement(systemAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(genericStorageListMenuItem)));
        pause(DEFAULT_PAUSE);
        findElement(genericStorageListMenuItem).click();
        return new GenericStorageList(getDriver());
    }

    public PDFTemplateEdit openPDFTemplateEditPage() {
        action.moveToElement(findElement(systemAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(pdfTemplateEditMenuItem)));
        pause(DEFAULT_PAUSE);
        findElement(pdfTemplateEditMenuItem).click();
        return new PDFTemplateEdit(getDriver());
    }

    public SiteMaintenance openSiteMaintenancePage() {
        action.moveToElement(findElement(systemAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(siteMaintenanceMenuItem)));
        pause(DEFAULT_PAUSE);
        findElement(siteMaintenanceMenuItem).click();
        return new SiteMaintenance(getDriver());
    }

    public SiteList openSiteListPage() {
        action.moveToElement(findElement(systemAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(siteListMenuItem)));
        pause(DEFAULT_PAUSE);
        findElement(siteListMenuItem).click();
        return new SiteList(getDriver());
    }

    public UserGroupList openUserGroupListPage() {
        action.moveToElement(findElement(systemAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(userGroupListMenuItem)));
        pause(DEFAULT_PAUSE);
        findElement(userGroupListMenuItem).click();
        return new UserGroupList(getDriver());
    }

    public GlobalModuleList openGlobalModuleListPage() {
        action.moveToElement(findElement(siteAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(globalModuleListMenuItem)));
        pause(DEFAULT_PAUSE);
        findElement(globalModuleListMenuItem).click();
        return new GlobalModuleList(getDriver());
    }

    public LayoutDefinitionList openLayoutDefinitionListPage() {
        action.moveToElement(findElement(siteAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(layoutDefinitionListMenuItem)));
        pause(DEFAULT_PAUSE);
        findElement(layoutDefinitionListMenuItem).click();
        return new LayoutDefinitionList(getDriver());
    }

    public ModuleDefinitionList openModuleDefinitionListPage() {
        action.moveToElement(findElement(siteAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(moduleDefinitionListMenuItem)));
        pause(DEFAULT_PAUSE);
        findElement(moduleDefinitionListMenuItem).click();
        return new ModuleDefinitionList(getDriver());
    }

    public CssFileList openCssFileListPage() {
        action.moveToElement(findElement(siteAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(cssFileListMenuItem)));
        pause(DEFAULT_PAUSE);
        findElement(cssFileListMenuItem).click();
        return new CssFileList(getDriver());
    }

    public ExternalFeedList openExternalFeedListPage() {
        action.moveToElement(findElement(siteAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(externalFeedListMenuItem)));
        pause(DEFAULT_PAUSE);
        findElement(externalFeedListMenuItem).click();
        return new ExternalFeedList(getDriver());
    }

    public IndexContent openIndexContentPage() {
        action.moveToElement(findElement(siteAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(indexContentMenuItem)));
        pause(DEFAULT_PAUSE);
        findElement(indexContentMenuItem).click();
        return new IndexContent(getDriver());
    }

    public LinkToPageList openLinkToPageListPage() {
        action.moveToElement(findElement(siteAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(linkToPageListMenuItem)));
        pause(DEFAULT_PAUSE);
        findElement(linkToPageListMenuItem).click();
        return new LinkToPageList(getDriver());
    }

    public LookupList openLookupListPage() {
        action.moveToElement(findElement(siteAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(lookupListMenuItem)));
        pause(DEFAULT_PAUSE);
        findElement(lookupListMenuItem).click();
        return new LookupList(getDriver());
    }

    public MobileLinkList openMobileLinkListPage() {
        action.moveToElement(findElement(siteAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(mobileLinkListMenuItem)));
        pause(DEFAULT_PAUSE);
        findElement(mobileLinkListMenuItem).click();
        return new MobileLinkList(getDriver());
    }

    public AliasList openAliasListPage() {
        action.moveToElement(findElement(siteAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(aliasListMenuItem)));
        pause(DEFAULT_PAUSE);
        findElement(aliasListMenuItem).click();
        return new AliasList(getDriver());
    }

    public DomainList openDomainListPage() {
        action.moveToElement(findElement(siteAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(domainListMenuItem)));
        pause(DEFAULT_PAUSE);
        findElement(domainListMenuItem).click();
        return new DomainList(getDriver());
    }

    public EditContentAdminPages openEditContentAdminPages() {
        action.moveToElement(findElement(siteAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(editContentAdminPagesMenuItem)));
        pause(DEFAULT_PAUSE);
        findElement(editContentAdminPagesMenuItem).click();
        return new EditContentAdminPages(getDriver());
    }

    public FinancialReports openFinancialReports() {
        action.moveToElement(findElement(contentAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(financialReportsMenuItem)));
        pause(DEFAULT_PAUSE);
        findElement(financialReportsMenuItem).click();
        return new FinancialReports(getDriver());
    }

    public PressReleaseCategories openPressReleaseCategories() {
        action.moveToElement(findElement(contentAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(pressReleaseCategoriesMenuItem)));
        pause(DEFAULT_PAUSE);
        findElement(pressReleaseCategoriesMenuItem).click();
        return new PressReleaseCategories(getDriver());
    }

    public QuickLinks openQuickLinks() {
        action.moveToElement(findElement(contentAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(quickLinksMenuItem)));
        pause(DEFAULT_PAUSE);
        findElement(quickLinksMenuItem).click();
        return new QuickLinks(getDriver());
    }

    public DownloadList openDownloadList() {
        action.moveToElement(findElement(contentAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(downloadListMenuItem)));
        pause(DEFAULT_PAUSE);
        findElement(downloadListMenuItem).click();
        return new DownloadList(getDriver());
    }

    public PersonList openPersonList() {
        action.moveToElement(findElement(contentAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(personListMenuItem)));
        pause(DEFAULT_PAUSE);
        findElement(personListMenuItem).click();
        return new PersonList(getDriver());
    }

    public DepartmentList openDepartmentList() {
        action.moveToElement(findElement(contentAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(departmentListMenuItem)));
        pause(DEFAULT_PAUSE);
        findElement(departmentListMenuItem).click();
        return new DepartmentList(getDriver());
    }

    public FaqList openFaqList() {
        action.moveToElement(findElement(contentAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(faqListMenuItem)));
        pause(DEFAULT_PAUSE);
        findElement(faqListMenuItem).click();
        return new FaqList(getDriver());
    }

    public JobPostingList openJobPostingList() {
        action.moveToElement(findElement(contentAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(jobPostingMenuItem)));
        pause(DEFAULT_PAUSE);
        findElement(jobPostingMenuItem).click();
        return new JobPostingList(getDriver());
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
