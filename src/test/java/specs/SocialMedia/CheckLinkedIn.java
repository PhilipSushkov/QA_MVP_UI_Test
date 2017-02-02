package specs.SocialMedia;

//import org.hamcrest.CoreMatchers;

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
 * Created by jasons on 2016-12-07.
 */
public class CheckLinkedIn extends AbstractSpec{

    @BeforeTest
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    // This test assumes that LinkedIn account is not currently setup and that you are not logged in to LinkedIn
    public void canConnectLinkedInAccount() throws Exception {
        String expectedTitle = "Social Media Summary";
        String selectCompanyMessage = "Select the Company page you want to link";
        String notSetupMessage = "LinkedIn account is not setup. Click the Authorize button below to add your LinkedIn account.";
        String linkedInEmail = "marcoss@q4inc.com";
        String linkedInPassword = "sunset00";
        String linkedInName = "NotJohn Smith";
        String linkedInCompany = "Q4TestInc";
        SocialMediaSummary socialMediaSummary = new Dashboard(driver).openSocialMedia();

        Assert.assertEquals(socialMediaSummary.getTitle(), expectedTitle,"Actual Social Media Summary page title doesn't match to expected");

        /* Assert.assertThat("Red X is not displayed at start of test (LinkedIn account not setup state expected)",
                socialMediaSummary.getLinkedInStatusIndicator(),
                CoreMatchers.containsString("/unchecked.png")); */
        Assert.assertTrue(socialMediaSummary.getLinkedInStatusIndicator().contains("/unchecked.png"),
                "Red X is not displayed at start of test (LinkedIn account not setup state expected)");

        // authorizing LinkedIn account
        socialMediaSummary.authorizeLinkedInAccount().allowAccessTo(linkedInEmail, linkedInPassword);
        Assert.assertEquals(socialMediaSummary.getTitle(), expectedTitle, "You are not returned to summary page after authorizing LinkedIn account");
        Assert.assertEquals(socialMediaSummary.getLinkedInStatusMessage(), selectCompanyMessage,"Select company status message is not properly displayed");

        socialMediaSummary.chooseFirstLinkedInCompany();
        Assert.assertEquals(socialMediaSummary.getLinkedInAccountName(),linkedInName, "Incorrect account name is displayed");
        Assert.assertEquals(socialMediaSummary.getLinkedInCompany(), linkedInCompany, "Incorrect company name is displayed");
        Assert.assertTrue(socialMediaSummary.numberOfLinkedInFollowersIsDisplayed(), "Number of followers is improperly displayed.");

        /* Assert.assertThat("Green checkmark is not displayed after authorizing LinkedIn account",
                socialMediaSummary.getLinkedInStatusIndicator(),
                CoreMatchers.containsString("/checked.png")); */
        Assert.assertTrue(socialMediaSummary.getLinkedInStatusIndicator().contains("/checked.png"),
                "Green checkmark is not displayed after authorizing LinkedIn account");

        Assert.assertTrue(socialMediaSummary.linkedInSettingsButtonIsDisplayed(), "Settings button is not displayed after authorizing LinkedIn account.");
        Assert.assertTrue(socialMediaSummary.linkedInDeAuthorizeButtonIsDisplayed(), "De-Authorize button is not displayed after authorizing LinkedIn account");
        Assert.assertTrue(socialMediaSummary.linkedInDisableButtonIsDisplayed(),"Disable button is not displayed after authorizing LinkedIn account");
        Assert.assertTrue(socialMediaSummary.linkedInReAuthorizeButtonIsDisplayed(),"Re-Authorize button is not displayed after authorizing LinkedIn account");

        // disabling LinkedIn account
        socialMediaSummary.disableLinkedInAccount();

        /*Assert.assertThat("Grey checkmark is not displayed after disabling LinkedIn account",
                socialMediaSummary.getLinkedInStatusIndicator(),
                CoreMatchers.containsString("/disabled.png"));*/
        Assert.assertTrue(socialMediaSummary.getLinkedInStatusIndicator().contains("/disabled.png"),
                "Grey checkmark is not displayed after disabling LinkedIn account");

        Assert.assertFalse(socialMediaSummary.linkedInDisableButtonIsDisplayed(),"Disable button is still present after disabling LinkedIn account.");
        Assert.assertTrue(socialMediaSummary.linkedInEnableButtonIsDisplayed(), "Enable button is not present after disabling LinkedIn account.");

        // enabling LinkedIn account
        socialMediaSummary.enableLinkedInAccount();

        /* Assert.assertThat("Green checkmark is not displayed after enabling LinkedIn account",
                socialMediaSummary.getLinkedInStatusIndicator(),
                CoreMatchers.containsString("/checked.png")); */
        Assert.assertTrue(socialMediaSummary.getLinkedInStatusIndicator().contains("/checked.png"),
                "Green checkmark is not displayed after enabling LinkedIn account");

        Assert.assertFalse(socialMediaSummary.linkedInEnableButtonIsDisplayed(),"Enable button is still present after enabling LinkedIn account.");
        Assert.assertTrue(socialMediaSummary.linkedInDisableButtonIsDisplayed(),"Disable button is not present after enabling LinkedIn account.");

        // re-authorizing LinkedIn account
        socialMediaSummary.reAuthorizeLinkedInAccount().allowAccessTo(linkedInEmail, linkedInPassword);
        Assert.assertEquals(socialMediaSummary.getTitle(), expectedTitle, "You are not returned to summary page after re-authorizing LinkedIn account");
        Assert.assertEquals(socialMediaSummary.getLinkedInStatusMessage(),selectCompanyMessage, "Select company status message is not properly displayed after re-authorizing");

        socialMediaSummary.chooseFirstLinkedInCompany();
        Assert.assertEquals(socialMediaSummary.getLinkedInAccountName(),linkedInName, "Incorrect account name is displayed after re-authorizing");
        Assert.assertEquals(socialMediaSummary.getLinkedInCompany(),linkedInCompany,"Incorrect company name is displayed after re-authorizing");
        Assert.assertTrue(socialMediaSummary.numberOfLinkedInFollowersIsDisplayed(),"Number of followers is improperly displayed after re-authorizing.");

        /* Assert.assertThat("Green checkmark is not displayed after re-authorizing LinkedIn account",
                socialMediaSummary.getLinkedInStatusIndicator(),
                CoreMatchers.containsString("/checked.png")); */
        Assert.assertTrue(socialMediaSummary.getLinkedInStatusIndicator().contains("/checked.png"),
                "Green checkmark is not displayed after re-authorizing LinkedIn account");

        Assert.assertTrue(socialMediaSummary.linkedInSettingsButtonIsDisplayed(),"Settings button is not displayed after re-authorizing LinkedIn account.");
        Assert.assertTrue(socialMediaSummary.linkedInDeAuthorizeButtonIsDisplayed(),"De-Authorize button is not displayed after re-authorizing LinkedIn account");
        Assert.assertTrue(socialMediaSummary.linkedInDisableButtonIsDisplayed(),"Disable button is not displayed after re-authorizing LinkedIn account");
        Assert.assertTrue(socialMediaSummary.linkedInReAuthorizeButtonIsDisplayed(),"Re-Authorize button is not displayed after re-authorizing LinkedIn account");

        // de-authorizing LinkedIn account
        socialMediaSummary.deAuthorizeLinkedInAccount();

        /* Assert.assertThat("Red X is not displayed after de-authorizing LinkedIn account",
                socialMediaSummary.getLinkedInStatusIndicator(),
                CoreMatchers.containsString("/unchecked.png")); */
        Assert.assertTrue(socialMediaSummary.getLinkedInStatusIndicator().contains("/unchecked.png"),
                "Red X is not displayed after de-authorizing LinkedIn account");

        Assert.assertEquals(socialMediaSummary.getLinkedInStatusMessage(), notSetupMessage, "Status message is not properly displayed after de-authorizing LinkedIn account");
        Assert.assertTrue(socialMediaSummary.linkedInSettingsButtonIsDisplayed(), "Settings button is not displayed after de-authorizing LinkedIn account.");
        Assert.assertTrue(socialMediaSummary.linkedInAuthorizeButtonIsDisplayed(), "Authorize button is not displayed after de-authorizing LinkedIn account.");
    }

