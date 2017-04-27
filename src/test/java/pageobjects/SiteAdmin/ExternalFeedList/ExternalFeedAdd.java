package pageobjects.SiteAdmin.ExternalFeedList;

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
 * Created by philipsushkov on 2017-04-27.
 */

public class ExternalFeedAdd extends AbstractPageObject {
    private static By moduleTitle, feedSelect, tagListInput, stockExchangeInput;
    private static By languageSelect, categorySelect, compIdInput, stockSymbolInput;
    private static By saveBtn, cancelBtn, deleteBtn, addNewLink, activeChk;
    private static By workflowStateSpan, commentsTxt, successMsg, currentContentSpan;
    private static String sPathToFile, sFileJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String PAGE_NAME="External Feed", PRESS_RELEASE = "Press Release (Acquire)", EOD_MAIL = "End Of Day Stock Quote Email";

    public ExternalFeedAdd(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISiteAdmin.getProperty("spanModule_Title"));
        feedSelect = By.xpath(propUISiteAdmin.getProperty("select_Feed"));
        tagListInput = By.xpath(propUISiteAdmin.getProperty("input_TagList"));
        languageSelect = By.xpath(propUISiteAdmin.getProperty("select_Language"));
        categorySelect = By.xpath(propUISiteAdmin.getProperty("select_Category"));
        compIdInput = By.xpath(propUISiteAdmin.getProperty("chk_CompId"));
        stockExchangeInput = By.xpath(propUISiteAdmin.getProperty("input_StockExchange"));
        stockSymbolInput = By.xpath(propUISiteAdmin.getProperty("input_StockSymbol"));
        saveBtn = By.xpath(propUISiteAdmin.getProperty("btn_Save"));
        cancelBtn = By.xpath(propUISiteAdmin.getProperty("btn_Cancel"));
        deleteBtn = By.xpath(propUISiteAdmin.getProperty("btn_Delete"));
        addNewLink = By.xpath(propUISiteAdmin.getProperty("input_AddNew"));
        workflowStateSpan = By.xpath(propUISiteAdmin.getProperty("select_WorkflowState"));
        commentsTxt = By.xpath(propUISiteAdmin.getProperty("txtarea_Comments"));
        successMsg = By.xpath(propUISiteAdmin.getProperty("msg_Success"));
        activeChk = By.xpath(propUISiteAdmin.getProperty("chk_Active"));
        currentContentSpan = By.xpath(propUISiteAdmin.getProperty("span_CurrentContent"));

        parser = new JSONParser();

        sPathToFile = System.getProperty("user.dir") + propUISiteAdmin.getProperty("dataPath_ExternalFeedList");
        sFileJson = propUISiteAdmin.getProperty("json_ExternalFeed");
    }

    public String getTitle() {
        findElement(addNewLink).click();
        waitForElement(moduleTitle);
        String sTitle = getText(moduleTitle);
        findElement(cancelBtn).click();
        return sTitle;
    }

    public String saveExternalFeed(JSONObject data, String name) {
        String feed, tag_list, language, category, comp_id;
        String stock_exchange, stock_symbol;
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

            feed = data.get("feed").toString();
            findElement(feedSelect).sendKeys(feed);
            jsonObj.put("feed", feed);

            tag_list = data.get("tag_list").toString();
            findElement(tagListInput).sendKeys(tag_list);
            jsonObj.put("tag_list", tag_list);

            switch (feed) {
                case PRESS_RELEASE:
                    language = data.get("language").toString();
                    findElement(languageSelect).sendKeys(language);
                    jsonObj.put("language", language);

                    category = data.get("category").toString();
                    findElement(categorySelect).sendKeys(category);
                    jsonObj.put("category", category);

                    comp_id = data.get("comp_id").toString();
                    findElement(compIdInput).sendKeys(comp_id);
                    jsonObj.put("comp_id", comp_id);
                    break;

                case EOD_MAIL:
                    stock_exchange = data.get("stock_exchange").toString();
                    findElement(stockExchangeInput).sendKeys(stock_exchange);
                    jsonObj.put("stock_exchange", stock_exchange);

                    stock_symbol = data.get("stock_symbol").toString();
                    findElement(stockSymbolInput).sendKeys(stock_symbol);
                    jsonObj.put("stock_symbol", stock_symbol);
                    break;

                default:
                    break;
            }



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

            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(moduleTitle);

            System.out.println(name + ": "+PAGE_NAME+" has been created");
            return findElement(moduleTitle).getText();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getPageUrl (JSONObject obj, String name) {
        String  sExternalFeedID = JsonPath.read(obj, "$.['"+name+"'].url_query.ExternalFeedID");
        String  sLanguageId = JsonPath.read(obj, "$.['"+name+"'].url_query.LanguageId");
        String  sSectionId = JsonPath.read(obj, "$.['"+name+"'].url_query.SectionId");
        return desktopUrl.toString()+"default.aspx?ExternalFeedID="+sExternalFeedID+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }

}
