package specs.SocialMedia;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SocialMedia.SocialMediaSummary;
import pageobjects.SocialMedia.SocialTemplates;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by zacharyk on 2017-06-09.
 */
public class CheckTwitter extends AbstractSpec {

    private final String expectedTitle = "Social Media Summary";
    private final String notSetupMessage = "Twitter account is not setup. Click the Authorize button below to add your Twitter account.";
    private final String DATA="getData";

    private static JSONParser parser = new JSONParser();

    private static String sPathToFile, sDataFileJson;

    private SocialMediaSummary socialMediaSummary;
    private SocialTemplates socialTemplates;

    @BeforeTest
    public void setUp() throws Exception {

        sPathToFile = System.getProperty("user.dir") + propUISocialMedia.getProperty("dataPath_SocialMedia");
        sDataFileJson = propUISocialMedia.getProperty("json_TwitterLogin");

        new LoginPage(driver).loginUser();
    }

    @Test(dataProvider = DATA, priority = 0)
    public void twitterNotSetup(JSONObject data) throws Exception {
        // This test assumes that Twitter account is not currently setup and that you are not logged in to Twitter
        socialMediaSummary = new Dashboard(driver).openSocialMedia();

        Assert.assertEquals(socialMediaSummary.getTitle(), expectedTitle,"Actual Social Media Summary page title doesn't match to expected");
        Assert.assertTrue(socialMediaSummary.getTwitterStatusIndicator().contains("/unchecked.png"),
                "Red X is not displayed at start of test (Twitter account not setup state expected)");
    }

    @Test(dataProvider = DATA, priority = 1)
    public void canAuthorizeTwitterAccount(JSONObject data) throws Exception {
        socialMediaSummary.authorizeTwitterAccount().authorizeApp(data.get("username").toString(), data.get("password").toString());
        Assert.assertEquals(socialMediaSummary.getTitle(), expectedTitle, "You are not returned to summary page after authorizing Twitter account");

        Assert.assertTrue(socialMediaSummary.getTwitterAccountName().contains(data.get("username").toString()), "Incorrect account name is displayed");
        Assert.assertTrue(socialMediaSummary.numberOfTwitterFollowersIsDisplayed(), "Number of followers is improperly displayed.");

        //socialMediaSummary.enableTwitterAccount();
        Assert.assertTrue(socialMediaSummary.getTwitterStatusIndicator().contains("/checked.png"),
                "Green checkmark is not displayed after authorizing Twitter account");

        Assert.assertTrue(socialMediaSummary.twitterSettingsButtonIsDisplayed(), "Settings button is not displayed after authorizing Twitter account.");
        Assert.assertTrue(socialMediaSummary.twitterDeAuthorizeButtonIsDisplayed(), "De-Authorize button is not displayed after authorizing Twitter account");
        Assert.assertTrue(socialMediaSummary.twitterDisableButtonIsDisplayed(), "Disable button is not displayed after authorizing Twitter account");
        Assert.assertTrue(socialMediaSummary.twitterReAuthorizeButtonIsDisplayed(), "Re-Authorize button is not displayed after authorizing Twitter account");
    }

    @Test(dataProvider = DATA, priority = 2)
    public void canDisableTwitterAccount(JSONObject data) {

        socialMediaSummary.disableTwitterAccount();

        Assert.assertTrue(socialMediaSummary.getTwitterStatusIndicator().contains("/disabled.png"),
                "Grey checkmark is not displayed after disabling Twitter account");
        Assert.assertFalse(socialMediaSummary.twitterDisableButtonIsDisplayed(), "Disable button is still present after disabling Twitter account.");
        Assert.assertTrue(socialMediaSummary.twitterEnableButtonIsDisplayed(), "Enable button is not present after disabling Twitter account.");
    }

    @Test(dataProvider = DATA, priority = 3)
    public void canEnableTwitterAccount(JSONObject data) {

        socialMediaSummary.enableTwitterAccount();
        Assert.assertTrue(socialMediaSummary.getTwitterStatusIndicator().contains("/checked.png"),
                "Green checkmark is not displayed after enabling Twitter account");

        Assert.assertFalse(socialMediaSummary.twitterEnableButtonIsDisplayed(), "Enable button is still present after enabling Twitter account.");
        Assert.assertTrue(socialMediaSummary.twitterDisableButtonIsDisplayed(), "Disable button is not present after enabling Twitter account.");
    }

