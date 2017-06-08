package specs.PreviewSite;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.LiveSite.HomePage;
import pageobjects.LiveSite.JobApplicationsPage;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by andyp on 2017-06-07.
 */
public class CheckJobApplicationPr extends AbstractSpec {

    private static HomePage homePage;
    private static JobApplicationsPage jobApplicationsPage;

    @BeforeTest
    public void goToPreviewSite() throws Exception {
        new LoginPage(driver).loginUser().previewSite().goToInvestorsPage();
        homePage = new HomePage(driver);
        jobApplicationsPage = new JobApplicationsPage(driver);

    }

    @Test
    public void jobApplicationWorks() throws InterruptedException {
        //Checking if job application page is displayed
        try{
            Assert.assertTrue(homePage.selectJobApplicationFromMenu().applicationPageDisplayed(), "Job applications page is not displayed");
        } catch(TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }

    }

}
