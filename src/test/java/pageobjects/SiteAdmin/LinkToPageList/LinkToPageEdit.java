package pageobjects.SiteAdmin.LinkToPageList;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISiteAdmin;

/**
 * Created by philipsushkov on 2016-12-20.
 */

public class LinkToPageEdit extends AbstractPageObject {
    private static By moduleTitle, keyNameField, linkToPageField, saveAndSubmitButton;

    public LinkToPageEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISiteAdmin.getProperty("spanModule_Title"));
        keyNameField = By.xpath(propUISiteAdmin.getProperty("input_KeyName"));
        linkToPageField = By.xpath(propUISiteAdmin.getProperty("select_LinkToPage"));
        saveAndSubmitButton = By.xpath(propUISiteAdmin.getProperty("btn_SaveAndSubmit"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getKeyNameInput() {
        WebElement element = null;

        try {
            waitForElement(keyNameField);
            element = findElement(keyNameField);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getLinkToPageSelect() {
        WebElement element = null;

        try {
            waitForElement(linkToPageField);
            element = findElement(linkToPageField);
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
