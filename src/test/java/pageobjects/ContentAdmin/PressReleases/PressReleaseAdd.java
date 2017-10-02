package pageobjects.ContentAdmin.PressReleases;

import com.mongodb.util.JSON;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import pageobjects.AbstractPageObject;

import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pageobjects.PageAdmin.WorkflowState;
import util.Functions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static specs.AbstractSpec.desktopUrl;
import static specs.AbstractSpec.propUIContentAdmin;
import static util.Functions.getUrlFromApiData;

/**
 * Created by charleszheng on 2017-09-27.
 */

public class PressReleaseAdd extends AbstractPageObject {
    private static By moduleTitle, timeHourSelect, timeMinSelect, relatedDocumentInput;
    private static By prDateInput, tagsInput, publishBtn,prHeadline;
    private static By currentContentSpan;
    private static By mtHttp, mtName, mtProperty, mtContent, mtScheme, mtLang, mtDir;
    private static By mtHttpChk, mtNameChk, mtPropertyChk, mtContentChk, mtSchemeChk, mtLangChk, mtDirChk;
    private static By activeChk, saveBtn, revertBtn, cancelBtn, deleteBtn, addNewLink, advancedBtn, advancedAddNewBtn,advancedokBtn;
    private static By workflowStateSpan, commentsTxt, successMsg, saveAndSubmitBtn;
    private static By previewLnk;
    private static String sPathToFile, sFileJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String PAGE_NAME="Press Release", METATAG="meta_tag";

