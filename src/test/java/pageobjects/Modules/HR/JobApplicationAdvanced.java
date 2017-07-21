package pageobjects.Modules.HR;

import com.jayway.jsonpath.JsonPath;
import netscape.javascript.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;
import pageobjects.PageAdmin.WorkflowState;
import pageobjects.PageObject;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static specs.AbstractSpec.*;

/**
 * Created by andyp on 2017-07-20.
 */
public class JobApplicationAdvanced extends AbstractPageObject {
    private static By workflowStateSpan, propertiesHref, commentsTxt, saveAndSubmitBtn;
    private static String sPathToModuleFile, sFileModuleJson, sResumeFile;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String firstNameField, addressField, lastNameField, cityField, countryField, homePhoneField;
    private final String businessPhoneField, faxField, provinceField, postalCodeField, emailField;
    private final String coverLetterTextField, resumeTextField, submitApplication;
    private final String uploadResume, uploadCoverLetter, validationSummary, submittedMessage;

    public JobApplicationAdvanced(WebDriver driver) {
        super(driver);

        workflowStateSpan = By.xpath(propUIPageAdmin.getProperty("select_WorkflowState"));
        commentsTxt = By.xpath(propUIPageAdmin.getProperty("txtarea_Comments"));
        propertiesHref = By.xpath(propUIModules.getProperty("href_Properties"));
        saveAndSubmitBtn = By.xpath(propUIPageAdmin.getProperty("btn_SaveAndSubmit"));

        sPathToModuleFile = System.getProperty("user.dir") + propUIModulesHR.getProperty("dataPath_HR");
        sFileModuleJson = propUIModulesHR.getProperty("json_JobApplicationAdvancedProp");
        sResumeFile = System.getProperty("user.dir") + "/src/test/java/pageobjects/LiveSite/Resume.txt";

        parser = new JSONParser();

        firstNameField = propUIModulesHR.getProperty("field_FirstName");
        lastNameField = propUIModulesHR.getProperty("field_LastName");
        addressField = propUIModulesHR.getProperty("field_Address");
        cityField = propUIModulesHR.getProperty("field_City");
        provinceField = propUIModulesHR.getProperty("field_Province");
        countryField = propUIModulesHR.getProperty("field_Country");
        postalCodeField = propUIModulesHR.getProperty("field_PostalCode");
        homePhoneField = propUIModulesHR.getProperty("field_HomePhone");
        businessPhoneField = propUIModulesHR.getProperty("field_BusinessPhone");
        faxField = propUIModulesHR.getProperty("field_Fax");
        emailField = propUIModulesHR.getProperty("field_Email");
        coverLetterTextField = propUIModulesHR.getProperty("field_CoverLetterText");
        resumeTextField = propUIModulesHR.getProperty("field_ResumeText");
        submitApplication = propUIModulesHR.getProperty("btn_Submit");
        uploadResume = propUIModulesHR.getProperty("btn_Resume");
        uploadCoverLetter = propUIModulesHR.getProperty("btn_Coverletter");
        validationSummary = propUIModulesHR.getProperty("div_validationSummary");
        submittedMessage = propUIModulesHR.getProperty("div_ModuleContainer");

    }

