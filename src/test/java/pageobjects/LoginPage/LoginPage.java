package pageobjects.LoginPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import pageobjects.Dashboard.Dashboard;
import pageobjects.Page;
import pageobjects.PageObject;


/**
 * Created by philips on 2016-11-02.
 */
public class LoginPage extends Page {
    private final By emailField = By.id("txtUserName");
    private final By passwordField = By.name("txtPassword");
    private final By loginButton = By.id("btnSubmit");


    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public Dashboard loginUser() {

        waitForElementToAppear(emailField);
        findElement(emailField).sendKeys("admin");
        findElement(passwordField).sendKeys("Song2Q4!");
        pause(1000L);
        retryClick(loginButton);

        return new Dashboard(getDriver());

        //JavascriptExecutor js = (JavascriptExecutor) driver;
        //WebElement elemSrc =  driver.findElement(passwordField);
        //js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", elemSrc, "value", "Song2Q4!");

        //findElement(passwordField).sendKeys("Song2Q4!");
        //pause(1000L);
        //waitForElementToAppear(loginButton);

    }

    public String[] sessionID() {

        //Get ASP.NET_SessionId from Browser Cookie
        String sessionIDname = "ASP.NET_SessionId";
        String sessionIDvalue = driver.manage().getCookieNamed("ASP.NET_SessionId").getValue();

        String sessionID[] = {sessionIDname, sessionIDvalue};
        return sessionID;
    }

}
