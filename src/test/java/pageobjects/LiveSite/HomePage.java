package pageobjects.LiveSite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by jasons on 2016-11-07.
 */
public class HomePage extends AbstractPageObject {

    private final By Q4Logo = By.className("ClientLogo");
    private final By versionNumber = By.className("Version");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean logoIsPresent(){

        return doesElementExist(Q4Logo);
    }

    public String getVersionNumber(){

        return findElement(versionNumber).getText();
    }
}
