package pageobjects.ContentAdmin.Glossary;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;
import pageobjects.PageObject;

import static specs.AbstractSpec.propUIContentAdmin;

/**
 * Created by philipsushkov on 2017-01-04.
 */

public class GlossaryList extends AbstractPageObject {
    private static By moduleTitle, grid, gridGlossaryList, dataGridPager;
    private final Integer columnsNumber = 7;

    public GlossaryList(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("spanModule_Title"));
        grid = By.xpath(propUIContentAdmin.getProperty("table_GridGlossaryList"));
        gridGlossaryList = By.xpath(propUIContentAdmin.getProperty("table_GridItem"));
        dataGridPager = By.xpath(propUIContentAdmin.getProperty("pager_DataGrid"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }


    public Integer getTitleQuantity() {
        //wait.until(ExpectedConditions.visibilityOf(findElement(grid)) );
        waitForElement(grid);
        return findElement(grid).findElements(gridGlossaryList).size()/columnsNumber;
    }


    public WebElement getGlossaryListPagination() {
        WebElement element = null;

        try {
            //wait.until(ExpectedConditions.visibilityOf(findElement(dataGridPager)));
            waitForElement(dataGridPager);
            element = findElement(dataGridPager);
        } catch (PageObject.ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

}
