package testrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.LoginPage.EnterToAdmin;
import specs.PressReleases.AddNewPressRelease;
import specs.Presentations.AddNewPresentation;
import specs.PublicSite.CheckPublicSite;
import specs.Events.AddNewEvent;

/**
 * Created by philips on 2016-11-07.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        EnterToAdmin.class,
        AddNewPressRelease.class,
        AddNewPresentation.class,
        AddNewEvent.class,
        CheckPublicSite.class
})

public class SmokeTest {
}
