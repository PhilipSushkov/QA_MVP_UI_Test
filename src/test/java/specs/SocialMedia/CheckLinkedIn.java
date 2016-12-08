package specs.SocialMedia;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SocialMedia.SocialMediaSummary;
import specs.AbstractSpec;

/**
 * Created by jasons on 2016-12-07.
 */
public class CheckLinkedIn extends AbstractSpec{

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void canConnectLinkedInAccount() throws Exception {
        String expectedTitle = "Social Media Summary";
        String selectCompanyMessage = "Select the Company page you want to link";
        String notSetupMessage = "LinkedIn account is not setup. Click the Authorize button below to add your LinkedIn account.";
        String linkedInEmail = "marcoss@q4inc.com";
        String linkedInPassword = "sunset00";
        String linkedInName = "NotJohn Smith";
        String linkedInCompany = "Q4TestInc";
        SocialMediaSummary socialMediaSummary = new Dashboard(driver).openSocialMedia();
        Assert.assertEquals("Actual Social Media Summary page title doesn't match to expected", expectedTitle, socialMediaSummary.getTitle());
        Assert.assertThat("Red X is not displayed at start of test (LinkedIn account not setup state expected)",
                socialMediaSummary.getLinkedInStatusIndicator(),
                CoreMatchers.containsString("/unchecked.png"));

        // authorizing LinkedIn account
        socialMediaSummary.authorizeLinkedInAccount().allowAccessTo(linkedInEmail, linkedInPassword);
        Assert.assertEquals("You are not returned to summary page after authorizing LinkedIn account", expectedTitle, socialMediaSummary.getTitle());
        Assert.assertEquals("Select company status message is not properly displayed", selectCompanyMessage, socialMediaSummary.getLinkedInStatusMessage());
        socialMediaSummary.chooseFirstLinkedInCompany();
        Assert.assertEquals("Incorrect account name is displayed", linkedInName, socialMediaSummary.getLinkedInAccountName());
        Assert.assertEquals("Incorrect company name is displayed", linkedInCompany, socialMediaSummary.getLinkedInCompany());
        Assert.assertTrue("Number of followers is improperly displayed.", socialMediaSummary.numberOfLinkedInFollowersIsDisplayed());
        Assert.assertThat("Green checkmark is not displayed after authorizing LinkedIn account",
                socialMediaSummary.getLinkedInStatusIndicator(),
                CoreMatchers.containsString("/checked.png"));
        Assert.assertTrue("Settings button is not displayed after authorizing LinkedIn account.", socialMediaSummary.linkedInSettingsButtonIsDisplayed());
        Assert.assertTrue("De-Authorize button is not displayed after authorizing LinkedIn account", socialMediaSummary.linkedInDeAuthorizeButtonIsDisplayed());
        Assert.assertTrue("Disable button is not displayed after authorizing LinkedIn account", socialMediaSummary.linkedInDisableButtonIsDisplayed());
        Assert.assertTrue("Re-Authorize button is not displayed after authorizing LinkedIn account", socialMediaSummary.linkedInReAuthorizeButtonIsDisplayed());

        // disabling LinkedIn account
        socialMediaSummary.disableLinkedInAccount();
        Assert.assertThat("Grey checkmark is not displayed after disabling LinkedIn account",
                socialMediaSummary.getLinkedInStatusIndicator(),
                CoreMatchers.containsString("/disabled.png"));
        Assert.assertFalse("Disable button is still present after disabling LinkedIn account.", socialMediaSummary.linkedInDisableButtonIsDisplayed());
        Assert.assertTrue("Enable button is not present after disabling LinkedIn account.", socialMediaSummary.linkedInEnableButtonIsDisplayed());

        // enabling LinkedIn account
        socialMediaSummary.enableLinkedInAccount();
        Assert.assertThat("Green checkmark is not displayed after enabling LinkedIn account",
                socialMediaSummary.getLinkedInStatusIndicator(),
                CoreMatchers.containsString("/checked.png"));
        Assert.assertFalse("Enable button is still present after enabling LinkedIn account.", socialMediaSummary.linkedInEnableButtonIsDisplayed());
        Assert.assertTrue("Disable button is not present after enabling LinkedIn account.", socialMediaSummary.linkedInDisableButtonIsDisplayed());

        // re-authorizing LinkedIn account
        socialMediaSummary.reAuthorizeLinkedInAccount().allowAccessTo(linkedInEmail, linkedInPassword);
        Assert.assertEquals("You are not returned to summary page after re-authorizing LinkedIn account", expectedTitle, socialMediaSummary.getTitle());
        Assert.assertEquals("Select company status message is not properly displayed after re-authorizing", selectCompanyMessage, socialMediaSummary.getLinkedInStatusMessage());
        socialMediaSummary.chooseFirstLinkedInCompany();
        Assert.assertEquals("Incorrect account name is displayed after re-authorizing", linkedInName, socialMediaSummary.getLinkedInAccountName());
        Assert.assertEquals("Incorrect company name is displayed after re-authorizing", linkedInCompany, socialMediaSummary.getLinkedInCompany());
        Assert.assertTrue("Number of followers is improperly displayed after re-authorizing.", socialMediaSummary.numberOfLinkedInFollowersIsDisplayed());
        Assert.assertThat("Green checkmark is not displayed after re-authorizing LinkedIn account",
                socialMediaSummary.getLinkedInStatusIndicator(),
                CoreMatchers.containsString("/checked.png"));
        Assert.assertTrue("Settings button is not displayed after re-authorizing LinkedIn account.", socialMediaSummary.linkedInSettingsButtonIsDisplayed());
        Assert.assertTrue("De-Authorize button is not displayed after re-authorizing LinkedIn account", socialMediaSummary.linkedInDeAuthorizeButtonIsDisplayed());
        Assert.assertTrue("Disable button is not displayed after re-authorizing LinkedIn account", socialMediaSummary.linkedInDisableButtonIsDisplayed());
        Assert.assertTrue("Re-Authorize button is not displayed after re-authorizing LinkedIn account", socialMediaSummary.linkedInReAuthorizeButtonIsDisplayed());

        // de-authorizing LinkedIn account
        socialMediaSummary.deAuthorizeLinkedInAccount();
        Assert.assertThat("Red X is not displayed after de-authorizing LinkedIn account",
                socialMediaSummary.getLinkedInStatusIndicator(),
                CoreMatchers.containsString("/unchecked.png"));
        Assert.assertEquals("Status message is not properly displayed after de-authorizing LinkedIn account", notSetupMessage, socialMediaSummary.getLinkedInStatusMessage());
        Assert.assertTrue("Settings button is not displayed after de-authorizing LinkedIn account.", socialMediaSummary.linkedInSettingsButtonIsDisplayed());
        Assert.assertTrue("Authorize button is not displayed after de-authorizing LinkedIn account.", socialMediaSummary.linkedInAuthorizeButtonIsDisplayed());
    }

    @After
    public void tearDown() {
        //driver.quit();
    }
}
