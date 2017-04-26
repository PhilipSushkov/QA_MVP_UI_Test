package pageobjects.SiteAdmin.CssFileList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
 * Created by philipsushkov on 2017-04-26.
 */

public class CssFileAdd extends AbstractPageObject {
    private static By moduleTitle;
    private static By saveBtn, cancelBtn, deleteBtn, addNewLink, publishBtn, activeChk;
    private static By revertBtn, workflowStateSpan, commentsTxt, successMsg, saveAndSubmitBtn, currentContentSpan;
    private static String sPathToFile, sFileJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String PAGE_NAME="CSS File";

    public CssFileAdd(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISiteAdmin.getProperty("spanModule_Title"));
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
        activeChk = By.xpath(propUISiteAdmin.getProperty("chk_Active"));
        currentContentSpan = By.xpath(propUISiteAdmin.getProperty("span_CurrentContent"));

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

    public String saveCssFile(JSONObject data, String name) {
        String module_title, module_definition, module_type, region_name;
        Boolean include_legacy_modules;
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonMain = new JSONObject();

        waitForElement(addNewLink);
        findElement(addNewLink).click();
        waitForElement(saveBtn);

        System.out.println(data.get("css_name").toString());
        System.out.println(data.get("css_body").toString());

        /*
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
        */

        return WorkflowState.IN_PROGRESS.state();
    }

    public String getPageUrl (JSONObject obj, String name) {
        String  sItemID = JsonPath.read(obj, "$.['"+name+"'].url_query.ItemID");
        String  sLanguageId = JsonPath.read(obj, "$.['"+name+"'].url_query.LanguageId");
        String  sSectionId = JsonPath.read(obj, "$.['"+name+"'].url_query.SectionId");
        return desktopUrl.toString()+"default.aspx?ItemID="+sItemID+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }

}
