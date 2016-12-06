package pageobjects.EmailAdmin.ManageList;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.PageObject;

/**
 * Created by philipsushkov on 2016-12-06.
 */

public class MailingLists extends AbstractPageObject {

    private final By moduleTitle = By.xpath("//span[contains(@class, 'AdminContent')]/h1/span[contains(@id,'ModuleTitle')]");
    private final By grid = By.xpath("//table[contains(@id, 'MailingListsDataGrid')]");
    private final By gridMailingLists = By.xpath("//td[contains(@class,'DataGridItemBorder')]");
    private final By inputSearch = By.xpath("//input[contains(@id, 'txtSearch')]");
    private final By buttonSearch = By.xpath("//input[contains(@id, 'btnSearch')]");
    private final Integer columnsNumber = 5;

    public MailingLists(WebDriver driver) {
        super(driver);
    }


    public String getUrl() {
        return driver.getCurrentUrl();
    }


    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOf(findElement(moduleTitle)) );
        return findElement(moduleTitle).getText();
    }


    public Integer getTitleQuantity() {
        wait.until(ExpectedConditions.visibilityOf(findElement(grid)) );
        return findElement(grid).findElements(gridMailingLists).size()/columnsNumber;
    }


    public WebElement getSearchField() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(inputSearch)));
            element = findElement(inputSearch);
        } catch (PageObject.ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }


    public WebElement getSearchButton() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(buttonSearch)));
            element = findElement(buttonSearch);
        } catch (PageObject.ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

}
