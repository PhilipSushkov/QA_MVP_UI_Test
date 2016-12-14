package pageobjects.ContentAdmin.PressReleaseCategories;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUIContentAdmin;

/**
 * Created by philipsushkov on 2016-11-23.
 */

public class PressReleaseCategories extends AbstractPageObject {
    private static By moduleTitle, grid, gridCategoryName;
    private final Integer columnsNumber = 7;

    public PressReleaseCategories(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("spanModule_Title"));
        grid = By.xpath(propUIContentAdmin.getProperty("table_GridPressReleaseCategories"));
        gridCategoryName = By.xpath(propUIContentAdmin.getProperty("table_GridItem"));
    }


    public String getUrl() {
        return driver.getCurrentUrl();
    }


    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOf(findElement(moduleTitle)) );
        return findElement(moduleTitle).getText();
    }


    public Integer getCategoryNameQuantity() {
        wait.until(ExpectedConditions.visibilityOf(findElement(grid)) );
        return findElement(grid).findElements(gridCategoryName).size()/columnsNumber;
    }

}
