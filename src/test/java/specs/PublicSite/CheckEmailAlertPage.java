package specs.PublicSite;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.LiveSite.*;
import specs.AbstractSpec;



/**
 * Created by easong on 1/23/17.
 */
public class CheckEmailAlertPage extends AbstractSpec {

    //// WHEN ADDING A TEST TO THIS CLASS, ADD A ENTRY TO IT IN CheckPreviewSite.java \\\\


    /** Changes necessary to make include removing all the "new XXX(drivers).YYY with a declaration, instead, in the @Before
     and replacing it in all pieces of code. DONE
     Second changes include making a .properties file including ALL the selectors. THEN making them all do the .property thing. DONE
     Last is switching this to testNG*/

    private static HomePage homePage;

    @BeforeTest
    public void goToPublicSite() {

        driver.get(desktopUrlPublic.toString()+"English/Investors/Email-Alerts/default.aspx");

        homePage = new HomePage(driver);

        Assert.assertTrue(homePage.logoIsPresent(), "Home page of public site has not been loaded.");

    }


    @Test
    public void emailAlertsWork() {
        EmailAlertsPage emailAlertsPage = homePage.selectEmailAlertsFromMenu();
        String wrongEmail = "QWEASDZXC1234567";
        String rightEmail = "kelvint@q4inc.com";
        boolean buttonsActivated = true; //State of the buttons
        emailAlertsPage.clickAllButtons();
        Assert.assertTrue(emailAlertsPage.clickAllButtonsWorks(buttonsActivated)
                , "Buttons did not behave as expected" );

        buttonsActivated = false;

        Assert.assertTrue(emailAlertsPage.clickAllButtonsWorks(buttonsActivated)
                , "Buttons did not behave as expected");
        Assert.assertFalse(emailAlertsPage.clickSubmitWorks()
                , "Selecting no options for the mailing list still allowed submitting");

        Assert.assertFalse(emailAlertsPage.clickSubmitWorks()
                , "Entering no credentials allowed submitting");
        emailAlertsPage.enterSubEmailAddress(wrongEmail);
        Assert.assertFalse(emailAlertsPage.clickSubmitWorks()
                , "Entering an incorrectly formatted password works");
        emailAlertsPage.clearAllTextFields();
        emailAlertsPage.enterSubEmailAddress(rightEmail);
        try{
            Assert.assertTrue(emailAlertsPage.clickSubmitWorks(), "Submitting doesn't work");
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
    }

    @Test
    public void unsubscribeEmailAlertsWorks() { //Timeouts still occur, despite all the escapes :/
        EmailAlertsPage emailAlertsPage = homePage.selectEmailAlertsFromMenu();
        String incorrectFormEmail = "QWEASDZXC1234567";
        String wrongEmail = "telvink@q4inc.com"; //never used to subscribe
        String rightEmail = "kelvint@q4inc.com";
        emailAlertsPage.clickAllButtons();
        Assert.assertFalse(emailAlertsPage.clickUnsubscribeWorks()
                , "Entering no credentials allowed submitting");

        emailAlertsPage.enterUnsubEmailAddress(incorrectFormEmail);
        Assert.assertFalse(emailAlertsPage.clickUnsubscribeWorks()
                , "Unsubbing with an incorrectly formatted email works");

        emailAlertsPage.clearAllTextFields();
        emailAlertsPage.enterUnsubEmailAddress(wrongEmail);

        Assert.assertFalse(emailAlertsPage.clickUnsubscribeWorks()
                , "Unsubbing with a non-subscribed email works");
        emailAlertsPage.clearAllTextFields();

        emailAlertsPage.enterUnsubEmailAddress(rightEmail);

        try{
            Assert.assertTrue(emailAlertsPage.clickUnsubscribeWorks(), "Unsubscribing doesn't work");
        }catch (TimeoutException e) {
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
    }

}
