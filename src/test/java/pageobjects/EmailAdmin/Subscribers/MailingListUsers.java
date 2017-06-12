package pageobjects.EmailAdmin.Subscribers;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.PageObject;

import java.util.List;

import static specs.AbstractSpec.propUIEmailAdmin;

/**
 * Created by philipsushkov on 2016-12-06.
 */

public class MailingListUsers extends AbstractPageObject {
    private static By moduleTitle, grid, gridEmailAddress, dataGridPager, inputKeyword, selectAllLists, selectAllCategories;
    private static By buttonSearch, linkExportList, linkSendToList, linkImportList, linkBulkDelete, linkLetterList;
    private final Integer columnsNumber = 5;

    public MailingListUsers(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIEmailAdmin.getProperty("spanModule_Title"));
        grid = By.xpath(propUIEmailAdmin.getProperty("table_GridSubscribers"));
        gridEmailAddress = By.xpath(propUIEmailAdmin.getProperty("table_GridItem"));
        dataGridPager = By.xpath(propUIEmailAdmin.getProperty("pager_DataGrid"));
        inputKeyword = By.xpath(propUIEmailAdmin.getProperty("input_Keyword"));
        selectAllLists = By.xpath(propUIEmailAdmin.getProperty("select_AllLists"));
        selectAllCategories = By.xpath(propUIEmailAdmin.getProperty("select_AllCategories"));
        buttonSearch = By.xpath(propUIEmailAdmin.getProperty("button_BoxSearch"));
        linkExportList = By.xpath(propUIEmailAdmin.getProperty("link_ExportList"));
        linkSendToList = By.xpath(propUIEmailAdmin.getProperty("link_SendToList"));
        linkImportList = By.xpath(propUIEmailAdmin.getProperty("link_ImportList"));
        linkBulkDelete = By.xpath(propUIEmailAdmin.getProperty("link_BulkDelete"));
        linkLetterList = By.xpath(propUIEmailAdmin.getProperty("link_LetterList"));
    }


    public String getUrl() {
        return driver.getCurrentUrl();
    }


    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
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
