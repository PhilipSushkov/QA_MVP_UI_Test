package pageobjects.Modules.Content;


import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
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
 * Created by zacharyk on 2017-06-26.
 */
public class CreatePresentation extends AbstractPageObject {
    private static By dateInput, timeHHSelect, timeMMSelect, timeAMSelect, tagsInput, titleInput, textArea, presentationFileInput, commentsInput;
    private static By switchToHtml, saveButton, saveAndSubmitButton, publishBtn, deleteBtn, workflowStateSpan, currentContentSpan, yourPageUrl;

    private static String sPathToFile, sFilePageJson;

    private static JSONParser parser;

    private final String presentationFile = "bitcoin.pdf";

    private static final long DEFAULT_PAUSE = 2500;
    private final String CONTENT_TYPE = "presentation";

    public CreatePresentation(WebDriver driver) {
        super(driver);

        dateInput = By.xpath(propUIContentAdmin.getProperty("input_PresentationDate"));
        timeHHSelect = By.xpath(propUIContentAdmin.getProperty("select_PresentationTimeHH"));
        timeMMSelect = By.xpath(propUIContentAdmin.getProperty("select_PresentationTimeMM"));
        timeAMSelect = By.xpath(propUIContentAdmin.getProperty("select_PresentationTimeAM"));
        tagsInput = By.xpath(propUIContentAdmin.getProperty("input_Tags"));
        textArea = By.tagName(propUIContentAdmin.getProperty("frame_Textarea"));
        presentationFileInput = By.xpath(propUIContentAdmin.getProperty("input_PresentationFile"));
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

    public String savePresentation(JSONObject data) {

        waitForElement(saveAndSubmitButton);

        findElement(dateInput).sendKeys(data.get("date").toString());
        findElement(timeHHSelect).sendKeys(data.get("hour").toString());
        findElement(timeMMSelect).sendKeys(data.get("minute").toString());
        findElement(timeAMSelect).sendKeys(data.get("AMPM").toString());
        findElement(titleInput).sendKeys(data.get("headline").toString());
        findElement(switchToHtml).click();

        driver.switchTo().frame(2);
        findElement(textArea).sendKeys(data.get("body").toString());
        driver.switchTo().defaultContent();
        pause(1000L);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement elemSrc =  findElement(presentationFileInput);
        js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", elemSrc, "value", "files/"+presentationFile);
        findElement(saveButton).click();

        // Write page parameters to json

        try {
            Thread.sleep(DEFAULT_PAUSE);

            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            JSONObject presentationsObj = (JSONObject) jsonObj.get(CONTENT_TYPE);

            if (presentationsObj == null) {
                presentationsObj = new JSONObject();
            }

            JSONObject presentation = new JSONObject();
            presentation.put("workflow_state", WorkflowState.IN_PROGRESS.state());
            URL pageURL = new URL(getUrl());
            String[] params = pageURL.getQuery().split("&");
            JSONObject jsonURLQuery = new JSONObject();
            for (String param:params) {
                jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);
            }
            presentation.put("url_query", jsonURLQuery);
            presentationsObj.put(data.get("headline").toString(), presentation);
            jsonObj.put(CONTENT_TYPE, presentationsObj);

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

    public String saveAndSubmitPresentation(JSONObject data) {
        String your_page_url;

        try {

            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            String presentationUrl = getContentUrl(jsonObj, CONTENT_TYPE, data.get("headline").toString());

            driver.get(presentationUrl);
            waitForElement(saveAndSubmitButton);

            findElement(tagsInput).sendKeys(data.get("tags").toString());
            findElement(commentsInput).sendKeys(data.get("comment").toString());
            findElement(saveAndSubmitButton).click();
            Thread.sleep(DEFAULT_PAUSE);

            driver.get(presentationUrl);
            waitForElement(workflowStateSpan);

            JSONObject presentationsObj = (JSONObject) jsonObj.get(CONTENT_TYPE);
            JSONObject presentation = (JSONObject) presentationsObj.get(data.get("headline").toString());

            your_page_url = findElement(yourPageUrl).getText().trim();
            //System.out.println(your_page_url);

            presentation.put("your_page_url", your_page_url);
            presentation.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            presentation.put("deleted", "false");

            presentationsObj.put(data.get("headline").toString(), presentation);
            jsonObj.put(CONTENT_TYPE, presentationsObj);

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

    public String publishPresentation(String headline) {
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            String presentationUrl = getContentUrl(jsonObj, CONTENT_TYPE, headline);

            driver.get(presentationUrl);
            waitForElement(publishBtn);
            findElement(publishBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            driver.get(presentationUrl);
            waitForElement(workflowStateSpan);

            JSONObject presentationsObj = (JSONObject) jsonObj.get(CONTENT_TYPE);
            JSONObject presentation = (JSONObject) presentationsObj.get(headline);

            presentation.put("workflow_state", WorkflowState.LIVE.state());
            presentation.put("deleted", "false");

            presentationsObj.put(headline, presentation);
            jsonObj.put(CONTENT_TYPE, presentationsObj);

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

    public String setupAsDeletedPresentation(String headline) {
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            String presentationUrl = getContentUrl(jsonObj, CONTENT_TYPE, headline);

            driver.get(presentationUrl);
            waitForElement(commentsInput);
            findElement(commentsInput).sendKeys("Removing the "+CONTENT_TYPE);

            findElement(deleteBtn).click();
            Thread.sleep(DEFAULT_PAUSE);
            driver.get(presentationUrl);

            JSONObject presentationsObj = (JSONObject) jsonObj.get(CONTENT_TYPE);
            JSONObject presentation = (JSONObject) presentationsObj.get(headline);

            presentation.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            presentation.put("deleted", "true");

            presentationsObj.put(headline, presentation);
            jsonObj.put(CONTENT_TYPE, presentationsObj);

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

    public String removePresentation(String headline) {
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            String presentationUrl = getContentUrl(jsonObj, CONTENT_TYPE, headline);

            driver.get(presentationUrl);
            waitForElement(commentsInput);

            if (findElement(currentContentSpan).getText().equals(WorkflowState.DELETE_PENDING.state())) {

                findElement(commentsInput).sendKeys("Approving the " + CONTENT_TYPE + " removal");
                findElement(publishBtn).click();

                JSONObject presentationsObj = (JSONObject) jsonObj.get(CONTENT_TYPE);
                presentationsObj.remove(headline);
                jsonObj.put(CONTENT_TYPE, presentationsObj);

                FileWriter file = new FileWriter(sPathToFile + sFilePageJson);
                file.write(jsonObj.toJSONString().replace("\\", ""));
                file.flush();

                Thread.sleep(DEFAULT_PAUSE * 2);
                driver.get(presentationUrl);

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
