package pageobjects.EmailAdmin.Subscribers;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.PageObject;

import java.util.List;

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
    private final By selectAllCategories = By.xpath("//select[contains(@id, 'ddlCategories')]");
    private final By buttonSearch = By.xpath("//div[contains(@class, 'serachBox')]/input[contains(@id, 'btnSearch')]");
    private final By linkExportList = By.xpath("//a[contains(@id, 'lnkExport')]");
    private final By linkSendToList = By.xpath("//a[contains(@id, 'lnkSendToList')]");
    private final By linkImportList = By.xpath("//a[contains(@id, 'lnkImport')]");
    private final By linkBulkDelete = By.xpath("//a[contains(@id, 'LinkBulkDelete')]");
    private final By linkLetterList = By.xpath("//a[contains(@id, 'lnkLetter')]");
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


    public WebElement getAllListSelect() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(selectAllLists)));
            element = findElement(selectAllLists);
        } catch (PageObject.ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }


    public WebElement getAllCategoriesSelect() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(selectAllCategories)));
            element = findElement(selectAllCategories);
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


    public WebElement getExportListLink() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(linkExportList)));
            element = findElement(linkExportList);
        } catch (PageObject.ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }


    public WebElement getSendToListLink() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(linkSendToList)));
            element = findElement(linkSendToList);
        } catch (PageObject.ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }


    public WebElement getImportListLink() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(linkImportList)));
            element = findElement(linkImportList);
        } catch (PageObject.ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }


    public WebElement getBulkDeleteLink() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(linkBulkDelete)));
            element = findElement(linkBulkDelete);
        } catch (PageObject.ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }


    public List<WebElement> getLetterListLink() {
        List<WebElement> elements = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(linkLetterList)));
            elements = findElements(linkLetterList);
        } catch (PageObject.ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return elements;
    }
}
