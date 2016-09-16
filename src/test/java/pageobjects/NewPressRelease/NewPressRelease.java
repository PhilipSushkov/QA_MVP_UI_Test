package pageobjects.NewPressRelease;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.Actions;
import pageobjects.AbstractPageObject;
import pageobjects.PressReleases.PressReleases;

public class NewPressRelease extends AbstractPageObject {
    Actions action = new Actions(driver);

    private final By pressReleaseDate = By.id("txtPressReleaseDate");
    private final By pressReleaseHour = By.xpath("//select[contains(@id,'ddlHour')]");
    private final By pressReleaseMinute = By.xpath("//select[contains(@id,'ddlMinute')]");
    private final By pressReleaseAMPM = By.xpath("//select[contains(@id,'ddlAMPM')]");
    private final By pressReleaseHeadline = By.id("txtHeadline");
    private final By insertMenuButton = By.className("rrbTab").linkText("Insert");
    private final By imageManagerButton = By.className("ImageManager");
    //private final By firstImage = By.className("rfeThumbTitle").tagName("span");
    //private final By imagePreview = By.className("imagePreview");
    //private final By insertImageButton = By.id("InsertButton");
    private final By switchToHtml = By.className("reMode_html");
    private final By htmlTextBox = By.xpath("//td[contains(@id,'RADeditor1Center')]");
    private final By updateComments = By.xpath("//textarea[contains(@id,'txtComments')]");
    private final By saveAndSubmit = By.xpath("//input[contains(@id,'btnSaveAndSubmit')]");

    public NewPressRelease(WebDriver driver) {
        super(driver);
    }

    public PressReleases addNewPressRelease(String headline) {
        wait.until(ExpectedConditions.visibilityOf(findElement(pressReleaseDate)));
        findElement(pressReleaseDate).sendKeys("12/25/2030");
        findElement(pressReleaseHour).sendKeys("12");
        findElement(pressReleaseMinute).sendKeys("30");
        findElement(pressReleaseAMPM).sendKeys("PM");
        findElement(pressReleaseHeadline).sendKeys(headline);
        findElement(switchToHtml).click();
        findElement(htmlTextBox).click();
        action.sendKeys("This is a test of a press release.").perform();
        /* findElement(insertMenuButton).click();
        findElement(imageManagerButton).click();
        System.out.println("Now waiting for images...");

        try{Thread.sleep(5000);}
        catch(InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("End of wait");
        //wait.until(ExpectedConditions.visibilityOf(findElement(firstImage)));
        findElement(By.xpath("//img[contains(@src,'png')]")).click();

        System.out.println("Now waiting 5 seconds...");
        try{Thread.sleep(5000);}
        catch(InterruptedException e){
            e.printStackTrace();
        }


        //wait.until(ExpectedConditions.visibilityOf(findElement(imagePreview)));
        //wait.until(ExpectedConditions.elementToBeClickable(findElement(insertImageButton)));
        //findElement(insertImageButton).click(); */
        findElement(updateComments).sendKeys("testing");

        findElement(saveAndSubmit).click();

        return new PressReleases(getDriver());
    }
}
