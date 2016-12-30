package pageobjects.EmailAdmin.EmailAlertList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;


import static specs.AbstractSpec.propUIEmailAdmin;

/**
 * Created by philipsushkov on 2016-12-30.
 */

public class EmailAlertList extends AbstractPageObject {
    private static By moduleTitle, grid, gridEmailAlertList;
    private final Integer columnsNumber = 2;

    public EmailAlertList(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIEmailAdmin.getProperty("spanModule_Title"));
        grid = By.xpath(propUIEmailAdmin.getProperty("table_GridEmailAlertList"));
        gridEmailAlertList = By.xpath(propUIEmailAdmin.getProperty("table_GridItem"));
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public Integer getDescriptionQuantity() {
        wait.until(ExpectedConditions.visibilityOf(findElement(grid)) );
        return findElement(grid).findElements(gridEmailAlertList).size()/columnsNumber;
    }
}
