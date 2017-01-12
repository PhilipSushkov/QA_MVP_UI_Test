package pageobjects.PageAdmin;

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;

import java.util.List;

import util.Functions;

import static specs.AbstractSpec.propUIPageAdmin;

/**
 * Created by philipsushkov on 2017-01-11.
 */

public class PageAdminList extends AbstractPageObject {
    private static By moduleTitle, contentInnerWrap, dataGridTable, dataGridItemBorder;
    private static String sSheetName, sPathToFile, sDataFile;
    private static final long DEFAULT_PAUSE = 2000;

    public PageAdminList(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIPageAdmin.getProperty("spanModule_Title"));
        contentInnerWrap = By.xpath(propUIPageAdmin.getProperty("span_ContentInnerWrap"));
        dataGridTable = By.xpath(propUIPageAdmin.getProperty("table_DataGrid"));
        dataGridItemBorder = By.xpath(propUIPageAdmin.getProperty("table_DataGridItemBorder"));

        sSheetName = "PageItems";
        sPathToFile = System.getProperty("user.dir") + propUIPageAdmin.getProperty("xlsPath_PageAdmin");
        sDataFile = propUIPageAdmin.getProperty("xlsData_PageAdmin");
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public Boolean getPageItems() {
        boolean pageItems = false;
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

                pageItems = true;

                for (WebElement element:elements)
                {
                    //System.out.println(element.getText());
                    i++;
                    sa1[i][0] = Integer.toString(i);
                    sa1[i][1] = element.getText();
                }
            }
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        //System.out.println(sPathToFile + sDataFile);
        Functions.WriteExcelSheet(sSheetName, sa1, sPathToFile + sDataFile);
        return pageItems;
    }

    public Boolean clickPageItems() throws InterruptedException {
        boolean pageNames = false;
        By innerWrapPage;
        int columnsTotal = 2;
        String sExcept = "ID";
        String[][] sa1 = null;

        sa1 = Functions.ReadExcelSheet(sSheetName, columnsTotal, sExcept, sPathToFile + sDataFile);

        try {
            for (int i=0; i<=sa1.length-1; i++) {
                //System.out.println(sa1[i][1]);
                innerWrapPage = By.xpath("//div[contains(@id, 'divContent')]//span[contains(@class, 'innerWrap')][(text()=\""+sa1[i][1]+"\")]/parent::span/parent::a");
                waitForElement(innerWrapPage);
                findElement(innerWrapPage).click();

                //Thread.sleep(DEFAULT_PAUSE);

                waitForElement(dataGridTable);
                //System.out.println(findElements(By.xpath("//td[contains(@class, 'DataGridItemBorder')]")).get(1).getText());

                if (!findElements(dataGridItemBorder).get(1).getText().contains(sa1[i][1])) {
                    pageNames = false;
                    break;
                } else {
                    pageNames = true;
                }
            }

        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        } catch (Exception e4) {
        }

        return pageNames;
    }

}
