package pageobjects.ContentAdmin.PressReleases;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.LiveSite.LivePressReleases;
import pageobjects.AbstractPageObject;

import java.util.concurrent.TimeUnit;

import static specs.AbstractSpec.propUIContentAdmin;


public class PressReleases extends AbstractPageObject {
    private static By moduleTitle, grid, gridPressReleaseList, dataGridPager, inputFilterByTag, categorySelect, publishButton;
    private final Integer columnsNumber = 8;

    public PressReleases(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("spanModule_Title"));
        grid = By.xpath(propUIContentAdmin.getProperty("table_GridPressReleaseList"));
        gridPressReleaseList = By.xpath(propUIContentAdmin.getProperty("table_GridItem"));
        dataGridPager = By.xpath(propUIContentAdmin.getProperty("pager_DataGrid"));
        inputFilterByTag = By.xpath(propUIContentAdmin.getProperty("input_FilterByTag"));
        categorySelect = By.xpath(propUIContentAdmin.getProperty("select_Category"));

        publishButton = By.xpath(propUIContentAdmin.getProperty("btn_PublishPressRelease"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getFilterByTagInput() {
        WebElement element = null;

        try {
            waitForElement(inputFilterByTag);
            element = findElement(inputFilterByTag);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getCategorySelect() {
        WebElement element = null;

        try {
            waitForElement(categorySelect);
            element = findElement(categorySelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public Integer getTitleQuantity() {
        waitForElement(grid);
        return findElement(grid).findElements(gridPressReleaseList).size()/columnsNumber;
    }

    public WebElement getPressReleaseListPagination() {
        WebElement element = null;

        try {
            waitForElement(dataGridPager);
            element = findElement(dataGridPager);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public PressReleaseEdit editPressRelease(String headline){
        By pressReleaseEditButton = By.xpath("//td[contains(text(),'"+headline+"')]/preceding-sibling::td/input[contains(@id,'imgEdit')]");

        findElement(pressReleaseEditButton).click();

        return new PressReleaseEdit(getDriver());
    }

    public LivePressReleases livePressReleases(String url) {

        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        try {
            driver.get(url);
        } catch (TimeoutException e) {
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
            if (driver.getCurrentUrl()!=url){
                try {
                    driver.get(url);
                } catch (TimeoutException e2) {
                    driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
                }
            }
        }

        return new LivePressReleases(getDriver());
    }

    public PressReleases publishPressRelease(String headline){

        By pressReleaseCheckbox;

        try {
            pressReleaseCheckbox = By.xpath("//td[contains(text(),'" + headline + "')]/following-sibling::td/input[contains(@id,'chkWorkflow')]");
            wait.until(ExpectedConditions.visibilityOf(findElement(pressReleaseCheckbox)));
            //waitForElement(pressReleaseCheckbox);
        } catch (ElementNotFoundException e1) {
            pressReleaseCheckbox = By.xpath("//td[contains(text(),'" + headline + "')]/following-sibling::td/span/input[contains(@id,'chkWorkflow')]");
            wait.until(ExpectedConditions.visibilityOf(findElement(pressReleaseCheckbox)));
            //waitForElement(pressReleaseCheckbox);
        }

        //wait.until(ExpectedConditions.visibilityOf(findElement(pressReleaseCheckbox)));
        //waitForElement(pressReleaseCheckbox);
        findElement(pressReleaseCheckbox).click();

        //waiting 1 second for publish button to activate
        try{Thread.sleep(1000);}
        catch(InterruptedException e2){
            e2.printStackTrace();
        }

        findElement(publishButton).click();

        return new PressReleases(getDriver());
    }


}
