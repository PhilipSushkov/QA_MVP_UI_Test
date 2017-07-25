package specs.api.AdminWeb;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.api.AdminWeb.Auth;
import specs.ApiAbstractSpec;
import util.LocalDriverManager;


/**
 * Created by philipsushkov on 2017-07-24.
 */

public class CheckClients extends ApiAbstractSpec {

    @BeforeTest
    public void setUp() throws InterruptedException {

    }

    @Test
    public void checkOAuth() throws InterruptedException {
        new Auth(LocalDriverManager.getDriver(), adminWebUrl).getGoogleAuthPage();
    }

    @AfterTest
    public void tearDown() {
    }

}