    @Test
    public void canModifyLinkedInSettings() throws Exception {
        SocialTemplates socialTemplates = new Dashboard(driver).openSocialMedia().openLinkedInSettings();
        Assert.assertTrue(socialTemplates.linkedInSocialTemplatesAreDisplayed(),"LinkedIn Social Templates screen is not open.");
        String firstTemplateBefore = socialTemplates.getFirstTemplateText();
        String firstTemplateAfter = (new Date().getTime()/1000)+": Event starting {Event.StartDate}, for details: {ShortURL}";

        // changing the first template to new value and saving it
        socialTemplates.editFirstTemplate();
        Assert.assertTrue(socialTemplates.editTemplateIsOpen(),"Edit template screen is not open.");
        Assert.assertEquals(socialTemplates.getEditableTemplateText(), firstTemplateBefore, "Editable template textbox is different from original value");
        socialTemplates.editTemplateTo(firstTemplateAfter).saveTemplate();

        //checking that the template has been changed (including after closing and reopening the settings screen)
        Assert.assertEquals(socialTemplates.getFirstTemplateText(), firstTemplateAfter, "Template is not set to new value");
        socialTemplates.closeSocialTemplates().openLinkedInSettings();
        Assert.assertEquals(socialTemplates.getFirstTemplateText(), firstTemplateAfter, "Template is not set to new value after closing and re-opening Social Templates");

        // reverting template back to original setting
        socialTemplates.editFirstTemplate().editTemplateTo(firstTemplateBefore).saveTemplate();
        Assert.assertEquals(socialTemplates.getFirstTemplateText(), firstTemplateBefore, "Template has not been reset to original value");
        Assert.assertTrue(socialTemplates.closeSocialTemplates().socialTemplatesIsClosed(), "Social Templates has not been closed.");
    }

    @AfterTest
    public void tearDown() {
        //driver.quit();
    }
}
