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
import specs.EmailAdmin.Compose.CheckCompose;
import specs.SiteAdmin.AliasList.CheckAliasList;
import specs.SiteAdmin.ContentAdminEdit.CheckEditContentAdminPages;
import specs.SiteAdmin.CssFileList.CheckCssFileList;
import specs.SiteAdmin.DomainList.CheckDomainList;
import specs.SiteAdmin.GlobalModuleList.CheckGlobalModuleList;
import specs.SiteAdmin.IndexContent.CheckIndexContent;
import specs.SiteAdmin.LayoutDefinitionList.CheckLayoutDefinitionList;
import specs.SiteAdmin.LinkToPageList.CheckLinkToPageList;
import specs.SiteAdmin.LookupList.CheckLookupList;
import specs.SiteAdmin.MobileLinkList.CheckMobileLinkList;
import specs.SiteAdmin.ModuleDefinitionList.CheckModuleDefinitionList;
import specs.SystemAdmin.AlertFilterList.CheckAlertFilterList;
import specs.SystemAdmin.GenericStorageList.CheckGenericStorageList;
import specs.SystemAdmin.PDFTemplateEdit.CheckPDFTemplateEdit;
import specs.SystemAdmin.SiteList.CheckSiteList;
import specs.SystemAdmin.UserGroupList.CheckUserGroupList;
import specs.SystemAdmin.UserList.CheckUserList;
import specs.SystemAdmin.WorkflowEmailList.CheckWorkflowEmailList;

/**
 * Created by philipsushkov on 2016-12-05.
 */


@RunWith(Suite.class)
@Suite.SuiteClasses({
        CheckUserList.class,
        CheckWorkflowEmailList.class,
        CheckAlertFilterList.class,
        CheckGenericStorageList.class,
        CheckPDFTemplateEdit.class,
        CheckSiteList.class,
        CheckUserGroupList.class,
        CheckGlobalModuleList.class,
        CheckLayoutDefinitionList.class,
        CheckModuleDefinitionList.class,
        CheckCssFileList.class,
        CheckIndexContent.class,
        CheckLinkToPageList.class,
        CheckLookupList.class,
        CheckAliasList.class,
        CheckMobileLinkList.class,
        CheckDomainList.class,
        CheckEditContentAdminPages.class,
        CheckFinancialReports.class,
        CheckPressReleaseCategories.class,
        CheckQuickLinkList.class,
        CheckDownloadList.class,
        CheckPersonList.class,
        CheckDepartmentList.class,
        CheckFaqList.class,
        CheckJobPostingList.class,
        CheckCompose.class
}
)

public class NavigationTest {

}
