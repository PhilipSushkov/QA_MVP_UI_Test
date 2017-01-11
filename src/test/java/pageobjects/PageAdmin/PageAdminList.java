package pageobjects.PageAdmin;

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;

import java.util.List;
import java.util.Properties;

import util.Functions;

import static specs.AbstractSpec.propUIPageAdmin;

/**
 * Created by philipsushkov on 2017-01-11.
 */

public class PageAdminList extends AbstractPageObject {
    private static By moduleTitle, contentInnerWrap;
    private static String sSheetName, sPathToFile, sDataFile;
    private static final long DEFAULT_PAUSE = 2000;

    public PageAdminList(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIPageAdmin.getProperty("spanModule_Title"));
        contentInnerWrap = By.xpath(propUIPageAdmin.getProperty("span_ContentInnerWrap"));
        sSheetName = "PageItems";
        sPathToFile = System.getProperty("user.dir") + propUIPageAdmin.getProperty("xlsPath_PageAdmin");
        sDataFile = propUIPageAdmin.getProperty("xlsData_PageAdmin");
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public List<WebElement> getPageItems() {
        List<WebElement> elements = null;
        String[][] sa1 = null;
        int i;

        try {
            waitForElement(contentInnerWrap);
            elements = findElements(contentInnerWrap);

            if (elements.size() > 0 ) {
                sa1 = new String[elements.size()+1][2];
                i = 0;

                sa1[i][0] = "ID";
                sa1[i][1] = "Page Name";

                for (WebElement element:elements)
                {
                    System.out.println(element.getText());
                    i++;
                    sa1[i][0] = Integer.toString(i);
                    sa1[i][1] = element.getText();
                }
            }
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        System.out.println(sPathToFile + sDataFile);
        Functions.WriteExcelSheet(sSheetName, sa1, sPathToFile + sDataFile);
        return elements;
    }

    public void clickPageItems() throws InterruptedException {
        By innerWrapPage;
        int columnsTotal = 2;
        String sExcept = "ID";
        String[][] sa1 = null;

        sa1 = Functions.ReadExcelSheet(sSheetName, columnsTotal, sExcept, sPathToFile + sDataFile);

        for (int i=0; i<=sa1.length-1; i++) {
            System.out.println(sa1[i][1]);
            innerWrapPage = By.xpath("//div[contains(@id, 'divContent')]//span[contains(@class, 'innerWrap')][contains(text(), \""+sa1[i][1]+"\")]/parent::span/parent::a");
            waitForElement(innerWrapPage);
            findElement(innerWrapPage).click();
            //Thread.sleep(DEFAULT_PAUSE);
        }

    }

}
