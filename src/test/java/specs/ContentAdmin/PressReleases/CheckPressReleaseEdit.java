package specs.ContentAdmin.PressReleases;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.ContentAdmin.PressReleases.PressReleaseEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2017-01-05.
 */

public class CheckPressReleaseEdit extends AbstractSpec {

    private static By contentAdminMenuButton, pressReleasesMenuButton, addNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static PressReleaseEdit pressReleaseEdit;

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        pressReleasesMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_PressReleases"));
        addNewLink = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        pressReleaseEdit = new PressReleaseEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(contentAdminMenuButton, pressReleasesMenuButton, addNewLink);
    }

    @Test
    public void checkPressReleaseEdit() throws Exception {
        final String expectedTitle = "Press Release Edit";

        Assert.assertNotNull(pressReleaseEdit.getUrl());
        Assert.assertEquals(pressReleaseEdit.getTitle(), expectedTitle, "Actual Press Release Edit page Title doesn't match to expected");

        Assert.assertTrue(pressReleaseEdit.getDateTimeSet(), "Date and Time elements don't exist");

        Assert.assertNotNull(pressReleaseEdit.getTitleInput(), "Title input doesn't exist");
        Assert.assertNotNull(pressReleaseEdit.getYourPageuUrlLabel(), "Your page URL label doesn't exist");
        Assert.assertNotNull(pressReleaseEdit.getChangeUrlLink(), "Change URL link doesn't exist");
        Assert.assertNotNull(pressReleaseEdit.getCategorySelect(), "Category select doesn't exist");
        Assert.assertNotNull(pressReleaseEdit.getTagsInput(), "Tags input doesn't exist");
        Assert.assertNotNull(pressReleaseEdit.getRadEditorFrame(), "RAD Editor frame doesn't exist");
        Assert.assertNotNull(pressReleaseEdit.getRelatedDocInput(), "Related Document field doesn't exist");

        Assert.assertTrue(pressReleaseEdit.getThumbnailSet(), "Thumbnail Path and Image don't exist");

        Assert.assertNotNull(pressReleaseEdit.getRelatedProjSelect(), "Related Project select doesn't exist");
        Assert.assertNotNull(pressReleaseEdit.getURLOverrideInput(), "URL Override field doesn't exist");

        Assert.assertTrue(pressReleaseEdit.getChkBoxSet(), "Checkbox elements don't exist");

        Assert.assertNotNull(pressReleaseEdit.getSaveAndSubmitButton(), "Save And Submit button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
