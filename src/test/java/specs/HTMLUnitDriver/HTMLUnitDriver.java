package specs.HTMLUnitDriver;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.HttpEntity;
//import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import org.json.simple.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by philipsushkov on 2016-11-18.
 */
public class HTMLUnitDriver {

    private final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:45.0)";
    private String access_token = null;
    private HttpClient client = null;

    @BeforeTest
    public void setup() throws Exception {
        client = HttpClientBuilder.create().build();

        //To hide warnings logs from execution console.
        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.OFF);
    }

    @AfterTest
    public void tearDown() throws Exception {

    }

    @Test (priority = 0)
    public void Q4DesktopAuth () throws IOException {

        String urlAuth = "https://staging.q4touch.com/api/auth";
        String appver = "appver=1.2.6";
        String urlAuthQuery = urlAuth+"?"+appver;

        HttpPost post = new HttpPost(urlAuthQuery);
        post.setHeader("User-Agent", USER_AGENT);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("id", "UserLogin-1"));
        urlParameters.add(new BasicNameValuePair("password", "qwerty@01"));
        urlParameters.add(new BasicNameValuePair("product", "desktop"));
        urlParameters.add(new BasicNameValuePair("user", "pylypsushkov@gmail.com"));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);

        System.out.println("Response Code: "
                + response.getStatusLine().getStatusCode());

        //JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;

        if(response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            //System.out.println("Entity:" + entity);
            if (entity != null) {
                String responseBody = EntityUtils.toString(entity);
                System.out.println("finalResult" + responseBody.toString());

                jsonObject = new JSONObject(responseBody.toString());

                Boolean success = (Boolean) jsonObject.get("success");
                System.out.println(success);

                JSONObject token = (JSONObject) jsonObject.get("token");
                access_token = (String) token.get("access_token");
                System.out.println(access_token);
            }

        }

        Assert.assertNotNull(access_token);

    }


    @Test (priority = 1)
    public void Q4DesktopHomePage() throws Exception {

        String urlHome = "https://aestest.s1.q4web.newtest/admin/";

        //WebDriver driver = new ChromeDriver();
        //HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME);
        //HtmlUnitDriver driver = new HtmlUnitDriver();
        //driver.setJavascriptEnabled(true);

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[] {"--web-security=no", "--ignore-ssl-errors=yes"});
        WebDriver driver = new PhantomJSDriver(caps);

        //WebDriver driver = new PhantomJSDriver(service_args=['--ignore-ssl-errors=true']);
        //WebDriver driver = new PhantomJSDriver();
        driver.get(urlHome);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        System.out.println(driver.getPageSource());
        System.out.println(driver.getCurrentUrl());

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.cookie = \"ASP.NET_SessionId=41giianresogaawzw0eccyz2;\"");


        String urlAdmin = "https://aestest.s1.q4web.newtest/admin/";

        driver.get(urlAdmin.toString());
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        System.out.println(driver.getPageSource());
        System.out.println(driver.getCurrentUrl());

        driver.findElement(By.id("txtUserName")).clear();
        driver.findElement(By.id("txtUserName")).sendKeys("admin");
        driver.findElement(By.name("txtPassword")).sendKeys("Song2Q4!");
        //driver.findElement(By.name("txtPassword")).clear();

        //JavascriptExecutor js2 = (JavascriptExecutor) driver;
        //WebElement elemSrc =  driver.findElement(By.name("txtPassword"));
        //js2.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", elemSrc, "value", "Song2Q4!");
        //setAttributeValue(elemSrc, "value");


        //driver.findElement(By.name("txtPassword")).sendKeys("Song2Q4!");
        //JavascriptExecutor javascript = (JavascriptExecutor) driver;
        //String pagetitle=(String)javascript.executeScript("return document.title");
        //System.out.println("My Page Title Is  : "+pagetitle);
        //javascript.executeScript("document.getElementByName('txtPassword').setAttribute('value', 'Song2Q4!')");
        driver.findElement(By.id("btnSubmit")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("btnSubmit")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("btnSubmit")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        Thread.sleep(10000);

        System.out.println(driver.getPageSource());
        System.out.println(driver.getCurrentUrl());

        //WebElement searchField = driver.findElement(By.name("search"));
        //Assert.assertNotNull(searchField);

        //WebElement loginButton = driver.findElement(By.cssSelector(".login .x-button.login-button"));
        //Assert.assertNotNull(loginButton);

    }




}
