package specs.SiteAdmin.ModuleDefinitionList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.SiteAdmin.ModuleDefinitionList.ModuleDefinitionList;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2016-11-15.
 */

public class CheckModuleDefinitionList extends AbstractSpec {
    private static By siteAdminMenuButton, moduleDefinitionListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static ModuleDefinitionList moduleDefinitionList;

    @BeforeTest
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        moduleDefinitionListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_ModuleDefinitionList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        moduleDefinitionList = new ModuleDefinitionList(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkModuleDefinitionList() throws Exception {

        final String expectedTitle = "Module Definition List";
        final Integer expectedQuantity = 10;

        dashboard.openPageFromMenu(siteAdminMenuButton, moduleDefinitionListMenuItem);

        Assert.assertNotNull(moduleDefinitionList.getUrl());
        Assert.assertEquals(moduleDefinitionList.getTitle(), expectedTitle, "Actual Module Definition List page Title doesn't match to expected");

        //System.out.println(new ModuleDefinitionList(driver).getModuleDefinitionQuantity().toString());
        Assert.assertTrue(expectedQuantity <= moduleDefinitionList.getModuleDefinitionQuantity(), "Actual Module Definition Name Quantity is less than expected: "+expectedQuantity);
        Assert.assertNotNull(moduleDefinitionList.getModuleDefinitionPagination(), "Module Definition pagination doesn't exist");

    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
