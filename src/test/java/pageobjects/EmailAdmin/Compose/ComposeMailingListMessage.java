package pageobjects.EmailAdmin.Compose;

import com.google.api.client.json.Json;
import com.sun.mail.gimap.GmailFolder;
import com.sun.mail.gimap.GmailRawSearchTerm;
import com.sun.mail.gimap.GmailStore;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

import javax.mail.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import static specs.AbstractSpec.propUIContentAdmin;
import static specs.AbstractSpec.propUIEmailAdmin;

/**
 * Created by charleszheng on 2017-10-16.
 */


public class ComposeMailingListMessage extends AbstractPageObject {
    private static By moduleTitle, selectTemplate, selectTo, inputFrom, inputSubject,
            textareaBody, textareaBodyContent, textarea, inputCreatedBy, inputSendTestMessageTo,
            buttonSendTestEmail, buttonSave, buttonSendEmail, buttonCancel, buttonDelete,
            successMsgTestSend, successMsgUpdate, successMsgSend, successMsgDelete,
            spanSentOn;

    private static String sPathToFile, sFileJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String PAGE_NAME="Mailing List Message";


    public ComposeMailingListMessage(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIEmailAdmin.getProperty("spanModule_Title"));
        selectTemplate = By.xpath(propUIEmailAdmin.getProperty("select_Template"));
        selectTo = By.xpath(propUIEmailAdmin.getProperty("select_To"));
        inputFrom = By.xpath(propUIEmailAdmin.getProperty("input_From"));
        inputSubject = By.xpath(propUIEmailAdmin.getProperty("input_Subject"));
        textareaBody = By.xpath(propUIEmailAdmin.getProperty("textarea_Body"));
        textareaBodyContent = By.xpath(propUIEmailAdmin.getProperty("textarea_BodyContent"));
        textarea = By.tagName(propUIEmailAdmin.getProperty("textarea"));
        inputCreatedBy = By.xpath(propUIEmailAdmin.getProperty("input_CreatedBy"));
        inputSendTestMessageTo = By.xpath(propUIEmailAdmin.getProperty("input_SendTestMessageTo"));
        buttonSendTestEmail = By.xpath(propUIEmailAdmin.getProperty("button_SendTestEmail"));
        buttonSave = By.xpath(propUIEmailAdmin.getProperty("button_Save"));
        buttonSendEmail = By.xpath(propUIEmailAdmin.getProperty("button_SendEmail"));
        buttonCancel = By.xpath(propUIEmailAdmin.getProperty("button_Cancel"));
        buttonDelete = By.xpath(propUIEmailAdmin.getProperty("button_Delete"));
        successMsgTestSend = By.xpath(propUIEmailAdmin.getProperty("success_Msg_TestSend"));
        successMsgUpdate = By.xpath(propUIEmailAdmin.getProperty("success_Msg_Update"));
        successMsgSend = By.xpath(propUIEmailAdmin.getProperty("success_Msg_Send"));
        successMsgDelete = By.xpath(propUIEmailAdmin.getProperty("success_Msg_Delete"));
        spanSentOn = By.xpath(propUIEmailAdmin.getProperty("span_SentOn"));

        parser = new JSONParser();

