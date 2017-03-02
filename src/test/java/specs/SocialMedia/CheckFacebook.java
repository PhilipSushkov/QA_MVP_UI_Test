package specs.SocialMedia;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SocialMedia.SocialMediaSummary;
import pageobjects.SocialMedia.SocialTemplates;
import specs.AbstractSpec;

import java.util.Date;

/**
 * Created by jasons on 2016-12-09.
 */
public class CheckFacebook extends AbstractSpec{

    @BeforeTest
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void canConnectFacebookAccount() throws Exception {
        // This test assumes that Facebook account is not currently setup and that you are not logged in to Facebook
        String expectedTitle = "Social Media Summary";
        String selectCompanyMessage = "Select the Facebook Page you want to link.";
        String notSetupMessage = "Facebook Account is not setup. Click the Authorize button below to add your Facebook account.";
        String facebookEmail = "yaroslavs@q4websystems.com";
        String facebookPassword = "sunset00";
        String facebookName = "Yaroslav Api";
        String facebookCompany = "Q4 Web Test";
        SocialMediaSummary socialMediaSummary = new Dashboard(driver).openSocialMedia();

        Assert.assertEquals(socialMediaSummary.getTitle(), expectedTitle,"Actual Social Media Summary page title doesn't match to expected");

        Assert.assertTrue(socialMediaSummary.getFacebookStatusIndicator().contains("/unchecked.png"),
                "Red X is not displayed at start of test (Facebook account not setup state expected)");

        // authorizing Facebook account
        socialMediaSummary.authorizeFacebookAccount().allowAccessTo(facebookEmail, facebookPassword);
        Assert.assertEquals(socialMediaSummary.getTitle(),expectedTitle,"You are not returned to summary page after authorizing Facebook account");
        Assert.assertEquals(socialMediaSummary.getFacebookStatusMessage(),selectCompanyMessage,"Select company status message is not properly displayed");

        socialMediaSummary.chooseFirstFacebookPage();
        Assert.assertEquals(socialMediaSummary.getFacebookAccountName(),facebookName,"Incorrect account name is displayed");
        Assert.assertEquals(socialMediaSummary.getFacebookPage(),facebookCompany,"Incorrect company name is displayed");
        Assert.assertTrue(socialMediaSummary.numberOfFacebookFansIsDisplayed(),"Number of followers is improperly displayed.");

        //socialMediaSummary.enableFacebookAccount();
        Assert.assertTrue(socialMediaSummary.getFacebookStatusIndicator().contains("/checked.png"),
                "Green checkmark is not displayed after authorizing Facebook account");

        Assert.assertTrue(socialMediaSummary.facebookSettingsButtonIsDisplayed(),"Settings button is not displayed after authorizing Facebook account.");
        Assert.assertTrue(socialMediaSummary.facebookDeAuthorizeButtonIsDisplayed(),"De-Authorize button is not displayed after authorizing Facebook account");
        Assert.assertTrue(socialMediaSummary.facebookDisableButtonIsDisplayed(),"Disable button is not displayed after authorizing Facebook account");
        Assert.assertTrue(socialMediaSummary.facebookReAuthorizeButtonIsDisplayed(),"Re-Authorize button is not displayed after authorizing Facebook account");

        // disabling Facebook account
        socialMediaSummary.disableFacebookAccount();

        Assert.assertTrue(socialMediaSummary.getFacebookStatusIndicator().contains("/disabled.png"),
                "Grey checkmark is not displayed after disabling Facebook account");
        Assert.assertFalse(socialMediaSummary.facebookDisableButtonIsDisplayed(),"Disable button is still present after disabling Facebook account.");
        Assert.assertTrue(socialMediaSummary.facebookEnableButtonIsDisplayed(),"Enable button is not present after disabling Facebook account.");

        // enabling Facebook account
        socialMediaSummary.enableFacebookAccount();
        Assert.assertTrue(socialMediaSummary.getFacebookStatusIndicator().contains("/checked.png"),
                "Green checkmark is not displayed after enabling Facebook account");

        Assert.assertFalse(socialMediaSummary.facebookEnableButtonIsDisplayed(),"Enable button is still present after enabling Facebook account.");
        Assert.assertTrue(socialMediaSummary.facebookDisableButtonIsDisplayed(),"Disable button is not present after enabling Facebook account.");

        // re-authorizing Facebook account
        socialMediaSummary.logoutFromFacebook(); // authorization process causes you to be logged in to Facebook; this deletes the Facebook session cookie to undo this
        socialMediaSummary.reAuthorizeFacebookAccount().allowAccessTo(facebookEmail, facebookPassword);

        Assert.assertEquals(socialMediaSummary.getTitle(),expectedTitle,"You are not returned to summary page after re-authorizing Facebook account");
        Assert.assertEquals(socialMediaSummary.getFacebookStatusMessage(),selectCompanyMessage,"Select company status message is not properly displayed after re-authorizing");
        socialMediaSummary.chooseFirstFacebookPage();

        Assert.assertEquals(socialMediaSummary.getFacebookAccountName(),facebookName,"Incorrect account name is displayed after re-authorizing");
        Assert.assertEquals(socialMediaSummary.getFacebookPage(),facebookCompany,"Incorrect company name is displayed after re-authorizing");
        Assert.assertTrue(socialMediaSummary.numberOfFacebookFansIsDisplayed(),"Number of followers is improperly displayed after re-authorizing.");

        Assert.assertTrue(socialMediaSummary.getFacebookStatusIndicator().contains("/checked.png"),
                "Green checkmark is not displayed after re-authorizing Facebook account");

        Assert.assertTrue(socialMediaSummary.facebookSettingsButtonIsDisplayed(),"Settings button is not displayed after re-authorizing Facebook account.");
        Assert.assertTrue(socialMediaSummary.facebookDeAuthorizeButtonIsDisplayed(),"De-Authorize button is not displayed after re-authorizing Facebook account");
        Assert.assertTrue(socialMediaSummary.facebookDisableButtonIsDisplayed(),"Disable button is not displayed after re-authorizing Facebook account");
        Assert.assertTrue(socialMediaSummary.facebookReAuthorizeButtonIsDisplayed(),"Re-Authorize button is not displayed after re-authorizing Facebook account");

        // de-authorizing Facebook account
        socialMediaSummary.deAuthorizeFacebookAccount();
        Assert.assertTrue(socialMediaSummary.getFacebookStatusIndicator().contains("/unchecked.png"),
                "Red X is not displayed after de-authorizing Facebook account");

        Assert.assertEquals(socialMediaSummary.getFacebookStatusMessage(),notSetupMessage,"Status message is not properly displayed after de-authorizing Facebook account");
        Assert.assertTrue(socialMediaSummary.facebookSettingsButtonIsDisplayed(),"Settings button is not displayed after de-authorizing Facebook account.");
        Assert.assertTrue(socialMediaSummary.facebookAuthorizeButtonIsDisplayed(),"Authorize button is not displayed after de-authorizing Facebook account.");
    }

