package pageobjects.LoginPage;

import org.openqa.selenium.*;
import pageobjects.Dashboard.Dashboard;
import pageobjects.Page;
import specs.AbstractSpec;

import static org.junit.Assert.fail;


/**
 * Created by philips on 2016-11-02.
 */
public class LoginPage extends Page {
    private final By emailField = By.id("txtUserName");
    private final By passwordField = By.name("txtPassword");
    private final By loginButton = By.id("btnSubmit");
    private final By loginErrorMessage = By.id("errLogin");
    private final By dashboardLabel = By.cssSelector(".AdminContentDiv2 h1"); //this is actually on the dashboard page; used to confirm that login is successful


    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public Dashboard loginUser() throws Exception {

        waitForElementToAppear(emailField);

        /*
        if (AbstractSpec.getSessionID() != null) {
            System.out.println(AbstractSpec.getSessionID());
            driver.manage().addCookie(new Cookie("ASP.NET_SessionId", AbstractSpec.getSessionID()));
            driver.get(AbstractSpec.desktopUrl.toString());
        } else {
            findElement(emailField).sendKeys("admintest");
            findElement(passwordField).sendKeys("qwerty@01");

            pause(1000L);
            retryClick(loginButton);
            new Dashboard(driver).getURL();
        }
        */

        findElement(emailField).sendKeys("admintest");
        findElement(passwordField).sendKeys("qwerty@01");

        pause(1000L);
        retryClick(loginButton);
        new Dashboard(driver).getURL();

        return new Dashboard(getDriver());

        //JavascriptExecutor js = (JavascriptExecutor) driver;
        //WebElement elemSrc =  driver.findElement(passwordField);
        //js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", elemSrc, "value", "Song2Q4!");

        //findElement(passwordField).sendKeys("Song2Q4!");
        //pause(1000L);
        //waitForElementToAppear(loginButton);

    }

    public Dashboard loginUser(String username, String password) throws Exception {
        waitForElementToAppear(emailField);
        findElement(emailField).sendKeys(username);
        findElement(passwordField).sendKeys(password);
        pause(1000L);
        retryClick(loginButton);
        return new Dashboard(getDriver());
    }

    public boolean credentialsWork(String username, String password) throws Exception {
        waitForElementToAppear(emailField);
        findElement(emailField).sendKeys(username);
        findElement(passwordField).sendKeys(password);
        pause(1000L);
        retryClick(loginButton);

        pause(5000);

        if (doesElementExist(loginErrorMessage) && findElement(loginErrorMessage).isDisplayed()){
            findElement(emailField).clear();
            findElement(passwordField).clear();
            return false;
        }
        else if (doesElementExist(dashboardLabel) && findElement(dashboardLabel).getText().toLowerCase().contains("dashboard")){
            logoutFromAdmin();
            return true;
        }
        System.out.println("Dashboard label: "+findElement(dashboardLabel).getText());
        fail("Unexpected login result occurred.");
        return false;
    }

    public String[] sessionID() {

        //Get ASP.NET_SessionId from Browser Cookie
        String sessionIDname = "ASP.NET_SessionId";
        String sessionIDvalue = driver.manage().getCookieNamed("ASP.NET_SessionId").getValue();

        String sessionID[] = {sessionIDname, sessionIDvalue};

        AbstractSpec.setSessionID(sessionIDvalue);

        return sessionID;
    }

}
