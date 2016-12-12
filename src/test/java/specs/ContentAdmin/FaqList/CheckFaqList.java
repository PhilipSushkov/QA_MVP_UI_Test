package specs.ContentAdmin.FaqList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import pageobjects.ContentAdmin.FaqList.FaqList;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-12-05.
 */

public class CheckFaqList extends AbstractSpec {
    private static By contentAdminMenuButton, faqListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static FaqList faqList;

    @Before
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        faqListMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_FaqList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        faqList = new FaqList(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkFaqList() throws Exception {
        final String expectedTitle = "Faq List";
        final Integer expectedQuantity = 1;

        dashboard.openPageFromMenu(contentAdminMenuButton, faqListMenuItem);

        Assert.assertNotNull(faqList.getUrl());
        Assert.assertEquals("Actual Faq List page Title doesn't match to expected", expectedTitle, faqList.getTitle());

        //System.out.println(new FaqList(driver).getTitleQuantity().toString());
        Assert.assertTrue("Actual Faq List Name Quantity is less than expected: "+expectedQuantity, expectedQuantity <= faqList.getTitleQuantity() );
        Assert.assertNotNull("Faq List Pagination doesn't exist", faqList.getFaqListPagination() );

    }

    @After
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
