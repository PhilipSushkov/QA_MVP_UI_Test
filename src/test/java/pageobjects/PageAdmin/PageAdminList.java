package pageobjects.PageAdmin;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import pageobjects.AbstractPageObject;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static By moduleInstancesSpan, sectionTitleSpan, pageNameLbl, moduleNameLbl, editModuleImg, moduleDefinitionSelect;
    private static String sSheetName, sPathToFile, sDataFile, sDataFileJson, sDataFilePagesJson, sDataFileModulesJson;
    private static final long DEFAULT_PAUSE = 1000;

    public PageAdminList(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIPageAdmin.getProperty("spanModule_Title"));
        contentInnerWrap = By.xpath(propUIPageAdmin.getProperty("span_ContentInnerWrap"));
        dataGridTable = By.xpath(propUIPageAdmin.getProperty("table_DataGrid"));
        dataGridItemBorder = By.xpath(propUIPageAdmin.getProperty("table_DataGridItemBorder"));
        editPageImg = By.xpath(propUIPageAdmin.getProperty("img_EditPage"));
        editModuleImg = By.xpath(propUIPageAdmin.getProperty("img_EditModule"));
        pageNameLbl = By.xpath(propUIPageAdmin.getProperty("lbl_PageName"));
        moduleNameLbl = By.xpath(propUIPageAdmin.getProperty("lbl_ModuleName"));
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
        moduleInstancesSpan = By.xpath(propUIPageAdmin.getProperty("span_ModuleInstances"));
        sectionTitleSpan = By.xpath(propUIPageAdmin.getProperty("span_SectionTitle"));
        moduleDefinitionSelect = By.xpath(propUIPageAdmin.getProperty("select_ModuleDefinition"));

        sSheetName = "PageItems";
        sDataFileJson = "PageNames.json";
        sDataFilePagesJson = "PagesProp.json";
        sDataFileModulesJson = "ModulesProp.json";
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
        Functions.WriteArrayToJSON(sa2, sPathToFile + sDataFileJson, "page_names");
        return pageItems;
    }

    public Boolean clickPageItems() throws InterruptedException {
        boolean pageNames = true;
        By innerWrapPage;
        String[] sa2;
        int i, j;

        sa2 = Functions.ReadArrayFromJSON(sPathToFile + sDataFileJson, "page_names");

        Functions.ClearJSONfile(sPathToFile + sDataFilePagesJson);
        Functions.ClearJSONfile(sPathToFile + sDataFileModulesJson);

        try {
            for (i=0; i<sa2.length; i++) {
                //System.out.println(sa1[i]);
                innerWrapPage = By.xpath("//div[contains(@id, 'divContent')]//span[contains(@class, 'innerWrap')][(text()=\""+sa2[i]+"\")]/parent::span/parent::a");
                waitForElement(innerWrapPage);
                findElement(innerWrapPage).click();

                waitForElement(dataGridTable);
                //System.out.println(findElements(By.xpath("//td[contains(@class, 'DataGridItemBorder')]")).get(1).getText());

                waitForElement(editPageImg);


                // print page list
                List<WebElement> pageList = findElements(pageNameLbl);
                /*
                System.out.println(sa2[i]);
                for (j=0; j<pageList.size(); j++)
                {
                    System.out.println(findElements(pageNameLbl).get(j).getText());
                }
                System.out.println(" --- ");
                */

                // Starts pages loop
                List<WebElement> editPages = findElements(editPageImg);
                for (int pageNum=0; pageNum<editPages.size(); pageNum++) {
                    findElements(editPageImg).get(pageNum).click();

                    waitForElement(backBtn);

                    // Create JSON object for Pages
                    JSONParser parser = new JSONParser();
                    Object obj;
                    JSONObject jsonObject = new JSONObject();
                    JSONObject mmjson = new JSONObject();

                    // Create JSON object for Modules
                    JSONParser parserModule = new JSONParser();
                    Object objModule;
                    JSONObject jsonObjectModule = new JSONObject();
                    JSONObject mmjsonModule = new JSONObject();

                    // Add values to JSON file
                    try {
                        try {
                            obj = parser.parse(new FileReader(sPathToFile + sDataFilePagesJson));
                            jsonObject = (JSONObject) obj;

                            objModule = parserModule.parse(new FileReader(sPathToFile + sDataFileModulesJson));
                            jsonObjectModule = (JSONObject) objModule;
                        } catch (ParseException e) {
                        }

                        URL pageURL = new URL(getUrl());
                        //System.out.println(pageURL.getQuery());

                        String[] params = pageURL.getQuery().split("&");
                        JSONObject jsonURLQuery = new JSONObject();

                        Map<String, String> mapQuery = new HashMap<String, String>();
                        for (String param : params) {
                            String name = param.split("=")[0];
                            String value = param.split("=")[1];
                            mapQuery.put(name, value);
                            jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);
                        }

                        mmjson.put("url_query", jsonURLQuery);

                        mmjson.put("section_title", findElement(sectionTitleInput).getAttribute("value"));
                        mmjson.put("you_page_url", findElement(yourPageUrlLabel).getText() + findElement(seoNameInput).getAttribute("value"));
                        mmjson.put("page_title", findElement(pageTitleInput).getAttribute("value"));

                        if (Boolean.parseBoolean(findElement(pageTypeInternalRd).getAttribute("checked"))) {
                            mmjson.put("page_type", "Internal");
                            mmjson.put("page_layout", new Select(driver.findElement(pageLayoutSelect)).getFirstSelectedOption().getText());
                            mmjson.put("description", findElement(descriptionTextarea).getText());
                            mmjson.put("domain", new Select(driver.findElement(domainSelect)).getFirstSelectedOption().getText());

                            findElement(switchBtn).click();
                            Thread.sleep(DEFAULT_PAUSE);

                            //System.out.println(new Select(driver.findElement(pageLayoutSelect)).getFirstSelectedOption().getText());
                        } else if (Boolean.parseBoolean(findElement(pageTypeExternalRd).getAttribute("checked"))) {
                            mmjson.put("page_type", "External");
                            mmjson.put("external_url", findElement(externalURLInput).getAttribute("value"));
                        }

                        mmjson.put("active", findElement(activeChk).getAttribute("checked"));

                        // Save Global Module Settings list
                        List<WebElement> globalModuleInputs;
                        List<WebElement> globalModuleLabels;
                        JSONArray globalModuleSettings = new JSONArray();
                        j = 0;
                        globalModuleInputs = findElements(globalModuleSetChk);
                        globalModuleLabels = findElements(globalModuleSetLbl);
                        for (WebElement globalModuleInput : globalModuleInputs) {
                            if (Boolean.parseBoolean(globalModuleInput.getAttribute("checked"))) {
                                //System.out.println(globalModuleLabels.get(j).getText());
                                globalModuleSettings.add(globalModuleLabels.get(j).getText());
                            }
                            j++;
                        }
                        mmjson.put("global_module_settings", globalModuleSettings);

                        // Save Modules list
                        List<WebElement> moduleSpans;
                        JSONArray moduleList = new JSONArray();
                        j = 0;
                        moduleSpans = findElements(moduleInstancesSpan);
                        for (WebElement moduleSpan : moduleSpans) {
                            //System.out.println(moduleSpan.getText());
                            moduleList.add(moduleSpan.getText());
                            j++;
                        }
                        mmjson.put("modules", moduleList);


                        // Open Module pages
                        List<WebElement> modulePages;
                        modulePages = findElements(moduleInstancesSpan);
                        System.out.println(" --- " + findElement(sectionTitleInput).getAttribute("value") + " modules start --- ");
                        for (int moduleNum=0; moduleNum<modulePages.size(); moduleNum++) {
                            System.out.println(findElements(moduleNameLbl).get(moduleNum).getText());
                            findElements(editModuleImg).get(moduleNum).click();
                            waitForElement(backBtn);

                            System.out.println(new Select(driver.findElement(moduleDefinitionSelect)).getFirstSelectedOption().getText());

                            JSONArray moduleProp = new JSONArray();
                            jsonObjectModule.put(new Select(driver.findElement(moduleDefinitionSelect)).getFirstSelectedOption().getText(), moduleProp);

                            findElement(backBtn).click();
                        }
                        System.out.println(" --- " + findElement(sectionTitleInput).getAttribute("value") + " modules end --- ");


                        // Save Sub Pages list
                        List<WebElement> subPagesSpans;
                        JSONArray subPagesList = new JSONArray();
                        j = 0;
                        subPagesSpans = findElements(sectionTitleSpan);
                        for (WebElement subPagesSpan : subPagesSpans) {
                            //System.out.println(subPagesSpan.getText());
                            subPagesList.add(subPagesSpan.getText());
                            j++;
                        }
                        mmjson.put("subpages", subPagesList);


                        //jsonObject.remove(sa2[i]);
                        //jsonObject.put(sa2[i], mmjson);
                        jsonObject.remove(findElement(sectionTitleInput).getAttribute("value"));
                        jsonObject.put(findElement(sectionTitleInput).getAttribute("value"), mmjson);

                        //System.out.println(obj.toString());

                        FileWriter file = new FileWriter(sPathToFile + sDataFilePagesJson);
                        file.write(jsonObject.toJSONString().replace("\\", ""));
                        file.flush();
                        file.close();

                        FileWriter fileModule = new FileWriter(sPathToFile + sDataFileModulesJson);
                        fileModule.write(jsonObjectModule.toJSONString().replace("\\", ""));
                        fileModule.flush();
                        fileModule.close();

                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                        pageNames = false;
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        pageNames = false;
                    }

                    findElement(backBtn).click();

                    waitForElement(dataGridItemBorder);

                    /*
                    if (!findElements(dataGridItemBorder).get(1).getText().contains(sa2[i])) {
                        pageNames = false;
                        break;
                    } else {
                        pageNames = true;
                    }
                    */

                }
            }

        } catch (ElementNotFoundException e1) {
            e1.printStackTrace();
            pageNames = false;
        } catch (ElementNotVisibleException e2) {
            e2.printStackTrace();
            pageNames = false;
        } catch (TimeoutException e3) {
            e3.printStackTrace();
            pageNames = false;
        } catch (Exception e4) {
            e4.printStackTrace();
            pageNames = false;
        }

        return pageNames;
    }

    public String getUrlPage(String searchPhrase) throws InterruptedException {
        JSONParser parser = new JSONParser();
        String urlPage = null;

        try {
            Object obj = parser.parse(new FileReader(sPathToFile + sDataFilePagesJson));
            JSONObject jsonObject = (JSONObject) obj;
            urlPage = JsonPath.read(jsonObject, "$."+searchPhrase+".you_page_url");
            String itemID = JsonPath.read(jsonObject, "$."+searchPhrase+".url_query.ItemID");
            System.out.println(itemID);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        System.out.println(urlPage);

        return urlPage;
    }


}
