package testrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import pageobjects.SiteAdmin.CssFileList.CssFileList;
import pageobjects.SiteAdmin.MobileLinkList.MobileLinkList;
import pageobjects.SiteAdmin.ModuleDefinitionList.ModuleDefinitionList;
import specs.LoginPage.EnterToAdmin;
import specs.PressReleases.AddNewPressRelease;
import specs.Presentations.AddNewPresentation;
import specs.PublicSite.CheckPublicSite;
import specs.Events.AddNewEvent;
import specs.SiteAdmin.AliasList.CheckAliasList;
import specs.SiteAdmin.GlobalModuleList.CheckGlobalModuleList;
import specs.SiteAdmin.IndexContent.CheckIndexContent;
import specs.SiteAdmin.LayoutDefinitionList.CheckLayoutDefinitionList;
import specs.SiteAdmin.LinkToPageList.CheckLinkToPageList;
import specs.SiteAdmin.LookupList.CheckLookupList;
import specs.SystemAdmin.PDFTemplateEdit.CheckPDFTemplateEdit;
import specs.SystemAdmin.SiteList.CheckSiteList;
import specs.SystemAdmin.UserGroupList.CheckUserGroupList;
import specs.SystemAdmin.UserList.CheckUserList;
import specs.SystemAdmin.WorkflowEmailList.CheckWorkflowEmailList;
import specs.SystemAdmin.AlertFilterList.CheckAlertFilterList;
import specs.SystemAdmin.GenericStorageList.CheckGenericStorageList;

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
        CheckUserList.class,
        CheckWorkflowEmailList.class,
        CheckAlertFilterList.class,
        CheckGenericStorageList.class,
        CheckPDFTemplateEdit.class,
        CheckSiteList.class,
        CheckUserGroupList.class,
        CheckGlobalModuleList.class,
        CheckLayoutDefinitionList.class,
        ModuleDefinitionList.class,
        CssFileList.class,
        CheckIndexContent.class,
        CheckLinkToPageList.class,
        CheckLookupList.class,
        CheckAliasList.class,
        MobileLinkList.class
        }
)

public class SmokeTest {
}
