package specs.PageAdmin;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.PageAdmin.PageAdminList;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2017-01-11.
 */

public class CheckPageAdminList extends AbstractSpec {

    private static By pageAdminMenuButton;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static PageAdminList pageAdminList;

    @BeforeTest
    public void setUp() throws Exception {
        pageAdminMenuButton = By.xpath(propUIPageAdmin.getProperty("btnMenu_PageAdmin"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        pageAdminList = new PageAdminList(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkPageAdminList() throws Exception {
        final String expectedTitle = "Public Page List";

        dashboard.openPageFromCommonTasks(pageAdminMenuButton);

        Assert.assertNotNull(pageAdminList.getUrl());
        Assert.assertEquals(pageAdminList.getTitle(), expectedTitle, "Public Page List page Title doesn't match to expected");

        System.out.println(pageAdminList.getTitle());

        Assert.assertNotNull(pageAdminList.getPageItems(), "Pages items don't exist");

    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
