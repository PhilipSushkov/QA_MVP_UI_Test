package pageobjects.LiveSite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUIPublicSite;

/**
 * Created by kelvint on 11/17/16.
 */
public class EmailAlertsPage extends AbstractPageObject {


    private final By emailSubTextField = By.xpath(propUIPublicSite.getProperty("emailSubTextField"));
    private final By pressReleaseBtn = By.xpath(propUIPublicSite.getProperty("pressReleaseBtn"));
    private final By testListBtn = By.xpath(propUIPublicSite.getProperty("testListBtn"));
    private final By eodBtn = By.xpath(propUIPublicSite.getProperty("eodBtn"));
    private final By submitBtn = By.xpath(propUIPublicSite.getProperty("submitBtn"));
    private final By submitMessage =  By.xpath(propUIPublicSite.getProperty("submitMessage"));
    private final By emailUnsubTextField = By.xpath(propUIPublicSite.getProperty("emailUnsubTextField"));
    private final By unsubscribeBtn = By.xpath(propUIPublicSite.getProperty("unsubscribeBtn"));
    private final By unsubscribeMessage =By.xpath(propUIPublicSite.getProperty("unsubscribeMessage"));

    public EmailAlertsPage(WebDriver driver) { super(driver); }


    public void enterSubEmailAddress(String email){
        findElement(emailSubTextField).click();
        findElement(emailSubTextField).sendKeys(email);
        pause(3000);
    }
    public void clearAllTextFields(){
        findElement(emailSubTextField).click();
        findElement(emailSubTextField).clear();
        findElement(emailUnsubTextField).click();
        findElement(emailUnsubTextField).clear();
    }
    public void enterUnsubEmailAddress(String email){
        findElement(emailUnsubTextField).click();
        findElement(emailUnsubTextField).sendKeys(email);
    }

    public void clickAllButtons(){
        findElement(pressReleaseBtn).click();
        findElement(testListBtn).click();
        findElement(eodBtn).click();

    }

    public boolean clickAllButtonsWorks(boolean allActivated){
        clickAllButtons();

        return !((findElement(pressReleaseBtn).isSelected() || findElement(testListBtn).isSelected()
                || findElement(eodBtn).isSelected()) && allActivated);

    }

    public boolean clickSubmitWorks(){
        findElement(submitBtn).click();
        WebElement submit = findElement(submitMessage);
        pause(3000);
        return submit.isDisplayed();
    }

    public boolean clickUnsubscribeWorks( ){
        findElement(unsubscribeBtn).click();

        WebElement unsubscribe = findElement(unsubscribeMessage);

        return unsubscribe.isDisplayed() && unsubscribe.getText().equals("Unsubscription Email Sent!");

    }

}
