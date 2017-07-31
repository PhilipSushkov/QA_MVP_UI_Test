package specs.Modules.Content;

import com.sun.xml.internal.bind.v2.TODO;
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
import pageobjects.Modules.Content.*;

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

    private static By addNewPresentationButton, addNewPressReleaseButton, addNewEventButton, siteAdminMenuButton;
    private static By stockSplitMenuItem, contentAdminEditItem, faqMenuItem, jobPostingMenuItem, lookupListMenuItem;
    private static By contentAdminMenuButton, glossaryListMenuItem, quickLinkListMenuItem, personMenuItem, fastFactMenuItem;

    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static CreatePresentation createPresentation;
    private static CreatePressRelease createPressRelease;
    private static CreateEvent createEvent;
    private static CreateLookup createLookup;
    private static CreateGlossary createGlossary;
    private static CreateQuickLink createQuickLink;
    private static CreatePerson createPerson;
    private static CreateFastFact createFastFact;
    private static CreateStockSplit createStockSplit;
    private static CreateJobPosting createJobPosting;
    private static CreateFaq createFaq;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;


    private final String PRESENTATION_DATA = "presentationData", PRESS_RELEASE_DATA = "pressReleaseData", EVENT_DATA = "eventData";
    private final String LOOKUP_DATA = "lookupData", GLOSSARY_DATA = "glossaryData", QUICKLINK_DATA = "quickLinkData";
    private final String PRESENTATION_NAME = "presentation", PRESS_RELEASE_NAME = "press_release", EVENT_NAME = "event";
    private final String LOOKUP_NAME = "lookup", GLOSSARY_NAME = "glossary", QUICKLINK_NAME = "quicklink";
    private final String PERSON_DATA ="personData", FAST_FACT_DATA="fastFactData";
    private final String PERSON_NAME ="person", FAST_FACT_NAME = "fast_fact";
    private final String JOB_POSTING_DATA = "jobPostingData", JOB_POSTING_NAME = "job_posting";
    private final String FAQ_DATA = "faqData", FAQ_NAME = "faq";
    private final String STOCK_SPLIT_DATA = "stockSplitData", STOCK_SPLIT_NAME = "stock_split";

    @BeforeTest
    public void setUp() throws Exception {

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        createPresentation = new CreatePresentation(driver);
        createPressRelease = new CreatePressRelease(driver);
        createEvent = new CreateEvent(driver);
        createLookup = new CreateLookup(driver);
        createGlossary = new CreateGlossary(driver);
        createQuickLink = new CreateQuickLink(driver);
        createPerson = new CreatePerson(driver);
        createFastFact = new CreateFastFact(driver);
        createJobPosting = new CreateJobPosting(driver);
        createFaq = new CreateFaq(driver);
        createStockSplit = new CreateStockSplit(driver);

        addNewPresentationButton = By.xpath(propUICommon.getProperty("btn_AddPresentation"));
        addNewPressReleaseButton = By.xpath(propUICommon.getProperty("btn_AddPressRelease"));
        addNewEventButton = By.xpath(propUICommon.getProperty("btn_AddEvent"));
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        lookupListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_LookupList"));
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        contentAdminEditItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_ContentAdminEdit"));
        glossaryListMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_Glossary"));
        quickLinkListMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_QuickLinks"));
        personMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_PersonList"));
        fastFactMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_FastFact"));
        stockSplitMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_SplitList"));
        jobPostingMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_JobPosting"));
        faqMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_FaqList"));

        sPathToFile = System.getProperty("user.dir") + propUIModules.getProperty("dataPath_Content");
        sDataFileJson = propUIModules.getProperty("json_ContentData");

        parser = new JSONParser();

        loginPage.loginUser();
    }

    @Test(dataProvider=PRESENTATION_DATA, priority=1, enabled=false)
    public void createPresentations(JSONObject data) throws Exception {
        dashboard.openPageFromCommonTasks(addNewPresentationButton);
        Assert.assertEquals(createPresentation.savePresentation(data), WorkflowState.IN_PROGRESS.state());
        Assert.assertEquals(createPresentation.saveAndSubmitPresentation(data), WorkflowState.FOR_APPROVAL.state());
        Assert.assertEquals(createPresentation.publishPresentation(data.get("headline").toString()), WorkflowState.LIVE.state());
    }

    @Test(dataProvider=PRESENTATION_DATA, priority=2, enabled=false)
    public void removePresentations(JSONObject data) throws Exception {
        Assert.assertEquals(createPresentation.setupAsDeletedPresentation(data.get("headline").toString()), WorkflowState.DELETE_PENDING.state());
        Assert.assertEquals(createPresentation.removePresentation(data.get("headline").toString()), WorkflowState.NEW_ITEM.state());
    }

    @Test(dataProvider=PRESS_RELEASE_DATA, priority=3, enabled=false)
    public void createPressRelease(JSONObject data) throws Exception {
        dashboard.openPageFromCommonTasks(addNewPressReleaseButton);
        Assert.assertEquals(createPressRelease.savePressRelease(data), WorkflowState.IN_PROGRESS.state());
        Assert.assertEquals(createPressRelease.saveAndSubmitPressRelease(data), WorkflowState.FOR_APPROVAL.state());
        Assert.assertEquals(createPressRelease.publishPressRelease(data.get("headline").toString()), WorkflowState.LIVE.state());
    }

    @Test(dataProvider=PRESS_RELEASE_DATA, priority=4, enabled=false)
    public void removePressRelease(JSONObject data) throws Exception {
        Assert.assertEquals(createPressRelease.setupAsDeletedPressRelease(data.get("headline").toString()), WorkflowState.DELETE_PENDING.state());
        Assert.assertEquals(createPressRelease.removePressRelease(data.get("headline").toString()), WorkflowState.NEW_ITEM.state());
    }

    @Test(dataProvider=EVENT_DATA, priority=5, enabled=false)
    public void createEvents(JSONObject data) throws Exception {
        dashboard.openPageFromCommonTasks(addNewEventButton);
        Assert.assertEquals(createEvent.saveEvent(data), WorkflowState.IN_PROGRESS.state());
        Assert.assertEquals(createEvent.saveAndSubmitEvent(data), WorkflowState.FOR_APPROVAL.state());
        Assert.assertEquals(createEvent.publishEvent(data.get("headline").toString()), WorkflowState.LIVE.state());
    }

    @Test(dataProvider=EVENT_DATA, priority=6, enabled=false)
    public void removeEvents(JSONObject data) throws Exception {
        Assert.assertEquals(createEvent.setupAsDeletedEvent(data.get("headline").toString()), WorkflowState.DELETE_PENDING.state());
        Assert.assertEquals(createEvent.removeEvent(data.get("headline").toString()), WorkflowState.NEW_ITEM.state());
    }

    @Test(dataProvider=LOOKUP_DATA, priority=7, enabled = false)
    public void createLookups(JSONObject data) throws Exception {
        dashboard.openPageFromMenu(siteAdminMenuButton, lookupListMenuItem);
        Assert.assertEquals(createLookup.saveLookup(data), WorkflowState.IN_PROGRESS.state());
        Assert.assertEquals(createLookup.saveAndSubmitLookup(data), WorkflowState.FOR_APPROVAL.state());
        Assert.assertEquals(createLookup.publishLookup(data.get("lookup_text").toString()), WorkflowState.LIVE.state());
    }

    @Test(dataProvider=LOOKUP_DATA, priority=8, enabled = false)
    public void removeLookups(JSONObject data) throws Exception {
        Assert.assertEquals(createLookup.setupAsDeletedLookup(data.get("lookup_text").toString()), WorkflowState.DELETE_PENDING.state());
        Assert.assertEquals(createLookup.removeLookup(data.get("lookup_text").toString()), WorkflowState.NEW_ITEM.state());
    }

    // MUST WRITE METHOD TO ADD GLOSSARIES TO CONTENT LIST BECAUSE THEY AREN'T ADDED BY DEFAULT
    @Test(dataProvider=GLOSSARY_DATA, priority=9, enabled=true)
    public void createGlossaries(JSONObject data) throws Exception {
        dashboard.openContentPageFromMenu(contentAdminMenuButton, glossaryListMenuItem, "Glossary", siteAdminMenuButton, contentAdminEditItem);
        Assert.assertEquals(createGlossary.saveGlossary(data), WorkflowState.IN_PROGRESS.state());
        Assert.assertEquals(createGlossary.saveAndSubmitGlossary(data), WorkflowState.FOR_APPROVAL.state());
        Assert.assertEquals(createGlossary.publishGlossary(data.get("glossary_title").toString()), WorkflowState.LIVE.state());
    }

    @Test(dataProvider=GLOSSARY_DATA, priority=10, enabled=false)
    public void removeGlossaries(JSONObject data) throws Exception {
        Assert.assertEquals(createGlossary.setupAsDeletedGlossary(data.get("glossary_title").toString()), WorkflowState.DELETE_PENDING.state());
        Assert.assertEquals(createGlossary.removeGlossary(data.get("glossary_title").toString()), WorkflowState.NEW_ITEM.state());
    }

    @Test(dataProvider=QUICKLINK_DATA, priority=9, enabled=true)
    public void createQuickLinks(JSONObject data) throws Exception {
        dashboard.openContentPageFromMenu(contentAdminMenuButton, quickLinkListMenuItem, "Quick Links", siteAdminMenuButton, contentAdminEditItem);
        Assert.assertEquals(createQuickLink.saveQuickLink(data), WorkflowState.IN_PROGRESS.state());
        Assert.assertEquals(createQuickLink.saveAndSubmitQuickLink(data), WorkflowState.FOR_APPROVAL.state());
        Assert.assertEquals(createQuickLink.publishQuickLink(data.get("quicklink_description").toString()), WorkflowState.LIVE.state());
    }

    @Test(dataProvider=QUICKLINK_DATA, priority=10, enabled=false)
    public void removeQuickLinks(JSONObject data) throws Exception {
        Assert.assertEquals(createQuickLink.setupAsDeletedQuickLink(data.get("quicklink_description").toString()), WorkflowState.DELETE_PENDING.state());
        Assert.assertEquals(createQuickLink.removeQuickLink(data.get("quicklink_description").toString()), WorkflowState.NEW_ITEM.state());
    }

    @Test(dataProvider=PERSON_DATA, priority=11, enabled=true)
    public void createPerson(JSONObject data) throws Exception {
        dashboard.openContentPageFromMenu(contentAdminMenuButton, personMenuItem, "Person List", siteAdminMenuButton, contentAdminEditItem);
        Assert.assertEquals(createPerson.savePerson(data), WorkflowState.IN_PROGRESS.state());
        Assert.assertEquals(createPerson.saveAndSubmitPerson(data), WorkflowState.FOR_APPROVAL.state());
        Assert.assertEquals(createPerson.publishPerson(data.get("person_text").toString()), WorkflowState.LIVE.state());
    }

    @Test(dataProvider=PERSON_DATA, priority=12, enabled=false)
    public void removePerson(JSONObject data) throws Exception {
        Assert.assertEquals(createPerson.setupAsDeletedPerson(data.get("person_text").toString()), WorkflowState.DELETE_PENDING.state());
        Assert.assertEquals(createPerson.removePerson(data.get("person_text").toString()), WorkflowState.NEW_ITEM.state());
    }

    @Test(dataProvider=FAST_FACT_DATA, priority=13, enabled=true)
    public void createFastFact(JSONObject data) throws Exception {
        dashboard.openContentPageFromMenu(contentAdminMenuButton, fastFactMenuItem, "Fast Facts", siteAdminMenuButton, contentAdminEditItem);
        Assert.assertEquals(createFastFact.saveFastFact(data), WorkflowState.IN_PROGRESS.state());
        Assert.assertEquals(createFastFact.saveAndSubmitFastFact(data), WorkflowState.FOR_APPROVAL.state());
        Assert.assertEquals(createFastFact.publishFastFact(data.get("description").toString()), WorkflowState.LIVE.state());
    }

    @Test(dataProvider=FAST_FACT_DATA, priority=14, enabled=false)
    public void removeFastFact(JSONObject data) throws Exception {
        Assert.assertEquals(createFastFact.setupAsDeletedFastFact(data.get("description").toString()), WorkflowState.DELETE_PENDING.state());
        Assert.assertEquals(createFastFact.removeFastFact(data.get("description").toString()), WorkflowState.NEW_ITEM.state());
    }

    @Test(dataProvider=JOB_POSTING_DATA, priority=15, enabled=true)
    public void createJobPosting(JSONObject data) throws Exception {
        dashboard.openPageFromMenu(contentAdminMenuButton, jobPostingMenuItem);
        Assert.assertEquals(createJobPosting.saveJobPosting(data), WorkflowState.IN_PROGRESS.state());
        Assert.assertEquals(createJobPosting.saveAndSubmitJobPosting(data), WorkflowState.FOR_APPROVAL.state());
        Assert.assertEquals(createJobPosting.publishJobPosting(data.get("job_title").toString()), WorkflowState.LIVE.state());
    }

    @Test(dataProvider=JOB_POSTING_DATA, priority=16, enabled=true)
    public void removeJobPosting(JSONObject data) throws Exception {
        Assert.assertEquals(createJobPosting.setupAsDeletedJobPosting(data.get("description").toString()), WorkflowState.DELETE_PENDING.state());
        Assert.assertEquals(createJobPosting.removeJobPosting(data.get("description").toString()), WorkflowState.NEW_ITEM.state());
    }

    @Test(dataProvider=FAQ_DATA, priority=17, enabled=true)
    public void createFaq(JSONObject data) throws Exception {
        dashboard.openPageFromMenu(contentAdminMenuButton, faqMenuItem);
        Assert.assertEquals(createFaq.saveFaq(data), WorkflowState.IN_PROGRESS.state());
        Assert.assertTrue(createFaq.saveQuestion(data), "Questions did not save properly");
        Assert.assertEquals(createFaq.saveAndSubmitFaq(data), WorkflowState.FOR_APPROVAL.state());
        Assert.assertEquals(createFaq.publishFaq(data.get("name_EN").toString()), WorkflowState.LIVE.state());
    }

    @Test(dataProvider=FAQ_DATA, priority=18, enabled=true)
    public void removeFaq(JSONObject data) throws Exception {
        Assert.assertEquals(createFaq.setupAsDeletedFaq(data.get("name_EN").toString()), WorkflowState.DELETE_PENDING.state());
        Assert.assertEquals(createFaq.removeFaq(data.get("name_EN").toString()), WorkflowState.NEW_ITEM.state());
    }

    @Test(dataProvider=STOCK_SPLIT_DATA, priority=3, enabled=false)
    public void createStockSplit(JSONObject data) throws Exception {
        dashboard.openContentPageFromMenu(contentAdminMenuButton, stockSplitMenuItem, "Div Split List", siteAdminMenuButton, contentAdminEditItem);
        Assert.assertEquals(createStockSplit.saveStockSplit(data), WorkflowState.IN_PROGRESS.state());
        Assert.assertEquals(createStockSplit.saveAndSubmitStockSplit(data), WorkflowState.FOR_APPROVAL.state());
        Assert.assertEquals(createStockSplit.publishStockSplit(data.get("description").toString()), WorkflowState.LIVE.state());
    }

    @Test(dataProvider=STOCK_SPLIT_DATA, priority=14, enabled=false)
    public void removeStockSplit(JSONObject data) throws Exception {
        Assert.assertEquals(createStockSplit.setupAsDeletedStockSplit(data.get("description").toString()), WorkflowState.DELETE_PENDING.state());
        Assert.assertEquals(createStockSplit.removeStockSplit(data.get("description").toString()), WorkflowState.NEW_ITEM.state());
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
        return genericProvider(PRESENTATION_NAME);
    }

    @DataProvider
    public Object[][] pressReleaseData() {
        return genericProvider(PRESS_RELEASE_NAME);
    }

    @DataProvider
    public Object[][] eventData() {
        return genericProvider(EVENT_NAME);
    }
    
    @DataProvider
    public Object[][] lookupData() { return genericProvider(LOOKUP_NAME); }
    
    @DataProvider
    public Object[][] glossaryData() { return genericProvider(GLOSSARY_NAME); }
    
    @DataProvider
    public Object[][] quickLinkData() { return genericProvider(QUICKLINK_NAME); }

    @DataProvider
    public Object[][] personData() { return genericProvider(PERSON_NAME); }

    @DataProvider
    public Object[][] fastFactData() { return genericProvider(FAST_FACT_NAME); }

    @DataProvider
    public Object[][] jobPostingData() { return genericProvider(JOB_POSTING_NAME); }

    @DataProvider
    public Object[][] faqData() { return genericProvider(FAQ_NAME); }

    @DataProvider
    public Object[][] stockSplitData(){
        return genericProvider(STOCK_SPLIT_NAME);
    }
}
