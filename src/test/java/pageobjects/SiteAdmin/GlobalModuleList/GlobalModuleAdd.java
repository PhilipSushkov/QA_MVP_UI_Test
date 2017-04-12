package pageobjects.SiteAdmin.GlobalModuleList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.Select;
import pageobjects.PageAdmin.WorkflowState;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import static specs.AbstractSpec.desktopUrl;
import static specs.AbstractSpec.propUISiteAdmin;

/**
 * Created by philipsushkov on 2017-04-11.
 */

public class GlobalModuleAdd extends AbstractPageObject {
    private static By moduleTitle, moduleTitleField, moduleDefinitionSelect, moduleTypeSelect, regionNameSelect;
    private static By saveBtn, cancelBtn, deleteBtn, addNewLink, legacyModulesChk, publishBtn, activeChk;
    private static By revertBtn, workflowStateSpan, commentsTxt, successMsg, saveAndSubmitBtn;
    private static String sPathToFile, sFileJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String PAGE_NAME="Global Module";

    public GlobalModuleAdd(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISiteAdmin.getProperty("spanSection_Title"));
        saveBtn = By.xpath(propUISiteAdmin.getProperty("btn_Save"));
        cancelBtn = By.xpath(propUISiteAdmin.getProperty("btn_Cancel"));
        deleteBtn = By.xpath(propUISiteAdmin.getProperty("btn_Delete"));
        publishBtn = By.xpath(propUISiteAdmin.getProperty("btn_Publish"));
        addNewLink = By.xpath(propUISiteAdmin.getProperty("input_AddNew"));
        moduleTitleField = By.xpath(propUISiteAdmin.getProperty("input_ModuleTitle"));
        moduleDefinitionSelect = By.xpath(propUISiteAdmin.getProperty("select_ModuleDefinition"));
        moduleTypeSelect = By.xpath(propUISiteAdmin.getProperty("select_ModuleType"));
        regionNameSelect = By.xpath(propUISiteAdmin.getProperty("select_RegionName"));
        legacyModulesChk = By.xpath(propUISiteAdmin.getProperty("chk_LegacyModules"));
        revertBtn = By.xpath(propUISiteAdmin.getProperty("btn_Revert"));
        workflowStateSpan = By.xpath(propUISiteAdmin.getProperty("select_WorkflowState"));
        commentsTxt = By.xpath(propUISiteAdmin.getProperty("txtarea_Comments"));
        successMsg = By.xpath(propUISiteAdmin.getProperty("msg_Success"));
        saveAndSubmitBtn = By.xpath(propUISiteAdmin.getProperty("btn_SaveAndSubmit"));
        activeChk = By.xpath(propUISiteAdmin.getProperty("chk_Active"));

        parser = new JSONParser();

