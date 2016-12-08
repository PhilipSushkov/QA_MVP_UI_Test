package pageobjects.SystemAdmin.AlertFilterList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISystemAdmin;

/**
 * Created by philipsushkov on 2016-11-11.
 */

public class AlertFilterList extends AbstractPageObject {
    private static By moduleTitle, grid, gridFilterName;
    private final Integer columnsNumber = 4;

    public AlertFilterList(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISystemAdmin.getProperty("spanModule_Title"));;
        grid = By.xpath(propUISystemAdmin.getProperty("table_GridFilter"));
        gridFilterName = By.xpath(propUISystemAdmin.getProperty("table_GridItem"));
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOf(findElement(moduleTitle)));
        return findElement(moduleTitle).getText();
    }

    public Integer getFilterNameQuantity() {
        wait.until(ExpectedConditions.visibilityOf(findElement(grid)));
        return findElement(grid).findElements(gridFilterName).size()/columnsNumber;
    }
}
