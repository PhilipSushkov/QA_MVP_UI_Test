package specs.PublicSite;

import org.apache.commons.lang.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.LiveSite.HomePage;
import pageobjects.LiveSite.SiteMapPage;
import specs.AbstractSpec;

/**
 * Created by sarahr on 4/6/2017.
 */
public class CheckSiteMapPage extends AbstractSpec {

    //Make sure that what ever tests are in this class, are also in the Preview folder too//

    private static HomePage homePage;

    @BeforeTest
    public void goToPublicSite() {

        driver.get("http://chicagotest.q4web.com/English/Investors/default.aspx");
        homePage = new HomePage(driver);
        Assert.assertTrue(homePage.logoIsPresent(), "Home page of public site has not been loaded.");

    }

    @Test
    public void wrongURLRedirectsToSiteMap(){
        //this test takes in a random string, and makes sure that the PageNotFound (404)
        //redirects to the site map
        SiteMapPage sitemap = homePage.selectSiteMapFromFooter();
        String url = "fakeurl"+ RandomStringUtils.randomAlphabetic(6);
        Assert.assertTrue(sitemap.pageNotFoundRedirects(url), "Page did not redirect to the site map");

    }

    @Test
    public void allLinksHave200Code(){
        SiteMapPage siteMapPage = homePage.selectSiteMapFromFooter();
        siteMapPage.checkBtnResponseCode();

    }
}
