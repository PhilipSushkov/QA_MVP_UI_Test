package specs.PublicSite;

import org.apache.commons.lang.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.LiveSite.HomePage;
import pageobjects.LiveSite.SearchPage;
import specs.AbstractSpec;
import static org.hamcrest.CoreMatchers.containsString;


/**
 * Created by sarahr on 3/17/2017.
 */
public class CheckSearch extends AbstractSpec{

    //// WHEN ADDING A TEST TO THIS CLASS, ADD A ENTRY TO IT IN CheckSitePr.java \\\\

    private static HomePage homePage;

    @BeforeTest
    public void goToPublicSite() {

        driver.get("http://chicagotest.q4web.com/English/Investors/default.aspx");
        homePage = new HomePage(driver);
        Assert.assertTrue(homePage.logoIsPresent(), "Home page of public site has not been loaded.");

    }

    @Test
    public void regularSearchWorks(){
        //this test goes the the presentations page, finds a recent presentation,
        //then uses it as a search term, searches it, then sees if that presentation is a result

        SearchPage searchPage = new SearchPage(driver);
        String searchTerm = searchPage.getSearchableTerm();
        String searchSummary = searchPage.searchSomething(searchTerm);

        String ifThereIsResults = "Results";
        //Test to see if there is search results on the page
        org.hamcrest.MatcherAssert.assertThat("No results appear", searchSummary, containsString(ifThereIsResults));
        //Test to see if the correct presentation is a result
        Assert.assertTrue(searchPage.isTermInResults(searchTerm));
    }

    @Test
    public void badSearchReturnsNoResults(){
        SearchPage searchPage = new SearchPage(driver);

        //There is a very slight chance, that this random string of letter will turn up with a result
        //However that is highly unlikely
        String searchTerm = "QWERTYUIOP" + RandomStringUtils.randomAlphabetic(6);

        String searchSummary = searchPage.searchSomething(searchTerm);
        String badResult = "No results found";

        Assert.assertEquals(badResult, searchSummary, "Search returned results when it shouldn't have");

    }

    @Test
    public void nextPageWork(){
        SearchPage searchPage = new SearchPage(driver);
        //Need a search term to guarantee more than one page of results
        //Hardcoded search term, but as of 17/03/2017 it gets many results on this site
        String searchTerm = "SEC";
        searchPage.searchSomething(searchTerm);
        boolean testResult = searchPage.goToNextPage();

        //Assert that there is search results available
        Assert.assertTrue(testResult, "Next page does not have results");

    }

}
