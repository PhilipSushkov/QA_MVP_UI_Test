package specs.EmailAdmin.Subscribers;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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

    @Before
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
        Assert.assertEquals("Actual Mailing List Users page Title doesn't match to expected", expectedTitle, mailingListUsers.getTitle());

        //System.out.println(new MailingListUsers(driver).getTitleQuantity().toString());
        Assert.assertTrue("Actual Email Address Quantity is less than expected: "+expectedQuantity, expectedQuantity <= mailingListUsers.getTitleQuantity() );
        Assert.assertNotNull("Mailing List Users Pagination doesn't exist", mailingListUsers.getMailingListUsersPagination() );

        Assert.assertNotNull("Keyword field doesn't exist", mailingListUsers.getKeywordField() );
        Assert.assertNotNull("All List selection doesn't exist", mailingListUsers.getAllListSelect() );
        Assert.assertNotNull("All Categories selection doesn't exist", mailingListUsers.getAllCategoriesSelect() );
        Assert.assertNotNull("Search button doesn't exist", mailingListUsers.getSearchButton() );
        Assert.assertNotNull("Export List link doesn't exist", mailingListUsers.getExportListLink() );
        Assert.assertNotNull("Send To List link doesn't exist", mailingListUsers.getSendToListLink() );
        Assert.assertNotNull("Import List link doesn't exist", mailingListUsers.getImportListLink() );
        Assert.assertNotNull("Bulk Delete link doesn't exist", mailingListUsers.getBulkDeleteLink() );
        Assert.assertNotNull("Letter List links don't exist", mailingListUsers.getLetterListLink() );

    }

    @After
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
