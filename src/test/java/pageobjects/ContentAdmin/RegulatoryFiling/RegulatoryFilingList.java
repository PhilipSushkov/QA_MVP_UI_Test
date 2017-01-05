package pageobjects.ContentAdmin.RegulatoryFiling;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;
import pageobjects.PageObject;

import static specs.AbstractSpec.propUIContentAdmin;

/**
 * Created by philipsushkov on 2017-01-05.
 */

public class RegulatoryFilingList extends AbstractPageObject {
    private static By moduleTitle, grid, gridRegulatoryFilingList, dataGridPager;
    private final Integer columnsNumber = 8;

    public RegulatoryFilingList(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("spanModule_Title"));
        grid = By.xpath(propUIContentAdmin.getProperty("table_GridRegulatoryFilingList"));
        gridRegulatoryFilingList = By.xpath(propUIContentAdmin.getProperty("table_GridItem"));
        dataGridPager = By.xpath(propUIContentAdmin.getProperty("pager_DataGrid"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }


    public Integer getTitleQuantity() {
        waitForElement(grid);
        return findElement(grid).findElements(gridRegulatoryFilingList).size()/columnsNumber;
    }


    public WebElement getRegulatoryFilingListPagination() {
        WebElement element = null;

        try {
            waitForElement(dataGridPager);
            element = findElement(dataGridPager);
        } catch (PageObject.ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

}
