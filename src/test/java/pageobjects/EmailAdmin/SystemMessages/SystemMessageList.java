package pageobjects.EmailAdmin.SystemMessages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUIEmailAdmin;

/**
 * Created by philipsushkov on 2016-12-30.
 */

public class SystemMessageList extends AbstractPageObject {
    private static By moduleTitle, grid, gridSystemMessages;
    private final Integer columnsNumber = 3;

    public SystemMessageList(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIEmailAdmin.getProperty("spanModule_Title"));
        grid = By.xpath(propUIEmailAdmin.getProperty("table_GridSystemMessages"));
        gridSystemMessages = By.xpath(propUIEmailAdmin.getProperty("table_GridItem"));
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public Integer getMessageNameQuantity() {
        wait.until(ExpectedConditions.visibilityOf(findElement(grid)) );
        return findElement(grid).findElements(gridSystemMessages).size()/columnsNumber;
    }

}
