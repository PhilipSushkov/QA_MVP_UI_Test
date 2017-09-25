package specs.ContentAdmin.DepartmentList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.ContentAdmin.DepartmentList.DepartmentAdd;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.PageAdmin.WorkflowState;
import pageobjects.SiteAdmin.LookupList.LookupAdd;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by charleszheng on 2017-09-25.
 */

public class CheckDepartmentAdd extends AbstractSpec {
    private static By contentAdminMenuButton, departmentListMenuButton;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static DepartmentAdd departmentAdd;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String DATA="getData", DEPARTMENT_NAME="department_name", PAGE_NAME="Department";

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        departmentListMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_DepartmentList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        departmentAdd = new DepartmentAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUIContentAdmin.getProperty("dataPath_DepartmentList");
        sDataFileJson = propUIContentAdmin.getProperty("json_DepartmentData");

        parser = new JSONParser();

        loginPage.loginUser();
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        dashboard.openPageFromMenu(contentAdminMenuButton, departmentListMenuButton);
    }

    @Test(dataProvider=DATA, priority=1)
    public void saveDepartment(JSONObject data) {
        String sDepartmentName = data.get(DEPARTMENT_NAME).toString();
        String expectedTitleEdit = "Department Edit";

        Assert.assertEquals(departmentAdd.getTitle(), expectedTitleEdit, "Actual "+PAGE_NAME+" Edit page Title doesn't match to expected");
        Assert.assertEquals(departmentAdd.saveDepartment(data, sDepartmentName), WorkflowState.IN_PROGRESS.state(), "New "+PAGE_NAME+" didn't save properly");
    }

    @Test(dataProvider=DATA, priority=2)
    public void saveAndSubmitDepartment(JSONObject data) throws InterruptedException {
        String sDepartmentName = data.get(DEPARTMENT_NAME).toString();

        Assert.assertEquals(departmentAdd.saveAndSubmitDepartment(data, sDepartmentName), WorkflowState.FOR_APPROVAL.state(), "New " + PAGE_NAME + " doesn't submit properly (after Save And Submit)");
        Assert.assertTrue(departmentAdd.checkDepartment(data, sDepartmentName), "Submitted New "+ PAGE_NAME +" data doesn't fit well to entry data (after Save and Submit)");
    }

    @Test(dataProvider=DATA, priority=3)
    public void publishDepartment(JSONObject data) throws InterruptedException {
        String sDepartmentName = data.get(DEPARTMENT_NAME).toString();
        Assert.assertEquals(departmentAdd.publishDepartment(data, sDepartmentName), WorkflowState.LIVE.state(), "New "+ PAGE_NAME +" doesn't publish properly (after Publish)");
    }

    @Test(dataProvider=DATA, priority=4)
    public void revertDepartment(JSONObject data) throws InterruptedException {
        String sDepartmentName = data.get(DEPARTMENT_NAME).toString();

        Assert.assertEquals(departmentAdd.changeAndSubmitDepartment(data, sDepartmentName), WorkflowState.FOR_APPROVAL.state(), "Some fields of New "+ PAGE_NAME +" didn't change properly (after Save and Submit)");
        Assert.assertEquals(departmentAdd.revertToLiveDepartment(sDepartmentName), WorkflowState.LIVE.state(), "Couldn't revert to Live changes for New "+ PAGE_NAME);
        Assert.assertTrue(departmentAdd.checkDepartment(data, sDepartmentName), "Submitted New "+ PAGE_NAME +" data doesn't fit well to entry data (after Revert To Live)");
    }

    @Test(dataProvider=DATA, priority=5)
    public void changeAndSubmitDepartment(JSONObject data) throws Exception {
        String sDepartmentName = data.get(DEPARTMENT_NAME).toString();

        Assert.assertEquals(departmentAdd.changeAndSubmitDepartment(data, sDepartmentName), WorkflowState.FOR_APPROVAL.state(), "Some fields of New "+ PAGE_NAME +" didn't change properly (after Save and Submit)");
        Assert.assertTrue(departmentAdd.checkDepartmentCh(data, sDepartmentName), "Submitted New "+ PAGE_NAME +" changes don't fit well to change data (after Change And Submit)");
    }

    @Test(dataProvider=DATA, priority=6)
    public void publishEditDepartment(JSONObject data) throws InterruptedException {
        String sDepartmentName = data.get(DEPARTMENT_NAME).toString();
        Assert.assertEquals(departmentAdd.publishDepartment(data, sDepartmentName), WorkflowState.LIVE.state(), "New "+ PAGE_NAME +" doesn't publish properly (after Publish)");
    }

    @Test(dataProvider=DATA, priority=7)
    public void deleteDepartment(JSONObject data) throws Exception {
        String sDepartmentName = data.get(DEPARTMENT_NAME).toString();
        Assert.assertEquals(departmentAdd.setupAsDeletedDepartment(sDepartmentName), WorkflowState.DELETE_PENDING.state(), "New "+ PAGE_NAME +" didn't setup as Deleted properly");
    }

    @Test(dataProvider=DATA, priority=8)
    public void removeDepartment(JSONObject data) throws Exception {
        String sDepartmentName = data.get(DEPARTMENT_NAME).toString();
        Assert.assertEquals(departmentAdd.removeDepartment(data, sDepartmentName), WorkflowState.NEW_ITEM.state(), "Couldn't remove New "+ PAGE_NAME +". Something went wrong.");
    }

    @DataProvider
    public Object[][] getData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray jsonArray = (JSONArray) jsonObject.get("department");
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