    @Test(dataProvider = DATA, priority = 4)
    public void canReAuthorizeTwitterAccount(JSONObject data) {

        socialMediaSummary.logoutFromTwitter(); // authorization process causes you to be logged in to Twitter; this deletes the Twitter session cookie to undo this
        socialMediaSummary.reAuthorizeTwitterAccount().reAuthorizeApp();

        Assert.assertEquals(socialMediaSummary.getTitle(), expectedTitle, "You are not returned to summary page after re-authorizing Twitter account");

        Assert.assertTrue(socialMediaSummary.getTwitterAccountName().contains(data.get("username").toString()), "Incorrect account name is displayed after re-authorizing");
        Assert.assertTrue(socialMediaSummary.numberOfTwitterFollowersIsDisplayed(), "Number of followers is improperly displayed after re-authorizing.");

        Assert.assertTrue(socialMediaSummary.getTwitterStatusIndicator().contains("/checked.png"),
                "Green checkmark is not displayed after re-authorizing Twitter account");

        Assert.assertTrue(socialMediaSummary.twitterSettingsButtonIsDisplayed(), "Settings button is not displayed after re-authorizing Twitter account.");
        Assert.assertTrue(socialMediaSummary.twitterDeAuthorizeButtonIsDisplayed(), "De-Authorize button is not displayed after re-authorizing Twitter account");
        Assert.assertTrue(socialMediaSummary.twitterDisableButtonIsDisplayed(), "Disable button is not displayed after re-authorizing Twitter account");
        Assert.assertTrue(socialMediaSummary.twitterReAuthorizeButtonIsDisplayed(), "Re-Authorize button is not displayed after re-authorizing Twitter account");
    }

    @Test(dataProvider = DATA, priority = 5)
    public void canModifyTwitterSettings(JSONObject data) {

        socialTemplates = socialMediaSummary.openTwitterSettings();
        Assert.assertTrue(socialTemplates.twitterSocialTemplatesAreDisplayed(), "Twitter social media templates are not displayed.");

        String firstTemplateBefore = socialTemplates.getFirstTemplateText();
        String firstTemplateAfter = (new Date().getTime()/1000)+": Event starting {Event.StartDate}, for details: {ShortURL}";

        // changing the first template to new value and saving it
        socialTemplates.editFirstTemplate();
        Assert.assertTrue(socialTemplates.editTemplateIsOpen(),"Edit template screen is not open.");
        Assert.assertEquals(socialTemplates.getEditableTemplateText(), firstTemplateBefore,"Editable template textbox is different from original value");
        socialTemplates.editTemplateTo(firstTemplateAfter).saveTemplate();

        //checking that the template has been changed (including after closing and reopening the settings screen)
        Assert.assertEquals(socialTemplates.getFirstTemplateText(), firstTemplateAfter,"Template is not set to new value");
        socialTemplates.closeSocialTemplates().openTwitterSettings();
        Assert.assertEquals(socialTemplates.getFirstTemplateText(), firstTemplateAfter,"Template is not set to new value after closing and re-opening Social Templates");

        // reverting template back to original setting
        socialTemplates.editFirstTemplate().editTemplateTo(firstTemplateBefore).saveTemplate();
        Assert.assertEquals(socialTemplates.getFirstTemplateText(), firstTemplateBefore,"Template has not been reset to original value");
        Assert.assertTrue(socialTemplates.closeSocialTemplates().socialTemplatesIsClosed(),"Social Templates has not been closed.");
    }

    @Test(dataProvider = DATA, priority = 6)
    public void canDeAuthorizeTwitterAccount(JSONObject data) {

        socialMediaSummary.deAuthorizeTwitterAccount();
        Assert.assertTrue(socialMediaSummary.getTwitterStatusIndicator().contains("/unchecked.png"),
                "Red X is not displayed after de-authorizing Twitter account");

        Assert.assertEquals(socialMediaSummary.getTwitterStatusMessage(), notSetupMessage,
                "Status message is not properly displayed after de-authorizing Twitter account");

        Assert.assertTrue(socialMediaSummary.twitterSettingsButtonIsDisplayed(),"Settings button is not displayed after de-authorizing Twitter account.");
        Assert.assertTrue(socialMediaSummary.twitterAuthorizeButtonIsDisplayed(),"Authorize button is not displayed after de-authorizing Twitter account.");
        Assert.assertFalse(socialMediaSummary.twitterEnableButtonIsDisplayed(),
                "Expected Enable button to not be displayed after de-authorizing Twitter account.");
        Assert.assertFalse(socialMediaSummary.twitterDisableButtonIsDisplayed(),
                "Expected Disable button to not be displayed after de-authorizing Twitter account.");
        Assert.assertFalse(socialMediaSummary.twitterReAuthorizeButtonIsDisplayed(),
                "Expected Re-Authorize button to not be displayed after de-authorizing Twitter account.");
    }

    @DataProvider
    public Object[][] getData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray jsonArray = (JSONArray) jsonObject.get("check_twitter");
            ArrayList<Object> zoom = new ArrayList<>();

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject pageObj = (JSONObject) jsonArray.get(i);
                if (Boolean.parseBoolean(pageObj.get("do_assertions").toString())) {
                    zoom.add(jsonArray.get(i));
                }
            }

            Object[][] data = new Object[zoom.size()][1];
            for (int i = 0; i < zoom.size(); i++) {
                data[i][0] = zoom.get(i);
            }

            return data;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
