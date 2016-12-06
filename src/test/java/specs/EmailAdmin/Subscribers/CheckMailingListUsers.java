package specs.EmailAdmin.Subscribers;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.Dashboard.Dashboard;
import pageobjects.EmailAdmin.Subscribers.MailingListUsers;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-12-06.
 */

public class CheckMailingListUsers extends AbstractSpec {

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkMailingListUsers() throws Exception {
        final String expectedTitle = "Mailing List Users";
        final Integer expectedQuantity = 15;

        Assert.assertNotNull(new Dashboard(driver).openMailingListUsers().getUrl());
        Assert.assertEquals("Actual Mailing List Users page Title doesn't match to expected", expectedTitle, new MailingListUsers(driver).getTitle());

        //System.out.println(new MailingListUsers(driver).getTitleQuantity().toString());
        Assert.assertTrue("Actual Email Address Quantity is less than expected: "+expectedQuantity, expectedQuantity <= new MailingListUsers(driver).getTitleQuantity() );
        Assert.assertNotNull("Mailing List Users Pagination doesn't exist", new MailingListUsers(driver).getMailingListUsersPagination() );

        Assert.assertNotNull("Keyword field doesn't exist", new MailingListUsers(driver).getKeywordField() );
        Assert.assertNotNull("All List selection doesn't exist", new MailingListUsers(driver).getAllListSelect() );
        Assert.assertNotNull("All Categories selection doesn't exist", new MailingListUsers(driver).getAllCategoriesSelect() );
        Assert.assertNotNull("Search button doesn't exist", new MailingListUsers(driver).getSearchButton() );
        Assert.assertNotNull("Export List link doesn't exist", new MailingListUsers(driver).getExportListLink() );
        Assert.assertNotNull("Send To List link doesn't exist", new MailingListUsers(driver).getSendToListLink() );
        Assert.assertNotNull("Import List link doesn't exist", new MailingListUsers(driver).getImportListLink() );
        Assert.assertNotNull("Bulk Delete link doesn't exist", new MailingListUsers(driver).getBulkDeleteLink() );
        Assert.assertNotNull("Letter List links don't exist", new MailingListUsers(driver).getLetterListLink() );

    }

    @After
    public void tearDown() {
        //driver.quit();
    }

}
