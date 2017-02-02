package specs.EmailAdmin.CategoryList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.Dashboard.Dashboard;
import pageobjects.EmailAdmin.CategoryList.CategoryList;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-12-30.
 */

public class CheckCategoryList extends AbstractSpec {
    private static By emailAdminMenuButton, categoryListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static CategoryList categoryList;

    @BeforeTest
    public void setUp() throws Exception {
        emailAdminMenuButton = By.xpath(propUIEmailAdmin.getProperty("btnMenu_EmailAdmin"));
        categoryListMenuItem = By.xpath(propUIEmailAdmin.getProperty("btnMenu_CategoryList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        categoryList = new CategoryList(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkCategoryList() throws Exception {
        final String expectedTitle = "Category List";
        final Integer expectedQuantity = 1;

        dashboard.openPageFromMenu(emailAdminMenuButton, categoryListMenuItem);

        Assert.assertNotNull(categoryList.getUrl());
        Assert.assertEquals(categoryList.getTitle(), expectedTitle, "Category List page Title doesn't match to expected");

        //System.out.println(new MailingListUsers(driver).getTitleQuantity().toString());
        Assert.assertTrue(expectedQuantity <= categoryList.getCategoryNameQuantity() , "Actual Category Name Quantity is less than expected: "+expectedQuantity);
        //Assert.assertNotNull(mailingListUsers.getMailingListUsersPagination(), "Mailing List Users Pagination doesn't exist");

    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
