package pageobjects.EmailAdmin.Compose;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.PageObject;

/**
 * Created by philipsushkov on 2016-12-05.
 */

public class Compose extends AbstractPageObject {

    private final By moduleTitle = By.xpath("//span[contains(@class, 'AdminContent')]/h1/span[contains(@id,'ModuleTitle')]");
    private final By selectTemplate = By.xpath("//select[contains(@id, 'ddlTemplate')]");
    private final By selectTo = By.xpath("//select[contains(@id, 'ddlMailingLists')]");
    private final By inputFrom = By.xpath("//input[contains(@id, 'txtFrom')]");
    private final By inputSubject = By.xpath("//input[contains(@id, 'txtSubject')]");
    private final By textareaBodyText = By.xpath("//iframe[contains(@id, 'radBody_contentIframe')]");
    private final By inputCreatedBy = By.xpath("//input[contains(@id, 'txtCreatedBy')]");
    private final By buttonSendTestEmail = By.xpath("//input[contains(@id, 'btnSendTestEmail')]");
    private final By buttonSave = By.xpath("//input[contains(@id, 'btnSave')]");

    public Compose(WebDriver driver) {
        super(driver);
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
