package pageobjects.SiteAdmin.CssFileList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISiteAdmin;

/**
 * Created by philipsushkov on 2016-11-18.
 */

public class CssFileList extends AbstractPageObject {
    private static By moduleTitle, grid, gridCssFileList;
    private final Integer columnsNumber = 7;

    public CssFileList(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISiteAdmin.getProperty("spanModule_Title"));
        grid = By.xpath(propUISiteAdmin.getProperty("table_GridCssFile"));
        gridCssFileList = By.xpath(propUISiteAdmin.getProperty("table_GridItem"));
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOf(findElement(moduleTitle)) );
        return findElement(moduleTitle).getText();
    }

    public Integer getCssFileListQuantity() {
        wait.until(ExpectedConditions.visibilityOf(findElement(grid)) );
        return findElement(grid).findElements(gridCssFileList).size()/columnsNumber;
    }
}
