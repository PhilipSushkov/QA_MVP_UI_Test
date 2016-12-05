package pageobjects.EmailAdmin;

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
    private final By fieldFrom = By.xpath("//select[contains(@id, 'txtFrom')]");
    private final By fieldSubject = By.xpath("//select[contains(@id, 'txtSubject')]");

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
            wait.until(ExpectedConditions.visibilityOf(findElement(fieldFrom)));
            element = findElement(fieldFrom);
        } catch (PageObject.ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getSubjectField() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(fieldSubject)));
            element = findElement(fieldSubject);
        } catch (PageObject.ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

}
