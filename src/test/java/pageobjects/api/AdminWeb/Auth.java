package pageobjects.api.AdminWeb;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;
import specs.ApiAbstractSpec;

import static specs.ApiAbstractSpec.propAPI;

/**
 * Created by philipsushkov on 2017-07-25.
 */

public class Auth extends ApiAbstractSpec {
    private WebDriver phDriver;
    private static final long DEFAULT_PAUSE = 2000;

    public Auth(WebDriver phDriver) {
        this.phDriver = phDriver;
    }

    public void getGoogleAuthPage() throws InterruptedException {

        System.out.println("getGoogleAuthPage method started");

        String sAdminWebUrl = adminWebUrl.toString();
        phDriver.get(sAdminWebUrl);
        Thread.sleep(DEFAULT_PAUSE);

        String winHandleBefore = phDriver.getWindowHandle();

        //findElement(loginButton).click();
        //findElement(googleLoginButton).click();
    }

}
