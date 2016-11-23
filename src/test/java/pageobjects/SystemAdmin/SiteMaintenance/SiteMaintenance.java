package pageobjects.SystemAdmin.SiteMaintenance;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
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
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(buttonGoLive)));
            element = findElement(buttonGoLive);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }


    public WebElement getOneTouchButton() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(buttonOneTouchButton)));
            element = findElement(buttonOneTouchButton);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;

    }


    public WebElement getTwoFactorAuthenticationButton() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(buttonTwoFactorAuthenticationButton)));
            element = findElement(buttonTwoFactorAuthenticationButton);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

}
