package pageobjects.ContentAdmin.JobPostingList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUIContentAdmin;

/**
 * Created by philipsushkov on 2016-12-05.
 */

public class JobPostingList extends AbstractPageObject {
    private static By moduleTitle, grid, gridJobPostingList;
    private final Integer columnsNumber = 7;

    public JobPostingList(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("spanModule_Title"));
        grid = By.xpath(propUIContentAdmin.getProperty("table_GridJobPosting"));
        gridJobPostingList = By.xpath(propUIContentAdmin.getProperty("table_GridItem"));
    }


    public String getUrl() {
        return driver.getCurrentUrl();
    }


    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOf(findElement(moduleTitle)) );
        return findElement(moduleTitle).getText();
    }


    public Integer getTitleQuantity() {
        wait.until(ExpectedConditions.visibilityOf(findElement(grid)) );
        return findElement(grid).findElements(gridJobPostingList).size()/columnsNumber;
    }

}
