package pageobjects.ContentAdmin.Presentations;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.LiveSite.LivePresentations;
import pageobjects.AbstractPageObject;

import java.util.concurrent.TimeUnit;
import static specs.AbstractSpec.propUIContentAdmin;

/**
 * Created by philipsushkov on 2016-11-08.
 */
public class Presentations extends AbstractPageObject {
    private static By moduleTitle, grid, gridPresentationList, dataGridPager, inputFilterByTag, publishButton;
    private final Integer columnsNumber = 8;

    public Presentations(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("spanModule_Title"));
        grid = By.xpath(propUIContentAdmin.getProperty("table_GridPresentationList"));
        gridPresentationList = By.xpath(propUIContentAdmin.getProperty("table_GridItem"));
        dataGridPager = By.xpath(propUIContentAdmin.getProperty("pager_DataGrid"));
        inputFilterByTag = By.xpath(propUIContentAdmin.getProperty("input_FilterByTag"));
        publishButton = By.xpath(propUIContentAdmin.getProperty("btn_PublishPresentation"));
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }


    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }


    public Integer getTitleQuantity() {
        wait.until(ExpectedConditions.visibilityOf(findElement(grid)) );
        return findElement(grid).findElements(gridPresentationList).size()/columnsNumber;
    }


    public WebElement getPresentationListPagination() {
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

    public Presentations publishPresentation(String headline) {

        By presentationCheckbox;

        try {
            presentationCheckbox = By.xpath("//td[contains(text(),'" + headline + "')]/following-sibling::td/input[contains(@id,'chkWorkflow')]");
            wait.until(ExpectedConditions.visibilityOf(findElement(presentationCheckbox)));
        } catch (ElementNotFoundException e1) {
            presentationCheckbox = By.xpath("//td[contains(text(),'" + headline + "')]/following-sibling::td/span/input[contains(@id,'chkWorkflow')]");
            wait.until(ExpectedConditions.visibilityOf(findElement(presentationCheckbox)));
        }

        wait.until(ExpectedConditions.visibilityOf(findElement(presentationCheckbox)));
        findElement(presentationCheckbox).click();

        //waiting 1 second for publish button to activate
        try{Thread.sleep(1000);}
        catch(InterruptedException e){
            e.printStackTrace();
        }

        findElement(publishButton).click();

        return new Presentations(getDriver());
    }

    public LivePresentations livePresentations(String url) {

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

        return new LivePresentations(getDriver());
    }

    public PresentationEdit editPresentation(String headline){
        By pressReleaseEditButton = By.xpath("//td[contains(text(),'"+headline+"')]/preceding-sibling::td/input[contains(@id,'imgEdit')]");
        findElement(pressReleaseEditButton).click();
        return new PresentationEdit(getDriver());
    }



}
