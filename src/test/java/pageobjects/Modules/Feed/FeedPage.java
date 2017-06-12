package pageobjects.Modules.Feed;

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

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;
import pageobjects.PageAdmin.PageAdminList;
import pageobjects.PageAdmin.WorkflowState;
import util.Functions;

import static specs.AbstractSpec.desktopUrl;
import static specs.AbstractSpec.propUIPageAdmin;
import static specs.AbstractSpec.propUIModulesFeed;

/**
 * Created by philipsushkov on 2017-06-12.
 */

public class FeedPage extends AbstractPageObject {
    private static By addNewBtn, backBtn, sectionTitleInput, pageTypeInternalRd, publishBtn, hasContentChk;
    private static By pageTemplateSelect, parentPageSelect, showNavChk, openNewWindChk, saveBtn, workflowStateSpan, currentContentSpan;
    private static By parentUrlSpan, seoNameInput, previewLnk, breadcrumbDiv, commentsTxt, deleteBtn, addNewInput;
    private static By saveAndSubmitBtn;
    private static String sPathToFile, sFilePageJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private static PageAdminList pageAdminList;

    public FeedPage(WebDriver driver) {
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
        previewLnk = By.xpath(propUIPageAdmin.getProperty("lnk_Preview"));
        breadcrumbDiv = By.xpath(propUIPageAdmin.getProperty("div_Breadcrumb"));
        commentsTxt = By.xpath(propUIPageAdmin.getProperty("txtarea_Comments"));
        addNewInput = By.xpath(propUIPageAdmin.getProperty("input_AddNew"));
        currentContentSpan = By.xpath(propUIPageAdmin.getProperty("span_CurrentContent"));

        saveBtn = By.xpath(propUIPageAdmin.getProperty("btn_Save"));
        deleteBtn = By.xpath(propUIPageAdmin.getProperty("btn_Delete"));
        publishBtn = By.xpath(propUIPageAdmin.getProperty("btn_Publish"));
        backBtn = By.xpath(propUIPageAdmin.getProperty("btn_Back"));
        saveAndSubmitBtn = By.xpath(propUIPageAdmin.getProperty("btn_SaveAndSubmit"));

        sPathToFile = System.getProperty("user.dir") + propUIModulesFeed.getProperty("dataPath_ModulesFeed");
        sFilePageJson = propUIModulesFeed.getProperty("json_FeedPageProp");

        parser = new JSONParser();
        pageAdminList = new PageAdminList(driver);;
    }

    public String createFeedPage(JSONObject pagesDataObj, String pageName) throws InterruptedException {
        String your_page_url, parent_page, page_type;

        waitForElement(addNewBtn);
        findElement(addNewBtn).click();
        waitForElement(backBtn);

        try {
            findElement(sectionTitleInput).sendKeys(pageName);

            page_type = pagesDataObj.get("page_type").toString();
            if (pagesDataObj.get("page_type").toString().equals("Internal")) {
                findElement(pageTypeInternalRd).click();
                Thread.sleep(DEFAULT_PAUSE);
                findElement(pageTemplateSelect).sendKeys(pagesDataObj.get("page_template").toString());
            } else {
                System.out.println("Page type in not defined. May lead to incorrect test implementation.");
            }

            parent_page = pagesDataObj.get("parent_page").toString();
            findElement(parentPageSelect).sendKeys(parent_page);

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

            Thread.sleep(DEFAULT_PAUSE);

            findElement(saveBtn).click();
            waitForElement(deleteBtn);

            Thread.sleep(DEFAULT_PAUSE);

            // Write page parameters to json
            JSONObject jsonObjectNew = new JSONObject();

            try {
                jsonObjectNew = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePageJson));
            } catch (ParseException e) {
                jsonObjectNew = (JSONObject) parser.parse("{}");
            }

            JSONObject page = new JSONObject();
            your_page_url = findElement(parentUrlSpan).getText() + findElement(seoNameInput).getAttribute("value");
            page.put("your_page_url", your_page_url);
            page.put("parent_page", parent_page);
            page.put("page_type", page_type);

            page.put("workflow_state", WorkflowState.IN_PROGRESS.state());

            URL pageURL = new URL(getUrl());
            String[] params = pageURL.getQuery().split("&");
            JSONObject jsonURLQuery = new JSONObject();
            for (String param:params) {
                jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);
            }
            page.put("url_query", jsonURLQuery);
            jsonObjectNew.put(pageName, page);

            try {
                FileWriter file = new FileWriter(sPathToFile + sFilePageJson);
                file.write(jsonObjectNew.toJSONString().replace("\\", ""));
                file.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(pageName+ ": New Feed Page has been created");
            return findElement(workflowStateSpan).getText();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public String getPageUrl (JSONObject obj, String pageName) {
        String  sItemID = JsonPath.read(obj, "$.['"+pageName+"'].url_query.ItemID");
        String  sLanguageId = JsonPath.read(obj, "$.['"+pageName+"'].url_query.LanguageId");
        String  sSectionId = JsonPath.read(obj, "$.['"+pageName+"'].url_query.SectionId");
        return desktopUrl.toString()+"default.aspx?ItemID="+sItemID+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }
}
