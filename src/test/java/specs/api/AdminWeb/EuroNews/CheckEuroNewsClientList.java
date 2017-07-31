package specs.api.AdminWeb.EuroNews;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.api.AdminWeb.Auth;
import pageobjects.api.AdminWeb.EuroNews.EuroNews;
import pageobjects.api.AdminWeb.LeftMainMenu;
import specs.ApiAbstractSpec;

/**
 * Created by philipsushkov on 2017-07-31.
 */

public class CheckEuroNewsClientList extends ApiAbstractSpec {
    private static Auth auth;
    private static LeftMainMenu leftMainMenu;
    private static EuroNews euroNews;
    private static final String EURO_NEWS = "Euro News";
    private static String sTitle;

    @BeforeTest
    public void setUp() throws InterruptedException {
        auth = new Auth(driver);
        leftMainMenu = new LeftMainMenu(driver);
        euroNews = new EuroNews(driver);

        // Authorization
        auth.getGoogleAuthPage();

        // Open Web Section
        auth.getWebSection();

        // Open Euro News Client List page in Web Section
        sTitle = leftMainMenu.getEuroNewsClientListPage(EURO_NEWS);
    }

    @Test
    public void checkEuroNewsClient() throws InterruptedException {
        final String expectedTitle = "Euro News Client List";

        Assert.assertNotNull(euroNews.getUrl());
        Assert.assertEquals(euroNews.getTitle(), expectedTitle, "Actual Euro News Client List page Title doesn't match to expected");
        Assert.assertNotNull(euroNews.getSearchInput(), "Search field doesn't exist");
    }

    @AfterTest
    public void tearDown() {
    }

}
