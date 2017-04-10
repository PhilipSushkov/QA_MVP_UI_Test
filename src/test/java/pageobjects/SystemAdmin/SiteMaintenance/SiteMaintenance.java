package pageobjects.SystemAdmin.SiteMaintenance;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISystemAdmin;

/**
 * Created by philipsushkov on 2016-11-14.
 */

public class SiteMaintenance extends AbstractPageObject {
    private static By moduleTitle, btnGoLive, btnOneTouch, btnTwoFactorAuthentication, btnIFrames;

    public SiteMaintenance(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISystemAdmin.getProperty("divModule_Title"));
        btnGoLive = By.xpath(propUISystemAdmin.getProperty("btn_GoLive"));
        btnOneTouch = By.xpath(propUISystemAdmin.getProperty("btn_OneTouch"));
        btnTwoFactorAuthentication = By.xpath(propUISystemAdmin.getProperty("btn_TwoFactorAuthentication"));
        btnIFrames = By.xpath(propUISystemAdmin.getProperty("btn_IFrames"));
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
            wait.until(ExpectedConditions.visibilityOf(findElement(btnGoLive)));
            element = findElement(btnGoLive);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }


    public WebElement getOneTouchButton() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(btnOneTouch)));
            element = findElement(btnOneTouch);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;

    }


    public WebElement getTwoFactorAuthenticationButton() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(btnTwoFactorAuthentication)));
            element = findElement(btnTwoFactorAuthentication);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getIFramesButton() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(btnIFrames)));
            element = findElement(btnIFrames);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

}
