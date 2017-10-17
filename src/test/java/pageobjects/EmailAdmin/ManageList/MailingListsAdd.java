package pageobjects.EmailAdmin.ManageList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;
import pageobjects.PageAdmin.WorkflowState;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import static specs.AbstractSpec.propUIContentAdmin;
import static specs.AbstractSpec.propUIEmailAdmin;
/*
public class MailingListsAdd extends AbstractPageObject {
    private static By moduleTitle, mailingListNameInput, descriptionTextarea, activationEmailSelect;
    private static By unsubscribeEmailSelect, activeCheckbox, publicYesCheckbox, publicNoCheckbox, saveButton,
                    cancelBtn, addNewLink;

    private static String sPathToFile, sFileJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String PAGE_NAME="MailingList";

    public MailingListsAdd(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIEmailAdmin.getProperty("spanModule_Title"));

        mailingListNameInput = By.xpath(propUIEmailAdmin.getProperty("input_MailingListName"));
        descriptionTextarea = By.xpath(propUIEmailAdmin.getProperty("txtarea_Description"));
        activationEmailSelect = By.xpath(propUIEmailAdmin.getProperty("select_ActivationEmail"));
        unsubscribeEmailSelect = By.xpath(propUIEmailAdmin.getProperty("select_UnsubscribeEmail"));

        activeCheckbox = By.xpath(propUIEmailAdmin.getProperty("chk_Active"));
        publicYesCheckbox = By.xpath(propUIEmailAdmin.getProperty("chk_PublicYes"));
        publicNoCheckbox = By.xpath(propUIEmailAdmin.getProperty("chk_PublicNo"));
        cancelBtn = By.xpath(propUIContentAdmin.getProperty("btn_Cancel"));
        addNewLink = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));
        saveButton = By.xpath(propUIEmailAdmin.getProperty("button_Save"));
        parser = new JSONParser();

        sPathToFile = System.getProperty("user.dir") + propUIEmailAdmin.getProperty("dataPath_ManageList");
        sFileJson = propUIEmailAdmin.getProperty("json_ManageList");
    }

    public String getTitle() {
        findElement(addNewLink).click();
        waitForElement(moduleTitle);
        String sTitle = getText(moduleTitle);
        findElement(cancelBtn).click();
        return sTitle;
    }

    public String saveMailingLists(JSONObject data, String name) {
        String mailing_list_name, description, activation_email, unsubscribe_email;
        Boolean active;
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonMain = new JSONObject();
        JSONObject  sObj;
        JSONObject  aObj;

        waitForElement(addNewLink);
        findElement(addNewLink).click();
        waitForElement(saveButton);

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            mailing_list_name = data.get("mailing_list_name").toString();
            findElement(mailingListNameInput).clear();
            findElement(mailingListNameInput).sendKeys(mailing_list_name);
            jsonObj.put("mailing_list_name", mailing_list_name);

            description = data.get("description").toString();
            findElement(des).clear();
            findElement(eventEndDate).sendKeys(description);
            jsonObj.put("description", description);

            start_time_hour = data.get("start_time_hour").toString();
            findElement(startTimeHourSelect).sendKeys(start_time_hour);
            jsonObj.put("start_time_hour", start_time_hour);

            start_time_min = data.get("start_time_min").toString();
            findElement(startTimeMinSelect).sendKeys(start_time_min);
            jsonObj.put("start_time_min", start_time_min);

            end_time_hour = data.get("end_time_hour").toString();
            findElement(endTimeHourSelect).sendKeys(end_time_hour);
            jsonObj.put("end_time_hour", end_time_hour);

            end_time_min = data.get("end_time_min").toString();
            findElement(endTimeMinSelect).sendKeys(end_time_min);
            jsonObj.put("end_time_min", end_time_min);

            time_zone = data.get("time_zone").toString();
            findElement(timeZone).sendKeys(time_zone);
            jsonObj.put("time_zone", time_zone);

            event_title= data.get("event_title").toString();
            findElement(eventTitle).clear();
            findElement(eventTitle).sendKeys(event_title);
            jsonObj.put("event_title", event_title);

            tags = data.get("tags").toString();
            findElement(tagsInput).clear();
            findElement(tagsInput).sendKeys(tags);
            jsonObj.put("tags", tags);

            sObj = (JSONObject) data.get("speakers");

            speaker_name = sObj.get("speaker_name").toString();
            findElement(speakerName).clear();
            findElement(speakerName).sendKeys(speaker_name);
            jsonObj.put("speaker_name", speaker_name);

            speaker_position = sObj.get("speaker_position").toString();
            findElement(speakerPosition).clear();
            findElement(speakerPosition).sendKeys(speaker_position);
            jsonObj.put("speaker_position", speaker_position);


            aObj = (JSONObject) data.get("attachments");

            attach_title = aObj.get("attach_title").toString();
            findElement(attachTitle).clear();
            findElement(attachTitle).sendKeys(attach_title);
            jsonObj.put("attach_title", attach_title);

            findElement(attachType).click();

            attach_file = aObj.get("attach_file").toString();
            findElement(attachFile).clear();
            findElement(attachFile).sendKeys(attach_file);
            jsonObj.put("attach_file", attach_file);


            findElement(btn_SpeakerOK).click();
            findElement(btn_AttachOk).click();

            // Save Active checkbox
            active = Boolean.parseBoolean(data.get("active").toString());
            jsonObj.put("active", Boolean.parseBoolean(data.get("active").toString()));
            if (active) {
                if (!Boolean.parseBoolean(findElement(activeChk).getAttribute("checked"))) {
                    findElement(activeChk).click();
                } else {
                }
            } else {
                if (!Boolean.parseBoolean(findElement(activeChk).getAttribute("checked"))) {
                } else {
                    findElement(activeChk).click();
                }
            }

            URL url = new URL(getUrl());
            String[] params = url.getQuery().split("&");
            JSONObject jsonURLQuery = new JSONObject();
            for (String param:params) {
                jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);
            }
            jsonObj.put("url_query", jsonURLQuery);

            findElement(saveBtn).click();
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(successMsg);

            jsonObj.put("workflow_state", WorkflowState.IN_PROGRESS.state());

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
            return findElement(workflowStateSpan).getText();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}





}
*/
