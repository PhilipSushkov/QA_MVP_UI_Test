package pageobjects.ContentAdmin.Glossary;

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUIContentAdmin;

/**
 * Created by philipsushkov on 2017-01-04.
 */

public class GlossaryEdit extends AbstractPageObject {
    private static By moduleTitle, titleInput, descriptionFrame, activeCheckbox, saveAndSubmitButton;

    public GlossaryEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("spanModule_Title"));
        titleInput = By.xpath(propUIContentAdmin.getProperty("input_Title"));
        descriptionFrame = By.xpath(propUIContentAdmin.getProperty("frame_RadEditor"));
        activeCheckbox = By.xpath(propUIContentAdmin.getProperty("chk_Active"));
        saveAndSubmitButton = By.xpath(propUIContentAdmin.getProperty("btn_SaveAndSubmit"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getTitleInput() {
        WebElement element = null;

        try {
            waitForElement(titleInput);
            element = findElement(titleInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getDescriptionFrame() {
        WebElement element = null;

        try {
            waitForElement(descriptionFrame);
            element = findElement(descriptionFrame);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
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
        } catch (TimeoutException e3) {
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
        } catch (TimeoutException e3) {
        }

        return element;
    }

}
