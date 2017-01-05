package pageobjects.ContentAdmin.PersonList;

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUIContentAdmin;

/**
 * Created by philipsushkov on 2017-01-05.
 */

public class PersonEdit extends AbstractPageObject {
    private static By moduleTitle, firstNameInput, lastNameInput, suffixInput, saveAndSubmitButton;
    private static By titleInput, descTextarea, careerHighlightTextarea, departmentSelect, tagsInput;

    public PersonEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("spanModule_Title"));

        firstNameInput = By.xpath(propUIContentAdmin.getProperty("input_FirstName"));
        lastNameInput = By.xpath(propUIContentAdmin.getProperty("input_LastName"));
        suffixInput = By.xpath(propUIContentAdmin.getProperty("input_Suffix"));
        titleInput = By.xpath(propUIContentAdmin.getProperty("input_Title"));
        descTextarea = By.xpath(propUIContentAdmin.getProperty("txtarea_Description"));
        careerHighlightTextarea = By.xpath(propUIContentAdmin.getProperty("txtarea_CareerHighlight"));
        departmentSelect = By.xpath(propUIContentAdmin.getProperty("select_Department"));
        tagsInput = By.xpath(propUIContentAdmin.getProperty("input_Tags"));

        saveAndSubmitButton = By.xpath(propUIContentAdmin.getProperty("btn_SaveAndSubmit"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getFirstNameInput() {
        WebElement element = null;

        try {
            waitForElement(firstNameInput);
            element = findElement(firstNameInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getLastNameInput() {
        WebElement element = null;

        try {
            waitForElement(lastNameInput);
            element = findElement(lastNameInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getSuffixInput() {
        WebElement element = null;

        try {
            waitForElement(suffixInput);
            element = findElement(suffixInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
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

    public WebElement getDescTextarea() {
        WebElement element = null;

        try {
            waitForElement(descTextarea);
            element = findElement(descTextarea);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getCareerHighlightTextarea() {
        WebElement element = null;

        try {
            waitForElement(careerHighlightTextarea);
            element = findElement(careerHighlightTextarea);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getDepartmentSelect() {
        WebElement element = null;

        try {
            waitForElement(departmentSelect);
            element = findElement(departmentSelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getTagsInput() {
        WebElement element = null;

        try {
            waitForElement(tagsInput);
            element = findElement(tagsInput);
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
