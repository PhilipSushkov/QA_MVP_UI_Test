package pageobjects.SiteAdmin.LookupList;

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
 * Created by philipsushkov on 2017-05-05.
 */

public class LookupAdd extends AbstractPageObject {
    private static By moduleTitle, lookupTypeField, lookupTextField, lookupValueField, additionalInfoField, lookupTypeSelect;
    private static By activeChk, saveBtn, revertBtn, cancelBtn, deleteBtn, addNewLink, publishBtn;
    private static By workflowStateSpan, commentsTxt, successMsg, saveAndSubmitBtn, currentContentSpan;
    private static String sPathToFile, sFileJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String PAGE_NAME="Lookup";

    public LookupAdd(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISiteAdmin.getProperty("spanModule_Title"));
        lookupTypeField = By.xpath(propUISiteAdmin.getProperty("input_LookupType"));
        lookupTextField = By.xpath(propUISiteAdmin.getProperty("input_LookupText"));
        lookupValueField = By.xpath(propUISiteAdmin.getProperty("input_LookupValue"));
        additionalInfoField = By.xpath(propUISiteAdmin.getProperty("input_AdditionalInfo"));
        lookupTypeSelect = By.xpath(propUISiteAdmin.getProperty("select_LookupType"));
        activeChk = By.xpath(propUISiteAdmin.getProperty("chk_Active"));
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

        sPathToFile = System.getProperty("user.dir") + propUISiteAdmin.getProperty("dataPath_LookupList");
        sFileJson = propUISiteAdmin.getProperty("json_Lookup");
    }

    public String getTitle() {
        findElement(addNewLink).click();
        waitForElement(moduleTitle);
        String sTitle = getText(moduleTitle);
        findElement(cancelBtn).click();
        return sTitle;
    }

    public String saveLookup(JSONObject data, String name) {
        String lookup_type, lookup_text, lookup_value, additional_info;
        Boolean active;
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

            lookup_type = data.get("lookup_type").toString();
            findElement(lookupTypeField).clear();
            findElement(lookupTypeField).sendKeys(lookup_type);
            jsonObj.put("lookup_type", lookup_type);

            lookup_text = data.get("lookup_text").toString();
            findElement(lookupTextField).clear();
            findElement(lookupTextField).sendKeys(lookup_text);
            jsonObj.put("lookup_text", lookup_text);

            lookup_value = data.get("lookup_value").toString();
            findElement(lookupValueField).clear();
            findElement(lookupValueField).sendKeys(lookup_value);
            jsonObj.put("lookup_value", lookup_value);

            additional_info = data.get("additional_info").toString();
            findElement(additionalInfoField).clear();
            findElement(additionalInfoField).sendKeys(additional_info);
            jsonObj.put("additional_info", additional_info);




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

    public String saveAndSubmitLookup(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        By editBtn = By.xpath("//td[(text()='" + data.get("lookup_text").toString() + "')]/parent::tr/td/input[contains(@id, 'imgEdit')][contains(@id, 'Lookup')]");

        try {
            waitForElement(moduleTitle);

            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            waitForElement(lookupTypeSelect);
            findElement(lookupTypeSelect).sendKeys(data.get("lookup_type").toString());
            Thread.sleep(DEFAULT_PAUSE);

            //String pageUrl = getPageUrl(jsonMain, name);
            //driver.get(pageUrl);
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

    public Boolean checkLookup(JSONObject data, String name) throws InterruptedException {
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
            try {
                if (!findElement(lookupTypeField).getAttribute("value").equals(data.get("lookup_type").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(lookupTextField).getAttribute("value").equals(data.get("lookup_text").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(lookupValueField).getAttribute("value").equals(data.get("lookup_value").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(additionalInfoField).getAttribute("value").equals(data.get("additional_info").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(activeChk).getAttribute("checked").equals(data.get("active").toString())) {
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

    public String publishLookup(JSONObject data, String name) throws InterruptedException {
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

    public String changeAndSubmitLookup(JSONObject data, String name) throws InterruptedException {
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
                if (!data.get("lookup_value_ch").toString().isEmpty()) {
                    findElement(lookupValueField).clear();
                    findElement(lookupValueField).sendKeys(data.get("lookup_value_ch").toString());
                    jsonObj.put("lookup_value", data.get("lookup_value_ch").toString());
                }
            } catch (NullPointerException e) {
            }

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

    public String revertToLiveLookup(String name) throws InterruptedException {
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

    public Boolean checkLookupCh(JSONObject data, String name) throws InterruptedException {
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
            try {
                if (!findElement(lookupTypeField).getAttribute("value").equals(data.get("lookup_type").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(lookupTextField).getAttribute("value").equals(data.get("lookup_text").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(lookupValueField).getAttribute("value").equals(data.get("lookup_value_ch").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(additionalInfoField).getAttribute("value").equals(data.get("additional_info").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(activeChk).getAttribute("checked").equals(data.get("active_ch").toString())) {
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

    public String setupAsDeletedLookup(String name) throws InterruptedException {
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

    public String removeLookup(JSONObject data, String name) throws InterruptedException {
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
