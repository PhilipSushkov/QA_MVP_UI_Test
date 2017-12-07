package pageobjects.SystemAdmin.PressReleaseFilterList;

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;

import java.util.List;

import static specs.AbstractSpec.propUISystemAdmin;

public class PressReleaseFilterEdit extends AbstractPageObject {
    private static By moduleTitle, filterNameInp, anyTermsTxt, allTermsTxt, notTermsTxt;
    private static By anyIconPlus, allIconPlus, notIconPlus, accordionTitleSpan, tagsTxt;
    private static By tagsIconPlus;
    private static final long DEFAULT_PAUSE = 2000;

    public PressReleaseFilterEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISystemAdmin.getProperty("h1_Title"));
        filterNameInp = By.xpath(propUISystemAdmin.getProperty("input_PRFilterName"));

        anyTermsTxt = By.xpath(propUISystemAdmin.getProperty("txt_ANYTerms"));
        allTermsTxt = By.xpath(propUISystemAdmin.getProperty("txt_ALLTerms"));
        notTermsTxt = By.xpath(propUISystemAdmin.getProperty("txt_NOTTerms"));
        tagsTxt = By.xpath(propUISystemAdmin.getProperty("txt_Tags"));

        anyIconPlus = By.xpath(propUISystemAdmin.getProperty("icon_PlusANY"));
        allIconPlus = By.xpath(propUISystemAdmin.getProperty("icon_PlusALL"));
        notIconPlus = By.xpath(propUISystemAdmin.getProperty("icon_PlusNOT"));
        tagsIconPlus = By.xpath(propUISystemAdmin.getProperty("icon_PlusTags"));

        accordionTitleSpan = By.xpath(propUISystemAdmin.getProperty("span_AccordionTitle"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getFilterNameInp() {
        WebElement element = null;

        try {
            waitForElement(filterNameInp);
            element = findElement(filterNameInp);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getANYTermsTxt() {
        WebElement element = null;

        try {
            waitForElement(anyTermsTxt);
            element = findElement(anyTermsTxt);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getALLTermsTxt() {
        WebElement element = null;

        try {
            waitForElement(allTermsTxt);
            element = findElement(allTermsTxt);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getNOTTermsTxt() {
        WebElement element = null;

        try {
            waitForElement(notTermsTxt);
            element = findElement(notTermsTxt);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getTagsTxt() {
        WebElement element = null;

        try {
            waitForElement(tagsTxt);
            element = findElement(tagsTxt);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getANYIconPlus() {
        WebElement element = null;

        try {
            waitForElement(anyIconPlus);
            element = findElement(anyIconPlus);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getALLIconPlus() {
        WebElement element = null;

        try {
            waitForElement(allIconPlus);
            element = findElement(allIconPlus);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getNOTIconPlus() {
        WebElement element = null;

        try {
            waitForElement(notIconPlus);
            element = findElement(notIconPlus);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getTagsIconPlus() {
        WebElement element = null;

        try {
            waitForElement(tagsIconPlus);
            element = findElement(tagsIconPlus);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public Boolean getNewswires() throws InterruptedException {
        try {
            waitForLoadingScreen(accordionTitleSpan);
            List<WebElement> titlesNewswires = findElements(accordionTitleSpan);

            for (int i=0; i<titlesNewswires.size()-1; i++) {
                System.out.println(titlesNewswires.get(i).getText());

                titlesNewswires.get(i).click();
                Thread.sleep(DEFAULT_PAUSE);

                String itemsNewswiresXPath = String.format(propUISystemAdmin.getProperty("label_FriendlyName"), i+1);
                List<WebElement> itemsNewswires = findElements(By.xpath(itemsNewswiresXPath));

                if (itemsNewswires.size() < 3) {
                    return false;
                }
            }

        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return true;
    }

}
