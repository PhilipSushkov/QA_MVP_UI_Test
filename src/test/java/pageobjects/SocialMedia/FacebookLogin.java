package pageobjects.SocialMedia;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISocialMedia;

/**
 * Created by jasons on 2016-12-09.
 */
public class FacebookLogin extends AbstractPageObject {
    private static By emailField, passwordField, LogInButton;

    public FacebookLogin(WebDriver driver) {
        super(driver);
        emailField = By.id(propUISocialMedia.getProperty("input_Email_Facebook"));
        passwordField = By.id(propUISocialMedia.getProperty("input_Password_Facebook"));
        LogInButton = By.id(propUISocialMedia.getProperty("btn_LogIn_Facebook"));
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
