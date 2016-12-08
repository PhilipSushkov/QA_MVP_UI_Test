package pageobjects.SocialMedia;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by jasons on 2016-12-07.
 */
public class LinkedInAuthorization extends AbstractPageObject {

    private final By emailField = By.cssSelector("[placeholder=Email]");
    private final By passwordField = By.cssSelector("[placeholder=Password]");
    private final By allowAccessButton = By.cssSelector("[value='Allow access']");

    public LinkedInAuthorization(WebDriver driver) {
        super(driver);
    }

    public SocialMediaSummary allowAccessTo(String email, String password){
        waitForElement(emailField);
        findElement(emailField).sendKeys(email);
        findElement(passwordField).sendKeys(password);
        findElement(allowAccessButton).click();
        return new SocialMediaSummary(getDriver());
    }
}