        sPathToFile = System.getProperty("user.dir") + propUISiteAdmin.getProperty("dataPath_GlobalModuleList");
        sFileJson = propUISiteAdmin.getProperty("json_GlobalModules");
    }

    public String getTitle() {
        findElement(addNewLink).click();
        waitForElement(moduleTitle);
        String sTitle = getText(moduleTitle);
        findElement(cancelBtn).click();
        return sTitle;
    }

    public String saveGlobalModule(JSONObject data, String name) {
        String module_title, module_definition, module_type, region_name;
        Boolean include_legacy_modules;
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

            module_title = data.get("module_title").toString();
            findElement(moduleTitleField).sendKeys(module_title);
            jsonObj.put("module_title", module_title);

            module_definition = data.get("module_definition").toString();
            findElement(moduleDefinitionSelect).sendKeys(module_definition);
            jsonObj.put("module_definition", module_definition);

            module_type = data.get("module_type").toString().split(";")[0];
            findElement(moduleTypeSelect).sendKeys(module_type);
            jsonObj.put("module_type", module_type);

            region_name = data.get("region_name").toString();
            findElement(regionNameSelect).sendKeys(region_name);
            jsonObj.put("region_name", region_name);

            include_legacy_modules = Boolean.parseBoolean(data.get("include_legacy_modules").toString());
            jsonObj.put("include_legacy_modules", Boolean.parseBoolean(data.get("include_legacy_modules").toString()));
            if (include_legacy_modules) {
                if (!Boolean.parseBoolean(findElement(legacyModulesChk).getAttribute("checked"))) {
                    findElement(legacyModulesChk).click();
                } else {
                }
            } else {
                if (!Boolean.parseBoolean(findElement(legacyModulesChk).getAttribute("checked"))) {
                } else {
                    findElement(legacyModulesChk).click();
                }
            }

            findElement(saveBtn).click();
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(successMsg);

            // Save Global Module Url
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

    public String saveAndSubmitGlobalModule(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        By editBtn = By.xpath("//td/span[(text()='" + data.get("module_title").toString() + "')]/parent::td/parent::tr/td/input[contains(@id, 'btnEdit')][contains(@id, 'ModuleInstances')]");
        System.out.println(findElement(editBtn).getAttribute("innerText"));

        try {
            waitForElement(moduleTitle);

            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            //findElement(editBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(commentsTxt);
            findElement(commentsTxt).sendKeys(data.get("comment").toString());
            findElement(saveAndSubmitBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(editBtn);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            JSONObject jsonObj = (JSONObject) jsonMain.get(name);

            jsonObj.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            jsonObj.put("deleted", "false");

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

    public Boolean checkGlobalModule(JSONObject data, String name) throws InterruptedException {
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
                if (findElement(moduleTitleField).toString().equals(data.get("module_title").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!new Select(findElement(moduleDefinitionSelect)).getFirstSelectedOption().getText().equals(data.get("module_definition").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!new Select(findElement(moduleTypeSelect)).getFirstSelectedOption().getText().equals(data.get("module_type").toString().split(";")[1])) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!new Select(findElement(regionNameSelect)).getFirstSelectedOption().getText().equals(data.get("region_name").toString())) {
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

    public String publishGlobalModule(JSONObject data, String name) throws InterruptedException {
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

    public String changeAndSubmitGlobalModule(JSONObject data, String name) throws InterruptedException {
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
            waitForElement(saveBtn);

            try {
                if (!data.get("region_name_ch").toString().isEmpty()) {
                    findElement(regionNameSelect).sendKeys(data.get("region_name_ch").toString());
                    findElement(commentsTxt).clear();
                    findElement(commentsTxt).sendKeys(data.get("comment_ch").toString());
                }
            } catch (NullPointerException e) {
            }

            try {
                // Save Active checkbox
                if (Boolean.parseBoolean(data.get("active_ch").toString())) {
                    if (!Boolean.parseBoolean(findElement(activeChk).getAttribute("checked"))) {
                        findElement(activeChk).click();
                        findElement(commentsTxt).clear();
                        findElement(commentsTxt).sendKeys(data.get("comment_ch").toString());
                    } else {
                    }
                } else {
                    if (!Boolean.parseBoolean(findElement(activeChk).getAttribute("checked"))) {
                    } else {
                        findElement(activeChk).click();
                        findElement(commentsTxt).clear();
                        findElement(commentsTxt).sendKeys(data.get("comment_ch").toString());
                    }
                }
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

    public String revertToLiveGlobalModule(String name) throws InterruptedException {
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

    public String getPageUrl (JSONObject obj, String name) {
        String  sItemID = JsonPath.read(obj, "$.['"+name+"'].url_query.ItemID");
        String  sLanguageId = JsonPath.read(obj, "$.['"+name+"'].url_query.LanguageId");
        String  sSectionId = JsonPath.read(obj, "$.['"+name+"'].url_query.SectionId");
        return desktopUrl.toString()+"default.aspx?ItemID="+sItemID+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }

}
