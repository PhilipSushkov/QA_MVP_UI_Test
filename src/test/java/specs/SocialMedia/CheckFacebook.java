package specs.SocialMedia;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void canConnectFacebookAccount() throws Exception {
        String expectedTitle = "Social Media Summary";
        String selectCompanyMessage = "Select the Facebook Page you want to link.";
        String notSetupMessage = "Facebook Account is not setup. Click the Authorize button below to add your Facebook account.";
        String facebookEmail = "yaroslavs@q4websystems.com";
        String facebookPassword = "sunset00";
        String facebookName = "Yaroslav Api";
        String facebookCompany = "Q4 Web Test";
        SocialMediaSummary socialMediaSummary = new Dashboard(driver).openSocialMedia();
        Assert.assertEquals("Actual Social Media Summary page title doesn't match to expected", expectedTitle, socialMediaSummary.getTitle());
        Assert.assertThat("Red X is not displayed at start of test (Facebook account not setup state expected)",
                socialMediaSummary.getFacebookStatusIndicator(),
                CoreMatchers.containsString("/unchecked.png"));

        // authorizing Facebook account
        socialMediaSummary.authorizeFacebookAccount().allowAccessTo(facebookEmail, facebookPassword);
        Assert.assertEquals("You are not returned to summary page after authorizing Facebook account", expectedTitle, socialMediaSummary.getTitle());
        Assert.assertEquals("Select company status message is not properly displayed", selectCompanyMessage, socialMediaSummary.getFacebookStatusMessage());
        socialMediaSummary.chooseFirstFacebookPage();
        Assert.assertEquals("Incorrect account name is displayed", facebookName, socialMediaSummary.getFacebookAccountName());
        Assert.assertEquals("Incorrect company name is displayed", facebookCompany, socialMediaSummary.getFacebookPage());
        Assert.assertTrue("Number of followers is improperly displayed.", socialMediaSummary.numberOfFacebookFansIsDisplayed());
        Assert.assertThat("Green checkmark is not displayed after authorizing Facebook account",
                socialMediaSummary.getFacebookStatusIndicator(),
                CoreMatchers.containsString("/checked.png"));
        Assert.assertTrue("Settings button is not displayed after authorizing Facebook account.", socialMediaSummary.facebookSettingsButtonIsDisplayed());
        Assert.assertTrue("De-Authorize button is not displayed after authorizing Facebook account", socialMediaSummary.facebookDeAuthorizeButtonIsDisplayed());
        Assert.assertTrue("Disable button is not displayed after authorizing Facebook account", socialMediaSummary.facebookDisableButtonIsDisplayed());
        Assert.assertTrue("Re-Authorize button is not displayed after authorizing Facebook account", socialMediaSummary.facebookReAuthorizeButtonIsDisplayed());

        // disabling Facebook account
        socialMediaSummary.disableFacebookAccount();
        Assert.assertThat("Grey checkmark is not displayed after disabling Facebook account",
                socialMediaSummary.getFacebookStatusIndicator(),
                CoreMatchers.containsString("/disabled.png"));
        Assert.assertFalse("Disable button is still present after disabling Facebook account.", socialMediaSummary.facebookDisableButtonIsDisplayed());
        Assert.assertTrue("Enable button is not present after disabling Facebook account.", socialMediaSummary.facebookEnableButtonIsDisplayed());

        // enabling Facebook account
        socialMediaSummary.enableFacebookAccount();
        Assert.assertThat("Green checkmark is not displayed after enabling Facebook account",
                socialMediaSummary.getFacebookStatusIndicator(),
                CoreMatchers.containsString("/checked.png"));
        Assert.assertFalse("Enable button is still present after enabling Facebook account.", socialMediaSummary.facebookEnableButtonIsDisplayed());
        Assert.assertTrue("Disable button is not present after enabling Facebook account.", socialMediaSummary.facebookDisableButtonIsDisplayed());

        // re-authorizing Facebook account
        socialMediaSummary.reAuthorizeFacebookAccount().allowAccessTo(facebookEmail, facebookPassword);
        Assert.assertEquals("You are not returned to summary page after re-authorizing Facebook account", expectedTitle, socialMediaSummary.getTitle());
        Assert.assertEquals("Select company status message is not properly displayed after re-authorizing", selectCompanyMessage, socialMediaSummary.getFacebookStatusMessage());
        socialMediaSummary.chooseFirstFacebookPage();
        Assert.assertEquals("Incorrect account name is displayed after re-authorizing", facebookName, socialMediaSummary.getFacebookAccountName());
        Assert.assertEquals("Incorrect company name is displayed after re-authorizing", facebookCompany, socialMediaSummary.getFacebookPage());
        Assert.assertTrue("Number of followers is improperly displayed after re-authorizing.", socialMediaSummary.numberOfFacebookFansIsDisplayed());
        Assert.assertThat("Green checkmark is not displayed after re-authorizing Facebook account",
                socialMediaSummary.getFacebookStatusIndicator(),
                CoreMatchers.containsString("/checked.png"));
        Assert.assertTrue("Settings button is not displayed after re-authorizing Facebook account.", socialMediaSummary.facebookSettingsButtonIsDisplayed());
        Assert.assertTrue("De-Authorize button is not displayed after re-authorizing Facebook account", socialMediaSummary.facebookDeAuthorizeButtonIsDisplayed());
        Assert.assertTrue("Disable button is not displayed after re-authorizing Facebook account", socialMediaSummary.facebookDisableButtonIsDisplayed());
        Assert.assertTrue("Re-Authorize button is not displayed after re-authorizing Facebook account", socialMediaSummary.facebookReAuthorizeButtonIsDisplayed());

        // de-authorizing Facebook account
        socialMediaSummary.deAuthorizeFacebookAccount();
        Assert.assertThat("Red X is not displayed after de-authorizing Facebook account",
                socialMediaSummary.getFacebookStatusIndicator(),
                CoreMatchers.containsString("/unchecked.png"));
        Assert.assertEquals("Status message is not properly displayed after de-authorizing Facebook account", notSetupMessage, socialMediaSummary.getFacebookStatusMessage());
        Assert.assertTrue("Settings button is not displayed after de-authorizing Facebook account.", socialMediaSummary.facebookSettingsButtonIsDisplayed());
        Assert.assertTrue("Authorize button is not displayed after de-authorizing Facebook account.", socialMediaSummary.facebookAuthorizeButtonIsDisplayed());
    }

    @Test
    public void canModifyFacebookSettings() throws Exception {
        SocialTemplates socialTemplates = new Dashboard(driver).openSocialMedia().openFacebookSettings();
        Assert.assertTrue("Facebook Social Templates screen is not open.", socialTemplates.facebookSocialTemplatesAreDisplayed());
        String firstTemplateBefore = socialTemplates.getFirstTemplateText();
        String firstTemplateAfter = (new Date().getTime()/1000)+": Event starting {Event.StartDate}, for details: {ShortURL}";
        socialTemplates.editFirstTemplate();
        Assert.assertTrue("Edit template screen is not open.", socialTemplates.editTemplateIsOpen());
        Assert.assertEquals("Editable template textbox is different from original value", firstTemplateBefore, socialTemplates.getEditableTemplateText());
        socialTemplates.editTemplateTo(firstTemplateAfter).saveTemplate();
        Assert.assertEquals("Template is not set to new value", firstTemplateAfter, socialTemplates.getFirstTemplateText());
        socialTemplates.closeSocialTemplates().openFacebookSettings();
        Assert.assertEquals("Template is not set to new value after closing and re-opening Social Templates", firstTemplateAfter, socialTemplates.getFirstTemplateText());

        // reverting template back to original setting
        socialTemplates.editFirstTemplate().editTemplateTo(firstTemplateBefore).saveTemplate();
        Assert.assertEquals("Template has not been reset to original value", firstTemplateBefore, socialTemplates.getFirstTemplateText());
        Assert.assertTrue("Social Templates has not been closed.", socialTemplates.closeSocialTemplates().socialTemplatesIsClosed());
    }

    @After
    public void tearDown() {
        //driver.quit();
    }
}
