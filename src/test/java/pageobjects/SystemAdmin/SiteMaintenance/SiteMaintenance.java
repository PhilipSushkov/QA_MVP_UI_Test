package pageobjects.SystemAdmin.SiteMaintenance;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

/**
 * Created by philipsushkov on 2016-11-14.
 */

public class SiteMaintenance extends AbstractPageObject {
    private final By moduleTitle = By.xpath("//div[contains(@id, 'SiteMaintenanceApp')]/h1/span[contains(@id,'ModuleTitle')]");
    private final By buttonGoLive = By.xpath("//button[contains(@ng-click, 'goLive()')]");
    private final By buttonOneTouchButton = By.xpath("//button[contains(@ng-click, 'toggleOneTouch()')]");
    private final By buttonTwoFactorAuthenticationButton = By.xpath("//button[contains(@ng-click, 'toggleTwoFactorAuthentication()')]");

    public SiteMaintenance(WebDriver driver) {
        super(driver);
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOf(findElement(moduleTitle)));
        return findElement(moduleTitle).getText();
    }

    public WebElement getGoLiveButton() {
        wait.until(ExpectedConditions.visibilityOf(findElement(buttonGoLive)));
        return findElement(buttonGoLive);
    }

    public WebElement getOneTouchButton() {
        wait.until(ExpectedConditions.visibilityOf(findElement(buttonOneTouchButton)));
        return findElement(buttonOneTouchButton);
    }

    public WebElement getTwoFactorAuthenticationButton() {
        wait.until(ExpectedConditions.visibilityOf(findElement(buttonTwoFactorAuthenticationButton)));
        return findElement(buttonTwoFactorAuthenticationButton);
    }

}
