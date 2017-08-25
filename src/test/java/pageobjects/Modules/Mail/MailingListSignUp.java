package pageobjects.Modules.Mail;

import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;
import pageobjects.PageAdmin.WorkflowState;
import pageobjects.PageObject;

import javax.mail.MessagingException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static specs.AbstractSpec.*;

/**
 * Created by andyp on 2017-07-26.
 */
public class MailingListSignUp extends AbstractPageObject {
    private static By workflowStateSpan, propertiesHref, commentsTxt, saveAndSubmitBtn;
    private static String sPathToModuleFile, sFileModuleJson, sResumeFile;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String firstNameInput, address1Input, address2Input, lastNameInput, companyInput, cityInput,  phoneInput;
    private final String  faxInput, heardFromSelect, provinceInput, postalCodeInput,countrySelect, emailInput, titleInput;
    private final String notesTextArea, captchaImage, captchaCodeInput, mailingListTable, mailinglistTableOld, regionInput;
    private final String firstMailingListChkBox, submitBtn, validationSummary, submittedMessage, submittedMessageOld, subscribeBtn;

    public MailingListSignUp(WebDriver driver) {
        super(driver);

        workflowStateSpan = By.xpath(propUIPageAdmin.getProperty("select_WorkflowState"));
        commentsTxt = By.xpath(propUIPageAdmin.getProperty("txtarea_Comments"));
        propertiesHref = By.xpath(propUIModules.getProperty("href_Properties"));
        saveAndSubmitBtn = By.xpath(propUIPageAdmin.getProperty("btn_SaveAndSubmit"));

        sPathToModuleFile = System.getProperty("user.dir") + propUIModulesMail.getProperty("dataPath_Mail");
        sFileModuleJson = propUIModulesMail.getProperty("json_MailingListSignUpProp");

        parser = new JSONParser();

        emailInput = propUIModulesMail.getProperty("input_Email");
        firstNameInput = propUIModulesMail.getProperty("input_FirstName");
        lastNameInput = propUIModulesMail.getProperty("input_LastName");
        companyInput = propUIModulesMail.getProperty("input_Company");
        titleInput = propUIModulesMail.getProperty("input_Title");
        address1Input = propUIModulesMail.getProperty("input_Address1");
        address2Input = propUIModulesMail.getProperty("input_Address2");
        cityInput = propUIModulesMail.getProperty("input_City");
        provinceInput = propUIModulesMail.getProperty("input_Province");
        postalCodeInput = propUIModulesMail.getProperty("input_PostalCode");
        countrySelect = propUIModulesMail.getProperty("select_Country");
        regionInput = propUIModulesMail.getProperty("input_Region");
        phoneInput = propUIModulesMail.getProperty("input_Phone");
        faxInput = propUIModulesMail.getProperty("input_Fax");
        heardFromSelect = propUIModulesMail.getProperty("select_HeardFrom");
        notesTextArea = propUIModulesMail.getProperty("textarea_Notes");
        captchaImage = propUIModulesMail.getProperty("img_Captcha");
        captchaCodeInput = propUIModulesMail.getProperty("input_CaptchaCode");
        mailingListTable = propUIModulesMail.getProperty("table_MailingList");
        mailinglistTableOld = propUIModulesMail.getProperty("table_MailingListOld");
        firstMailingListChkBox = propUIModulesMail.getProperty("input_FirstMailingListChkBox");

        submitBtn = propUIModulesMail.getProperty("input_Submit");
        subscribeBtn = propUIModulesMail.getProperty("input_Subscribe");
        validationSummary = propUIModulesMail.getProperty("div_ValidationSummary");
        submittedMessage = propUIModulesMail.getProperty("div_ConfirmationMessage");
        submittedMessageOld = propUIModulesMail.getProperty("div_MailingListConfirmation");
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

    public Boolean checkEmail(String user, String password, String subjectID) throws InterruptedException, IOException, MessagingException {
        System.out.println("Checking email: " + subjectID);
        for(int i =1;i <=5; i++){
            if (getSpecificMail(user, password, subjectID) == null){
                System.out.println("Email was not found, attempt # "+ i);
            } else{
                System.out.println("Email found");
                return true;
            }
        }
        return false;
    }

    public void enterFields(JSONObject module) {
        if (module.get("module_title").toString().contains("Mail/MailingListSignUp")) {
            findElement(By.xpath(module.get("module_path") + emailInput)).sendKeys(module.get("email").toString());
            findElement(By.xpath(module.get("module_path") + firstMailingListChkBox)).click();
        } else if (module.get("module_title").toString().contains("Mail/3_5/MailingListSignUp")){
            findElement(By.xpath(module.get("module_path") + emailInput)).sendKeys(module.get("email").toString());
            findElement(By.xpath(module.get("module_path") + firstNameInput)).sendKeys(module.get("first_name").toString());
            findElement(By.xpath(module.get("module_path") + lastNameInput)).sendKeys(module.get("last_name").toString());
            findElement(By.xpath(module.get("module_path") + companyInput)).sendKeys(module.get("company").toString());
            findElement(By.xpath(module.get("module_path") + titleInput)).sendKeys(module.get("title").toString());
            findElement(By.xpath(module.get("module_path") + address1Input)).sendKeys(module.get("address1").toString());
            findElement(By.xpath(module.get("module_path") + address2Input)).sendKeys(module.get("address2").toString());
            findElement(By.xpath(module.get("module_path") + cityInput)).sendKeys(module.get("city").toString());
            findElement(By.xpath(module.get("module_path") + provinceInput)).sendKeys(module.get("province").toString());
            findElement(By.xpath(module.get("module_path") + postalCodeInput)).sendKeys(module.get("postal_code").toString());
            findElement(By.xpath(module.get("module_path") + countrySelect)).sendKeys(module.get("country").toString());
            findElement(By.xpath(module.get("module_path") + regionInput)).sendKeys(module.get("region").toString());
            findElement(By.xpath(module.get("module_path") + phoneInput)).sendKeys(module.get("phone").toString());
            findElement(By.xpath(module.get("module_path") + faxInput)).sendKeys(module.get("fax").toString());
            findElement(By.xpath(module.get("module_path") + heardFromSelect)).sendKeys(module.get("heard_from").toString());
            findElement(By.xpath(module.get("module_path") + notesTextArea)).sendKeys(module.get("notes").toString());
        } else{
            findElement(By.xpath(module.get("module_path") + emailInput)).sendKeys(module.get("email").toString());
            findElement(By.xpath(module.get("module_path") + firstNameInput)).sendKeys(module.get("first_name").toString());
            findElement(By.xpath(module.get("module_path") + lastNameInput)).sendKeys(module.get("last_name").toString());
            findElement(By.xpath(module.get("module_path") + companyInput)).sendKeys(module.get("company").toString());
            findElement(By.xpath(module.get("module_path") + titleInput)).sendKeys(module.get("title").toString());
            findElement(By.xpath(module.get("module_path") + address1Input)).sendKeys(module.get("address1").toString());
            findElement(By.xpath(module.get("module_path") + address2Input)).sendKeys(module.get("address2").toString());
            findElement(By.xpath(module.get("module_path") + cityInput)).sendKeys(module.get("city").toString());
            findElement(By.xpath(module.get("module_path") + provinceInput)).sendKeys(module.get("province").toString());
            findElement(By.xpath(module.get("module_path") + postalCodeInput)).sendKeys(module.get("postal_code").toString());
            findElement(By.xpath(module.get("module_path") + regionInput)).sendKeys(module.get("region").toString());
            findElement(By.xpath(module.get("module_path") + phoneInput)).sendKeys(module.get("phone").toString());
            findElement(By.xpath(module.get("module_path") + faxInput)).sendKeys(module.get("fax").toString());
            findElement(By.xpath(module.get("module_path") + heardFromSelect)).sendKeys(module.get("heard_from").toString());
            findElement(By.xpath(module.get("module_path") + notesTextArea)).sendKeys(module.get("notes").toString());

        }
    }

    public void clickSubmit(JSONObject module) {
        System.out.println("Clicking Submit");
        if(module.get("module_title").toString().contains("Mail/MailingListSignUp")){
            scrollToElementAndClick(By.xpath(module.get("module_path") + subscribeBtn));
        } else {
            ((JavascriptExecutor)driver).executeScript("arguments[0].click();", findElement(By.xpath(module.get("module_path") + submitBtn)));
        }
    }

    public boolean getValidationSummary(JSONObject module) {
        System.out.println("Checking validation summary");
        if(module.get("module_title").toString().contains("Mail/MailingListSignUp")){
            waitForElementText(By.xpath(module.get("module_path") + submittedMessageOld));
            if (findElement(By.xpath(module.get("module_path") + submittedMessageOld)).getText().contains(module.get("expected_message").toString()))
                return true;
        } else{
            waitForElementText(By.xpath(module.get("module_path") + validationSummary));
            if (findElement(By.xpath(module.get("module_path") + validationSummary)).getText().contains(module.get("expected_message").toString()))
                return true;
        }
        return false;
    }

    // Submitted message works differently for 3.5 and 4.2.2 because it gets rid of all module content and replace it with the message
    // So the selector can't find them and have to resort to selecting by order
    public boolean getSubmittedMessage(JSONObject module){
        System.out.println("Checking submitted message");
        if(module.get("module_title").toString().contains("Mail/MailingListSignUp")){
            waitForElementText(By.xpath(module.get("module_path") + submittedMessageOld));
            if (findElement(By.xpath(module.get("module_path") + submittedMessageOld)).getText().contains(module.get("expected_message").toString()))
                return true;
        } else if (module.get("module_title").toString().contains("Mail/3_5/MailingListSignUp")){
            waitForElementText(By.xpath("//span[contains(@class,'ContentPaneDiv4')]" + submittedMessage));
            if (findElement(By.xpath("//span[contains(@class,'ContentPaneDiv4')]" + submittedMessage)).getText().contains(module.get("expected_message").toString()))
                return true;
        } else if (module.get("module_title").toString().contains("Mail/4_2_2/MailingListSignUp")){
        waitForElementText(By.xpath("//span[contains(@class,'ContentPaneDiv6')]" + submittedMessage));
        if (findElement(By.xpath("//span[contains(@class,'ContentPaneDiv6')]" + submittedMessage)).getText().contains(module.get("expected_message").toString()))
            return true;
    }
        return false;
    }
}
