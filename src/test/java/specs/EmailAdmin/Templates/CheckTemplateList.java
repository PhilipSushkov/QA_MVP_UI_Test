package specs.EmailAdmin.Templates;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.Dashboard.Dashboard;
import pageobjects.EmailAdmin.Templates.TemplateList;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;


/**
 * Created by philipsushkov on 2016-12-30.
 */

public class CheckTemplateList extends AbstractSpec {
    private static By emailAdminMenuButton, templatesMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static TemplateList templateList;

    @BeforeTest
    public void setUp() throws Exception {
        emailAdminMenuButton = By.xpath(propUIEmailAdmin.getProperty("btnMenu_EmailAdmin"));
        templatesMenuItem = By.xpath(propUIEmailAdmin.getProperty("btnMenu_Templates"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        templateList = new TemplateList(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkMailingListUsers() throws Exception {
        final String expectedTitle = "Template Edit";
        final Integer expectedQuantity = 10;

        dashboard.openPageFromMenu(emailAdminMenuButton, templatesMenuItem);

        Assert.assertNotNull(templateList.getUrl());
        Assert.assertEquals(templateList.getTitle(), expectedTitle, "Actual Template Edit page Title doesn't match to expected");

        //System.out.println(new MailingListUsers(driver).getTitleQuantity().toString());
        Assert.assertTrue(expectedQuantity <= templateList.getTemplateNameQuantity() , "Actual Template Name Quantity is less than expected: "+expectedQuantity);
        //Assert.assertNotNull(mailingListUsers.getMailingListUsersPagination(), "Mailing List Users Pagination doesn't exist");

    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
