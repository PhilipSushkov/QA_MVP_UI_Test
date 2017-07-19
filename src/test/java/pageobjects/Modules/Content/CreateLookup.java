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
 * Created by zacharyk on 2017-06-28.
 */
public class CreateLookup extends AbstractPageObject {
    private static By addNewLink, typeInput, textInput, valueInput, additionalInfoInput, commentsInput;
    private static By saveButton, saveAndSubmitButton, publishBtn, deleteBtn, workflowStateSpan, currentContentSpan;

    private static String sPathToFile, sFilePageJson;

    private static JSONParser parser;

    private static final long DEFAULT_PAUSE = 2500;
    private final String CONTENT_TYPE = "lookup";

    public CreateLookup(WebDriver driver) {
        super(driver);

        addNewLink = By.xpath(propUISiteAdmin.getProperty("input_AddNew"));
        typeInput = By.xpath(propUISiteAdmin.getProperty("input_LookupType"));
        textInput = By.xpath(propUISiteAdmin.getProperty("input_LookupText"));
        valueInput = By.xpath(propUISiteAdmin.getProperty("input_LookupValue"));
        additionalInfoInput = By.xpath(propUISiteAdmin.getProperty("input_AdditionalInfo"));

        commentsInput = By.xpath(propUISiteAdmin.getProperty("txtarea_Comments"));
        saveButton = By.xpath(propUISiteAdmin.getProperty("btn_Save"));
        saveAndSubmitButton = By.xpath(propUISiteAdmin.getProperty("btn_SaveAndSubmit"));
        publishBtn = By.xpath(propUISiteAdmin.getProperty("btn_Publish"));
        deleteBtn = By.xpath(propUISiteAdmin.getProperty("btn_Delete"));
        workflowStateSpan = By.xpath(propUISiteAdmin.getProperty("select_WorkflowState"));
        currentContentSpan = By.xpath(propUISiteAdmin.getProperty("span_CurrentContent"));

        sPathToFile = System.getProperty("user.dir") + propUIModules.getProperty("dataPath_Content");
        sFilePageJson = propUIModules.getProperty("json_ContentProp");

        parser = new JSONParser();
    }

