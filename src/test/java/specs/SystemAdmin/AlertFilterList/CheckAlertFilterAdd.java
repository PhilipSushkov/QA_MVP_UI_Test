package specs.SystemAdmin.AlertFilterList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.SystemAdmin.AlertFilterList.AlertFilterAdd;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by philipsushkov on 2017-02-07.
 */

public class CheckAlertFilterAdd extends AbstractSpec {
    private static By systemAdminMenuButton, alertFilterListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static AlertFilterAdd alertFilterAdd;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String DATA="getData";

    @BeforeTest
    public void setUp() throws Exception {
        systemAdminMenuButton = By.xpath(propUISystemAdmin.getProperty("btnMenu_SystemAdmin"));
        alertFilterListMenuItem = By.xpath(propUISystemAdmin.getProperty("itemMenu_AlertFilterList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        alertFilterAdd = new AlertFilterAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUISystemAdmin.getProperty("dataPath_AlertFilterList");
        sDataFileJson = propUISystemAdmin.getProperty("json_AlertFilterData");

        parser = new JSONParser();

        loginPage.loginUser();
    }


    @Test(dataProvider=DATA, priority=1)
    public void saveAlertFilter(JSONObject data) throws Exception {
        String sFilterName = data.get("filter_name").toString();
        String expectedTitleEdit = "Alert Filter Edit";
        String expectedTitleList = "Alert Filter List";

        dashboard.openPageFromMenu(systemAdminMenuButton, alertFilterListMenuItem);

        Assert.assertEquals(alertFilterAdd.getTitle(), expectedTitleEdit, "Actual Alert Filter Edit page Title doesn't match to expected");
        Assert.assertEquals(alertFilterAdd.saveAlertFilter(data, sFilterName), expectedTitleList, "New Alert Filter didn't save properly");
    }


    @Test(dataProvider=DATA, priority=2)
    public void checkAlertFilter(JSONObject data) throws Exception {
        String sFilterName = data.get("filter_name").toString();
        //System.out.println(data.get("filter_name").toString());

        dashboard.openPageFromMenu(systemAdminMenuButton, alertFilterListMenuItem);

        Assert.assertTrue(alertFilterAdd.checkAlertFilter(sFilterName), "New Alert Filter didn't find in Alert Filter List (after Save)");
    }


    @Test(dataProvider=DATA, priority=3)
    public void removeAlertFilter(JSONObject data) throws Exception {
        //System.out.println(" --- " + new Object(){}.getClass().getEnclosingMethod().getName() + " --- ");
        String sFilterName = data.get("filter_name").toString();

        dashboard.openPageFromMenu(systemAdminMenuButton, alertFilterListMenuItem);

        Assert.assertTrue(alertFilterAdd.removeAlertFilter(sFilterName), "New Alert Filter shouldn't be shown in Alert Filter List (after Delete)");
    }


    @DataProvider
    public Object[][] getData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray jsonArray = (JSONArray) jsonObject.get("alert_filter");
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
        //driver.quit();
    }

}
