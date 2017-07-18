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
 * Created by dannyl on 2017-07-18.
 */
public class CreatePerson extends AbstractPageObject {
    private static By saveButton, saveAndSubmitButton, publishBtn, deleteBtn, addNewBtn, workflowStateSpan, commentsInput, currentContentSpan, yourPageUrl;
    private static By firstNameInput, lastNameInput, inputDepartment;
    private static String sPathToFile, sFilePageJson;

    private static JSONParser parser;

    private static final long DEFAULT_PAUSE = 2500;
    private final String CONTENT_TYPE = "person";

    public CreatePerson(WebDriver driver) {
        super(driver);



        commentsInput = By.xpath(propUIContentAdmin.getProperty("txtarea_UpdateComments"));
        saveButton = By.xpath(propUIContentAdmin.getProperty("btn_Save"));
        saveAndSubmitButton = By.xpath(propUIContentAdmin.getProperty("btn_SaveAndSubmit"));
        publishBtn = By.xpath(propUIContentAdmin.getProperty("btn_Publish"));
        deleteBtn = By.xpath(propUIContentAdmin.getProperty("btn_Delete"));
        addNewBtn = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));
        workflowStateSpan = By.xpath(propUIContentAdmin.getProperty("span_WorkflowState"));
        currentContentSpan = By.xpath(propUIContentAdmin.getProperty("span_CurrentContent"));
        yourPageUrl = By.xpath(propUIContentAdmin.getProperty("span_YourPageUrl"));

        firstNameInput = By.xpath(propUIContentAdmin.getProperty("input_FirstName"));
        lastNameInput = By.xpath(propUIContentAdmin.getProperty("input_LastName"));
        inputDepartment = By.xpath(propUIContentAdmin.getProperty("input_Department"));

        sPathToFile = System.getProperty("user.dir") + propUIModules.getProperty("dataPath_Content");
        sFilePageJson = propUIModules.getProperty("json_ContentProp");

        parser = new JSONParser();
    }

    public String savePerson(JSONObject data) {
        waitForElement(addNewBtn);
        findElement(addNewBtn).click();
        waitForElement(saveAndSubmitButton);

        findElement(firstNameInput).sendKeys(data.get("first_name").toString());
        findElement(lastNameInput).sendKeys(data.get("last_name").toString());
        findElement(inputDepartment).sendKeys(data.get("department").toString());

        findElement(saveButton).click();

        // Write page parameters to json

        try {
            Thread.sleep(DEFAULT_PAUSE);

            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            JSONObject personObj = (JSONObject) jsonObj.get(CONTENT_TYPE);

            if (personObj == null) {
                personObj = new JSONObject();
            }

            JSONObject person = new JSONObject();
            person.put("workflow_state", WorkflowState.IN_PROGRESS.state());
            URL pageURL = new URL(getUrl());
            String[] params = pageURL.getQuery().split("&");
            JSONObject jsonURLQuery = new JSONObject();
            for (String param:params) {
                jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);
            }
            person.put("url_query", jsonURLQuery);
            personObj.put(data.get("person_text").toString(), person);
            jsonObj.put(CONTENT_TYPE, personObj);

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

    public String saveAndSubmitPerson(JSONObject data) {
        try {

            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            String personUrl = getContentUrl(jsonObj, CONTENT_TYPE, data.get("person_text").toString());

            driver.get(personUrl);
            waitForElement(saveAndSubmitButton);

            // ADD here
            findElement(commentsInput).sendKeys(data.get("comment").toString());
            findElement(saveAndSubmitButton).click();
            Thread.sleep(DEFAULT_PAUSE);

            driver.get(personUrl);
            waitForElement(workflowStateSpan);

            JSONObject personObj = (JSONObject) jsonObj.get(CONTENT_TYPE);
            JSONObject person = (JSONObject) personObj.get(data.get("person_text").toString());
            person.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            person.put("deleted", "false");

            personObj.put(data.get("person_text").toString(), person);
            jsonObj.put(CONTENT_TYPE, personObj);

            FileWriter file = new FileWriter(sPathToFile + sFilePageJson);
            file.write(jsonObj.toJSONString().replace("\\", ""));
            file.flush();

            System.out.println("New "+CONTENT_TYPE+" has been submitted: " + data.get("person_text").toString());
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

    public String publishPerson(String person_text) {
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            String personUrl = getContentUrl(jsonObj, CONTENT_TYPE, person_text);

            driver.get(personUrl);
            waitForElement(publishBtn);
            findElement(publishBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            driver.get(personUrl);
            waitForElement(workflowStateSpan);

            JSONObject personObj = (JSONObject) jsonObj.get(CONTENT_TYPE);
            JSONObject person = (JSONObject) personObj.get(person_text);

            person.put("workflow_state", WorkflowState.LIVE.state());
            person.put("deleted", "false");

            personObj.put(person_text, person);
            jsonObj.put(CONTENT_TYPE, personObj);

            FileWriter file = new FileWriter(sPathToFile + sFilePageJson);
            file.write(jsonObj.toJSONString().replace("\\", ""));
            file.flush();

            Thread.sleep(DEFAULT_PAUSE*2);
            driver.navigate().refresh();

            System.out.println("New "+CONTENT_TYPE+" has been published: " + person_text);
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

    public String setupAsDeletedPerson(String person_text) {
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            String personUrl = getContentUrl(jsonObj, CONTENT_TYPE, person_text);

            driver.get(personUrl);
            waitForElement(commentsInput);
            findElement(commentsInput).sendKeys("Removing the "+CONTENT_TYPE);

            findElement(deleteBtn).click();
            Thread.sleep(DEFAULT_PAUSE);
            driver.get(personUrl);

            JSONObject personObj = (JSONObject) jsonObj.get(CONTENT_TYPE);
            JSONObject person = (JSONObject) personObj.get(person_text);

            person.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            person.put("deleted", "true");

            personObj.put(person_text, person);
            jsonObj.put(CONTENT_TYPE, personObj);

            FileWriter file = new FileWriter(sPathToFile + sFilePageJson);
            file.write(jsonObj.toJSONString().replace("\\", ""));
            file.flush();

            System.out.println(CONTENT_TYPE+" has been set up as deleted: " + person_text);
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

    public String removePerson(String person_text) {
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            String personUrl = getContentUrl(jsonObj, CONTENT_TYPE, person_text);

            driver.get(personUrl);
            waitForElement(commentsInput);

            if (findElement(currentContentSpan).getText().equals(WorkflowState.DELETE_PENDING.state())) {

                findElement(commentsInput).sendKeys("Approving the " + CONTENT_TYPE + " removal");
                findElement(publishBtn).click();

                JSONObject personObj = (JSONObject) jsonObj.get(CONTENT_TYPE);
                personObj.remove(person_text);
                jsonObj.put(CONTENT_TYPE, personObj);

                FileWriter file = new FileWriter(sPathToFile + sFilePageJson);
                file.write(jsonObj.toJSONString().replace("\\", ""));
                file.flush();

                Thread.sleep(DEFAULT_PAUSE * 2);
                driver.get(personUrl);

                System.out.println(person_text + ": " + CONTENT_TYPE + " has been removed");
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
