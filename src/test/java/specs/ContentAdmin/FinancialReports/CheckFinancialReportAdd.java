package specs.ContentAdmin.FinancialReports;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.ContentAdmin.FinancialReports.FinancialReportAdd;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.PageAdmin.WorkflowState;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by philipsushkov on 2017-06-07.
 */

public class CheckFinancialReportAdd extends AbstractSpec {
    private static By contentAdminMenuButton, financialReportsMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static FinancialReportAdd financialReportAdd;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String DATA="getData", PAGE_NAME="Financial Report";
    private final String FINANCIAL_REPORT_YEAR="report_year", FINANCIAL_REPORT_TYPE="report_type";

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        financialReportsMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_FinancialReports"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        financialReportAdd = new FinancialReportAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUISiteAdmin.getProperty("dataPath_FinancialReportList");
        sDataFileJson = propUISiteAdmin.getProperty("json_FinancialReportData");

        parser = new JSONParser();

        loginPage.loginUser();
    }

}