    public String saveLookup(JSONObject data) {

        findElement(addNewLink).click();

        waitForElement(saveAndSubmitButton);

        findElement(typeInput).sendKeys(data.get("lookup_type").toString());
        findElement(textInput).sendKeys(data.get("lookup_text").toString());
        findElement(valueInput).sendKeys(data.get("lookup_value").toString());
        findElement(additionalInfoInput).sendKeys(data.get("lookup_info").toString());

        findElement(saveButton).click();

        // Write page parameters to json

        try {
            Thread.sleep(DEFAULT_PAUSE);

            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            JSONObject lookupsObj = (JSONObject) jsonObj.get(CONTENT_TYPE);

            if (lookupsObj == null) {
                lookupsObj = new JSONObject();
            }

            JSONObject lookup = new JSONObject();
            lookup.put("workflow_state", WorkflowState.IN_PROGRESS.state());
            URL pageURL = new URL(getUrl());
            String[] params = pageURL.getQuery().split("&");
            JSONObject jsonURLQuery = new JSONObject();
            for (String param:params) {
                jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);
            }
            lookup.put("url_query", jsonURLQuery);
            lookupsObj.put(data.get("lookup_text").toString(), lookup);
            jsonObj.put(CONTENT_TYPE, lookupsObj);

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

    public String saveAndSubmitLookup(JSONObject data) {
        try {

            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            String lookupUrl = getContentUrl(jsonObj, CONTENT_TYPE, data.get("lookup_text").toString());

            driver.get(lookupUrl);
            waitForElement(saveAndSubmitButton);

            findElement(commentsInput).sendKeys(data.get("comment").toString());
            findElement(saveAndSubmitButton).click();
            Thread.sleep(DEFAULT_PAUSE);

            driver.get(lookupUrl);
            waitForElement(workflowStateSpan);

            JSONObject lookupsObj = (JSONObject) jsonObj.get(CONTENT_TYPE);
            JSONObject lookup = (JSONObject) lookupsObj.get(data.get("lookup_text").toString());

            lookup.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            lookup.put("deleted", "false");

            lookupsObj.put(data.get("lookup_text").toString(), lookup);
            jsonObj.put(CONTENT_TYPE, lookupsObj);

            FileWriter file = new FileWriter(sPathToFile + sFilePageJson);
            file.write(jsonObj.toJSONString().replace("\\", ""));
            file.flush();

            System.out.println("New "+CONTENT_TYPE+" has been submitted: " + data.get("lookup_text").toString());
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

    public String publishLookup(String lookupText) {
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            String lookupUrl = getContentUrl(jsonObj, CONTENT_TYPE, lookupText);

            driver.get(lookupUrl);
            waitForElement(publishBtn);
            findElement(publishBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            driver.get(lookupUrl);
            waitForElement(workflowStateSpan);

            JSONObject lookupsObj = (JSONObject) jsonObj.get(CONTENT_TYPE);
            JSONObject lookup = (JSONObject) lookupsObj.get(lookupText);

            lookup.put("workflow_state", WorkflowState.LIVE.state());
            lookup.put("deleted", "false");

            lookupsObj.put(lookupText, lookup);
            jsonObj.put(CONTENT_TYPE, lookupsObj);

            FileWriter file = new FileWriter(sPathToFile + sFilePageJson);
            file.write(jsonObj.toJSONString().replace("\\", ""));
            file.flush();

            Thread.sleep(DEFAULT_PAUSE*2);
            driver.navigate().refresh();

            System.out.println("New "+CONTENT_TYPE+" has been published: " + lookupText);
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

    public String setupAsDeletedLookup(String lookupText) {
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            String lookupUrl = getContentUrl(jsonObj, CONTENT_TYPE, lookupText);

            driver.get(lookupUrl);
            waitForElement(commentsInput);
            findElement(commentsInput).sendKeys("Removing the "+CONTENT_TYPE);

            findElement(deleteBtn).click();
            Thread.sleep(DEFAULT_PAUSE);
            driver.get(lookupUrl);

            JSONObject lookupsObj = (JSONObject) jsonObj.get(CONTENT_TYPE);
            JSONObject lookup = (JSONObject) lookupsObj.get(lookupText);

            lookup.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            lookup.put("deleted", "true");

            lookupsObj.put(lookupText, lookup);
            jsonObj.put(CONTENT_TYPE, lookupsObj);

            FileWriter file = new FileWriter(sPathToFile + sFilePageJson);
            file.write(jsonObj.toJSONString().replace("\\", ""));
            file.flush();

            System.out.println(CONTENT_TYPE+" has been set up as deleted: " + lookupText);
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

    public String removeLookup(String lookupText) {
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            String lookupUrl = getContentUrl(jsonObj, CONTENT_TYPE, lookupText);

            driver.get(lookupUrl);
            waitForElement(commentsInput);

            if (findElement(currentContentSpan).getText().equals(WorkflowState.DELETE_PENDING.state())) {

                findElement(commentsInput).sendKeys("Approving the " + CONTENT_TYPE + " removal");
                findElement(publishBtn).click();

                JSONObject lookupsObj = (JSONObject) jsonObj.get(CONTENT_TYPE);
                lookupsObj.remove(lookupText);
                jsonObj.put(CONTENT_TYPE, lookupsObj);

                FileWriter file = new FileWriter(sPathToFile + sFilePageJson);
                file.write(jsonObj.toJSONString().replace("\\", ""));
                file.flush();

                Thread.sleep(DEFAULT_PAUSE * 2);
                driver.get(lookupUrl);

                System.out.println(lookupText + ": " + CONTENT_TYPE + " has been removed");
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
