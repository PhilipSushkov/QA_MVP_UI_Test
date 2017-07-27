package pageobjects.api.AdminWeb;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.core.har.HarRequest;
import net.lightbody.bmp.core.har.HarResponse;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import static io.github.seleniumquery.SeleniumQuery.$;

import static specs.ApiAbstractSpec.propAPI;

/**
 * Created by philipsushkov on 2017-07-25.
 */

public class Auth extends AbstractPageObject {
    private static By loginUsingGoogleBtn, loginWithGoogleBtn, googleEmailInp, googlePasswordInp;
    private static By googleIdNextBtn, googlePsNextBtn;
    private static String sEmail, sPassword, sProductWeb;
    private URL adminWebUrl;
    private BrowserMobProxy proxy = new BrowserMobProxyServer();
    private static final long DEFAULT_PAUSE = 2000;

    public Auth(WebDriver driver, URL adminWebUrl, BrowserMobProxy selProxy) {
        super(driver);
        this.adminWebUrl = adminWebUrl;
        this.proxy = selProxy;

        sEmail = propAPI.getProperty("login");
        sPassword = propAPI.getProperty("password");

        sProductWeb = propAPI.getProperty("productItemWeb");

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

    public String getWebSection() throws InterruptedException {
        return new ProductMenu(driver).changeMenuItem(sProductWeb);
    }

    public void getSecondPage() throws InterruptedException {
        //driver.get("https://admin-dev.q4inc.com/#/euroNews");

        // or use ("decorate") any previously existing driver
        $.driver().use(driver);

        // starts the driver (if not started already) and opens the URL
        $.url("https://admin-dev.q4inc.com/#/euroNews");

        // interact with the page
        $(".search-input").val("4imprint"); // the keys are actually typed!
        Thread.sleep(DEFAULT_PAUSE);
        //$(":tr.ui-widget-content").click(); // simulates a real user click (not just the JS event)

        // Besides the short syntax and the jQuery behavior you already know,
        // other very useful function in seleniumQuery is .waitUntil(),
        // handy for dealing with user-waiting actions (specially in Ajax enabled pages):
        //$(".search-input").waitUntil().is(":visible");

        //System.out.println(resultsText);
        // should print something like: About 24,900,000 results (0.37 seconds)
    }

    public void getBrowserMobResponse() throws InterruptedException {

        //proxy.newHar("Har-EuroNews");
        proxy.newPage("Page-EuroNews");
        proxy.enableHarCaptureTypes(CaptureType.REQUEST_HEADERS, CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_HEADERS, CaptureType.RESPONSE_CONTENT);

        driver.get("https://admin-dev.q4inc.com/#/euroNews");
        Har har = proxy.getHar();
        Thread.sleep(DEFAULT_PAUSE*6);

        for (HarEntry entry : har.getLog().getEntries()) {
            HarRequest request = entry.getRequest();
            HarResponse response = entry.getResponse();

            System.out.println(request.getUrl() + " " + response.getStatus());
        }

        // Write HAR Data in a File
        File harFile = new File("euroNews");
        try {
            har.writeTo(harFile);
        } catch (IOException ex) {
            System.out.println (ex.toString());
            System.out.println("Could not find file: euroNews");
        }

    }
}
