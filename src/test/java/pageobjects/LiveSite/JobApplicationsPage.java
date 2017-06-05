package pageobjects.LiveSite;

import org.json.simple.JSONObject;
import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import java.io.File;
import java.io.IOException;

import static specs.AbstractSpec.propUIPublicSite;
import static util.Functions.*;


/**
 * Created by easong on 1/26/17.
 */

public class JobApplicationsPage extends AbstractPageObject {
    private final By firstNameField, addressField, lastNameField, cityField, countryField, homePhoneField;
    private final By businessPhoneField, faxField, provinceField, postalCodeField, emailField;
    private final By coverLetterTextField, resumeTextField, submitApplication, applicationsHeader;
    private final By uploadResume, uploadCoverLetter;

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
        uploadResume = By.xpath(propUIPublicSite.getProperty("btn_resume"));
        uploadCoverLetter = By.xpath(propUIPublicSite.getProperty("btn_coverletter"));
        applicationsHeader = By.xpath(propUIPublicSite.getProperty("applicationHeader"));

    }

    public String submitJobApplication(JSONObject data) {
        String sMessage;

        cleanTextFields(findElements(By.xpath("//input[@type='text']")));
        cleanTextFields(findElements(By.xpath("//textarea")));
        enterFields(data);
        submitApplication();

        sMessage = getSysMessage(data).getText();

        return sMessage;
    }

    public boolean checkEmail(JSONObject data) throws IOException, MessagingException, InterruptedException {
        String content = (getSpecificMail("test@q4websystems.com", "testing!", "Job Application for position").getContent()).toString();

        //Return true if content contains all of the items below - ASSERTION FOR EACH ONE?
        return (
                content.contains(data.get("first_name").toString()) &&
                content.contains(data.get("last_name").toString()) &&
                content.contains(data.get("address").toString()) &&
                content.contains(data.get("city").toString()) &&
                content.contains(data.get("province").toString()) &&
                content.contains(data.get("country").toString()) &&
                content.contains(data.get("postal_code").toString())&&
                content.contains(data.get("home_phone").toString()) &&
                content.contains(data.get("business_phone").toString()) &&
                content.contains(data.get("fax").toString()) &&
                content.contains(data.get("email").toString()) &&
                content.contains(data.get("coverletter_text").toString()) &&
                content.contains(data.get("resume_text").toString())
                );
    }


    public boolean checkAttachments(JSONObject data) throws IOException, MessagingException, InterruptedException {
        Message msg = getSpecificMail("test@q4websystems.com", "testing!", "Job Application for position");

        Multipart mp = (Multipart) msg.getContent();
        for (int i = 0; i < mp.getCount(); i++){
            BodyPart bp = mp.getBodyPart(i);
            String disp = bp.getDisposition();

            if (disp != null && (disp.equalsIgnoreCase("ATTACHMENT"))){
                DataHandler handler = bp.getDataHandler();

                if (handler.getName().equals(data.get("resume_file"))){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasAttachments(JSONObject data) throws MessagingException, IOException, InterruptedException {
        Message msg = getSpecificMail("test@q4websystems.com", "testing!", "Job Application for position");

        if (msg != null) {
            if (msg.isMimeType("multipart/mixed")) {
                Multipart mp = (Multipart) msg.getContent();
                if (mp.getCount() > 1)
                return true;
            }
        } return false;
    }

    public void enterFields(JSONObject data){
        findElement(firstNameField).sendKeys(data.get("first_name").toString());
        findElement(lastNameField).sendKeys(data.get("last_name").toString());
        findElement(addressField).sendKeys(data.get("address").toString());
        findElement(cityField).sendKeys(data.get("city").toString());
        findElement(provinceField).sendKeys(data.get("province").toString());
        findElement(countryField).sendKeys(data.get("country").toString());
        findElement(postalCodeField).sendKeys(data.get("postal_code").toString());
        findElement(homePhoneField).sendKeys(data.get("home_phone").toString());
        findElement(businessPhoneField).sendKeys(data.get("business_phone").toString());
        findElement(faxField).sendKeys(data.get("fax").toString());
        findElement(emailField).sendKeys(data.get("email").toString());
        findElement(coverLetterTextField).sendKeys(data.get("coverletter_text").toString());
        findElement(resumeTextField).sendKeys(data.get("resume_text").toString());
        findElement(uploadResume).sendKeys(getFilePath(data));
    }


    public void submitApplication()
    {
        findElement(submitApplication).click();
    }

    public boolean applicationPageDisplayed()
    {
        return findElement(applicationsHeader).isDisplayed();
    }

    public WebElement getSysMessage(JSONObject data) {
        WebElement element = null;

        try {
            By sysMessage = By.xpath(data.get("expected_path").toString());
            element = findElement(sysMessage);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public String getFilePath(JSONObject data){
        //Checks file if check_file is set to true
        if (data.get("check_file").toString() == "true"){
            //Finds path to the file
            String path = new File(data.get("resume_file").toString()).getAbsolutePath();

            return path;
        }
        return "";

    }
}
