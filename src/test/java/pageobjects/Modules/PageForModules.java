package pageobjects.Modules;

import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;
import pageobjects.PageAdmin.PageAdminList;
import pageobjects.PageAdmin.WorkflowState;
import util.Functions;

import static specs.AbstractSpec.*;

/**
 * Created by philipsushkov on 2017-06-12.
 */

public class PageForModules extends AbstractPageObject {
    private static By addNewBtn, backBtn, sectionTitleInput, pageTypeInternalRd, publishBtn, hasContentChk;
    private static By pageTemplateSelect, parentPageSelect, showNavChk, openNewWindChk, saveBtn, workflowStateSpan;
    private static By parentUrlSpan, seoNameInput, commentsTxt, deleteBtn;
    private static By saveAndSubmitBtn, pageLayoutSelect, globalModulesChk;
    private static String sPathToFile, sFilePageJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private static PageAdminList pageAdminList;

    public PageForModules(WebDriver driver) {
        super(driver);

        addNewBtn = By.xpath(propUIPageAdmin.getProperty("btn_AddNew"));
        sectionTitleInput = By.xpath(propUIPageAdmin.getProperty("input_SectionTitle"));
        pageTypeInternalRd = By.xpath(propUIPageAdmin.getProperty("rd_PageTypeInt"));
        pageTemplateSelect = By.xpath(propUIPageAdmin.getProperty("select_PageTemplate"));
        parentPageSelect = By.xpath(propUIPageAdmin.getProperty("select_ParentPage"));
        hasContentChk = By.xpath(propUIPageAdmin.getProperty("chk_HasContent"));
        showNavChk = By.xpath(propUIPageAdmin.getProperty("chk_ShowInNav"));
        openNewWindChk = By.xpath(propUIPageAdmin.getProperty("chk_OpenInNewWindow"));
        workflowStateSpan = By.xpath(propUIPageAdmin.getProperty("select_WorkflowState"));
        parentUrlSpan = By.xpath(propUIPageAdmin.getProperty("span_YourPageUrl"));
        seoNameInput = By.xpath(propUIPageAdmin.getProperty("input_SeoName"));
        commentsTxt = By.xpath(propUIPageAdmin.getProperty("txtarea_Comments"));
        pageLayoutSelect = By.xpath(propUIPageAdmin.getProperty("select_PageLayout"));
        globalModulesChk = By.xpath(propUIPageAdmin.getProperty("chk_GlobalModuleSet"));

        saveBtn = By.xpath(propUIPageAdmin.getProperty("btn_Save"));
        deleteBtn = By.xpath(propUIPageAdmin.getProperty("btn_Delete"));
        publishBtn = By.xpath(propUIPageAdmin.getProperty("btn_Publish"));
        backBtn = By.xpath(propUIPageAdmin.getProperty("btn_Back"));
        saveAndSubmitBtn = By.xpath(propUIPageAdmin.getProperty("btn_SaveAndSubmit"));

        sPathToFile = System.getProperty("user.dir") + propUIModules.getProperty("dataPath_Modules");
        sFilePageJson = propUIModules.getProperty("json_PagesProp");

        parser = new JSONParser();
        pageAdminList = new PageAdminList(driver);;
    }

