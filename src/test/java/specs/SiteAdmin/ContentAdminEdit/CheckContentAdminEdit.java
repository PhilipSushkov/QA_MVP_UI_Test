package specs.SiteAdmin.ContentAdminEdit;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

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

    @BeforeTest
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
        Assert.assertEquals(contentAdminEdit.getTitle(), expectedTitle, "Actual Content Admin Edit page Title doesn't match to expected");

        //System.out.println(new ContentAdminEdit(driver).getShowInNavCkbQuantity().toString());
        Assert.assertTrue(expectedQuantity <= contentAdminEdit.getDomainQuantity(), "Actual Content Admin Title Quantity is less than expected: "+expectedQuantity);
        Assert.assertTrue(expectedQuantity <= contentAdminEdit.getShowInNavCkbQuantity(), "Actual Show In Nav Checkboxes Quantity is less than expected: "+expectedQuantity+" or doesn't exist");

    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
