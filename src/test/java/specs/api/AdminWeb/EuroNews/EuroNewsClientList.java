package specs.api.AdminWeb.EuroNews;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.api.AdminWeb.Auth;
import pageobjects.api.AdminWeb.LeftMainMenu;
import specs.ApiAbstractSpec;
import util.LocalDriverManager;

/**
 * Created by philipsushkov on 2017-07-31.
 */

public class EuroNewsClientList extends ApiAbstractSpec {
    private static Auth auth;
    private static LeftMainMenu leftMainMenu;
    private static WebDriver driver;

    @BeforeTest
    public void setUp() throws InterruptedException {
        driver = LocalDriverManager.getDriver();
        auth = new Auth(driver, adminWebUrl, LocalDriverManager.getProxy());
        leftMainMenu = new LeftMainMenu(driver);
;
        // Authorization
        auth.getGoogleAuthPage();

        // Open Web Section
        auth.getWebSection();

        // Open Euro News Client List page in Web Section
        leftMainMenu.getEuroNewsClientListPage();
    }

    @Test
    public void checkEuroNewsClient() throws InterruptedException {

    }

    @AfterTest
    public void tearDown() {
    }

}
