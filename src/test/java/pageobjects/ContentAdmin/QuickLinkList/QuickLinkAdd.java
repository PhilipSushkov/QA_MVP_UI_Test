package pageobjects.ContentAdmin.QuickLinkList;

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
import static specs.AbstractSpec.propUISiteAdmin;


/**
 * Created by andyp on 2017-06-19.
 */
public class QuickLinkAdd extends AbstractPageObject {

    private static By moduleTitle, addNewLink, descriptionInput, urlInput, textInput, tagsInput, internalUrlSelect, documentUrlInput;
    private static By typeExternalRadio, typeInternalRadio, typeEmailRadio, typeDocumentRadio, selectedUrl;
    private static By quickLinkPagesLink, linkToNewChk, activeChk, gridPagesTable, currentContentSpan;
    private static By saveBtn, cancelBtn, deleteBtn, publishBtn, revertBtn, workflowStateSpan, commentsTxt, successMsg, saveAndSubmitBtn;
    private static String sPathToFile, sFileJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String PAGE_NAME="Quicklink";

    public QuickLinkAdd(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("spanModule_Title"));
        descriptionInput = By.xpath(propUIContentAdmin.getProperty("input_Description"));
        urlInput = By.xpath(propUIContentAdmin.getProperty("input_Url"));
        internalUrlSelect = By.xpath(propUIContentAdmin.getProperty("select_internalUrl"));
        selectedUrl = By.xpath(propUIContentAdmin.getProperty("option_selectedUrl"));
        documentUrlInput = By.xpath(propUIContentAdmin.getProperty("input_documentUrl"));
        textInput = By.xpath(propUIContentAdmin.getProperty("input_Text"));
        tagsInput = By.xpath(propUIContentAdmin.getProperty("input_Tags"));

        typeExternalRadio = By.xpath(propUIContentAdmin.getProperty("rdo_TypeExternal"));
        typeInternalRadio = By.xpath(propUIContentAdmin.getProperty("rdo_TypeInternal"));
        typeEmailRadio = By.xpath(propUIContentAdmin.getProperty("rdo_TypeEmail"));
        typeDocumentRadio = By.xpath(propUIContentAdmin.getProperty("rdo_TypeDocument"));

