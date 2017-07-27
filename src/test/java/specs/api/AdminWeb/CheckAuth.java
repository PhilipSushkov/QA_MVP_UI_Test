package specs.api.AdminWeb;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.api.AdminWeb.Auth;
import specs.ApiAbstractSpec;
import util.LocalDriverManager;


/**
 * Created by philipsushkov on 2017-07-24.
 */

public class CheckAuth extends ApiAbstractSpec {

    @BeforeTest
    public void setUp() throws InterruptedException {

    }

    @Test
    public void checkOAuth() throws InterruptedException {
        final String expectedTitle = "Q4 Admin";
        final String webBackgroundColor = "rgba(26, 188, 156, 1)";

        Assert.assertEquals(new Auth(LocalDriverManager.getDriver(), adminWebUrl).getGoogleAuthPage(), expectedTitle,
                "Actual page title doesn't match to expected. Probably not Q4 Admin.");
        Assert.assertEquals(new Auth(LocalDriverManager.getDriver(), adminWebUrl).getWebSection(), webBackgroundColor,
                "Actual Background Colour doesn't match to expected. Probably not Q4 Admin Web section.");
        new Auth(LocalDriverManager.getDriver(), adminWebUrl).getBrowserMobResponse();
    }

    @AfterTest
    public void tearDown() {
    }

}
