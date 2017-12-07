package specs.SystemAdmin.PressReleaseFilterList;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SystemAdmin.PressReleaseFilterList.PressReleaseFilterEdit;
import specs.AbstractSpec;


public class CheckPressReleaseFilterEdit extends AbstractSpec {
    private static By systemAdminMenuButton, pressReleaseFilterListMenuItem, userAddNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static PressReleaseFilterEdit pressReleaseFilterEdit;

    @BeforeTest
    public void setUp() throws Exception {
        systemAdminMenuButton = By.xpath(propUISystemAdmin.getProperty("btnMenu_SystemAdmin"));
        pressReleaseFilterListMenuItem = By.xpath(propUISystemAdmin.getProperty("itemMenu_PressReleaseFilterList"));
        userAddNewLink = By.xpath(propUISystemAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        pressReleaseFilterEdit = new PressReleaseFilterEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(systemAdminMenuButton, pressReleaseFilterListMenuItem, userAddNewLink);
    }

    @Test
    public void checkPressReleaseFilterEdit() throws Exception {
        final String expectedTitle = "Press Release Filter Edit";

        Assert.assertNotNull(pressReleaseFilterEdit.getUrl());
        Assert.assertEquals(pressReleaseFilterEdit.getTitle(), expectedTitle, "Actual Press Release Filter Edit page Title doesn't match to expected");

        Assert.assertNotNull(pressReleaseFilterEdit.getFilterNameInp(), "Filter Name field doesn't exist");

        Assert.assertNotNull(pressReleaseFilterEdit.getANYTermsTxt(), "ANY Terms text field doesn't exist");
        Assert.assertNotNull(pressReleaseFilterEdit.getALLTermsTxt(), "ALL Terms text field doesn't exist");
        Assert.assertNotNull(pressReleaseFilterEdit.getNOTTermsTxt(), "NOT Terms text field doesn't exist");

        Assert.assertNotNull(pressReleaseFilterEdit.getANYIconPlus(), "ANY Plus icon doesn't exist");
        Assert.assertNotNull(pressReleaseFilterEdit.getALLIconPlus(), "ALL Plus icon doesn't exist");
        Assert.assertNotNull(pressReleaseFilterEdit.getNOTIconPlus(), "NOT Plus icon doesn't exist");

        Assert.assertTrue(pressReleaseFilterEdit.getNewswires(), "Some Newswires channels don't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }
}
