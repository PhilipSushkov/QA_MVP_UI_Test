package pageobjects.ContentAdmin.DownloadList;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUIContentAdmin;

/**
 * Created by philipsushkov on 2016-12-02.
 */

public class DownloadList extends AbstractPageObject {
    private static By moduleTitle, grid, gridDownloadList, dataGridPager, inputFilterByTag, generalSelect, publishButton;
    private final Integer columnsNumber = 8;

    public DownloadList(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("spanModule_Title"));
        grid = By.xpath(propUIContentAdmin.getProperty("table_GridDownload"));
        gridDownloadList = By.xpath(propUIContentAdmin.getProperty("table_GridItem"));
        dataGridPager = By.xpath(propUIContentAdmin.getProperty("pager_DataGrid"));
        inputFilterByTag = By.xpath(propUIContentAdmin.getProperty("input_FilterByTag"));
        generalSelect = By.xpath(propUIContentAdmin.getProperty("select_General"));
        publishButton = By.xpath(propUIContentAdmin.getProperty("btn_Publish"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }


    public Integer getTitleQuantity() {
        waitForElement(grid);
        return findElement(grid).findElements(gridDownloadList).size()/columnsNumber;
    }


    public WebElement getDownloadListPagination() {
        WebElement element = null;

        try {
            waitForElement(dataGridPager);
            element = findElement(dataGridPager);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }


    public WebElement getFilterByTag() {
        WebElement element = null;

        try {
            waitForElement(inputFilterByTag);
            element = findElement(inputFilterByTag);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getGeneralSelect() {
        WebElement element = null;

        try {
            waitForElement(generalSelect);
            element = findElement(generalSelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getPublishButton() {
        WebElement element = null;

        try {
            waitForElement(publishButton);
            element = findElement(publishButton);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }
}
