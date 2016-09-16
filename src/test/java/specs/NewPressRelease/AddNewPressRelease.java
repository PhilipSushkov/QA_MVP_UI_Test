package specs.NewPressRelease;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.NewPressRelease.NewPressRelease;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;
import pageobjects.PressReleases.PressReleases;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNewPressRelease extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser();
    }

    private Date current = new Date();
    private SimpleDateFormat fullDateF = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    private SimpleDateFormat dateF = new SimpleDateFormat("MM/dd/");
    private SimpleDateFormat yearF = new SimpleDateFormat("yyyy");
    private SimpleDateFormat hourF = new SimpleDateFormat("h");
    private SimpleDateFormat minF = new SimpleDateFormat("mm");
    private SimpleDateFormat AMPMF = new SimpleDateFormat("a");
    private int futureYear = Integer.parseInt(yearF.format(current))+15; // increase date to 15 years from now to ensure this is newer than any other press release

    private String headline = "Exciting testing news! v: " + fullDateF.format(current);
    private String date = dateF.format(current)+String.valueOf(futureYear);
    private String hour = hourF.format(current);
    private String min = minF.format(current);
    private String AMPM = AMPMF.format(current);

    @Test
    public void canAddNewPressRelease(){
        // adding new press release
        new Dashboard(driver).newPressRelease().addNewPressRelease(headline, date, hour, min, AMPM);
        String newPressReleaseHeadline = new PressReleases(driver).findNewPressReleaseHeadline();
        System.out.println("The new headline is: "+newPressReleaseHeadline);
        Assert.assertEquals(headline,newPressReleaseHeadline);

        //publishing press release
        new PressReleases(driver).publishNewPressRelease();

        //checking press release on AES live site
        String newAESLiveHeadline = new PressReleases(driver).aesLiveHome().findNewHeadline();
        System.out.println("The new AES live site headline is: "+newAESLiveHeadline);
        Assert.assertEquals(headline,newAESLiveHeadline);
    }
}
