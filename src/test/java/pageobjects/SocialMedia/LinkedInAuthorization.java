package pageobjects.SocialMedia;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISocialMedia;

/**
 * Created by jasons on 2016-12-07.
 */
public class LinkedInAuthorization extends AbstractPageObject {
    private static By emailField, passwordField, allowAccessButton;

    public LinkedInAuthorization(WebDriver driver) {
        super(driver);
        emailField = By.cssSelector(propUISocialMedia.getProperty("input_Email_LinkedIn"));
        passwordField = By.cssSelector(propUISocialMedia.getProperty("input_Password_LinkedIn"));
        allowAccessButton = By.cssSelector(propUISocialMedia.getProperty("btn_allowAccess_LinkedIn"));
    }

    public SocialMediaSummary allowAccessTo(String email, String password){
        waitForElement(emailField);
        findElement(emailField).sendKeys(email);
        findElement(passwordField).sendKeys(password);
        findElement(allowAccessButton).click();
        return new SocialMediaSummary(getDriver());
    }
}
