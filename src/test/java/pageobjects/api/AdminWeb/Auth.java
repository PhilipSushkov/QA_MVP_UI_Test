package pageobjects.api.AdminWeb;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.core.har.*;
import net.lightbody.bmp.proxy.CaptureType;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import static specs.ApiAbstractSpec.propAPI;

/**
 * Created by philipsushkov on 2017-07-25.
 */

public class Auth extends AbstractPageObject {
    private static By loginUsingGoogleBtn, loginWithGoogleBtn, googleEmailInp, googlePasswordInp;
    private static By googleIdNextBtn, googlePsNextBtn, productDown;
    private static String sEmail, sPassword, sProductWeb;
    private static final long DEFAULT_PAUSE = 2000;

    public Auth(WebDriver driver) {
        super(driver);

        sEmail = propAPI.getProperty("login");
        sPassword = propAPI.getProperty("password");

        sProductWeb = propAPI.getProperty("productItemWeb");

        loginUsingGoogleBtn = By.xpath(propAPI.getProperty("btnLoginUsingGoogle"));
        loginWithGoogleBtn = By.xpath(propAPI.getProperty("btnLoginWithGoogle"));
        googleEmailInp = By.xpath(propAPI.getProperty("inpGoogleEmail"));
        googlePasswordInp = By.xpath(propAPI.getProperty("inpGooglePassword"));
        googleIdNextBtn = By.xpath(propAPI.getProperty("btnGoogleIdNext"));
        googlePsNextBtn = By.xpath(propAPI.getProperty("btnGooglePsNext"));
        productDown = By.cssSelector(propAPI.getProperty("dropdown_Product"));
    }

    public String getGoogleAuthPage() throws InterruptedException {

        Thread.sleep(DEFAULT_PAUSE);

        String winHandleBefore = driver.getWindowHandle();

        waitForElementAndClick(driver, loginUsingGoogleBtn, DEFAULT_PAUSE);
        waitForElementAndClick(driver, loginWithGoogleBtn, DEFAULT_PAUSE);
        Thread.sleep(DEFAULT_PAUSE);
        System.out.println("Google Sign In page should open");

        // Switch to new window opened
        windowDidLoad("Sign in - Google Accounts");
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }
        Thread.sleep(DEFAULT_PAUSE);

        // Perform the actions on new window
        waitForElementRefresh(driver, googleEmailInp, DEFAULT_PAUSE);
        findElement(googleEmailInp).sendKeys(sEmail);
        findElement(googleIdNextBtn).click();

        Thread.sleep(DEFAULT_PAUSE);
        System.out.println("Google Password page should open");

        waitForElement(googlePasswordInp);
        findElement(googlePasswordInp).sendKeys(sPassword);
        findElement(googlePsNextBtn).click();

        Thread.sleep(DEFAULT_PAUSE);

        driver.switchTo().window(winHandleBefore);
        System.out.println("Q4 Admin Home page should open");

        waitForElementRefresh(driver, productDown, DEFAULT_PAUSE);

        return driver.getTitle();

    }

    public String getWebSection() throws InterruptedException {
        return new ProductMenu(driver).changeMenuItem(sProductWeb);
    }

    public void getBrowserMobResponse() throws InterruptedException {
        /*
        JSONParser parser;
        HttpClient client;
        String reguestUrl;
        int i = 0;

        //  enable more detailed HAR capture, if desired (see CaptureType for the complete list)
        proxy.enableHarCaptureTypes(CaptureType.REQUEST_HEADERS, CaptureType.RESPONSE_HEADERS);

        // create a new HAR with the label "admin-dev.q4inc.com/#/euroNews"
        proxy.newHar("admin-dev.q4inc.com/#/euroNews");
        //proxy.newPage("Page-EuroNews");

        // open https://admin-dev.q4inc.com/#/euroNews
        driver.get("https://admin-dev.q4inc.com/#/euroNews");
        Thread.sleep(DEFAULT_PAUSE);

        // get the HAR data
        Har har = proxy.getHar();
        Thread.sleep(DEFAULT_PAUSE);

        // Get Request and Response headers data
        for (HarEntry entry : har.getLog().getEntries()) {
            HarRequest request = entry.getRequest();
            HarResponse response = entry.getResponse();

            List<HarNameValuePair> harList = request.getHeaders();

            // Analyze request for euroadmin-dev.q4api.com domain only and GET methods only
            if (harList.get(0).getValue().equals("euroadmin-dev.q4api.com") && request.getMethod().equals("GET")) {
                reguestUrl = request.getUrl();
                System.out.println(request.getUrl() + " " + response.getStatus());

                parser = new JSONParser();
                client = HttpClientBuilder.create().build();

                // Send Http Api request for chosen upper URL
                HttpGet get = new HttpGet(reguestUrl);
                List<HarNameValuePair> params = request.getHeaders();
                for (HarNameValuePair param : params) {
                    get.setHeader(param.getName(), param.getValue());
                }

                // Analyze Http Response and extract JSON-data
                try {
                    HttpResponse httpResponse = client.execute(get);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    if (httpEntity != null) {
                        String responseBody = EntityUtils.toString(httpEntity);
                        JSONObject jsonResponse = (JSONObject) parser.parse(responseBody);

                        // Write JSON-data to the file
                        FileWriter file = new FileWriter(i + "_euroNewsData.json");
                        i++;
                        file.write(jsonResponse.toJSONString().replace("\\", ""));
                        file.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

        }
        */

        /*
        // Write HAR Data in a File
        File harFile = new File("euroNews");
        try {
            har.writeTo(harFile);
        } catch (IOException ex) {
            System.out.println (ex.toString());
            System.out.println("Could not find file: euroNews");
        }
        */

    }

}
