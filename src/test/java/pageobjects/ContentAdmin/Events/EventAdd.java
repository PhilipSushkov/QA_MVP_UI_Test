package pageobjects.ContentAdmin.Events;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import pageobjects.AbstractPageObject;

import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pageobjects.PageAdmin.WorkflowState;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static specs.AbstractSpec.desktopUrl;
import static specs.AbstractSpec.propUIContentAdmin;

/**
 * Created by charleszheng on 2017-10-12.
 */

public class EventAdd extends AbstractPageObject{
    private static By moduleTitle, startTimeHourSelect, startTimeMinSelect,endTimeHourSelect, endTimeMinSelect;
    private static By eventStartDate, eventEndDate, tagsInput, publishBtn, eventTitle, timeZone;
    private static By speakerName, speakerPosition, btn_SpeakerOK, speakerNameChk, speakerPositionChk, speakersAddNewBtn;
    private static By attachTitle, attachFile, btn_AttachOk, attachTitleChk, attachFileChk, attachAddNewBtn, attachType;
    private static By currentContentSpan;
    private static By activeChk, saveBtn, revertBtn, cancelBtn, deleteBtn, addNewLink;
    private static By workflowStateSpan, commentsTxt, successMsg, saveAndSubmitBtn;
    private static String sPathToFile, sFileJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String PAGE_NAME="Event", SPEAKERS="speakers", ATTACHMENTS = "attachments";

