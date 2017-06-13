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

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;
import util.Functions;

import static specs.AbstractSpec.desktopUrl;
import static specs.AbstractSpec.propUIPageAdmin;

/**
 * Created by philipsushkov on 2017-01-24.
 */

public class PageAdd extends AbstractPageObject {
    private static By addNewBtn, backBtn, sectionTitleInput, pageTypeInternalRd, pageTypeExternalRd, externalURLInput, publishBtn;
    private static By pageTemplateSelect, parentPageSelect, showNavChk, openNewWindChk, saveBtn, workflowStateSpan, currentContentSpan;
    private static By revertBtn, parentUrlSpan, seoNameInput, previewLnk, breadcrumbDiv, commentsTxt, deleteBtn, addNewInput;
    private static By saveAndSubmitBtn, rejectBtn;
    private static String sPathToFile, sFilePagesJson, sFileModulesJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private static PageAdminList pageAdminList;

    public PageAdd(WebDriver driver) {
        super(driver);

        addNewBtn = By.xpath(propUIPageAdmin.getProperty("btn_AddNew"));
        sectionTitleInput = By.xpath(propUIPageAdmin.getProperty("input_SectionTitle"));
        pageTypeInternalRd = By.xpath(propUIPageAdmin.getProperty("rd_PageTypeInt"));
        pageTypeExternalRd = By.xpath(propUIPageAdmin.getProperty("rd_PageTypeExt"));
        externalURLInput = By.xpath(propUIPageAdmin.getProperty("input_ExternalURL"));
        pageTemplateSelect = By.xpath(propUIPageAdmin.getProperty("select_PageTemplate"));
        parentPageSelect = By.xpath(propUIPageAdmin.getProperty("select_ParentPage"));
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
        revertBtn = By.xpath(propUIPageAdmin.getProperty("btn_Revert"));
        deleteBtn = By.xpath(propUIPageAdmin.getProperty("btn_Delete"));
        publishBtn = By.xpath(propUIPageAdmin.getProperty("btn_Publish"));
        backBtn = By.xpath(propUIPageAdmin.getProperty("btn_Back"));
        saveAndSubmitBtn = By.xpath(propUIPageAdmin.getProperty("btn_SaveAndSubmit"));
        rejectBtn = By.xpath(propUIPageAdmin.getProperty("btn_Reject"));

        sPathToFile = System.getProperty("user.dir") + propUIPageAdmin.getProperty("dataPath_PageAdmin");
        sFilePagesJson = propUIPageAdmin.getProperty("json_PagesProp");
        sFileModulesJson = propUIPageAdmin.getProperty("json_ModulesProp");

        parser = new JSONParser();
        pageAdminList = new PageAdminList(driver);;
    }

