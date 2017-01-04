package specs.ContentAdmin.Glossary;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.ContentAdmin.Glossary.GlossaryList;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2017-01-04.
 */

public class CheckGlossaryList extends AbstractSpec {
    private static By contentAdminMenuButton, glossaryListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static GlossaryList glossaryList;

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        glossaryListMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_Glossary"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        glossaryList = new GlossaryList(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkGlossaryList() throws Exception {
        final String expectedTitle = "Glossary List";
        final Integer expectedQuantity = 0;

        dashboard.openPageFromMenu(contentAdminMenuButton, glossaryListMenuItem);

        Assert.assertNotNull(glossaryList.getUrl());
        Assert.assertEquals(glossaryList.getTitle(), expectedTitle, "Actual Glossary List page Title doesn't match to expected");

        //System.out.println(glossaryList.getTitleQuantity().toString());
        Assert.assertTrue(expectedQuantity <= glossaryList.getTitleQuantity(), "Actual Glossary Title Quantity is less than expected: "+expectedQuantity);
        Assert.assertNotNull(glossaryList.getGlossaryListPagination(), "Glossary List Pagination doesn't exist");
        //Assert.assertNotNull(quickLinks.getFilterByTag(), "Filter By Tag field doesn't exist");

    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }
}
