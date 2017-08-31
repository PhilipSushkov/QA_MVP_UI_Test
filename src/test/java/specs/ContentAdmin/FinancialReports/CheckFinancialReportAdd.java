package specs.ContentAdmin.FinancialReports;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.ContentAdmin.FinancialReports.FinancialReportAdd;
import pageobjects.ContentAdmin.FinancialReports.RelatedDocumentAdd;
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
    private static RelatedDocumentAdd relatedDocumentAdd;

    private static String sPathToFile, sDataFileJson, sFinancialReportTitle, sFinancialReportYear, sFinancialReportType;
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
        relatedDocumentAdd = new RelatedDocumentAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUIContentAdmin.getProperty("dataPath_FinancialReportList");
        sDataFileJson = propUIContentAdmin.getProperty("json_FinancialReportData");

        parser = new JSONParser();

        loginPage.loginUser();
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        dashboard.openPageFromMenu(contentAdminMenuButton, financialReportsMenuItem);
    }

    @Test(dataProvider=DATA, priority=1, enabled=false)
    public void saveFinancialReport(JSONObject data) {
        String expectedTitleEdit = "Financial Report Edit";

        getFinancialReportTitle(data);
        Assert.assertEquals(financialReportAdd.getTitle(), expectedTitleEdit, "Actual "+PAGE_NAME+" Edit page Title doesn't match to expected");
        Assert.assertEquals(financialReportAdd.saveFinancialReport(data, sFinancialReportTitle), WorkflowState.IN_PROGRESS.state(), "New "+PAGE_NAME+" didn't save properly");
    }

    @Test(dataProvider=DATA, priority=2, enabled=false)
    public void saveRelatedDocument(JSONObject data) throws InterruptedException {
        getFinancialReportTitle(data);
        Assert.assertEquals(relatedDocumentAdd.saveRelatedDocument(data, sFinancialReportTitle), WorkflowState.IN_PROGRESS.state(), "New " + PAGE_NAME + " Related Document doesn't save properly");
    }

    @Test(dataProvider=DATA, priority=3, enabled=false)
    public void saveAndSubmitFinancialReport(JSONObject data) throws InterruptedException {
        getFinancialReportTitle(data);
        Assert.assertEquals(financialReportAdd.saveAndSubmitFinancialReport(data, sFinancialReportTitle), WorkflowState.FOR_APPROVAL.state(), "New " + PAGE_NAME + " doesn't submit properly (after Save And Submit)");
        Assert.assertTrue(financialReportAdd.checkFinancialReport(data, sFinancialReportTitle), "Submitted New "+ PAGE_NAME +" data doesn't fit well to entry data (after Save and Submit)");
    }

    @Test(dataProvider=DATA, priority=4, enabled=false)
    public void saveAndSubmitRelatedDocument(JSONObject data) throws InterruptedException {
        getFinancialReportTitle(data);
        Assert.assertEquals(relatedDocumentAdd.saveAndSubmitRelatedDocument(data, sFinancialReportTitle), WorkflowState.FOR_APPROVAL.state(), "New " + PAGE_NAME + " Related Document doesn't submit properly (after Save And Submit)");
        Assert.assertTrue(relatedDocumentAdd.checkRelatedDocument(data, sFinancialReportTitle), "Submitted New "+ PAGE_NAME +" Related Document data doesn't fit well to entry data (after Save and Submit)");
    }

    @Test(dataProvider=DATA, priority=5, enabled=false)
    public void publishFinancialReport(JSONObject data) throws InterruptedException {
        getFinancialReportTitle(data);
        Assert.assertEquals(financialReportAdd.publishFinancialReport(data, sFinancialReportTitle), WorkflowState.LIVE.state(), "New "+ PAGE_NAME +" doesn't publish properly (after Publish)");
    }

    @Test(dataProvider=DATA, priority=6)
    public void publishRelatedDocument(JSONObject data) throws InterruptedException {
        getFinancialReportTitle(data);
        Assert.assertEquals(relatedDocumentAdd.publishRelatedDocument(data, sFinancialReportTitle), WorkflowState.LIVE.state(), "New "+ PAGE_NAME +" Related Document doesn't publish properly (after Publish)");
    }

    @Test(dataProvider=DATA, priority=7)
    public void revertFinancialReport(JSONObject data) throws InterruptedException {
        getFinancialReportTitle(data);
        Assert.assertEquals(financialReportAdd.changeAndSubmitFinancialReport(data, sFinancialReportTitle), WorkflowState.FOR_APPROVAL.state(), "Some fields of New "+ PAGE_NAME +" didn't change properly (after Save and Submit)");
        Assert.assertEquals(financialReportAdd.revertToLiveFinancialReport(sFinancialReportTitle), WorkflowState.LIVE.state(), "Couldn't revert to Live changes for New "+ PAGE_NAME);
        Assert.assertTrue(financialReportAdd.checkFinancialReport(data, sFinancialReportTitle), "Submitted New "+ PAGE_NAME +" data doesn't fit well to entry data (after Revert To Live)");
    }

    @Test(dataProvider=DATA, priority=8)
    public void revertRelatedDocument(JSONObject data) throws InterruptedException {
        getFinancialReportTitle(data);
        Assert.assertEquals(relatedDocumentAdd.changeAndSubmitRelatedDocument(data, sFinancialReportTitle), WorkflowState.FOR_APPROVAL.state(), "Some fields of New "+ PAGE_NAME +" Related Document didn't change properly (after Save and Submit)");
        Assert.assertEquals(relatedDocumentAdd.revertToLiveRelatedDocument(data, sFinancialReportTitle), WorkflowState.LIVE.state(), "Couldn't revert to Live changes for New "+ PAGE_NAME + " Related Document");
        Assert.assertTrue(relatedDocumentAdd.checkRelatedDocument(data, sFinancialReportTitle), "Submitted New "+ PAGE_NAME +" data doesn't fit well to entry data (after Revert To Live)");
    }

    @Test(dataProvider=DATA, priority=9)
    public void changeAndSubmitFinancialReport(JSONObject data) throws Exception {
        getFinancialReportTitle(data);
        Assert.assertEquals(financialReportAdd.changeAndSubmitFinancialReport(data, sFinancialReportTitle), WorkflowState.FOR_APPROVAL.state(), "Some fields of New "+ PAGE_NAME +" didn't change properly (after Save and Submit)");
        Assert.assertTrue(financialReportAdd.checkFinancialReportCh(data, sFinancialReportTitle), "Submitted New "+ PAGE_NAME +" changes don't fit well to change data (after Change And Submit)");
    }

    @Test(dataProvider=DATA, priority=10)
    public void changeAndSubmitRelatedDocument(JSONObject data) throws Exception {
        getFinancialReportTitle(data);
        Assert.assertEquals(relatedDocumentAdd.changeAndSubmitRelatedDocument(data, sFinancialReportTitle), WorkflowState.FOR_APPROVAL.state(), "Some fields of New "+ PAGE_NAME +" didn't change properly (after Save and Submit)");
        Assert.assertTrue(relatedDocumentAdd.checkRelatedDocumentCh(data, sFinancialReportTitle), "Submitted New "+ PAGE_NAME +" changes don't fit well to change data (after Change And Submit)");
    }

    @Test(dataProvider=DATA, priority=11)
    public void publishEditFinancialReport(JSONObject data) throws InterruptedException {
        getFinancialReportTitle(data);
        Assert.assertEquals(financialReportAdd.publishFinancialReport(data, sFinancialReportTitle), WorkflowState.LIVE.state(), "New "+ PAGE_NAME +" doesn't publish properly (after Publish)");
    }

    @Test(dataProvider=DATA, priority=12)
    public void publishEditRelatedDocument(JSONObject data) throws InterruptedException {
        getFinancialReportTitle(data);
        Assert.assertEquals(relatedDocumentAdd.publishRelatedDocument(data, sFinancialReportTitle), WorkflowState.LIVE.state(), "New "+ PAGE_NAME +" doesn't publish properly (after Publish)");
    }

    @Test(dataProvider=DATA, priority=13)
    public void deleteRelatedDocument(JSONObject data) throws Exception {
        getFinancialReportTitle(data);
        Assert.assertEquals(relatedDocumentAdd.setupAsDeletedRelatedDocument(data, sFinancialReportTitle), WorkflowState.DELETE_PENDING.state(), "New "+ PAGE_NAME +" didn't setup as Deleted properly");
    }

    @Test(dataProvider=DATA, priority=14)
    public void removeRelatedDocument(JSONObject data) throws Exception {
        getFinancialReportTitle(data);
        Assert.assertEquals(relatedDocumentAdd.removeRelatedDocument(data, sFinancialReportTitle), WorkflowState.NEW_ITEM.state(), "Couldn't remove New "+ PAGE_NAME +". Something went wrong.");
    }

    @Test(dataProvider=DATA, priority=15)
    public void deleteFinancialReport(JSONObject data) throws Exception {
        getFinancialReportTitle(data);
        Assert.assertEquals(financialReportAdd.setupAsDeletedFinancialReport(sFinancialReportTitle), WorkflowState.DELETE_PENDING.state(), "New "+ PAGE_NAME +" didn't setup as Deleted properly");
    }

    @Test(dataProvider=DATA, priority=16)
    public void removeFinancialReport(JSONObject data) throws Exception {
        getFinancialReportTitle(data);
        Assert.assertEquals(financialReportAdd.removeFinancialReport(data, sFinancialReportTitle), WorkflowState.NEW_ITEM.state(), "Couldn't remove New "+ PAGE_NAME +". Something went wrong.");
    }

    public void getFinancialReportTitle(JSONObject data) {
        sFinancialReportYear = data.get(FINANCIAL_REPORT_YEAR).toString();
        sFinancialReportType = data.get(FINANCIAL_REPORT_TYPE).toString();

        if (sFinancialReportType.equals(ANNUAL_REPORT)) {
            sFinancialReportTitle = sFinancialReportYear + " " + sFinancialReportType;
        } else {
            sFinancialReportTitle = sFinancialReportType + " " + sFinancialReportYear;
        }
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
