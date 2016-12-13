package specs.EmailAdmin.Compose;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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

    @Before
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
        Assert.assertEquals("Actual Mailing List Messages Admin page Title doesn't match to expected", expectedTitle, compose.getTitle());

        Assert.assertNotNull("Template drop down list doesn't exist", compose.getTemplateList() );
        Assert.assertNotNull("To drop down list doesn't exist", compose.getToList() );
        Assert.assertNotNull("From field doesn't exist", compose.getFromField() );
        Assert.assertNotNull("Subject field doesn't exist", compose.getSubjectField() );
        Assert.assertNotNull("Body textarea doesn't exist", compose.getBodyTextArea() );
        Assert.assertNotNull("Created By field doesn't exist", compose.getCreatedByField() );
        Assert.assertNotNull("Send Test Email button doesn't exist", compose.getSendTestEmailButton() );
        Assert.assertNotNull("Save button doesn't exist", compose.getSaveButton() );

    }

    @After
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}