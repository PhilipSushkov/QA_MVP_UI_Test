package pageobjects.SystemAdmin.WorkflowEmailList;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISystemAdmin;

/**
 * Created by philipsushkov on 2016-12-19.
 */
public class WorkflowEmailEdit extends AbstractPageObject {
    private static By moduleTitle, descriptionField, systemTaskSelect, systemMessageSelect, saveButton;

    public WorkflowEmailEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISystemAdmin.getProperty("spanModule_Title"));
        descriptionField = By.xpath(propUISystemAdmin.getProperty("input_Description"));
        systemTaskSelect = By.xpath(propUISystemAdmin.getProperty("select_SystemTask"));
        systemMessageSelect = By.xpath(propUISystemAdmin.getProperty("select_SystemMessage"));
        saveButton = By.xpath(propUISystemAdmin.getProperty("btn_Save"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
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

    public WebElement getSystemTaskSelect() {
        WebElement element = null;

        try {
            waitForElement(systemTaskSelect);
            element = findElement(systemTaskSelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getSystemMessageSelect() {
        WebElement element = null;

        try {
            waitForElement(systemMessageSelect);
            element = findElement(systemMessageSelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getSaveButton() {
        WebElement element = null;

        try {
            waitForElement(saveButton);
            element = findElement(saveButton);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }
}
