package pageobjects.SystemAdmin.AlertFilterList;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISystemAdmin;

/**
 * Created by philipsushkov on 2016-12-19.
 */

public class AlertFilterEdit extends AbstractPageObject {
    private static By moduleTitle, filterNameField, saveButton;

    public AlertFilterEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISystemAdmin.getProperty("spanModule_Title"));
        filterNameField = By.xpath(propUISystemAdmin.getProperty("input_FilterName"));
        saveButton = By.xpath(propUISystemAdmin.getProperty("btn_Save"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getFilterNameInput() {
        WebElement element = null;

        try {
            waitForElement(filterNameField);
            element = findElement(filterNameField);
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
