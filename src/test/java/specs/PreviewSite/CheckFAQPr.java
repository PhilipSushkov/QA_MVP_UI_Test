package specs.PreviewSite;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.LiveSite.FAQPage;
import pageobjects.LiveSite.HomePage;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;
import specs.PublicSite.CheckPublicSite;

/**
 * Created by easong on 1/24/17.
 */
public class CheckFAQPr extends AbstractSpec {

    //// THERE SHOULD BE ONE TEST HERE FOR EVERY TEST ON CheckPublicSite.java \\\\

    private CheckPublicSite publicTests = new CheckPublicSite();
    private static HomePage homePage;

    @BeforeTest

    public void goToPreviewSite() throws Exception {
        new LoginPage(driver).loginUser().previewSite().goToInvestorsPage();
        homePage = new HomePage(driver);
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
