package pageobjects.SystemAdmin.UserList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import java.util.List;

/**
 * Created by philipsushkov on 2016-11-10.
 */

public class UserList extends AbstractPageObject {
    private final By moduleTitle = By.xpath("//span[contains(@class, 'AdminContent')]/h1/span[contains(@id,'ModuleTitle')]");
    private final By grid = By.xpath("//table[contains(@id, 'dataGridUsers')]");
    private final By gridUserName = By.xpath("//td[contains(@class,'DataGridItemBorder')]");

    public UserList(WebDriver driver) {
        super(driver);
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOf(findElement(moduleTitle)));
        return findElement(moduleTitle).getText();
    }

    public Integer getUserNameQuantity() {
        wait.until(ExpectedConditions.visibilityOf(findElement(grid)));
        return findElement(grid).findElements(gridUserName).size()/4;
    }

}
