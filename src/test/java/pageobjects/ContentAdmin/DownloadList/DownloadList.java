package pageobjects.ContentAdmin.DownloadList;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUIContentAdmin;

/**
 * Created by philipsushkov on 2016-12-02.
 */

public class DownloadList extends AbstractPageObject {
    private static By moduleTitle, grid, gridDownloadList, dataGridPager, inputFilterByTag;
    private final Integer columnsNumber = 8;

    public DownloadList(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("spanModule_Title"));
        grid = By.xpath(propUIContentAdmin.getProperty("table_GridDownload"));
        gridDownloadList = By.xpath(propUIContentAdmin.getProperty("table_GridItem"));
        dataGridPager = By.xpath(propUIContentAdmin.getProperty("pager_DataGrid"));
        inputFilterByTag = By.xpath(propUIContentAdmin.getProperty("input_FilterByTag"));
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
        return findElement(grid).findElements(gridDownloadList).size()/columnsNumber;
    }


    public WebElement getDownloadListPagination() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(dataGridPager)));
            element = findElement(dataGridPager);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }


    public WebElement getFilterByTag() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(inputFilterByTag)));
            element = findElement(inputFilterByTag);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }
}
