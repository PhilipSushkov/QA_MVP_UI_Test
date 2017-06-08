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

    private final String DATA="getData", PAGE_NAME="Financial Report", ANNUAL_REPORT="Annual Report";
    private final String FINANCIAL_REPORT_YEAR="report_year", FINANCIAL_REPORT_TYPE="report_type";

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        financialReportsMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_FinancialReports"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        financialReportAdd = new FinancialReportAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUIContentAdmin.getProperty("dataPath_FinancialReportList");
        sDataFileJson = propUIContentAdmin.getProperty("json_FinancialReportData");

        parser = new JSONParser();

        loginPage.loginUser();
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        dashboard.openPageFromMenu(contentAdminMenuButton, financialReportsMenuItem);
    }

    @Test(dataProvider=DATA, priority=1)
    public void saveFinancialReport(JSONObject data) {
        String sFinancialReportTitle;
        String sFinancialReportYear = data.get(FINANCIAL_REPORT_YEAR).toString();
        String sFinancialReportType = data.get(FINANCIAL_REPORT_TYPE).toString();

        if (sFinancialReportType.equals(ANNUAL_REPORT)) {
            sFinancialReportTitle = sFinancialReportYear + " " + sFinancialReportType;
        } else {
            sFinancialReportTitle = sFinancialReportType + " " + sFinancialReportYear;
        }

        String expectedTitleEdit = "Financial Report Edit";

        Assert.assertEquals(financialReportAdd.getTitle(), expectedTitleEdit, "Actual "+PAGE_NAME+" Edit page Title doesn't match to expected");
        Assert.assertEquals(financialReportAdd.saveFinancialReport(data, sFinancialReportTitle), WorkflowState.IN_PROGRESS.state(), "New "+PAGE_NAME+" didn't save properly");
    }

    @DataProvider
    public Object[][] getData() {

        try {
            System.out.println(sPathToFile + sDataFileJson);
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray jsonArray = (JSONArray) jsonObject.get("financial_report");
            ArrayList<Object> zoom = new ArrayList();

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject pageObj = (JSONObject) jsonArray.get(i);
                if (Boolean.parseBoolean(pageObj.get("do_assertions").toString())) {
                    zoom.add(jsonArray.get(i));
                }
            }

            Object[][] data = new Object[zoom.size()][1];
            for (int i = 0; i < zoom.size(); i++) {
                data[i][0] = zoom.get(i);
            }

            return data;

        }  catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
    }

}
