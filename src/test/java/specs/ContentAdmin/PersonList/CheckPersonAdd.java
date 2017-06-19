package specs.ContentAdmin.PersonList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.ContentAdmin.PersonList.PersonAdd;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.PageAdmin.WorkflowState;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by zacharyk on 2017-06-15.
 */
public class CheckPersonAdd extends AbstractSpec {
    private static By contentAdminMenuButton, personListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static PersonAdd personAdd;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String DATA="getData", PERSON_NAME = "first_name", PAGE_NAME = "Person";

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        personListMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_PersonList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        personAdd = new PersonAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUIContentAdmin.getProperty("dataPath_PersonList");
        sDataFileJson = propUIContentAdmin.getProperty("json_PersonData");

        parser = new JSONParser();

        loginPage.loginUser();
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        dashboard.openPageFromMenu(contentAdminMenuButton, personListMenuItem);
    }

    @Test(dataProvider=DATA, priority=1)
    public void savePerson(JSONObject data) {
        String sPersonName = data.get(PERSON_NAME).toString();
        String expectedTitleEdit = "Person Edit";

        Assert.assertEquals(personAdd.getTitle(), expectedTitleEdit, "Actual "+PAGE_NAME+" Edit page Title doesn't match to expected");
        Assert.assertEquals(personAdd.savePerson(data, sPersonName), WorkflowState.IN_PROGRESS.state(), "New "+PAGE_NAME+" didn't save properly");
    }

    @Test(dataProvider=DATA, priority=2)
    public void saveAndSubmitPerson(JSONObject data) throws InterruptedException {
        String sPersonName = data.get(PERSON_NAME).toString();

        Assert.assertEquals(personAdd.saveAndSubmitPerson(data, sPersonName), WorkflowState.FOR_APPROVAL.state(), "New " + PAGE_NAME + " doesn't submit properly (after Save And Submit)");
        Assert.assertTrue(personAdd.checkPerson(data, sPersonName), "Submitted New "+ PAGE_NAME +" data doesn't fit well to entry data (after Save and Submit)");
    }

    @Test(dataProvider=DATA, priority=3)
    public void publishPerson(JSONObject data) throws InterruptedException {
        String sPersonName = data.get(PERSON_NAME).toString();
        Assert.assertEquals(personAdd.publishPerson(data, sPersonName), WorkflowState.LIVE.state(), "New "+ PAGE_NAME +" doesn't publish properly (after Publish)");
    }

    @Test(dataProvider=DATA, priority=4)
    public void revertPerson(JSONObject data) throws InterruptedException {
        String sPersonName = data.get(PERSON_NAME).toString();

        Assert.assertEquals(personAdd.changeAndSubmitPerson(data, sPersonName), WorkflowState.FOR_APPROVAL.state(), "Expected workflow state to be For Approval");
        Assert.assertEquals(personAdd.revertToLivePerson(sPersonName), WorkflowState.LIVE.state(), "Couldn't revert to Live changes for New "+ PAGE_NAME);
        Assert.assertTrue(personAdd.checkPerson(data, sPersonName), "Submitted New "+ PAGE_NAME +" data doesn't fit well to entry data (after Revert To Live)");
    }

    @Test(dataProvider=DATA, priority=5)
    public void changeAndSubmitPerson(JSONObject data) throws Exception {
        String sPersonName = data.get(PERSON_NAME).toString();

        Assert.assertEquals(personAdd.changeAndSubmitPerson(data, sPersonName), WorkflowState.FOR_APPROVAL.state(), "Some fields of New "+ PAGE_NAME +" didn't change properly (after Save and Submit)");
        Assert.assertTrue(personAdd.checkLookupCh(data, sPersonName), "Submitted New "+ PAGE_NAME +" changes don't fit well to change data (after Change And Submit)");
    }

    @Test(dataProvider=DATA, priority=6)
    public void publishEditPerson(JSONObject data) throws InterruptedException {
        String sPersonName = data.get(PERSON_NAME).toString();
        Assert.assertEquals(personAdd.publishPerson(data, sPersonName), WorkflowState.LIVE.state(), "New "+ PAGE_NAME +" doesn't publish properly (after Publish)");
    }

    @Test(dataProvider=DATA, priority=7)
    public void deletePerson(JSONObject data) throws Exception {
        String sPersonName = data.get(PERSON_NAME).toString();
        Assert.assertEquals(personAdd.setupAsDeletedPerson(sPersonName), WorkflowState.DELETE_PENDING.state(), "New "+ PAGE_NAME +" didn't setup as Deleted properly");
    }

    @Test(dataProvider=DATA, priority=8)
    public void removePerson(JSONObject data) throws Exception {
        String sPersonName = data.get(PERSON_NAME).toString();
        Assert.assertEquals(personAdd.removePerson(data, sPersonName), WorkflowState.NEW_ITEM.state(), "Couldn't remove New "+ PAGE_NAME +". Something went wrong.");
    }

    @DataProvider
    public Object[][] getData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray jsonArray = (JSONArray) jsonObject.get("person");
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
