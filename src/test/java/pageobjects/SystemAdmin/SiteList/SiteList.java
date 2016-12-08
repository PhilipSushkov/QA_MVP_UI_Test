package pageobjects.SystemAdmin.SiteList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISystemAdmin;

/**
 * Created by philipsushkov on 2016-11-14.
 */

public class SiteList extends AbstractPageObject {
    private static By moduleTitle, grid, gridSiteList;
    private final Integer columnsNumber = 7;

    public SiteList(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISystemAdmin.getProperty("spanModule_Title"));
        grid = By.xpath(propUISystemAdmin.getProperty("table_GridSite"));
        gridSiteList = By.xpath(propUISystemAdmin.getProperty("table_GridItem"));
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOf(findElement(moduleTitle)));
        return findElement(moduleTitle).getText();
    }

    public Integer getSiteListQuantity() {
        wait.until(ExpectedConditions.visibilityOf(findElement(grid)));
        return findElement(grid).findElements(gridSiteList).size()/columnsNumber;
    }
}
