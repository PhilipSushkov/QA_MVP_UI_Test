package specs.EmailAdmin.EmailAlertList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.Dashboard.Dashboard;
import pageobjects.EmailAdmin.EmailAlertList.EmailAlertList;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-12-30.
 */

public class CheckEmailAlertList extends AbstractSpec {
    private static By emailAdminMenuButton, emailAlertListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static EmailAlertList emailAlertList;

    @BeforeTest
    public void setUp() throws Exception {
        emailAdminMenuButton = By.xpath(propUIEmailAdmin.getProperty("btnMenu_EmailAdmin"));
        emailAlertListMenuItem = By.xpath(propUIEmailAdmin.getProperty("btnMenu_EmailAlertList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        emailAlertList = new EmailAlertList(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkEmailAlertList() throws Exception {
        final String expectedTitle = "Email Alert List";
        final Integer expectedQuantity = 5;

        dashboard.openPageFromMenu(emailAdminMenuButton, emailAlertListMenuItem);

        Assert.assertNotNull(emailAlertList.getUrl());
        Assert.assertEquals(emailAlertList.getTitle(), expectedTitle, "Email Alert List page Title doesn't match to expected");

        //System.out.println(new MailingListUsers(driver).getTitleQuantity().toString());
        Assert.assertTrue(expectedQuantity <= emailAlertList.getDescriptionQuantity() , "Actual Description Quantity is less than expected: "+expectedQuantity);
        //Assert.assertNotNull(mailingListUsers.getMailingListUsersPagination(), "Mailing List Users Pagination doesn't exist");

    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
