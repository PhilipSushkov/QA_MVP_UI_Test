package pageobjects.Events;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.LiveSite.LiveEvents;

import java.util.concurrent.TimeUnit;

/**
 * Created by philipsushkov on 2016-11-09.
 */
public class Events extends AbstractPageObject {
    private final By publishButton = By.xpath("//input[contains(@id,'Events_btnPublish')]");

    public Events(WebDriver driver) {
        super(driver);
    }

    public Events publishEvent(String headline) {

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

        //waiting 1 second for publish button to activate
        try{Thread.sleep(1000);}
        catch(InterruptedException e){
            e.printStackTrace();
        }

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


    public EditEvent clickEditEventButton(String headline){
        By pressReleaseEditButton = By.xpath("//td[contains(text(),'"+headline+"')]/preceding-sibling::td/input[contains(@id,'imgEdit')]");
        findElement(pressReleaseEditButton).click();
        return new EditEvent(getDriver());
    }
}
