package pageobjects.SystemAdmin.PressReleaseFilterList;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISystemAdmin;


public class PressReleaseFilterList extends AbstractPageObject {
    private static By moduleTitle, grid, gridPressReleaseFilterListHeader;
    private final Integer columnsNumber = 4;

    public PressReleaseFilterList(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISystemAdmin.getProperty("spanModule_Title"));;
        grid = By.xpath(propUISystemAdmin.getProperty("table_GridPressReleaseFilter"));
        gridPressReleaseFilterListHeader = By.xpath(propUISystemAdmin.getProperty("table_GridHeader"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public Integer getPressReleaseFilterHeaderSize() {
        Integer headerSize = 0;

        try {
            waitForElement(grid);
            headerSize = getGridRowQuantity(findElement(grid).findElements(gridPressReleaseFilterListHeader).size(), columnsNumber);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return headerSize;
    }

}
