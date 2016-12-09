package pageobjects.SiteAdmin.LinkToPageList;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISiteAdmin;

/**
 * Created by philipsushkov on 2016-11-21.
 */

public class LinkToPageList extends AbstractPageObject {
    private static By moduleTitle, grid, gridKeyName, dataGridPager;
    private final Integer columnsNumber = 7;

    public LinkToPageList(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISiteAdmin.getProperty("spanModule_Title"));
        grid = By.xpath(propUISiteAdmin.getProperty("table_GridLinkToPage"));
        gridKeyName = By.xpath(propUISiteAdmin.getProperty("table_GridItem"));
        dataGridPager = By.xpath(propUISiteAdmin.getProperty("pager_DataGrid"));
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOf(findElement(moduleTitle)) );
        return findElement(moduleTitle).getText();
    }

    public Integer getLinkToPageListQuantity() {
        wait.until(ExpectedConditions.visibilityOf(findElement(grid)) );
        return findElement(grid).findElements(gridKeyName).size()/columnsNumber;
    }

    public WebElement getLinkToPageListPagination() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(dataGridPager)));
            element = findElement(dataGridPager);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }
}
