package pageobjects.PageAdmin;

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
import util.Functions;

import static specs.AbstractSpec.desktopUrl;
import static specs.AbstractSpec.propUIPageAdmin;

/**
 * Created by philipsushkov on 2017-01-24.
 */
public class PageAdd extends AbstractPageObject {
    private static By addNewBtn, backBtn, sectionTitleInput, pageTypeInternalRd, pageTypeExternalRd, externalURLInput;
    private static By pageTemplateSelect, parentPageSelect, showNavChk, openNewWindChk, saveBtn, workflowStateSpan;
    private static By revertBtn, parentUrlSpan, seoNameInput, previewLnk, breadcrumbDiv;
    private static String sPathToFile, sDataFileJson, sDataFilePagesJson, sNewPageName, you_page_url, parent_page;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 1500;

    public PageAdd(WebDriver driver) {
        super(driver);
        addNewBtn = By.xpath(propUIPageAdmin.getProperty("btn_AddNew"));
        backBtn = By.xpath(propUIPageAdmin.getProperty("btn_Back"));
        sectionTitleInput = By.xpath(propUIPageAdmin.getProperty("input_SectionTitle"));
        pageTypeInternalRd = By.xpath(propUIPageAdmin.getProperty("rd_PageTypeInt"));
        pageTypeExternalRd = By.xpath(propUIPageAdmin.getProperty("rd_PageTypeExt"));
        externalURLInput = By.xpath(propUIPageAdmin.getProperty("input_ExternalURL"));
        pageTemplateSelect = By.xpath(propUIPageAdmin.getProperty("select_PageTemplate"));
        parentPageSelect = By.xpath(propUIPageAdmin.getProperty("select_ParentPage"));
        showNavChk = By.xpath(propUIPageAdmin.getProperty("chk_ShowInNav"));
        openNewWindChk = By.xpath(propUIPageAdmin.getProperty("chk_OpenInNewWindow"));
        saveBtn = By.xpath(propUIPageAdmin.getProperty("btn_Save"));
        workflowStateSpan = By.xpath(propUIPageAdmin.getProperty("select_WorkflowState"));
        revertBtn = By.xpath(propUIPageAdmin.getProperty("btn_Revert"));
        parentUrlSpan = By.xpath(propUIPageAdmin.getProperty("span_YourPageUrl"));
        seoNameInput = By.xpath(propUIPageAdmin.getProperty("input_SeoName"));
        previewLnk = By.xpath(propUIPageAdmin.getProperty("lnk_Preview"));
        breadcrumbDiv = By.xpath(propUIPageAdmin.getProperty("div_Breadcrumb"));

        sPathToFile = System.getProperty("user.dir") + propUIPageAdmin.getProperty("dataPath_PageAdmin");
        sDataFileJson = propUIPageAdmin.getProperty("json_CreatePageData");
        sDataFilePagesJson = propUIPageAdmin.getProperty("json_PagesProp");

        parser = new JSONParser();
    }

