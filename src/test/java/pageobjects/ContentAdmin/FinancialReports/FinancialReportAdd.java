package pageobjects.ContentAdmin.FinancialReports;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
import static specs.AbstractSpec.propUIContentAdmin;

/**
 * Created by philipsushkov on 2017-06-07.
 */

public class FinancialReportAdd extends AbstractPageObject {
    private static By moduleTitle, reportYearSelect, reportTypeSelect, coverImageInput;
    private static By filingDateInput, tagsInput, activeCheckbox, addNewRelatedDocLink, documentsTable;
    private static By activeChk, saveBtn, revertBtn, cancelBtn, deleteBtn, addNewLink, publishBtn;
    private static By workflowStateSpan, commentsTxt, successMsg, saveAndSubmitBtn, currentContentSpan;
    private static String sPathToFile, sFileJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String PAGE_NAME="Financial Report";

    public FinancialReportAdd(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("spanModule_Title"));
        reportYearSelect = By.xpath(propUIContentAdmin.getProperty("select_ReportYear"));
        reportTypeSelect = By.xpath(propUIContentAdmin.getProperty("select_ReportType"));
        coverImageInput = By.xpath(propUIContentAdmin.getProperty("input_CoverImage"));
        filingDateInput = By.xpath(propUIContentAdmin.getProperty("input_FilingDate"));
        tagsInput = By.xpath(propUIContentAdmin.getProperty("input_Tags"));
        addNewRelatedDocLink = By.xpath(propUIContentAdmin.getProperty("href_AddNewRelatedDoc"));
        documentsTable = By.xpath(propUIContentAdmin.getProperty("table_Documents"));
        activeChk = By.xpath(propUIContentAdmin.getProperty("chk_Active"));
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

        parser = new JSONParser();

        sPathToFile = System.getProperty("user.dir") + propUIContentAdmin.getProperty("dataPath_FinancialReportList");
        sFileJson = propUIContentAdmin.getProperty("json_FinancialReport");
    }

    public String getTitle() {
        findElement(addNewLink).click();
        waitForElement(moduleTitle);
        String sTitle = getText(moduleTitle);
        findElement(cancelBtn).click();
        return sTitle;
    }

    public String saveFinancialReport(JSONObject data, String name) {
        String report_year, report_type, cover_image, filing_date, tags;
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

            report_year = data.get("report_year").toString();
            findElement(reportYearSelect).sendKeys(report_year);
            jsonObj.put("report_year", report_year);

            report_type = data.get("report_type").toString();
            findElement(reportTypeSelect).sendKeys(report_type);
            jsonObj.put("report_type", report_type);

            cover_image = data.get("cover_image").toString();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement elemSrc =  driver.findElement(coverImageInput);
            js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", elemSrc, "value", cover_image);
            jsonObj.put("cover_image", cover_image);

            filing_date = data.get("filing_date").toString();
            findElement(filingDateInput).clear();
            findElement(filingDateInput).sendKeys(filing_date);
            jsonObj.put("filing_date", filing_date);

            tags = data.get("tags").toString();
            findElement(tagsInput).clear();
            findElement(tagsInput).sendKeys(tags);
            jsonObj.put("tags", tags);

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

    public String saveRelatedDocument(JSONObject data, String name) {


        return null;
    }

}
