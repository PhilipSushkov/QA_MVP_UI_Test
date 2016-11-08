package pageobjects.Presentations;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.LiveSite.LivePresentations;
import pageobjects.AbstractPageObject;

import java.util.concurrent.TimeUnit;

/**
 * Created by philipsushkov on 2016-11-08.
 */
public class Presentations extends AbstractPageObject {
    private final By publishButton = By.xpath("//input[contains(@id,'Presentations_btnPublish')]");

    public Presentations(WebDriver driver) {
        super(driver);
    }


    public Presentations publishPresentation(String headline) {
        By presentationCheckbox = By.xpath("//td[contains(text(),'"+headline+"')]/following-sibling::td/input[contains(@id,'chkWorkflow')]");

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

    public EditPresentation editPresentation(String headline){
        By pressReleaseEditButton = By.xpath("//td[contains(text(),'"+headline+"')]/preceding-sibling::td/input[contains(@id,'imgEdit')]");
        findElement(pressReleaseEditButton).click();
        return new EditPresentation(getDriver());
    }

}
