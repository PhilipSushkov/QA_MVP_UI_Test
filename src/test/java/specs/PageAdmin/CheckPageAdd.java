package specs.PageAdmin;

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
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.PageAdmin.PageAdd;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by philipsushkov on 2017-01-24.
 */

public class CheckPageAdd extends AbstractSpec {

    private static By pageAdminMenuButton;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static PageAdd pageAdd;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    @BeforeTest
    public void setUp() throws Exception {
        pageAdminMenuButton = By.xpath(propUIPageAdmin.getProperty("btnMenu_PageAdmin"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        pageAdd = new PageAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUIPageAdmin.getProperty("dataPath_PageAdmin");
        sDataFileJson = propUIPageAdmin.getProperty("json_CreatePageData");

        parser = new JSONParser();

        loginPage.loginUser();
    }

    @Test(dataProvider="pageData")
    public void checkAddPage(String pageName) throws Exception {
        dashboard.openPageFromCommonTasks(pageAdminMenuButton);

        Assert.assertTrue(pageAdd.createNewPage(pageName), "New Page didn't create properly");

    }

    @DataProvider
    public Object[][] pageData() {

        try {
            Object obj = parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray pageData = (JSONArray) jsonObject.get("page_names");
            Object[][] pageName = new Object[pageData.size()][1];

            for (int i = 0; i < pageData.size(); i++) {
                pageName[i][0] = (pageData.get(i)).toString();
            }

            return pageName;

        }  catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

}
