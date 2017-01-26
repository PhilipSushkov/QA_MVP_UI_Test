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
import util.Functions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by philipsushkov on 2017-01-24.
 */

public class CheckPageAdd extends AbstractSpec {

    private static By pageAdminMenuButton;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static PageAdd pageAdd;

    private static String sPathToFile, sDataFileJson, sNewPagesJson;
    private static JSONParser parser;

    @BeforeTest
    public void setUp() throws Exception {
        pageAdminMenuButton = By.xpath(propUIPageAdmin.getProperty("btnMenu_PageAdmin"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        pageAdd = new PageAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUIPageAdmin.getProperty("dataPath_PageAdmin");
        sDataFileJson = propUIPageAdmin.getProperty("json_CreatePageData");
        sNewPagesJson = propUIPageAdmin.getProperty("json_NewPagesData");

        parser = new JSONParser();
        Functions.ClearJSONfile(sPathToFile + sNewPagesJson);

        loginPage.loginUser();
    }

    @Test(dataProvider="pageData")
    public void checkAddPage(String pageName) throws Exception {
        String workflowState = "In Progress";
        dashboard.openPageFromCommonTasks(pageAdminMenuButton);

        Assert.assertEquals(pageAdd.createNewPage(pageName), workflowState, "New Page didn't create properly");
        Assert.assertTrue(pageAdd.previewNewPage(), "Preview of New Page didn't work properly");
        Assert.assertTrue(pageAdd.publicNewPage(), "Public site shouldn't show the content of New Page when it is not published yet");

        dashboard.openPageFromCommonTasks(pageAdminMenuButton);
        Assert.assertTrue(pageAdd.listNewPage(), "New Page isn't found on Pablic Page List");
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

    @AfterTest
    public void tearDown() {
        //dashboard.logoutFromAdmin();
        driver.quit();
    }

}