    public String savePage(JSONObject pagesDataObj, String pageName) throws InterruptedException {
        String your_page_url, parent_page, page_type;

        waitForElement(addNewBtn);
        findElement(addNewBtn).click();
        waitForElement(backBtn);

        try {
            findElement(sectionTitleInput).sendKeys(pageName);

            try {
                page_type = pagesDataObj.get("page_type").toString();
                if (pagesDataObj.get("page_type").toString().equals("Internal")) {
                    findElement(pageTypeInternalRd).click();
                    Thread.sleep(DEFAULT_PAUSE);
                    findElement(pageTemplateSelect).sendKeys(pagesDataObj.get("page_template").toString());
                } else {
                    System.out.println("Page type in not defined. May lead to incorrect test implementation.");
                }
            } catch (NullPointerException e) {
                page_type = "Internal";
                findElement(pageTypeInternalRd).click();
                Thread.sleep(DEFAULT_PAUSE);
                findElement(pageTemplateSelect).sendKeys("Blank Template");
            }

            parent_page = pagesDataObj.get("parent_page").toString();
            findElement(parentPageSelect).sendKeys(parent_page);

            try {
                if (Boolean.parseBoolean(pagesDataObj.get("show_in_navigation").toString())) {
                    if (!Boolean.parseBoolean(findElement(showNavChk).getAttribute("checked"))) {
                        findElement(showNavChk).click();
                    } else {
                    }
                } else {
                    if (!Boolean.parseBoolean(findElement(showNavChk).getAttribute("checked"))) {
                    } else {
                        findElement(showNavChk).click();
                    }
                }
            } catch (NullPointerException e) {
            }

            try {
                if (Boolean.parseBoolean(pagesDataObj.get("open_in_new_window").toString())) {
                    if (!Boolean.parseBoolean(findElement(openNewWindChk).getAttribute("checked"))) {
                        findElement(openNewWindChk).click();
                    } else {
                    }
                } else {
                    if (!Boolean.parseBoolean(findElement(openNewWindChk).getAttribute("checked"))) {
                    } else {
                        findElement(openNewWindChk).click();
                    }
                }
            } catch (NullPointerException e) {
            }


            Thread.sleep(DEFAULT_PAUSE);

            findElement(saveBtn).click();
            waitForElement(deleteBtn);

            Thread.sleep(DEFAULT_PAUSE);

            // Write page parameters to json
            JSONObject jsonObj = new JSONObject();

            try {
                jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            } catch (ParseException e) {
                jsonObj = (JSONObject) parser.parse("{}");
            }

            JSONObject page = new JSONObject();
            your_page_url = findElement(parentUrlSpan).getText() + findElement(seoNameInput).getAttribute("value");
            page.put("your_page_url", your_page_url);
            page.put("parent_page", parent_page);
            page.put("page_type", page_type);

            page.put("workflow_state", WorkflowState.IN_PROGRESS.state());
            page.put("active", "true");
            page.put("show_in_navigation", "true");

            URL pageURL = new URL(getUrl());
            String[] params = pageURL.getQuery().split("&");
            JSONObject jsonURLQuery = new JSONObject();
            for (String param:params) {
                jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);
            }
            page.put("url_query", jsonURLQuery);
            jsonObj.put(pageName, page);

            try {
                FileWriter file = new FileWriter(sPathToFile + sFilePageJson);
                file.write(jsonObj.toJSONString().replace("\\", ""));
                file.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(pageName+ ": New "+pageName+" Page has been created");
            return findElement(workflowStateSpan).getText();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String saveAndSubmitPage(JSONObject pagesDataObj, String pageName) throws InterruptedException {
        String page_layout;

        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));

            String pageUrl = getPageUrl(jsonObj, pageName);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(commentsTxt);

            try {
                if (Boolean.parseBoolean(pagesDataObj.get("has_content").toString())) {
                    if (!Boolean.parseBoolean(findElement(hasContentChk).getAttribute("checked"))) {
                        findElement(hasContentChk).click();
                    } else {
                    }
                } else {
                    if (!Boolean.parseBoolean(findElement(hasContentChk).getAttribute("checked"))) {
                    } else {
                        findElement(hasContentChk).click();
                    }
                }
            } catch (NullPointerException e) {
            }

            try {
                page_layout = pagesDataObj.get("page_layout").toString();
            } catch (NullPointerException e) {
                page_layout = "Three Column Layout";
            }

            findElement(pageLayoutSelect).sendKeys(page_layout);

            uncheckGlobalModuleSettings();

            findElement(commentsTxt).sendKeys(pagesDataObj.get("comment").toString());
            findElement(saveAndSubmitBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            JSONObject page = (JSONObject) jsonObj.get(pageName);

            page.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            page.put("deleted", "false");
            page.put("has_content", "true");

            jsonObj.put(pageName, page);

            FileWriter file = new FileWriter(sPathToFile + sFilePageJson);
            file.write(jsonObj.toJSONString().replace("\\", ""));
            file.flush();

            System.out.println(pageName+ ": New "+pageName+" Page has been submitted");
            return findElement(workflowStateSpan).getText();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String publishPage(String pageName) throws InterruptedException {
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));

            String pageUrl = getPageUrl(jsonObj, pageName);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(publishBtn);
            findElement(publishBtn).click();
            Thread.sleep(DEFAULT_PAUSE*2);

            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            JSONObject pageObj = (JSONObject) jsonObj.get(pageName);

            pageObj.put("workflow_state", WorkflowState.LIVE.state());
            pageObj.put("deleted", "false");

            jsonObj.put(pageName, pageObj);

            FileWriter file = new FileWriter(sPathToFile + sFilePageJson);
            file.write(jsonObj.toJSONString().replace("\\", ""));
            file.flush();


            Thread.sleep(DEFAULT_PAUSE*2);
            driver.navigate().refresh();

            System.out.println(pageName+ ": New "+pageName+" Page has been published");
            return findElement(workflowStateSpan).getText();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String getPageUrl(JSONObject obj, String pageName) {
        String  sItemID = JsonPath.read(obj, "$.['"+pageName+"'].url_query.ItemID");
        String  sLanguageId = JsonPath.read(obj, "$.['"+pageName+"'].url_query.LanguageId");
        String  sSectionId = JsonPath.read(obj, "$.['"+pageName+"'].url_query.SectionId");
        return desktopUrl.toString()+"default.aspx?ItemID="+sItemID+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }

    private void uncheckGlobalModuleSettings() throws InterruptedException {
        List<WebElement> elementsChk = findElements(globalModulesChk);
        for (WebElement elementChk:elementsChk) {
            if (Boolean.parseBoolean(elementChk.getAttribute("checked"))) {
                elementChk.click();
                Thread.sleep(DEFAULT_PAUSE/2);
            }
        }

    }

}
