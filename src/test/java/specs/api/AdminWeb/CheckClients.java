package specs.api.AdminWeb;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.api.AdminWeb.Auth;
import util.LocalDriverManager;

import java.io.IOException;

/**
 * Created by philipsushkov on 2017-07-24.
 */

public class CheckClients {

    @BeforeTest
    public void setUp() throws InterruptedException {
        new Auth(LocalDriverManager.getDriver()).getGoogleAuthPage();
    }

    @Test
    public void checkOAuth() throws IOException {

    }

    @AfterTest
    public void tearDown() {
    }

}
