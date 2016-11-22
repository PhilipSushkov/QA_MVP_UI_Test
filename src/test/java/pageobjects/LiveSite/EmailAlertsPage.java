package pageobjects.LiveSite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;

/**
 * Created by kelvint on 11/17/16.
 */
public class EmailAlertsPage extends AbstractPageObject {


    private final By emailSubTextField = By.name("_ctrl0$ctl48$txtEmail");
    private final By pressReleaseBtn = By.id("_ctrl0_ctl48_chkLists_0");
    private final By testListBtn = By.id("_ctrl0_ctl48_chkLists_1");
    private final By eodBtn = By.id("_ctrl0_ctl48_chkLists_2");
    private final By submitBtn = By.id("_ctrl0_ctl48_btnSubmit");
    private final By submitMessage =  By.id("_ctrl0_ctl48_divEditSubscriberConfirmation");
    private final By emailUnsubTextField = By.name("_ctrl0$ctl54$txtEmailAddress");
    private final By unsubscribeBtn = By.name("_ctrl0$ctl54$btnUnsubscribe");
    private final By unsubscribeMessage = By.className("MailingListUnsubscribeMessage");

    public EmailAlertsPage(WebDriver driver) { super(driver); }


    public void enterSubEmailAddress(String email){
        findElement(emailSubTextField).click();
        findElement(emailSubTextField).sendKeys(email);
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
        pause(5000L);
        WebElement submit = findElement(submitMessage);
        return submit.isDisplayed();

    }

    public boolean clickUnsubscribeWorks( ){
        findElement(unsubscribeBtn).click();
        pause(5000L);
        WebElement unsubscribe = findElement(unsubscribeMessage);
        //String message = findElement(unsubscribeMessage).getText();

/*
        if (unsubscribe.isDisplayed() && unsubscribe.getText() != "\n" + "Unsubscription Failed."){
            System.out.print(unsubscribe.getText());
            return true;
        } */
        return unsubscribe.isDisplayed() && unsubscribe.getText().equals("Unsubscription Email Sent!");

    }

}
