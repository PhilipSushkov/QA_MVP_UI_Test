package pageobjects.Modules.Mail;

import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;
import pageobjects.PageAdmin.WorkflowState;
import pageobjects.PageObject;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static specs.AbstractSpec.*;
import static util.Functions.getSpecificMail;

/**
 * Created by andyp on 2017-08-21.
 */
public class MailingListUnsubscribeAction extends AbstractPageObject {
    private static By workflowStateSpan, propertiesHref, commentsTxt, saveAndSubmitBtn;
    private static String sPathToModuleFile, sFileModuleJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final By emailInput, unsubscribeBtn;
    private final String feedbackMessage;

    public MailingListUnsubscribeAction(WebDriver driver) {
        super(driver);

        workflowStateSpan = By.xpath(propUIPageAdmin.getProperty("select_WorkflowState"));
        commentsTxt = By.xpath(propUIPageAdmin.getProperty("txtarea_Comments"));
        propertiesHref = By.xpath(propUIModules.getProperty("href_Properties"));
        saveAndSubmitBtn = By.xpath(propUIPageAdmin.getProperty("btn_SaveAndSubmit"));

        sPathToModuleFile = System.getProperty("user.dir") + propUIModulesMail.getProperty("dataPath_Mail");
        sFileModuleJson = propUIModulesMail.getProperty("json_MailingListUnsubscribeActionProp");

        parser = new JSONParser();

        emailInput = By.xpath(propUIModulesMail.getProperty("input_Email"));

        unsubscribeBtn = By.xpath(propUIModulesMail.getProperty("input_UnsubscribeBtn"));
        feedbackMessage = propUIModulesMail.getProperty("span_Feedback");
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


    public String unsubscribeFromMailingList(JSONObject module) {
        System.out.println("Unsubscribe from mailing list");
        findElement(emailInput).sendKeys(module.get("email").toString());
        findElement(unsubscribeBtn).click();
        waitForElementText(By.xpath(module.get("module_path").toString() + feedbackMessage));

        return findElement(By.xpath(module.get("module_path").toString() + feedbackMessage)).getText();
    }

    public String getUnsubscriptionURL(String emailContent){
        Document emailHTML = Jsoup.parse(emailContent);

        return emailHTML.getElementsByTag("a").attr("href");
    }

    public String getUnsubscriptionMessage(JSONObject module){
        waitForElement(By.xpath(module.get("module_path").toString() + feedbackMessage));
        return findElement(By.xpath(module.get("module_path").toString() + feedbackMessage)).getText();
    }

}
