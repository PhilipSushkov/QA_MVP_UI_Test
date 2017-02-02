package pageobjects.ContentAdmin.PersonList;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.PageObject;

import static specs.AbstractSpec.propUIContentAdmin;

/**
 * Created by philipsushkov on 2016-12-02.
 */

public class PersonList extends AbstractPageObject {
    private static By moduleTitle, grid, gridPersonList, dataGridPager, selectDepartment;
    private final Integer columnsNumber = 7;

    public PersonList(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("spanModule_Title"));
        grid = By.xpath(propUIContentAdmin.getProperty("table_GridPerson"));
        gridPersonList = By.xpath(propUIContentAdmin.getProperty("table_GridItem"));
        dataGridPager = By.xpath(propUIContentAdmin.getProperty("pager_DataGrid"));
        selectDepartment = By.xpath(propUIContentAdmin.getProperty("select_Department"));
    }


    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }


    public Integer getTitleQuantity() {
        //wait.until(ExpectedConditions.visibilityOf(findElement(grid)) );
        waitForElement(grid);
        return findElement(grid).findElements(gridPersonList).size()/columnsNumber;
    }


    public WebElement getPersonListPagination() {
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


    public WebElement getDepartmentList() {
        WebElement element = null;

        try {
            //wait.until(ExpectedConditions.visibilityOf(findElement(selectDepartment)));
            waitForElement(selectDepartment);
            element = findElement(selectDepartment);
        } catch (PageObject.ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }
}
