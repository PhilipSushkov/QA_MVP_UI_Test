package pageobjects.Modules.Mail;

import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;
import pageobjects.PageAdmin.WorkflowState;
import pageobjects.PageObject;

import javax.mail.MessagingException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static specs.AbstractSpec.*;
import static util.Functions.getSpecificMail;

/**
 * Created by andyp on 2017-08-21.
 */
public class EmailThisPage extends AbstractPageObject {
    private static By workflowStateSpan, propertiesHref, commentsTxt, saveAndSubmitBtn;
    private static String sPathToModuleFile, sFileModuleJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String friendEmailInput, nameInput, emailInput, subjectInput, confirmationMessage, sendEmailButton, validationSummary;

    public EmailThisPage(WebDriver driver) {
        super(driver);

        workflowStateSpan = By.xpath(propUIPageAdmin.getProperty("select_WorkflowState"));
        commentsTxt = By.xpath(propUIPageAdmin.getProperty("txtarea_Comments"));
        propertiesHref = By.xpath(propUIModules.getProperty("href_Properties"));
        saveAndSubmitBtn = By.xpath(propUIPageAdmin.getProperty("btn_SaveAndSubmit"));

        sPathToModuleFile = System.getProperty("user.dir") + propUIModulesMail.getProperty("dataPath_Mail");
        sFileModuleJson = propUIModulesMail.getProperty("json_EmailThisPageProp");

        parser = new JSONParser();

        friendEmailInput = propUIModulesMail.getProperty("input_FriendEmail");
        nameInput = propUIModulesMail.getProperty("input_Name");
        emailInput = propUIModulesMail.getProperty("input_Email");
        subjectInput = propUIModulesMail.getProperty("input_Subject");
        confirmationMessage = propUIModulesMail.getProperty("div_EmailConfirmation");
        sendEmailButton = propUIModulesMail.getProperty("input_Submit");
        validationSummary = propUIModulesMail.getProperty("div_ValidationSummary");

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
        return desktopUrl.toString() + "default.aspx?ItemId=" + sItemID + "&LanguageId=" + sLanguageId + "&SectionId=" + sSectionId;
    }

    public Boolean checkEmail(String user, String password, String subjectID) throws InterruptedException, IOException, MessagingException {
        System.out.println("Checking email");
        for (int i = 1; i <= 5; i++) {
            if (getSpecificMail(user, password, subjectID) == null) {
                System.out.println("Email was not found, attempt # " + i);
            } else {
                System.out.println("Email found");
                return true;
            }
        }
        return false;
    }

    public void sendEmail(JSONObject module) {
        findElement(By.xpath(module.get("module_path").toString() + friendEmailInput)).sendKeys(module.get("friend_email").toString());
        findElement(By.xpath(module.get("module_path").toString() + nameInput)).sendKeys(module.get("name").toString());
        findElement(By.xpath(module.get("module_path").toString() + emailInput)).sendKeys(module.get("email").toString());
        findElement(By.xpath(module.get("module_path").toString() + subjectInput)).sendKeys(module.get("subject").toString());

        findElement(By.xpath(module.get("module_path").toString() + sendEmailButton)).click();
    }

    public Boolean getConfirmationMessage(JSONObject module) {
        System.out.println("Checking confirmation message");
        waitForElement(By.xpath(module.get("module_path").toString() + confirmationMessage));
        return findElement(By.xpath(module.get("module_path").toString() + confirmationMessage)).getText().contains(module.get("expected_message").toString());
    }

    public Boolean getValidationMessage(JSONObject module){
        System.out.println("Checking validation message");
        waitForElement(By.xpath(module.get("module_path").toString() + validationSummary));
        return findElement(By.xpath(module.get("module_path").toString() + validationSummary)).getText().contains(module.get("expected_message").toString());

    }

}


