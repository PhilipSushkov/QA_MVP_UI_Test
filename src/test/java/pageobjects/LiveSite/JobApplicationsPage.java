package pageobjects.LiveSite;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import pageobjects.AbstractPageObject;

import javax.mail.MessagingException;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static specs.AbstractSpec.propUIPublicSite;
import static util.Functions.getRecentMail;

/**
 * Created by easong on 1/26/17.
 */

public class JobApplicationsPage extends AbstractPageObject {
    private final By firstNameField, addressField, lastNameField, cityField, countryField, homePhoneField;
    private final By businessPhoneField, faxField, provinceField, postalCodeField, emailField, successMessage;
    private final By coverLetterTextField, resumeTextField, submitApplication, applicationsHeader, errorMessages;

    private static String testAccount = "test@q4websystems.com", testPassword = "testing!";

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
        errorMessages = By.xpath(propUIPublicSite.getProperty("errorMessage"));
        successMessage = By.xpath(propUIPublicSite.getProperty("successMessage"));
    }

    /*
    public Boolean checkFirstNameRequiredField(JSONObject data){
        clearFields();
        enterFields(
                "",
                data.get("last_name").toString(),
                data.get("address").toString(),
                data.get("city").toString(),
                data.get("province").toString(),
                data.get("country").toString(),
                data.get("postal_code").toString(),
                data.get("home_phone").toString(),
                data.get("business_phone").toString(),
                data.get("fax").toString(),
                data.get("email").toString(),
                data.get("coverletter_text").toString(),
                data.get("resume_text").toString());
        submitApplication();

        return(findElement(errorMessages).getText().contains(data.get("firstNameMissing").toString()));
    }

    public Boolean checkEmailFormat(JSONObject data){
        clearFields();
        enterEmail(data.get("email_fail").toString());
        submitApplication();
        return(findElement(errorMessages).getText().contains(data.get("invalidEmail").toString()));
    }

    public Boolean checkSuccessSubmission(JSONObject data){
        clearFields();
        enterFields(
                data.get("first_name").toString(),
                data.get("last_name").toString(),
                data.get("address").toString(),
                data.get("city").toString(),
                data.get("province").toString(),
                data.get("country").toString(),
                data.get("postal_code").toString(),
                data.get("home_phone").toString(),
                data.get("business_phone").toString(),
                data.get("fax").toString(),
                data.get("email").toString(),
                data.get("coverletter_text").toString(),
                data.get("resume_text").toString());
        submitApplication();
        return(findElement(successMessage).getText().contains(data.get("successMessage").toString()));
    }

    public Boolean getEmailContents(JSONObject data) throws IOException, MessagingException {
        String content = getRecentMail(testAccount, testPassword, "Job Application for position" ).getContent().toString();
        System.out.print(content);
        if (
                content.contains(data.get("first_name").toString()) &&
                content.contains(data.get("last_name").toString()) &&
                content.contains(data.get("address").toString()) &&
                content.contains(data.get("city").toString()) &&
                content.contains(data.get("province").toString()) &&
                content.contains(data.get("country").toString()) &&
                content.contains(data.get("postal_code").toString()) &&
                content.contains(data.get("home_phone").toString()) &&
                content.contains(data.get("business_phone").toString()) &&
                content.contains(data.get("fax").toString()) &&
                content.contains(data.get("email").toString()) &&
                content.contains(data.get("coverletter_text").toString()) &&
                content.contains(data.get("resume_text").toString()))
            return true;
        return false;
    }
    */

    public String submitJobApplication(JSONObject data) {
        String sMessage = null;
        List<WebElement> elements = null;

        clearFields(elements);



        return sMessage;
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


    public void clearFields(List<WebElement> elements) {

    }

    /*
    public void clearFields(){
        waitForElementToAppear(applicationsHeader);
        findElement(firstNameField).clear();
        findElement(lastNameField).clear();
        findElement(addressField).clear();
        findElement(cityField).clear();
        findElement(provinceField).clear();
        findElement(countryField).clear();
        findElement(postalCodeField).clear();
        findElement(homePhoneField).clear();
        findElement(businessPhoneField).clear();
        findElement(faxField).clear();
        findElement(emailField).clear();
        findElement(coverLetterTextField).clear();
        findElement(resumeTextField).clear();
    }
    */

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


}
