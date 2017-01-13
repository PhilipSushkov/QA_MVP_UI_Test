package pageobjects.PageAdmin;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import pageobjects.AbstractPageObject;

import java.io.*;
import java.util.List;

import util.Functions;

import static specs.AbstractSpec.propUIPageAdmin;

/**
 * Created by philipsushkov on 2017-01-11.
 */

public class PageAdminList extends AbstractPageObject {
    private static By moduleTitle, contentInnerWrap, dataGridTable, dataGridItemBorder, editPageImg;
    private static By backBtn, sectionTitleInput, yourPageUrlLabel, seoNameInput,pageTitleInput;
    private static By pageTypeInternalRd, pageLayoutSelect;
    private static String sSheetName, sPathToFile, sDataFile, sDataFileJson, sDataFilePagesJson;
    private static final long DEFAULT_PAUSE = 2000;

    public PageAdminList(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIPageAdmin.getProperty("spanModule_Title"));
        contentInnerWrap = By.xpath(propUIPageAdmin.getProperty("span_ContentInnerWrap"));
        dataGridTable = By.xpath(propUIPageAdmin.getProperty("table_DataGrid"));
        dataGridItemBorder = By.xpath(propUIPageAdmin.getProperty("table_DataGridItemBorder"));
        editPageImg = By.xpath(propUIPageAdmin.getProperty("img_EditPage"));
        backBtn = By.xpath(propUIPageAdmin.getProperty("btn_Back"));
        sectionTitleInput = By.xpath(propUIPageAdmin.getProperty("input_SectionTitle"));
        yourPageUrlLabel = By.xpath(propUIPageAdmin.getProperty("span_YourPageUrl"));
        seoNameInput = By.xpath(propUIPageAdmin.getProperty("input_SeoName"));
        pageTitleInput = By.xpath(propUIPageAdmin.getProperty("input_PageTitle"));
        pageLayoutSelect = By.xpath(propUIPageAdmin.getProperty("select_PageLayout"));
        pageTypeInternalRd = By.xpath(propUIPageAdmin.getProperty("rd_PageTypeInternal"));

        sSheetName = "PageItems";
        sDataFileJson = "PageNames.json";
        sDataFilePagesJson = "PagesProp.json";
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

        Functions.ClearJSONfile(sPathToFile + sDataFilePagesJson);

        try {
            for (int i=0; i<sa2.length; i++) {
                //System.out.println(sa1[i]);
                innerWrapPage = By.xpath("//div[contains(@id, 'divContent')]//span[contains(@class, 'innerWrap')][(text()=\""+sa2[i]+"\")]/parent::span/parent::a");
                waitForElement(innerWrapPage);
                findElement(innerWrapPage).click();

                waitForElement(dataGridTable);
                //System.out.println(findElements(By.xpath("//td[contains(@class, 'DataGridItemBorder')]")).get(1).getText());

                waitForElement(editPageImg);
                findElements(editPageImg).get(0).click();

                waitForElement(backBtn);

                JSONParser parser = new JSONParser();
                Object obj;
                JSONObject jsonObject = new JSONObject();
                JSONObject mmjson=new JSONObject();

                // Add values to JSON file
                try {
                    try {
                        obj = parser.parse(new FileReader(sPathToFile + sDataFilePagesJson));
                        jsonObject = (JSONObject) obj;
                    } catch (ParseException e) {
                    }

                    mmjson.put("Section Title", findElement(sectionTitleInput).getAttribute("value"));
                    mmjson.put("You page URL", findElement(yourPageUrlLabel).getText()+findElement(seoNameInput).getAttribute("value"));
                    mmjson.put("Page Title", findElement(pageTitleInput).getAttribute("value"));
                    if (Boolean.parseBoolean(findElement(pageTypeInternalRd).getAttribute("checked")) ) {
                        mmjson.put("Page Layout", new Select(driver.findElement(pageLayoutSelect)).getFirstSelectedOption().getText());
                        System.out.println(new Select(driver.findElement(pageLayoutSelect)).getFirstSelectedOption().getText());
                    }

                    jsonObject.remove(sa2[i]);
                    jsonObject.put(sa2[i], mmjson);

                    //System.out.println(obj.toString());

                    FileWriter file = new FileWriter(sPathToFile + sDataFilePagesJson);
                    file.write(jsonObject.toJSONString().replace("\\", ""));
                    file.flush();
                    file.close();

                } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

                //obj.put("section title", findElement(sectionTitleInput).getAttribute("value"));

                /*
                JsonWriter jsonWriter = null;
                try {
                    jsonWriter = new JsonWriter(new FileWriter(sPathToFile + sDataFileJson));
                    jsonWriter.beginObject();
                        jsonWriter.name(sa2[i]);
                            jsonWriter.beginArray();
                                jsonWriter.beginObject();
                                    jsonWriter.name("section title");
                                    jsonWriter.value(findElement(sectionTitleInput).getAttribute("value"));
                                jsonWriter.endObject();
                            jsonWriter.endArray();
                    jsonWriter.endObject();
                } catch (IOException e) {
                } finally {
                    try {
                        jsonWriter.close();
                    } catch (IOException e) {
                    }
                }
                */

                // ---------------------------------------------------------


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
