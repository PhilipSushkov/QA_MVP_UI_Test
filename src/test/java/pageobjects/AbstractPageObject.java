package pageobjects;

import org.apache.commons.collections4.Predicate;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AbstractPageObject implements PageObject {

    public final WebDriver driver;
    public final WebDriverWait wait;
    private final Predicate<WebElement> displayedElementPredicate = new Predicate<WebElement>() {
        @Override
        public boolean evaluate(WebElement t) {
            return t.isDisplayed();
        }
    };

    private static final long DEFAULT_PAUSE = 1000;
    private static final int ATTEMPTS = 3;

    public AbstractPageObject(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 15L);
    }

    @Override
    public WebDriver getDriver() {
        return this.driver;
    }

    @Override
    public Predicate<WebElement> getDisplayedElementPredicate() {
        return this.displayedElementPredicate;
    }

    @Override
    public WebDriverWait getWait() {
        return this.wait;
    }

    public void checkIfUnchecked(By selector) {
        String value = findElement(selector).getAttribute("checked");
        if (!Boolean.parseBoolean(value)) {
            findElement(selector).click();
        }
    }

    public long getScrollPositionY(){
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        return (Long) executor.executeScript("return window.pageYOffset;");
    }

    // Get current URL
    public String getUrl() {
        return driver.getCurrentUrl();
    }

    // Get text value of found element
    public String getText(By by) {
        return findElement(by).getText();
    }

    // Get row quantity of table
    public Integer getGridRowQuantity(int size, int columnsNumber) {
        return size/columnsNumber;
    }

    public void openPageFromCommonTasks(By taskLink) throws InterruptedException {
        waitForElement(taskLink);

        for (int i=0; i<ATTEMPTS; i++) {
            try {
                findElement(taskLink).click();
                break;
            } catch (ElementNotVisibleException e1){
                System.out.println("Attempt #" + i);
            } catch (ElementNotFoundException e2) {
                System.out.println("Attempt #" + i);
            } catch (TimeoutException e3) {
                System.out.println("Attempt #" + i);
            }
        }
    }

    public boolean openPageFromMenu(By menuButton, By menuItem) throws InterruptedException {
        Actions action = new Actions(driver);
        wait.until(ExpectedConditions.visibilityOf(findElement(menuButton)));

        for (int i=0; i<ATTEMPTS; i++) {
            try {
                action.moveToElement(findElement(menuButton)).perform();
                wait.until(ExpectedConditions.visibilityOf(findElement(menuItem)));
                Thread.sleep(DEFAULT_PAUSE);
                findElement(menuItem).click();
                return true;
            } catch (ElementNotVisibleException e){
                System.out.println("Attempt #" + i);
            } catch (ElementNotFoundException e) {
                System.out.println("Attempt #" + i);
            } catch (TimeoutException e) {
                System.out.println("Attempt #" + i);
            }

        }

        return false;
    }

    // Purpose: openContentPageFromMenu takes into account the case when the content page might not be added to the Content Admin by adding it.
    // Contract: "title" must be one of the titles from Content Admin Edit page, e.g "Glossary"
    public void openContentPageFromMenu(By menuButton, By menuItem, String title, By siteAdmin, By siteAdminItem) throws Exception {
        By selectedContent = By.xpath("//span[contains(text(), '" + title + "')]/../..//input[contains(@type, 'checkbox')]");
        By saveButton = By.xpath("//img[contains(@alt, 'Save Order')]");
        Actions action = new Actions(driver);
        wait.until(ExpectedConditions.visibilityOf(findElement(menuButton)));

        for (int i=0; i<ATTEMPTS; i++) {
            try {
                action.moveToElement(findElement(menuButton)).perform();
                wait.until(ExpectedConditions.visibilityOf(findElement(menuItem)));
                Thread.sleep(DEFAULT_PAUSE);
                findElement(menuItem).click();
                break;
            } catch (ElementNotVisibleException e1){
                System.out.println("Attempt #" + i);
            } catch (ElementNotFoundException e2) {
                System.out.println("Attempt #" + i);
            } catch (TimeoutException e3) {
                System.out.println("Attempt #" + i);
            }
            if (i == 4) {
                openPageFromMenu(siteAdmin, siteAdminItem);
                findElement(selectedContent).click();
                findElement(saveButton).click();
                Thread.sleep(DEFAULT_PAUSE);
                openPageFromMenu(menuButton, menuItem);
            }
        }
    }

    public void openEditPageFromAddNew(By menuButton, By menuItem, By linkAddNew) throws Exception {
        Actions action = new Actions(driver);
        wait.until(ExpectedConditions.visibilityOf(findElement(menuButton)));

        for (int i=0; i<ATTEMPTS; i++) {
            try {
                action.moveToElement(findElement(menuButton)).perform();
                wait.until(ExpectedConditions.visibilityOf(findElement(menuItem)));
                Thread.sleep(DEFAULT_PAUSE);
                findElement(menuItem).click();
                wait.until(ExpectedConditions.visibilityOf(findElement(linkAddNew)));
                findElement(linkAddNew).click();
                break;
            } catch (ElementNotVisibleException e1){
                System.out.println("Attempt #" + i);
            } catch (ElementNotFoundException e2) {
                System.out.println("Attempt #" + i);
            } catch (TimeoutException e3) {
                System.out.println("Attempt #" + i);
            }
        }
    }

    public void scrollToElementAndClick(By locator){
        WebElement element = driver.findElement(locator);
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.click().perform();
    }

    public boolean windowDidLoad (String title) {
        for (int i = 0; i < 10; i++) {
            for (String winHandle : driver.getWindowHandles()) {

                if (driver.switchTo().window(winHandle).getTitle().equals(title)) {
                    return true;
                }
            }
            pause(1000L);
        }
        return false;
    }

    public WebElement checkElementExists(By selector) {
        WebElement element = null;

        try {
            waitForAnyElementToAppear(selector);
            element = findElement(selector);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }


}
