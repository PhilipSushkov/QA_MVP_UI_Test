package specs.Modules;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.*;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Modules.PageForModules;
import pageobjects.PageAdmin.WorkflowState;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by philipsushkov on 2017-06-12.
 */

public class CreatePages extends AbstractSpec {
    private static By pageAdminMenuButton;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static PageForModules pageForModules;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String PAGE_DATA="pageData", SECTION_TITLE="section_title";

    @BeforeTest
    public void setUp() throws Exception {
        pageAdminMenuButton = By.xpath(propUIPageAdmin.getProperty("btnMenu_PageAdmin"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        pageForModules = new PageForModules(driver);

        sPathToFile = System.getProperty("user.dir") + propUIModules.getProperty("dataPath_Modules");
        sDataFileJson = propUIModules.getProperty("json_PagesData");

        parser = new JSONParser();

        loginPage.loginUser();
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        dashboard.openPageFromCommonTasks(pageAdminMenuButton);
    }

    @Test(dataProvider=PAGE_DATA, priority=1, enabled=false)
    public void createModulePage(JSONObject page) throws Exception {
        String pageName = page.get(SECTION_TITLE).toString();
        Assert.assertEquals(pageForModules.savePage(page, pageName), WorkflowState.IN_PROGRESS.state(), "New "+pageName+" Page didn't create properly");
        Assert.assertEquals(pageForModules.saveAndSubmitPage(page, pageName), WorkflowState.FOR_APPROVAL.state(), "Couldn't submit New "+pageName+" Page");
        Assert.assertEquals(pageForModules.publishPage(pageName), WorkflowState.LIVE.state(), "Couldn't publish New "+pageName+" Page properly");
    }

    @Test(dataProvider=PAGE_DATA, priority=2, enabled=true)
    public void removeModulePage(JSONObject page) throws Exception {
        String pageName = page.get(SECTION_TITLE).toString();
        Assert.assertEquals(pageForModules.setupAsDeletedPage(pageName), WorkflowState.DELETE_PENDING.state(), pageName+" Page didn't setup as Deleted properly");
        Assert.assertEquals(pageForModules.removePage(page, pageName), WorkflowState.NEW_ITEM.state(), "Couldn't remove "+pageName+" Page. Something went wrong.");
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
