package specs.ContentAdmin.PersonList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.ContentAdmin.PersonList.PersonEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2017-01-05.
 */

public class CheckPersonEdit extends AbstractSpec {

    private static By contentAdminMenuButton, personListMenuItem, addNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static PersonEdit personEdit;

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        personListMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_PersonList"));
        addNewLink = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        personEdit = new PersonEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(contentAdminMenuButton, personListMenuItem, addNewLink);
    }

    @Test
    public void checkPersonEdit() throws Exception {
        final String expectedTitle = "Person Edit";

        Assert.assertNotNull(personEdit.getUrl());
        Assert.assertEquals(personEdit.getTitle(), expectedTitle, "Actual Person Edit page Title doesn't match to expected");

        Assert.assertNotNull(personEdit.getFirstNameInput(), "First Name input doesn't exist");
        Assert.assertNotNull(personEdit.getLastNameInput(), "Last Name input doesn't exist");
        Assert.assertNotNull(personEdit.getSuffixInput(), "Suffix input doesn't exist");
        Assert.assertNotNull(personEdit.getTitleInput(), "Title input doesn't exist");
        Assert.assertNotNull(personEdit.getDescTextarea(), "Description textarea doesn't exist");
        Assert.assertNotNull(personEdit.getCareerHighlightTextarea(), "Career Highlight textarea doesn't exist");
        Assert.assertNotNull(personEdit.getDepartmentSelect(), "Department select doesn't exist");
        Assert.assertNotNull(personEdit.getTagsInput(), "Tags input doesn't exist");

        /*

        Assert.assertTrue(pressReleaseEdit.getChkBoxSet(), "Checkbox elements don't exist");

        */

        Assert.assertNotNull(personEdit.getSaveAndSubmitButton(), "Save And Submit button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}

