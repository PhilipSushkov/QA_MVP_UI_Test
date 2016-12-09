package pageobjects.SystemAdmin.UserGroupList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISystemAdmin;

/**
 * Created by philipsushkov on 2016-11-15.
 */

public class UserGroupList extends AbstractPageObject {
    private static By moduleTitle, grid, gridUserGroup;
    private final Integer columnsNumber = 3;

    public UserGroupList(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISystemAdmin.getProperty("spanModule_Title"));
        grid = By.xpath(propUISystemAdmin.getProperty("table_GridUserGroup"));
        gridUserGroup = By.xpath(propUISystemAdmin.getProperty("table_GridItem"));
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOf(findElement(moduleTitle)));
        return findElement(moduleTitle).getText();
    }

    public Integer getUserGroupListQuantity() {
        wait.until(ExpectedConditions.visibilityOf(findElement(grid)));
        return findElement(grid).findElements(gridUserGroup).size()/columnsNumber;
    }
}
