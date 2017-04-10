package specs.SystemAdmin.SiteMaintenance;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.SystemAdmin.SiteMaintenance.FunctionalBtn;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2017-04-10.
 */

public class CheckFunctionalBtn extends AbstractSpec {
    private static By systemAdminMenuButton, siteMaintenanceMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static FunctionalBtn functionalBtn;

    @BeforeTest
    public void setUp() throws Exception {
        systemAdminMenuButton = By.xpath(propUISystemAdmin.getProperty("btnMenu_SystemAdmin"));
        siteMaintenanceMenuItem = By.xpath(propUISystemAdmin.getProperty("itemMenu_SiteMaintenance"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        functionalBtn = new FunctionalBtn(driver);

        loginPage.loginUser();
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        dashboard.openPageFromMenu(systemAdminMenuButton, siteMaintenanceMenuItem);
    }

    @Test(priority=1)
    public void checkGoLiveBtn() throws Exception {
        Assert.assertTrue(functionalBtn.getGoLiveBtnState(), "Actual Go Live button state is supposed to be Disabled");
    }

    @Test(priority=2)
    public void checkPressReleasePublishingLoginBtn() throws Exception {
        Assert.assertTrue(functionalBtn.getPressReleasePublishingLoginBtnState(), "Some discrepancies are found for Press Release Publishing Login elements and their states");
        Assert.assertTrue(functionalBtn.clickPressReleasePublishingLoginBtn(), "Press Release Publishing Login button (Enable/Disable) doesn't work well");
    }

    @Test(priority=3)
    public void checkTwoFactorAuthenticationBtn() throws Exception {
        Assert.assertTrue(functionalBtn.getTwoFactorAuthenticationBtnState(), "Some discrepancies are found for Two-Factor Authentication elements and their states");
    }

    @Test(priority=4)
    public void checkSecurityBtn() throws Exception {
        Assert.assertTrue(functionalBtn.getSecurityBtnState(), "Some discrepancies are found for Security elements and their states");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
