package pageobjects.LiveSite;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import util.Functions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by philipsushkov on 2017-03-28.
 */

public class CrawlingSite {
    private WebDriver phDriver;
    private String sSite, sPathToFile;
    private static final String sSlash = "/";
    private static final String sHttp = "http://";
    private static final long DEFAULT_PAUSE = 2000;
    private JSONParser parser;

    public CrawlingSite(WebDriver phDriver, String sSite, String sPathToFile) {
        this.phDriver = phDriver;
        this.sSite = sSite;
        this.sPathToFile = sPathToFile;

        parser = new JSONParser();
    }

    public String getSiteVersion() throws Exception {
        String sSiteFull = Functions.UrlAddSlash(sSite, sSlash, sHttp);
        phDriver.get(sSiteFull);

        String sVersion = Functions.GetVersion(phDriver);
        saveSiteVersion(sVersion);
        return sVersion;
    }

    public void saveSiteVersion(String sVersion) throws Exception {
        JSONObject jsonObjSite = new JSONObject();
        //System.out.println(sPathToFile + sSite + ".json");

        try {
            jsonObjSite = (JSONObject) parser.parse(new FileReader(sPathToFile + sSite + ".json"));
        } catch (ParseException e) {
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

        jsonObjSite.put("domain", sSite);
        jsonObjSite.put("version", sVersion);

        try {
            FileWriter file = new FileWriter(sPathToFile + sSite + ".json");
            file.write(jsonObjSite.toJSONString());
            file.flush();
            file.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean getSiteMap() throws IOException {
        final String sPageSitemap = "sitemap.ashx";
        //HttpClient client = HttpClientBuilder.create().build();
        String sURLSitemap = Functions.UrlAddSlash(sSite, sSlash, sHttp) + sPageSitemap;

        //HttpGet get = new HttpGet(sURLSitemap);
        //HttpResponse response = client.execute(get);

        if (Functions.GetResponseCode(sURLSitemap) == 200) {

            JSONObject jsonObjSite = new JSONObject();
            JSONArray jsonListPage = new JSONArray();
            //System.out.println(sPathToFile + sSite + ".json");

            try {
                jsonObjSite = (JSONObject) parser.parse(new FileReader(sPathToFile + sSite + ".json"));
            } catch (ParseException e) {
            } catch (FileNotFoundException e) {
            }

            phDriver.get(sURLSitemap);
            List<WebElement> eUrls = phDriver.findElements(By.tagName("loc"));
            for (WebElement eUrl:eUrls) {
                if (Functions.GetResponseCode(eUrl.getAttribute("textContent")) != 404) {
                    jsonListPage.add( eUrl.getAttribute("textContent"));
                    //System.out.println(eUrl.getAttribute("textContent") + " " + Integer.toString(Functions.GetResponseCode(eUrl.getAttribute("textContent"))));
                }
            }

            jsonObjSite.put("pages", jsonListPage);

            try {
                FileWriter file = new FileWriter(sPathToFile + sSite + ".json");
                file.write(jsonObjSite.toJSONString().replace("\\", ""));
                file.flush();
                file.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }

        return false;
    }


    public boolean getSiteModule(String sDataModuleJson) throws Exception {
        JSONObject jsonListModule = new JSONObject();
        JSONArray jsonListUrl = new JSONArray();

        try {
            JSONObject jsonObjSite = (JSONObject) parser.parse(new FileReader(sPathToFile + sSite + ".json"));
            JSONArray jsonListPage = (JSONArray) jsonObjSite.get("pages");
            for (int j = 0; j < jsonListPage.size(); j++) {
                String sPageUrl = jsonListPage.get(j).toString();
                //System.out.println(sSite + ": " + sModuleName + ": " + sPageUrl);

                try {
                    phDriver.get(sPageUrl);
                    Thread.sleep(DEFAULT_PAUSE);

                    JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataModuleJson));
                    JSONArray moduleData = (JSONArray) jsonObject.get("modules");

                    for (int i = 0; i < moduleData.size(); i++) {
                        JSONObject pageObj = (JSONObject) moduleData.get(i);
                        JSONObject jsonObjModule = new JSONObject();

                        if (Boolean.parseBoolean(pageObj.get("do_assertions").toString())) {
                            String sModuleName = ((JSONObject) moduleData.get(i)).get("name").toString();
                            //System.out.println(sSite + ": " + sModuleName);

                            By by_xpath = null;
                            try {
                                by_xpath = By.xpath("//div[contains(@class, \""+((JSONObject) moduleData.get(i)).get("class").toString()+"\")]");
                                //System.out.println("//div[contains(@class, \""+((JSONObject) moduleData.get(i)).get("class").toString()+"\")]");
                            } catch (NullPointerException e) {
                            }

                            boolean bElement = phDriver.findElements(by_xpath).size() > 0;

                            if (bElement) {
                                System.out.println(sSite + ": " + sModuleName + ": " + sPageUrl + " - " + bElement);

                                jsonListUrl.add(sPageUrl);

                                jsonObjModule.put("url", jsonListUrl);
                                jsonObjModule.put("class", ((JSONObject) moduleData.get(i)).get("class").toString());

                                jsonListModule.put(sModuleName, jsonObjModule);
                                //jsonObjSite.put(sModuleName, sPageUrl);
                                jsonObjSite.put("modules", jsonListModule);

                                try {
                                    FileWriter file = new FileWriter(sPathToFile + sSite + ".json");
                                    file.write(jsonObjSite.toJSONString().replace("\\", ""));
                                    file.flush();
                                    file.close();

                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                System.out.println(sSite + ": " + bElement);
                            }

                        }

                    }

                } catch (FailingHttpStatusCodeException e) {
                } catch (TimeoutException e) {
                }

            }

        } catch (FileNotFoundException e) {
        } catch (ParseException e) {
        }

        return true;
    }

}
