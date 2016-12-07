package testrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.ContentAdmin.DepartmentList.CheckDepartmentList;
import specs.ContentAdmin.DownloadList.CheckDownloadList;
import specs.ContentAdmin.FaqList.CheckFaqList;
import specs.ContentAdmin.FinancialReports.CheckFinancialReports;
import specs.ContentAdmin.JobPostingList.CheckJobPostingList;
import specs.ContentAdmin.PersonList.CheckPersonList;
import specs.ContentAdmin.PressReleaseCategories.CheckPressReleaseCategories;
import specs.ContentAdmin.QuickLinkList.CheckQuickLinkList;
import specs.EmailAdmin.ManageList.CheckMailingLists;
import specs.EmailAdmin.Subscribers.CheckMailingListUsers;
import specs.Dashboard.CheckDashboard;
import specs.LoginPage.EnterToAdmin;
import specs.PressReleases.AddNewPressRelease;
import specs.Presentations.AddNewPresentation;
import specs.PreviewSite.CheckPreviewSite;
import specs.PublicSite.CheckPublicSite;
import specs.Events.AddNewEvent;
import specs.SiteAdmin.AliasList.CheckAliasList;
import specs.SiteAdmin.CssFileList.CheckCssFileList;
import specs.SiteAdmin.DomainList.CheckDomainList;
import specs.SiteAdmin.ContentAdminEdit.CheckEditContentAdminPages;
import specs.SiteAdmin.GlobalModuleList.CheckGlobalModuleList;
import specs.SiteAdmin.IndexContent.CheckIndexContent;
import specs.SiteAdmin.LayoutDefinitionList.CheckLayoutDefinitionList;
import specs.SiteAdmin.LinkToPageList.CheckLinkToPageList;
import specs.SiteAdmin.LookupList.CheckLookupList;
import specs.SiteAdmin.MobileLinkList.CheckMobileLinkList;
import specs.SiteAdmin.ModuleDefinitionList.CheckModuleDefinitionList;
import specs.SystemAdmin.PDFTemplateEdit.CheckPDFTemplateEdit;
import specs.SystemAdmin.SiteList.CheckSiteList;
import specs.SystemAdmin.UserGroupList.CheckUserGroupList;
import specs.SystemAdmin.UserList.CheckUserList;
import specs.SystemAdmin.WorkflowEmailList.CheckWorkflowEmailList;
import specs.SystemAdmin.AlertFilterList.CheckAlertFilterList;
import specs.SystemAdmin.GenericStorageList.CheckGenericStorageList;
import specs.EmailAdmin.Compose.CheckCompose;

/**
 * Created by philips on 2016-11-07.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        EnterToAdmin.class,
        AddNewPressRelease.class,
        AddNewPresentation.class,
        AddNewEvent.class,
        CheckPublicSite.class,
        CheckPreviewSite.class,
        CheckDashboard.class
        }
)

public class SmokeTest {
}
