package specs.SiteAdmin.ContentAdminEdit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SiteAdmin.ContentAdminEdit.ContentAdminEdit;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-11-23.
 */

public class CheckContentAdminEdit extends AbstractSpec {
    private static By siteAdminMenuButton, contentAdminEditMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static ContentAdminEdit contentAdminEdit;

    @Before
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        contentAdminEditMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_ContentAdminEdit"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        contentAdminEdit = new ContentAdminEdit(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkContentAdminEdit() throws Exception {
        final String expectedTitle = "Edit Content Admin Pages";
        final Integer expectedQuantity = 20;

        dashboard.openPageFromMenu(siteAdminMenuButton, contentAdminEditMenuItem);

        Assert.assertNotNull(contentAdminEdit.getUrl());
        Assert.assertEquals("Actual Content Admin Edit page Title doesn't match to expected", expectedTitle, contentAdminEdit.getTitle());

        //System.out.println(new ContentAdminEdit(driver).getShowInNavCkbQuantity().toString());
        Assert.assertTrue("Actual Content Admin Title Quantity is less than expected: "+expectedQuantity, expectedQuantity <= contentAdminEdit.getDomainQuantity() );
        Assert.assertTrue("Actual Show In Nav Checkboxes Quantity is less than expected: "+expectedQuantity+" or doesn't exist", expectedQuantity <= contentAdminEdit.getShowInNavCkbQuantity() );

    }

    @After
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
