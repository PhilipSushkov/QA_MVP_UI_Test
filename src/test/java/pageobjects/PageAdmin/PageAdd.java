package pageobjects.PageAdmin;

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
import org.openqa.selenium.interactions.Actions;
import pageobjects.AbstractPageObject;
import util.Functions;

import static specs.AbstractSpec.propUIPageAdmin;

/**
 * Created by philipsushkov on 2017-01-24.
 */
public class PageAdd extends AbstractPageObject {
    private static By addNewBtn, backBtn, sectionTitleInput, pageTypeInternalRd, pageTypeExternalRd, externalURLInput;
    private static By pageTemplateSelect, parentPageSelect, showNavChk, openNewWindChk, saveBtn, workflowStateSpan;
    private static By revertBtn, parentUrlSpan, seoNameInput, previewLnk, breadcrumbDiv;
    private static String sPathToFile, sDataFileJson, sNewPagesJson, sNewPageName, you_page_url;
    private static JSONParser parser;

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
        sNewPagesJson = propUIPageAdmin.getProperty("json_NewPagesData");

        parser = new JSONParser();
    }

    public String createNewPage(String pageName) {
        int randNum = Functions.randInt(0, 999);

        waitForElement(addNewBtn);
        findElement(addNewBtn).click();
        waitForElement(backBtn);

        try {
            Object obj = parser.parse(new FileReader(sPathToFile + sDataFileJson));

            JSONObject jsonObject = (JSONObject) obj;
            JSONObject pagesDataObj = (JSONObject) jsonObject.get(pageName);

            sNewPageName = pagesDataObj.get("section_title").toString() + randNum;

            findElement(sectionTitleInput).sendKeys(sNewPageName);

            if (pagesDataObj.get("page_type").toString().equals("Internal")) {
                findElement(pageTypeInternalRd).click();
                pause(1000);
                findElement(pageTemplateSelect).sendKeys(pagesDataObj.get("page_template").toString());
            } else if (pagesDataObj.get("page_type").toString().equals("External")) {
                findElement(pageTypeExternalRd).click();
                pause(1000);
                findElement(externalURLInput).sendKeys(pagesDataObj.get("external_url").toString());
            } else {
                System.out.println("Page type in not defined. May lead to incorrect test implementation.");
            }

            findElement(parentPageSelect).sendKeys(pagesDataObj.get("parent_page").toString());

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

            pause(1000);

            findElement(saveBtn).click();
            waitForElement(revertBtn);

            // Write page parameters to json
            Object objNew;
            JSONObject jsonObjectNew;
            JSONArray list;
            try {
                objNew = parser.parse(new FileReader(sPathToFile + sNewPagesJson));
                jsonObjectNew = (JSONObject) objNew;
                list = (JSONArray) jsonObjectNew.get("new_page_names");
            } catch (ParseException e) {
                objNew = new JSONObject();
                jsonObjectNew = (JSONObject) objNew;
                list = new JSONArray();
            }

            list.add(sNewPageName);

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

            FileWriter file = new FileWriter(sPathToFile + sNewPagesJson);

            jsonObjectNew.put("new_page_names", list);
            jsonObjectNew.put(sNewPageName, page);

            file.write(jsonObjectNew.toJSONString());
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

    public Boolean previewNewPage() {
        findElement(previewLnk).click();
        pause(1500);
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        //System.out.println(driver.getCurrentUrl());
        waitForElement(breadcrumbDiv);

        if ( (findElement(breadcrumbDiv).getText().contains(sNewPageName)) && (driver.getTitle().contains(sNewPageName)) ) {
            driver.switchTo().window(tabs.get(1)).close();
            pause(1000);
            driver.switchTo().window(tabs.get(0));
            return true;
        } else {
            driver.switchTo().window(tabs.get(1)).close();
            pause(1000);
            driver.switchTo().window(tabs.get(0));
            return false;
        }

    }

    public Boolean publicNewPage() {
        ((JavascriptExecutor)driver).executeScript("window.open();");
        pause(1500);
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        //you_page_url = "http://chicagotest.q4web.release/Test";
        driver.get(you_page_url);
        waitForElement(breadcrumbDiv);
        System.out.println(findElement(breadcrumbDiv).getText());
        System.out.println(driver.getTitle());

        if ( (findElement(breadcrumbDiv).getText().contains(sNewPageName)) && (driver.getTitle().contains(sNewPageName)) ) {
            driver.switchTo().window(tabs.get(1)).close();
            pause(1000);
            driver.switchTo().window(tabs.get(0));
            return false;
        } else {
            driver.switchTo().window(tabs.get(1)).close();
            pause(1000);
            driver.switchTo().window(tabs.get(0));
            return true;
        }

    }

    public Boolean listNewPage() {
        boolean item = false;
        By innerWrapPage;

        try {
            innerWrapPage = By.xpath("//div[contains(@id, 'divContent')]//span[contains(@class, 'innerWrap')][(text()=\""+sNewPageName+"\")]/parent::span/parent::a");
            waitForElement(innerWrapPage);
            item = true;
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        } catch (Exception e3) {
        }

        return item;
    }

}
