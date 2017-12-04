package specs.SystemAdmin.PressReleaseFilterList;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SystemAdmin.PressReleaseFilterList.PressReleaseFilterList;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2017-12-04.
 */

public class CheckPressReleaseFilterList extends AbstractSpec {
    private static By systemAdminMenuButton, pressReleaseFilterListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static PressReleaseFilterList pressReleaseFilterList;


    @BeforeTest
    public void setUp() throws Exception {
        systemAdminMenuButton = By.xpath(propUISystemAdmin.getProperty("btnMenu_SystemAdmin"));
        pressReleaseFilterListMenuItem = By.xpath(propUISystemAdmin.getProperty("itemMenu_PressReleaseFilterList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        pressReleaseFilterList = new PressReleaseFilterList(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkGenericStorageList() throws Exception {
        final String expectedTitle = "Press Release Filter List";
        final Integer expectedSize = 1;

        dashboard.openPageFromMenu(systemAdminMenuButton, pressReleaseFilterListMenuItem);

        Assert.assertNotNull(pressReleaseFilterList.getUrl());
        Assert.assertEquals(pressReleaseFilterList.getTitle(), expectedTitle, "Actual Press Release Filter List page Title doesn't match to expected");
        Assert.assertTrue(expectedSize == pressReleaseFilterList.getPressReleaseFilterHeaderSize(), "Actual Press Release Filter List Size is less than expected: "+expectedSize+" or doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}


