package pageobjects.SiteAdmin.MobileLinkList;

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

public class MobileLinkList extends AbstractPageObject {
    private static By moduleTitle, grid, gridMobileLinkList, dataGridPager;
    private final Integer columnsNumber = 8;

    public MobileLinkList(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISiteAdmin.getProperty("spanModule_Title"));
        grid = By.xpath(propUISiteAdmin.getProperty("table_GridMobileLink"));
        gridMobileLinkList = By.xpath(propUISiteAdmin.getProperty("table_GridItem"));
        dataGridPager = By.xpath(propUISiteAdmin.getProperty("pager_DataGrid"));
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOf(findElement(moduleTitle)) );
        return findElement(moduleTitle).getText();
    }

    public Integer getMobileLinkListQuantity() {
        wait.until(ExpectedConditions.visibilityOf(findElement(grid)) );
        return findElement(grid).findElements(gridMobileLinkList).size()/columnsNumber;
    }

    public WebElement getMobileLinkListPagination() {
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
