package specs.PublicSite;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.LiveSite.*;
import specs.AbstractSpec;


/**
 * Created by easong on 1/23/17.
 */
public class CheckFAQPage extends AbstractSpec  {

    private final String Q4WebVersionNumber = "4.3.0.63";

    //// WHEN ADDING A TEST TO THIS CLASS, ADD A ENTRY TO IT IN CheckPreviewSite.java \\\\


    /** Changes necessary to make include removing all the "new XXX(drivers).YYY with a declaration, instead, in the @Before
     and replacing it in all pieces of code. DONE
     Second changes include making a .properties file including ALL the selectors. THEN making them all do the .property thing. DONE
     Last is switching this to testNG*/

    private static HomePage homePage;

    @BeforeTest
    public void goToPublicSite() {

        driver.get("http://chicagotest.q4web.com/English/Investors/default.aspx");
        //driver.get("http://fiesta.q4web.newtest/stock-information/default.aspx");

        homePage = new HomePage(driver);

        Assert.assertTrue(homePage.logoIsPresent(), "Home page of public site has not been loaded.");

    }


    @Test
    public void faqPageWorks(){
        // going to FAQ page and checking that at least one question is displayed at the top of the page
        FAQPage faqPage = homePage.selectFAQFromMenu();
        int numQuestionsTop = faqPage.getNumQuestionsTop();
        Assert.assertTrue(numQuestionsTop > 0, "No questions are displayed at top of page." );

        // checking that the same number of questions are displayed at the top of the page as are displayed below
        int numQuestionsBelow = faqPage.getNumQuestionsBelow();
        Assert.assertEquals(numQuestionsBelow, numQuestionsTop
                ,"Number of questions displayed at below is different from number of questions displayed at top of page");

        // checking that there is an answer for every question
        int numAnswers = faqPage.getNumAnswers();
        Assert.assertEquals(numAnswers, numQuestionsBelow, "There is not an answer for every question");
        // clicking on the first question (on the top of the page) and checking that the page scrolls down to that question below
        // this may not work if the vertical distance between the first question below and the bottom of the page is less than the window height

        Assert.assertTrue(faqPage.doesAnchorLinkWork(), "The link does not direct anywhere");


    }

}
