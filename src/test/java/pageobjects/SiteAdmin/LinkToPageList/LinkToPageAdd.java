package pageobjects.SiteAdmin.LinkToPageList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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

import static specs.AbstractSpec.desktopUrl;
import static specs.AbstractSpec.propUISiteAdmin;

/**
 * Created by philipsushkov on 2017-05-03.
 */

public class LinkToPageAdd extends AbstractPageObject {
    private static By moduleTitle, keyNameField, linkToPageField;
    private static By saveBtn, revertBtn, cancelBtn, deleteBtn, addNewLink, publishBtn;
    private static By workflowStateSpan, commentsTxt, successMsg, saveAndSubmitBtn, currentContentSpan;
    private static String sPathToFile, sFileJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String PAGE_NAME="Link To Page";

    public LinkToPageAdd(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISiteAdmin.getProperty("spanModule_Title"));
        keyNameField = By.xpath(propUISiteAdmin.getProperty("input_KeyName"));
        linkToPageField = By.xpath(propUISiteAdmin.getProperty("select_LinkToPage"));
        saveBtn = By.xpath(propUISiteAdmin.getProperty("btn_Save"));
        cancelBtn = By.xpath(propUISiteAdmin.getProperty("btn_Cancel"));
        deleteBtn = By.xpath(propUISiteAdmin.getProperty("btn_Delete"));
        publishBtn = By.xpath(propUISiteAdmin.getProperty("btn_Publish"));
        addNewLink = By.xpath(propUISiteAdmin.getProperty("input_AddNew"));
        revertBtn = By.xpath(propUISiteAdmin.getProperty("btn_Revert"));
        workflowStateSpan = By.xpath(propUISiteAdmin.getProperty("select_WorkflowState"));
        commentsTxt = By.xpath(propUISiteAdmin.getProperty("txtarea_Comments"));
        successMsg = By.xpath(propUISiteAdmin.getProperty("msg_Success"));
        saveAndSubmitBtn = By.xpath(propUISiteAdmin.getProperty("btn_SaveAndSubmit"));
        currentContentSpan = By.xpath(propUISiteAdmin.getProperty("span_CurrentContent"));

        parser = new JSONParser();

        sPathToFile = System.getProperty("user.dir") + propUISiteAdmin.getProperty("dataPath_LinkToPageList");
        sFileJson = propUISiteAdmin.getProperty("json_LinkToPage");
    }

    public String getTitle() {
        findElement(addNewLink).click();
        waitForElement(moduleTitle);
        String sTitle = getText(moduleTitle);
        findElement(cancelBtn).click();
        return sTitle;
    }

    public String saveLinkToPage(JSONObject data, String name) {
        String key_name, link_to_page;
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonMain = new JSONObject();

        waitForElement(addNewLink);
        findElement(addNewLink).click();
        waitForElement(saveBtn);

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            key_name = data.get("key_name").toString();
            findElement(keyNameField).clear();
            findElement(keyNameField).sendKeys(key_name);
            jsonObj.put("key_name", key_name);

            link_to_page = data.get("link_to_page").toString();
            findElement(linkToPageField).sendKeys(link_to_page);
            jsonObj.put("link_to_page", link_to_page);

            findElement(saveBtn).click();
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(successMsg);

            // Save Link To Page Url
            URL url = new URL(getUrl());
            String[] params = url.getQuery().split("&");
            JSONObject jsonURLQuery = new JSONObject();
            for (String param:params) {
                jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);
            }
            jsonObj.put("url_query", jsonURLQuery);

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

    public String getPageUrl(JSONObject obj, String name) {
        String  sItemID = JsonPath.read(obj, "$.['"+name+"'].url_query.ItemID");
        String  sLanguageId = JsonPath.read(obj, "$.['"+name+"'].url_query.LanguageId");
        String  sSectionId = JsonPath.read(obj, "$.['"+name+"'].url_query.SectionId");
        return desktopUrl.toString()+"default.aspx?ItemID="+sItemID+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }
}
