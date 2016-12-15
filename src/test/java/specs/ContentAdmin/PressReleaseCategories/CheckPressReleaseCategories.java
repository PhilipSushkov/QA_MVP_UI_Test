package specs.ContentAdmin.PressReleaseCategories;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.ContentAdmin.PressReleaseCategories.PressReleaseCategories;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-11-23.
 */

public class CheckPressReleaseCategories extends AbstractSpec {
    private static By contentAdminMenuButton, pressReleaseCategoriesMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static PressReleaseCategories pressReleaseCategories;

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        pressReleaseCategoriesMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_PressReleaseCategories"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        pressReleaseCategories = new PressReleaseCategories(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkPressReleaseCategories() throws Exception {
        final String expectedTitle = "Press Release Categories";
        final Integer expectedQuantity = 1;

        dashboard.openPageFromMenu(contentAdminMenuButton, pressReleaseCategoriesMenuItem);

        Assert.assertNotNull(pressReleaseCategories.getUrl());
        Assert.assertEquals(pressReleaseCategories.getTitle(), expectedTitle, "Actual Press Release Categories page Title doesn't match to expected");

        //System.out.println(new PressReleaseCategories(driver).getCategoryNameQuantity().toString());
        Assert.assertTrue(expectedQuantity <= pressReleaseCategories.getCategoryNameQuantity(), "Actual Category Name Quantity is less than expected: "+expectedQuantity);

    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