    public EventAdd(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("spanModule_Title"));
        startTimeHourSelect = By.xpath(propUIContentAdmin.getProperty("select_StartTimeHH"));
        startTimeMinSelect = By.xpath(propUIContentAdmin.getProperty("select_StartTimeMM"));
        endTimeHourSelect = By.xpath(propUIContentAdmin.getProperty("select_EndTimeHH"));
        endTimeMinSelect = By.xpath(propUIContentAdmin.getProperty("select_EndTimeMM"));;
        eventStartDate = By.xpath(propUIContentAdmin.getProperty("input_StartDate"));
        eventEndDate = By.xpath(propUIContentAdmin.getProperty("input_EndDate"));
        eventTitle = By.xpath(propUIContentAdmin.getProperty("input_eventTitle"));
        timeZone = By.xpath(propUIContentAdmin.getProperty("select_StartTimeZone"));
        tagsInput = By.xpath(propUIContentAdmin.getProperty("input_Tags"));
        activeChk = By.xpath(propUIContentAdmin.getProperty("chk_Active"));
        saveBtn = By.xpath(propUIContentAdmin.getProperty("btn_Save"));
        cancelBtn = By.xpath(propUIContentAdmin.getProperty("btn_Cancel"));
        deleteBtn = By.xpath(propUIContentAdmin.getProperty("btn_Delete"));
        publishBtn = By.xpath(propUIContentAdmin.getProperty("btn_Publish"));
        addNewLink = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));
        revertBtn = By.xpath(propUIContentAdmin.getProperty("btn_Revert"));
        workflowStateSpan = By.xpath(propUIContentAdmin.getProperty("select_WorkflowState"));
        commentsTxt = By.xpath(propUIContentAdmin.getProperty("txtarea_Comments"));
        successMsg = By.xpath(propUIContentAdmin.getProperty("msg_Success"));
        saveAndSubmitBtn = By.xpath(propUIContentAdmin.getProperty("btn_SaveAndSubmit"));
        currentContentSpan = By.xpath(propUIContentAdmin.getProperty("span_CurrentContent"));
        speakersAddNewBtn = By.xpath(propUIContentAdmin.getProperty("href_AddNewSpeakers"));
        speakerName = By.xpath(propUIContentAdmin.getProperty("input_SpeakerName")) ;
        speakerPosition = By.xpath(propUIContentAdmin.getProperty("input_SpeakerPosition")) ;
        btn_SpeakerOK = By.xpath(propUIContentAdmin.getProperty("btn_SpeakerOK"));
        speakerNameChk = By.xpath(propUIContentAdmin.getProperty("input_SpeakerName_Chk"));
        speakerPositionChk = By.xpath(propUIContentAdmin.getProperty("input_SpeakerPosition_Chk"));
        attachAddNewBtn = By.xpath(propUIContentAdmin.getProperty("href_AddNewAttachments"));
        attachTitle = By.xpath(propUIContentAdmin.getProperty("input_AttachmentTitle")) ;
        attachFile = By.xpath(propUIContentAdmin.getProperty("input_AttachmentPathOnline")) ;
        btn_AttachOk = By.xpath(propUIContentAdmin.getProperty("btn_AttachmentOK"));
        attachTitleChk = By.xpath(propUIContentAdmin.getProperty("input_AttachmentTitle_Chk"));
        attachFileChk = By.xpath(propUIContentAdmin.getProperty("input_AttachmentFile_Chk"));
        attachType   = By.xpath(propUIContentAdmin.getProperty("input_AttachmentType"));


        parser = new JSONParser();

        sPathToFile = System.getProperty("user.dir") + propUIContentAdmin.getProperty("dataPath_EventList");
        sFileJson = propUIContentAdmin.getProperty("json_Event");
    }

    public String getTitle() {
        findElement(addNewLink).click();
        waitForElement(moduleTitle);
        String sTitle = getText(moduleTitle);
        findElement(cancelBtn).click();
        return sTitle;
    }

    public String saveEvent(JSONObject data, String name) {
        String start_time_hour, start_time_min, end_time_hour, end_time_min, event_start_date, event_end_date, time_zone,
                tags, event_title, attach_title, attach_file,
                speaker_name, speaker_position;

        Boolean active;
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonMain = new JSONObject();
        JSONObject  sObj;
        JSONObject  aObj;

        waitForElement(addNewLink);
        findElement(addNewLink).click();
        waitForElement(saveBtn);
        waitForElement(speakersAddNewBtn);
        findElement(speakersAddNewBtn).click();
        waitForElement(attachAddNewBtn);
        findElement(attachAddNewBtn).click();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            event_start_date = data.get("event_start_date").toString();
            findElement(eventStartDate).clear();
            findElement(eventStartDate).sendKeys(event_start_date);
            jsonObj.put("event_start_date", event_start_date);

            event_end_date = data.get("event_end_date").toString();
            findElement(eventEndDate).clear();
            findElement(eventEndDate).sendKeys(event_end_date);
            jsonObj.put("event_end_date", event_end_date);

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

    public String saveAndSubmitEvent(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        By editBtn = By.xpath("//td[(text()='"+name+"')]/parent::tr/td/input[contains(@id, 'imgEdit')][contains(@id, 'Event')]");

        try {
            waitForElement(moduleTitle);

            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(commentsTxt);

            findElement(commentsTxt).sendKeys(data.get("comment").toString());
            findElement(saveAndSubmitBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(editBtn);
            findElement(editBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            JSONObject jsonObj = (JSONObject) jsonMain.get(name);

            jsonObj.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            jsonObj.put("deleted", "false");

            FileWriter file = new FileWriter(sPathToFile + sFileJson);
            file.write(jsonMain.toJSONString().replace("\\", ""));
            file.flush();

            System.out.println(name+ ": "+PAGE_NAME+" has been sumbitted");
            return findElement(workflowStateSpan).getText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Boolean checkEvent(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonSpeakers = (JSONObject) data.get(SPEAKERS);
        JSONObject jsonAttachments = (JSONObject) data.get(ATTACHMENTS);

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(commentsTxt);

            // Compare field values with entry data
            try {
                if (!new Select(findElement(startTimeHourSelect)).getFirstSelectedOption().getText().trim().equals(data.get("start_time_hour").toString())) {
                    System.out.println(findElement(startTimeHourSelect).getAttribute("value"));
                    System.out.println(data.get("start_time_hour").toString());
                    System.out.println("Fails start time hour");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!new Select(findElement(startTimeMinSelect)).getFirstSelectedOption().getText().trim().equals(data.get("start_time_min").toString())) {
                    System.out.println(findElement(startTimeMinSelect).getAttribute("value"));
                    System.out.println(data.get("start_time_min").toString());
                    System.out.println("Fails start time min");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!new Select(findElement(endTimeHourSelect)).getFirstSelectedOption().getText().trim().equals(data.get("end_time_hour").toString())) {
                    System.out.println(findElement(endTimeHourSelect).getAttribute("value"));
                    System.out.println(data.get("end_time_hour").toString());
                    System.out.println("Fails end time hour");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!new Select(findElement(endTimeMinSelect)).getFirstSelectedOption().getText().trim().equals(data.get("end_time_min").toString())) {
                    System.out.println(findElement(endTimeMinSelect).getAttribute("value"));
                    System.out.println(data.get("end_time_Min").toString());
                    System.out.println("Fails end time min");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(eventStartDate).getAttribute("value").equals(data.get("start_date").toString())) {
                    System.out.println("Fails start date");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(eventEndDate).getAttribute("value").equals(data.get("end_date").toString())) {
                    System.out.println("Fails end date");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(eventTitle).getAttribute("value").equals(data.get("event_title").toString())) {
                    System.out.println("Fails title");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(tagsInput).getAttribute("value").trim().equals(data.get("tags").toString())) {
                    System.out.println(findElement(tagsInput).getAttribute("value"));
                    System.out.println(data.get("tags").toString());
                    System.out.println("Fails tags");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(speakerNameChk).getAttribute("value").trim().equals(jsonSpeakers.get("speaker_name").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(speakerPositionChk).getAttribute("value").trim().equals(jsonSpeakers.get("speaker_position").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(attachTitleChk).getAttribute("value").trim().equals(jsonAttachments.get("attach_title").toString())) {
                    System.out.println(findElement(attachTitleChk).getAttribute("value").trim());
                    System.out.println(jsonAttachments.get("attach_title").toString());
                    System.out.println("Fails attachment title");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(attachFileChk).getAttribute("value").trim().equals(jsonAttachments.get("attach_file").toString())) {
                    System.out.println(findElement(attachFileChk).getAttribute("value").trim());
                    System.out.println(jsonAttachments.get("attach_file").toString());
                    System.out.println("Fails attachment file");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(activeChk).getAttribute("checked").equals(data.get("active").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            System.out.println(name+ ": New "+PAGE_NAME+" has been checked");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String publishEvent(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
                jsonObj = (JSONObject) jsonMain.get(name);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(publishBtn);
            findElement(publishBtn).click();
            Thread.sleep(DEFAULT_PAUSE*2);

            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE*2);
            driver.navigate().refresh();
            Thread.sleep(DEFAULT_PAUSE);

            jsonObj.put("workflow_state", WorkflowState.LIVE.state());
            jsonObj.put("deleted", "false");

            jsonMain.put(name, jsonObj);

            FileWriter file = new FileWriter(sPathToFile + sFileJson);
            file.write(jsonMain.toJSONString().replace("\\", ""));
            file.flush();

            Thread.sleep(DEFAULT_PAUSE*2);
            driver.navigate().refresh();

            System.out.println(jsonObj.get("event_title").toString() + ": New "+PAGE_NAME+" has been published");
            return findElement(workflowStateSpan).getText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String changeAndSubmitEvent(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
                jsonObj = (JSONObject) jsonMain.get(name);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(saveAndSubmitBtn);

            try {
                if (!data.get("event_title_ch").toString().isEmpty()) {
                    findElement(eventTitle).clear();
                    findElement(eventTitle).sendKeys(data.get("event_title_ch").toString());
                    jsonObj.put("event_title", data.get("event_title_ch").toString());
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!data.get("event_start_date_ch").toString().isEmpty()) {
                    findElement(eventStartDate).clear();
                    findElement(eventStartDate).sendKeys(data.get("event_start_date_ch").toString());
                    jsonObj.put("event_start_date", data.get("event_start_date_ch").toString());
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!data.get("event_end_date_ch").toString().isEmpty()) {
                    findElement(eventEndDate).clear();
                    findElement(eventEndDate).sendKeys(data.get("event_end_date_ch").toString());
                    jsonObj.put("event_end_date", data.get("event_end_date_ch").toString());
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!data.get("tags_ch").toString().isEmpty()) {
                    findElement(tagsInput).clear();
                    findElement(tagsInput).sendKeys(data.get("tags_ch").toString());
                    jsonObj.put("tags", data.get("tags_ch").toString());
                }
            } catch (NullPointerException e) {
            }


            jsonObj.put("active", Boolean.parseBoolean(data.get("active").toString()));
            try {
                // Save Active checkbox
                if (Boolean.parseBoolean(data.get("active_ch").toString())) {
                    if (!Boolean.parseBoolean(findElement(activeChk).getAttribute("checked"))) {
                        findElement(activeChk).click();
                        jsonObj.put("active", true);
                    } else {
                    }
                } else {
                    if (!Boolean.parseBoolean(findElement(activeChk).getAttribute("checked"))) {
                    } else {
                        findElement(activeChk).click();
                        jsonObj.put("active", false);
                    }
                }
            } catch (NullPointerException e) {
            }

            try {
                findElement(commentsTxt).clear();
                findElement(commentsTxt).sendKeys(data.get("comment_ch").toString());
            } catch (NullPointerException e) {
            }

            findElement(saveAndSubmitBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            jsonObj.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            jsonObj.put("deleted", "false");

            jsonMain.put(name, jsonObj);

            FileWriter file = new FileWriter(sPathToFile + sFileJson);
            file.write(jsonMain.toJSONString().replace("\\", ""));
            file.flush();

            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(workflowStateSpan);

            System.out.println(jsonObj.get("event_title").toString() + ": New "+PAGE_NAME+" changes have been submitted");
            return findElement(workflowStateSpan).getText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String revertToLiveEvent(String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
                jsonObj = (JSONObject) jsonMain.get(name);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(revertBtn);

            if (jsonObj.get("workflow_state").toString().equals(WorkflowState.FOR_APPROVAL.state())) {
                findElement(revertBtn).click();
                Thread.sleep(DEFAULT_PAUSE);

                jsonObj.put("workflow_state", WorkflowState.LIVE.state());
                jsonObj.put("deleted", "false");

                jsonMain.put(name, jsonObj);

                FileWriter file = new FileWriter(sPathToFile + sFileJson);
                file.write(jsonMain.toJSONString().replace("\\", ""));
                file.flush();

                driver.get(pageUrl);
                Thread.sleep(DEFAULT_PAUSE);
                waitForElement(workflowStateSpan);

                System.out.println(jsonObj.get("event_title").toString()+ ": "+PAGE_NAME+" has been reverted to Live");
                return findElement(workflowStateSpan).getText();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Boolean checkEventCh(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonSpeakers = (JSONObject) data.get(SPEAKERS);
        JSONObject jsonAttachments = (JSONObject) data.get(ATTACHMENTS);

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }
            JSONObject obj = (JSONObject) jsonMain.get(name);

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(commentsTxt);

            // Compare field values with entry data
            try {
                if (!new Select(findElement(startTimeHourSelect)).getFirstSelectedOption().getText().trim().equals(data.get("start_time_hour").toString())) {
                    System.out.println(findElement(startTimeHourSelect).getAttribute("value"));
                    System.out.println(data.get("start_time_hour").toString());
                    System.out.println("Fails start time hour");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!new Select(findElement(startTimeMinSelect)).getFirstSelectedOption().getText().trim().equals(data.get("start_time_min").toString())) {
                    System.out.println(findElement(startTimeMinSelect).getAttribute("value"));
                    System.out.println(data.get("start_time_min").toString());
                    System.out.println("Fails start time min");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!new Select(findElement(endTimeHourSelect)).getFirstSelectedOption().getText().trim().equals(data.get("end_time_hour").toString())) {
                    System.out.println(findElement(endTimeHourSelect).getAttribute("value"));
                    System.out.println(data.get("end_time_hour").toString());
                    System.out.println("Fails end time hour");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!new Select(findElement(endTimeMinSelect)).getFirstSelectedOption().getText().trim().equals(data.get("end_time_min").toString())) {
                    System.out.println(findElement(endTimeMinSelect).getAttribute("value"));
                    System.out.println(data.get("end_time_min").toString());
                    System.out.println("Fails end time min");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(eventStartDate).getAttribute("value").equals(data.get("event_start_date_ch").toString())) {
                    System.out.println("Fails start date");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(eventEndDate).getAttribute("value").equals(data.get("event_end_date_ch").toString())) {
                    System.out.println("Fails end date");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(eventTitle).getAttribute("value").equals(data.get("event_title_ch").toString())) {
                    System.out.println("Fails title");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(tagsInput).getAttribute("value").trim().equals(data.get("tags_ch").toString())) {
                    System.out.println(findElement(tagsInput).getAttribute("value"));
                    System.out.println(data.get("tags").toString());
                    System.out.println("Fails tags");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(speakerNameChk).getAttribute("value").trim().equals(jsonSpeakers.get("speaker_name").toString())) {
                    System.out.println(findElement(speakerNameChk).getAttribute("value"));
                    System.out.println(data.get("speaker_name").toString());
                    System.out.println("Fails speaker name");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(speakerPositionChk).getAttribute("value").trim().equals(jsonSpeakers.get("speaker_position").toString())) {
                    System.out.println(findElement(speakerNameChk).getAttribute("value"));
                    System.out.println(data.get("speaker_position").toString());
                    System.out.println("Fails speaker position");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(attachTitleChk).getAttribute("value").trim().equals(jsonAttachments.get("attach_title").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(attachFileChk).getAttribute("value").trim().equals(jsonAttachments.get("attach_file").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(activeChk).getAttribute("checked").equals(data.get("active_ch").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }


            System.out.println(obj.get("event_title").toString()+ ": New "+PAGE_NAME+" has been checked");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String setupAsDeletedEvent(String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
                jsonObj = (JSONObject) jsonMain.get(name);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(commentsTxt);
            findElement(commentsTxt).sendKeys("Removing "+PAGE_NAME);
            findElement(deleteBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            try {
                Thread.sleep(DEFAULT_PAUSE*2);
                driver.get(pageUrl);
            } catch (UnhandledAlertException e) {
                driver.switchTo().alert().accept();
                Thread.sleep(DEFAULT_PAUSE*2);
                try {
                    driver.get(pageUrl);
                } catch (UnhandledAlertException e2) {
                    driver.switchTo().alert().accept();
                    Thread.sleep(DEFAULT_PAUSE*2);
                    driver.get(pageUrl);
                }
            }

            waitForElement(currentContentSpan);

            jsonObj.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            jsonObj.put("deleted", "true");

            jsonMain.put(name, jsonObj);

            FileWriter file = new FileWriter(sPathToFile + sFileJson);
            file.write(jsonMain.toJSONString().replace("\\", ""));
            file.flush();

            System.out.println(jsonObj.get("event_title").toString()+ ": "+PAGE_NAME+" set up as deleted");
            return findElement(currentContentSpan).getText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String removeEvent(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            if (findElement(currentContentSpan).getText().equals(WorkflowState.DELETE_PENDING.state())) {

                waitForElement(commentsTxt);
                findElement(commentsTxt).sendKeys("Approving "+PAGE_NAME+" removal");
                findElement(publishBtn).click();

                Thread.sleep(DEFAULT_PAUSE*2);

                try {
                    Thread.sleep(DEFAULT_PAUSE*2);
                    driver.get(pageUrl);
                } catch (UnhandledAlertException e) {
                    driver.switchTo().alert().accept();
                    Thread.sleep(DEFAULT_PAUSE*2);
                    try {
                        driver.get(pageUrl);
                    } catch (UnhandledAlertException e2) {
                        driver.switchTo().alert().accept();
                        Thread.sleep(DEFAULT_PAUSE*2);
                        driver.get(pageUrl);
                    }
                }

                jsonMain.remove(name);

                FileWriter file = new FileWriter(sPathToFile + sFileJson);
                file.write(jsonMain.toJSONString().replace("\\", ""));
                file.flush();

                Thread.sleep(DEFAULT_PAUSE*2);
                driver.navigate().refresh();

                System.out.println(": New "+PAGE_NAME+" has been removed");
                return findElement(workflowStateSpan).getText();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Boolean publicEvent(JSONObject data, String name) throws InterruptedException {
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sFileJson));
            JSONObject Obj = (JSONObject) jsonObject.get(name);

            String pageUrl = getPageUrl(jsonObject, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            String publicUrl = findElement(By.xpath("//span[contains(@id,'PageUrl')]")).getText();

            ((JavascriptExecutor)driver).executeScript("window.open();");

            Thread.sleep(DEFAULT_PAUSE);

            ArrayList<String> tabs = new ArrayList<> (driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));


            try {
                driver.get(publicUrl);
            } catch (TimeoutException e) {
                driver.findElement(By.tagName("body")).sendKeys("Keys.ESCAPE");
            }

            if (driver.getTitle().contains(name)) {
                driver.switchTo().window(tabs.get(1)).close();
                driver.switchTo().window(tabs.get(0));

                System.out.println(name + ": New Event Public has checked");
                return true;
            }
            else {
                driver.switchTo().window(tabs.get(1)).close();
                driver.switchTo().window(tabs.get(0));
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
        }

        return null;
    }

    public Boolean publicEventCh(JSONObject data, String name) throws InterruptedException {
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sFileJson));
            JSONObject Obj = (JSONObject) jsonObject.get(name);

            String pageUrl = getPageUrl(jsonObject, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            String publicUrl = findElement(By.xpath("//span[contains(@id,'PageUrl')]")).getText();

            ((JavascriptExecutor)driver).executeScript("window.open();");

            Thread.sleep(DEFAULT_PAUSE);

            ArrayList<String> tabs = new ArrayList<> (driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));


            try {
                driver.get(publicUrl);
            } catch (TimeoutException e) {
                driver.findElement(By.tagName("body")).sendKeys("Keys.ESCAPE");
            }

            if (driver.getTitle().contains(data.get("event_title_ch").toString())){
                driver.switchTo().window(tabs.get(1)).close();
                driver.switchTo().window(tabs.get(0));

                System.out.println(data.get("event_title_ch").toString() + ": New Event Public has checked");
                return true;
            }  else {
                driver.switchTo().window(tabs.get(1)).close();
                driver.switchTo().window(tabs.get(0));
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
        }

        return null;
    }

    public String getPageUrl(JSONObject obj, String name) {
        String sItemID = JsonPath.read(obj, "$.['"+name+"'].url_query.ItemID");
        String sLanguageId = JsonPath.read(obj, "$.['"+name+"'].url_query.LanguageId");
        String sSectionId = JsonPath.read(obj, "$.['"+name+"'].url_query.SectionId");
        return desktopUrl.toString()+"default.aspx?ItemID="+sItemID+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }
}
