package pageobjects.AESLiveSite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

public class AESLiveHome extends AbstractPageObject{
    public static String url = "https://aes.q4web.newtest";

    private final By latestHeadlineLink = By.xpath("//div[contains(@class,'block01')]/div/a");

    public AESLiveHome(WebDriver driver) {
        super(driver);
    }

    public String findNewHeadline(){
        return findElement(latestHeadlineLink).getText();
    }
}
