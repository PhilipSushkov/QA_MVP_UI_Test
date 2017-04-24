package specs.PublicSite;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.LiveSite.HomePage;
import pageobjects.LiveSite.SECFilingsPage;
import specs.AbstractSpec;

import java.util.Calendar;

/**
 * Created by sarahr on 4/12/2017.
 */
public class CheckSECFilingsPage extends AbstractSpec {

    private static HomePage homePage;
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);

    @BeforeTest
    public void goToPublicSite() {

        driver.get("http://chicagotest.q4web.com/English/Investors/default.aspx");
        homePage = new HomePage(driver);
        Assert.assertTrue(homePage.logoIsPresent(), "Home page of public site has not been loaded.");

    }

    @BeforeMethod
    public void goToPage(){
        homePage.selectSECFilingsFromMenu();
    }

    @Test
    public void checkIfDetailsPageWorks(){
        SECFilingsPage sec = new SECFilingsPage(driver);

        int year = currentYear;
        boolean yearLoop = false;

        while(!yearLoop) {
            String yearString = Integer.toString(year);
            try {
                sec.switchYearTo(yearString);
                yearLoop = sec.doFilingsExist();
                year = year - 1;
            } catch (NullPointerException e) {
                //no more years
                Assert.assertTrue(yearLoop, "There is NO SEC Filings AT ALL - Cannot test accurately");
            }
        }
        //filings exist
        //get date and title
        String title = sec.getSECTitle(1);
        String date = sec.getSECDate(1);
        sec.clickSECFiling(1);
        Assert.assertTrue(sec.detailsPageAppears(),"Details page title is missing.");
        Assert.assertTrue(sec.detailsPageCorrectInfo(title, date),"Information on details page is not correct");

    }

    //This year checks 2017, and if there is no filings for that year, then it goes back a year
    @Test
    public void checkIfYearFilteringWorks(){
        SECFilingsPage sec = new SECFilingsPage(driver);
        Assert.assertTrue(sec.checkAllYears(),"Nope.");
    }

    //test to see if they are from the proper filter
    @Test
    public void checkIfTypeFilteringWorks(){
        SECFilingsPage sec = new SECFilingsPage(driver);
        Assert.assertTrue(sec.checkAllFilters(),"Filtering is not working properly");
    }

    //Checks to see if filing exists for All Forms. If it does,'t it switches year until it finds a filing
    //Once there is a filing, checks the pfd to see if it works
    @Test
    public void checkIfPDFWorks(){
        SECFilingsPage sec = new SECFilingsPage(driver);
        int year = currentYear;
        boolean yearLoop = false;

        while(!yearLoop) {
            String yearString = Integer.toString(year);
            try {
                sec.switchYearTo(yearString);
                yearLoop = sec.doFilingsExist();
                year = year - 1;
            }
            catch(NullPointerException e){
                //no more years
                Assert.assertTrue(yearLoop,"There is NO SEC Filings AT ALL - Cannot test accurately");
            }
        }

        Assert.assertTrue(sec.pdfIconsLinkToPDF(),"Something is wrong with the PDF icons");

    }

}
