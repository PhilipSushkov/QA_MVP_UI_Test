package pageobjects.SiteAdmin.ModuleDefinitionList;

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
 * Created by philipsushkov on 2017-04-25.
 */

public class ModuleDefinitionAdd extends AbstractPageObject {
    private static By moduleTitle, friendlyNameField, sourceFileField, qualifiedPathField, ContentTypeField;
    private static By linkToEditPageSelect, linkToListPageSelect, linkToAdminPageSelect, linkToAdminTextField;
    private static By useDefaultRb, useCustomRb, activeChk, viewStateChk;
    private static By saveBtn, cancelBtn, deleteBtn, addNewLink, publishBtn, backBtn;
    private static By revertBtn, workflowStateSpan, commentsTxt, successMsg, saveAndSubmitBtn, currentContentSpan;
    private static String sPathToFile, sFileJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String PAGE_NAME="Module Definition";

    public ModuleDefinitionAdd(WebDriver driver) {
        super(driver);

        moduleTitle = By.xpath(propUISiteAdmin.getProperty("spanModule_Title"));
        friendlyNameField = By.xpath(propUISiteAdmin.getProperty("input_FriendlyName"));
        sourceFileField = By.xpath(propUISiteAdmin.getProperty("input_SourceFile"));
        qualifiedPathField = By.xpath(propUISiteAdmin.getProperty("input_QualifiedPath"));
        ContentTypeField = By.xpath(propUISiteAdmin.getProperty("input_ContentType"));
        linkToEditPageSelect = By.xpath(propUISiteAdmin.getProperty("select_LinkToEditPage"));
        linkToListPageSelect = By.xpath(propUISiteAdmin.getProperty("select_LinkToListPage"));
        linkToAdminPageSelect = By.xpath(propUISiteAdmin.getProperty("select_LinkToAdminPage"));
        linkToAdminTextField = By.xpath(propUISiteAdmin.getProperty("input_LinkToAdminText"));

        useDefaultRb = By.xpath(propUISiteAdmin.getProperty("rb_UseDefault"));
        useCustomRb = By.xpath(propUISiteAdmin.getProperty("rb_UseCustom"));
        activeChk = By.xpath(propUISiteAdmin.getProperty("chk_Active"));
        viewStateChk = By.xpath(propUISiteAdmin.getProperty("chk_ViewState"));

        backBtn = By.xpath(propUISiteAdmin.getProperty("btn_Back"));
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

        sPathToFile = System.getProperty("user.dir") + propUISiteAdmin.getProperty("dataPath_ModuleDefinitionList");
        sFileJson = propUISiteAdmin.getProperty("json_ModuleDefinition");
    }

    public String getTitle() {
        findElement(addNewLink).click();
        waitForElement(moduleTitle);
        String sTitle = getText(moduleTitle);
        findElement(cancelBtn).click();
        return sTitle;
    }

