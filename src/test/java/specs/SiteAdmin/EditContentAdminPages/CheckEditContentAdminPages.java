package specs.SiteAdmin.EditContentAdminPages;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SiteAdmin.EditContentAdminPages.EditContentAdminPages;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-11-23.
 */

public class CheckEditContentAdminPages extends AbstractSpec {

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkEditContentAdminPages() throws Exception {
        final String expectedTitle = "Edit Content Admin Pages";
        final Integer expectedQuantity = 20;

        Assert.assertNotNull(new Dashboard(driver).openEditContentAdminPages().getUrl());
        Assert.assertEquals("Actual Edit Content Admin Pages Title doesn't match to expected", expectedTitle, new EditContentAdminPages(driver).getTitle());

        System.out.println(new EditContentAdminPages(driver).getShowInNavCkbQuantity().toString());
        Assert.assertTrue("Actual Title Quantity is less than expected: "+expectedQuantity, expectedQuantity <= new EditContentAdminPages(driver).getDomainQuantity() );
        Assert.assertTrue("Actual Show In Nav Checkboxes Quantity is less than expected: "+expectedQuantity+" or doesn't exist", expectedQuantity <= new EditContentAdminPages(driver).getShowInNavCkbQuantity() );

    }

    @After
    public void tearDown() {
        //driver.quit();
    }

}
