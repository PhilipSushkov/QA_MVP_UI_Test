package pageobjects.ContentAdmin.Events;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.LiveSite.LiveEvents;

import java.util.concurrent.TimeUnit;
import static specs.AbstractSpec.propUIContentAdmin;

/**
 * Created by philipsushkov on 2016-11-09.
 */
public class Events extends AbstractPageObject {
    private static By moduleTitle, grid, gridEventWebcastList, dataGridPager, inputFilterByTag, publishButton;
    private final Integer columnsNumber = 8;
    private static final long DEFAULT_PAUSE = 2500;

    public Events(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("spanModule_Title"));
        grid = By.xpath(propUIContentAdmin.getProperty("table_GridEventWebcastList"));
        gridEventWebcastList = By.xpath(propUIContentAdmin.getProperty("table_GridItem"));
        dataGridPager = By.xpath(propUIContentAdmin.getProperty("pager_DataGrid"));
        inputFilterByTag = By.xpath(propUIContentAdmin.getProperty("input_FilterByTag"));
        publishButton = By.xpath(propUIContentAdmin.getProperty("btn_PublishEvent"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }


    public Integer getTitleQuantity() {
        wait.until(ExpectedConditions.visibilityOf(findElement(grid)) );
        return findElement(grid).findElements(gridEventWebcastList).size()/columnsNumber;
    }


    public WebElement getEventWebcastListPagination() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(dataGridPager)));
            element = findElement(dataGridPager);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }


    public WebElement getFilterByTag() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(inputFilterByTag)));
            element = findElement(inputFilterByTag);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public Events publishEvent(String headline) throws InterruptedException {

        By eventCheckbox;

        try {
            eventCheckbox = By.xpath("//td[contains(text(),'" + headline + "')]/following-sibling::td/input[contains(@id,'chkWorkflow')]");
            wait.until(ExpectedConditions.visibilityOf(findElement(eventCheckbox)));
        } catch (ElementNotFoundException e1) {
            eventCheckbox = By.xpath("//td[contains(text(),'" + headline + "')]/following-sibling::td/span/input[contains(@id,'chkWorkflow')]");
            wait.until(ExpectedConditions.visibilityOf(findElement(eventCheckbox)));
        }

        wait.until(ExpectedConditions.visibilityOf(findElement(eventCheckbox)));
        findElement(eventCheckbox).click();

        Thread.sleep(DEFAULT_PAUSE);

        findElement(publishButton).click();

        return new Events(getDriver());
    }


    public LiveEvents liveEvents(String url) {

        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        try {
            driver.get(url);
        } catch (TimeoutException e) {
            driver.findElement(By.tagName("body")).sendKeys("Keys.ESCAPE");
            if (driver.getCurrentUrl()!=url){
                try {
                    driver.get(url);
                } catch (TimeoutException e2) {
                    driver.findElement(By.tagName("body")).sendKeys("Keys.ESCAPE");
                }
            }
        }

        return new LiveEvents(getDriver());
    }


    public EventWebcastEdit clickEditEventButton(String headline){
        By pressReleaseEditButton = By.xpath("//td[contains(text(),'"+headline+"')]/preceding-sibling::td/input[contains(@id,'imgEdit')]");
        findElement(pressReleaseEditButton).click();
        return new EventWebcastEdit(getDriver());
    }
}
