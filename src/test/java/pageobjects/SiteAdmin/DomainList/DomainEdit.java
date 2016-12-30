package pageobjects.SiteAdmin.DomainList;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISiteAdmin;

/**
 * Created by philipsushkov on 2016-12-30.
 */

public class DomainEdit extends AbstractPageObject {

    private static By moduleTitle, domainNameInput, landingPageSelect, saveAndSubmitButton;
    private static By addNewAltLink, domainNameAltInput, cancelAltButton;

    public DomainEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISiteAdmin.getProperty("spanModule_Title"));
        domainNameInput = By.xpath(propUISiteAdmin.getProperty("input_DomainName"));
        landingPageSelect = By.xpath(propUISiteAdmin.getProperty("select_LandingPage"));
        addNewAltLink = By.xpath(propUISiteAdmin.getProperty("href_AddNewAlt"));
        domainNameAltInput = By.xpath(propUISiteAdmin.getProperty("input_DomainNameAlt"));
        cancelAltButton = By.xpath(propUISiteAdmin.getProperty("btn_CancelAlt"));
        saveAndSubmitButton = By.xpath(propUISiteAdmin.getProperty("btn_SaveAndSubmit"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getDomainNameInput() {
        WebElement element = null;

        try {
            waitForElement(domainNameInput);
            element = findElement(domainNameInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getLandingPageSelect() {
        WebElement element = null;

        try {
            waitForElement(landingPageSelect);
            element = findElement(landingPageSelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getDomainNameAltInput() {
        WebElement element = null;

        try {
            waitForElement(addNewAltLink);
            findElement(addNewAltLink).click();

            waitForElement(domainNameAltInput);
            element = findElement(domainNameAltInput);

            waitForElement(cancelAltButton);
            findElement(cancelAltButton).click();

            waitForElement(addNewAltLink);
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
