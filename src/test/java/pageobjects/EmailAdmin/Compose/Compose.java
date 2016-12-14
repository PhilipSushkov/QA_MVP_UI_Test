package pageobjects.EmailAdmin.Compose;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.PageObject;

import static specs.AbstractSpec.propUIEmailAdmin;

/**
 * Created by philipsushkov on 2016-12-05.
 */

public class Compose extends AbstractPageObject {
    private static By moduleTitle, selectTemplate, selectTo, inputFrom, inputSubject, textareaBodyText, inputCreatedBy, buttonSendTestEmail, buttonSave;

    public Compose(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIEmailAdmin.getProperty("spanModule_Title"));
        selectTemplate = By.xpath(propUIEmailAdmin.getProperty("select_Template"));
        selectTo = By.xpath(propUIEmailAdmin.getProperty("select_To"));
        inputFrom = By.xpath(propUIEmailAdmin.getProperty("input_From"));
        inputSubject = By.xpath(propUIEmailAdmin.getProperty("input_Subject"));
        textareaBodyText = By.xpath(propUIEmailAdmin.getProperty("textarea_BodyText"));
        inputCreatedBy = By.xpath(propUIEmailAdmin.getProperty("input_CreatedBy"));
        buttonSendTestEmail = By.xpath(propUIEmailAdmin.getProperty("button_SendTestEmail"));
        buttonSave = By.xpath(propUIEmailAdmin.getProperty("button_Save"));
    }


    public String getUrl() {
        return driver.getCurrentUrl();
    }


    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOf(findElement(moduleTitle)) );
        return findElement(moduleTitle).getText();
    }


    public WebElement getTemplateList() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(selectTemplate)));
            element = findElement(selectTemplate);
        } catch (PageObject.ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getToList() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(selectTo)));
            element = findElement(selectTo);
        } catch (PageObject.ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getFromField() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(inputFrom)));
            element = findElement(inputFrom);
        } catch (PageObject.ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getSubjectField() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(inputSubject)));
            element = findElement(inputSubject);
        } catch (PageObject.ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getBodyTextArea() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(textareaBodyText)));
            element = findElement(textareaBodyText);
        } catch (PageObject.ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getCreatedByField() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(inputCreatedBy)));
            element = findElement(inputCreatedBy);
        } catch (PageObject.ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getSendTestEmailButton() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(buttonSendTestEmail)));
            element = findElement(buttonSendTestEmail);
        } catch (PageObject.ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getSaveButton() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(buttonSave)));
            element = findElement(buttonSave);
        } catch (PageObject.ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

}
