package pageobjects.Modules.Content;

import com.jayway.jsonpath.JsonPath;
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

import static specs.AbstractSpec.desktopUrl;
import static specs.AbstractSpec.propUIContentAdmin;
import static specs.AbstractSpec.propUIModules;

/**
 * Created by zacharyk on 2017-06-27.
 */
public class CreateEvent extends AbstractPageObject {
    private static By startDateInput, endDateInput, startTimeHHSelect, startTimeMMSelect, startTimeAMSelect;
    private static By endTimeHHSelect, endTimeMMSelect, endTimeAMSelect, tagsInput, titleInput, textArea, commentsInput;
    private static By switchToHtml, saveButton, saveAndSubmitButton, publishBtn, deleteBtn, workflowStateSpan, currentContentSpan, yourPageUrl;

    private static String sPathToFile, sFilePageJson;

    private static JSONParser parser;

    private static final long DEFAULT_PAUSE = 2500;
    private final String CONTENT_TYPE = "event";

    public CreateEvent(WebDriver driver) {
        super(driver);

        startDateInput = By.xpath(propUIContentAdmin.getProperty("input_StartDate"));
        startTimeHHSelect = By.xpath(propUIContentAdmin.getProperty("select_StartTimeHH"));
        startTimeMMSelect = By.xpath(propUIContentAdmin.getProperty("select_StartTimeMM"));
        startTimeAMSelect = By.xpath(propUIContentAdmin.getProperty("select_StartTimeAM"));
        endDateInput = By.xpath(propUIContentAdmin.getProperty("input_EndDate"));
        endTimeHHSelect = By.xpath(propUIContentAdmin.getProperty("select_EndTimeHH"));
        endTimeMMSelect = By.xpath(propUIContentAdmin.getProperty("select_EndTimeMM"));
        endTimeAMSelect = By.xpath(propUIContentAdmin.getProperty("select_EndTimeAM"));
        tagsInput = By.xpath(propUIContentAdmin.getProperty("input_Tags"));
        textArea = By.tagName(propUIContentAdmin.getProperty("frame_Textarea"));
        switchToHtml = By.className(propUIContentAdmin.getProperty("html_SwitchTo"));

        commentsInput = By.xpath(propUIContentAdmin.getProperty("txtarea_UpdateComments"));
        saveButton = By.xpath(propUIContentAdmin.getProperty("btn_Save"));
        saveAndSubmitButton = By.xpath(propUIContentAdmin.getProperty("btn_SaveAndSubmit"));
        publishBtn = By.xpath(propUIContentAdmin.getProperty("btn_Publish"));
        deleteBtn = By.xpath(propUIContentAdmin.getProperty("btn_Delete"));
        workflowStateSpan = By.xpath(propUIContentAdmin.getProperty("span_WorkflowState"));
        currentContentSpan = By.xpath(propUIContentAdmin.getProperty("span_CurrentContent"));
        yourPageUrl = By.xpath(propUIContentAdmin.getProperty("span_YourPageUrl"));

        titleInput = By.xpath(propUIContentAdmin.getProperty("input_Title"));

        sPathToFile = System.getProperty("user.dir") + propUIModules.getProperty("dataPath_Content");
        sFilePageJson = propUIModules.getProperty("json_ContentProp");

        parser = new JSONParser();
    }

