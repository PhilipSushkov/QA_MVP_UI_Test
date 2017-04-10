package pageobjects.SystemAdmin.SiteMaintenance;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISystemAdmin;

/**
 * Created by philipsushkov on 2017-04-10.
 */

public class FunctionalBtn extends AbstractPageObject {
    private static By btnGoLive, btnOneTouch, spanOneTouch, btnTwoFactorAuthentication, spanTwoFactorAuthentication, btnIFrames, spanIFrames, btnUpdateRememberDays;
    private static final String sEnabledLbl = "ENABLED", sDisabledLbl = "DISABLED", sClassNameRed = "ng-binding red", sClassNameGreen = "ng-binding green";
    private static final String sEnableBtn = "ENABLE", sDisableBtn = "DISABLE";
    private static final long DEFAULT_PAUSE = 2500;

    public FunctionalBtn(WebDriver driver) {
        super(driver);
        btnGoLive = By.xpath(propUISystemAdmin.getProperty("btn_GoLive"));
        btnOneTouch = By.xpath(propUISystemAdmin.getProperty("btn_OneTouch"));
        spanOneTouch = By.xpath(propUISystemAdmin.getProperty("span_OneTouch"));
        btnTwoFactorAuthentication = By.xpath(propUISystemAdmin.getProperty("btn_TwoFactorAuthentication"));
        btnUpdateRememberDays = By.xpath(propUISystemAdmin.getProperty("btn_UpdateRememberDays"));
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
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        }

