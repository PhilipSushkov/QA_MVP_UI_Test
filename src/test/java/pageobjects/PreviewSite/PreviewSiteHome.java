package pageobjects.PreviewSite;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;
import pageobjects.LiveSite.HomePage;

/**
 * Created by jasons on 2016-11-30.
 */
public class PreviewSiteHome extends AbstractPageObject {

    private final By investorsLink = By.linkText("Investors");

    public PreviewSiteHome(WebDriver driver) {
        super(driver);
    }

    public HomePage goToInvestorsPage(){
        waitForElementToAppear(investorsLink);
        findVisibleElement(investorsLink).click();
        return new HomePage(getDriver());
    }
}
