package pageobjects.SystemAdmin.SiteMaintenance;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISystemAdmin;

/**
 * Created by philipsushkov on 2016-11-14.
 */

public class SiteMaintenance extends AbstractPageObject {
    private static By moduleTitle, btnGoLive, btnOneTouch, btnTwoFactorAuthentication, btnIFrames;
    private static By selNewPasswordReuseLimit, btnUpdatePasswordReuseLimit, spanPasswordReuseLimit;
    private static By spanSendGridStatus, spanPressReleasePublishing, spanTwoFactorAuthentication, spanIFrames;
    private static By btnSendGrid, inpSendGridAPIKey, btnUpdateApi, spanNewswireImageResizeStatus, btnNewswireImageResize;
    private static By inpResizeWidthInp, inpResizeHeightInp, btnNewswireImageResizeUpdate;


    public SiteMaintenance(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISystemAdmin.getProperty("divModule_Title"));
        btnGoLive = By.xpath(propUISystemAdmin.getProperty("btn_GoLive"));
        btnOneTouch = By.xpath(propUISystemAdmin.getProperty("btn_OneTouch"));
        btnTwoFactorAuthentication = By.xpath(propUISystemAdmin.getProperty("btn_TwoFactorAuthentication"));
        btnIFrames = By.xpath(propUISystemAdmin.getProperty("btn_IFrames"));
        selNewPasswordReuseLimit = By.xpath(propUISystemAdmin.getProperty("ngmodel_NewPasswordReuseLimit"));
        btnUpdatePasswordReuseLimit = By.xpath(propUISystemAdmin.getProperty("btn_UpdatePasswordReuseLimit"));
        spanPasswordReuseLimit = By.xpath(propUISystemAdmin.getProperty("span_PasswordReuseLimit"));
        spanSendGridStatus = By.xpath(propUISystemAdmin.getProperty("span_SendGridStatus"));
        spanPressReleasePublishing = By.xpath(propUISystemAdmin.getProperty("span_PressReleasePublishing"));
        spanTwoFactorAuthentication = By.xpath(propUISystemAdmin.getProperty("span_TwoFactorAuthentication"));
        spanIFrames = By.xpath(propUISystemAdmin.getProperty("span_IFrames"));
        btnSendGrid = By.xpath(propUISystemAdmin.getProperty("btn_SendGrid"));
        inpSendGridAPIKey = By.xpath(propUISystemAdmin.getProperty("inp_SendGridAPIKey"));
        btnUpdateApi = By.xpath(propUISystemAdmin.getProperty("btn_UpdateApi"));
        spanNewswireImageResizeStatus = By.xpath(propUISystemAdmin.getProperty("span_NewswireImageResizeStatus"));
        btnNewswireImageResize = By.xpath(propUISystemAdmin.getProperty("btn_NewswireImageResize"));
        inpResizeWidthInp = By.xpath(propUISystemAdmin.getProperty("inp_ResizeWidthInp"));
        inpResizeHeightInp = By.xpath(propUISystemAdmin.getProperty("inp_ResizeHeightInp"));
        btnNewswireImageResizeUpdate = By.xpath(propUISystemAdmin.getProperty("btn_NewswireImageResizeUpdate"));
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOf(findElement(moduleTitle)));
        return findElement(moduleTitle).getText();
    }

    public WebElement getGoLiveButton() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(btnGoLive)));
            element = findElement(btnGoLive);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        }

        return element;
    }


    public WebElement getOneTouchButton() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(btnOneTouch)));
            element = findElement(btnOneTouch);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        }

        return element;

    }


    public WebElement getTwoFactorAuthenticationButton() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(btnTwoFactorAuthentication)));
            element = findElement(btnTwoFactorAuthentication);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        }

        return element;
    }

    public WebElement getIFramesButton() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(btnIFrames)));
            element = findElement(btnIFrames);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        }

        return element;
    }

    public WebElement getPasswordReuseLimitList() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(selNewPasswordReuseLimit)));
            element = findElement(selNewPasswordReuseLimit);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        }

        return element;
    }

    public WebElement getPasswordLimitUpdateBtn() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(btnUpdatePasswordReuseLimit)));
            element = findElement(btnUpdatePasswordReuseLimit);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        }

        return element;
    }

    public Boolean getPasswordReuseLimit() {
        Boolean checkPasswordReuseLimit = false;
        int[] arrayLimits = {0, 1, 2, 3};

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(spanPasswordReuseLimit)));

            int iLimit = Integer.parseInt(findElement(spanPasswordReuseLimit).getText());

            for (int i=0; i<arrayLimits.length; i++) {
                if (iLimit == arrayLimits[i]) {
                    checkPasswordReuseLimit = true;
                    break;
                }
            }

        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        }

        return checkPasswordReuseLimit;
    }

    public Boolean getSendGridStatus() {
        Boolean checkSendGridStatus = false;
        String[] arrayStatus = {"ENABLED", "DISABLED"};

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(spanSendGridStatus)));

            String iStatus = findElement(spanSendGridStatus).getText();

            for (int i=0; i<arrayStatus.length; i++) {
                if (iStatus.equals(arrayStatus[i])) {
                    checkSendGridStatus = true;
                    break;
                }
            }

        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        }

        return checkSendGridStatus;
    }

    public Boolean getPressReleasePublishingStatus() {
        Boolean checkPressReleasePublishingStatus = false;
        String[] arrayStatus = {"ENABLED", "DISABLED"};

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(spanPressReleasePublishing)));

            String iStatus = findElement(spanPressReleasePublishing).getText();

            for (int i=0; i<arrayStatus.length; i++) {
                if (iStatus.equals(arrayStatus[i])) {
                    checkPressReleasePublishingStatus = true;
                    break;
                }
            }

        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        }

        return checkPressReleasePublishingStatus;
    }

    public Boolean getTwoFactorAuthenticationStatus() {
        Boolean checkTwoFactorAuthenticationStatus = false;
        String[] arrayStatus = {"ENABLED", "DISABLED"};

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(spanTwoFactorAuthentication)));

            String iStatus = findElement(spanTwoFactorAuthentication).getText();

            for (int i=0; i<arrayStatus.length; i++) {
                if (iStatus.equals(arrayStatus[i])) {
                    checkTwoFactorAuthenticationStatus = true;
                    break;
                }
            }

        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        }

        return checkTwoFactorAuthenticationStatus;
    }

    public Boolean getIFramesStatus() {
        Boolean checkTwoFactorAuthenticationStatus = false;
        String[] arrayStatus = {"ENABLED", "DISABLED"};

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(spanIFrames)));

            String iStatus = findElement(spanIFrames).getText();

            for (int i=0; i<arrayStatus.length; i++) {
                if (iStatus.equals(arrayStatus[i])) {
                    checkTwoFactorAuthenticationStatus = true;
                    break;
                }
            }

        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        }

        return checkTwoFactorAuthenticationStatus;
    }

    public WebElement getSendGridBtn() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(btnSendGrid)));
            element = findElement(btnSendGrid);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        }

        return element;
    }

    public WebElement getSendGridAPIKeyInp() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(inpSendGridAPIKey)));
            element = findElement(inpSendGridAPIKey);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        }

        return element;
    }

    public WebElement getUpdateApiBtn() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(btnUpdateApi)));
            element = findElement(btnUpdateApi);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        }

        return element;
    }

    public Boolean getNewswireImageResizeStatus() {
        Boolean checkNewswireImageResizeStatus = false;
        String[] arrayStatus = {"ENABLED", "DISABLED"};

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(spanNewswireImageResizeStatus)));

            String iStatus = findElement(spanNewswireImageResizeStatus).getText();

            for (int i=0; i<arrayStatus.length; i++) {
                if (iStatus.equals(arrayStatus[i])) {
                    checkNewswireImageResizeStatus = true;
                    break;
                }
            }

        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        }

        return checkNewswireImageResizeStatus;
    }

    public WebElement getNewswireImageResizeBtn() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(btnNewswireImageResize)));
            element = findElement(btnNewswireImageResize);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        }

        return element;
    }

    public WebElement getResizeWidthInp() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(inpResizeWidthInp)));
            element = findElement(inpResizeWidthInp);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        }

        return element;
    }

    public WebElement getResizeHeightInp() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(inpResizeHeightInp)));
            element = findElement(inpResizeHeightInp);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        }

        return element;
    }

    public WebElement getNewswireImageResizeUpdateBtn() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(btnNewswireImageResizeUpdate)));
            element = findElement(btnNewswireImageResizeUpdate);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        }

        return element;
    }

}
