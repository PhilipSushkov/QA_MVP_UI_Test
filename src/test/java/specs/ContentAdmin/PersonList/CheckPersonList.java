package specs.ContentAdmin.PersonList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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

    @Before
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
        Assert.assertEquals("Actual Person List page Title doesn't match to expected", expectedTitle, personList.getTitle());

        //System.out.println(new PersonList(driver).getTitleQuantity().toString());
        Assert.assertTrue("Actual Person List Name Quantity is less than expected: "+expectedQuantity, expectedQuantity <= personList.getTitleQuantity() );
        //Assert.assertNotNull("Person List Pagination doesn't exist", new PersonList(driver).getQuickLinksPagination() );
        Assert.assertNotNull("Department drop down list doesn't exist", personList.getDepartmentList() );

    }

    @After
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