        sPathToFile = System.getProperty("user.dir") + propUIContentAdmin.getProperty("dataPath_ComposeList");
        sFileJson = propUIContentAdmin.getProperty("json_Mailing");


    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }


    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public String saveMail(JSONObject data, String name) {
        String template, to, from, subject, created_by, body_text;

        JSONObject jsonObj = new JSONObject();
        JSONObject jsonMain = new JSONObject();
        waitForElement(buttonSave);
        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            template = data.get("template").toString();
            if (template != null) {
                findElement(selectTemplate).sendKeys(template);
                driver.switchTo().frame(findElement(textareaBody));
                pause(1000L);
                if (findElement(textareaBodyContent).getText() == null) {
                    System.out.println("Body text area should not be empty when template is selected.");
                    return null;
                }
                driver.switchTo().defaultContent();
            }
            jsonObj.put("template", template);

            to = data.get("to").toString();
            findElement(selectTo).sendKeys(to);
            jsonObj.put("to", to);

            from = data.get("from").toString();
            findElement(inputFrom).clear();
            findElement(inputFrom).sendKeys(from);
            jsonObj.put("from",from);

            subject = data.get("subject").toString();
            findElement(inputSubject).clear();
            findElement(inputSubject).sendKeys(subject);
            jsonObj.put("subject",subject);

            body_text = data.get("body_text").toString();
            driver.switchTo().frame(findElement(textareaBody));
            findElement(textareaBodyContent).clear();
            findElement(textareaBodyContent).sendKeys(body_text);
            driver.switchTo().defaultContent();
            pause(1000L);
            jsonObj.put("body_text", body_text);

            created_by = data.get("created_by").toString();
            findElement(inputCreatedBy).sendKeys(created_by);
            jsonObj.put("created_by",created_by);


            findElement(buttonSave).click();
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(successMsgUpdate);


            jsonMain.put(name, jsonObj);

            try {
                FileWriter writeFile = new FileWriter(sPathToFile + sFileJson);
                writeFile.write(jsonMain.toJSONString().replace("\\", ""));
                writeFile.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(name + ": "+PAGE_NAME+" has been created");

            return "PASS";

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String sendTestMail(JSONObject data, String name){
        String  send_test_message_to;
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonMain = new JSONObject();

        try{
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }
            waitForElement(buttonCancel);
            findElement(buttonCancel).click();
            By editBtn = By.xpath("//td[(text()='"+name+"')]/parent::tr/td/input[contains(@id, 'btnEdit')][contains(@id, 'MailingList')]");
            waitForElement(editBtn);
            findElement(editBtn).click();

            send_test_message_to = data.get("send_test_message_to").toString();
            findElement(inputSendTestMessageTo).sendKeys(send_test_message_to);
            jsonObj.put("send_test_message_to",send_test_message_to);
            findElement(buttonSendTestEmail).click();
            waitForElementText(successMsgTestSend);

            try {
                FileWriter writeFile = new FileWriter(sPathToFile + sFileJson);
                writeFile.write(jsonMain.toJSONString().replace("\\", ""));
                writeFile.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "PASS";
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String updateAndSendMail(JSONObject data, String name){
        try{
            waitForElement(buttonCancel);
            findElement(buttonCancel).click();
            By editBtn = By.xpath("//td[(text()='"+name+"')]/parent::tr/td/input[contains(@id, 'btnEdit')][contains(@id, 'MailingList')]");
            waitForElement(editBtn);
            findElement(editBtn).click();

            JSONObject jsonObj = new JSONObject();
            JSONObject jsonMain = new JSONObject();
            waitForElement(buttonSave);
            try {
                try {
                    FileReader readFile = new FileReader(sPathToFile + sFileJson);
                    jsonMain = (JSONObject) parser.parse(readFile);
                } catch (ParseException e) {
                }

                try {
                    if (!data.get("subject_ch").toString().isEmpty()) {
                        findElement(inputSubject).clear();
                        findElement(inputSubject).sendKeys(data.get("subject_ch").toString());
                        jsonObj.put("subject", data.get("subject_ch").toString());
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (!data.get("to_ch").toString().isEmpty()) {
                        findElement(inputSendTestMessageTo).clear();
                        findElement(inputSendTestMessageTo).sendKeys(data.get("to_ch").toString());
                        jsonObj.put("to", data.get("to_ch").toString());
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (!data.get("from_ch").toString().isEmpty()) {
                        findElement(inputFrom).clear();
                        findElement(inputFrom).sendKeys(data.get("from_ch").toString());
                        jsonObj.put("from", data.get("from_ch").toString());
                    }
                } catch (NullPointerException e) {
                }

                findElement(buttonSave).click();
                Thread.sleep(DEFAULT_PAUSE);
                waitForElement(successMsgUpdate);

                jsonMain.put(name, jsonObj);

                try {
                    FileWriter writeFile = new FileWriter(sPathToFile + sFileJson);
                    writeFile.write(jsonMain.toJSONString().replace("\\", ""));
                    writeFile.flush();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println(name + ": "+PAGE_NAME+" has been updated");

            } catch (Exception e) {
                e.printStackTrace();
            }


            findElement(buttonSendEmail).click();
            waitForElementText(successMsgSend);
            return "PASS";
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String deleteMail(JSONObject data, String name){
        waitForElement(buttonCancel);
        findElement(buttonCancel).click();
        By editBtn = By.xpath("//td[(text()='"+name+"')]/parent::tr/td/input[contains(@id, 'btnEdit')][contains(@id, 'MailingList')]");
        if (checkElementExists(editBtn) == null)
            return "DELETE SUCCESSFUL";
        else {
            waitForElement(editBtn);
            findElement(editBtn).click();

            waitForElement(buttonDelete);
            findElement(buttonDelete).click();
            waitForElement(successMsgDelete);
            if (checkElementExists(editBtn) == null)
                return "DELETE SUCCESSFUL";
            else
                return null;
        }
    }

    public String getSentOnInfo(){
        waitForElement(spanSentOn);
        return findElement(spanSentOn).getText();
    }

    public static boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
    }

    public static Message getSpecificComposeMail(String user, String password, String subjectID) throws InterruptedException, IOException {
        //Use this one if the email in question has files

        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "gimap");
        Thread.sleep(10000);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println("Email checked at: " + dateFormat.format(date));

        try {
            Session session = Session.getDefaultInstance(props, null);
            GmailStore store = (GmailStore) session.getStore("gimap");
            store.connect("imap.gmail.com", user, password);
            GmailFolder inbox = (GmailFolder) store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            Message[] Messages = inbox.search(new GmailRawSearchTerm("subject:" + subjectID));

            for (int i = 1; i <= 5; i++) {
                if (Messages != null) {
                    for (int j = 0; j < Messages.length; j++) {
                        return Messages[j];
                    }
                }
                else {
                    System.out.println("attempt #" + i);
                    Messages = inbox.search(new GmailRawSearchTerm("subject:" + subjectID));
                    Thread.sleep(10000);
                }
            }

            inbox.close(true);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }


}
