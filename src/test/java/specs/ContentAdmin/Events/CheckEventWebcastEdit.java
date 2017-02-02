package specs.ContentAdmin.Events;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.ContentAdmin.Events.EventWebcastEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2017-01-03.
 */

public class CheckEventWebcastEdit extends AbstractSpec {

    private static By contentAdminMenuButton, eventsMenuItem, userAddNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static EventWebcastEdit eventWebcastEdit;

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        eventsMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_Events"));
        userAddNewLink = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        eventWebcastEdit = new EventWebcastEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(contentAdminMenuButton, eventsMenuItem, userAddNewLink);
    }

    @Test
    public void checkEventWebcastEdit() throws Exception {
        final String expectedTitle = "Event / Webcast Edit";

        Assert.assertNotNull(eventWebcastEdit.getUrl());
        Assert.assertEquals(eventWebcastEdit.getTitle(), expectedTitle, "Actual Event/Webcast Edit page Title doesn't match to expected");

        Assert.assertNotNull(eventWebcastEdit.getEventBtnWrap(), "Event button wrap doesn't exist");
        Assert.assertNotNull(eventWebcastEdit.getWebcastParticipantsBtnWrap(), "Webcast Participants button wrap doesn't exist");

        Assert.assertNotNull(eventWebcastEdit.getStartDateInput(), "Start Date input doesn't exist");
        Assert.assertTrue(eventWebcastEdit.getStartTimeSet(), "Start Time elements don't exist");

        Assert.assertNotNull(eventWebcastEdit.getEndDateInput(), "End Date input doesn't exist");
        Assert.assertTrue(eventWebcastEdit.getEndTimeSet(), "End Time elements don't exist");

        Assert.assertNotNull(eventWebcastEdit.getTitleInput(), "Title input doesn't exist");
        Assert.assertNotNull(eventWebcastEdit.getYourPageuUrlLabel(), "Your page URL label doesn't exist");
        Assert.assertNotNull(eventWebcastEdit.getChangeUrlLink(), "Change URL link doesn't exist");
        Assert.assertNotNull(eventWebcastEdit.getTagsInput(), "Tags input doesn't exist");
        Assert.assertNotNull(eventWebcastEdit.getLocationInput(), "Location input doesn't exist");
        Assert.assertNotNull(eventWebcastEdit.getRadEditorFrame(), "RAD Editor frame doesn't exist");

        Assert.assertTrue(eventWebcastEdit.getChkBoxSet(), "Checkbox elements don't exist");

        Assert.assertNotNull(eventWebcastEdit.getUrlOverrideInput(), "Url Override field doesn't exist");

        Assert.assertTrue(eventWebcastEdit.getRelatedSet(), "Related elements don't exist");

        Assert.assertTrue(eventWebcastEdit.getSpeakersSet(), "Speakers elements don't exist");
        Assert.assertTrue(eventWebcastEdit.getAttachmentsSet(), "Attachments elements don't exist");

        Assert.assertNotNull(eventWebcastEdit.getSaveAndSubmitButton(), "Save And Submit button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }
}
