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
    private static By moduleTitle, contentInnerWrap, dataGridTable, dataGridItemBorder, editPageImg;
    private static By backBtn;
    private static String sSheetName, sPathToFile, sDataFile, sDataFileJson;
    private static final long DEFAULT_PAUSE = 2000;

    public PageAdminList(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIPageAdmin.getProperty("spanModule_Title"));
        contentInnerWrap = By.xpath(propUIPageAdmin.getProperty("span_ContentInnerWrap"));
        dataGridTable = By.xpath(propUIPageAdmin.getProperty("table_DataGrid"));
        dataGridItemBorder = By.xpath(propUIPageAdmin.getProperty("table_DataGridItemBorder"));
        editPageImg = By.xpath(propUIPageAdmin.getProperty("img_EditPage"));
        backBtn = By.xpath(propUIPageAdmin.getProperty("btn_Back"));

        sSheetName = "PageItems";
        sDataFileJson = "PageNames.json";
        sPathToFile = System.getProperty("user.dir") + propUIPageAdmin.getProperty("dataPath_PageAdmin");
        sDataFile = propUIPageAdmin.getProperty("xlsData_PageAdmin");
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public Boolean getPageItems() {
        boolean pageItems = false;
        List<WebElement> elements;
        String[][] sa1;
        String[] sa2 = null;
        int i;

        try {
            waitForElement(contentInnerWrap);
            elements = findElements(contentInnerWrap);

            if (elements.size() > 0 ) {
                sa1 = new String[elements.size()+1][2];
                sa2 = new String[elements.size()];
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
                    sa2[i-1] = element.getText();
                }
            }
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        //System.out.println(sPathToFile + sDataFile);
        //Functions.WriteExcelSheet(sSheetName, sa1, sPathToFile + sDataFile);
        Functions.WriteArrayToJSON(sa2, sPathToFile + sDataFileJson, "page names");
        return pageItems;
    }

    public Boolean clickPageItems() throws InterruptedException {
        boolean pageNames = false;
        By innerWrapPage;
        String[] sa2;

        sa2 = Functions.ReadArrayFromJSON(sPathToFile + sDataFileJson, "page names");

        try {
            for (int i=0; i<sa2.length; i++) {
                //System.out.println(sa1[i]);
                innerWrapPage = By.xpath("//div[contains(@id, 'divContent')]//span[contains(@class, 'innerWrap')][(text()=\""+sa2[i]+"\")]/parent::span/parent::a");
                waitForElement(innerWrapPage);
                findElement(innerWrapPage).click();

                waitForElement(dataGridTable);
                //System.out.println(findElements(By.xpath("//td[contains(@class, 'DataGridItemBorder')]")).get(1).getText());

                waitForElement(editPageImg);
                findElement(editPageImg).click();

                waitForElement(backBtn);
                findElement(backBtn).click();

                waitForElement(dataGridItemBorder);

                if (!findElements(dataGridItemBorder).get(1).getText().contains(sa2[i])) {
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
