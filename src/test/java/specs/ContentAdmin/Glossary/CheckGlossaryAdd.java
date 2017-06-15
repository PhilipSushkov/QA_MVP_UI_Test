package specs.ContentAdmin.Glossary;

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
import pageobjects.ContentAdmin.Glossary.GlossaryAdd;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.PageAdmin.WorkflowState;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by andyp on 2017-06-13.
 */
public class CheckGlossaryAdd extends AbstractSpec {

    private static By contentAdminMenuButton, glossaryListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static GlossaryAdd glossaryAdd;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String DATA="getData", GLOSSARY_TITLE="title", PAGE_NAME="Glossary";

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        glossaryListMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_Glossary"));
        //addNewLink = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        glossaryAdd = new GlossaryAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUIContentAdmin.getProperty("dataPath_glossaryListData");
        sDataFileJson = propUIContentAdmin.getProperty("json_glossaryListData");

        parser = new JSONParser();

        loginPage.loginUser();
        }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        dashboard.openPageFromMenu(contentAdminMenuButton, glossaryListMenuItem);
        }

    @Test(dataProvider=DATA, priority=1)
    public void saveGlossary(JSONObject data) {
        String sGlossaryName = data.get(GLOSSARY_TITLE).toString();
        String expectedTitleEdit = "Glossary Edit";

        Assert.assertEquals(glossaryAdd.getTitle(), expectedTitleEdit, "Actual "+PAGE_NAME+" Edit page Title doesn't match to expected");
        Assert.assertEquals(glossaryAdd.saveGlossary(data, sGlossaryName), WorkflowState.IN_PROGRESS.state(), "New "+PAGE_NAME+" didn't save properly");
    }

    @Test(dataProvider=DATA, priority=2)
    public void saveAndSubmitGlossary(JSONObject data) throws InterruptedException {
        String sGlossaryName = data.get(GLOSSARY_TITLE).toString();

        Assert.assertEquals(glossaryAdd.saveAndSubmitGlossary(data, sGlossaryName), WorkflowState.FOR_APPROVAL.state(), "New " + PAGE_NAME + " doesn't submit properly (after Save And Submit)");
        Assert.assertTrue(glossaryAdd.checkGlossary(data, sGlossaryName), "Submitted New "+ PAGE_NAME +" data doesn't fit well to entry data (after Save and Submit)");
    }

    @Test(dataProvider=DATA, priority=3)
    public void publishGlossary(JSONObject data) throws InterruptedException {
        String sGlossaryName = data.get(GLOSSARY_TITLE).toString();
        Assert.assertEquals(glossaryAdd.publishGlossary(data, sGlossaryName), WorkflowState.LIVE.state(), "New "+ PAGE_NAME +" doesn't publish properly (after Publish)");
    }

    @Test(dataProvider=DATA, priority=4)
    public void revertGlossary(JSONObject data) throws InterruptedException {
        String sGlossaryName = data.get(GLOSSARY_TITLE).toString();

        Assert.assertEquals(glossaryAdd.changeAndSubmitGlossary(data, sGlossaryName), WorkflowState.FOR_APPROVAL.state(), "Some fields of New "+ PAGE_NAME +" didn't change properly (after Save and Submit)");
        Assert.assertEquals(glossaryAdd.revertToLiveGlossary(sGlossaryName), WorkflowState.LIVE.state(), "Couldn't revert to Live changes for New "+ PAGE_NAME);
        Assert.assertTrue(glossaryAdd.checkGlossary(data, sGlossaryName), "Submitted New "+ PAGE_NAME +" data doesn't fit well to entry data (after Revert To Live)");
    }

    @Test(dataProvider=DATA, priority=5)
    public void changeAndSubmitGlossary(JSONObject data) throws Exception {
        String sGlossaryName = data.get(GLOSSARY_TITLE).toString();

        Assert.assertEquals(glossaryAdd.changeAndSubmitGlossary(data, sGlossaryName), WorkflowState.FOR_APPROVAL.state(), "Some fields of New "+ PAGE_NAME +" didn't change properly (after Save and Submit)");
        Assert.assertTrue(glossaryAdd.checkGlossaryCh(data, sGlossaryName), "Submitted New "+ PAGE_NAME +" changes don't fit well to change data (after Change And Submit)");
    }

    @Test(dataProvider=DATA, priority=6)
    public void publishEditGlossary(JSONObject data) throws InterruptedException {
        String sGlossaryName = data.get(GLOSSARY_TITLE).toString();
        Assert.assertEquals(glossaryAdd.publishGlossary(data, sGlossaryName), WorkflowState.LIVE.state(), "New "+ PAGE_NAME +" doesn't publish properly (after Publish)");
    }

    @Test(dataProvider=DATA, priority=7)
    public void deleteGlossary(JSONObject data) throws Exception {
        String sGlossaryName = data.get(GLOSSARY_TITLE).toString();
        Assert.assertEquals(glossaryAdd.setupAsDeletedGlossary(sGlossaryName), WorkflowState.DELETE_PENDING.state(), "New "+ PAGE_NAME +" didn't setup as Deleted properly");
    }

    @Test(dataProvider=DATA, priority=8)
    public void removeGlossary(JSONObject data) throws Exception {
        String sGlossaryName = data.get(GLOSSARY_TITLE).toString();
        Assert.assertEquals(glossaryAdd.removeGlossary(data, sGlossaryName), WorkflowState.NEW_ITEM.state(), "Couldn't remove New "+ PAGE_NAME +". Something went wrong.");
    }

    @DataProvider
    public Object[][] getData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray jsonArray = (JSONArray) jsonObject.get("glossary");
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
}
