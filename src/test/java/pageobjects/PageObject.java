package pageobjects;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public interface PageObject {

    final class ElementNotFoundException extends RuntimeException {
        private ElementNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    WebDriver getDriver();

    Predicate<WebElement> getDisplayedElementPredicate();

    WebDriverWait getWait();

    default void typeInSelect2Input(String keys) {
        findElement(By.cssSelector("input.select2-input.select2-focused")).sendKeys(keys);
    }

    default boolean retryTypeInSelect2Input(String keys) {
        boolean result = false;

        for (int i = 0; i < 3; i++) {
            try {
                findElement(By.cssSelector("input.select2-input.select2-focused")).clear();
                findElement(By.cssSelector("input.select2-input.select2-focused")).sendKeys(keys);
                result = true;
                break;
            } catch (StaleElementReferenceException e) {
                // Retry if Stale Element Reference Exception occurs
            }
        }
        return result;
    }

    default void chooseSelect2Match() {
        findElement(By.className("select2-match")).click();
    }

    default void chooseFirstSelect2Result() {
        retryClick(By.className("select2-result"));
    }

    default WebElement findElement(By selector) {
        try {
            return getDriver().findElement(selector);
        } catch (NoSuchElementException e) {
            throw new ElementNotFoundException("Could not find element " + selector, e);
        }
    }

    default boolean doesElementExist(By selector) {
        try {
            getDriver().findElement(selector);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    default List<WebElement> findElements(By selector) {
        return getDriver().findElements(selector);
    }

    default WebElement findChildOfVisibleParent(By parentSelector, By childSelector) {
        return findVisibleElement(parentSelector).findElement(childSelector);
    }

    default WebElement findVisibleElement(By selector) {
        List<WebElement> elements = getDriver().findElements(selector);

        return CollectionUtils.find(elements, getDisplayedElementPredicate());
    }

    default WebElement findParentOf(By selector) {
        return findElement(selector).findElement(By.xpath("parent::*"));
    }

    default void waitForElement(By selector) {
        getWait().until(ExpectedConditions.presenceOfElementLocated(selector));
    }

    default void waitForElementToDissapear(By selector) {
        getWait().until(ExpectedConditions.invisibilityOfElementLocated(selector));
    }

    default void waitForElementToAppear(By selector) {
        getWait().until(ExpectedConditions.visibilityOfElementLocated(selector));
    }

    default <T extends PageObject> T pause(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return (T) this;
    }

    default void waitForAjaxLoader() {
        waitForElementToDissapear(By.cssSelector(".ajax-load"));
    }

    default void searchSelect2For(By selector, String thingToSelect) {
        findParentOf(selector).findElement(By.cssSelector(".select2-container > a")).click();
        typeInSelect2Input(thingToSelect);
        chooseSelect2Match();
    }

    // Attempts to click an element 3 times to prevent stale element reference.
    //   This is caused when the driver tries to click an element but the element
    //   reloads for some reason
    default boolean retryClick(By by) {
        boolean result = false;

        for (int i = 0; i < 3; i++) {
            try {
                findElement(by).click();
                result = true;
                break;
            } catch (StaleElementReferenceException e) {
                // Retry if Stale Element Reference Exception occurs
            }
        }
        return result;
    }

    default boolean retryClick(WebElement element) {
        boolean result = false;

        for (int i = 0; i < 3; i++) {
            try {
                element.click();
                result = true;
                break;
            } catch (StaleElementReferenceException e) {
                // Retry if Stale Element Reference Exception occurs
            }
        }
        return result;
    }

    default String retryGetText(By by) {
        String result = null;
        for (int i = 0; i < 3; i++) {
            try {
                result = findElement(by).getText();
                break;
            } catch (StaleElementReferenceException e) {
                // Retry if Stale Element Reference Exception occurs
            }
        }
        return result;
    }

}
