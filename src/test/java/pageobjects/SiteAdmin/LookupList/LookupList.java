package pageobjects.SiteAdmin.LookupList;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISiteAdmin;

/**
 * Created by philipsushkov on 2016-11-21.
 */

public class LookupList extends AbstractPageObject {
    private static By moduleTitle, grid, gridModuleDefinitionList, selectLookupType;
    private final Integer columnsNumber = 8;

    public LookupList(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISiteAdmin.getProperty("spanModule_Title"));
        grid = By.xpath(propUISiteAdmin.getProperty("table_GridLookupList"));
        gridModuleDefinitionList = By.xpath(propUISiteAdmin.getProperty("table_GridItem"));
        selectLookupType = By.xpath(propUISiteAdmin.getProperty("select_LookupType"));
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOf(findElement(moduleTitle)) );
        return findElement(moduleTitle).getText();
    }

    public Integer getLookupListQuantity() {
        wait.until(ExpectedConditions.visibilityOf(findElement(grid)) );
        return findElement(grid).findElements(gridModuleDefinitionList).size()/columnsNumber;
    }

    public WebElement getLookupListLookupType() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(selectLookupType)));
            element = findElement(selectLookupType);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

}
