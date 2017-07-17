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

import static specs.AbstractSpec.*;

/**
 * Created by zacharyk on 2017-07-10.
 */
public class CreateQuickLink extends AbstractPageObject {
    private static By addNewLink, descriptionInput, urlInput, textInput, tagsInput, commentsInput;
    private static By saveButton, saveAndSubmitButton, publishBtn, deleteBtn, workflowStateSpan, currentContentSpan;

    private static String sPathToFile, sFilePageJson;

    private static JSONParser parser;

    private static final long DEFAULT_PAUSE = 2500;
    private final String CONTENT_TYPE = "quicklink";

    public CreateQuickLink(WebDriver driver) {
        super(driver);

        addNewLink = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));
        descriptionInput = By.xpath(propUIContentAdmin.getProperty("input_Description"));
        urlInput = By.xpath(propUIContentAdmin.getProperty("input_Url"));
        textInput = By.xpath(propUIContentAdmin.getProperty("input_Text"));
        tagsInput = By.xpath(propUIContentAdmin.getProperty("input_Tags"));

        commentsInput = By.xpath(propUIContentAdmin.getProperty("txtarea_Comments"));
        saveButton = By.xpath(propUIContentAdmin.getProperty("btn_Save"));
        saveAndSubmitButton = By.xpath(propUIContentAdmin.getProperty("btn_SaveAndSubmit"));
        publishBtn = By.xpath(propUIContentAdmin.getProperty("btn_QuickLinkPublish"));
        deleteBtn = By.xpath(propUIContentAdmin.getProperty("btn_Delete"));
        workflowStateSpan = By.xpath(propUIContentAdmin.getProperty("select_WorkflowState"));
        currentContentSpan = By.xpath(propUIContentAdmin.getProperty("span_CurrentContent"));

        sPathToFile = System.getProperty("user.dir") + propUIModules.getProperty("dataPath_Content");
        sFilePageJson = propUIModules.getProperty("json_ContentProp");

        parser = new JSONParser();
    }

    public String saveQuickLink(JSONObject data) {

        findElement(addNewLink).click();

        waitForElement(saveAndSubmitButton);

        findElement(descriptionInput).sendKeys(data.get("quicklink_description").toString());
        findElement(urlInput).sendKeys(data.get("quicklink_url").toString());
        findElement(textInput).sendKeys(data.get("quicklink_text").toString());
        findElement(tagsInput).sendKeys(data.get("quicklink_tags").toString());

        findElement(saveButton).click();

        // Write page parameters to json

        try {
            Thread.sleep(DEFAULT_PAUSE);

            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            JSONObject quicklinksObj = (JSONObject) jsonObj.get(CONTENT_TYPE);

            if (quicklinksObj == null) {
                quicklinksObj = new JSONObject();
            }

            JSONObject quicklink = new JSONObject();
            quicklink.put("workflow_state", WorkflowState.IN_PROGRESS.state());
            URL pageURL = new URL(getUrl());
            String[] params = pageURL.getQuery().split("&");
            JSONObject jsonURLQuery = new JSONObject();
            for (String param:params) {
                jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);
            }
            quicklink.put("url_query", jsonURLQuery);
            quicklinksObj.put(data.get("quicklink_description").toString(), quicklink);
            jsonObj.put(CONTENT_TYPE, quicklinksObj);

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

    public String saveAndSubmitQuickLink(JSONObject data) {
        try {

            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            String quicklinkUrl = getContentUrl(jsonObj, CONTENT_TYPE, data.get("quicklink_description").toString());

            driver.get(quicklinkUrl);
            waitForElement(saveAndSubmitButton);

            findElement(commentsInput).sendKeys(data.get("comment").toString());
            findElement(saveAndSubmitButton).click();
            Thread.sleep(DEFAULT_PAUSE);

            driver.get(quicklinkUrl);
            waitForElement(workflowStateSpan);

            JSONObject quicklinksObj = (JSONObject) jsonObj.get(CONTENT_TYPE);
            JSONObject quicklink = (JSONObject) quicklinksObj.get(data.get("quicklink_description").toString());

            quicklink.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            quicklink.put("deleted", "false");

            quicklinksObj.put(data.get("quicklink_description").toString(), quicklink);
            jsonObj.put(CONTENT_TYPE, quicklinksObj);

            FileWriter file = new FileWriter(sPathToFile + sFilePageJson);
            file.write(jsonObj.toJSONString().replace("\\", ""));
            file.flush();

            System.out.println("New "+CONTENT_TYPE+" has been submitted: " + data.get("quicklink_description").toString());
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

    public String publishQuickLink(String quicklinkText) {
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            String quicklinkUrl = getContentUrl(jsonObj, CONTENT_TYPE, quicklinkText);

            driver.get(quicklinkUrl);
            waitForElement(publishBtn);
            findElement(publishBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            driver.get(quicklinkUrl);
            waitForElement(workflowStateSpan);

            JSONObject quicklinksObj = (JSONObject) jsonObj.get(CONTENT_TYPE);
            JSONObject quicklink = (JSONObject) quicklinksObj.get(quicklinkText);

            quicklink.put("workflow_state", WorkflowState.LIVE.state());
            quicklink.put("deleted", "false");

            quicklinksObj.put(quicklinkText, quicklink);
            jsonObj.put(CONTENT_TYPE, quicklinksObj);

            FileWriter file = new FileWriter(sPathToFile + sFilePageJson);
            file.write(jsonObj.toJSONString().replace("\\", ""));
            file.flush();

            Thread.sleep(DEFAULT_PAUSE*2);
            driver.navigate().refresh();

            System.out.println("New "+CONTENT_TYPE+" has been published: " + quicklinkText);
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

    public String setupAsDeletedQuickLink(String quicklinkText) {
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            String quicklinkUrl = getContentUrl(jsonObj, CONTENT_TYPE, quicklinkText);

            driver.get(quicklinkUrl);
            waitForElement(commentsInput);
            findElement(commentsInput).sendKeys("Removing the "+CONTENT_TYPE);

            findElement(deleteBtn).click();
            Thread.sleep(DEFAULT_PAUSE);
            driver.get(quicklinkUrl);

            JSONObject quicklinksObj = (JSONObject) jsonObj.get(CONTENT_TYPE);
            JSONObject quicklink = (JSONObject) quicklinksObj.get(quicklinkText);

            quicklink.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            quicklink.put("deleted", "true");

            quicklinksObj.put(quicklinkText, quicklink);
            jsonObj.put(CONTENT_TYPE, quicklinksObj);

            FileWriter file = new FileWriter(sPathToFile + sFilePageJson);
            file.write(jsonObj.toJSONString().replace("\\", ""));
            file.flush();

            System.out.println(CONTENT_TYPE+" has been set up as deleted: " + quicklinkText);
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

    public String removeQuickLink(String quicklinkText) {
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            String quicklinkUrl = getContentUrl(jsonObj, CONTENT_TYPE, quicklinkText);

            driver.get(quicklinkUrl);
            waitForElement(commentsInput);

            if (findElement(currentContentSpan).getText().equals(WorkflowState.DELETE_PENDING.state())) {

                findElement(commentsInput).sendKeys("Approving the " + CONTENT_TYPE + " removal");
                findElement(publishBtn).click();

                JSONObject quicklinksObj = (JSONObject) jsonObj.get(CONTENT_TYPE);
                quicklinksObj.remove(quicklinkText);
                jsonObj.put(CONTENT_TYPE, quicklinksObj);

                FileWriter file = new FileWriter(sPathToFile + sFilePageJson);
                file.write(jsonObj.toJSONString().replace("\\", ""));
                file.flush();

                Thread.sleep(DEFAULT_PAUSE * 2);
                driver.get(quicklinkUrl);

                System.out.println(quicklinkText + ": " + CONTENT_TYPE + " has been removed");
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
        String  sLanguageId = JsonPath.read(obj, "$.['"+type+"'].['"+contentName+"'].url_query.LanguageId");
        String  sSectionId = JsonPath.read(obj, "$.['"+type+"'].['"+contentName+"'].url_query.SectionId");
        return desktopUrl.toString()+"default.aspx?ItemID="+sItemID+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }
}
