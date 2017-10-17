package pageobjects.EmailAdmin.Compose;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static specs.AbstractSpec.propUIContentAdmin;
import static specs.AbstractSpec.propUIEmailAdmin;

/**
 * Created by charleszheng on 2017-10-16.
 */


public class ComposeMailingListMessage extends AbstractPageObject {
    private static By moduleTitle, selectTemplate, selectTo, inputFrom, inputSubject,
            textareaBody, textareaBodyContent, textarea, inputCreatedBy, inputSendTestMessageTo,
            buttonSendTestEmail, buttonSave, buttonSendEmail,
            successMsgTestSend, successMsgUpdate, successMsgSend;

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
        successMsgTestSend = By.xpath(propUIEmailAdmin.getProperty("success_Msg_TestSend"));
        successMsgUpdate = By.xpath(propUIEmailAdmin.getProperty("success_Msg_Update"));
        successMsgSend = By.xpath(propUIEmailAdmin.getProperty("success_Msg_Send"));

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
        String template, to, from, subject, created_by, send_test_message_to, body_text;

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

            send_test_message_to = data.get("send_test_message_to").toString();
            findElement(inputSendTestMessageTo).sendKeys(send_test_message_to);
            jsonObj.put("send_test_message_to",send_test_message_to);

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

    public String sendTestMail(){
        try{
            findElement(buttonSendTestEmail).click();
            waitForElementText(successMsgTestSend);
            return "PASS";
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String updateAndSendMail(JSONObject data, String name){
        try{
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


}
