package specs.PublicSite;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;

import org.testng.annotations.Test;
import pageobjects.LiveSite.HomePage;
import pageobjects.LiveSite.JobApplicationsPage;
import specs.AbstractSpec;

/**
 * Created by andyp on 2017-05-17.
 */
public class CheckJobApplicationsPage extends AbstractSpec {
    //Data
    private final String firstName = "First";
    private final String lastName = "Last";
    private final String address = "Street";
    private final String country = "Country";
    private final String city = "City";
    private final String province = "Province";
    private final String postalCode = "Postal Code";
    private final String homePhone = "111-111-1111";
    private final String businessPhone = "222-222-2222";
    private final String fax = "idk fax";
    private final String email = "email@email.com";
    private final String coverLetterText = "Hire me";
    private final String resumeText = "Resume";

    private static HomePage homePage;
    private static JobApplicationsPage jobApplicationsPage;

    @BeforeTest
    public void setUp(){
        driver.get("http://chicagotest.q4web.com/English/Investors/default.aspx");
        homePage = new HomePage(driver);
        jobApplicationsPage = new JobApplicationsPage(driver);
        homePage.selectJobApplicationFromMenu();
    }

    @Test
    public void canNavigateToJobApplicationsPage() {
        try {
            Assert.assertTrue(homePage.selectJobApplicationFromMenu().applicationPageDisplayed(), "Job Applications Page couldn't be opened");
        } catch (TimeoutException e) {
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
    }

    @Test
    public void firstNameFieldNotFilled(){
        jobApplicationsPage.clearFields();
        jobApplicationsPage.enterFields("",lastName, address, city, province, country, postalCode,
                homePhone, businessPhone, fax, email, coverLetterText, resumeText);
        jobApplicationsPage.submitApplication();

        //Checking to see if you get validation error
        Assert.assertNotNull(jobApplicationsPage.checkErrorMessages(), "There are no error messages");
        //Checking if it is a "First Name is required" error
        Assert.assertTrue(jobApplicationsPage.getErrorMessage("First Name is required"));
    }

    @Test
    public void wrongEmailFormatting() {
        jobApplicationsPage.clearFields();
        String wrongEmail = "dogdog";
        String wrongEmail2 = "dogdog@dog";
        String wrongEmail3 = "dogdog@dog.";

        jobApplicationsPage.enterEmail(wrongEmail);
        jobApplicationsPage.submitApplication();
        Assert.assertNotNull(jobApplicationsPage.checkErrorMessages(), "There are no error messages");
        Assert.assertTrue(jobApplicationsPage.getErrorMessage("Email is invalid"));

        jobApplicationsPage.enterEmail(wrongEmail2);
        jobApplicationsPage.submitApplication();
        Assert.assertNotNull(jobApplicationsPage.checkErrorMessages(), "There are no error messages");
        Assert.assertTrue(jobApplicationsPage.getErrorMessage("Email is invalid"));

        jobApplicationsPage.enterEmail(wrongEmail3);
        jobApplicationsPage.submitApplication();
        Assert.assertNotNull(jobApplicationsPage.checkErrorMessages(), "There are no error messages");
        Assert.assertTrue(jobApplicationsPage.getErrorMessage("Email is invalid"));

        //Checking to see if the error for invalid email is gone
        jobApplicationsPage.enterEmail(email);
        jobApplicationsPage.submitApplication();
        Assert.assertNotNull(jobApplicationsPage.checkErrorMessages(), "There are no error messages");
        Assert.assertFalse(jobApplicationsPage.getErrorMessage("Email is invalid"));

    }

    @Test(dependsOnMethods = {"wrongEmailFormatting"})
    public void successfulSubmission(){
        jobApplicationsPage.clearFields();
        jobApplicationsPage.enterFields(firstName,lastName, address, city, province, country, postalCode,
                homePhone, businessPhone, fax, email, coverLetterText, resumeText);
        jobApplicationsPage.submitApplication();

        Assert.assertTrue(jobApplicationsPage.getSuccessMessage());
    }
}