    public String createNewPage(String pageName) throws InterruptedException {

        waitForElement(addNewBtn);
        findElement(addNewBtn).click();
        waitForElement(backBtn);

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONObject pagesDataObj = (JSONObject) jsonObject.get(pageName);

            sNewPageName = pageName;

            findElement(sectionTitleInput).sendKeys(sNewPageName);

            if (pagesDataObj.get("page_type").toString().equals("Internal")) {
                findElement(pageTypeInternalRd).click();
                Thread.sleep(DEFAULT_PAUSE);
                findElement(pageTemplateSelect).sendKeys(pagesDataObj.get("page_template").toString());
            } else if (pagesDataObj.get("page_type").toString().equals("External")) {
                findElement(pageTypeExternalRd).click();
                Thread.sleep(DEFAULT_PAUSE);
                findElement(externalURLInput).sendKeys(pagesDataObj.get("external_url").toString());
            } else {
                System.out.println("Page type in not defined. May lead to incorrect test implementation.");
            }

            parent_page = pagesDataObj.get("parent_page").toString();
            findElement(parentPageSelect).sendKeys(parent_page);

            if (Boolean.parseBoolean(pagesDataObj.get("show_in_navigation").toString())) {
                if (!Boolean.parseBoolean(findElement(showNavChk).getAttribute("checked"))) {
                    //System.out.println("show_in_navigation - true, Show in Navigation - unchecked");
                    findElement(showNavChk).click();
                } else {
                    //System.out.println("show_in_navigation - true, Show in Navigation - checked");
                }
            } else {
                if (!Boolean.parseBoolean(findElement(showNavChk).getAttribute("checked"))) {
                    //System.out.println("show_in_navigation - false, Show in Navigation - unchecked");
                } else {
                    //System.out.println("show_in_navigation - false, Show in Navigation - checked");
                    findElement(showNavChk).click();
                }
            }

            if (Boolean.parseBoolean(pagesDataObj.get("open_in_new_window").toString())) {
                if (!Boolean.parseBoolean(findElement(openNewWindChk).getAttribute("checked"))) {
                    //System.out.println("open_in_new_window - true, Open In New Window - unchecked");
                    findElement(openNewWindChk).click();
                } else {
                    //System.out.println("open_in_new_window - true, Open In New Window - checked");
                }
            } else {
                if (!Boolean.parseBoolean(findElement(openNewWindChk).getAttribute("checked"))) {
                    //System.out.println("open_in_new_window - false, Open In New Window - unchecked");
                } else {
                    //System.out.println("open_in_new_window - false, Open In New Window - checked");
                    findElement(openNewWindChk).click();
                }
            }

            Thread.sleep(DEFAULT_PAUSE);

            findElement(saveBtn).click();
            waitForElement(revertBtn);

            // Write page parameters to json
            JSONObject jsonObjectNew = new JSONObject();
            JSONArray pageNamesArray = new JSONArray();

            try {
                jsonObjectNew = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFilePagesJson));
                pageNamesArray = (JSONArray) jsonObjectNew.get("page_names");
            } catch (ParseException e) {
            }

            if (parent_page.equals("Home")) {
                pageNamesArray.add(sNewPageName);
            }

            JSONObject page = new JSONObject();
            you_page_url = findElement(parentUrlSpan).getText() + findElement(seoNameInput).getAttribute("value");
            page.put("your_page_url", you_page_url);

            URL pageURL = new URL(getUrl());
            String[] params = pageURL.getQuery().split("&");
            JSONObject jsonURLQuery = new JSONObject();
            for (String param:params) {
                jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);
            }
            page.put("url_query", jsonURLQuery);

            FileWriter file = new FileWriter(sPathToFile + sDataFilePagesJson);

            jsonObjectNew.put(sNewPageName, page);

            file.write(jsonObjectNew.toJSONString().replace("\\", ""));
            file.flush();

            return findElement(workflowStateSpan).getText();

        }  catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e1) {
            e1.printStackTrace();
            return null;
        } catch (ParseException e2) {
            e2.printStackTrace();
            return null;
        }

    }

    public Boolean previewNewPage() throws InterruptedException {
        findElement(previewLnk).click();

        Thread.sleep(DEFAULT_PAUSE);

        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        waitForElement(breadcrumbDiv);

        if ( (findElement(breadcrumbDiv).getText().contains(sNewPageName)) && (driver.getTitle().contains(sNewPageName)) ) {
            driver.switchTo().window(tabs.get(1)).close();
            Thread.sleep(DEFAULT_PAUSE);
            driver.switchTo().window(tabs.get(0));
            return true;
        } else {
            driver.switchTo().window(tabs.get(1)).close();
            Thread.sleep(DEFAULT_PAUSE);
            driver.switchTo().window(tabs.get(0));
            return false;
        }

    }

    public Boolean publicNewPage() throws InterruptedException {
        ((JavascriptExecutor)driver).executeScript("window.open();");

        Thread.sleep(DEFAULT_PAUSE);

        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.get(you_page_url);
        waitForElement(breadcrumbDiv);

        if ( (findElement(breadcrumbDiv).getText().contains(sNewPageName)) && (driver.getTitle().contains(sNewPageName)) ) {
            driver.switchTo().window(tabs.get(1)).close();
            Thread.sleep(DEFAULT_PAUSE);
            driver.switchTo().window(tabs.get(0));
            return false;
        } else {
            driver.switchTo().window(tabs.get(1)).close();
            Thread.sleep(DEFAULT_PAUSE);
            driver.switchTo().window(tabs.get(0));
            return true;
        }

    }

    public Boolean listNewPage() throws InterruptedException {
        boolean item = false;
        By innerWrapPage, newPageLbl;

        try {
            if (parent_page.equals("Home")) {
                innerWrapPage = By.xpath("//div[contains(@id, 'divContent')]//span[contains(@class, 'innerWrap')][(text()=\"" + sNewPageName + "\")]/parent::span/parent::a");
                waitForElement(innerWrapPage);
                item = true;
            } else {
                innerWrapPage = By.xpath("//div[contains(@id, 'divContent')]//span[contains(@class, 'innerWrap')][(text()=\"" + parent_page + "\")]/parent::span/parent::a");
                waitForElement(innerWrapPage);
                findElement(innerWrapPage).click();
                newPageLbl = By.xpath("//td[contains(@class, 'DataGridItemBorder')][contains(@style, \'padding-left\')][contains(text(), \'" + sNewPageName + "\')]");
                waitForElement(newPageLbl);
                item = true;
            }
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        } catch (Exception e3) {
        }

        return item;
    }

    public Boolean setupAsDeletedPage(String pageName) throws InterruptedException {
        boolean item = false;
        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFilePagesJson));
            JSONArray pageNamesArray = (JSONArray) jsonObject.get("page_names");
            //System.out.println(pageNamesArray.toJSONString());

            //System.out.println(jsonObject.toJSONString());

            String  sItemID = JsonPath.read(jsonObject, "$.['"+pageName+"'].url_query.ItemID");
            String  sLanguageId = JsonPath.read(jsonObject, "$.['"+pageName+"'].url_query.LanguageId");
            String  sSectionId = JsonPath.read(jsonObject, "$.['"+pageName+"'].url_query.SectionId");

            //System.out.println("Domain: "+desktopUrl.toString());
            //System.out.println("ItemID: "+sItemID);
            //System.out.println("LanguageId: "+sLanguageId);
            //System.out.println("SectionId: "+sSectionId);

            driver.get(desktopUrl.toString()+"default.aspx?ItemID="+sItemID+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId);
            Thread.sleep(DEFAULT_PAUSE);

            //System.out.println(Functions.RemoveArrayItem(pageNamesArray, pageName).toJSONString());

            jsonObject.remove(pageName);

            //System.out.println(jsonObject.toJSONString());

            item = true;
        }  catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return item;
    }

}
