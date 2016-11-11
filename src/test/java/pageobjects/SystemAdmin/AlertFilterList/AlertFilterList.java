package pageobjects.SystemAdmin.AlertFilterList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

/**
 * Created by philipsushkov on 2016-11-11.
 */

public class AlertFilterList extends AbstractPageObject {
    private final By moduleTitle = By.xpath("//span[contains(@class, 'AdminContent')]/h1/span[contains(@id,'ModuleTitle')]");
    private final By grid = By.xpath("//table[contains(@id, 'AlertFilterList')]");
    private final By gridFilterName = By.xpath("//td[contains(@class,'DataGridItemBorder')]");

    public AlertFilterList(WebDriver driver) {
        super(driver);
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
        return findElement(grid).findElements(gridFilterName).size()/4;
    }
}
