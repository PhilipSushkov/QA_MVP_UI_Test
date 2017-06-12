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
        moduleTitle = By.xpath(propUISystemAdmin.getProperty("spanModule_Title"));
        grid = By.xpath(propUISystemAdmin.getProperty("table_GridFilter"));
        gridFilterName = By.xpath(propUISystemAdmin.getProperty("table_GridItem"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public Integer getFilterNameQuantity() {
        waitForElement(grid);
        return getGridRowQuantity(findElement(grid).findElements(gridFilterName).size(), columnsNumber);
    }

}
