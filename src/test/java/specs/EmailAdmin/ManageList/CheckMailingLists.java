package specs.EmailAdmin.ManageList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import pageobjects.Dashboard.Dashboard;
import pageobjects.EmailAdmin.ManageList.MailingLists;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;


/**
 * Created by philipsushkov on 2016-12-06.
 */

public class CheckMailingLists extends AbstractSpec {
    private final By emailAdminMenuButton = By.xpath("//span[contains(text(),'Email Admin')]");
    private final By manageListMenuItem = By.xpath("//a[contains(text(),'Manage List')]/parent::li");

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkMailingLists() throws Exception {
        final String expectedTitle = "Mailing Lists";
        final Integer expectedQuantity = 5;

        new Dashboard(driver).openPageFromMenu(emailAdminMenuButton, manageListMenuItem);

        Assert.assertNotNull(new MailingLists(driver).getUrl());
        Assert.assertEquals("Actual Mailing Lists page Title doesn't match to expected", expectedTitle, new MailingLists(driver).getTitle());

        //System.out.println(new MailingLists(driver).getTitleQuantity().toString());
        Assert.assertTrue("Actual List Name Quantity is less than expected: "+expectedQuantity, expectedQuantity <= new MailingLists(driver).getTitleQuantity() );
        Assert.assertNotNull("Search field doesn't exist", new MailingLists(driver).getSearchField() );
        Assert.assertNotNull("Search button doesn't exist", new MailingLists(driver).getSearchButton() );

    }

    @After
    public void tearDown() {
        new Dashboard(driver).logoutFromAdmin();
        //driver.quit();
    }
}
