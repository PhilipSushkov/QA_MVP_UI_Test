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
    private static By emailAdminMenuButton, manageListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static MailingLists mailingLists;

    @Before
    public void setUp() throws Exception {
        emailAdminMenuButton = By.xpath(propUIEmailAdmin.getProperty("btnMenu_EmailAdmin"));
        manageListMenuItem = By.xpath(propUIEmailAdmin.getProperty("btnMenu_ManageList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        mailingLists = new MailingLists(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkMailingLists() throws Exception {
        final String expectedTitle = "Mailing Lists";
        final Integer expectedQuantity = 5;

        dashboard.openPageFromMenu(emailAdminMenuButton, manageListMenuItem);

        Assert.assertNotNull(mailingLists.getUrl());
        Assert.assertEquals("Actual Mailing Lists page Title doesn't match to expected", expectedTitle, mailingLists.getTitle());

        //System.out.println(new MailingLists(driver).getTitleQuantity().toString());
        Assert.assertTrue("Actual List Name Quantity is less than expected: "+expectedQuantity, expectedQuantity <= mailingLists.getTitleQuantity() );
        Assert.assertNotNull("Search field doesn't exist", mailingLists.getSearchField() );
        Assert.assertNotNull("Search button doesn't exist", mailingLists.getSearchButton() );

    }

    @After
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }
}
