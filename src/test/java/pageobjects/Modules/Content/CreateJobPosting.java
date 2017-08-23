package pageobjects.Modules.Content;

import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
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
 * Created by andyp on 2017-07-21.
 */
public class CreateJobPosting extends AbstractPageObject {
    private static By regionSelect, countrySelect, locationInput, divisionSelect, jobTitleInput, jobTypeSelect, jobFunctionInput;
    private static By refNoInput, managerEmailInput, openingDateInput, closingDateInput, summaryFrame, documentPathInput, addNewBtn, textArea;
    private static By commentsInput, switchToHtml, saveButton, saveAndSubmitButton, publishBtn, deleteBtn, workflowStateSpan, currentContentSpan, yourPageUrl;

    private static String sPathToFile, sFilePageJson;

    private static JSONParser parser;

    private static final long DEFAULT_PAUSE = 2500;
    private final String CONTENT_TYPE = "job_posting";

    public CreateJobPosting(WebDriver driver) {
        super(driver);
        regionSelect = By.xpath(propUIContentAdmin.getProperty("select_Region"));
        countrySelect = By.xpath(propUIContentAdmin.getProperty("select_Country"));
        locationInput = By.xpath(propUIContentAdmin.getProperty("input_Location"));
        divisionSelect = By.xpath(propUIContentAdmin.getProperty("select_Division"));
        jobTitleInput = By.xpath(propUIContentAdmin.getProperty("input_JobTitle"));
        jobTypeSelect = By.xpath(propUIContentAdmin.getProperty("select_JobType"));
        jobFunctionInput = By.xpath(propUIContentAdmin.getProperty("input_JobFunction"));
        refNoInput = By.xpath(propUIContentAdmin.getProperty("input_RefNo"));
        managerEmailInput = By.xpath(propUIContentAdmin.getProperty("input_ManagerEmail"));
        openingDateInput = By.xpath(propUIContentAdmin.getProperty("input_OpeningDate"));
        closingDateInput = By.xpath(propUIContentAdmin.getProperty("input_ClosingDate"));
        summaryFrame = By.xpath(propUIContentAdmin.getProperty("frame_Summary"));
        textArea = By.xpath(propUIContentAdmin.getProperty("frame_Textarea"));
        documentPathInput = By.xpath(propUIContentAdmin.getProperty("input_DocumentPath"));

        addNewBtn = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));
        switchToHtml = By.className(propUIContentAdmin.getProperty("html_SwitchTo"));
        commentsInput = By.xpath(propUIContentAdmin.getProperty("txtarea_UpdateComments"));
        saveButton = By.xpath(propUIContentAdmin.getProperty("btn_Save"));
        saveAndSubmitButton = By.xpath(propUIContentAdmin.getProperty("btn_SaveAndSubmit"));
        publishBtn = By.xpath(propUIContentAdmin.getProperty("btn_Publish"));
        deleteBtn = By.xpath(propUIContentAdmin.getProperty("btn_Delete"));
        workflowStateSpan = By.xpath(propUIContentAdmin.getProperty("span_WorkflowState"));
        currentContentSpan = By.xpath(propUIContentAdmin.getProperty("span_CurrentContent"));
        yourPageUrl = By.xpath(propUIContentAdmin.getProperty("span_YourPageUrl"));
        
        sPathToFile = System.getProperty("user.dir") + propUIModules.getProperty("dataPath_Content");
        sFilePageJson = propUIModules.getProperty("json_ContentProp");

        parser = new JSONParser();
    }

    public String saveJobPosting(JSONObject data) {
        waitForElement(addNewBtn);
        findElement(addNewBtn).click();
        waitForElement(saveAndSubmitButton);

        findElement(regionSelect).sendKeys(data.get("region").toString());
        findElement(countrySelect).sendKeys(data.get("country").toString());
        findElement(locationInput).sendKeys(data.get("location").toString());
        findElement(divisionSelect).sendKeys(data.get("division").toString());
        findElement(jobTitleInput).sendKeys(data.get("job_title").toString());
        findElement(jobTypeSelect).sendKeys(data.get("job_type").toString());
        findElement(jobFunctionInput).sendKeys(data.get("job_function").toString());
        findElement(refNoInput).sendKeys(data.get("ref_no").toString());
        findElement(managerEmailInput).sendKeys(data.get("manager_email").toString());
        findElement(openingDateInput).sendKeys(data.get("opening_date").toString());
        findElement(closingDateInput).sendKeys(data.get("closing_date").toString());

        findElement(switchToHtml).click();

        // writing to summary frame since it is iFrame
        driver.switchTo().frame(findElement(summaryFrame));
        findElement(By.xpath("//body")).sendKeys(data.get("summary").toString());
        driver.switchTo().defaultContent();

        pause(1000L);

        findElement(saveButton).click();

        // Write page parameters to json

        try {
            Thread.sleep(DEFAULT_PAUSE);

            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            JSONObject jobPostingsObj = (JSONObject) jsonObj.get(CONTENT_TYPE);

            if (jobPostingsObj == null) {
                jobPostingsObj = new JSONObject();
            }

            JSONObject jobPosting = new JSONObject();
            jobPosting.put("workflow_state", WorkflowState.IN_PROGRESS.state());
            URL pageURL = new URL(getUrl());
            String[] params = pageURL.getQuery().split("&");
            JSONObject jsonURLQuery = new JSONObject();
            for (String param:params) {
                jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);
            }
            jobPosting.put("url_query", jsonURLQuery);
            jobPostingsObj.put(data.get("job_title").toString(), jobPosting);
            jsonObj.put(CONTENT_TYPE, jobPostingsObj);

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

    public String saveAndSubmitJobPosting(JSONObject data) {
        String your_page_url;

        try {

            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            String jobPostingUrl = getContentUrl(jsonObj, CONTENT_TYPE, data.get("job_title").toString());

            driver.get(jobPostingUrl);
            waitForElement(saveAndSubmitButton);

            findElement(commentsInput).sendKeys(data.get("comment").toString());
            findElement(saveAndSubmitButton).click();
            Thread.sleep(DEFAULT_PAUSE);

            driver.get(jobPostingUrl);
            waitForElement(workflowStateSpan);

            JSONObject jobPostingsObj = (JSONObject) jsonObj.get(CONTENT_TYPE);
            JSONObject jobPosting = (JSONObject) jobPostingsObj.get(data.get("job_title").toString());

            jobPosting.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            jobPosting.put("deleted", "false");

            jobPostingsObj.put(data.get("job_title").toString(), jobPosting);
            jsonObj.put(CONTENT_TYPE, jobPostingsObj);

            FileWriter file = new FileWriter(sPathToFile + sFilePageJson);
            file.write(jsonObj.toJSONString().replace("\\", ""));
            file.flush();

            System.out.println("New "+CONTENT_TYPE+" has been submitted: " + data.get("job_title").toString());
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

    public String publishJobPosting(String job_title) {
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            String jobPostingUrl = getContentUrl(jsonObj, CONTENT_TYPE, job_title);

            driver.get(jobPostingUrl);
            waitForElement(publishBtn);
            findElement(publishBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            driver.get(jobPostingUrl);
            waitForElement(workflowStateSpan);

            JSONObject jobPostingsObj = (JSONObject) jsonObj.get(CONTENT_TYPE);
            JSONObject jobPosting = (JSONObject) jobPostingsObj.get(job_title);

            jobPosting.put("workflow_state", WorkflowState.LIVE.state());
            jobPosting.put("deleted", "false");

            jobPostingsObj.put(job_title, jobPosting);
            jsonObj.put(CONTENT_TYPE, jobPostingsObj);

            FileWriter file = new FileWriter(sPathToFile + sFilePageJson);
            file.write(jsonObj.toJSONString().replace("\\", ""));
            file.flush();

            Thread.sleep(DEFAULT_PAUSE*2);
            driver.navigate().refresh();

            System.out.println("New "+CONTENT_TYPE+" has been published: " + job_title);
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

    public String setupAsDeletedJobPosting(String job_title) {
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            String jobPostingUrl = getContentUrl(jsonObj, CONTENT_TYPE, job_title);

            driver.get(jobPostingUrl);
            waitForElement(commentsInput);
            findElement(commentsInput).sendKeys("Removing the "+CONTENT_TYPE);

            findElement(deleteBtn).click();
            Thread.sleep(DEFAULT_PAUSE);
            driver.get(jobPostingUrl);

            JSONObject jobPostingsObj = (JSONObject) jsonObj.get(CONTENT_TYPE);
            JSONObject jobPosting = (JSONObject) jobPostingsObj.get(job_title);

            jobPosting.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            jobPosting.put("deleted", "true");

            jobPostingsObj.put(job_title, jobPosting);
            jsonObj.put(CONTENT_TYPE, jobPostingsObj);

            FileWriter file = new FileWriter(sPathToFile + sFilePageJson);
            file.write(jsonObj.toJSONString().replace("\\", ""));
            file.flush();

            System.out.println(CONTENT_TYPE+" has been set up as deleted: " + job_title);
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

    public String removeJobPosting(String job_title) {
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            String jobPostingUrl = getContentUrl(jsonObj, CONTENT_TYPE, job_title);

            driver.get(jobPostingUrl);
            waitForElement(commentsInput);

            if (findElement(currentContentSpan).getText().equals(WorkflowState.DELETE_PENDING.state())) {

                findElement(commentsInput).sendKeys("Approving the " + CONTENT_TYPE + " removal");
                findElement(publishBtn).click();

                JSONObject jobPostingsObj = (JSONObject) jsonObj.get(CONTENT_TYPE);
                jobPostingsObj.remove(job_title);
                jsonObj.put(CONTENT_TYPE, jobPostingsObj);

                FileWriter file = new FileWriter(sPathToFile + sFilePageJson);
                file.write(jsonObj.toJSONString().replace("\\", ""));
                file.flush();

                Thread.sleep(DEFAULT_PAUSE * 2);
                driver.get(jobPostingUrl);

                System.out.println(job_title + ": " + CONTENT_TYPE + " has been removed");
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
