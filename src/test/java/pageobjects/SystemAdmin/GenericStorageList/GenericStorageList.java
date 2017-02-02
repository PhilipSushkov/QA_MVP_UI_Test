package pageobjects.SystemAdmin.GenericStorageList;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISystemAdmin;

/**
 * Created by philipsushkov on 2016-11-11.
 */

public class GenericStorageList extends AbstractPageObject {
    private static By moduleTitle, grid, gridStorageListHeader;
    private final Integer columnsNumber = 7;

    public GenericStorageList(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISystemAdmin.getProperty("spanModule_Title"));;
        grid = By.xpath(propUISystemAdmin.getProperty("table_GridStorage"));
        gridStorageListHeader = By.xpath(propUISystemAdmin.getProperty("table_GridHeader"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public Integer getStorageHeaderSize() {
        Integer headerSize = 0;

        try {
            waitForElement(grid);
            headerSize = getGridRowQuantity(findElement(grid).findElements(gridStorageListHeader).size(), columnsNumber);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return headerSize;
    }

}
