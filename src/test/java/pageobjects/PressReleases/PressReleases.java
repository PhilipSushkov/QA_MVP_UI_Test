package pageobjects.PressReleases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;
import pageobjects.Dashboard.Dashboard;

public class PressReleases extends AbstractPageObject {
    private final By newPressReleaseHeadlineCell = By.xpath("//table[contains(@id,'UCPressReleases')]/tbody/tr[2]/td[3]");

    public PressReleases(WebDriver driver) {
        super(driver);
    }

    public String findNewPressReleaseHeadline(){
        return findElement(newPressReleaseHeadlineCell).getText();
    }

}
