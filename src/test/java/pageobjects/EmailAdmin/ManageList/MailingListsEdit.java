package pageobjects.EmailAdmin.ManageList;

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUIEmailAdmin;

/**
 * Created by philipsushkov on 2017-01-06.
 */

public class MailingListsEdit extends AbstractPageObject {

    private static By moduleTitle, mailingListNameInput, descriptionTextarea, activationEmailSelect;
    private static By unsubscribeEmailSelect, activeCheckbox, publicYesCheckbox, publicNoCheckbox, saveButton;

    public MailingListsEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIEmailAdmin.getProperty("spanModule_Title"));

        mailingListNameInput = By.xpath(propUIEmailAdmin.getProperty("input_MailingListName"));
        descriptionTextarea = By.xpath(propUIEmailAdmin.getProperty("txtarea_Description"));
        activationEmailSelect = By.xpath(propUIEmailAdmin.getProperty("select_ActivationEmail"));
        unsubscribeEmailSelect = By.xpath(propUIEmailAdmin.getProperty("select_UnsubscribeEmail"));

        activeCheckbox = By.xpath(propUIEmailAdmin.getProperty("chk_Active"));
        publicYesCheckbox = By.xpath(propUIEmailAdmin.getProperty("chk_PublicYes"));
        publicNoCheckbox = By.xpath(propUIEmailAdmin.getProperty("chk_PublicNo"));

        saveButton = By.xpath(propUIEmailAdmin.getProperty("button_Save"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getMailingListNameInput() {
        WebElement element = null;

        try {
            waitForElement(mailingListNameInput);
            element = findElement(mailingListNameInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getDescriptionTextarea() {
        WebElement element = null;

        try {
            waitForElement(descriptionTextarea);
            element = findElement(descriptionTextarea);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getActivationEmailSelect() {
        WebElement element = null;

        try {
            waitForElement(activationEmailSelect);
            element = findElement(activationEmailSelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getUnsubscribeEmailSelect() {
        WebElement element = null;

        try {
            waitForElement(unsubscribeEmailSelect);
            element = findElement(unsubscribeEmailSelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public Boolean getPublicRadioSet() {
        Boolean publicRdSet = false;

        try {
            waitForElement(publicYesCheckbox);
            findElement(publicYesCheckbox);

            waitForElement(publicNoCheckbox);
            findElement(publicNoCheckbox);

            publicRdSet = true;
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return publicRdSet;
    }

    public Boolean getChkBoxSet() {
        Boolean chkBoxSet = false;

        try {
            waitForElement(activeCheckbox);
            findElement(activeCheckbox);

            chkBoxSet = true;
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return chkBoxSet;
    }

    public WebElement getSaveButton() {
        WebElement element = null;

        try {
            waitForElement(saveButton);
            element = findElement(saveButton);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

}
