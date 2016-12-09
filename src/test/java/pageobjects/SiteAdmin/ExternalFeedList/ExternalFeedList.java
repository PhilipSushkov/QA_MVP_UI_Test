package pageobjects.SiteAdmin.ExternalFeedList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISiteAdmin;

/**
 * Created by philipsushkov on 2016-11-18.
 */

public class ExternalFeedList extends AbstractPageObject {
    private static By moduleTitle, grid, gridExternalFeedList;
    private final Integer columnsNumber = 7;

    public ExternalFeedList(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISiteAdmin.getProperty("spanModule_Title"));
        grid = By.xpath(propUISiteAdmin.getProperty("table_GridExternalFeed"));
        gridExternalFeedList = By.xpath(propUISiteAdmin.getProperty("table_GridItem"));
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOf(findElement(moduleTitle)) );
        return findElement(moduleTitle).getText();
    }

    public Integer getExternalFeedListQuantity() {
        wait.until(ExpectedConditions.visibilityOf(findElement(grid)) );
        return findElement(grid).findElements(gridExternalFeedList).size()/columnsNumber;
    }
}
