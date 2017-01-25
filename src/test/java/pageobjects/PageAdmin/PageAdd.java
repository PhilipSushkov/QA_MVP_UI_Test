package pageobjects.PageAdmin;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;
import util.Functions;

import static specs.AbstractSpec.propUIPageAdmin;

/**
 * Created by philipsushkov on 2017-01-24.
 */
public class PageAdd extends AbstractPageObject {
    private static By addNewBtn, backBtn, sectionTitleInput, pageTypeInternalRd, pageTypeExternalRd, externalURLInput;
    private static By pageTemplateSelect, parentPageSelect, showNavChk, openNewWindChk;
    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    public PageAdd(WebDriver driver) {
        super(driver);
        addNewBtn = By.xpath(propUIPageAdmin.getProperty("btn_AddNew"));
        backBtn = By.xpath(propUIPageAdmin.getProperty("btn_Back"));
        sectionTitleInput = By.xpath(propUIPageAdmin.getProperty("input_SectionTitle"));
        pageTypeInternalRd = By.xpath(propUIPageAdmin.getProperty("rd_PageTypeInt"));
        pageTypeExternalRd = By.xpath(propUIPageAdmin.getProperty("rd_PageTypeExt"));
        externalURLInput = By.xpath(propUIPageAdmin.getProperty("input_ExternalURL"));
        pageTemplateSelect = By.xpath(propUIPageAdmin.getProperty("select_PageTemplate"));
        parentPageSelect = By.xpath(propUIPageAdmin.getProperty("select_ParentPage"));
        showNavChk = By.xpath(propUIPageAdmin.getProperty("chk_ShowInNav"));
        openNewWindChk = By.xpath(propUIPageAdmin.getProperty("chk_OpenInNewWindow"));

        sPathToFile = System.getProperty("user.dir") + propUIPageAdmin.getProperty("dataPath_PageAdmin");
        sDataFileJson = propUIPageAdmin.getProperty("json_CreatePageData");

        parser = new JSONParser();
    }

    public Boolean createNewPage(String pageName) {
        int randNum = Functions.randInt(0, 999);

        waitForElement(addNewBtn);
        findElement(addNewBtn).click();
        waitForElement(backBtn);

        System.out.println(pageName);

        try {
            Object obj = parser.parse(new FileReader(sPathToFile + sDataFileJson));

            JSONObject jsonObject = (JSONObject) obj;
            //System.out.println(jsonObject);

            JSONObject pagesDataObj = (JSONObject) jsonObject.get(pageName);

            findElement(sectionTitleInput).sendKeys(pagesDataObj.get("section_title").toString() + randNum);

            if (pagesDataObj.get("page_type").toString().equals("Internal")) {
                findElement(pageTypeInternalRd).click();
                pause(1000);
                findElement(pageTemplateSelect).sendKeys(pagesDataObj.get("page_template").toString());
            } else if (pagesDataObj.get("page_type").toString().equals("External")) {
                findElement(pageTypeExternalRd).click();
                pause(1000);
                findElement(externalURLInput).sendKeys(pagesDataObj.get("external_url").toString());
            } else {
                System.out.println("Page type in not defined. May lead to incorrect test implementation.");
            }

            pause(3000);

            if (Boolean.parseBoolean(pagesDataObj.get("show_in_navigation").toString())) {
                if (!Boolean.parseBoolean(findElement(showNavChk).getAttribute("checked"))) {
                    System.out.println("show_in_navigation - true, show_in_navigation - unchecked");
                    findElement(showNavChk).click();
                } else {
                    System.out.println("show_in_navigation - true, show_in_navigation - checked");
                }
            } else {
                if (!Boolean.parseBoolean(findElement(showNavChk).getAttribute("checked"))) {
                    System.out.println("show_in_navigation - false, show_in_navigation - unchecked");
                } else {
                    System.out.println("show_in_navigation - false, show_in_navigation - checked");
                    findElement(showNavChk).click();
                }
            }

            findElement(parentPageSelect).sendKeys(pagesDataObj.get("parent_page").toString());

            pause(3000);

        }  catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
