package pageobjects.LiveSite;

import org.openqa.selenium.*;
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
    private final By businessPhoneField;
    private final By faxField;
    private final By provinceField;
    private final By postalCodeField;
    private final By emailField;
    private final By coverLetterTextField;
    private final By resumeTextField;
    private final By submitApplication;
    private final By applicationsHeader;
    private final By errorMessages;

    Actions actions = new Actions(driver);

    public JobApplicationsPage(WebDriver driver) {

        super(driver);

        firstNameField = By.xpath(propUIPublicSite.getProperty("field_firstName"));
        lastNameField = By.xpath(propUIPublicSite.getProperty("field_lastName"));
        addressField = By.xpath(propUIPublicSite.getProperty("field_address"));
        cityField = By.xpath(propUIPublicSite.getProperty("field_city"));
        provinceField = By.xpath(propUIPublicSite.getProperty("field_province"));
        countryField = By.xpath(propUIPublicSite.getProperty("field_country"));
        postalCodeField = By.xpath(propUIPublicSite.getProperty("field_postalCode"));
        homePhoneField = By.xpath(propUIPublicSite.getProperty("field_homePhone"));
        businessPhoneField = By.xpath(propUIPublicSite.getProperty("field_businessPhone"));
        faxField = By.xpath(propUIPublicSite.getProperty("field_fax"));
        emailField = By.xpath(propUIPublicSite.getProperty("field_email"));
        coverLetterTextField = By.xpath(propUIPublicSite.getProperty("field_coverLetterText"));
        resumeTextField = By.xpath(propUIPublicSite.getProperty("field_resumeText"));
        submitApplication = By.xpath(propUIPublicSite.getProperty("btn_submit"));
        applicationsHeader = By.xpath(propUIPublicSite.getProperty("applicationHeader"));
        errorMessages = By.xpath((propUIPublicSite).getProperty("errorMessage"));
    }

    public void enterFields(String firstName, String lastName, String address, String city, String province,
                            String country, String postalCode, String homePhone, String businessPhone,
                            String fax, String email, String coverLetter, String resume){
        waitForElementToAppear(applicationsHeader);
        findElement(firstNameField).sendKeys(firstName);
        findElement(lastNameField).sendKeys(lastName);
        findElement(addressField).sendKeys(address);
        findElement(cityField).sendKeys(city);
        findElement(provinceField).sendKeys(province);
        findElement(countryField).sendKeys(country);
        findElement(postalCodeField).sendKeys(postalCode);
        findElement(homePhoneField).sendKeys(homePhone);
        findElement(businessPhoneField).sendKeys(businessPhone);
        findElement(faxField).sendKeys(fax);
        findElement(emailField).sendKeys(email);
        findElement(coverLetterTextField).sendKeys(coverLetter);
        findElement(resumeTextField).sendKeys(resume);

    }

    public void enterEmail(String email){
        waitForElementToAppear(applicationsHeader);
        findElement(emailField).clear();
        findElement(emailField).sendKeys(email);
    }

    public void submitApplication()
    {
        findElement(submitApplication).click();
    }

    public boolean applicationPageDisplayed()
    {
        return findElement(applicationsHeader).isDisplayed();
    }

    public WebElement checkErrorMessages() {
        WebElement element = null;

        try {
            waitForElement(errorMessages);
            element = findElement(errorMessages);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public boolean getErrorMessage(String missingField){
        return findElement(errorMessages).getText().contains(missingField);
    }
}