    public String saveEvent(JSONObject data) {

        waitForElement(saveAndSubmitButton);

        findElement(startDateInput).sendKeys(data.get("start_date").toString());
        findElement(startTimeHHSelect).sendKeys(data.get("start_hour").toString());
        findElement(startTimeMMSelect).sendKeys(data.get("start_minute").toString());
        findElement(startTimeAMSelect).sendKeys(data.get("start_AMPM").toString());
        findElement(endDateInput).sendKeys(data.get("end_date").toString());
        findElement(endTimeHHSelect).sendKeys(data.get("end_hour").toString());
        findElement(endTimeMMSelect).sendKeys(data.get("end_minute").toString());
        findElement(endTimeAMSelect).sendKeys(data.get("end_AMPM").toString());
        findElement(titleInput).sendKeys(data.get("headline").toString());
        findElement(switchToHtml).click();

        driver.switchTo().frame(2);
        findElement(textArea).sendKeys(data.get("body").toString());
        driver.switchTo().defaultContent();
        pause(1000L);

        findElement(saveButton).click();

        // Write page parameters to json

        try {
            Thread.sleep(DEFAULT_PAUSE);

            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            JSONObject eventsObj = (JSONObject) jsonObj.get(CONTENT_TYPE);

            if (eventsObj == null) {
                eventsObj = new JSONObject();
            }

            JSONObject event = new JSONObject();
            event.put("workflow_state", WorkflowState.IN_PROGRESS.state());
            URL pageURL = new URL(getUrl());
            String[] params = pageURL.getQuery().split("&");
            JSONObject jsonURLQuery = new JSONObject();
            for (String param:params) {
                jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);
            }
            event.put("url_query", jsonURLQuery);
            eventsObj.put(data.get("headline").toString(), event);
            jsonObj.put(CONTENT_TYPE, eventsObj);

            try {
                FileWriter file = new FileWriter(sPathToFile + sFilePageJson);
                file.write(jsonObj.toJSONString().replace("\\", ""));
                file.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return findElement(workflowStateSpan).getText();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public String saveAndSubmitEvent(JSONObject data) {
        String your_page_url;

        try {

            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            String eventUrl = getContentUrl(jsonObj, CONTENT_TYPE, data.get("headline").toString());

            driver.get(eventUrl);
            waitForElement(saveAndSubmitButton);

            findElement(tagsInput).sendKeys(data.get("tags").toString());
            findElement(commentsInput).sendKeys(data.get("comment").toString());
            findElement(saveAndSubmitButton).click();
            Thread.sleep(DEFAULT_PAUSE);

            driver.get(eventUrl);
            waitForElement(workflowStateSpan);

            JSONObject eventsObj = (JSONObject) jsonObj.get(CONTENT_TYPE);
            JSONObject event = (JSONObject) eventsObj.get(data.get("headline").toString());

            your_page_url = findElement(yourPageUrl).getText().trim();

            event.put("your_page_url", your_page_url);
            event.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            event.put("deleted", "false");

            eventsObj.put(data.get("headline").toString(), event);
            jsonObj.put(CONTENT_TYPE, eventsObj);

            FileWriter file = new FileWriter(sPathToFile + sFilePageJson);
            file.write(jsonObj.toJSONString().replace("\\", ""));
            file.flush();

            System.out.println("New "+CONTENT_TYPE+" has been submitted: " + data.get("headline").toString());
            return  findElement(workflowStateSpan).getText();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String publishEvent(String headline) {
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            String eventUrl = getContentUrl(jsonObj, CONTENT_TYPE, headline);

            driver.get(eventUrl);
            waitForElement(publishBtn);
            findElement(publishBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            driver.get(eventUrl);
            waitForElement(workflowStateSpan);

            JSONObject eventsObj = (JSONObject) jsonObj.get(CONTENT_TYPE);
            JSONObject event = (JSONObject) eventsObj.get(headline);

            event.put("workflow_state", WorkflowState.LIVE.state());
            event.put("deleted", "false");

            eventsObj.put(headline, event);
            jsonObj.put(CONTENT_TYPE, eventsObj);

            FileWriter file = new FileWriter(sPathToFile + sFilePageJson);
            file.write(jsonObj.toJSONString().replace("\\", ""));
            file.flush();

            Thread.sleep(DEFAULT_PAUSE*2);
            driver.navigate().refresh();

            System.out.println("New "+CONTENT_TYPE+" has been published: " + headline);
            return  findElement(workflowStateSpan).getText();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;

    }

    public String setupAsDeletedEvent(String headline) {
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            String eventUrl = getContentUrl(jsonObj, CONTENT_TYPE, headline);

            driver.get(eventUrl);
            waitForElement(commentsInput);
            findElement(commentsInput).sendKeys("Removing the "+CONTENT_TYPE);

            findElement(deleteBtn).click();
            Thread.sleep(DEFAULT_PAUSE);
            driver.get(eventUrl);

            JSONObject eventsObj = (JSONObject) jsonObj.get(CONTENT_TYPE);
            JSONObject event = (JSONObject) eventsObj.get(headline);

            event.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            event.put("deleted", "true");

            eventsObj.put(headline, event);
            jsonObj.put(CONTENT_TYPE, eventsObj);

            FileWriter file = new FileWriter(sPathToFile + sFilePageJson);
            file.write(jsonObj.toJSONString().replace("\\", ""));
            file.flush();

            System.out.println(CONTENT_TYPE+" has been set up as deleted: " + headline);
            return  findElement(currentContentSpan).getText();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String removeEvent(String headline) {
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            String eventUrl = getContentUrl(jsonObj, CONTENT_TYPE, headline);

            driver.get(eventUrl);
            waitForElement(commentsInput);

            if (findElement(currentContentSpan).getText().equals(WorkflowState.DELETE_PENDING.state())) {

                findElement(commentsInput).sendKeys("Approving the " + CONTENT_TYPE + " removal");
                findElement(publishBtn).click();

                JSONObject eventsObj = (JSONObject) jsonObj.get(CONTENT_TYPE);
                eventsObj.remove(headline);
                jsonObj.put(CONTENT_TYPE, eventsObj);

                FileWriter file = new FileWriter(sPathToFile + sFilePageJson);
                file.write(jsonObj.toJSONString().replace("\\", ""));
                file.flush();

                Thread.sleep(DEFAULT_PAUSE * 2);
                driver.get(eventUrl);

                System.out.println(headline + ": " + CONTENT_TYPE + " has been removed");
                return findElement(workflowStateSpan).getText();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String getContentUrl(JSONObject obj,String type, String contentName) {
        String  sItemID = JsonPath.read(obj, "$.['"+type+"'].['"+contentName+"'].url_query.ItemID");
        String  sLanguageId = JsonPath.read(obj, "$.['"+type+"'].['"+contentName+"'].url_query.LangugageId");
        String  sSectionId = JsonPath.read(obj, "$.['"+type+"'].['"+contentName+"'].url_query.SectionID");
        return desktopUrl.toString()+"default.aspx?ItemID="+sItemID+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }
}
