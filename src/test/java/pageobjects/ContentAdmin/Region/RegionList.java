package pageobjects.ContentAdmin.Region;

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;
import pageobjects.PageObject;

import static specs.AbstractSpec.propUIContentAdmin;

/**
 * Created by philipsushkov on 2017-01-06.
 */

public class RegionList extends AbstractPageObject {
    private static By moduleTitle, grid, gridRegionList, dataGridPager, inputFilterByTag;
    private final Integer columnsNumber = 7;

    public RegionList(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("spanModule_Title"));
        grid = By.xpath(propUIContentAdmin.getProperty("table_GridRegionList"));
        gridRegionList = By.xpath(propUIContentAdmin.getProperty("table_GridItem"));
        dataGridPager = By.xpath(propUIContentAdmin.getProperty("pager_DataGrid"));
        inputFilterByTag = By.xpath(propUIContentAdmin.getProperty("input_FilterByTag"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }


    public Integer getTitleQuantity() {
        waitForElement(grid);
        return findElement(grid).findElements(gridRegionList).size()/columnsNumber;
    }


    public WebElement getRegionListPagination() {
        WebElement element = null;

        try {
            waitForElement(dataGridPager);
            element = findElement(dataGridPager);
        } catch (PageObject.ElementNotFoundException e1) {
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

}
