package pageobjects.SiteAdmin.AliasList;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISiteAdmin;

/**
 * Created by philipsushkov on 2016-12-20.
 */

public class AliasEdit extends AbstractPageObject {
    private static By moduleTitle, aliasTypeSelect, redirectTypeSelect, aliasNameInput;
    private static By targetLanguageSelect, targetPageSelect, saveAndSubmitButton;

    public AliasEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISiteAdmin.getProperty("spanModule_Title"));
        aliasTypeSelect = By.xpath(propUISiteAdmin.getProperty("select_AliasType"));
        redirectTypeSelect = By.xpath(propUISiteAdmin.getProperty("select_RedirectType"));
        aliasNameInput = By.xpath(propUISiteAdmin.getProperty("input_AliasName"));
        targetLanguageSelect = By.xpath(propUISiteAdmin.getProperty("select_TargetLanguage"));
        targetPageSelect = By.xpath(propUISiteAdmin.getProperty("select_TargetPage"));
        saveAndSubmitButton = By.xpath(propUISiteAdmin.getProperty("btn_SaveAndSubmit"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getAliasTypeSelect() {
        WebElement element = null;

        try {
            waitForElement(aliasTypeSelect);
            element = findElement(aliasTypeSelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getRedirectTypeSelect() {
        WebElement element = null;

        try {
            waitForElement(redirectTypeSelect);
            element = findElement(redirectTypeSelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getAliasNameInput() {
        WebElement element = null;

        try {
            waitForElement(aliasNameInput);
            element = findElement(aliasNameInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getTargetLanguageSelect() {
        WebElement element = null;

        try {
            waitForElement(targetLanguageSelect);
            element = findElement(targetLanguageSelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getTargetPageSelect() {
        WebElement element = null;

        try {
            waitForElement(targetPageSelect);
            element = findElement(targetPageSelect);
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
