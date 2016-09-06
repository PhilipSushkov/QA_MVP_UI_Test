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
    private final By emailField = By.name("user");
    private final By passwordField = By.name("password");
    private final By loginButton = By.cssSelector(".login .x-button.login-button");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public Dashboard loginUser() {
        waitForElementToAppear(emailField);
        findElement(emailField).sendKeys("patrickp@q4inc.com");
        findElement(passwordField).sendKeys("patrick!");
        pause(2000L);
        waitForElementToAppear(loginButton);
        retryClick(loginButton);

        return new Dashboard(getDriver());
    }
}
