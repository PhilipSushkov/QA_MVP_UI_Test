package pageobjects.SiteAdmin.GlobalModuleList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

/**
 * Created by philipsushkov on 2016-11-15.
 */

public class GlobalModuleList extends AbstractPageObject {
    private final By moduleTitle = By.xpath("//span[contains(@class, 'AdminContent')]/h1/span[contains(@id,'Title')]");
    private final By grid = By.xpath("//table[contains(@id, 'ModuleInstances')]");
    private final By gridGlobalModuleList = By.xpath("//td[contains(@class,'DataGridItemBorder')]");
    private final Integer columnsNumber = 6;

    public GlobalModuleList(WebDriver driver) {
        super(driver);
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
