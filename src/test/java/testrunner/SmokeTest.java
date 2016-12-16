package testrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.Dashboard.CheckChangePassword;
import specs.LoginPage.EnterToAdmin;
import specs.ContentAdmin.PressReleases.PublishPressRelease;
import specs.ContentAdmin.Presentations.PublishPresentation;
import specs.PreviewSite.CheckPreviewSite;
import specs.PublicSite.CheckPublicSite;
import specs.ContentAdmin.Events.PublishEvent;
import specs.SocialMedia.CheckLinkedIn;
import specs.SystemAdmin.UserList.CreateNewUser;

/**
 * Created by philips on 2016-11-07.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        EnterToAdmin.class,
        PublishPressRelease.class,
        PublishPresentation.class,
        PublishEvent.class,
        CheckPublicSite.class,
        CheckPreviewSite.class,
        CheckChangePassword.class,
        CreateNewUser.class,
        CheckLinkedIn.class
        }
)

public class SmokeTest {
}
