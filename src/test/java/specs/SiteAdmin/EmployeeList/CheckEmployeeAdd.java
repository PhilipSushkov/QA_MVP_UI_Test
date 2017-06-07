package specs.SiteAdmin.EmployeeList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SiteAdmin.EmployeeList.EmployeeAdd;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by andyp on 2017-05-29.
 */

public class CheckEmployeeAdd extends AbstractSpec{

    private static By siteAdminMenuButton, employeeListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static EmployeeAdd employeeAdd;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String DATA="getData", EMPLOYEE_EMAIL = "email", PAGE_NAME="Employee List";

    @BeforeTest
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        employeeListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_EmployeeList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        employeeAdd = new EmployeeAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUISiteAdmin.getProperty("dataPath_EmployeeList");
        sDataFileJson = propUISiteAdmin.getProperty("json_EmployeeListData");

        parser = new JSONParser();

        loginPage.loginUser();
    }
    @BeforeMethod
    public void beforeMethod() throws Exception {
        dashboard.openPageFromMenu(siteAdminMenuButton, employeeListMenuItem);
    }

    @Test(dataProvider=DATA, priority=1)
    public void saveEmployeeList(JSONObject data) throws IOException {
        //Checking Employee List vs. Employee Edit
        String sEmployeeEmail = data.get(EMPLOYEE_EMAIL).toString();
        String expectedTitleList = "Employee List";
        String expectedTitleEdit = "Employee Edit";

        Assert.assertEquals(employeeAdd.getTitle(), expectedTitleEdit, "Actual "+PAGE_NAME+" Edit page Title doesn't match to expected");
        Assert.assertEquals(employeeAdd.saveEmployeeList(data, sEmployeeEmail), expectedTitleList, "New "+PAGE_NAME+" didn't save properly");
    }

    @Test(dataProvider=DATA, priority=2)
    public void checkEmployeeList(JSONObject data) {
        String sEmployeeEmail = data.get(EMPLOYEE_EMAIL).toString();
        Assert.assertTrue(employeeAdd.checkEmployeeList(data, sEmployeeEmail), "New "+PAGE_NAME+" doesn't fit to entry data (after Save)");
    }

    @Test(dataProvider=DATA, priority=3)
    public void editEmployeeList(JSONObject data) throws Exception {
        String sEmployeeEmail = data.get(EMPLOYEE_EMAIL).toString();

        Assert.assertTrue(employeeAdd.editEmployeeList(data, sEmployeeEmail), PAGE_NAME+" didn't change properly (after Save)");
    }

    @Test(dataProvider=DATA, priority=4)
    public void checkEmployeeListCh(JSONObject data) {
        String sEmployeeEmail = data.get(EMPLOYEE_EMAIL).toString();
        Assert.assertTrue(employeeAdd.checkEmployeeListCh(data, sEmployeeEmail), "New "+PAGE_NAME+" doesn't fit to change data (after Edit)");
    }

    @Test(dataProvider=DATA, priority=5)
    public void removeEmployeeList(JSONObject data) {
        String sEmployeeEmail = data.get(EMPLOYEE_EMAIL).toString();
        Assert.assertTrue(employeeAdd.removeEmployee(data, sEmployeeEmail), "New "+PAGE_NAME+" shouldn't be shown in "+PAGE_NAME+" List (after Delete)");
    }

    @DataProvider
    public Object[][] getData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray jsonArray = (JSONArray) jsonObject.get("employee_list");
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

    //Save Employee
}
