package pageobjects.SystemAdmin.GenericStorageList;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISystemAdmin;

/**
 * Created by philipsushkov on 2016-12-19.
 */

public class GenericStorageEdit extends AbstractPageObject {
    private static By moduleTitle, dataTokenSelect, dataContentTextarea,saveAndSubmitButton;

    public GenericStorageEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISystemAdmin.getProperty("spanModule_Title"));
        dataTokenSelect = By.xpath(propUISystemAdmin.getProperty("select_DataToken"));
        dataContentTextarea = By.xpath(propUISystemAdmin.getProperty("txtarea_DataContent"));
        saveAndSubmitButton = By.xpath(propUISystemAdmin.getProperty("btn_SaveAndSubmit"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getDataTokenSelect() {
        WebElement element = null;

        try {
            waitForElement(dataTokenSelect);
            element = findElement(dataTokenSelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getDataContentTextarea() {
        WebElement element = null;

        try {
            waitForElement(dataContentTextarea);
            element = findElement(dataContentTextarea);
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
