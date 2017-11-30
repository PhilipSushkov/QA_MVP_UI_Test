package specs.PublicSite;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.LiveSite.SearchPage;

/**
 * Created by juntianz on 11/27/2017.
 */

public class CheckScriptRequest{
    private static String request1 = "<body onload=alert('test1')>",
                        request2 = "<b onmouseover=alert('Wufff!')>click me!</b>",
                        request3 = "<img src=\"http://url.to.file.which/not.exist\" onerror=alert(document.cookie);>",
                        request4 = "<a href=\"javascript:alert('XSS')\">test</test>",
                        request5 = "<iframe src=javascript:alert('XSS'></iframe>";

    public WebDriver driver = new ChromeDriver();

    @BeforeTest
    public void goToPublicSite() {
        driver.get("http://salesforce.q4web.dev/about-us/investor/overview/default.aspx");
    }

    @Test
    public void noPopUpWindowShows(){
        SearchPage searchPage = new SearchPage(driver);
        //Search for the request in the search bar and checks for pop up windows
        searchPage.searchSomething(request1);
        Assert.assertEquals(searchPage.getModuleTitle(), "Search Results", "Failed getting module title");
        Assert.assertFalse(searchPage.getPopUpWindow());
        searchPage.searchSomething(request2);
        Assert.assertEquals(searchPage.getModuleTitle(), "Search Results", "Failed getting module title");
        Assert.assertFalse(searchPage.getPopUpWindow());
        searchPage.searchSomething(request3);
        Assert.assertEquals(searchPage.getModuleTitle(), "Search Results", "Failed getting module title");
        Assert.assertFalse(searchPage.getPopUpWindow());
        searchPage.searchSomething(request4);
        Assert.assertEquals(searchPage.getModuleTitle(), "Search Results", "Failed getting module title");
        Assert.assertFalse(searchPage.getPopUpWindow());
        searchPage.searchSomething(request5);
        Assert.assertEquals(searchPage.getModuleTitle(), "Search Results", "Failed getting module title");
        Assert.assertFalse(searchPage.getPopUpWindow());
    }

    @AfterTest
    public void tearDown(){
        driver.quit();
    }
}
