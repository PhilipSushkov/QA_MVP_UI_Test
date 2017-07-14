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
import static specs.AbstractSpec.propUIModules;
import static specs.AbstractSpec.propUIContentAdmin;

/**
 * Created by zacharyk on 2017-07-07.
 */
public class CreateGlossary extends AbstractPageObject {
    private static By addNewLink, titleInput, descriptionInput, commentsInput;
    private static By saveButton, saveAndSubmitButton, publishBtn, deleteBtn, workflowStateSpan, currentContentSpan;

    private static String sPathToFile, sFilePageJson;

    private static JSONParser parser;

    private static final long DEFAULT_PAUSE = 2500;
    private final String CONTENT_TYPE = "glossary";

    public CreateGlossary(WebDriver driver) {
        super(driver);

        addNewLink = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));
        titleInput = By.xpath(propUIContentAdmin.getProperty("input_GlossaryTitle"));
        descriptionInput = By.xpath(propUIContentAdmin.getProperty("input_GlossaryDescription"));

        commentsInput = By.xpath(propUIContentAdmin.getProperty("txtarea_Comments"));
        saveButton = By.xpath(propUIContentAdmin.getProperty("btn_Save"));
        saveAndSubmitButton = By.xpath(propUIContentAdmin.getProperty("btn_SaveAndSubmit"));
        publishBtn = By.xpath(propUIContentAdmin.getProperty("btn_Publish"));
        deleteBtn = By.xpath(propUIContentAdmin.getProperty("btn_Delete"));
        workflowStateSpan = By.xpath(propUIContentAdmin.getProperty("select_WorkflowState"));
        currentContentSpan = By.xpath(propUIContentAdmin.getProperty("span_CurrentContent"));

        sPathToFile = System.getProperty("user.dir") + propUIModules.getProperty("dataPath_Content");
        sFilePageJson = propUIModules.getProperty("json_ContentProp");

        parser = new JSONParser();
    }

    public String saveGlossary(JSONObject data) {

        findElement(addNewLink).click();

        waitForElement(saveAndSubmitButton);

        findElement(titleInput).sendKeys(data.get("glossary_title").toString());
        findElement(descriptionInput).sendKeys(data.get("glossary_text").toString());

        findElement(saveButton).click();

        // Write page parameters to json

        try {
            Thread.sleep(DEFAULT_PAUSE);

            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            JSONObject glossariesObj = (JSONObject) jsonObj.get(CONTENT_TYPE);

            if (glossariesObj == null) {
                glossariesObj = new JSONObject();
            }

            JSONObject glossary = new JSONObject();
            glossary.put("workflow_state", WorkflowState.IN_PROGRESS.state());
            URL pageURL = new URL(getUrl());
            String[] params = pageURL.getQuery().split("&");
            JSONObject jsonURLQuery = new JSONObject();
            for (String param:params) {
                jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);
            }
            glossary.put("url_query", jsonURLQuery);
            glossariesObj.put(data.get("glossary_title").toString(), glossary);
            jsonObj.put(CONTENT_TYPE, glossariesObj);

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

    public String saveAndSubmitGlossary(JSONObject data) {
        try {

            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            String glossaryUrl = getContentUrl(jsonObj, CONTENT_TYPE, data.get("glossary_title").toString());

            driver.get(glossaryUrl);
            waitForElement(saveAndSubmitButton);

            findElement(commentsInput).sendKeys(data.get("comment").toString());
            findElement(saveAndSubmitButton).click();
            Thread.sleep(DEFAULT_PAUSE);

            driver.get(glossaryUrl);
            waitForElement(workflowStateSpan);

            JSONObject glossariesObj = (JSONObject) jsonObj.get(CONTENT_TYPE);
            JSONObject glossary = (JSONObject) glossariesObj.get(data.get("glossary_title").toString());

            glossary.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            glossary.put("deleted", "false");

            glossariesObj.put(data.get("glossary_title").toString(), glossary);
            jsonObj.put(CONTENT_TYPE, glossariesObj);

            FileWriter file = new FileWriter(sPathToFile + sFilePageJson);
            file.write(jsonObj.toJSONString().replace("\\", ""));
            file.flush();

            System.out.println("New "+CONTENT_TYPE+" has been submitted: " + data.get("glossary_title").toString());
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

    public String publishGlossary(String glossaryTitle) {
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            String glossaryUrl = getContentUrl(jsonObj, CONTENT_TYPE, glossaryTitle);

            driver.get(glossaryUrl);
            waitForElement(publishBtn);
            findElement(publishBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            driver.get(glossaryUrl);
            waitForElement(workflowStateSpan);

            JSONObject glossariesObj = (JSONObject) jsonObj.get(CONTENT_TYPE);
            JSONObject glossary = (JSONObject) glossariesObj.get(glossaryTitle);

            glossary.put("workflow_state", WorkflowState.LIVE.state());
            glossary.put("deleted", "false");

            glossariesObj.put(glossaryTitle, glossary);
            jsonObj.put(CONTENT_TYPE, glossariesObj);

            FileWriter file = new FileWriter(sPathToFile + sFilePageJson);
            file.write(jsonObj.toJSONString().replace("\\", ""));
            file.flush();

            Thread.sleep(DEFAULT_PAUSE*2);
            driver.navigate().refresh();

            System.out.println("New "+CONTENT_TYPE+" has been published: " + glossaryTitle);
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

    public String setupAsDeletedGlossary(String glossaryTitle) {
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            String glossaryUrl = getContentUrl(jsonObj, CONTENT_TYPE, glossaryTitle);

            driver.get(glossaryUrl);
            waitForElement(commentsInput);
            findElement(commentsInput).sendKeys("Removing the "+CONTENT_TYPE);

            findElement(deleteBtn).click();
            Thread.sleep(DEFAULT_PAUSE);
            driver.get(glossaryUrl);

            JSONObject glossariesObj = (JSONObject) jsonObj.get(CONTENT_TYPE);
            JSONObject glossary = (JSONObject) glossariesObj.get(glossaryTitle);

            glossary.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            glossary.put("deleted", "true");

            glossariesObj.put(glossaryTitle, glossary);
            jsonObj.put(CONTENT_TYPE, glossariesObj);

            FileWriter file = new FileWriter(sPathToFile + sFilePageJson);
            file.write(jsonObj.toJSONString().replace("\\", ""));
            file.flush();

            System.out.println(CONTENT_TYPE+" has been set up as deleted: " + glossaryTitle);
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

    public String removeGlossary(String glossaryTitle) {
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            String glossaryUrl = getContentUrl(jsonObj, CONTENT_TYPE, glossaryTitle);

            driver.get(glossaryUrl);
            waitForElement(commentsInput);

            if (findElement(currentContentSpan).getText().equals(WorkflowState.DELETE_PENDING.state())) {

                findElement(commentsInput).sendKeys("Approving the " + CONTENT_TYPE + " removal");
                findElement(publishBtn).click();

                JSONObject glossariesObj = (JSONObject) jsonObj.get(CONTENT_TYPE);
                glossariesObj.remove(glossaryTitle);
                jsonObj.put(CONTENT_TYPE, glossariesObj);

                FileWriter file = new FileWriter(sPathToFile + sFilePageJson);
                file.write(jsonObj.toJSONString().replace("\\", ""));
                file.flush();

                Thread.sleep(DEFAULT_PAUSE * 2);
                driver.get(glossaryUrl);

                System.out.println(glossaryTitle + ": " + CONTENT_TYPE + " has been removed");
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
