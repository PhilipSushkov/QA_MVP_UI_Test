package pageobjects.LiveSite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import pageobjects.AbstractPageObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

import static specs.AbstractSpec.propUIPublicSite;

/**
 * Created by easong on 1/26/17.
 */

public class JobApplicationsPage extends AbstractPageObject {

    private final By firstNameField;
    private final By addressField;
    private final By lastNameField;
    private final By cityField;
    private final By countryField;
    private final By homePhoneField;
    private final By provinceField;
    private final By postalCodeField;
    private final By emailField;
    private final By coverLetterField;
    private final By submitApplication;
    private final By applicationsHeader;

    Actions actions = new Actions(driver);

    public JobApplicationsPage(WebDriver driver) {

        super(driver);
        firstNameField = By.xpath("/html/body[@class='BodyBackground']//input[contains(@id,'FirstName')]");
        addressField = By.xpath("/html/body[@class='BodyBackground']//input[contains(@id,'Address')]");
        lastNameField = By.xpath("/html/body[@class='BodyBackground']//input[contains(@id,'LastName')]");
        cityField = By.xpath("/html/body[@class='BodyBackground']//input[contains(@id,'City')]");
        countryField = By.xpath("/html/body[@class='BodyBackground']//input[contains(@id,'Country')]");
        homePhoneField = By.xpath("/html/body[@class='BodyBackground']//input[contains(@id,'HomePhone')]");
        provinceField = By.xpath("/html/body[@class='BodyBackground']//input[contains(@id,'Province')]");
        postalCodeField = By.xpath("/html/body[@class='BodyBackground']//input[contains(@id,'PostalCode')]");
        emailField = By.xpath("/html/body[@class='BodyBackground']//input[contains(@id,'Email')]");
        coverLetterField = By.xpath("/html/body[@class='BodyBackground']//textarea[contains(@id, 'txtCoverLetter')]");
        submitApplication = By.xpath("/html/body[@class='BodyBackground']//a[contains(@id, '_ctrl0_ctl45_lnkApply')]");
        applicationsHeader = By.xpath("/html/body[@class='BodyBackground']//span[contains(@id, 'ModuleTitle')]");

    }

    public void enterFirstName(String name)
    {
        findElement(firstNameField).sendKeys(name);
    }

    public void enterLastName(String lastname)
    {
        findElement(lastNameField).sendKeys(lastname);
    }

    public void enterAddress(String address)
    {
        findElement(addressField).sendKeys(address);
    }

    public void enterCity(String City)
    {
        findElement(cityField).sendKeys(City);
    }

    public void enterCountry(String Country)
    {
        findElement(countryField).sendKeys(Country);
    }

    public void enterHomePhone(String HomePhone)
    {
        findElement(homePhoneField).sendKeys(HomePhone);
    }

    public void enterProvince(String Province)
    {
        findElement(provinceField).sendKeys(Province);
    }

    public void enterPostalCodeField(String PostalCode)
    {
        findElement(postalCodeField).sendKeys(PostalCode);
    }

    public void enterEmailField(String email)
    {
        findElement(emailField).sendKeys(email);
    }

    public void enterCoverLetterField(String coverLetter)
    {
        findElement(coverLetterField).sendKeys(coverLetter);
    }

    public void submitApplication()
    {
        findElement(submitApplication).click();
    }

    public boolean applicationPageDisplayed()
    {
        return findElement(applicationsHeader).isDisplayed();
    }

}
