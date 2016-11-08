package pageobjects.PressReleases;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.LiveSite.LivePressReleases;
import pageobjects.AbstractPageObject;

import java.util.concurrent.TimeUnit;


public class PressReleases extends AbstractPageObject {

    private final By publishButton = By.xpath("//input[contains(@id,'PressReleases_btnPublish')]");

    public PressReleases(WebDriver driver) {

        super(driver);
    }

    public EditPressRelease editPressRelease(String headline){
        By pressReleaseEditButton = By.xpath("//td[contains(text(),'"+headline+"')]/preceding-sibling::td/input[contains(@id,'imgEdit')]");

        findElement(pressReleaseEditButton).click();

        return new EditPressRelease(getDriver());
    }

    public LivePressReleases livePressReleases(String url) {

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

        return new LivePressReleases(getDriver());
    }

    public PressReleases publishPressRelease(String headline){
        By pressReleaseCheckbox = By.xpath("//td[contains(text(),'"+headline+"')]/following-sibling::td/input[contains(@id,'chkWorkflow')]");

        wait.until(ExpectedConditions.visibilityOf(findElement(pressReleaseCheckbox)));
        findElement(pressReleaseCheckbox).click();

        //waiting 1 second for publish button to activate
        try{Thread.sleep(1000);}
        catch(InterruptedException e){
            e.printStackTrace();
        }

        findElement(publishButton).click();

        return new PressReleases(getDriver());
    }


}