    public String saveModuleDefinition(JSONObject data, String name) {
        String friendly_name, source_file, qualified_path, content_type, link_to_edit_page;
        String link_to_list_page, link_to_admin_page, link_to_admin_text, user_control;
        Boolean active, enable_viewState;
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

            friendly_name = data.get("friendly_name").toString();
            findElement(friendlyNameField).sendKeys(friendly_name);
            jsonObj.put("module_title", friendly_name);

            source_file = data.get("source_file").toString();
            findElement(sourceFileField).sendKeys(source_file);
            jsonObj.put("source_file", source_file);

            qualified_path = data.get("qualified_path").toString();
            findElement(qualifiedPathField).sendKeys(qualified_path);
            jsonObj.put("qualified_path", qualified_path);

            content_type = data.get("content_type").toString();
            if (!content_type.isEmpty()) {
                findElement(ContentTypeField).sendKeys(content_type);
                jsonObj.put("content_type", content_type);
            }

            link_to_edit_page = data.get("link_to_edit_page").toString();
            if (!link_to_edit_page.isEmpty()) {
                findElement(linkToEditPageSelect).sendKeys(link_to_edit_page);
                jsonObj.put("link_to_edit_page", link_to_edit_page);
            } else {
                jsonObj.put("link_to_edit_page", new Select(findElement(linkToEditPageSelect)).getFirstSelectedOption().getText());
            }

            link_to_list_page = data.get("link_to_list_page").toString();
            if (!link_to_list_page.isEmpty()) {
                findElement(linkToListPageSelect).sendKeys(link_to_list_page);
                jsonObj.put("link_to_list_page", link_to_list_page);
            } else {
                jsonObj.put("link_to_list_page", new Select(findElement(linkToListPageSelect)).getFirstSelectedOption().getText());
            }

            link_to_admin_page = data.get("link_to_admin_page").toString();
            if (!link_to_admin_page.isEmpty()) {
                findElement(linkToAdminPageSelect).sendKeys(link_to_admin_page);
                jsonObj.put("link_to_admin_page", link_to_admin_page);
            } else {
                jsonObj.put("link_to_admin_page", new Select(findElement(linkToAdminPageSelect)).getFirstSelectedOption().getText());
            }

            link_to_admin_text = data.get("link_to_admin_text").toString();
            if (!link_to_admin_text.isEmpty()) {
                findElement(linkToAdminTextField).sendKeys(link_to_admin_text);
                jsonObj.put("link_to_admin_text", link_to_admin_text);
            }

            user_control = data.get("user_control").toString();
            jsonObj.put("user_control", user_control);
            if (user_control.equals("Use Default")) {
                findElement(useDefaultRb).click();
            } else if (user_control.equals("Custom Layout")) {
                findElement(useCustomRb).click();
            }

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

            enable_viewState = Boolean.parseBoolean(data.get("enable_viewState").toString());
            jsonObj.put("enable_viewState", Boolean.parseBoolean(data.get("enable_viewState").toString()));
            if (enable_viewState) {
                if (!Boolean.parseBoolean(findElement(viewStateChk).getAttribute("checked"))) {
                    findElement(viewStateChk).click();
                } else {
                }
            } else {
                if (!Boolean.parseBoolean(findElement(viewStateChk).getAttribute("checked"))) {
                } else {
                    findElement(viewStateChk).click();
                }
            }


            findElement(saveBtn).click();
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(successMsg);

            // Save ModuleDefinition Url
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

    public String saveAndSubmitModuleDefinition(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();

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

            waitForElement(moduleTitle);
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

    public Boolean checkModuleDefinition(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonName = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
                jsonName = (JSONObject) jsonMain.get(name);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(commentsTxt);

            // Compare field values with entry data
            try {
                if (findElement(friendlyNameField).toString().equals(data.get("friendly_name").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (findElement(sourceFileField).toString().equals(data.get("source_file").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (findElement(qualifiedPathField).toString().equals(data.get("qualified_path").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (findElement(ContentTypeField).toString().equals(data.get("content_type").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!new Select(findElement(linkToEditPageSelect)).getFirstSelectedOption().getText().equals(jsonName.get("link_to_edit_page").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!new Select(findElement(linkToListPageSelect)).getFirstSelectedOption().getText().equals(jsonName.get("link_to_list_page").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!new Select(findElement(linkToAdminPageSelect)).getFirstSelectedOption().getText().equals(jsonName.get("link_to_admin_page").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (findElement(linkToAdminTextField).toString().equals(data.get("link_to_admin_text").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (Boolean.parseBoolean(findElement(useDefaultRb).getAttribute("checked"))) {
                    if (!data.get("user_control").toString().equals("Use Default")) {
                        return false;
                    }
                } else if (Boolean.parseBoolean(findElement(useCustomRb).getAttribute("checked"))) {
                    if (!data.get("user_control").toString().equals("Use Custom Layout")) {
                        return false;
                    }
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(activeChk).getAttribute("checked").equals(data.get("active").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(viewStateChk).getAttribute("checked").equals(data.get("enable_viewState").toString())) {
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

    public String publishModuleDefinition(JSONObject data, String name) throws InterruptedException {
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

    public String changeAndSubmitModuleDefinition(JSONObject data, String name) throws InterruptedException {
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
                if (!data.get("source_file_ch").toString().isEmpty()) {
                    findElement(sourceFileField).sendKeys(data.get("source_file_ch").toString());
                    jsonObj.put("source_file", data.get("source_file_ch").toString());
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!data.get("link_to_edit_page_ch").toString().isEmpty()) {
                    findElement(linkToEditPageSelect).sendKeys(data.get("link_to_edit_page_ch").toString());
                    jsonObj.put("link_to_edit_page", data.get("link_to_edit_page_ch").toString());
                } else {
                    findElement(linkToEditPageSelect).sendKeys(new Select(findElement(linkToEditPageSelect)).getOptions().get(0).getText());
                    jsonObj.put("link_to_edit_page", new Select(findElement(linkToEditPageSelect)).getOptions().get(0).getText());
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

    public String revertToLiveModuleDefinition(JSONObject data, String name) throws InterruptedException {
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

                jsonObj.put("link_to_edit_page", data.get("link_to_edit_page").toString());
                jsonObj.put("link_to_list_page", data.get("link_to_list_page").toString());
                jsonObj.put("link_to_admin_page", data.get("link_to_admin_page").toString());

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

    public Boolean checkModuleDefinitionCh(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonName = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
                jsonName = (JSONObject) jsonMain.get(name);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(commentsTxt);

            // Compare field values with entry data
            try {
                if (findElement(friendlyNameField).toString().equals(data.get("friendly_name").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (findElement(sourceFileField).toString().equals(data.get("source_file_ch").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (findElement(qualifiedPathField).toString().equals(data.get("qualified_path").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (findElement(ContentTypeField).toString().equals(data.get("content_type").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!new Select(findElement(linkToEditPageSelect)).getFirstSelectedOption().getText().equals(data.get("link_to_edit_page_ch").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!new Select(findElement(linkToListPageSelect)).getFirstSelectedOption().getText().equals(data.get("link_to_list_page").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!new Select(findElement(linkToAdminPageSelect)).getFirstSelectedOption().getText().equals(data.get("link_to_admin_page").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (findElement(linkToAdminTextField).toString().equals(data.get("link_to_admin_text").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (Boolean.parseBoolean(findElement(useDefaultRb).getAttribute("checked"))) {
                    if (!data.get("user_control").toString().equals("Use Default")) {
                        return false;
                    }
                } else if (Boolean.parseBoolean(findElement(useCustomRb).getAttribute("checked"))) {
                    if (!data.get("user_control").toString().equals("Use Custom Layout")) {
                        return false;
                    }
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(activeChk).getAttribute("checked").equals(data.get("active").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(viewStateChk).getAttribute("checked").equals(data.get("enable_viewState").toString())) {
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

    public String setupAsDeletedModuleDefinition(String name) throws InterruptedException {
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

    public String removeModuleDefinition(JSONObject data, String name) throws InterruptedException {
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