        quickLinkPagesLink = By.xpath(propUIContentAdmin.getProperty("href_QuickLinkPages"));
        gridPagesTable = By.xpath(propUIContentAdmin.getProperty("table_GridPages"));
        saveBtn = By.xpath(propUIContentAdmin.getProperty("btn_Save"));
        cancelBtn = By.xpath(propUIContentAdmin.getProperty("btn_Cancel"));
        deleteBtn = By.xpath(propUIContentAdmin.getProperty("btn_Delete"));
        publishBtn = By.xpath(propUIContentAdmin.getProperty("btn_Publish")+"[@type='submit']");
        addNewLink = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));
        revertBtn = By.xpath(propUIContentAdmin.getProperty("btn_Revert"));
        workflowStateSpan = By.xpath(propUIContentAdmin.getProperty("select_WorkflowState"));
        commentsTxt = By.xpath(propUIContentAdmin.getProperty("txtarea_Comments"));
        successMsg = By.xpath(propUIContentAdmin.getProperty("msg_Success"));
        saveAndSubmitBtn = By.xpath(propUIContentAdmin.getProperty("btn_SaveAndSubmit"));
        currentContentSpan = By.xpath(propUIContentAdmin.getProperty("span_CurrentContent"));

        linkToNewChk = By.xpath(propUIContentAdmin.getProperty("chk_LinkToNew"));
        activeChk = By.xpath(propUIContentAdmin.getProperty("chk_Active"));

        parser = new JSONParser();

        sPathToFile = System.getProperty("user.dir") + propUIContentAdmin.getProperty("dataPath_quickLinkListData");
        sFileJson = propUIContentAdmin.getProperty("json_quickLinkList");
    }

    public String getTitle() {
        findElement(addNewLink).click();
        waitForElement(moduleTitle);
        String sTitle = getText(moduleTitle);
        findElement(cancelBtn).click();
        return sTitle;
    }

    public String saveQuickLink(JSONObject data, String name) {
        String description, URL, text, tags, type;
        Boolean active, linkToNew;
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

            description = data.get("description").toString();
            findElement(descriptionInput).clear();
            findElement(descriptionInput).sendKeys(description);
            jsonObj.put("description", description);

            text = data.get("text").toString();
            findElement(textInput).clear();
            findElement(textInput).sendKeys(text);
            jsonObj.put("text", text);

            tags = data.get("tags").toString();
            findElement(tagsInput).clear();
            findElement(tagsInput).sendKeys(tags);
            jsonObj.put("tags", tags);

            type = data.get("type").toString();
            switch(type){
                case "External":
                    findElement(typeExternalRadio).click();
                    URL = data.get("URL").toString();
                    findElement(urlInput).clear();
                    findElement(urlInput).sendKeys(URL);
                    jsonObj.put("URL", URL);
                    break;

                case "Internal":
                    findElement(typeInternalRadio).click();
                    waitForElement(internalUrlSelect);
                    URL = data.get("URL").toString();
                    findElement(internalUrlSelect).sendKeys(URL);
                    jsonObj.put("URL", URL);
                    break;

                case "Document":
                    findElement(typeDocumentRadio).click();
                    waitForElement(documentUrlInput);
                    URL = data.get("URL").toString();
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    WebElement elemSrc =  driver.findElement(documentUrlInput);
                    js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", elemSrc, "value", "files/"+URL);
                    jsonObj.put("URL",URL);
                    break;

            }

            //Link to new
            linkToNew = Boolean.parseBoolean(data.get("link_to_new").toString());
            jsonObj.put("link_to_new", Boolean.parseBoolean(data.get("link_to_new").toString()));
            if (linkToNew) {
                if (!Boolean.parseBoolean(findElement(linkToNewChk).getAttribute("checked"))) {
                    findElement(linkToNewChk).click();
                } else {
                }
            } else {
                if (!Boolean.parseBoolean(findElement(linkToNewChk).getAttribute("checked"))) {
                } else {
                    findElement(linkToNewChk).click();
                }
            }

            // Save Active checkbox
            active = Boolean.parseBoolean(data.get("active").toString());
            jsonObj.put("active", Boolean.parseBoolean(data.get("active").toString()));
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
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(successMsg);

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

    public String saveAndSubmitQuickLink(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        By editBtn = By.xpath("//td[(text()='" + data.get("description").toString() + "')]/parent::tr/td/input[contains(@id, 'imgEdit')][contains(@id, 'QuickLink')]");

        try {
            waitForElement(moduleTitle);

            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            waitForElement(editBtn);
            findElement(editBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(commentsTxt);
            findElement(commentsTxt).sendKeys(data.get("comment").toString());
            findElement(saveAndSubmitBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(editBtn);
            findElement(editBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            JSONObject jsonObj = (JSONObject) jsonMain.get(name);

            jsonObj.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            jsonObj.put("deleted", "false");

            // Save Link To Page Url
            URL url = new URL(getUrl());
            String[] params = url.getQuery().split("&");
            JSONObject jsonURLQuery = new JSONObject();
            for (String param:params) {
                jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);
            }
            jsonObj.put("url_query", jsonURLQuery);


            jsonMain.put(name, jsonObj);

            FileWriter file = new FileWriter(sPathToFile + sFileJson);
            file.write(jsonMain.toJSONString().replace("\\", ""));
            file.flush();

            System.out.println(name+ ": "+PAGE_NAME+" has been sumbitted");
            return findElement(workflowStateSpan).getText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Boolean checkQuickLink(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        String type;

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            //Causing errors
            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(commentsTxt);


            // Compare field values with entry data
            try {
                if (!findElement(descriptionInput).getAttribute("value").equals(data.get("description").toString())) {
                    System.out.println("Description is wrong");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            type = data.get("type").toString();
            switch(type){
                case "External":
                    try {
                        if (!findElement(urlInput).getAttribute("value").equals(data.get("URL").toString())) {
                            System.out.println("URL input is wrong");
                            return false;
                        }
                    } catch (NullPointerException e) {
                    }
                    break;

                case "Internal":
                    try {
                        if (!findElement(selectedUrl).getAttribute("innerhtml").equals(data.get("URL").toString())) {
                            System.out.println("URL input is wrong");
                            return false;
                        }
                    } catch (NullPointerException e) {
                    }
                    break;

                case "Document":
                    try {
                        if (!findElement(documentUrlInput).getAttribute("value").equals("files/" + data.get("URL").toString())) {
                            System.out.println("URL input is wrong");
                            return false;
                        }
                    } catch (NullPointerException e) {
                    }
                    break;

            }

            try {
                if (!findElement(textInput).getAttribute("value").equals(data.get("text").toString())) {
                    System.out.println("Text does not correspond to data");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                //Contains because adding a tag automatically adds a space at the end
                if (!findElement(tagsInput).getAttribute("value").contains(data.get("tags").toString())) {
                    System.out.println("Tag does not correspond to data");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(linkToNewChk).getAttribute("checked").equals(data.get("link_to_new").toString())) {
                    System.out.println("Link to new does not correspond to data");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(activeChk).getAttribute("checked").equals(data.get("active").toString())) {
                    System.out.println("Active checkbox does not correspond to data");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            System.out.println(name+ ": New "+PAGE_NAME+" has been checked");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String publishQuickLink(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
                jsonObj = (JSONObject) jsonMain.get(name);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

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

            System.out.println(name+ ": New "+PAGE_NAME+" has been published");
            return findElement(workflowStateSpan).getText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String revertToLiveQuickLink(String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
                jsonObj = (JSONObject) jsonMain.get(name);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(revertBtn);

            if (jsonObj.get("workflow_state").toString().equals(WorkflowState.FOR_APPROVAL.state())) {
                findElement(revertBtn).click();
                Thread.sleep(DEFAULT_PAUSE);

                jsonObj.put("workflow_state", WorkflowState.LIVE.state());
                jsonObj.put("deleted", "false");

                jsonMain.put(name, jsonObj);

                FileWriter file = new FileWriter(sPathToFile + sFileJson);
                file.write(jsonMain.toJSONString().replace("\\", ""));
                file.flush();

                driver.get(pageUrl);
                Thread.sleep(DEFAULT_PAUSE);
                waitForElement(workflowStateSpan);

                System.out.println(name+ ": "+PAGE_NAME+" has been reverted to Live");
                return findElement(workflowStateSpan).getText();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String changeAndSubmitQuickLink(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
                jsonObj = (JSONObject) jsonMain.get(name);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(saveAndSubmitBtn);

            // Change type
            try {
                if (!data.get("type_ch").toString().isEmpty()) {
                    String type_ch = data.get("type_ch").toString();
                    String url_ch = data.get("URL_ch").toString();

                    findElement(typeEmailRadio).click();
                    waitForElement(urlInput);
                    findElement(urlInput).clear();
                    findElement(urlInput).sendKeys(url_ch);

                    jsonObj.put("type", type_ch);
                    jsonObj.put("URL", url_ch);
                }
            } catch (NullPointerException e){
            }
            // Changing active
            jsonObj.put("active", Boolean.parseBoolean(data.get("active").toString()));
            try {
                // Save Active checkbox
                if (Boolean.parseBoolean(data.get("active_ch").toString())) {
                    if (!Boolean.parseBoolean(findElement(activeChk).getAttribute("checked"))) {
                        findElement(activeChk).click();
                        jsonObj.put("active", true);
                    } else {
                    }
                } else {
                    if (!Boolean.parseBoolean(findElement(activeChk).getAttribute("checked"))) {
                    } else {
                        findElement(activeChk).click();
                        jsonObj.put("active", false);
                    }
                }
            } catch (NullPointerException e) {
            }

            //Changing tags
            try {
                if (!data.get("tags_ch").toString().isEmpty()) {
                    String tags_ch = data.get("tags_ch").toString();
                    waitForElement(tagsInput);
                    findElement(tagsInput).clear();
                    findElement(tagsInput).sendKeys(tags_ch);

                    jsonObj.put("tags", tags_ch);
                }
            } catch (NullPointerException e){
            }

            try {
                findElement(commentsTxt).clear();
                findElement(commentsTxt).sendKeys(data.get("comment_ch").toString());
            } catch (NullPointerException e) {
            }

            findElement(saveAndSubmitBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            jsonObj.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            jsonObj.put("deleted", "false");

            jsonMain.put(name, jsonObj);

            FileWriter file = new FileWriter(sPathToFile + sFileJson);
            file.write(jsonMain.toJSONString().replace("\\", ""));
            file.flush();

            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(workflowStateSpan);

            System.out.println(name+ ": New "+PAGE_NAME+" changes have been submitted");
            return findElement(workflowStateSpan).getText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Boolean checkQuickLinkCh(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(commentsTxt);

            // Compare field values with changed data
            try {
                if(data.get("active_ch").toString() != null){
                    if (!findElement(activeChk).getAttribute("checked").equals(data.get("active_ch").toString())) {
                        System.out.println("Change in active button was not reflected");
                        System.out.println("Expected: "+ findElement(activeChk).getAttribute("checked") +", but found: "+ data.get("active_ch").toString());
                        return false;
                    }
                }
            } catch (NullPointerException e) {
            }

            try {
                if(data.get("tags_ch").toString() != null){
                    if (!findElement(tagsInput).getAttribute("value").contains(data.get("tags_ch").toString())) {
                        System.out.println("Change in tag was not reflected");
                        System.out.println("Expected: "+ findElement(tagsInput).getAttribute("value") +", but found: "+ data.get("tags_ch").toString());
                        return false;
                    }
                }
            } catch (NullPointerException e) {
            }

            //Check changing type
            try {
                if(data.get("type_ch") != null){
                    if (!findElement(typeEmailRadio).getAttribute("checked").equals("true")) {
                        System.out.println("Change in type was not reflected");
                        return false;
                    }
                    if (!findElement(urlInput).getAttribute("innerhtml").equals(data.get("URL_ch"))) {

                        System.out.println("Change in url was not reflected");
                        System.out.println("Expected: "+ findElement(urlInput).getAttribute("innerhtml") +", but found: "+ data.get("URL_ch").toString());
                        return false;
                    }
                }
            } catch (NullPointerException e) {
            }

            System.out.println(name+ ": New "+PAGE_NAME+" has been checked");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String setupAsDeletedQuickLink(String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
                jsonObj = (JSONObject) jsonMain.get(name);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(commentsTxt);
            findElement(commentsTxt).sendKeys("Removing "+PAGE_NAME);
            findElement(deleteBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(currentContentSpan);

            jsonObj.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            jsonObj.put("deleted", "true");

            jsonMain.put(name, jsonObj);

            FileWriter file = new FileWriter(sPathToFile + sFileJson);
            file.write(jsonMain.toJSONString().replace("\\", ""));
            file.flush();

            System.out.println(name+ ": "+PAGE_NAME+" set up as deleted");
            return findElement(currentContentSpan).getText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String removeQuickLink(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            if (findElement(currentContentSpan).getText().equals(WorkflowState.DELETE_PENDING.state())) {

                waitForElement(commentsTxt);
                findElement(commentsTxt).sendKeys("Approving "+PAGE_NAME+" removal");
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

                System.out.println(name+ ": New "+PAGE_NAME+" has been removed");
                return findElement(workflowStateSpan).getText();
            }
        } catch (IOException e) {
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
