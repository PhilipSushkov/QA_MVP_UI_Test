package specs.PublicSite;

import org.junit.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.LiveSite.HomePage;
import pageobjects.LiveSite.RSSFeedsPage;
import pageobjects.LiveSite.SECFilingsPage;
import specs.AbstractSpec;

/**
 * Created by sarahr on 3/22/2017.
 */
public class CheckRSSFeedPage extends AbstractSpec{

    //// Make sure that all tests here are also in a Preview version \\\\

    private static HomePage homePage;

    @BeforeTest
    public void goToPublicSite() {

        driver.get("http://chicagotest.q4web.com/English/Investors/default.aspx");
        homePage = new HomePage(driver);
        org.testng.Assert.assertTrue(homePage.logoIsPresent(), "Home page of public site has not been loaded.");

    }

    @Test
    public void RSSFeedExistsPressRelease(){
        RSSFeedsPage rss = homePage.selectRSSFeedsFromMenu();
        Assert.assertTrue("Press Release RSS Feeds don't exist",rss.pressReleaseRSSExists());
    }

    @Test
    public void RSSFeedPressReleaseWorks(){
        RSSFeedsPage rss = homePage.selectRSSFeedsFromMenu();
        Assert.assertTrue("Press Release Feed doesn't work", rss.pressReleaseRSSWorks());
    }

    @Test
    public void RSSFeedsExistsEvent(){
        RSSFeedsPage rss = homePage.selectRSSFeedsFromMenu();
        Assert.assertTrue("Event RSS Feeds don't exist",rss.eventRSSExists());
    }

    @Test
    public void RSSFeedEventWorks(){
        RSSFeedsPage rss = homePage.selectRSSFeedsFromMenu();
        Assert.assertTrue("Event RSS Feeds don't work", rss.eventRSSWork());
    }

    @Test
    public void RSSFeedsExistsPresentation(){
        RSSFeedsPage rss = homePage.selectRSSFeedsFromMenu();
        Assert.assertTrue("Presentation RSS Feeds don't exist",rss.presentationRSSExists());
    }

    @Test
    public void RSSFeedPresentationWorks(){
        RSSFeedsPage rss = homePage.selectRSSFeedsFromMenu();
        Assert.assertTrue("Event Presentation Feeds don't work", rss.presentationRSSWork());
    }

    @Test
    public void RSSFeedsExistsSECFilings(){
        //this one needs to go to the SEC Filings page,
        //this is testing whether or not the feeds can work on a different page

        SECFilingsPage sec = homePage.selectSECFilingsFromMenu();
        RSSFeedsPage rss = new RSSFeedsPage(driver);

        Assert.assertTrue("SEC Filings RSS Feeds don't exist", rss.SECReleaseRSSExists());
    }

    @Test
    public void RSSFeedsSECWorks(){
        SECFilingsPage sec = homePage.selectSECFilingsFromMenu();
        RSSFeedsPage rss = new RSSFeedsPage(driver);

        Assert.assertTrue("SEC Filings RSS Feeds don't exist", rss.SECRSSWorks());
    }

}