package specs.ContentAdmin.PressReleaseCategories;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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

    @Before
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
        Assert.assertEquals("Actual Press Release Categories page Title doesn't match to expected", expectedTitle, pressReleaseCategories.getTitle());

        //System.out.println(new PressReleaseCategories(driver).getCategoryNameQuantity().toString());
        Assert.assertTrue("Actual Category Name Quantity is less than expected: "+expectedQuantity, expectedQuantity <= pressReleaseCategories.getCategoryNameQuantity() );

    }

    @After
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
