package pageobjects.SiteAdmin.IndexContent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

/**
 * Created by philipsushkov on 2016-11-18.
 */

public class IndexContent extends AbstractPageObject {
    private final By moduleTitle = By.xpath("//span[contains(@class, 'AdminContent')]/h1[contains(@id,'Title')]");
    private final By grid = By.xpath("//table[contains(@id, 'dataGridContent')]");
    private final By gridIndexContent = By.xpath("//td[contains(@class,'DataGridItemBorder')]");
    private final By dataGridPager = By.xpath("//tr[contains(@class, 'DataGridPager')]");
    private final Integer columnsNumber = 4;

    public IndexContent(WebDriver driver) {
        super(driver);
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOf(findElement(moduleTitle)) );
        return findElement(moduleTitle).getText();
    }

    public Integer getIndexContentQuantity() {
        wait.until(ExpectedConditions.visibilityOf(findElement(grid)) );
        return findElement(grid).findElements(gridIndexContent).size()/columnsNumber;
    }

    public WebElement getIndexContentPagination() {
        wait.until(ExpectedConditions.visibilityOf(findElement(dataGridPager)) );
        return findElement(dataGridPager);
    }

}
