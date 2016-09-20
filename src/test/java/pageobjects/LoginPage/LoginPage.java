package pageobjects.LoginPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.Dashboard.Dashboard;
import pageobjects.Page;
import pageobjects.PageObject;

/**
 * Created by patrickp on 2016-08-03.
 */
public class LoginPage extends Page {
    private final By emailField = By.id("txtUserName");
    private final By passwordField = By.name("txtPassword");
    private final By loginButton = By.name("btnSubmit");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public Dashboard loginUser() {
        waitForElementToAppear(emailField);
        //findElement(emailField).sendKeys("admin");
        findElement(passwordField).sendKeys("Song2Q4!"); //aes password
        findElement(passwordField).sendKeys("Song2Q4!_"); //kinross password
        pause(2000L);
        waitForElementToAppear(loginButton);
        retryClick(loginButton);

        return new Dashboard(getDriver());
    }
}
