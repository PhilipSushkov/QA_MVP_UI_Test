package specs.PublicSite;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LiveSite.LoginProtectedPage;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by charleszheng on 2017-10-19.
 */


// Stock Information page on Chicagotest must be protected
// Anonymous must not have Read privileges for Stock Information page

public class CheckLoginProtectedPage extends AbstractSpec {
   private static  LoginProtectedPage loginProtectedPage;
   private static  LoginPage loginPage;
   private static  Dashboard dashboard;
   private static  By pageAdminMenuButton;
   private  String originPageUrl;
   private  String targetPageUrl;

    @BeforeTest
    public void setUp () throws Exception {
        pageAdminMenuButton = By.xpath(propUIPageAdmin.getProperty("btnMenu_PageAdmin"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        loginProtectedPage = new LoginProtectedPage(driver);

        loginPage.loginUser();
        dashboard.openPageFromCommonTasks(pageAdminMenuButton);
        originPageUrl = loginProtectedPage.getOriginPageUrl();
        dashboard.openPageFromCommonTasks(pageAdminMenuButton);
        targetPageUrl = loginProtectedPage.getTargetPageUrl();
   }

   @Test
    public void checkLoginProtectedPage() throws InterruptedException {
       Assert.assertFalse(loginProtectedPage.targetPageAccessible(targetPageUrl));
       loginProtectedPage.loginToTargetPage(originPageUrl);
       Assert.assertTrue(loginProtectedPage.targetPageAccessible(targetPageUrl));
       loginProtectedPage.logoutFromTargetPage(originPageUrl);
       Assert.assertFalse(loginProtectedPage.targetPageAccessible(targetPageUrl));
   }

   @AfterTest
    public void tearDown() {
       //driver.quit();
   }


}
