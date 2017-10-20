package pageobjects.EmailAdmin.Templates;

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

public class TemplateAdd extends AbstractPageObject{
    private static By moduleTitle, selectTestEmailType, inputTemplateName, inputSubject,
            inputFrom, inputTestEmail, textareaBody, textareaBodyContent, textarea,
            buttonSendTestEmail, buttonSave, buttonCancel, buttonDelete,
            successMsgTestSend, successMsgDelete, successMsgSave,
            activeCheckbox, hideFooterCheckbox, addNewLink;

    private static String sPathToFile, sFileJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String PAGE_NAME="Template";

    public TemplateAdd(WebDriver driver){
        super(driver);
        moduleTitle = By.xpath(propUIEmailAdmin.getProperty("spanModule_Title"));
        textareaBody = By.xpath(propUIEmailAdmin.getProperty("textarea_Body"));
        textareaBodyContent = By.xpath(propUIEmailAdmin.getProperty("textarea_BodyContent"));
        textarea = By.tagName(propUIEmailAdmin.getProperty("textarea"));
        selectTestEmailType = By.xpath(propUIEmailAdmin.getProperty("select_TestEmailType"));
        inputTemplateName = By.xpath(propUIEmailAdmin.getProperty("input_TemplateName"));
        inputSubject = By.xpath(propUIEmailAdmin.getProperty("input_Subject"));
        inputFrom = By.xpath(propUIEmailAdmin.getProperty("input_From"));
        inputTestEmail = By.xpath(propUIEmailAdmin.getProperty("input_TestEmail"));
        buttonSendTestEmail = By.xpath(propUIEmailAdmin.getProperty("btn_SendTestEmail"));
        buttonSave = By.xpath(propUIContentAdmin.getProperty("btn_Save"));
        buttonCancel = By.xpath(propUIEmailAdmin.getProperty("button_Cancel"));
        buttonDelete = By.xpath(propUIEmailAdmin.getProperty("button_Delete"));
        successMsgDelete = By.xpath(propUIEmailAdmin.getProperty("success_Msg_DeleteTemplate"));
        successMsgSave = By.xpath(propUIEmailAdmin.getProperty("success_Msg_Save"));
        successMsgTestSend = By.xpath(propUIEmailAdmin.getProperty("success_Msg_TestSendEmail"));
        activeCheckbox = By.xpath(propUIContentAdmin.getProperty("chk_Active"));
        hideFooterCheckbox = By.xpath(propUIEmailAdmin.getProperty("chk_HideFooter"));
        addNewLink = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));

        parser = new JSONParser();

        sPathToFile = System.getProperty("user.dir") + propUIEmailAdmin.getProperty("dataPath_Template");
        sFileJson = propUIEmailAdmin.getProperty("json_Template");
    }

    public String getTitle() {
        findElement(addNewLink).click();
        waitForElement(moduleTitle);
        String sTitle = getText(moduleTitle);
        findElement(buttonCancel).click();
        return sTitle;
    }

    public String saveTemplate(JSONObject data, String name) {
        String template_name, subject, from, body_text;
        Boolean active, hide_footer;

        JSONObject jsonObj = new JSONObject();
        JSONObject jsonMain = new JSONObject();

        waitForElement(addNewLink);
        findElement(addNewLink).click();
        waitForElement(buttonSave);

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            template_name = data.get("template_name").toString();
            findElement(inputTemplateName).sendKeys(template_name);
            jsonObj.put("template_name", template_name);

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

            // Save Active checkbox
            active = Boolean.parseBoolean(data.get("active").toString());
            jsonObj.put("active", Boolean.parseBoolean(data.get("active").toString()));
            if (active) {
                if (!Boolean.parseBoolean(findElement(activeCheckbox).getAttribute("checked"))) {
                    findElement(activeCheckbox).click();
                } else {
                }
            } else {
                if (!Boolean.parseBoolean(findElement(activeCheckbox).getAttribute("checked"))) {
                } else {
                    findElement(activeCheckbox).click();
                }
            }

            // Save Hide Footer checkbox
            hide_footer = Boolean.parseBoolean(data.get("hide_footer").toString());
            jsonObj.put("hide_footer", Boolean.parseBoolean(data.get("hide_footer").toString()));
            if (hide_footer) {
                if (!Boolean.parseBoolean(findElement(hideFooterCheckbox).getAttribute("checked"))) {
                    findElement(hideFooterCheckbox).click();
                } else {
                }
            } else {
                if (!Boolean.parseBoolean(findElement(hideFooterCheckbox).getAttribute("checked"))) {
                } else {
                    findElement(hideFooterCheckbox).click();
                }
            }

            findElement(buttonSave).click();
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(successMsgSave);


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
        String  test_email, test_email_type;
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonMain = new JSONObject();

        try{
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }
            By editBtn = By.xpath("//td[(text()='" + name + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");
            waitForElement(editBtn);
            findElement(editBtn).click();

            test_email = data.get("test_email").toString();
            findElement(inputTestEmail).clear();
            findElement(inputTestEmail).sendKeys(test_email);
            jsonObj.put("test_email",test_email);

            test_email_type = data.get("test_email_type").toString();
            findElement(selectTestEmailType).sendKeys(test_email_type);
            jsonObj.put("test_email_type",test_email_type);


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

    public String editTemplate(JSONObject data, String name){
        try{
            By editBtn = By.xpath("//td[(text()='" + name + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");
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
                    if (!data.get("template_name_ch").toString().isEmpty()) {
                        findElement(inputTemplateName).clear();
                        findElement(inputTemplateName).sendKeys(data.get("template_name_ch").toString());
                        jsonObj.put("template_name", data.get("template_name_ch").toString());
                    }
                } catch (NullPointerException e) {
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
                    if (!data.get("from_ch").toString().isEmpty()) {
                        findElement(inputFrom).sendKeys(data.get("from_ch").toString());
                        jsonObj.put("from", data.get("from_ch").toString());
                    }
                } catch (NullPointerException e) {
                }

                try {
                    // Edit Active checkbox
                    if (Boolean.parseBoolean(data.get("active_ch").toString())) {
                        if (!Boolean.parseBoolean(findElement(activeCheckbox).getAttribute("checked"))) {
                            findElement(activeCheckbox).click();
                            jsonObj.put("active", true);
                        } else {
                        }
                    } else {
                        if (!Boolean.parseBoolean(findElement(activeCheckbox).getAttribute("checked"))) {
                        } else {
                            findElement(activeCheckbox).click();
                            jsonObj.put("active", false);
                        }
                    }
                } catch (NullPointerException e) {
                }

                try {
                    // Edit Hide Footer checkbox
                    if (Boolean.parseBoolean(data.get("hide_footer_ch").toString())) {
                        if (!Boolean.parseBoolean(findElement(hideFooterCheckbox).getAttribute("checked"))) {
                            findElement(hideFooterCheckbox).click();
                            jsonObj.put("hide_footer", true);
                        } else {
                        }
                    } else {
                        if (!Boolean.parseBoolean(findElement(hideFooterCheckbox).getAttribute("checked"))) {
                        } else {
                            findElement(hideFooterCheckbox).click();
                            jsonObj.put("hide_footer", false);
                        }
                    }
                } catch (NullPointerException e) {
                }

                driver.switchTo().frame(findElement(textareaBody));

                try {
                    if (!data.get("body_text_ch").toString().isEmpty()) {
                        findElement(textareaBodyContent).clear();
                        findElement(textareaBodyContent).sendKeys(data.get("body_text_ch").toString());
                        jsonObj.put("body_text", data.get("body_text_ch").toString());
                    }
                } catch (NullPointerException e) {
                }

                driver.switchTo().defaultContent();
                pause(1000L);

                findElement(buttonSave).click();
                Thread.sleep(DEFAULT_PAUSE);
                waitForElement(successMsgSave);

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

            return "PASS";
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Boolean checkTemplate (JSONObject data, String name){
            By editBtn = By.xpath("//td[(text()='"+ name +"')]/parent::tr/td/input[contains(@id, 'btnEdit')]");
            waitForElement(editBtn);
            findElement(editBtn).click();

            // Compare field values with entry data
            try {
                if (!findElement(inputTemplateName).getAttribute("value").equals(data.get("template_name").toString())) {
                    System.out.println(findElement(inputTemplateName).getAttribute("value"));
                    System.out.println(data.get("template_name").toString());
                    System.out.println("Fails template name");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(inputSubject).getAttribute("value").equals(data.get("subject").toString())) {
                    System.out.println(findElement(inputSubject).getAttribute("value"));
                    System.out.println(data.get("subject").toString());
                    System.out.println("Fails subject");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(inputFrom).getAttribute("value").equals(data.get("from").toString())) {
                    System.out.println(findElement(inputFrom).getAttribute("value"));
                    System.out.println(data.get("from").toString());
                    System.out.println("Fails from");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(activeCheckbox).getAttribute("checked").equals(data.get("active").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(hideFooterCheckbox).getAttribute("checked").equals(data.get("hide_footer").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

       driver.switchTo().frame(findElement(textareaBody));
        try {
            if (!findElement(textareaBodyContent).getAttribute("value").equals(data.get("body_text").toString())) {
                System.out.println(findElement(textareaBodyContent).getAttribute("value"));
                System.out.println(data.get("body_text").toString());
                System.out.println("Fails body text");
                driver.switchTo().defaultContent();
                pause(1000L);
                return false;
            }
        } catch (NullPointerException e) {
        }

        driver.switchTo().defaultContent();
        pause(1000L);
        System.out.println(name+ ": New "+PAGE_NAME+" has been checked");
            return true;
    }

    public Boolean checkTemplateCh (JSONObject data, String name) {
            By editBtn;
                if (data.containsKey("template_name_ch")) {
                    editBtn = By.xpath("//td[(text()='" + data.get("template_name_ch").toString() + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");
                } else {
                    editBtn = By.xpath("//td[(text()='" + name + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");
                }
                waitForElement(editBtn);
                findElement(editBtn).click();

                // Compare field values with entry data
                try {
                    if (!findElement(inputTemplateName).getAttribute("value").equals(data.get("template_name_ch").toString())) {
                        System.out.println(findElement(inputTemplateName).getAttribute("value"));
                        System.out.println(data.get("template_name_ch").toString());
                        System.out.println("Fails template name");
                        return false;
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (!findElement(inputSubject).getAttribute("value").equals(data.get("subject_ch").toString())) {
                        System.out.println(findElement(inputSubject).getAttribute("value"));
                        System.out.println(data.get("subject_ch").toString());
                        System.out.println("Fails subject");
                        return false;
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (!findElement(inputFrom).getAttribute("value").equals(data.get("from_ch").toString())) {
                        System.out.println(findElement(inputFrom).getAttribute("value"));
                        System.out.println(data.get("from_ch").toString());
                        System.out.println("Fails from");
                        return false;
                    }
                } catch (NullPointerException e) {
                }


                try {
                    if (!findElement(activeCheckbox).getAttribute("checked").equals(data.get("active_ch").toString())) {
                        return false;
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (!findElement(hideFooterCheckbox).getAttribute("checked").equals(data.get("hide_footer_ch").toString())) {
                        return false;
                    }
                } catch (NullPointerException e) {
                }

                driver.switchTo().frame(findElement(textareaBody));
                try {
                    if (!findElement(textareaBodyContent).getAttribute("value").equals(data.get("body_text_ch").toString())) {
                        System.out.println(findElement(textareaBodyContent).getAttribute("value"));
                        System.out.println(data.get("body_text_ch").toString());
                        System.out.println("Fails body text");
                        driver.switchTo().defaultContent();
                        pause(1000L);
                        return false;
                    }
                } catch (NullPointerException e) {
                }

                driver.switchTo().defaultContent();
                 pause(1000L);
                System.out.println(name + ": New " + PAGE_NAME + " has been checked");
                return true;
    }

    public String deleteTemplate(JSONObject data, String name){
        By editBtn;
        try {
            if (data.containsKey("template_name_ch")) {
                editBtn = By.xpath("//td[(text()='" + data.get("template_name_ch").toString() + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");
            } else {
                editBtn = By.xpath("//td[(text()='" + name + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");
            }
            waitForElement(editBtn);
            findElement(editBtn).click();
            waitForElement(buttonDelete);
            findElement(buttonDelete).click();
            waitForElement(successMsgDelete);
            if (checkElementExists(editBtn) == null)
                return "DELETE SUCCESSFUL";
        } catch(NullPointerException e){
            e.printStackTrace();
        }
        return null;
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
