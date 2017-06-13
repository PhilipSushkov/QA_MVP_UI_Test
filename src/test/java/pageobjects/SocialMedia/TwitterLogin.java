package pageobjects.SocialMedia;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISocialMedia;

/**
 * Created by zacharyk on 2017-06-09.
 */
public class TwitterLogin extends AbstractPageObject {
    private static By usernameField, passwordField, authorizeButton, reAuthorizeButton;

    public TwitterLogin(WebDriver driver) {
        super(driver);
        usernameField = By.xpath(propUISocialMedia.getProperty("input_Username_Twitter"));
        passwordField = By.xpath(propUISocialMedia.getProperty("input_Password_Twitter"));
        authorizeButton = By.xpath(propUISocialMedia.getProperty("btn_authorizeApp_Twitter"));
    }

    public SocialMediaSummary authorizeApp(String username, String password) {
        waitForElement(usernameField);
        findElement(usernameField).clear();
        findElement(usernameField).sendKeys(username);
        findElement(passwordField).sendKeys(password);
        findElement(authorizeButton).click();
        return new SocialMediaSummary(getDriver());
    }

    public SocialMediaSummary reAuthorizeApp() {
        waitForElement(authorizeButton);
        findElement(authorizeButton).click();
        return new SocialMediaSummary(getDriver());
    }

}
