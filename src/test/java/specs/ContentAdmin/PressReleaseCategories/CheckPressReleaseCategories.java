package specs.ContentAdmin.PressReleaseCategories;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.ContentAdmin.PressReleaseCategories.PressReleaseCategories;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-11-23.
 */

public class CheckPressReleaseCategories extends AbstractSpec {

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkPressReleaseCategories() throws Exception {
        final String expectedTitle = "Press Release Categories";
        final Integer expectedQuantity = 1;

        Assert.assertNotNull(new Dashboard(driver).openPressReleaseCategories().getUrl());
        Assert.assertEquals("Actual Press Release Categories page Title doesn't match to expected", expectedTitle, new PressReleaseCategories(driver).getTitle());

        //System.out.println(new PressReleaseCategories(driver).getCategoryNameQuantity().toString());
        Assert.assertTrue("Actual Category Name Quantity is less than expected: "+expectedQuantity, expectedQuantity <= new PressReleaseCategories(driver).getCategoryNameQuantity() );

    }

    @After
    public void tearDown() {
        //driver.quit();
    }

}
