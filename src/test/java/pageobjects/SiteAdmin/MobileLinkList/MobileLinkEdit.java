package pageobjects.SiteAdmin.MobileLinkList;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISiteAdmin;

/**
 * Created by philipsushkov on 2016-12-30.
 */

public class MobileLinkEdit extends AbstractPageObject {

    private static By moduleTitle, pageSelect, mobileViewSelect, saveAndSubmitButton;

    public MobileLinkEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISiteAdmin.getProperty("spanModule_Title"));
        pageSelect = By.xpath(propUISiteAdmin.getProperty("select_Page"));
        mobileViewSelect = By.xpath(propUISiteAdmin.getProperty("select_MobileView"));
        saveAndSubmitButton = By.xpath(propUISiteAdmin.getProperty("btn_SaveAndSubmit"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getPageSelect() {
        WebElement element = null;

        try {
            waitForElement(pageSelect);
            element = findElement(pageSelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getMobileViewSelect() {
        WebElement element = null;

        try {
            waitForElement(mobileViewSelect);
            element = findElement(mobileViewSelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getSaveAndSubmitButton() {
        WebElement element = null;

        try {
            waitForElement(saveAndSubmitButton);
            element = findElement(saveAndSubmitButton);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

}