        return bGoLiveBtnState;
    }


    public boolean getPressReleasePublishingLoginBtnState() {
        boolean bState = false;
        wait.until(ExpectedConditions.visibilityOf(findElement(btnOneTouch)));

        /*
        System.out.println(findElement(spanOneTouch).getAttribute("innerText").equals(sEnabledLbl));
        System.out.println(findElement(spanOneTouch).getAttribute("className").equals(sClassNameGreen));
        System.out.println(findElement(btnOneTouch).getAttribute("innerText").equals(sDisableBtn));
        */

        try {
            if (findElement(spanOneTouch).getAttribute("innerText").equals(sEnabledLbl)
                    && (findElement(spanOneTouch).getAttribute("className").equals(sClassNameGreen))
                    && (findElement(btnOneTouch).getAttribute("innerText").equals(sDisableBtn)) ) {
                bState = true;
            } else {
                if (findElement(spanOneTouch).getAttribute("innerText").equals(sDisabledLbl)
                        && (findElement(spanOneTouch).getAttribute("className").equals(sClassNameRed))
                        && (findElement(btnOneTouch).getAttribute("innerText").equals(sEnableBtn)) ) {
                    bState = true;
                } else {
                    return false;
                }
            }

        } catch (NullPointerException e) {
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        }

        return bState;
    }


    public boolean clickPressReleasePublishingLoginBtn() throws InterruptedException {
        boolean bState = false;
        String sInnerTextBtn, sInnerTextLbl, sClassNameLbl;
        wait.until(ExpectedConditions.visibilityOf(findElement(btnOneTouch)));

        try {

            sInnerTextBtn = findElement(btnOneTouch).getAttribute("innerText");

            if (sInnerTextBtn.equals(sDisableBtn)) {
                findElement(btnOneTouch).click();

                Thread.sleep(DEFAULT_PAUSE);

                sInnerTextBtn = findElement(btnOneTouch).getAttribute("innerText");
                sInnerTextLbl = findElement(spanOneTouch).getAttribute("innerText");
                sClassNameLbl = findElement(spanOneTouch).getAttribute("className");

                if (sInnerTextBtn.equals(sEnableBtn)
                        && sInnerTextLbl.equals(sDisabledLbl)
                        && sClassNameLbl.equals(sClassNameRed)) {
                    bState = true;
                    return bState;
                }
            }

            if (sInnerTextBtn.equals(sEnableBtn)) {
                findElement(btnOneTouch).click();

                Thread.sleep(DEFAULT_PAUSE);

                sInnerTextBtn = findElement(btnOneTouch).getAttribute("innerText");
                sInnerTextLbl = findElement(spanOneTouch).getAttribute("innerText");
                sClassNameLbl = findElement(spanOneTouch).getAttribute("className");

                if (sInnerTextBtn.equals(sDisableBtn)
                        && sInnerTextLbl.equals(sEnabledLbl)
                        && sClassNameLbl.equals(sClassNameGreen)) {
                    bState = true;
                    return bState;
                }
            }

        } catch (NullPointerException e) {
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        }

        return bState;
    }


    public boolean getTwoFactorAuthenticationBtnState() {
        boolean bState = false;
        wait.until(ExpectedConditions.visibilityOf(findElement(btnTwoFactorAuthentication)));

        try {
            if (findElement(spanTwoFactorAuthentication).getAttribute("innerText").equals(sEnabledLbl)
                    && (findElement(spanTwoFactorAuthentication).getAttribute("className").equals(sClassNameGreen))
                    && (findElement(btnTwoFactorAuthentication).getAttribute("innerText").equals(sDisableBtn)) ) {
                bState = true;
            } else {
                if (findElement(spanTwoFactorAuthentication).getAttribute("innerText").equals(sDisabledLbl)
                        && (findElement(spanTwoFactorAuthentication).getAttribute("className").equals(sClassNameRed))
                        && (findElement(btnTwoFactorAuthentication).getAttribute("innerText").equals(sEnableBtn)) ) {
                    bState = true;
                } else {
                    return false;
                }
            }

        } catch (NullPointerException e) {
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        }

        return bState;
    }


    public boolean clickTwoFactorAuthenticationBtn() throws InterruptedException {
        boolean bState = false;
        String sInnerTextBtn, sInnerTextLbl, sClassNameLbl;
        wait.until(ExpectedConditions.visibilityOf(findElement(btnTwoFactorAuthentication)));

        try {

            sInnerTextBtn = findElement(btnTwoFactorAuthentication).getAttribute("innerText");

            if (sInnerTextBtn.equals(sDisableBtn)) {
                findElement(btnTwoFactorAuthentication).click();

                Thread.sleep(DEFAULT_PAUSE);

                sInnerTextBtn = findElement(btnTwoFactorAuthentication).getAttribute("innerText");
                sInnerTextLbl = findElement(spanTwoFactorAuthentication).getAttribute("innerText");
                sClassNameLbl = findElement(spanTwoFactorAuthentication).getAttribute("className");

                if (sInnerTextBtn.equals(sEnableBtn)
                        && sInnerTextLbl.equals(sDisabledLbl)
                        && sClassNameLbl.equals(sClassNameRed)) {
                    bState = true;
                    return bState;
                }
            }

            if (sInnerTextBtn.equals(sEnableBtn)) {
                findElement(btnTwoFactorAuthentication).click();

                Thread.sleep(DEFAULT_PAUSE);
                wait.until(ExpectedConditions.visibilityOf(findElement(btnUpdateRememberDays)));

                sInnerTextBtn = findElement(btnTwoFactorAuthentication).getAttribute("innerText");
                sInnerTextLbl = findElement(spanTwoFactorAuthentication).getAttribute("innerText");
                sClassNameLbl = findElement(spanTwoFactorAuthentication).getAttribute("className");

                if (sInnerTextBtn.equals(sDisableBtn)
                        && sInnerTextLbl.equals(sEnabledLbl)
                        && sClassNameLbl.equals(sClassNameGreen)) {
                    bState = true;
                    return bState;
                }
            }

        } catch (NullPointerException e) {
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        }

        return bState;
    }


    public boolean getSecurityBtnState() {
        boolean bState = false;
        wait.until(ExpectedConditions.visibilityOf(findElement(btnIFrames)));

        try {
            if (findElement(spanIFrames).getAttribute("innerText").equals(sEnabledLbl)
                    && (findElement(spanIFrames).getAttribute("className").equals(sClassNameGreen))
                    && (findElement(btnIFrames).getAttribute("innerText").equals(sDisableBtn)) ) {
                bState = true;
            } else {
                if (findElement(spanIFrames).getAttribute("innerText").equals(sDisabledLbl)
                        && (findElement(spanIFrames).getAttribute("className").equals(sClassNameRed))
                        && (findElement(btnIFrames).getAttribute("innerText").equals(sEnableBtn)) ) {
                    bState = true;
                } else {
                    return false;
                }
            }

        } catch (NullPointerException e) {
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        }

        return bState;
    }


    public boolean clickIFramesBtn() throws InterruptedException {
        boolean bState = false;
        String sInnerTextBtn, sInnerTextLbl, sClassNameLbl;
        wait.until(ExpectedConditions.visibilityOf(findElement(btnIFrames)));

        try {

            sInnerTextBtn = findElement(btnIFrames).getAttribute("innerText");

            if (sInnerTextBtn.equals(sDisableBtn)) {
                findElement(btnIFrames).click();

                Thread.sleep(DEFAULT_PAUSE);

                sInnerTextBtn = findElement(btnIFrames).getAttribute("innerText");
                sInnerTextLbl = findElement(spanIFrames).getAttribute("innerText");
                sClassNameLbl = findElement(spanIFrames).getAttribute("className");

                if (sInnerTextBtn.equals(sEnableBtn)
                        && sInnerTextLbl.equals(sDisabledLbl)
                        && sClassNameLbl.equals(sClassNameRed)) {
                    bState = true;
                    return bState;
                }
            }

            if (sInnerTextBtn.equals(sEnableBtn)) {
                findElement(btnIFrames).click();

                Thread.sleep(DEFAULT_PAUSE);

                sInnerTextBtn = findElement(btnIFrames).getAttribute("innerText");
                sInnerTextLbl = findElement(spanIFrames).getAttribute("innerText");
                sClassNameLbl = findElement(spanIFrames).getAttribute("className");

                if (sInnerTextBtn.equals(sDisableBtn)
                        && sInnerTextLbl.equals(sEnabledLbl)
                        && sClassNameLbl.equals(sClassNameGreen)) {
                    bState = true;
                    return bState;
                }
            }

        } catch (NullPointerException e) {
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        }

        return bState;
    }

}
