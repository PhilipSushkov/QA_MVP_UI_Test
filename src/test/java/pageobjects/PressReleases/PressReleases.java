package pageobjects.PressReleases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.LiveSite.LivePressReleases;
import pageobjects.AbstractPageObject;


public class PressReleases extends AbstractPageObject {

    private final By publishButton = By.xpath("//input[contains(@id,'UCPressReleases_btnPublish')]");

    public PressReleases(WebDriver driver) {
        super(driver);
    }

    public EditPressRelease editPressRelease(String headline){
        By pressReleaseEditButton = By.xpath("//td[contains(text(),'"+headline+"')]/preceding-sibling::td/input[contains(@id,'imgEdit')]");

        findElement(pressReleaseEditButton).click();

        return new EditPressRelease(getDriver());
    }

    public LivePressReleases livePressReleases(String url) {

        driver.get(url);

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
