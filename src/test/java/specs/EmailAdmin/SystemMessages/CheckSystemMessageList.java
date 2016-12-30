package specs.EmailAdmin.SystemMessages;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.Dashboard.Dashboard;
import pageobjects.EmailAdmin.SystemMessages.SystemMessageList;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-12-30.
 */
public class CheckSystemMessageList extends AbstractSpec {
    private static By emailAdminMenuButton, systemMessagesMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static SystemMessageList systemMessageList;

    @BeforeTest
    public void setUp() throws Exception {
        emailAdminMenuButton = By.xpath(propUIEmailAdmin.getProperty("btnMenu_EmailAdmin"));
        systemMessagesMenuItem = By.xpath(propUIEmailAdmin.getProperty("btnMenu_SystemMessages"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        systemMessageList = new SystemMessageList(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkCompose() throws Exception {
        final String expectedTitle = "Mailing List System Message List";
        final Integer expectedQuantity = 5;

        dashboard.openPageFromMenu(emailAdminMenuButton, systemMessagesMenuItem);

        Assert.assertNotNull(systemMessageList.getUrl());
        Assert.assertEquals(systemMessageList.getTitle(), expectedTitle, "System Message List page Title doesn't match to expected");

        //System.out.println(mailingListMessagesView.getTitleQuantity().toString());
        Assert.assertTrue(expectedQuantity <= systemMessageList.getMessageNameQuantity(), "Message Name Quantity is less than expected: "+expectedQuantity);
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
