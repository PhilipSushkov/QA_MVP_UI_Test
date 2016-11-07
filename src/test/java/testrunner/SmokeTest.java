package testrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.LoginPage.EnterToAdmin;
import specs.PressReleases.AddNewPressRelease;
import specs.PublicSite.CheckPublicSite;

/**
 * Created by philips on 2016-11-07.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        EnterToAdmin.class,
        //AddNewPressRelease.class,
        CheckPublicSite.class
})

public class SmokeTest {
}
