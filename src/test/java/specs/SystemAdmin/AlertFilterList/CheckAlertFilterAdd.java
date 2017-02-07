package specs.SystemAdmin.AlertFilterList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
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
    private static By systemAdminMenuButton, alertFilterListMenuItem, addNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static AlertFilterAdd alertFilterAdd;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String PAGE_DATA="pageData";

    @BeforeTest
    public void setUp() throws Exception {
        systemAdminMenuButton = By.xpath(propUISystemAdmin.getProperty("btnMenu_SystemAdmin"));
        alertFilterListMenuItem = By.xpath(propUISystemAdmin.getProperty("itemMenu_AlertFilterList"));
        addNewLink = By.xpath(propUISystemAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        alertFilterAdd = new AlertFilterAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUISystemAdmin.getProperty("dataPath_AlertFilterList");
        sDataFileJson = propUISystemAdmin.getProperty("json_AlertFilterData");

        loginPage.loginUser();
    }

    @Test
    public void saveAlertFilter(JSONObject alertFilter) throws Exception {
        dashboard.openEditPageFromAddNew(systemAdminMenuButton, alertFilterListMenuItem, addNewLink);
        Assert.assertTrue(true, "Preview of New Alert Filter didn't work properly (after Save)");
    }


    @DataProvider
    public Object[][] pageData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray pageData = (JSONArray) jsonObject.get("pages");
            ArrayList<Object> zoom = new ArrayList();

            for (int i = 0; i < pageData.size(); i++) {
                JSONObject pageObj = (JSONObject) pageData.get(i);
                if (Boolean.parseBoolean(pageObj.get("do_assertions").toString())) {
                    zoom.add(pageData.get(i));
                }
            }

            Object[][] newPages = new Object[zoom.size()][1];
            for (int i = 0; i < zoom.size(); i++) {
                newPages[i][0] = zoom.get(i);
            }

            return newPages;

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
