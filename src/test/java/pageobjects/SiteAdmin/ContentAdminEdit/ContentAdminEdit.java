package pageobjects.SiteAdmin.ContentAdminEdit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.PageObject;

import static specs.AbstractSpec.propUISiteAdmin;

/**
 * Created by philipsushkov on 2016-11-23.
 */

public class ContentAdminEdit extends AbstractPageObject {
    private static By moduleTitle, grid, gridTitleList, gridShowInNav;
    private final Integer columnsNumber = 2;

    public ContentAdminEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISiteAdmin.getProperty("spanModule_TitleContentAdmin"));
        grid = By.xpath(propUISiteAdmin.getProperty("table_GridContentAdmin"));
        gridTitleList = By.xpath(propUISiteAdmin.getProperty("table_GridItem"));
        gridShowInNav = By.xpath(propUISiteAdmin.getProperty("table_GridShowInNav"));
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOf(findElement(moduleTitle)) );
        return findElement(moduleTitle).getText();
    }

    public Integer getDomainQuantity() {
        wait.until(ExpectedConditions.visibilityOf(findElement(grid)) );
        return findElement(grid).findElements(gridTitleList).size()/columnsNumber;
    }

    public Integer getShowInNavCkbQuantity() {
        Integer ckbSize = 0;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(gridShowInNav)));
            ckbSize = findElement(grid).findElements(gridShowInNav).size();
        } catch (PageObject.ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return ckbSize;
    }

}
