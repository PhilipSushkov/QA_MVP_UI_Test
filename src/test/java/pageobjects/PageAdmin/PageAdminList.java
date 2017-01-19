package pageobjects.PageAdmin;

import org.json.simple.JSONArray;
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
    private static By backBtn, sectionTitleInput, yourPageUrlLabel, seoNameInput, pageTitleInput;
    private static By descriptionTextarea, domainSelect, switchBtn, pageTypeExternalRd, globalModuleSetLbl;
    private static By pageTypeInternalRd, pageLayoutSelect, externalURLInput, activeChk, globalModuleSetChk;
    private static String sSheetName, sPathToFile, sDataFile, sDataFileJson, sDataFilePagesJson;
    private static final long DEFAULT_PAUSE = 1000;

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
        pageTypeExternalRd = By.xpath(propUIPageAdmin.getProperty("rd_PageTypeExternal"));
        descriptionTextarea = By.xpath(propUIPageAdmin.getProperty("txtarea_Description"));
        domainSelect = By.xpath(propUIPageAdmin.getProperty("select_Domain"));
        switchBtn = By.xpath(propUIPageAdmin.getProperty("btn_Switch"));
        externalURLInput = By.xpath(propUIPageAdmin.getProperty("input_ExternalURL"));
        activeChk = By.xpath(propUIPageAdmin.getProperty("chk_Active"));
        globalModuleSetChk = By.xpath(propUIPageAdmin.getProperty("chk_GlobalModuleSet"));
        globalModuleSetLbl = By.xpath(propUIPageAdmin.getProperty("lbl_GlobalModuleSet"));

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
                        mmjson.put("Page Type", "Internal");
                        mmjson.put("Page Layout", new Select(driver.findElement(pageLayoutSelect)).getFirstSelectedOption().getText());
                        mmjson.put("Description", findElement(descriptionTextarea).getText());
                        mmjson.put("Domain", new Select(driver.findElement(domainSelect)).getFirstSelectedOption().getText());

                        findElement(switchBtn).click();
                        Thread.sleep(DEFAULT_PAUSE);

                        //System.out.println(new Select(driver.findElement(pageLayoutSelect)).getFirstSelectedOption().getText());
                    } else if (Boolean.parseBoolean(findElement(pageTypeExternalRd).getAttribute("checked")) ) {
                        mmjson.put("Page Type", "External");
                        mmjson.put("External URL", findElement(externalURLInput).getAttribute("value"));
                    }

                    mmjson.put("Active", findElement(activeChk).getAttribute("checked"));

                    // Save Global Module Settings list
                    List<WebElement> globalModuleInputs;
                    List<WebElement> globalModuleLabels;
                    JSONArray globalModuleSettings = new JSONArray();
                    i = 0;
                    globalModuleInputs = findElements(globalModuleSetChk);
                    globalModuleLabels = findElements(globalModuleSetLbl);
                    for (WebElement globalModuleInput:globalModuleInputs)
                    {
                        if (Boolean.parseBoolean(globalModuleInput.getAttribute("checked"))) {
                            //System.out.println(globalModuleLabels.get(j).getText());
                            globalModuleSettings.add(globalModuleLabels.get(i).getText());
                        }
                        i++;
                    }

                    mmjson.put("GlobalModuleSettings", globalModuleSettings);

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
