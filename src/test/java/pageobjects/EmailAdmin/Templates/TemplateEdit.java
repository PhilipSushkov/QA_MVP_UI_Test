package pageobjects.EmailAdmin.Templates;

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUIEmailAdmin;

/**
 * Created by philipsushkov on 2017-01-09.
 */

public class TemplateEdit extends AbstractPageObject {

    private static By moduleTitle, templateNameInput, subjectInput, fromInput, saveButton, sendBtn;
    private static By hideFooterCheckbox, activeCheckbox, bodyFrame, testEmailInput, entityTypeSelect;

    public TemplateEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIEmailAdmin.getProperty("spanModule_Title"));

        templateNameInput = By.xpath(propUIEmailAdmin.getProperty("input_TemplateName"));
        subjectInput = By.xpath(propUIEmailAdmin.getProperty("input_Subject"));
        fromInput = By.xpath(propUIEmailAdmin.getProperty("input_From"));

        hideFooterCheckbox = By.xpath(propUIEmailAdmin.getProperty("chk_HideFooter"));
        activeCheckbox = By.xpath(propUIEmailAdmin.getProperty("chk_Active"));
        bodyFrame = By.xpath(propUIEmailAdmin.getProperty("frame_Body"));
        testEmailInput = By.xpath(propUIEmailAdmin.getProperty("input_TestEmail"));
        entityTypeSelect = By.xpath(propUIEmailAdmin.getProperty("select_EntityType"));
        sendBtn = By.xpath(propUIEmailAdmin.getProperty("btn_Send"));

        saveButton = By.xpath(propUIEmailAdmin.getProperty("button_Save"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getTemplateNameInput() {
        WebElement element = null;

        try {
            waitForElement(templateNameInput);
            element = findElement(templateNameInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getSubjectInput() {
        WebElement element = null;

        try {
            waitForElement(subjectInput);
            element = findElement(subjectInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getFromInput() {
        WebElement element = null;

        try {
            waitForElement(fromInput);
            element = findElement(fromInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public Boolean getChkBoxSet() {
        Boolean chkBoxSet = false;

        try {
            waitForElement(hideFooterCheckbox);
            findElement(hideFooterCheckbox);

            waitForElement(activeCheckbox);
            findElement(activeCheckbox);

            chkBoxSet = true;
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return chkBoxSet;
    }

    public WebElement getBodyFrame() {
        WebElement element = null;

        try {
            waitForElement(bodyFrame);
            element = findElement(bodyFrame);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getTestEmailInput() {
        WebElement element = null;

        try {
            waitForElement(testEmailInput);
            element = findElement(testEmailInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getEntityTypeSelect() {
        WebElement element = null;

        try {
            waitForElement(entityTypeSelect);
            element = findElement(entityTypeSelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getSendButton() {
        WebElement element = null;

        try {
            waitForElement(sendBtn);
            element = findElement(sendBtn);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
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
        } catch (TimeoutException e3) {
        }

        return element;
    }

}
