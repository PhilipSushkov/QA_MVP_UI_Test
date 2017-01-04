package specs.ContentAdmin.Presentations;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.ContentAdmin.Presentations.Presentations;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2017-01-04.
 */

public class CheckPresentationList extends AbstractSpec {
    private static By contentAdminMenuButton, presentationsMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static Presentations presentationList;

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        presentationsMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_Presentations"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        presentationList = new Presentations(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkEventWebcastList() throws Exception {
        final String expectedTitle = "Presentation List";
        final Integer expectedQuantity = 10;

        dashboard.openPageFromMenu(contentAdminMenuButton, presentationsMenuItem);

        Assert.assertNotNull(presentationList.getUrl());
        Assert.assertEquals(presentationList.getTitle(), expectedTitle, "Actual Presentation List page Title doesn't match to expected");

        //System.out.println(presentationList.getTitleQuantity().toString());
        Assert.assertTrue(expectedQuantity <= presentationList.getTitleQuantity(), "Actual Presentation Title Quantity is less than expected: "+expectedQuantity);
        Assert.assertNotNull(presentationList.getPresentationListPagination(), "Presentation List Pagination doesn't exist");
        Assert.assertNotNull(presentationList.getFilterByTag(), "Filter By Tag field doesn't exist");

    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