    @Test
    public void canModifyFacebookSettings() throws Exception {
        SocialTemplates socialTemplates = new Dashboard(driver).openSocialMedia().openFacebookSettings();
        Assert.assertTrue(socialTemplates.facebookSocialTemplatesAreDisplayed(),"Facebook Social Templates screen is not open.");
        String firstTemplateBefore = socialTemplates.getFirstTemplateText();
        String firstTemplateAfter = (new Date().getTime()/1000)+": Event starting {Event.StartDate}, for details: {ShortURL}";

        // changing the first template to new value and saving it
        socialTemplates.editFirstTemplate();
        Assert.assertTrue(socialTemplates.editTemplateIsOpen(),"Edit template screen is not open.");
        Assert.assertEquals(socialTemplates.getEditableTemplateText(), firstTemplateBefore,"Editable template textbox is different from original value");
        socialTemplates.editTemplateTo(firstTemplateAfter).saveTemplate();

        //checking that the template has been changed (including after closing and reopening the settings screen)
        Assert.assertEquals(socialTemplates.getFirstTemplateText(), firstTemplateAfter,"Template is not set to new value");
        socialTemplates.closeSocialTemplates().openFacebookSettings();
        Assert.assertEquals(socialTemplates.getFirstTemplateText(), firstTemplateAfter,"Template is not set to new value after closing and re-opening Social Templates");

        // reverting template back to original setting
        socialTemplates.editFirstTemplate().editTemplateTo(firstTemplateBefore).saveTemplate();
        Assert.assertEquals(socialTemplates.getFirstTemplateText(), firstTemplateBefore,"Template has not been reset to original value");
        Assert.assertTrue(socialTemplates.closeSocialTemplates().socialTemplatesIsClosed(),"Social Templates has not been closed.");
    }

    @AfterTest
    public void tearDown() {
        //driver.quit();
    }
}
