package specs.ContentAdmin.PressReleases;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.ContentAdmin.PressReleases.PressReleases;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2017-01-05.
 */

public class CheckPressReleaseList extends AbstractSpec {
    private static By contentAdminMenuButton, pressReleasesMenuButton;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static PressReleases pressReleaseList;

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        pressReleasesMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_PressReleases"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        pressReleaseList = new PressReleases(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkPressReleaseList() throws Exception {
        final String expectedTitle = "Press Release List";
        final Integer expectedQuantity = 10;

        dashboard.openPageFromMenu(contentAdminMenuButton, pressReleasesMenuButton);

        Assert.assertNotNull(pressReleaseList.getUrl());
        Assert.assertEquals(pressReleaseList.getTitle(), expectedTitle, "Actual Press Release List page Title doesn't match to expected");

        Assert.assertNotNull(pressReleaseList.getFilterByTagInput(), "Filter By Tag field doesn't exist");
        Assert.assertNotNull(pressReleaseList.getCategorySelect(), "Category select doesn't exist");

        //System.out.println(pressReleaseList.getTitleQuantity().toString());
        Assert.assertTrue(expectedQuantity <= pressReleaseList.getTitleQuantity(), "Actual Press Release Headline Quantity is less than expected: "+expectedQuantity);

        Assert.assertNotNull(pressReleaseList.getPressReleaseListPagination(), "Press Release List Pagination doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
