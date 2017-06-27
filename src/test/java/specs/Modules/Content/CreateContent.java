package specs.Modules.Content;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Modules.Content.CreateEvent;
import pageobjects.Modules.Content.CreatePresentation;
import pageobjects.Modules.Content.CreatePressRelease;

import pageobjects.PageAdmin.WorkflowState;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by zacharyk on 2017-06-26.
 */
public class CreateContent extends AbstractSpec {

    // DESIGN CONTENT-DEPENDENT TESTS TO CONTINUE WORKING IF NEW CONTENT IS ADDED \\

    private static By addNewPresentationButton, addNewPressReleaseButton, addNewEventButton;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static CreatePresentation createPresentation;
    private static CreatePressRelease createPressRelease;
    private static CreateEvent createEvent;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String PRESENTATION_DATA = "presentationData", PRESS_RELEASE_DATA = "pressReleaseData", EVENT_DATA = "eventData";

    @BeforeTest
    public void setUp() throws Exception {

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        createPresentation = new CreatePresentation(driver);
        createPressRelease = new CreatePressRelease(driver);
        createEvent = new CreateEvent(driver);

        addNewPresentationButton = By.xpath(propUICommon.getProperty("btn_AddPresentation"));
        addNewPressReleaseButton = By.xpath(propUICommon.getProperty("btn_AddPressRelease"));
        addNewEventButton = By.xpath(propUICommon.getProperty("btn_AddEvent"));

        sPathToFile = System.getProperty("user.dir") + propUIModules.getProperty("dataPath_Content");
        sDataFileJson = propUIModules.getProperty("json_ContentData");

        parser = new JSONParser();

        loginPage.loginUser();
    }

    @Test(dataProvider=PRESENTATION_DATA)
    public void createPresentations(JSONObject data) throws Exception {
        dashboard.openPageFromCommonTasks(addNewPresentationButton);
        Assert.assertEquals(createPresentation.savePresentation(data), WorkflowState.IN_PROGRESS.state());
        Assert.assertEquals(createPresentation.saveAndSubmitPresentation(data), WorkflowState.FOR_APPROVAL.state());
        Assert.assertEquals(createPresentation.publishPresentation(data.get("headline").toString()), WorkflowState.LIVE.state());
    }

    @Test(dataProvider=PRESENTATION_DATA)
    public void removePresentations(JSONObject data) throws Exception {
        Assert.assertEquals(createPresentation.setupAsDeletedPresentation(data.get("headline").toString()), WorkflowState.DELETE_PENDING.state());
        Assert.assertEquals(createPresentation.removePresentation(data.get("headline").toString()), WorkflowState.NEW_ITEM.state());
    }

    @Test(dataProvider=PRESS_RELEASE_DATA)
    public void createPressRelease(JSONObject data) throws Exception {
        dashboard.openPageFromCommonTasks(addNewPressReleaseButton);
        Assert.assertEquals(createPressRelease.savePressRelease(data), WorkflowState.IN_PROGRESS.state());
        Assert.assertEquals(createPressRelease.saveAndSubmitPressRelease(data), WorkflowState.FOR_APPROVAL.state());
        Assert.assertEquals(createPressRelease.publishPressRelease(data.get("headline").toString()), WorkflowState.LIVE.state());
    }

    @Test(dataProvider=PRESS_RELEASE_DATA)
    public void removePressRelease(JSONObject data) throws Exception {
        Assert.assertEquals(createPressRelease.setupAsDeletedPressRelease(data.get("headline").toString()), WorkflowState.DELETE_PENDING.state());
        Assert.assertEquals(createPressRelease.removePressRelease(data.get("headline").toString()), WorkflowState.NEW_ITEM.state());
    }

    @Test(dataProvider=EVENT_DATA)
    public void createEvents(JSONObject data) throws Exception {
        dashboard.openPageFromCommonTasks(addNewEventButton);
        Assert.assertEquals(createEvent.saveEvent(data), WorkflowState.IN_PROGRESS.state());
        Assert.assertEquals(createEvent.saveAndSubmitEvent(data), WorkflowState.FOR_APPROVAL.state());
        Assert.assertEquals(createEvent.publishEvent(data.get("headline").toString()), WorkflowState.LIVE.state());
    }

    @Test(dataProvider=EVENT_DATA)
    public void removeEvents(JSONObject data) throws Exception {
        Assert.assertEquals(createEvent.setupAsDeletedEvent(data.get("headline").toString()), WorkflowState.DELETE_PENDING.state());
        Assert.assertEquals(createEvent.removeEvent(data.get("headline").toString()), WorkflowState.NEW_ITEM.state());
    }

    private Object[][] genericProvider(String type) {
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray pageData = (JSONArray) jsonObject.get(type);
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

    @DataProvider
    public Object[][] presentationData() {
        return genericProvider("presentation");
    }

    @DataProvider
    public Object[][] pressReleaseData() {
        return genericProvider("press_release");
    }

    @DataProvider
    public Object[][] eventData() {
        return genericProvider("event");
    }
}
