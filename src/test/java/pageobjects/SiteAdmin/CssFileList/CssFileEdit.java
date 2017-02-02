package pageobjects.SiteAdmin.CssFileList;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISiteAdmin;

/**
 * Created by philipsushkov on 2016-12-20.
 */

public class CssFileEdit extends AbstractPageObject {
    private static By moduleTitle, cssNameField, cssHighlightingCheckbox, cssBodyTextarea, activeCheckbox, saveAndSubmitButton;

    public CssFileEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISiteAdmin.getProperty("spanModule_Title"));
        cssNameField = By.xpath(propUISiteAdmin.getProperty("input_CssName"));
        cssHighlightingCheckbox = By.xpath(propUISiteAdmin.getProperty("chk_CssHighlighting"));
        cssBodyTextarea = By.xpath(propUISiteAdmin.getProperty("txtarea_CssBody"));
        activeCheckbox = By.xpath(propUISiteAdmin.getProperty("chk_Active"));
        saveAndSubmitButton = By.xpath(propUISiteAdmin.getProperty("btn_SaveAndSubmit"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getCssNameInput() {
        WebElement element = null;

        try {
            waitForElement(cssNameField);
            element = findElement(cssNameField);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getCssHighlightingCheckbox() {
        WebElement element = null;

        try {
            waitForElement(cssHighlightingCheckbox);
            element = findElement(cssHighlightingCheckbox);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getCssBodyTextarea() {
        WebElement element = null;

        try {
            waitForElement(cssBodyTextarea);
            element = findElement(cssBodyTextarea);
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
