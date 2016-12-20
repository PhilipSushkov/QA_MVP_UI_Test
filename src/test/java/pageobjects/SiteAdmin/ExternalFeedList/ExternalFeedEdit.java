package pageobjects.SiteAdmin.ExternalFeedList;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISiteAdmin;

/**
 * Created by philipsushkov on 2016-12-20.
 */
public class ExternalFeedEdit extends AbstractPageObject {
    private static By moduleTitle, feedSelect, tagListInput, languageSelect;
    private static By categorySelect, compIdInput, activeCheckbox, saveButton;

    public ExternalFeedEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISiteAdmin.getProperty("spanModule_Title"));
        feedSelect = By.xpath(propUISiteAdmin.getProperty("select_Feed"));
        tagListInput = By.xpath(propUISiteAdmin.getProperty("input_TagList"));
        languageSelect = By.xpath(propUISiteAdmin.getProperty("select_Language"));
        categorySelect = By.xpath(propUISiteAdmin.getProperty("select_Category"));
        compIdInput = By.xpath(propUISiteAdmin.getProperty("chk_CompId"));
        activeCheckbox = By.xpath(propUISiteAdmin.getProperty("chk_Active"));
        saveButton = By.xpath(propUISiteAdmin.getProperty("btn_Save"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getFeedSelect() {
        WebElement element = null;

        try {
            waitForElement(feedSelect);
            element = findElement(feedSelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getTagListInput() {
        WebElement element = null;

        try {
            waitForElement(tagListInput);
            element = findElement(tagListInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getLanguageSelect() {
        WebElement element = null;

        try {
            waitForElement(languageSelect);
            element = findElement(languageSelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getCategorySelect() {
        WebElement element = null;

        try {
            waitForElement(categorySelect);
            element = findElement(categorySelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getCompIdInput() {
        WebElement element = null;

        try {
            waitForElement(compIdInput);
            element = findElement(compIdInput);
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