    public String createPage(JSONObject pagesDataObj, String pageName) throws InterruptedException {
        String your_page_url, parent_page, page_type, external_url = null;

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
            } else if (pagesDataObj.get("page_type").toString().equals("External")) {
                findElement(pageTypeExternalRd).click();
                Thread.sleep(DEFAULT_PAUSE);
                external_url = pagesDataObj.get("external_url").toString();
                findElement(externalURLInput).sendKeys(pagesDataObj.get("external_url").toString());
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
            JSONArray pageNamesArray = new JSONArray();

            try {
                jsonObjectNew = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePagesJson));
                pageNamesArray = (JSONArray) jsonObjectNew.get("page_names");
            } catch (ParseException e) {
                jsonObjectNew = (JSONObject) parser.parse("{\"page_names\":[]}");
                pageNamesArray = (JSONArray) jsonObjectNew.get("page_names");
            }

            if (parent_page.equals("Home")) {
                pageNamesArray.add(pageName);
            }

            JSONObject page = new JSONObject();
            your_page_url = findElement(parentUrlSpan).getText() + findElement(seoNameInput).getAttribute("value");
            page.put("your_page_url", your_page_url);
            page.put("parent_page", parent_page);
            page.put("page_type", page_type);
            if (page_type.equals("External")) {
                page.put("external_url", external_url);
            }

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
                FileWriter file = new FileWriter(sPathToFile + sFilePagesJson);
                file.write(jsonObjectNew.toJSONString().replace("\\", ""));
                file.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(pageName+ ": New Page has been created");
            return findElement(workflowStateSpan).getText();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public Boolean previewPage(String pageName) throws InterruptedException {
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePagesJson));

            String pageUrl = getPageUrl(jsonObject, pageName);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            findElement(previewLnk).click();

            Thread.sleep(DEFAULT_PAUSE);

            ArrayList<String> tabs = new ArrayList<> (driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));

            if (driver.getTitle().contains(pageName)) {
                /* try {
                    waitForElement(breadcrumbDiv);

                    if (findElement(breadcrumbDiv).getText().contains(pageName)) { */
                        driver.switchTo().window(tabs.get(1)).close();
                        Thread.sleep(DEFAULT_PAUSE);
                        driver.switchTo().window(tabs.get(0));

                        System.out.println(pageName+ ": New Page Preview has been checked");
                        return true;
                    /* } else {
                        driver.switchTo().window(tabs.get(1)).close();
                        Thread.sleep(DEFAULT_PAUSE);
                        driver.switchTo().window(tabs.get(0));
                        return false;
                    }
                }  catch (TimeoutException e) {
                    driver.switchTo().window(tabs.get(1)).close();
                    Thread.sleep(DEFAULT_PAUSE);
                    driver.switchTo().window(tabs.get(0));
                    return false;
                } */
            } else {
                driver.switchTo().window(tabs.get(1)).close();
                Thread.sleep(DEFAULT_PAUSE);
                driver.switchTo().window(tabs.get(0));
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }


    public Boolean publicPage(String pageName) throws InterruptedException {
        int randNum = Functions.randInt(0, 99);

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePagesJson));

            String pageUrl = getPageUrl(jsonObject, pageName);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            ((JavascriptExecutor)driver).executeScript("window.open();");

            Thread.sleep(DEFAULT_PAUSE);

            ArrayList<String> tabs = new ArrayList<> (driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));

            try {
                driver.get(JsonPath.read(jsonObject, "$.['"+pageName+"'].your_page_url").toString()+"?param="+Integer.toString(randNum));
            } catch (TimeoutException e) {
                driver.findElement(By.tagName("body")).sendKeys("Keys.ESCAPE");
            }

            if (driver.getTitle().contains(pageName)) {
                /*try {
                    waitForElement(breadcrumbDiv);
                    if ((findElement(breadcrumbDiv).getText().contains(pageName))) { */
                        driver.switchTo().window(tabs.get(1)).close();
                        driver.switchTo().window(tabs.get(0));

                        System.out.println(pageName+ ": New Page Public has checked");
                        return true;
                    /*}
                } catch (TimeoutException e) {
                } */
            } else if (JsonPath.read(jsonObject, "$.['"+pageName+"'].page_type").toString().equals("External")) {
                if (driver.getCurrentUrl().contains(JsonPath.read(jsonObject, "$.['" + pageName + "'].external_url").toString())) {
                    driver.switchTo().window(tabs.get(1)).close();
                    driver.switchTo().window(tabs.get(0));

                    System.out.println(pageName+ ": New Page Public has been checked");
                    return true;
                } else {
                    driver.switchTo().window(tabs.get(1)).close();
                    driver.switchTo().window(tabs.get(0));
                    return false;
                }
            } else {
                driver.switchTo().window(tabs.get(1)).close();
                driver.switchTo().window(tabs.get(0));
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
        }

        return null;
    }


    public Boolean listPage(String pageName) throws InterruptedException {
        boolean item = false;
        By innerWrapPage, newPageLbl;

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePagesJson));
            String parent_page = JsonPath.read(jsonObject, "$.['"+pageName+"'].parent_page").toString();

            try {
                if (parent_page.equals("Home")) {
                    innerWrapPage = By.xpath("//div[contains(@id, 'divContent')]//span[contains(@class, 'innerWrap')][(text()=\"" + pageName + "\")]/parent::span/parent::a");
                    waitForElement(innerWrapPage);
                    item = true;
                } else {
                    innerWrapPage = By.xpath("//div[contains(@id, 'divContent')]//span[contains(@class, 'innerWrap')][(text()=\"" + parent_page + "\")]/parent::span/parent::a");
                    waitForElement(innerWrapPage);
                    findElement(innerWrapPage).click();
                    newPageLbl = By.xpath("//td[contains(@class, 'DataGridItemBorder')][contains(@style, \'padding-left\')][contains(text(), \'" + pageName + "\')]");
                    waitForElement(newPageLbl);
                    item = true;
                }
            } catch (ElementNotFoundException e) {
            } catch (ElementNotVisibleException e) {
            } catch (TimeoutException e) {
            } catch (Exception e) {
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println(pageName+ ": New Page in the list has been checked");
        return item;
    }


    public String saveAndSubmitPage(JSONObject pagesDataObj, String pageName) throws InterruptedException {
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePagesJson));

            String pageUrl = getPageUrl(jsonObject, pageName);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(commentsTxt);
            findElement(commentsTxt).sendKeys(pagesDataObj.get("comment").toString());
            findElement(saveAndSubmitBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            JSONObject pageObj = (JSONObject) jsonObject.get(pageName);

            pageObj.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            pageObj.put("deleted", "false");

            jsonObject.put(pageName, pageObj);

            FileWriter file = new FileWriter(sPathToFile + sFilePagesJson);
            file.write(jsonObject.toJSONString().replace("\\", ""));
            file.flush();

            System.out.println(pageName+ ": New Page has been submitted");
            return findElement(workflowStateSpan).getText();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }


    public Boolean checkPageData(JSONObject pageDataObj, String pageName) throws InterruptedException {
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePagesJson));

            String pageUrl = getPageUrl(jsonObject, pageName);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            JSONObject pageObj = (JSONObject) pageAdminList.savePageToJSON(sPathToFile, sFilePagesJson, sFileModulesJson).get(pageName);

            if (!pageObj.get("section_title").toString().equals(pageDataObj.get("section_title").toString())) {
                return false;
            }

            if (!pageObj.get("page_type").toString().equals(pageDataObj.get("page_type").toString())) {
                return false;
            }

            if (pageObj.get("page_type").toString().equals("External")) {
                if (!pageObj.get("external_url").toString().equals(pageDataObj.get("external_url").toString())) {
                    return false;
                }
            }

            if (!pageObj.get("parent_section").toString().contains(pageDataObj.get("parent_page").toString())) {
                return false;
            }

            if (!pageObj.get("show_in_navigation").toString().equals(pageDataObj.get("show_in_navigation").toString())) {
                return false;
            }

            if (!pageObj.get("open_in_new_window").toString().equals(pageDataObj.get("open_in_new_window").toString())) {
                return false;
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(pageName+ ": New Page data has been validated");
        return false;
    }


    public Boolean checkPageChanges(JSONObject pageDataObj, String pageName) throws InterruptedException {
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePagesJson));
            JSONArray pageNamesArray = (JSONArray) jsonObject.get("page_names");

            String pageUrl = getPageUrl(jsonObject, pageName);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            try {
                if (!pageDataObj.get("section_title_ch").toString().isEmpty()) {
                    Functions.RemoveArrayItem(pageNamesArray, pageName);
                    pageName = pageDataObj.get("section_title_ch").toString();

                    if (pageDataObj.get("parent_page").toString().equals("Home")) {
                        pageNamesArray.add(pageName);
                    }

                    jsonObject.put("page_names", pageNamesArray);

                    FileWriter file = new FileWriter(sPathToFile + sFilePagesJson);
                    file.write(jsonObject.toJSONString().replace("\\", ""));
                    file.flush();
                }
            } catch (NullPointerException e) {
            }

            JSONObject pageObj = (JSONObject) pageAdminList.savePageToJSON(sPathToFile, sFilePagesJson, sFileModulesJson).get(pageName);

            try {
                if (!pageObj.get("section_title").toString().equals(pageDataObj.get("section_title_ch").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {}

            try {
                if (!pageObj.get("page_type").toString().equals(pageDataObj.get("page_type_ch").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {}

            try {
                if (pageObj.get("page_type").toString().equals("External")) {
                    if (!pageObj.get("external_url").toString().equals(pageDataObj.get("external_url_ch").toString())) {
                        return false;
                    }
                }
            } catch (NullPointerException e) {}

            System.out.println(pageName+ ": New Page changes have been checked");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }


    public String publishPage(String pageName) throws InterruptedException {
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePagesJson));

            String pageUrl = getPageUrl(jsonObject, pageName);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(publishBtn);
            findElement(publishBtn).click();
            Thread.sleep(DEFAULT_PAUSE*2);

            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            JSONObject pageObj = (JSONObject) jsonObject.get(pageName);

            pageObj.put("workflow_state", WorkflowState.LIVE.state());
            pageObj.put("deleted", "false");

            jsonObject.put(pageName, pageObj);

            FileWriter file = new FileWriter(sPathToFile + sFilePagesJson);
            file.write(jsonObject.toJSONString().replace("\\", ""));
            file.flush();


            Thread.sleep(DEFAULT_PAUSE*2);
            driver.navigate().refresh();

            System.out.println(pageName+ ": New Page has been published");
            return findElement(workflowStateSpan).getText();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String changeAndSubmitPage(JSONObject pagesDataObj, String pageName) throws InterruptedException {
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePagesJson));
            JSONObject pageObj = (JSONObject) jsonObject.get(pageName);

            if (!pageObj.get("workflow_state").toString().equals(WorkflowState.FOR_APPROVAL.state())) {
                String pageUrl = getPageUrl(jsonObject, pageName);
                driver.get(pageUrl);
                Thread.sleep(DEFAULT_PAUSE);

                try {
                    if (!pagesDataObj.get("section_title_ch").toString().isEmpty()) {
                        findElement(sectionTitleInput).clear();
                        findElement(sectionTitleInput).sendKeys(pagesDataObj.get("section_title_ch").toString());
                        findElement(commentsTxt).clear();
                        findElement(commentsTxt).sendKeys(pagesDataObj.get("comment_ch").toString());
                    }
                } catch (NullPointerException e) {}

                try {
                    if (!pagesDataObj.get("page_type_ch").toString().isEmpty()) {

                        if (pagesDataObj.get("page_type_ch").toString().equals("Internal")) {
                            findElement(pageTypeInternalRd).click();
                        } else if (pagesDataObj.get("page_type_ch").toString().equals("External")) {
                            findElement(pageTypeExternalRd).click();
                            waitForElement(externalURLInput);
                            findElement(externalURLInput).clear();
                            findElement(externalURLInput).sendKeys(pagesDataObj.get("external_url_ch").toString());
                        }
                        findElement(commentsTxt).clear();
                        findElement(commentsTxt).sendKeys(pagesDataObj.get("comment_ch").toString());
                    }
                } catch (NullPointerException e) {}


                findElement(saveAndSubmitBtn).click();
                Thread.sleep(DEFAULT_PAUSE);

                pageObj.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
                pageObj.put("deleted", "false");

                jsonObject.put(pageName, pageObj);

                FileWriter file = new FileWriter(sPathToFile + sFilePagesJson);
                file.write(jsonObject.toJSONString().replace("\\", ""));
                file.flush();

                driver.get(pageUrl);
                Thread.sleep(DEFAULT_PAUSE);
                waitForElement(sectionTitleInput);

                System.out.println(pageName+ ": New Page changes have been submitted");
                return findElement(workflowStateSpan).getText();
            }

            return null;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }


    public String revertToLivePage(String pageName) throws InterruptedException {
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePagesJson));

            JSONObject pageObj = (JSONObject) jsonObject.get(pageName);

            if (pageObj.get("workflow_state").toString().equals(WorkflowState.FOR_APPROVAL.state())) {
                String pageUrl = getPageUrl(jsonObject, pageName);
                driver.get(pageUrl);
                Thread.sleep(DEFAULT_PAUSE);

                findElement(revertBtn).click();
                Thread.sleep(DEFAULT_PAUSE);

                pageObj.put("workflow_state", WorkflowState.LIVE.state());
                pageObj.put("deleted", "false");

                jsonObject.put(pageName, pageObj);

                FileWriter file = new FileWriter(sPathToFile + sFilePagesJson);
                file.write(jsonObject.toJSONString().replace("\\", ""));
                file.flush();

                driver.get(pageUrl);
                Thread.sleep(DEFAULT_PAUSE);
                waitForElement(sectionTitleInput);

                System.out.println(pageName+ ": Page has been reverted to Live");
                return findElement(workflowStateSpan).getText();
            }

            return null;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }


    public String setupAsDeletedPage(String pageName) throws InterruptedException {
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePagesJson));

            String pageUrl = getPageUrl(jsonObject, pageName);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(commentsTxt);
            findElement(commentsTxt).sendKeys("Removing the page");
            findElement(deleteBtn).click();

            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(addNewInput);
            driver.get(pageUrl);
            waitForElement(currentContentSpan);

            JSONObject pageObj = (JSONObject) jsonObject.get(pageName);

            pageObj.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            pageObj.put("deleted", "true");

            jsonObject.put(pageName, pageObj);

            FileWriter file = new FileWriter(sPathToFile + sFilePagesJson);
            file.write(jsonObject.toJSONString().replace("\\", ""));
            file.flush();

            System.out.println(pageName+ ": Page set up as deleted");
            return findElement(currentContentSpan).getText();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }


    public String removePage(JSONObject pageDataObj, String pageName) throws InterruptedException {
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sFilePagesJson));
            JSONArray pageNamesArray = (JSONArray) jsonObject.get("page_names");

            String pageUrl = getPageUrl(jsonObject, pageName);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            if (findElement(currentContentSpan).getText().equals(WorkflowState.DELETE_PENDING.state())) {

                waitForElement(commentsTxt);
                findElement(commentsTxt).sendKeys("Approving the page removal");
                findElement(publishBtn).click();

                Thread.sleep(DEFAULT_PAUSE*2);

                driver.get(pageUrl);
                Thread.sleep(DEFAULT_PAUSE);

                try {
                    String pageNameCh = pageDataObj.get("section_title_ch").toString();
                    Functions.RemoveArrayItem(pageNamesArray, pageNameCh);
                    jsonObject.remove(pageNameCh);
                } catch (NullPointerException e) {}

                Functions.RemoveArrayItem(pageNamesArray, pageName);
                jsonObject.remove(pageName);

                FileWriter file = new FileWriter(sPathToFile + sFilePagesJson);
                file.write(jsonObject.toJSONString().replace("\\", ""));
                file.flush();

                Thread.sleep(DEFAULT_PAUSE*2);
                driver.navigate().refresh();

                System.out.println(pageName+ ": New Page has been removed");
                return findElement(workflowStateSpan).getText();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
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
