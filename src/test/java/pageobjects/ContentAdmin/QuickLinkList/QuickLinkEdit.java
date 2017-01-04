package pageobjects.ContentAdmin.QuickLinkList;

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUIContentAdmin;

/**
 * Created by philipsushkov on 2017-01-04.
 */

public class QuickLinkEdit extends AbstractPageObject {
    private static By moduleTitle, descriptionInput, urlInput, textInput, tagsInput;
    private static By typeExternalRadio, typeInternalRadio, typeEmailRadio, typeDocumentRadio, saveAndSubmitButton;
    private static By quickLinkPagesLink, linkToNewCheckbox, activeCheckbox, gridPagesTable, publishButton;

    public QuickLinkEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("spanModule_Title"));
        descriptionInput = By.xpath(propUIContentAdmin.getProperty("input_Description"));
        urlInput = By.xpath(propUIContentAdmin.getProperty("input_Url"));
        textInput = By.xpath(propUIContentAdmin.getProperty("input_Text"));
        tagsInput = By.xpath(propUIContentAdmin.getProperty("input_Tags"));

        typeExternalRadio = By.xpath(propUIContentAdmin.getProperty("rdo_TypeExternal"));
        typeInternalRadio = By.xpath(propUIContentAdmin.getProperty("rdo_TypeInternal"));
        typeEmailRadio = By.xpath(propUIContentAdmin.getProperty("rdo_TypeEmail"));
        typeDocumentRadio = By.xpath(propUIContentAdmin.getProperty("rdo_TypeDocument"));

        quickLinkPagesLink = By.xpath(propUIContentAdmin.getProperty("href_QuickLinkPages"));
        gridPagesTable = By.xpath(propUIContentAdmin.getProperty("table_GridPages"));
        publishButton = By.xpath(propUIContentAdmin.getProperty("btn_Publish"));

        linkToNewCheckbox = By.xpath(propUIContentAdmin.getProperty("chk_LinkToNew"));
        activeCheckbox = By.xpath(propUIContentAdmin.getProperty("chk_Active"));
        saveAndSubmitButton = By.xpath(propUIContentAdmin.getProperty("btn_SaveAndSubmit"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getDescriptionInput() {
        WebElement element = null;

        try {
            waitForElement(descriptionInput);
            element = findElement(descriptionInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public Boolean getRadioTypeSet() {
        Boolean radioTypeSet = false;

        try {
            waitForElement(typeExternalRadio);
            findElement(typeExternalRadio);

            waitForElement(typeInternalRadio);
            findElement(typeInternalRadio);

            waitForElement(typeEmailRadio);
            findElement(typeEmailRadio);

            waitForElement(typeDocumentRadio);
            findElement(typeDocumentRadio);

            radioTypeSet = true;
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return radioTypeSet;
    }

    public WebElement getUrlInput() {
        WebElement element = null;

        try {
            waitForElement(urlInput);
            element = findElement(urlInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getTextInput() {
        WebElement element = null;

        try {
            waitForElement(textInput);
            element = findElement(textInput);
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

    public Boolean getChkBoxSet() {
        Boolean timeSet = false;

        try {
            waitForElement(linkToNewCheckbox);
            findElement(linkToNewCheckbox);

            waitForElement(activeCheckbox);
            findElement(activeCheckbox);

            timeSet = true;
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return timeSet;
    }

    public Boolean getQuickLinkPagesSet() {
        Boolean quickLinkPages = false;

        try {
            waitForElement(quickLinkPagesLink);
            findElement(quickLinkPagesLink).click();

            waitForElement(gridPagesTable);
            findElement(gridPagesTable);

            waitForElement(publishButton);
            findElement(publishButton);

            findElement(quickLinkPagesLink).click();
            waitForElement(quickLinkPagesLink);

            quickLinkPages = true;
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return quickLinkPages;
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
