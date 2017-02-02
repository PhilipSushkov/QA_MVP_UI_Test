package pageobjects.SiteAdmin.LookupList;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISiteAdmin;

/**
 * Created by philipsushkov on 2016-12-20.
 */

public class LookupEdit extends AbstractPageObject {
    private static By moduleTitle, lookupTypeField, lookupTextField, lookupValueField;
    private static By additionalInfoField, activeCheckbox, saveAndSubmitButton;

    public LookupEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISiteAdmin.getProperty("spanModule_Title"));
        lookupTypeField = By.xpath(propUISiteAdmin.getProperty("input_LookupType"));
        lookupTextField = By.xpath(propUISiteAdmin.getProperty("input_LookupText"));
        lookupValueField = By.xpath(propUISiteAdmin.getProperty("input_LookupValue"));
        additionalInfoField = By.xpath(propUISiteAdmin.getProperty("input_AdditionalInfo"));
        activeCheckbox = By.xpath(propUISiteAdmin.getProperty("chk_Active"));
        saveAndSubmitButton = By.xpath(propUISiteAdmin.getProperty("btn_SaveAndSubmit"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getLookupTypeInput() {
        WebElement element = null;

        try {
            waitForElement(lookupTypeField);
            element = findElement(lookupTypeField);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getLookupTextInput() {
        WebElement element = null;

        try {
            waitForElement(lookupTextField);
            element = findElement(lookupTextField);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getLookupValueInput() {
        WebElement element = null;

        try {
            waitForElement(lookupValueField);
            element = findElement(lookupValueField);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getAdditionalInfoInput() {
        WebElement element = null;

        try {
            waitForElement(additionalInfoField);
            element = findElement(additionalInfoField);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getActiveCheckbox() {
        WebElement element = null;

        try {
            waitForElement(activeCheckbox);
            element = findElement(activeCheckbox);
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
