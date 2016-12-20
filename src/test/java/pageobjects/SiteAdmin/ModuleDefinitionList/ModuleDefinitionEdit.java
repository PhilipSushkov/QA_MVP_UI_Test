package pageobjects.SiteAdmin.ModuleDefinitionList;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISiteAdmin;

/**
 * Created by philipsushkov on 2016-12-20.
 */

public class ModuleDefinitionEdit extends AbstractPageObject {
    private static By moduleTitle, friendlyNameField, qualifiedPathField, linkToEditPageSelect, linkToListPageSelect;
    private static By linkToAdminPageSelect, activeCheckbox, saveAndSubmitButton;

    public ModuleDefinitionEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISiteAdmin.getProperty("spanModule_Title"));
        friendlyNameField = By.xpath(propUISiteAdmin.getProperty("input_FriendlyName"));
        qualifiedPathField = By.xpath(propUISiteAdmin.getProperty("input_QualifiedPath"));
        linkToEditPageSelect = By.xpath(propUISiteAdmin.getProperty("input_LinkToEditPage"));
        linkToListPageSelect = By.xpath(propUISiteAdmin.getProperty("input_LinkToListPage"));
        linkToAdminPageSelect = By.xpath(propUISiteAdmin.getProperty("input_LinkToAdminPage"));
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

    public WebElement getQualifiedPathInput() {
        WebElement element = null;

        try {
            waitForElement(qualifiedPathField);
            element = findElement(qualifiedPathField);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getLinkToEditPageSelect() {
        WebElement element = null;

        try {
            waitForElement(linkToEditPageSelect);
            element = findElement(linkToEditPageSelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getLinkToListPageSelect() {
        WebElement element = null;

        try {
            waitForElement(linkToListPageSelect);
            element = findElement(linkToListPageSelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getLinkToAdminPageSelect() {
        WebElement element = null;

        try {
            waitForElement(linkToAdminPageSelect);
            element = findElement(linkToAdminPageSelect);
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
