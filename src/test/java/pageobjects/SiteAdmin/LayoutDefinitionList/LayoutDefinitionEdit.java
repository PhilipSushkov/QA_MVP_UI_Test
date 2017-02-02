package pageobjects.SiteAdmin.LayoutDefinitionList;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISiteAdmin;

/**
 * Created by philipsushkov on 2016-12-20.
 */

public class LayoutDefinitionEdit extends AbstractPageObject {
    private static By moduleTitle, friendlyNameField, descriptionField, defaultLayoutSelect, activeCheckbox, saveAndSubmitButton;

    public LayoutDefinitionEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISiteAdmin.getProperty("spanModule_Title"));
        friendlyNameField = By.xpath(propUISiteAdmin.getProperty("input_FriendlyName"));
        descriptionField = By.xpath(propUISiteAdmin.getProperty("input_Description"));
        defaultLayoutSelect = By.xpath(propUISiteAdmin.getProperty("select_DefaultLayout"));
        activeCheckbox = By.xpath(propUISiteAdmin.getProperty("chk_Active"));
        saveAndSubmitButton = By.xpath(propUISiteAdmin.getProperty("btn_SaveAndSubmit"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getFriendlyNameInput() {
        WebElement element = null;

        try {
            waitForElement(friendlyNameField);
            element = findElement(friendlyNameField);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getDescriptionInput() {
        WebElement element = null;

        try {
            waitForElement(descriptionField);
            element = findElement(descriptionField);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getDefaultLayoutSelect() {
        WebElement element = null;

        try {
            waitForElement(defaultLayoutSelect);
            element = findElement(defaultLayoutSelect);
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
