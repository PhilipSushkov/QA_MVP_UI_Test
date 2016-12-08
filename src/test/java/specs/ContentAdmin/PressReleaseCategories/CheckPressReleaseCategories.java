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
    private final By contentAdminMenuButton = By.xpath("//span[contains(text(),'Content Admin')]");
    private final By pressReleaseCategoriesMenuItem = By.xpath("//a[contains(text(),'Press Release Categories')]/parent::li");

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkPressReleaseCategories() throws Exception {
        final String expectedTitle = "Press Release Categories";
        final Integer expectedQuantity = 1;

        new Dashboard(driver).openPageFromMenu(contentAdminMenuButton, pressReleaseCategoriesMenuItem);

        Assert.assertNotNull(new PressReleaseCategories(driver).getUrl());
        Assert.assertEquals("Actual Press Release Categories page Title doesn't match to expected", expectedTitle, new PressReleaseCategories(driver).getTitle());

        //System.out.println(new PressReleaseCategories(driver).getCategoryNameQuantity().toString());
        Assert.assertTrue("Actual Category Name Quantity is less than expected: "+expectedQuantity, expectedQuantity <= new PressReleaseCategories(driver).getCategoryNameQuantity() );

    }

    @After
    public void tearDown() {
        new Dashboard(driver).logout();
        //driver.quit();
    }

}