    public String saveAndSubmitModule(JSONObject modulesDataObj, String moduleName) throws InterruptedException {

        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToModuleFile + sFileModuleJson));
            JSONArray jsonArrProp = (JSONArray) modulesDataObj.get("properties");

            String moduleUrl = getModuleUrl(jsonObj, moduleName);
            driver.get(moduleUrl);
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(commentsTxt);

            JSONObject module = (JSONObject) jsonObj.get(moduleName);

            findElement(propertiesHref).click();
            Thread.sleep(DEFAULT_PAUSE);

            for (int i = 0; i < jsonArrProp.size(); i++) {
                try {
                    By propertyTextValue = By.xpath("//td[contains(@class, 'DataGridItemBorderLeft')][(text()='" + jsonArrProp.get(i).toString().split(";")[0] + "')]/parent::tr/td/div/input[contains(@id, 'txtStatic')]");
                    findElement(propertyTextValue).clear();
                    findElement(propertyTextValue).sendKeys(jsonArrProp.get(i).toString().split(";")[1]);
                } catch (PageObject.ElementNotFoundException e) {
                    By propertyDropdownValue = By.xpath("//td[contains(@class, 'DataGridItemBorderLeft')][(text()='" + jsonArrProp.get(i).toString().split(";")[0] + "')]/parent::tr/td/div/select[contains(@id, 'ddlDynamic')]");
                    findElement(propertyDropdownValue).sendKeys(jsonArrProp.get(i).toString().split(";")[1]);
                }
            }

            findElement(commentsTxt).sendKeys(modulesDataObj.get("comment").toString());
            findElement(saveAndSubmitBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            driver.get(moduleUrl);
            Thread.sleep(DEFAULT_PAUSE);

            module.put("properties", jsonArrProp);
            module.put("workflow_state", WorkflowState.FOR_APPROVAL.state());

            jsonObj.put(moduleName, module);

            FileWriter file = new FileWriter(sPathToModuleFile + sFileModuleJson);
            file.write(jsonObj.toJSONString().replace("\\", ""));
            file.flush();

            System.out.println(moduleName + ": New " + moduleName + " has been submitted");
            return findElement(workflowStateSpan).getText();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String goToModuleEditPage(String moduleName) {
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToModuleFile + sFileModuleJson));
            driver.get(getModuleUrl(jsonObj, moduleName));
            findElement(propertiesHref).click();
            return findElement(workflowStateSpan).getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getModuleUrl(JSONObject obj, String moduleName) {
        String sItemID = JsonPath.read(obj, "$.['" + moduleName + "'].url_query.ItemId");
        String sLanguageId = JsonPath.read(obj, "$.['" + moduleName + "'].url_query.LanguageId");
        String sSectionId = JsonPath.read(obj, "$.['" + moduleName + "'].url_query.SectionId");
        return desktopUrl.toString() + "default.aspx?ItemID=" + sItemID + "&LanguageId=" + sLanguageId + "&SectionId=" + sSectionId;
    }

    public ArrayList<String> getMultipartEmailContent(String user, String password, String subjectID) throws IOException, MessagingException, InterruptedException {
        System.out.println("Checking multipart email");
        for(int i =1;i <=5; i++){
            if (getSpecificMail(user, password, subjectID) == null){
                System.out.println("Email was not found, attempt # "+ i);
            } else{
                System.out.println("Email found");
                break;
            }
        }
        ArrayList<String> content = new ArrayList<String>();
        Message msg = getSpecificMail(user, password, subjectID);
        Multipart mp = (Multipart) msg.getContent();
        for (int i = 0; i < mp.getCount(); i++) {
            BodyPart bp = mp.getBodyPart(i);
            String disp = bp.getDisposition();

            //Checks the content of the email
            if (disp != null && (disp.equalsIgnoreCase("ATTACHMENT"))) {
                DataHandler handler = bp.getDataHandler();
                content.add(handler.getName());
            } else {
                content.add(bp.getContent().toString());
            }
        }
        return content;
    }

    public String getEmailContent(String user, String password, String subjectID) throws InterruptedException, IOException, MessagingException {
        System.out.println("Checking email");
        for(int i =1;i <=5; i++){
            if (getSpecificMail(user, password, subjectID) == null){
                System.out.println("Email was not found, attempt # "+ i);
            } else{
                System.out.println("Email found");
                break;
            }
        }
        Message msg = getSpecificMail(user, password, subjectID);
        String content = String.valueOf(msg.getContent());
        System.out.println("Email was sent at: "+ msg.getSentDate());

        return content;
    }

    public void enterFields(JSONObject module) {
        findElement(By.xpath(module.get("module_path") + firstNameField)).sendKeys(module.get("first_name").toString());
        findElement(By.xpath(module.get("module_path") + lastNameField)).sendKeys(module.get("last_name").toString());
        findElement(By.xpath(module.get("module_path") + addressField)).sendKeys(module.get("address").toString());
        findElement(By.xpath(module.get("module_path") + cityField)).sendKeys(module.get("city").toString());
        findElement(By.xpath(module.get("module_path") + provinceField)).sendKeys(module.get("province").toString());
        findElement(By.xpath(module.get("module_path") + countryField)).sendKeys(module.get("country").toString());
        findElement(By.xpath(module.get("module_path") + postalCodeField)).sendKeys(module.get("postal_code").toString());
        findElement(By.xpath(module.get("module_path") + homePhoneField)).sendKeys(module.get("home_phone").toString());
        findElement(By.xpath(module.get("module_path") + businessPhoneField)).sendKeys(module.get("business_phone").toString());
        findElement(By.xpath(module.get("module_path") + faxField)).sendKeys(module.get("fax").toString());
        findElement(By.xpath(module.get("module_path") + emailField)).sendKeys(module.get("email").toString());
        findElement(By.xpath(module.get("module_path") + coverLetterTextField)).sendKeys(module.get("coverletter_text").toString());
        findElement(By.xpath(module.get("module_path") + resumeTextField)).sendKeys(module.get("resume_text").toString());
        findElement(By.xpath(module.get("module_path") + uploadResume)).sendKeys(sResumeFile);
    }

    public void clickSubmit(JSONObject module) {
        findElement(By.xpath(module.get("module_path") + submitApplication)).click();
    }

    public String getContent(ArrayList<String> arrayList) {
        return arrayList.get(0);
    }

    public String getFile(ArrayList<String> arrayList) {
        return arrayList.get(1);
    }

    public boolean getFirstName(JSONObject module, String content){
        System.out.println("Checking first name");
        if(content.contains(module.get("first_name").toString()))
            return true;
        return false;
    }

    public boolean getLastName(JSONObject module, String content){
        System.out.println("Checking last name");
        if(content.contains(module.get("last_name").toString()))
            return true;
        return false;
    }

    public boolean getAddress(JSONObject module, String content){
        System.out.println("Checking address");
        if(content.contains(module.get("address").toString()))
            return true;
        return false;
    }

    public boolean getCity(JSONObject module, String content){
        System.out.println("Checking city");
        if(content.contains(module.get("city").toString()))
            return true;
        return false;
    }

    public boolean getProvince(JSONObject module, String content){
        System.out.println("Checking province");
        if(content.contains(module.get("province").toString()))
            return true;
        return false;
    }

    public boolean getCountry(JSONObject module, String content){
        System.out.println("Checking country");
        if(content.contains(module.get("country").toString()))
            return true;
        return false;
    }

    public boolean getPostalCode(JSONObject module, String content){
        System.out.println("Checking postal code");
        if(content.contains(module.get("postal_code").toString()))
            return true;
        return false;
    }

    public boolean getHomePhone(JSONObject module, String content){
        System.out.println("Checking home phone");
        if(content.contains(module.get("home_phone").toString()))
            return true;
        return false;
    }

    public boolean getBusinessPhone(JSONObject module, String content){
        System.out.println("Checking business phone");
        if(content.contains(module.get("business_phone").toString()))
            return true;
        return false;
    }

    public boolean getFax(JSONObject module, String content){
        System.out.println("Checking fax");
        if(content.contains(module.get("fax").toString()))
            return true;
        return false;
    }

    public boolean getEmail(JSONObject module, String content){
        System.out.println("Checking email");
        if(content.contains(module.get("email").toString()))
            return true;
        return false;
    }

    public boolean getCoverLetterText(JSONObject module, String content){
        System.out.println("Checking coverletter text");
        if(content.contains(module.get("coverletter_text").toString()))
            return true;
        return false;
    }

    public boolean getResumeText(JSONObject module, String content){
        System.out.println("Checking resume text");
        if(content.contains(module.get("resume_text").toString()))
            return true;
        return false;
    }

    public boolean getResumeFile(JSONObject module, String content){
        System.out.println("Checking resume file");
        if(content.contains(module.get("resume_file").toString()))
            return true;
        return false;
    }

    public boolean getValidationSummary(JSONObject module) {
        System.out.println("Checking validation summary");
        if (findElement(By.xpath(module.get("module_path") + validationSummary)).getText().contains(module.get("expected_message").toString()))
            return true;
        return false;
    }

    public boolean getSubmittedMessage(JSONObject module){
        System.out.println("Checking submitted message");
        if (findElement(By.xpath(module.get("module_path") + submittedMessage)).getText().contains(module.get("expected_message").toString()))
            return true;
        return false;
    }
}

