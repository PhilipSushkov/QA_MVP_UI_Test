package specs.ContentAdmin.PersonList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.ContentAdmin.PersonList.PersonList;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-12-02.
 */

public class CheckPersonList extends AbstractSpec {
    private static By contentAdminMenuButton, personListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static PersonList personList;

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        personListMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_PersonList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        personList = new PersonList(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkPersonList() throws Exception {
        final String expectedTitle = "Person List";
        final Integer expectedQuantity = 1;

        dashboard.openPageFromMenu(contentAdminMenuButton, personListMenuItem);

        Assert.assertNotNull(personList.getUrl());
        Assert.assertEquals(personList.getTitle(), expectedTitle, "Actual Person List page Title doesn't match to expected");

        //System.out.println(new PersonList(driver).getTitleQuantity().toString());
        Assert.assertTrue(expectedQuantity <= personList.getTitleQuantity(), "Actual Person List Name Quantity is less than expected: "+expectedQuantity);
        //Assert.assertNotNull("Person List Pagination doesn't exist", new PersonList(driver).getQuickLinksPagination() );
        Assert.assertNotNull(personList.getDepartmentList(), "Department drop down list doesn't exist");

    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
