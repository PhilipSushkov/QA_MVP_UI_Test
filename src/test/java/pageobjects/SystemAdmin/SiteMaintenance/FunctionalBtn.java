package pageobjects.SystemAdmin.SiteMaintenance;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISystemAdmin;

/**
 * Created by philipsushkov on 2017-04-10.
 */

public class FunctionalBtn extends AbstractPageObject {
    private static By btnGoLive, btnOneTouch, spanOneTouch, btnTwoFactorAuthentication, spanTwoFactorAuthentication, btnIFrames, spanIFrames;
    private static final String sEnabledLbl = "ENABLED", sDisabledLbl = "DISABLED", sClassNameRed = "ng-binding red", sClassNameGreen = "ng-binding green";
    private static final String sEnableBtn = "ENABLE", sDisableBtn = "DISABLE";

    public FunctionalBtn(WebDriver driver) {
        super(driver);
        btnGoLive = By.xpath(propUISystemAdmin.getProperty("btn_GoLive"));
        btnOneTouch = By.xpath(propUISystemAdmin.getProperty("btn_OneTouch"));
        spanOneTouch = By.xpath(propUISystemAdmin.getProperty("span_OneTouch"));
        btnTwoFactorAuthentication = By.xpath(propUISystemAdmin.getProperty("btn_TwoFactorAuthentication"));
        spanTwoFactorAuthentication = By.xpath(propUISystemAdmin.getProperty("span_TwoFactorAuthentication"));
        btnIFrames = By.xpath(propUISystemAdmin.getProperty("btn_IFrames"));
        spanIFrames = By.xpath(propUISystemAdmin.getProperty("span_IFrames"));
    }


    public boolean getGoLiveBtnState() {
        boolean bGoLiveBtnState = false;
        wait.until(ExpectedConditions.visibilityOf(findElement(btnGoLive)));

        try {
            if (Boolean.parseBoolean(findElement(btnGoLive).getAttribute("disabled"))) {
                bGoLiveBtnState = true;
            }
        } catch (NullPointerException e) {
            System.out.println("The attribute Disabled is not found for GoLive button");
            return false;
        }

        return bGoLiveBtnState;
    }


    public boolean getPressReleasePublishingLoginBtnState() {
        boolean bState;
        wait.until(ExpectedConditions.visibilityOf(findElement(btnOneTouch)));

        /*
        System.out.println(findElement(spanOneTouch).getAttribute("innerText").equals(sEnabledLbl));
        System.out.println(findElement(spanOneTouch).getAttribute("className").equals(sClassNameGreen));
        System.out.println(findElement(btnOneTouch).getAttribute("innerText").equals(sDisableBtn));
        */

        try {
            if ((findElement(spanOneTouch).getAttribute("innerText").equals(sEnabledLbl)
                    && (findElement(spanOneTouch).getAttribute("className").equals(sClassNameGreen))
                    && (findElement(btnOneTouch).getAttribute("innerText").equals(sDisableBtn))) ) {
                bState = true;
            } else {
                if ((findElement(spanOneTouch).getAttribute("innerText").equals(sDisabledLbl)
                        && (findElement(spanOneTouch).getAttribute("className").equals(sClassNameRed))
                        && (findElement(btnOneTouch).getAttribute("innerText").equals(sEnableBtn))) ) {
                    bState = true;
                } else {
                    return false;
                }
            }

        } catch (NullPointerException e) {
            return false;
        }

        return bState;
    }


    public boolean clickPressReleasePublishingLoginBtn() {
        boolean bState = false;


        return bState;
    }


    public boolean getTwoFactorAuthenticationBtnState() {

        boolean bState;
        wait.until(ExpectedConditions.visibilityOf(findElement(btnTwoFactorAuthentication)));

        try {
            if ((findElement(spanTwoFactorAuthentication).getAttribute("innerText").equals(sEnabledLbl)
                    && (findElement(spanTwoFactorAuthentication).getAttribute("className").equals(sClassNameGreen))
                    && (findElement(btnTwoFactorAuthentication).getAttribute("innerText").equals(sDisableBtn))) ) {
                bState = true;
            } else {
                if ((findElement(spanTwoFactorAuthentication).getAttribute("innerText").equals(sDisabledLbl)
                        && (findElement(spanTwoFactorAuthentication).getAttribute("className").equals(sClassNameRed))
                        && (findElement(btnTwoFactorAuthentication).getAttribute("innerText").equals(sEnableBtn))) ) {
                    bState = true;
                } else {
                    return false;
                }
            }

        } catch (NullPointerException e) {
            return false;
        }

        return bState;
    }


    public boolean getSecurityBtnState() {

        boolean bState;
        wait.until(ExpectedConditions.visibilityOf(findElement(btnIFrames)));

        try {
            if ((findElement(spanIFrames).getAttribute("innerText").equals(sEnabledLbl)
                    && (findElement(spanIFrames).getAttribute("className").equals(sClassNameGreen))
                    && (findElement(btnIFrames).getAttribute("innerText").equals(sDisableBtn))) ) {
                bState = true;
            } else {
                if ((findElement(spanIFrames).getAttribute("innerText").equals(sDisabledLbl)
                        && (findElement(spanIFrames).getAttribute("className").equals(sClassNameRed))
                        && (findElement(btnIFrames).getAttribute("innerText").equals(sEnableBtn))) ) {
                    bState = true;
                } else {
                    return false;
                }
            }

        } catch (NullPointerException e) {
            return false;
        }

        return bState;
    }

}
