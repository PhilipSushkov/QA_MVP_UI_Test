package pageobjects.SocialMedia;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by jasons on 2016-12-09.
 */
public class FacebookLogin extends AbstractPageObject {

    private final By emailField = By.id("email");
    private final By passwordField = By.id("pass");
    private final By LogInButton = By.id("loginbutton");

    public FacebookLogin(WebDriver driver) {
        super(driver);
    }

    public SocialMediaSummary allowAccessTo(String email, String password){
        waitForElement(emailField);
        findElement(emailField).clear();
        findElement(emailField).sendKeys(email);
        findElement(passwordField).sendKeys(password);
        findElement(LogInButton).click();
        return new SocialMediaSummary(getDriver());
    }
}
