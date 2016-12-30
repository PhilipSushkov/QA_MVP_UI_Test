package specs.EmailAdmin.MailingListMessages;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.Dashboard.Dashboard;
import pageobjects.EmailAdmin.MailingListMessages.MailingListMessagesView;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-12-30.
 */

public class CheckMailingListMessagesView extends AbstractSpec {
    private static By emailAdminMenuButton, mailingListMessagesMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static MailingListMessagesView mailingListMessagesView;

    @BeforeTest
    public void setUp() throws Exception {
        emailAdminMenuButton = By.xpath(propUIEmailAdmin.getProperty("btnMenu_EmailAdmin"));
        mailingListMessagesMenuItem = By.xpath(propUIEmailAdmin.getProperty("btnMenu_MailingListMessages"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        mailingListMessagesView = new MailingListMessagesView(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkMailingListMessagesView() throws Exception {
        final String expectedTitle = "Mailing List Messages View";
        final Integer expectedQuantity = 1;

        dashboard.openPageFromMenu(emailAdminMenuButton, mailingListMessagesMenuItem);

        Assert.assertNotNull(mailingListMessagesView.getUrl());
        Assert.assertEquals(mailingListMessagesView.getTitle(), expectedTitle, "Mailing List Messages View page Title doesn't match to expected");

        //System.out.println(mailingListMessagesView.getTitleQuantity().toString());
        Assert.assertTrue(expectedQuantity <= mailingListMessagesView.getTitleQuantity(), "Mailing List Name Quantity is less than expected: "+expectedQuantity);
        Assert.assertNotNull(mailingListMessagesView.getSearchField(), "Search field doesn't exist");
        Assert.assertNotNull(mailingListMessagesView.getSearchButton(), "Search button doesn't exist");
        Assert.assertNotNull(mailingListMessagesView.getMailingListsSelect(), "Mailing Lists select doesn't exist");
        Assert.assertNotNull(mailingListMessagesView.getYearDataListLink(), "Year Data List links don't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
