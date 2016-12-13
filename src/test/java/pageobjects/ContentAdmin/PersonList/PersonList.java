package pageobjects.ContentAdmin.PersonList;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("span_Title"));
        grid = By.xpath(propUIContentAdmin.getProperty("table_GridPerson"));
        gridPersonList = By.xpath(propUIContentAdmin.getProperty("table_GridItem"));
        dataGridPager = By.xpath(propUIContentAdmin.getProperty("pager_DataGrid"));
        selectDepartment = By.xpath(propUIContentAdmin.getProperty("select_Department"));
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
        return findElement(grid).findElements(gridPersonList).size()/columnsNumber;
    }


    public WebElement getQuickLinksPagination() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(dataGridPager)));
            element = findElement(dataGridPager);
        } catch (PageObject.ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }


    public WebElement getDepartmentList() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(selectDepartment)));
            element = findElement(selectDepartment);
        } catch (PageObject.ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }
}
