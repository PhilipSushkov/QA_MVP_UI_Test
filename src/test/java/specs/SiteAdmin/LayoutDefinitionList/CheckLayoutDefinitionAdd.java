package specs.SiteAdmin.LayoutDefinitionList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.PageAdmin.WorkflowState;
import pageobjects.SiteAdmin.LayoutDefinitionList.LayoutDefinitionAdd;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by philipsushkov on 2017-04-21.
 */

public class CheckLayoutDefinitionAdd extends AbstractSpec {
    private static By siteAdminMenuButton, layoutDefinitionListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static LayoutDefinitionAdd layoutDefinitionAdd;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String DATA="getData", LAYOUT_DEFINITION_NAME="friendly_name", PAGE_NAME="Layout Definition";

    @BeforeTest
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        layoutDefinitionListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_LayoutDefinitionList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        layoutDefinitionAdd = new LayoutDefinitionAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUISiteAdmin.getProperty("dataPath_LayoutDefinitionList");
        sDataFileJson = propUISiteAdmin.getProperty("json_LayoutDefinitionData");

        parser = new JSONParser();

        loginPage.loginUser();
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        dashboard.openPageFromMenu(siteAdminMenuButton, layoutDefinitionListMenuItem);
    }

    @Test(dataProvider=DATA, priority=1)
    public void saveLayoutDefinition(JSONObject data) {
        String sLayoutDefinitionName = data.get(LAYOUT_DEFINITION_NAME).toString();
        String expectedTitleEdit = "Layout Definition Edit";

        Assert.assertEquals(layoutDefinitionAdd.getTitle(), expectedTitleEdit, "Actual "+PAGE_NAME+" Edit page Title doesn't match to expected");
        Assert.assertEquals(layoutDefinitionAdd.saveLayoutDefinition(data, sLayoutDefinitionName), WorkflowState.IN_PROGRESS.state(), "New "+PAGE_NAME+" didn't save properly");
    }

    @Test(dataProvider=DATA, priority=2)
    public void saveAndSubmitLayoutDefinition(JSONObject data) throws InterruptedException {
        String sLayoutDefinitionName = data.get(LAYOUT_DEFINITION_NAME).toString();

        Assert.assertEquals(layoutDefinitionAdd.saveAndSubmitLayoutDefinition(data, sLayoutDefinitionName), WorkflowState.FOR_APPROVAL.state(), "New " + PAGE_NAME + " doesn't submit properly (after Save And Submit)");
        //Assert.assertTrue(layoutDefinitionAdd.checkLayoutDefinition(data, sLayoutDefinitionName), "Submitted New "+ PAGE_NAME +" data doesn't fit well to entry data (after Save and Submit)");
    }

    @DataProvider
    public Object[][] getData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray jsonArray = (JSONArray) jsonObject.get("layout_definition");
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
