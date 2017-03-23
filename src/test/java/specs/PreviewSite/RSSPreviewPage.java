package specs.PreviewSite;

import org.junit.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.LiveSite.HomePage;
import pageobjects.LiveSite.RSSFeedsPage;
import pageobjects.LiveSite.SECFilingsPage;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by sarahr on 3/22/2017.
 */
public class RSSPreviewPage extends AbstractSpec{

    //// THERE SHOULD BE ONE TEST HERE FOR EVERY TEST ON CheckPublicSite.java \\\\

    private static HomePage homePage;

    @BeforeTest
    public void goToPreviewSite() throws Exception {
        new LoginPage(driver).loginUser().previewSite().goToInvestorsPage();
        homePage = new HomePage(driver);
    }

    @Test
    public void RSSFeedWorksPressRelease(){
        RSSFeedsPage rss = homePage.selectRSSFeedsFromMenu();
        Assert.assertTrue("Press Release RSS Feeds don't exist",rss.pressReleaseRSSExists());
    }

    @Test
    public void RSSFeedsWorksEvent(){
        RSSFeedsPage rss = homePage.selectRSSFeedsFromMenu();
        Assert.assertTrue("Press Release RSS Feeds don't exist",rss.eventRSSExists());
    }

    @Test
    public void RSSFeedsWorksPresentation(){
        RSSFeedsPage rss = homePage.selectRSSFeedsFromMenu();
        Assert.assertTrue("Press Release RSS Feeds don't exist",rss.presentationRSSExists());
    }

    @Test
    public void RSSFeedsWorksSECFilings(){
        //this one needs to go to the SEC Filings page,
        //this is testing whether or not the feeds can work on a different page

        SECFilingsPage sec = homePage.selectSECFilingsFromMenu();
        RSSFeedsPage rss = new RSSFeedsPage(driver);

        Assert.assertTrue("SEC Filings RSS Feeds don't exist", rss.SECReleaseRSSExists());
    }
}
