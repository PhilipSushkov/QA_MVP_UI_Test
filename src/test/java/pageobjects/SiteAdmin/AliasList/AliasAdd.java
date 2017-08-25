package pageobjects.SiteAdmin.AliasList;

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
import static specs.AbstractSpec.propUISiteAdmin;

/**
 * Created by andyp on 2017-08-22.
 */
public class AliasAdd extends AbstractPageObject{
    private static By moduleTitle, aliasTypeSelect, redirectTypeSelect, aliasNameInput, targetURLInput;
    private static By selectedAliasType, selectedRedirectType, selectedTargetLanguage, selectedTargetPage;
    private static By targetLanguageSelect, targetPageSelect, saveAndSubmitButton;
    private static By saveBtn, cancelBtn, deleteBtn,publishBtn, revertBtn, addNewLink;
    private static By workflowStateSpan, commentsTxt, successMsg, saveAndSubmitBtn, currentContentSpan;
    private static String sPathToFile, sFileJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String PAGE_NAME="Alias";

    public AliasAdd(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISiteAdmin.getProperty("spanModule_Title"));
        aliasTypeSelect = By.xpath(propUISiteAdmin.getProperty("select_AliasType"));
        redirectTypeSelect = By.xpath(propUISiteAdmin.getProperty("select_RedirectType"));
        aliasNameInput = By.xpath(propUISiteAdmin.getProperty("input_AliasName"));
        targetURLInput = By.xpath(propUISiteAdmin.getProperty("input_TargetURL"));
        targetLanguageSelect = By.xpath(propUISiteAdmin.getProperty("select_TargetLanguage"));
        targetPageSelect = By.xpath(propUISiteAdmin.getProperty("select_TargetPage"));

        selectedAliasType = By.xpath(propUISiteAdmin.getProperty("option_SelectedAliasType"));
        selectedRedirectType = By.xpath(propUISiteAdmin.getProperty("option_SelectedRedirectType"));
        selectedTargetLanguage = By.xpath(propUISiteAdmin.getProperty("option_SelectedTargetLanguage"));
        selectedTargetPage =  By.xpath(propUISiteAdmin.getProperty("option_SelectedTargetPage"));

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

