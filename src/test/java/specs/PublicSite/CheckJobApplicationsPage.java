package specs.PublicSite;

import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.LiveSite.*;
import specs.AbstractSpec;

import java.io.IOException;
import java.util.Calendar;

import static org.testng.Assert.fail;

/**
 * Created by easong on 1/26/17.
 */
public class CheckJobApplicationsPage extends AbstractSpec {

    private final String firstName = "Luke";
    private final String lastName = "Skywalker";
    private final String address = "55 Darth Street";
    private final String country = "tattooine";
    private final String city = "JabbaVille";
    private final String province = "the farming part";
    private final String postalCode = "C3POBB8";
    private final String homePhone = "r2d2";
    private final String email = "jedimaster227@rebels.com";
    private final String coverLetter = "I am his father - Darth Vader";

    private static HomePage homePage;
    private static JobApplicationsPage jobApplicationsPage;

    @BeforeTest
    public void goToPublicSite() {

        driver.get("http://chicagotest.q4web.com/English/Investors/default.aspx");
        //driver.get("http://fiesta.q4web.newtest/stock-information/default.aspx");

        homePage = new HomePage(driver);
        jobApplicationsPage = new JobApplicationsPage(driver);

        Assert.assertTrue(homePage.logoIsPresent(), "Home page of public site has not been loaded.");

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
        public void canSubmitJobApplication ()
        {
            homePage.selectJobApplicationFromMenu();
            jobApplicationsPage.enterFirstName(firstName);
            jobApplicationsPage.enterAddress(address);
            jobApplicationsPage.enterLastName(lastName);
            jobApplicationsPage.enterCity(city);
            jobApplicationsPage.enterCountry(country);
            jobApplicationsPage.enterHomePhone(homePhone);
            jobApplicationsPage.enterProvince(province);
            jobApplicationsPage.enterPostalCodeField(postalCode);
            jobApplicationsPage.enterEmailField(email);
            jobApplicationsPage.enterCoverLetterField(coverLetter);
            jobApplicationsPage.submitApplication();

        }
    }

