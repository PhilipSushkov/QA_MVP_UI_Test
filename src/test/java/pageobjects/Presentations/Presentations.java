package pageobjects.Presentations;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.LiveSite.LivePressReleases;
import pageobjects.AbstractPageObject;

import java.util.concurrent.TimeUnit;

/**
 * Created by philipsushkov on 2016-11-08.
 */
public class Presentations extends AbstractPageObject {
    private final By publishButton = By.xpath("//input[contains(@id,'Presentations_btnPublish')]");

    public Presentations(WebDriver driver) {
        super(driver);
    }

}
