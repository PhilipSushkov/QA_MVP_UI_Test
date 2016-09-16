package pageobjects.PressReleases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AESLiveSite.AESLiveHome;
import pageobjects.AbstractPageObject;


public class PressReleases extends AbstractPageObject {

    private final By newPressReleaseHeadlineCell = By.xpath("//table[contains(@id,'UCPressReleases')]/tbody/tr[2]/td[3]");
    private final By newPressReleaseStatusCell = By.xpath("//table[contains(@id,'UCPressReleases')]/tbody/tr[2]/td[7]/span");
    private final By newPressReleaseCheckbox = By.xpath("//table[contains(@id,'UCPressReleases')]/tbody/tr[2]/td[8]/input[2]");
    private final By publishButton = By.xpath("//input[contains(@id,'UCPressReleases_btnPublish')]");

    public PressReleases(WebDriver driver) {
        super(driver);
    }

    public AESLiveHome aesLiveHome() {

        driver.get(AESLiveHome.url);

        return new AESLiveHome(getDriver());
    }

    public String findNewPressReleaseHeadline(){
        return findElement(newPressReleaseHeadlineCell).getText();
    }

    public PressReleases publishNewPressRelease(){
        wait.until(ExpectedConditions.visibilityOf(findElement(newPressReleaseCheckbox)));
        findElement(newPressReleaseCheckbox).click();

        //waiting 1 second for publish button to activate
        try{Thread.sleep(1000);}
        catch(InterruptedException e){
            e.printStackTrace();
        }

        findElement(publishButton).click();

        //waiting up to about 2 minutes for the press release to "go live" with refresh about every 30 seconds
        System.out.println("Now waiting for press release to go live...");
        int refreshAttempts = 0;
        while (findElement(newPressReleaseStatusCell).getText().startsWith("Live - Publish Pending") && refreshAttempts<4){
            try{Thread.sleep(30000);}
            catch(InterruptedException e){
                e.printStackTrace();
            }
            driver.navigate().refresh();
            refreshAttempts++;
        }
        if (findElement(newPressReleaseStatusCell).getText().startsWith("Live - Publish Pending")){
            System.out.println("Waiting timeout reached. Moving on.");
        }
        else {
            System.out.println("Press release now live.");
        }

        return new PressReleases(getDriver());
    }


}
