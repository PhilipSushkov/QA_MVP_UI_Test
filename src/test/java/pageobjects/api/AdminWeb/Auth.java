package pageobjects.api.AdminWeb;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;
import pageobjects.PageObject;
import specs.ApiAbstractSpec;

import java.net.URL;

import static specs.ApiAbstractSpec.propAPI;

/**
 * Created by philipsushkov on 2017-07-25.
 */

public class Auth extends AbstractPageObject {
    private static By loginUsingGoogleBtn, loginWithGoogleBtn, googleEmailInp, googlePasswordInp;
    private static By googleIdNextBtn, googlePsNextBtn;
    private static String sEmail, sPassword;
    private URL adminWebUrl;
    private static final long DEFAULT_PAUSE = 2000;

    public Auth(WebDriver driver, URL adminWebUrl) {
        super(driver);
        this.adminWebUrl = adminWebUrl;

        sEmail = propAPI.getProperty("login");
        sPassword = propAPI.getProperty("password");

        loginUsingGoogleBtn = By.xpath(propAPI.getProperty("btnLoginUsingGoogle"));
        loginWithGoogleBtn = By.xpath(propAPI.getProperty("btnLoginWithGoogle"));
        googleEmailInp = By.xpath(propAPI.getProperty("inpGoogleEmail"));
        googlePasswordInp = By.xpath(propAPI.getProperty("inpGooglePassword"));
        googleIdNextBtn = By.xpath(propAPI.getProperty("btnGoogleIdNext"));
        googlePsNextBtn = By.xpath(propAPI.getProperty("btnGooglePsNext"));
    }

    public String getGoogleAuthPage() throws InterruptedException {

        String sAdminWebUrl = adminWebUrl.toString();
        driver.get(sAdminWebUrl);
        Thread.sleep(DEFAULT_PAUSE);

        String winHandleBefore = driver.getWindowHandle();

        waitForElement(loginUsingGoogleBtn);
        findElement(loginUsingGoogleBtn).click();

        waitForElement(loginWithGoogleBtn);
        findElement(loginWithGoogleBtn).click();

        // Switch to new window opened
        windowDidLoad("Sign in - Google Accounts");
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        // Perform the actions on new window
        waitForElement(googleEmailInp);
        findElement(googleEmailInp).sendKeys(sEmail);
        findElement(googleIdNextBtn).click();

        Thread.sleep(DEFAULT_PAUSE);

        waitForElement(googlePasswordInp);
        findElement(googlePasswordInp).sendKeys(sPassword);
        findElement(googlePsNextBtn).click();

        Thread.sleep(DEFAULT_PAUSE);

        driver.switchTo().window(winHandleBefore);

        return driver.getTitle();

    }

}
