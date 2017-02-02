package specs.EmailAdmin.Compose;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.Dashboard.Dashboard;
import pageobjects.EmailAdmin.Compose.Compose;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;


/**
 * Created by philipsushkov on 2016-12-05.
 */

public class CheckCompose extends AbstractSpec {
    private static By emailAdminMenuButton, composeMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static Compose compose;

    @BeforeTest
    public void setUp() throws Exception {
        emailAdminMenuButton = By.xpath(propUIEmailAdmin.getProperty("btnMenu_EmailAdmin"));
        composeMenuItem = By.xpath(propUIEmailAdmin.getProperty("btnMenu_Compose"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        compose = new Compose(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkCompose() throws Exception {
        final String expectedTitle = "Mailing List Messages Admin";

        dashboard.openPageFromMenu(emailAdminMenuButton, composeMenuItem);

        Assert.assertNotNull(compose.getUrl());
        Assert.assertEquals(compose.getTitle(), expectedTitle, "Actual Mailing List Messages Admin page Title doesn't match to expected");

        Assert.assertNotNull(compose.getTemplateList(), "Template drop down list doesn't exist");
        Assert.assertNotNull(compose.getToList(), "To drop down list doesn't exist");
        Assert.assertNotNull(compose.getFromField(), "From field doesn't exist");
        Assert.assertNotNull(compose.getSubjectField(), "Subject field doesn't exist");
        Assert.assertNotNull(compose.getBodyTextArea(), "Body textarea doesn't exist");
        Assert.assertNotNull(compose.getCreatedByField(), "Created By field doesn't exist");
        Assert.assertNotNull(compose.getSendTestEmailButton(), "Send Test Email button doesn't exist");
        Assert.assertNotNull(compose.getSaveButton(), "Save button doesn't exist");

    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}