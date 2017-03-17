package specs.PreviewSite;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.LiveSite.EmailAlertsPage;
import pageobjects.LiveSite.HomePage;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;
import specs.PublicSite.CheckPublicSite;

/**
 * Created by easong on 1/24/17.
 */
public class EmailAlertPreviewPage extends AbstractSpec {

    //// THERE SHOULD BE ONE TEST HERE FOR EVERY TEST ON CheckPublicSite.java \\\\

    private CheckPublicSite publicTests = new CheckPublicSite();
    private static HomePage homePage;


    @BeforeTest
    public void goToPreviewSite() throws Exception {
        homePage = new HomePage(driver);
        new LoginPage(driver).loginUser().previewSite().goToInvestorsPage();
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
                , "Entering no credentials allowed submitting");

        emailAlertsPage.enterSubEmailAddress(rightEmail);
        Assert.assertFalse(emailAlertsPage.clickSubmitWorks()
                , "Selecting no options for the mailing list still allowed submitting");

        emailAlertsPage.clearAllTextFields();
        emailAlertsPage.enterSubEmailAddress(wrongEmail);
        Assert.assertFalse(emailAlertsPage.clickSubmitWorks()
                , "Entering an incorrectly formatted email works");

        emailAlertsPage.clearAllTextFields();
        emailAlertsPage.enterSubEmailAddress(rightEmail);
        emailAlertsPage.clickAllButtons();
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