        sPathToFile = System.getProperty("user.dir") + propUISiteAdmin.getProperty("dataPath_AliasList");
        sFileJson = propUISiteAdmin.getProperty("json_AliasList");

    }

    public String getTitle() {
        findElement(addNewLink).click();
        waitForElement(moduleTitle);
        String sTitle = getText(moduleTitle);
        findElement(cancelBtn).click();
        return sTitle;
    }

    public String saveAlias(JSONObject data, String name) {
        String type, redirect, aliasName, language, page, url;
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

            type = data.get("type").toString();
            findElement(aliasTypeSelect).sendKeys(type);
            jsonObj.put("type", type);

            redirect = data.get("redirect").toString();
            findElement(redirectTypeSelect).sendKeys(redirect);
            jsonObj.put("redirect", redirect);

            aliasName = data.get("name").toString();
            findElement(aliasNameInput).sendKeys(aliasName);
            jsonObj.put("name", aliasName);

            if(data.get("type").toString().equals("Page")){
                language = data.get("language").toString();
                findElement(targetLanguageSelect).sendKeys(language);
                jsonObj.put("language", language);

                page = data.get("page").toString();
                findElement(targetPageSelect).sendKeys(page);
                jsonObj.put("page", page);
            } else {
                System.out.println();
                url = data.get("url").toString();
                findElement(targetURLInput).sendKeys(url);
                jsonObj.put("url", url);
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

    public String saveAndSubmitAlias(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        By editBtn = By.xpath("//td[(text()='" + data.get("name").toString() + "')]/parent::tr/td/input[contains(@id, 'imgEdit')][contains(@id, 'Alias')]");

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

    public Boolean checkAlias(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();

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
                if (!findElement(selectedAliasType).getAttribute("value").equals(data.get("type").toString())) {
                    System.out.println("Alias type do not match");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(selectedRedirectType).getAttribute("value").equals(data.get("redirect").toString())) {
                    System.out.println("Redirect type do not match");
                    return false;
                }
            } catch (NullPointerException e) {
            }
            try {
                if (!findElement(aliasNameInput).getAttribute("value").contains(data.get("name").toString())) {
                    System.out.println("Name do not match");
                    return false;
                }
            } catch (NullPointerException e) {
            }


            if(data.get("type").toString().equals("Page")){
                try {
                    if (!findElement(selectedTargetLanguage).getAttribute("innerhtml").equals(data.get("language").toString())) {
                        System.out.println("Target language do not match");
                        return false;
                    }
                } catch (NullPointerException e) {
                }
                try {
                    if (!findElement(selectedTargetPage).getAttribute("innerhtml").contains(data.get("page").toString())) {
                        System.out.println("Target page do not match");
                        return false;
                    }
                } catch (NullPointerException e) {
                }

            } else {
                try {
                    if (!findElement(targetURLInput).getAttribute("value").equals(data.get("url").toString())) {
                        System.out.println("Target URL do not match");
                        return false;
                    }
                } catch (NullPointerException e) {
                }
            }


            System.out.println(name+ ": New "+PAGE_NAME+" has been checked");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String publishAlias(JSONObject data, String name) throws InterruptedException {
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

    public String changeAndSubmitAlias(JSONObject data, String name) throws InterruptedException {
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

            try {
                if (!data.get("type_ch").toString().isEmpty()) {
                    findElement(aliasTypeSelect).sendKeys(data.get("type_ch").toString());
                    // Might have to wait for everything to load
                    jsonObj.put("type", data.get("type_ch").toString());
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!data.get("name_ch").toString().isEmpty()) {
                    findElement(aliasNameInput).clear();
                    findElement(aliasNameInput).sendKeys(data.get("name_ch").toString());
                    jsonObj.put("name", data.get("name_ch").toString());
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!data.get("url_ch").toString().isEmpty()) {
                    findElement(targetURLInput).clear();
                    findElement(targetURLInput).sendKeys(data.get("url_ch").toString());
                    jsonObj.put("url", data.get("url_ch").toString());
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!data.get("redirect_ch").toString().isEmpty()) {
                    findElement(redirectTypeSelect).sendKeys(data.get("redirect_ch").toString());
                    jsonObj.put("redirect_name", data.get("redirect_ch").toString());
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!data.get("language_ch").toString().isEmpty()) {
                    findElement(targetLanguageSelect).sendKeys(data.get("language_ch").toString());
                    jsonObj.put("language", data.get("language_ch").toString());
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!data.get("page_ch").toString().isEmpty()) {
                    findElement(targetPageSelect).sendKeys(data.get("page_ch").toString());
                    jsonObj.put("page", data.get("page_ch").toString());
                }
            } catch (NullPointerException e) {
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

    public String revertToLiveAlias(String name) throws InterruptedException {
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

    public Boolean checkAliasCh(JSONObject data, String name) throws InterruptedException {
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

            // Compare field values with entry data
            try{
                if (!data.get("name_ch").toString().isEmpty()){
                    try {
                        if (!findElement(aliasNameInput).getAttribute("value").equals(data.get("name_ch").toString())) {
                            System.out.println("Alias name was not changed");
                            return false;
                        }
                    } catch (NullPointerException e) {
                    }
                } else {
                    try {
                        if (!findElement(aliasNameInput).getAttribute("value").equals(data.get("name").toString())) {
                            System.out.println("Alias name do not match");
                            return false;
                        }
                    } catch (NullPointerException e) {
                    }
                }
                
                if (!data.get("url_ch").toString().isEmpty()){
                    try {
                        if (!findElement(targetURLInput).getAttribute("value").contains(data.get("url_ch").toString())) {
                            System.out.println("url was not changed");
                            return false;
                        }
                    } catch (NullPointerException e) {
                    }
                } else{
                    try {
                        if (!findElement(targetURLInput).getAttribute("value").contains(data.get("url").toString())) {
                            System.out.println("target URL do not match");
                            return false;
                        }
                    } catch (NullPointerException e) {
                    }
                }

                if (!data.get("type_ch").toString().isEmpty()){
                    try {
                        if (!findElement(selectedAliasType).getAttribute("value").equals(data.get("type_ch").toString())) {
                            System.out.println("Type was not changed");
                            return false;
                        }
                    } catch (NullPointerException e) {
                    }
                } else {
                    try {
                        if (!findElement(selectedAliasType).getAttribute("value").equals(data.get("type").toString())) {
                            System.out.println("Type do not match");
                            return false;
                        }
                    } catch (NullPointerException e) {
                    }
                }

                if (!data.get("redirect_ch").toString().isEmpty()){
                    try {
                        if (!findElement(selectedRedirectType).getAttribute("value").equals(data.get("redirect_ch").toString())) {
                            System.out.println("Redirect was not changed");
                            return false;
                        }
                    } catch (NullPointerException e) {
                    }
                } else {
                    try {
                        if (!findElement(selectedRedirectType).getAttribute("value").equals(data.get("redirect").toString())) {
                            System.out.println("Redirect do not match");
                            return false;
                        }
                    } catch (NullPointerException e) {
                    }
                }

                if (!data.get("page_ch").toString().isEmpty()){
                    try {
                        if (!findElement(selectedTargetPage).getAttribute("innerhtml").contains(data.get("page_ch").toString())) {
                            System.out.println("Target page was not changed");
                            return false;
                        }
                    } catch (NullPointerException e) {
                    }
                } else {
                    try {
                        if (!findElement(selectedTargetPage).getAttribute("innerhtml").contains(data.get("page").toString())) {
                            System.out.println("Target page do not match");
                            return false;
                        }
                    } catch (NullPointerException e) {
                    }
                }

            } catch(NullPointerException e){
            }

            System.out.println(name+ ": New "+PAGE_NAME+" has been checked");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String setupAsDeletedAlias(String name) throws InterruptedException {
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

    public String removeAlias(JSONObject data, String name) throws InterruptedException {
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
