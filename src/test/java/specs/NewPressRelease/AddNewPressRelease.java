package specs.NewPressRelease;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.NewPressRelease.NewPressRelease;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;
import pageobjects.PressReleases.PressReleases;

public class AddNewPressRelease extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser();
    }

    private String headline = "Exciting testing news! v2";

    @Test
    public void canAddNewPressRelease(){
        new Dashboard(driver).newPressRelease().addNewPressRelease(headline);
        String newPressReleaseHeadline = new PressReleases(driver).findNewPressReleaseHeadline();
        System.out.println("The new headline is: "+newPressReleaseHeadline);
        Assert.assertEquals(headline,newPressReleaseHeadline);
    }
}
