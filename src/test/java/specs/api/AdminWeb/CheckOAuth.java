package specs.api.AdminWeb;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.api.AdminWeb.OAuth;

import java.io.IOException;

/**
 * Created by philipsushkov on 2017-07-24.
 */

public class CheckOAuth {
    private static OAuth oAuth;

    @BeforeTest
    public void setUp() {
        oAuth = new OAuth();
    }

    @Test
    public void checkOAuth() throws IOException {

    }

    @AfterTest
    public void tearDown() {
    }

}
