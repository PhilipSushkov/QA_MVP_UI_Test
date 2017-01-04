package pageobjects.ContentAdmin.PressReleaseCategories;

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUIContentAdmin;

/**
 * Created by philipsushkov on 2017-01-04.
 */

public class PressReleaseCategoryEdit extends AbstractPageObject {
    private static By moduleTitle, categoryNameInput, linkToPageSelect, defaultCheckbox, activeCheckbox, saveAndSubmitButton;

    public PressReleaseCategoryEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("spanModule_Title"));
        categoryNameInput = By.xpath(propUIContentAdmin.getProperty("input_CategoryName"));
        linkToPageSelect = By.xpath(propUIContentAdmin.getProperty("select_linkToPage"));
        defaultCheckbox = By.xpath(propUIContentAdmin.getProperty("chk_Default"));
        activeCheckbox = By.xpath(propUIContentAdmin.getProperty("chk_IsActive"));
        saveAndSubmitButton = By.xpath(propUIContentAdmin.getProperty("btn_SaveAndSubmit"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getCategoryNameInput() {
        WebElement element = null;

        try {
            waitForElement(categoryNameInput);
            element = findElement(categoryNameInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getLinkToPageSelect() {
        WebElement element = null;

        try {
            waitForElement(linkToPageSelect);
            element = findElement(linkToPageSelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public Boolean getChkBoxSet() {
        Boolean timeSet = false;

        try {
            waitForElement(defaultCheckbox);
            findElement(defaultCheckbox);

            waitForElement(activeCheckbox);
            findElement(activeCheckbox);

            timeSet = true;
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return timeSet;
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
