package specs.SocialMedia;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import pageobjects.SocialMedia.SocialMediaSummary;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2017-01-10.
 */

public class CheckSocialMediaSummary extends AbstractSpec {

    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static SocialMediaSummary socialMediaSummary;

    @BeforeTest
    public void setUp() throws Exception {
        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        socialMediaSummary = new SocialMediaSummary(driver);

        loginPage.loginUser();
        dashboard.openSocialMedia();
    }

    @Test
    public void checkSocialMediaSummary() throws Exception {
        final String expectedTitle = "Social Media Summary";

        Assert.assertNotNull(socialMediaSummary.getUrl());
        Assert.assertEquals(socialMediaSummary.getTitle(), expectedTitle, "Actual Social Media page Title doesn't match to expected");

        Assert.assertNotNull(socialMediaSummary.getLinkedInSpan(), "LinkedIn title doesn't exist");
        Assert.assertNotNull(socialMediaSummary.getFacebookSpan(), "Facebook title doesn't exist");
        Assert.assertNotNull(socialMediaSummary.getTwitterSpan(), "Twitter title doesn't exist");
        Assert.assertNotNull(socialMediaSummary.getStockTwitsSpan(), "Stock Twits title doesn't exist");
        Assert.assertNotNull(socialMediaSummary.getSlideShareSpan(), "Slide Share title doesn't exist");
        Assert.assertNotNull(socialMediaSummary.getGoogleAPISpan(), "Google API title doesn't exist");
        Assert.assertNotNull(socialMediaSummary.getBitLySpan(), "Bit.Ly title doesn't exist");

        Assert.assertNotNull(socialMediaSummary.getLinkedInBtns(), "LinkedIn buttons don't exist");
        Assert.assertNotNull(socialMediaSummary.getFacebookBtns(), "Facebook buttons don't exist");
        Assert.assertNotNull(socialMediaSummary.getTwitterBtns(), "Twitter buttons don't exist");
        Assert.assertNotNull(socialMediaSummary.getStockTwitsBtns(), "StockTwits buttons don't exist");
        Assert.assertNotNull(socialMediaSummary.getSlideShareBtns(), "SlideShare buttons don't exist");
        Assert.assertNotNull(socialMediaSummary.getGoogleAPIBtns(), "Google API buttons don't exist");
        Assert.assertNotNull(socialMediaSummary.getBitLyBtns(), "Bit.ly buttons don't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
