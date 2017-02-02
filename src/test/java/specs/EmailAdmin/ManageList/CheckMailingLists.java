package specs.EmailAdmin.ManageList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

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

    @BeforeTest
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
        Assert.assertEquals(mailingLists.getTitle(), expectedTitle, "Actual Mailing Lists page Title doesn't match to expected");

        //System.out.println(new MailingLists(driver).getTitleQuantity().toString());
        Assert.assertTrue(expectedQuantity <= mailingLists.getTitleQuantity(), "Actual List Name Quantity is less than expected: "+expectedQuantity);
        Assert.assertNotNull(mailingLists.getSearchField(), "Search field doesn't exist");
        Assert.assertNotNull(mailingLists.getSearchButton(), "Search button doesn't exist");

    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }
}
