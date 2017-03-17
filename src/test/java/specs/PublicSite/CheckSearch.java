package specs.PublicSite;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.LiveSite.HomePage;
import pageobjects.LiveSite.LivePresentations;
import pageobjects.LiveSite.SearchPage;
import specs.AbstractSpec;


/**
 * Created by sarahr on 3/17/2017.
 */
public class CheckSearch extends AbstractSpec{

    //// WHEN ADDING A TEST TO THIS CLASS, ADD A ENTRY TO IT IN CheckPreviewSite.java \\\\



    private static HomePage homePage;

    @BeforeTest
    public void goToPublicSite() {

        driver.get("http://chicagotest.q4web.com/English/Investors/default.aspx");

        homePage = new HomePage(driver);

        Assert.assertTrue(homePage.logoIsPresent(), "Home page of public site has not been loaded.");

    }

    //Tests need to be made:
    //that results appear in a regular search
    //that the appropriate message appears with a sour search
    //That search works on any page

    @Test
    public void regularSearchWorks(){
        //this test goes the the presentations page, finds a recent presentation,
        //then uses it as a search term, searches it, then sees if that presentation is a result

        SearchPage searchPage = new SearchPage(driver);
        String searchTerm = searchPage.getSearchableTerm();
        searchPage.searchSomething(searchTerm);

    }

    @Test
    public void badSearchReturnsNoResults(){

    }

    @Test
    public void searchWorksOnAnyPage(){

    }
}
