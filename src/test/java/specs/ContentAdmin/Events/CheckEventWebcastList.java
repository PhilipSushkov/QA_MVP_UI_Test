package specs.ContentAdmin.Events;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.ContentAdmin.Events.Events;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2017-01-03.
 */

public class CheckEventWebcastList extends AbstractSpec {
    private static By contentAdminMenuButton, eventsMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static Events eventWebcastList;

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        eventsMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_Events"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        eventWebcastList = new Events(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkEventWebcastList() throws Exception {
        final String expectedTitle = "Event / Webcast List";
        final Integer expectedQuantity = 10;

        dashboard.openPageFromMenu(contentAdminMenuButton, eventsMenuItem);

        Assert.assertNotNull(eventWebcastList.getUrl());
        Assert.assertEquals(eventWebcastList.getTitle(), expectedTitle, "Actual Event/Webcast List page Title doesn't match to expected");

        //System.out.println(new FinancialReports(driver).getTitleQuantity().toString());
        Assert.assertTrue(expectedQuantity <= eventWebcastList.getTitleQuantity(), "Actual Event/Webcast Title Quantity is less than expected: "+expectedQuantity);
        Assert.assertNotNull(eventWebcastList.getEventWebcastListPagination(), "Event/Webcast List Pagination doesn't exist");
        Assert.assertNotNull(eventWebcastList.getFilterByTag(), "Filter By Tag field doesn't exist");

    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