    public PressReleaseAdd(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("spanModule_Title"));
        timeHourSelect = By.xpath(propUIContentAdmin.getProperty("select_Hour"));
        timeMinSelect = By.xpath(propUIContentAdmin.getProperty("select_Min"));
        relatedDocumentInput = By.xpath(propUIContentAdmin.getProperty("input_RelatedDocument"));
        prDateInput = By.xpath(propUIContentAdmin.getProperty("input_PRDate"));
        prHeadline = By.xpath(propUIContentAdmin.getProperty("input_PRHeadline"));
        tagsInput = By.xpath(propUIContentAdmin.getProperty("input_Tags"));
        activeChk = By.xpath(propUIContentAdmin.getProperty("chk_Active"));
        advancedBtn = By.xpath(propUIContentAdmin.getProperty("btn_Advanced"));
        advancedAddNewBtn = By.xpath(propUIContentAdmin.getProperty("btn_AdvancedAddNew"));
        advancedokBtn = By.xpath(propUIContentAdmin.getProperty("btn_AdvancedOk"));
        saveBtn = By.xpath(propUIContentAdmin.getProperty("btn_Save"));
        cancelBtn = By.xpath(propUIContentAdmin.getProperty("btn_Cancel"));
        deleteBtn = By.xpath(propUIContentAdmin.getProperty("btn_Delete"));
        publishBtn = By.xpath(propUIContentAdmin.getProperty("btn_Publish"));
        addNewLink = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));
        revertBtn = By.xpath(propUIContentAdmin.getProperty("btn_Revert"));
        workflowStateSpan = By.xpath(propUIContentAdmin.getProperty("select_WorkflowState"));
        commentsTxt = By.xpath(propUIContentAdmin.getProperty("txtarea_Comments"));
        successMsg = By.xpath(propUIContentAdmin.getProperty("msg_Success"));
        saveAndSubmitBtn = By.xpath(propUIContentAdmin.getProperty("btn_SaveAndSubmit"));
        currentContentSpan = By.xpath(propUIContentAdmin.getProperty("span_CurrentContent"));
        mtHttp = By.xpath(propUIContentAdmin.getProperty("input_MTHttp"));
        mtName = By.xpath(propUIContentAdmin.getProperty("input_MTName"));
        mtProperty = By.xpath(propUIContentAdmin.getProperty("input_MTProperty"));
        mtContent = By.xpath(propUIContentAdmin.getProperty("input_MTContent"));
        mtScheme = By.xpath(propUIContentAdmin.getProperty("input_MTScheme"));
        mtLang = By.xpath(propUIContentAdmin.getProperty("input_MTLang"));
        mtDir = By.xpath(propUIContentAdmin.getProperty("input_MTDir"));
        mtHttpChk = By.xpath(propUIContentAdmin.getProperty("input_MTHttpChk"));
        mtNameChk = By.xpath(propUIContentAdmin.getProperty("input_MTNameChk"));
        mtPropertyChk = By.xpath(propUIContentAdmin.getProperty("input_MTPropertyChk"));
        mtContentChk = By.xpath(propUIContentAdmin.getProperty("input_MTContentChk"));
        mtSchemeChk = By.xpath(propUIContentAdmin.getProperty("input_MTSchemeChk"));
        mtLangChk = By.xpath(propUIContentAdmin.getProperty("input_MTLangChk"));
        mtDirChk = By.xpath(propUIContentAdmin.getProperty("input_MTDirChk"));
        previewLnk = By.xpath(propUIContentAdmin.getProperty("lnk_Preview"));


        parser = new JSONParser();

        sPathToFile = System.getProperty("user.dir") + propUIContentAdmin.getProperty("dataPath_PressReleaseList");
        sFileJson = propUIContentAdmin.getProperty("json_PressRelease");
    }

    public String getTitle() {
        findElement(addNewLink).click();
        waitForElement(moduleTitle);
        String sTitle = getText(moduleTitle);
        findElement(cancelBtn).click();
        return sTitle;
    }

    public String savePressRelease(JSONObject data, String name) {
        String time_hour, time_min, related_document, pressrelease_date, tags, pressrelease_headline,
        mt_http, mt_name, mt_property, mt_content, mt_scheme, mt_lang, mt_dir;
        Boolean active;
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonMain = new JSONObject();
        JSONObject  mtObj;

        waitForElement(addNewLink);
        findElement(addNewLink).click();
        waitForElement(saveBtn);
        waitForElement(advancedBtn);
        findElement(advancedBtn).click();
        waitForElement(advancedAddNewBtn);
        findElement(advancedAddNewBtn).click();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            pressrelease_date = data.get("pressrelease_date").toString();
            findElement(prDateInput).clear();
            findElement(prDateInput).sendKeys(pressrelease_date);
            jsonObj.put("pressrelease_date", pressrelease_date);

            time_hour = data.get("time_hour").toString();
            findElement(timeHourSelect).sendKeys(time_hour);
            jsonObj.put("time_hour", time_hour);

            time_min = data.get("time_min").toString();
            findElement(timeMinSelect).sendKeys(time_min);
            jsonObj.put("time_min", time_min);

            pressrelease_headline= data.get("pressrelease_headline").toString();
            findElement(prHeadline).clear();
            findElement(prHeadline).sendKeys(pressrelease_headline);
            jsonObj.put("pressrelease_headline", pressrelease_headline);

            tags = data.get("tags").toString();
            findElement(tagsInput).clear();
            findElement(tagsInput).sendKeys(tags);
            jsonObj.put("tags", tags);

            mtObj = (JSONObject) data.get("meta_tag");

            mt_http = mtObj.get("http_equiv").toString();
            findElement(mtHttp).clear();
            findElement(mtHttp).sendKeys(mt_http);
            jsonObj.put("http_equiv", mt_http);

            mt_name = mtObj.get("name").toString();
            findElement(mtName).clear();
            findElement(mtName).sendKeys(mt_name);
            jsonObj.put("name", mt_name);

            mt_property = mtObj.get("property").toString();
            findElement(mtProperty).clear();
            findElement(mtProperty).sendKeys(mt_property);
            jsonObj.put("property", mt_property);

            mt_content = mtObj.get("content").toString();
            findElement(mtContent).clear();
            findElement(mtContent).sendKeys(mt_content);
            jsonObj.put("content", mt_content);

            mt_scheme = mtObj.get("scheme").toString();
            findElement(mtScheme).clear();
            findElement(mtScheme).sendKeys(mt_scheme);
            jsonObj.put("scheme", mt_scheme);

            mt_lang = mtObj.get("lang").toString();
            findElement(mtLang).clear();
            findElement(mtLang).sendKeys(mt_lang);
            jsonObj.put("lang", mt_lang);

            mt_dir = mtObj.get("dir").toString();
            findElement(mtDir).clear();
            findElement(mtDir).sendKeys(mt_dir);
            jsonObj.put("dir", mt_dir);

            findElement(advancedokBtn).click();

            related_document = data.get("related_document").toString();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement elemSrc =  driver.findElement(relatedDocumentInput);
            String filename = related_document;
            js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", elemSrc, "value", "files/"+filename);
            jsonObj.put("download_filename", related_document);


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

            URL url = new URL(getUrl());
            String[] params = url.getQuery().split("&");
            JSONObject jsonURLQuery = new JSONObject();
            for (String param:params) {
                jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);
            }
            jsonObj.put("url_query", jsonURLQuery);

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

    public String saveAndSubmitPressRelease(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        By editBtn = By.xpath("//td[(text()='"+name+"')]/parent::tr/td/input[contains(@id, 'imgEdit')][contains(@id, 'PressRelease')]");

        try {
            waitForElement(moduleTitle);

            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
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

    public Boolean checkPressRelease(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonMetaTag = (JSONObject) data.get(METATAG);

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
            waitForElement(advancedBtn);
            findElement(advancedBtn).click();

            // Compare field values with entry data
            try {
                if (!new Select(findElement(timeHourSelect)).getFirstSelectedOption().getText().trim().equals(data.get("time_hour").toString())) {
                    System.out.println(findElement(timeHourSelect).getAttribute("value"));
                    System.out.println(data.get("time_hour").toString());
                    System.out.println("Fails time hour");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!new Select(findElement(timeMinSelect)).getFirstSelectedOption().getText().trim().equals(data.get("time_min").toString())) {
                    System.out.println(findElement(timeMinSelect).getAttribute("value"));
                    System.out.println(data.get("time_Min").toString());
                    System.out.println("Fails time min");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(prDateInput).getAttribute("value").equals(data.get("pressrelease_date").toString())) {
                    System.out.println("Fails date");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(prHeadline).getAttribute("value").equals(data.get("pressrelease_headline").toString())) {
                    System.out.println("Fails headline");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(tagsInput).getAttribute("value").trim().equals(data.get("tags").toString())) {
                    System.out.println(findElement(tagsInput).getAttribute("value"));
                    System.out.println(data.get("tags").toString());
                    System.out.println("Fails tags");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(mtHttpChk).getAttribute("value").trim().equals(jsonMetaTag.get("http_equiv").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(mtNameChk).getAttribute("value").trim().equals(jsonMetaTag.get("name").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(mtPropertyChk).getAttribute("value").trim().equals(jsonMetaTag.get("property").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(mtContentChk).getAttribute("value").trim().equals(jsonMetaTag.get("content").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(mtSchemeChk).getAttribute("value").trim().equals(jsonMetaTag.get("scheme").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(mtLangChk).getAttribute("value").trim().equals(jsonMetaTag.get("lang").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(mtDirChk).getAttribute("value").trim().equals(jsonMetaTag.get("dir").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                JavascriptExecutor js = (JavascriptExecutor)driver;
                if (!js.executeScript("return arguments[0].value;", driver.findElement(relatedDocumentInput)).equals("files/" + data.get("related_document").toString())) {
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

    public String publishPressRelease(JSONObject data, String name) throws InterruptedException {
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

            System.out.println(jsonObj.get("pressrelease_headline").toString() + ": New "+PAGE_NAME+" has been published");
            return findElement(workflowStateSpan).getText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String changeAndSubmitPressRelease(JSONObject data, String name) throws InterruptedException {
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
                if (!data.get("pressrelease_headline_ch").toString().isEmpty()) {
                    findElement(prHeadline).clear();
                    findElement(prHeadline).sendKeys(data.get("pressrelease_headline_ch").toString());
                    jsonObj.put("pressrelease_headline", data.get("pressrelease_headline_ch").toString());
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!data.get("tags_ch").toString().isEmpty()) {
                    findElement(tagsInput).clear();
                    findElement(tagsInput).sendKeys(data.get("tags_ch").toString());
                    jsonObj.put("tags", data.get("tags_ch").toString());
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!data.get("related_document_ch").toString().isEmpty()) {
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    WebElement elemSrc =  driver.findElement(relatedDocumentInput);
                    String filename = data.get("related_document_ch").toString();
                    js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", elemSrc, "value", "files/"+filename);
                    jsonObj.put("download_filename", filename);
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

            System.out.println(jsonObj.get("pressrelease_headline").toString() + ": New "+PAGE_NAME+" changes have been submitted");
            return findElement(workflowStateSpan).getText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String revertToLivePressRelease(String name) throws InterruptedException {
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

                System.out.println(jsonObj.get("pressrelease_headline").toString()+ ": "+PAGE_NAME+" has been reverted to Live");
                return findElement(workflowStateSpan).getText();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Boolean checkPressReleaseCh(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonMetaTag = (JSONObject) data.get(METATAG);

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }
            JSONObject obj = (JSONObject) jsonMain.get(name);

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(commentsTxt);
            waitForElement(advancedBtn);
            findElement(advancedBtn).click();

            // Compare field values with entry data
            try {
                if (!new Select(findElement(timeHourSelect)).getFirstSelectedOption().getText().trim().equals(data.get("time_hour").toString())) {
                    System.out.println(findElement(timeHourSelect).getAttribute("value"));
                    System.out.println(data.get("time_hour").toString());
                    System.out.println("Fails time hour");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!new Select(findElement(timeMinSelect)).getFirstSelectedOption().getText().trim().equals(data.get("time_min").toString())) {
                    System.out.println(findElement(timeMinSelect).getAttribute("value"));
                    System.out.println(data.get("time_Min").toString());
                    System.out.println("Fails time min");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(prDateInput).getAttribute("value").equals(data.get("pressrelease_date").toString())) {
                    System.out.println("Fails date");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(prHeadline).getAttribute("value").equals(data.get("pressrelease_headline_ch").toString())) {
                    System.out.println("Fails headline");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(tagsInput).getAttribute("value").trim().equals(data.get("tags_ch").toString())) {
                    System.out.println(findElement(tagsInput).getAttribute("value"));
                    System.out.println(data.get("tags").toString());
                    System.out.println("Fails tags");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(mtHttpChk).getAttribute("value").trim().equals(jsonMetaTag.get("http_equiv").toString())) {
                    System.out.println(findElement(mtHttpChk).getAttribute("value"));
                    System.out.println(data.get("http_equiv").toString());
                    System.out.println("Fails http_equiv");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(mtNameChk).getAttribute("value").trim().equals(jsonMetaTag.get("name").toString())) {
                    System.out.println(findElement(mtNameChk).getAttribute("value"));
                    System.out.println(data.get("name").toString());
                    System.out.println("Fails name");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(mtPropertyChk).getAttribute("value").trim().equals(jsonMetaTag.get("property").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(mtContentChk).getAttribute("value").trim().equals(jsonMetaTag.get("content").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(mtSchemeChk).getAttribute("value").trim().equals(jsonMetaTag.get("scheme").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(mtLangChk).getAttribute("value").trim().equals(jsonMetaTag.get("lang").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(mtDirChk).getAttribute("value").trim().equals(jsonMetaTag.get("dir").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                JavascriptExecutor js = (JavascriptExecutor)driver;
                if (!js.executeScript("return arguments[0].value;", driver.findElement(relatedDocumentInput)).equals("files/" + data.get("related_document_ch").toString())) {
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


            System.out.println(obj.get("pressrelease_headline").toString()+ ": New "+PAGE_NAME+" has been checked");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String setupAsDeletedPressRelease(String name) throws InterruptedException {
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

            try {
                Thread.sleep(DEFAULT_PAUSE*2);
                driver.get(pageUrl);
            } catch (UnhandledAlertException e) {
                driver.switchTo().alert().accept();
                Thread.sleep(DEFAULT_PAUSE*2);
                try {
                    driver.get(pageUrl);
                } catch (UnhandledAlertException e2) {
                    driver.switchTo().alert().accept();
                    Thread.sleep(DEFAULT_PAUSE*2);
                    driver.get(pageUrl);
                }
            }

            waitForElement(currentContentSpan);

            jsonObj.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            jsonObj.put("deleted", "true");

            jsonMain.put(name, jsonObj);

            FileWriter file = new FileWriter(sPathToFile + sFileJson);
            file.write(jsonMain.toJSONString().replace("\\", ""));
            file.flush();

            System.out.println(jsonObj.get("pressrelease_headline").toString()+ ": "+PAGE_NAME+" set up as deleted");
            return findElement(currentContentSpan).getText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String removePressRelease(JSONObject data, String name) throws InterruptedException {
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

                try {
                    Thread.sleep(DEFAULT_PAUSE*2);
                    driver.get(pageUrl);
                } catch (UnhandledAlertException e) {
                    driver.switchTo().alert().accept();
                    Thread.sleep(DEFAULT_PAUSE*2);
                    try {
                        driver.get(pageUrl);
                    } catch (UnhandledAlertException e2) {
                        driver.switchTo().alert().accept();
                        Thread.sleep(DEFAULT_PAUSE*2);
                        driver.get(pageUrl);
                    }
                }

                jsonMain.remove(name);

                FileWriter file = new FileWriter(sPathToFile + sFileJson);
                file.write(jsonMain.toJSONString().replace("\\", ""));
                file.flush();

                Thread.sleep(DEFAULT_PAUSE*2);
                driver.navigate().refresh();

                System.out.println(": New "+PAGE_NAME+" has been removed");
                return findElement(workflowStateSpan).getText();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Boolean previewPressRelease(JSONObject data, String name) throws InterruptedException {
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sFileJson));
            JSONObject Obj = (JSONObject) jsonObject.get(name);

            String pageUrl = getPageUrl(jsonObject, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            findElement(previewLnk).click();

            Thread.sleep(DEFAULT_PAUSE*3);
            //    ((JavascriptExecutor)driver).executeScript("window.open()");
            ArrayList<String> tabs = new ArrayList<> (driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));
            //       driver.get(getPreviewPageUrl(jsonObject,name));

                if (findElement(By.xpath("//span[contains(@class,'ModuleHeadline')][contains(text(),'" + name + "')]")) != null) {
                /* try {
                    waitForElement(breadcrumbDiv);

                    if (findElement(breadcrumbDiv).getText().contains(pageName)) { */
                    driver.switchTo().window(tabs.get(1)).close();
                    Thread.sleep(DEFAULT_PAUSE * 3);
                    driver.switchTo().window(tabs.get(0));

                    System.out.println(name + ": New Press Release Preview has been checked");
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

    public Boolean previewPressReleaseCh(JSONObject data, String name) throws InterruptedException {
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sFileJson));

            String pageUrl = getPageUrl(jsonObject, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            findElement(previewLnk).click();

            Thread.sleep(DEFAULT_PAUSE*3);
            //    ((JavascriptExecutor)driver).executeScript("window.open()");
            ArrayList<String> tabs = new ArrayList<> (driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));
            //       driver.get(getPreviewPageUrl(jsonObject,name));
            if (findElement(By.xpath("//span[contains(@class,'ModuleHeadline')][contains(text(),'" + data.get("pressrelease_headline_ch").toString() + "')]")) != null) {
                /* try {
                    waitForElement(breadcrumbDiv);

                    if (findElement(breadcrumbDiv).getText().contains(pageName)) { */
                driver.switchTo().window(tabs.get(1)).close();
                Thread.sleep(DEFAULT_PAUSE * 3);
                driver.switchTo().window(tabs.get(0));

                System.out.println(data.get("pressrelease_headline_ch").toString() + ": New Press Release Preview has been checked");
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

    public Boolean publicPressRelease(JSONObject data, String name) throws InterruptedException {
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sFileJson));
            JSONObject Obj = (JSONObject) jsonObject.get(name);

            String pageUrl = getPageUrl(jsonObject, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            String publicUrl = findElement(By.xpath("//span[contains(@id,'PageUrl')]")).getText();

            ((JavascriptExecutor)driver).executeScript("window.open();");

            Thread.sleep(DEFAULT_PAUSE);

            ArrayList<String> tabs = new ArrayList<> (driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));


            try {
                driver.get(publicUrl);
            } catch (TimeoutException e) {
                driver.findElement(By.tagName("body")).sendKeys("Keys.ESCAPE");
            }

                if (driver.getTitle().contains(name)){
                /*try {
                    waitForElement(breadcrumbDiv);
                    if ((findElement(breadcrumbDiv).getText().contains(pageName))) { */
                    driver.switchTo().window(tabs.get(1)).close();
                    driver.switchTo().window(tabs.get(0));

                    System.out.println(name + ": New Press Release Public has checked");
                    return true;
                    /*}
                } catch (TimeoutException e) {
                } */
                } /*else if (JsonPath.read(jsonObject, "$.['"+pageName+"'].page_type").toString().equals("External")) {
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
            }*/ else {
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

    public Boolean publicPressReleaseCh(JSONObject data, String name) throws InterruptedException {
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sFileJson));
            JSONObject Obj = (JSONObject) jsonObject.get(name);

            String pageUrl = getPageUrl(jsonObject, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            String publicUrl = findElement(By.xpath("//span[contains(@id,'PageUrl')]")).getText();

            ((JavascriptExecutor)driver).executeScript("window.open();");

            Thread.sleep(DEFAULT_PAUSE);

            ArrayList<String> tabs = new ArrayList<> (driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));


            try {
                driver.get(publicUrl);
            } catch (TimeoutException e) {
                driver.findElement(By.tagName("body")).sendKeys("Keys.ESCAPE");
            }

            if (driver.getTitle().contains(data.get("pressrelease_headline_ch").toString())){
                /*try {
                    waitForElement(breadcrumbDiv);
                    if ((findElement(breadcrumbDiv).getText().contains(pageName))) { */
                driver.switchTo().window(tabs.get(1)).close();
                driver.switchTo().window(tabs.get(0));

                System.out.println(data.get("pressrelease_headline_ch").toString() + ": New Press Release Public has checked");
                return true;
                    /*}
                } catch (TimeoutException e) {
                } */
            } /*else if (JsonPath.read(jsonObject, "$.['"+pageName+"'].page_type").toString().equals("External")) {
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
            }*/ else {
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

    public String getPageUrl(JSONObject obj, String name) {
        String sItemID = JsonPath.read(obj, "$.['"+name+"'].url_query.ItemID");
        String sLanguageId = JsonPath.read(obj, "$.['"+name+"'].url_query.LanguageId");
        String sSectionId = JsonPath.read(obj, "$.['"+name+"'].url_query.SectionId");
        return desktopUrl.toString()+"default.aspx?ItemID="+sItemID+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }



/*    public String getPreviewPageUrl(JSONObject obj, String name) {
        String sPressReleaseID = JsonPath.read(obj, "$.['"+name+"'].url_query.PressReleaseID");
        String sLanguageId = JsonPath.read(obj, "$.['"+name+"'].url_query.LanguageId");
        String sSectionId = JsonPath.read(obj, "$.['"+name+"'].url_query.SectionId");
        return desktopUrl.toString()+"preview/preview.aspx?PressReleaseID="+sPressReleaseID+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }*/

}
