package pageobjects.SystemAdmin.SiteList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

/**
 * Created by philipsushkov on 2016-11-14.
 */

public class SiteList extends AbstractPageObject {
    private final By moduleTitle = By.xpath("//span[contains(@class, 'AdminContent')]/h1/span[contains(@id,'ModuleTitle')]");
    private final By grid = By.xpath("//table[contains(@id, 'Sites_dataGrid')]");
    private final By gridSiteListHeader = By.xpath("//td[contains(@class,'DataGridItemBorder')]");
    private final Integer columnsNumber = 7;

    public SiteList(WebDriver driver) {
        super(driver);
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOf(findElement(moduleTitle)));
        return findElement(moduleTitle).getText();
    }

    public Integer getSiteListHeader() {
        wait.until(ExpectedConditions.visibilityOf(findElement(grid)));
        return findElement(grid).findElements(gridSiteListHeader).size()/columnsNumber;
    }
}
