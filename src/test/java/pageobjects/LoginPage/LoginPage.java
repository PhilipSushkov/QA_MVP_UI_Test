package pageobjects.LoginPage;

import org.openqa.selenium.*;
import pageobjects.Dashboard.Dashboard;
import pageobjects.Page;
import specs.AbstractSpec;
import util.Functions;

import static org.junit.Assert.fail;


/**
 * Created by philips on 2016-11-02.
 */
public class LoginPage extends Page {
    private static Dashboard dashboard;
    private final By emailField = By.id("txtUserName");
    private final By passwordField = By.name("txtPassword");
    private final By loginButton = By.id("btnSubmit");
    private final By loginErrorMessage = By.id("errLogin");
    private final By dashboardLabel = By.cssSelector(".AdminContentDiv2 h1"); //this is actually on the dashboard page; used to confirm that login is successful
    private final By logoutMenuItem = By.xpath("//li/a[contains(text(),'Logout')]");
    private static final long DEFAULT_PAUSE = 2500;


    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public Dashboard loginUser() throws Exception {
        int randNum = Functions.randInt(0, 19);

        waitForElementToAppear(emailField);

        //findElement(emailField).sendKeys("admintest"+randNum);
        //findElement(passwordField).sendKeys("qwerty@01!");

        //findElement(emailField).sendKeys("dannyl" /*+randNum*/);
        //findElement(passwordField).sendKeys("q4pass1234!");

        //findElement(emailField).sendKeys("philips");
        //findElement(passwordField).sendKeys("qwerty@01");

        //findElement(emailField).sendKeys("admintest0");
        //findElement(passwordField).sendKeys("qwerty@01");

        //findElement(emailField).sendKeys("marcoss");
        //findElement(passwordField).sendKeys("q4pass1234!");

        //findElement(emailField).sendKeys("rt450");
        //indElement(passwordField).sendKeys("q4pass1234!");

        findElement(emailField).sendKeys("juntianz");
        findElement(passwordField).sendKeys("q4pass1234!");

        Thread.sleep(DEFAULT_PAUSE);
        retryClick(loginButton);
        Thread.sleep(DEFAULT_PAUSE);
        waitForElement(logoutMenuItem);
        new Dashboard(driver).getUrl();
        
        return new Dashboard(getDriver());

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

        //JavascriptExecutor js = (JavascriptExecutor) driver;
        //WebElement elemSrc =  driver.findElement(passwordField);
        //js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", elemSrc, "value", "Song2Q4!");

        //findElement(passwordField).sendKeys("Song2Q4!");
        //pause(1000L);
        //waitForElementToAppear(loginButton);

    }

    // alternative login method for using a different username and password (use only with valid credentials)
    public Dashboard loginUser(String username, String password) throws Exception {
        waitForElementToAppear(emailField);
        findElement(emailField).sendKeys(username);
        findElement(passwordField).sendKeys(password);
        pause(1000L);
        retryClick(loginButton);
        return new Dashboard(getDriver());
    }

    /*
        Checks whether you can login with a given username and password
        If login is successful, it will log out and then return true
        If error message is presented, it will clear the fields and then return false
        NOTE: Repeatedly running this method with incorrect passwords may result in the account becoming temporarily locked out
     */
    public boolean credentialsWork(String username, String password) throws Exception {
        waitForElementToAppear(emailField);
        findElement(emailField).sendKeys(username);
        findElement(passwordField).sendKeys(password);
        pause(1000L);
        retryClick(loginButton);

        pause(5000);

        // checks if login error message is present
        if (doesElementExist(loginErrorMessage) && findElement(loginErrorMessage).isDisplayed()){
            findElement(emailField).clear();
            findElement(passwordField).clear();
            return false;
        }
        // checks if dashboard has been loaded
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
