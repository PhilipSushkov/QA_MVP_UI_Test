package specs.PreviewSite;

import org.apache.commons.lang.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.LiveSite.HomePage;
import pageobjects.LiveSite.SiteMapPage;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by sarahr on 4/7/2017.
 */

public class CheckSiteMapPr extends AbstractSpec {

    private static HomePage homePage;

    @BeforeTest
    public void goToPreviewSite() throws Exception {
        homePage = new HomePage(driver);
        new LoginPage(driver).loginUser().previewSite().goToInvestorsPage();
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
