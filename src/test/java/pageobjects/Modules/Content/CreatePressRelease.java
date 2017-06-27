package pageobjects.Modules.Content;

import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
public class CreatePressRelease extends AbstractPageObject {
    private static By dateInput, timeHHSelect, timeMMSelect, timeAMSelect, tagsInput, headlineInput, textArea, commentsInput;
    private static By switchToHtml, saveButton, saveAndSubmitButton, publishBtn, deleteBtn, workflowStateSpan, currentContentSpan, yourPageUrl;

    private static String sPathToFile, sFilePageJson;

    private static JSONParser parser;

    private static final long DEFAULT_PAUSE = 2500;
    private final String CONTENT_TYPE = "press_release";

    public CreatePressRelease(WebDriver driver) {
        super(driver);

        dateInput = By.xpath(propUIContentAdmin.getProperty("input_PressReleaseDate"));
        timeHHSelect = By.xpath(propUIContentAdmin.getProperty("select_PressReleaseTimeHH"));
        timeMMSelect = By.xpath(propUIContentAdmin.getProperty("select_PressReleaseTimeMM"));
        timeAMSelect = By.xpath(propUIContentAdmin.getProperty("select_PressReleaseTimeAM"));
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

        headlineInput = By.xpath(propUIContentAdmin.getProperty("input_Headline"));

        sPathToFile = System.getProperty("user.dir") + propUIModules.getProperty("dataPath_Content");
        sFilePageJson = propUIModules.getProperty("json_ContentProp");

        parser = new JSONParser();
    }

    public String savePressRelease(JSONObject data) {

        waitForElement(saveAndSubmitButton);

        findElement(dateInput).sendKeys(data.get("date").toString());
        findElement(timeHHSelect).sendKeys(data.get("hour").toString());
        findElement(timeMMSelect).sendKeys(data.get("minute").toString());
        findElement(timeAMSelect).sendKeys(data.get("AMPM").toString());
        findElement(headlineInput).sendKeys(data.get("headline").toString());
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
            JSONObject pressReleasesObj = (JSONObject) jsonObj.get(CONTENT_TYPE);

            if (pressReleasesObj == null) {
                pressReleasesObj = new JSONObject();
            }

            JSONObject pressRelease = new JSONObject();
            pressRelease.put("workflow_state", WorkflowState.IN_PROGRESS.state());
            URL pageURL = new URL(getUrl());
            String[] params = pageURL.getQuery().split("&");
            JSONObject jsonURLQuery = new JSONObject();
            for (String param:params) {
                jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);
            }
            pressRelease.put("url_query", jsonURLQuery);
            pressReleasesObj.put(data.get("headline").toString(), pressRelease);
            jsonObj.put(CONTENT_TYPE, pressReleasesObj);

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

    public String saveAndSubmitPressRelease(JSONObject data) {
        String your_page_url;

        try {

            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            String pressReleaseUrl = getContentUrl(jsonObj, CONTENT_TYPE, data.get("headline").toString());

            driver.get(pressReleaseUrl);
            waitForElement(saveAndSubmitButton);

            findElement(tagsInput).sendKeys(data.get("tags").toString());
            findElement(commentsInput).sendKeys(data.get("comment").toString());
            findElement(saveAndSubmitButton).click();
            Thread.sleep(DEFAULT_PAUSE);

            driver.get(pressReleaseUrl);
            waitForElement(workflowStateSpan);

            JSONObject pressReleasesObj = (JSONObject) jsonObj.get(CONTENT_TYPE);
            JSONObject pressRelease = (JSONObject) pressReleasesObj.get(data.get("headline").toString());

            your_page_url = findElement(yourPageUrl).getText();

            pressRelease.put("your_page_url", your_page_url);
            pressRelease.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            pressRelease.put("deleted", "false");

            pressReleasesObj.put(data.get("headline").toString(), pressRelease);
            jsonObj.put(CONTENT_TYPE, pressReleasesObj);

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

    public String publishPressRelease(String headline) {
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            String pressReleaseUrl = getContentUrl(jsonObj, CONTENT_TYPE, headline);

            driver.get(pressReleaseUrl);
            waitForElement(publishBtn);
            findElement(publishBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            driver.get(pressReleaseUrl);
            waitForElement(workflowStateSpan);

            JSONObject pressReleasesObj = (JSONObject) jsonObj.get(CONTENT_TYPE);
            JSONObject pressRelease = (JSONObject) pressReleasesObj.get(headline);

            pressRelease.put("workflow_state", WorkflowState.LIVE.state());
            pressRelease.put("deleted", "false");

            pressReleasesObj.put(headline, pressRelease);
            jsonObj.put(CONTENT_TYPE, pressReleasesObj);

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

    public String setupAsDeletedPressRelease(String headline) {
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            String pressReleaseUrl = getContentUrl(jsonObj, CONTENT_TYPE, headline);

            driver.get(pressReleaseUrl);
            waitForElement(commentsInput);
            findElement(commentsInput).sendKeys("Removing the "+CONTENT_TYPE);

            findElement(deleteBtn).click();
            Thread.sleep(DEFAULT_PAUSE);
            driver.get(pressReleaseUrl);

            JSONObject pressReleasesObj = (JSONObject) jsonObj.get(CONTENT_TYPE);
            JSONObject pressRelease = (JSONObject) pressReleasesObj.get(headline);

            pressRelease.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            pressRelease.put("deleted", "true");

            pressReleasesObj.put(headline, pressRelease);
            jsonObj.put(CONTENT_TYPE, pressReleasesObj);

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

    public String removePressRelease(String headline) {
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            String pressReleaseUrl = getContentUrl(jsonObj, CONTENT_TYPE, headline);

            driver.get(pressReleaseUrl);
            waitForElement(commentsInput);

            if (findElement(currentContentSpan).getText().equals(WorkflowState.DELETE_PENDING.state())) {

                findElement(commentsInput).sendKeys("Approving the " + CONTENT_TYPE + " removal");
                findElement(publishBtn).click();

                JSONObject pressReleasesObj = (JSONObject) jsonObj.get(CONTENT_TYPE);
                pressReleasesObj.remove(headline);
                jsonObj.put(CONTENT_TYPE, pressReleasesObj);

                FileWriter file = new FileWriter(sPathToFile + sFilePageJson);
                file.write(jsonObj.toJSONString().replace("\\", ""));
                file.flush();

                Thread.sleep(DEFAULT_PAUSE * 2);
                driver.get(pressReleaseUrl);

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
