package pageobjects.EmailAdmin.Subscribers;

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

public class MailingListUsers extends AbstractPageObject {

    private final By moduleTitle = By.xpath("//span[contains(@class, 'AdminContent')]/h1/span[contains(@id,'ModuleTitle')]");
    private final By grid = By.xpath("//table[contains(@id, 'UsersDataGrid')]");
    private final By gridEmailAddress = By.xpath("//td[contains(@class,'DataGridItemBorder')]");
    private final By dataGridPager = By.xpath("//tr[contains(@class, 'DataGridPager')]");
    private final By inputKeyword = By.xpath("//input[contains(@id, 'txtSearch')]");
    private final By selectAllLists = By.xpath("//select[contains(@id, 'ddlMailingLists')]");
    private final Integer columnsNumber = 5;

    public MailingListUsers(WebDriver driver) {
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
        return findElement(grid).findElements(gridEmailAddress).size()/columnsNumber;
    }

    public WebElement getMailingListUsersPagination() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(dataGridPager)));
            element = findElement(dataGridPager);
        } catch (PageObject.ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }


    public WebElement getKeywordField() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(inputKeyword)));
            element = findElement(inputKeyword);
        } catch (PageObject.ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }


    public WebElement getSelectAllList() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(selectAllLists)));
            element = findElement(selectAllLists);
        } catch (PageObject.ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

}
