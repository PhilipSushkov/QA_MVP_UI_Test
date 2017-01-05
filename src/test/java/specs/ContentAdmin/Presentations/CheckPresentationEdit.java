package specs.ContentAdmin.Presentations;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.ContentAdmin.Presentations.PresentationEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2017-01-04.
 */

public class CheckPresentationEdit extends AbstractSpec {

    private static By contentAdminMenuButton, presentationsMenuItem, addNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static PresentationEdit presentationEdit;

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        presentationsMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_Presentations"));
        addNewLink = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        presentationEdit = new PresentationEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(contentAdminMenuButton, presentationsMenuItem, addNewLink);
    }

    @Test
    public void checkPresentationEdit() throws Exception {
        final String expectedTitle = "Presentation Edit";

        Assert.assertNotNull(presentationEdit.getUrl());
        Assert.assertEquals(presentationEdit.getTitle(), expectedTitle, "Actual Presentation Edit page Title doesn't match to expected");

        Assert.assertTrue(presentationEdit.getDateTimeSet(), "Date and Time elements don't exist");

        Assert.assertNotNull(presentationEdit.getTitleInput(), "Title input doesn't exist");
        Assert.assertNotNull(presentationEdit.getYourPageuUrlLabel(), "Your page URL label doesn't exist");
        Assert.assertNotNull(presentationEdit.getChangeUrlLink(), "Change URL link doesn't exist");
        Assert.assertNotNull(presentationEdit.getTagsInput(), "Tags input doesn't exist");
        Assert.assertNotNull(presentationEdit.getRadEditorFrame(), "RAD Editor frame doesn't exist");
        Assert.assertNotNull(presentationEdit.getPresentationFileInput(), "Presentation File field doesn't exist");

        Assert.assertTrue(presentationEdit.getThumbnailSet(), "Thumbnail Path and Image don't exist");

        Assert.assertNotNull(presentationEdit.getURLOverrideInput(), "URL Override field doesn't exist");

        Assert.assertTrue(presentationEdit.getChkBoxSet(), "Checkbox elements don't exist");

        Assert.assertTrue(presentationEdit.getRelatedFilesSet(), "Related Files elements don't exist");
        Assert.assertTrue(presentationEdit.getSpeakersSet(), "Speakers elements don't exist");

        Assert.assertNotNull(presentationEdit.getSaveAndSubmitButton(), "Save And Submit button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
