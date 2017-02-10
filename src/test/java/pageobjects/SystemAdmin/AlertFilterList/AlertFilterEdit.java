package pageobjects.SystemAdmin.AlertFilterList;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.browser;
import static specs.AbstractSpec.propUISystemAdmin;

/**
 * Created by philipsushkov on 2016-12-19.
 */

public class AlertFilterEdit extends AbstractPageObject {
    private static By moduleTitle, filterNameInput, saveButton, entityTypeSelect, filterTypeSelect;
    private static By includedTitleItems, includedBodyItems, excludedTitleItems, excludedBodyItems;
    private static By tagsInclInput, tagsExclInput, templateSelect, mailingListSelect, sendByCountryChk;
    private static By sendByCountrySelect, contriesSelect, activeChk;
    private static final String INCLUDE = "Include", EXCLUDE = "Exclude";
    private static final long DEFAULT_PAUSE = 1000;

    public AlertFilterEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISystemAdmin.getProperty("spanModule_Title"));
        filterNameInput = By.xpath(propUISystemAdmin.getProperty("input_FilterName"));
        entityTypeSelect = By.xpath(propUISystemAdmin.getProperty("select_EntityType"));
        filterTypeSelect = By.xpath(propUISystemAdmin.getProperty("select_FilterType"));
        includedTitleItems = By.xpath(propUISystemAdmin.getProperty("txtarea_IncludedTitleItems"));
        includedBodyItems = By.xpath(propUISystemAdmin.getProperty("txtarea_IncludedBodyItems"));
        excludedTitleItems = By.xpath(propUISystemAdmin.getProperty("txtarea_ExcludedTitleItems"));
        excludedBodyItems = By.xpath(propUISystemAdmin.getProperty("txtarea_ExcludedBodyItems"));
        tagsInclInput = By.xpath(propUISystemAdmin.getProperty("input_TagsIncl"));
        tagsExclInput = By.xpath(propUISystemAdmin.getProperty("input_TagsExcl"));
        templateSelect = By.xpath(propUISystemAdmin.getProperty("select_Template"));
        mailingListSelect = By.xpath(propUISystemAdmin.getProperty("select_MailingList"));
        sendByCountryChk = By.xpath(propUISystemAdmin.getProperty("chk_SendByCountry"));
        sendByCountrySelect = By.xpath(propUISystemAdmin.getProperty("select_SendByCountry"));
        contriesSelect = By.xpath(propUISystemAdmin.getProperty("select_Countries"));
        activeChk = By.xpath(propUISystemAdmin.getProperty("chk_IsActive"));
        saveButton = By.xpath(propUISystemAdmin.getProperty("btn_Save"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getFilterNameInput() {
        WebElement element = null;

        try {
            waitForElement(filterNameInput);
            element = findElement(filterNameInput);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getEntityTypeSelect() {
        WebElement element = null;

        try {
            waitForElement(entityTypeSelect);
            element = findElement(entityTypeSelect);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getFilterTypeSelect() {
        WebElement element = null;

        try {
            waitForElement(filterTypeSelect);
            element = findElement(filterTypeSelect);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getIncludedTitleItemsTextarea() {
        WebElement element = null;

        waitForElement(filterTypeSelect);
        findElement(filterTypeSelect).sendKeys(INCLUDE);

        try {
            waitForElement(includedTitleItems);
            element = findElement(includedTitleItems);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getIncludedBodyItemsTextarea() {
        WebElement element = null;

        waitForElement(filterTypeSelect);
        findElement(filterTypeSelect).sendKeys(INCLUDE);

        try {
            waitForElement(includedBodyItems);
            element = findElement(includedBodyItems);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getExcludedTitleItemsTextarea() throws InterruptedException {
        WebElement element = null;

        waitForElement(filterTypeSelect);
        findElement(filterTypeSelect).sendKeys(EXCLUDE);

        try {
            waitForElement(excludedTitleItems);
            element = findElement(excludedTitleItems);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getExcludedBodyItemsTextarea() throws InterruptedException {
        WebElement element = null;

        waitForElement(filterTypeSelect);
        findElement(filterTypeSelect).sendKeys(EXCLUDE);

        try {
            waitForElement(excludedBodyItems);
            element = findElement(excludedBodyItems);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getTagsInclInput() throws InterruptedException {
        WebElement element = null;

        try {
            waitForElement(tagsInclInput);
            element = findElement(tagsInclInput);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getTagsExclInput() throws InterruptedException {
        WebElement element = null;

        try {
            waitForElement(tagsExclInput);
            element = findElement(tagsExclInput);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getTemplateSelect() throws InterruptedException {
        WebElement element = null;

        try {
            waitForElement(templateSelect);
            element = findElement(templateSelect);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getMailingListSelect() throws InterruptedException {
        WebElement element = null;

        try {
            waitForElement(mailingListSelect);
            element = findElement(mailingListSelect);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getSendByCountryChk() throws InterruptedException {
        WebElement element = null;

        try {
            waitForElement(sendByCountryChk);
            element = findElement(sendByCountryChk);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getSendByCountrySelect() throws InterruptedException {
        WebElement element = null;

        try {
            waitForElement(sendByCountrySelect);
            element = findElement(sendByCountrySelect);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getCountriesSelect() throws InterruptedException {
        WebElement element = null;

        try {
            waitForElement(contriesSelect);
            element = findElement(contriesSelect);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getActiveChk() throws InterruptedException {
        WebElement element = null;

        try {
            waitForElement(activeChk);
            element = findElement(activeChk);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getSaveButton() {
        WebElement element = null;

        try {
            waitForElement(saveButton);
            element = findElement(saveButton);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }

}
