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
 * Created by andyp on 2017-07-25.
 */
public class CreateFaq extends AbstractPageObject{
    private static By activeChk, nameInputEN, nameInputGE, questionInput, questionsListName, questionModuleTitle;
    private static By moduleTitle, saveBtn, revertBtn, cancelBtn, deleteBtn, addNewLink, publishBtn, saveAndSubmitBtn;
    private static By switchToHtml, workflowStateSpan, commentsTxt, successMsg, currentContentSpan;
    private static By textArea, radEditor, radEditorContent;
    private static String sPathToFile, sFileJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String CONTENT_TYPE = "faq";

    public CreateFaq(WebDriver driver) {
        super(driver);

        activeChk = By.xpath(propUIContentAdmin.getProperty("chk_Active"));
        nameInputEN = By.xpath(propUIContentAdmin.getProperty("input_FaqName"));
        nameInputGE = By.xpath(propUIContentAdmin.getProperty("input_FaqNameGerman"));

        questionInput = By.xpath(propUIContentAdmin.getProperty("input_Question"));
        questionsListName = By.xpath(propUIContentAdmin.getProperty("span_QuestionName"));
        questionModuleTitle = By.xpath(propUIContentAdmin.getProperty("header_QuestionModuleTitle"));

        moduleTitle = By.xpath(propUIContentAdmin.getProperty("spanModule_Title"));
        saveBtn = By.xpath(propUIContentAdmin.getProperty("btn_Save"));
        revertBtn = By.xpath(propUIContentAdmin.getProperty("btn_Revert"));
        cancelBtn = By.xpath(propUIContentAdmin.getProperty("btn_Cancel"));
        deleteBtn = By.xpath(propUIContentAdmin.getProperty("btn_Delete"));
        addNewLink = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));
        publishBtn = By.xpath(propUIContentAdmin.getProperty("btn_Publish"));
        saveAndSubmitBtn = By.xpath(propUIContentAdmin.getProperty("btn_SaveAndSubmit"));

        switchToHtml = By.className(propUIContentAdmin.getProperty("html_SwitchTo"));
        workflowStateSpan = By.xpath(propUIContentAdmin.getProperty("select_WorkflowState"));
        commentsTxt = By.xpath(propUIContentAdmin.getProperty("txtarea_Comments"));
        successMsg = By.xpath(propUIContentAdmin.getProperty("msg_Success"));
        currentContentSpan = By.xpath(propUIContentAdmin.getProperty("span_CurrentContent"));

        textArea = By.tagName(propUIContentAdmin.getProperty("frame_Textarea"));
        radEditor = By.xpath(propUIContentAdmin.getProperty("frame_RadEditor"));
        radEditorContent = By.xpath(propUIContentAdmin.getProperty("field_RadEContent"));

        parser = new JSONParser();

        sPathToFile = System.getProperty("user.dir") + propUIModules.getProperty("dataPath_Content");
        sFileJson = propUIModules.getProperty("json_ContentProp");
    }

    
    public String saveFaq(JSONObject data) {
        String name = data.get("name_EN").toString();
        String name_EN;
        Boolean active;

        waitForElement(addNewLink);
        findElement(addNewLink).click();
        waitForElement(saveBtn);

        name_EN = data.get("name_EN").toString();
        findElement(nameInputEN).clear();
        findElement(nameInputEN).sendKeys(name_EN);

        // Save Active checkbox
        active = Boolean.parseBoolean(data.get("active").toString());
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

        findElement(saveBtn).click();
        // Thread.sleep(DEFAULT_PAUSE);
        waitForElement(successMsg);

        try {
            FileReader readFile = new FileReader(sPathToFile + sFileJson);
            JSONObject jsonMain = (JSONObject) parser.parse(readFile);
            JSONObject jsonFaqObj = (JSONObject) jsonMain.get(CONTENT_TYPE);

            if (jsonFaqObj == null) {
                jsonFaqObj = new JSONObject();
            }
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("workflow_state", WorkflowState.IN_PROGRESS.state());
            URL pageURL = new URL(getUrl());
            String[] params = pageURL.getQuery().split("&");
            JSONObject jsonURLQuery = new JSONObject();
            for (String param:params) {
                jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);
            }

            jsonObj.put("url_query", jsonURLQuery);
            jsonFaqObj.put(data.get("name_EN").toString(),jsonObj);
            jsonMain.put(CONTENT_TYPE, jsonFaqObj);

            try {
                FileWriter writeFile = new FileWriter(sPathToFile + sFileJson);
                writeFile.write(jsonMain.toJSONString().replace("\\", ""));
                writeFile.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(name + ": "+CONTENT_TYPE+" has been saved");
            return findElement(workflowStateSpan).getText();

        } catch (ParseException e1) {
            e1.printStackTrace();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return null;
    }

    public Boolean saveQuestion(JSONObject data) {
        String name = data.get("name_EN").toString();
        String question, answer;

        waitForElement(addNewLink);
        findElement(addNewLink).click();
        waitForElement(saveBtn);

        try{
            question = data.get("question").toString();
            findElement(questionInput).clear();
            findElement(questionInput).sendKeys(question);

            findElement(switchToHtml).click();

            answer = data.get("answer").toString();
            driver.switchTo().frame(2);
            findElement(textArea).sendKeys(answer);
            driver.switchTo().defaultContent();
            pause(1000L);

            findElement(saveBtn).click();
            waitForElement(questionsListName);

            System.out.println(name + ": Question has been created");

            return findElement(questionsListName).getText().contains(question);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public String saveAndSubmitFaq(JSONObject data) throws InterruptedException {
        String workflow;
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();
        By editBtn = By.xpath("//td[(text()='" + data.get("name_EN").toString() + "')]/parent::tr/td/input[contains(@id, 'imgEdit')][contains(@id, 'Faq')]");
        //Please don't use data with special characters ' or " in question name
        By editQuestionBtn = By.xpath("//td//input[contains(@id, 'btnEdit')][../../td/span[text()='" + data.get("question").toString()+ "']]");

        try {
            waitForElement(moduleTitle);

            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, CONTENT_TYPE, data.get("name_EN").toString());
            driver.get(pageUrl);
            waitForElement(saveAndSubmitBtn);

            waitForElement(commentsTxt);
            findElement(commentsTxt).sendKeys(data.get("comment").toString());
            findElement(saveAndSubmitBtn).click();
            //Thread.sleep(DEFAULT_PAUSE);

            waitForElement(editBtn);
            findElement(editBtn).click();
            waitForElement(commentsTxt);

            jsonMain.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            jsonMain.put("deleted", "false");

            workflow = findElement(workflowStateSpan).getText();

            jsonMain.put(data.get("name_EN").toString(), jsonObj);

            FileWriter file = new FileWriter(sPathToFile + sFileJson);
            file.write(jsonMain.toJSONString().replace("\\", ""));
            file.flush();

            System.out.println(CONTENT_TYPE+ ": "+ data.get("name_EN") +" has been sumbitted");
            return workflow;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String publishFaq(String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
                jsonObj = (JSONObject) jsonMain.get(name);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, CONTENT_TYPE, name);
            driver.get(pageUrl);

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

            System.out.println(name+ ": New "+CONTENT_TYPE+" has been published");
            return findElement(workflowStateSpan).getText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    public String setupAsDeletedFaq(String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
                jsonObj = (JSONObject) jsonMain.get(name);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, CONTENT_TYPE, name);
            driver.get(pageUrl);

            waitForElement(commentsTxt);
            findElement(commentsTxt).sendKeys("Removing "+CONTENT_TYPE);
            findElement(deleteBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            driver.get(pageUrl);
            waitForElement(currentContentSpan);

            jsonObj.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            jsonObj.put("deleted", "true");

            jsonMain.put(name, jsonObj);

            FileWriter file = new FileWriter(sPathToFile + sFileJson);
            file.write(jsonMain.toJSONString().replace("\\", ""));
            file.flush();

            System.out.println(name+ ": "+CONTENT_TYPE+" set up as deleted");
            return findElement(currentContentSpan).getText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String removeFaq(String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, CONTENT_TYPE, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            if (findElement(currentContentSpan).getText().equals(WorkflowState.DELETE_PENDING.state())) {

                waitForElement(commentsTxt);
                findElement(commentsTxt).sendKeys("Approving "+CONTENT_TYPE+" removal");
                findElement(publishBtn).click();

                Thread.sleep(DEFAULT_PAUSE*2);

                driver.get(pageUrl);
                Thread.sleep(DEFAULT_PAUSE);

                jsonMain.remove(name);

                FileWriter file = new FileWriter(sPathToFile + sFileJson);
                file.write(jsonMain.toJSONString().replace("\\", ""));
                file.flush();

                Thread.sleep(DEFAULT_PAUSE*2);
                driver.navigate().refresh();

                System.out.println(name+ ": New "+CONTENT_TYPE+" has been removed");
                return findElement(workflowStateSpan).getText();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String getPageUrl(JSONObject obj,String type, String contentName) {
        String  sItemID = JsonPath.read(obj, "$.['"+type+"'].['"+contentName+"'].url_query.ItemID");
        String  sLanguageId = JsonPath.read(obj, "$.['"+type+"'].['"+contentName+"'].url_query.LanguageId");
        String  sSectionId = JsonPath.read(obj, "$.['"+type+"'].['"+contentName+"'].url_query.SectionId");
        return desktopUrl.toString()+"default.aspx?ItemID="+sItemID+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }

    public String getPageUrlQuestion(JSONObject obj, String name) {
        String  sFaqId = JsonPath.read(obj, "$.['"+name+"'].url_query2.FaqId");
        String  sFaqQuestionId = JsonPath.read(obj, "$.['"+name+"'].url_query2.FaqQuestionId");
        String  sItemId = JsonPath.read(obj, "$.['"+name+"'].url_query2.ItemId");
        String  sLanguageId = JsonPath.read(obj, "$.['"+name+"'].url_query2.LanguageId");
        String  sSectionId = JsonPath.read(obj, "$.['"+name+"'].url_query2.SectionId");
        return desktopUrl.toString()+"default.aspx?FaqId="+sFaqId+"&FaqQuestionId="+ sFaqQuestionId + "&ItemId="+ sItemId + "&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }
}
