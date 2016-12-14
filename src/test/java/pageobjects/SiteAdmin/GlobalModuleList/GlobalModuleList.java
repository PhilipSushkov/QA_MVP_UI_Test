package pageobjects.SiteAdmin.GlobalModuleList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISiteAdmin;

/**
 * Created by philipsushkov on 2016-11-15.
 */

public class GlobalModuleList extends AbstractPageObject {
    private static By moduleTitle, grid, gridGlobalModuleList;
    private final Integer columnsNumber = 6;

    public GlobalModuleList(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISiteAdmin.getProperty("span_Title"));
        grid = By.xpath(propUISiteAdmin.getProperty("table_GridGlobalModule"));
        gridGlobalModuleList = By.xpath(propUISiteAdmin.getProperty("table_GridItem"));
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOf(findElement(moduleTitle)));
        return findElement(moduleTitle).getText();
    }

    public Integer getGlobalModuleListQuantity() {
        wait.until(ExpectedConditions.visibilityOf(findElement(grid)));
        return findElement(grid).findElements(gridGlobalModuleList).size()/columnsNumber;
    }

}
