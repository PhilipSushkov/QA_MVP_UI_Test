package specs.EmailAdmin.Subscribers;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.Dashboard.Dashboard;
import pageobjects.EmailAdmin.Subscribers.MailingListUsers;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-12-06.
 */

public class CheckMailingListUsers extends AbstractSpec {
    private static By emailAdminMenuButton, subscribersMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static MailingListUsers mailingListUsers;

    @BeforeTest
    public void setUp() throws Exception {
        emailAdminMenuButton = By.xpath(propUIEmailAdmin.getProperty("btnMenu_EmailAdmin"));
        subscribersMenuItem = By.xpath(propUIEmailAdmin.getProperty("btnMenu_Subscribers"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        mailingListUsers = new MailingListUsers(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkMailingListUsers() throws Exception {
        final String expectedTitle = "Mailing List Users";
        final Integer expectedQuantity = 15;

        dashboard.openPageFromMenu(emailAdminMenuButton, subscribersMenuItem);

        Assert.assertNotNull(mailingListUsers.getUrl());
        Assert.assertEquals(mailingListUsers.getTitle(), expectedTitle, "Actual Mailing List Users page Title doesn't match to expected");

        //System.out.println(new MailingListUsers(driver).getTitleQuantity().toString());
        Assert.assertTrue(expectedQuantity <= mailingListUsers.getTitleQuantity() , "Actual Email Address Quantity is less than expected: "+expectedQuantity);
        Assert.assertNotNull(mailingListUsers.getMailingListUsersPagination(), "Mailing List Users Pagination doesn't exist");

        Assert.assertNotNull(mailingListUsers.getKeywordField(), "Keyword field doesn't exist");
        Assert.assertNotNull(mailingListUsers.getAllListSelect(), "All List selection doesn't exist");
        Assert.assertNotNull(mailingListUsers.getAllCategoriesSelect(), "All Categories selection doesn't exist");
        Assert.assertNotNull(mailingListUsers.getSearchButton(), "Search button doesn't exist");
        Assert.assertNotNull(mailingListUsers.getExportListLink(), "Export List link doesn't exist");
        Assert.assertNotNull(mailingListUsers.getSendToListLink(), "Send To List link doesn't exist");
        Assert.assertNotNull(mailingListUsers.getImportListLink(), "Import List link doesn't exist");
        Assert.assertNotNull(mailingListUsers.getBulkDeleteLink(), "Bulk Delete link doesn't exist");
        Assert.assertNotNull(mailingListUsers.getLetterListLink(), "Letter List links don't exist");

    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
