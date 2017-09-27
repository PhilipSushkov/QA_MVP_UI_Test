package pageobjects.LiveSite;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.server.handler.FindElement;
import util.Functions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by philipsushkov on 2017-03-28.
 */

public class CrawlingSite {
    private WebDriver phDriver;
    private String sSite, sPathToFile;
    private static final String sSlash = "/";
    private static final String sHttp = "http://";
    private static final long DEFAULT_PAUSE = 2500;
    private JSONParser parser;

    public CrawlingSite(WebDriver phDriver, String sSite, String sPathToFile) {
        this.phDriver = phDriver;
        this.sSite = sSite;
        this.sPathToFile = sPathToFile;

        parser = new JSONParser();
    }

    public String getSiteVersion() throws Exception {
        String sVersion = "Not Defined";
        String sSiteFull = Functions.UrlAddSlash(sSite, sSlash, sHttp);

        try {
            phDriver.get(sSiteFull);
            Thread.sleep(DEFAULT_PAUSE);
            sVersion = Functions.GetVersion(phDriver);
        } catch (TimeoutException e) {
            return "TimeoutException";
        } catch (WebDriverException e) {
            saveSiteVersion(sVersion);
            return sVersion;
        }

        System.out.println(sSite + ": " + sVersion);
        saveSiteVersion(sVersion);
        return sVersion;
    }

    public Boolean getSslTrust() throws Exception {
        boolean result = false;

        try {
            phDriver.get("https://www.sslshopper.com/ssl-checker.html#hostname="+sSite);
            Thread.sleep(DEFAULT_PAUSE);
            WebElement element = phDriver.findElement(By.xpath(""));
            result = true;
        } catch (WebDriverException e) {
            e.printStackTrace();
        }

        return result;
    }

    public String getSiteUrl() throws Exception {
        String sUrl = "Not Defined";
        String sSiteFull = Functions.UrlAddSlash(sSite, sSlash, sHttp);

        try {
            phDriver.get(sSiteFull);
            Thread.sleep(DEFAULT_PAUSE);
            sUrl = phDriver.getCurrentUrl();
        } catch (TimeoutException e) {
            return "TimeoutException";
        } catch (WebDriverException e) {
            saveSiteUrl(sUrl);
            return sUrl;
        }

        System.out.println(sSite + ": " + sUrl);
        saveSiteUrl(sUrl);
        return sUrl;
    }


    public String getXCacheStatus(String sSiteFull) throws Exception {
        String sXCacheStatus = "Not Defined";
        //String sSiteFull = Functions.UrlAddSlash(sSite, sSlash, sHttp);

        try {
            HttpGet httpGet = new HttpGet(sSiteFull);

            //httpGet.setHeader(HttpHeaders.HOST, sSite);
            httpGet.setHeader(HttpHeaders.CONTENT_TYPE, "text/html; charset=utf-8");
            httpGet.setHeader(HttpHeaders.CONNECTION, "keep-alive");
            httpGet.setHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:45.0) Gecko/20100101 Firefox/45.0");
            httpGet.setHeader(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            httpGet.setHeader(HttpHeaders.CACHE_CONTROL, "max-age=0");

            HttpClient httpClient = HttpClientBuilder.create().build();

            try {
                HttpResponse response = httpClient.execute(httpGet);
                Header[] headers = response.getAllHeaders();
                for (Header header : headers) {
                    if (header.getName().equals("X-Cache-status")) {
                        sXCacheStatus = header.getValue();
                    }
                }
            } catch(IOException e)
            {
                saveXCacheStatus(sXCacheStatus);
            } catch(Exception e)
            {
                saveXCacheStatus(sXCacheStatus);
            }

        } catch (TimeoutException e) {
            return "TimeoutException";
        } catch (WebDriverException e) {
            saveXCacheStatus(sXCacheStatus);
            return sXCacheStatus;
        }

        System.out.println(sSite + " X-Cache-Status: " + sXCacheStatus);
        saveXCacheStatus(sXCacheStatus);
        return sXCacheStatus;
    }


    public String getSiteVersionAfter() throws Exception {
        String sVersion = "Not Defined";
        String sSiteFull = Functions.UrlAddSlash(sSite, sSlash, sHttp);

        try {
            phDriver.get(sSiteFull);
            Thread.sleep(DEFAULT_PAUSE);
            sVersion = Functions.GetVersion(phDriver);
        } catch (TimeoutException e) {
            return "TimeoutException";
        } catch (WebDriverException e) {
            saveSiteVersionAfter(sVersion);
            return sVersion;
        }

        System.out.println(sSite + ": " + sVersion);
        saveSiteVersionAfter(sVersion);
        return sVersion;
    }

    public String getSiteUrlAfter() throws Exception {
        String sUrl = "Not Defined";
        String sSiteFull = Functions.UrlAddSlash(sSite, sSlash, sHttp);

        try {
            phDriver.get(sSiteFull);
            Thread.sleep(DEFAULT_PAUSE);
            sUrl = phDriver.getCurrentUrl();
        } catch (TimeoutException e) {
            return "TimeoutException";
        } catch (WebDriverException e) {
            saveSiteUrlAfter(sUrl);
            return sUrl;
        }

        System.out.println(sSite + ": " + sUrl);
        saveSiteUrlAfter(sUrl);
        return sUrl;
    }

    public String getXCacheStatusAfter(String sSiteFull) throws Exception {
        String sXCacheStatus = "Not Defined";
        //String sSiteFull = Functions.UrlAddSlash(sSite, sSlash, sHttp);

        try {
            HttpGet httpGet = new HttpGet(sSiteFull);

            //httpGet.setHeader(HttpHeaders.HOST, sSite);
            httpGet.setHeader(HttpHeaders.CONTENT_TYPE, "text/html; charset=utf-8");
            httpGet.setHeader(HttpHeaders.CONNECTION, "keep-alive");
            httpGet.setHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:45.0) Gecko/20100101 Firefox/45.0");
            httpGet.setHeader(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            httpGet.setHeader(HttpHeaders.CACHE_CONTROL, "max-age=0");

            HttpClient httpClient = HttpClientBuilder.create().build();

            try {
                HttpResponse response = httpClient.execute(httpGet);
                Header[] headers = response.getAllHeaders();
                for (Header header : headers) {
                    if (header.getName().equals("X-Cache-status")) {
                        sXCacheStatus = header.getValue();
                    }
                }
            } catch(IOException e)
            {
                saveXCacheStatusAfter(sXCacheStatus);
            } catch(Exception e)
            {
                saveXCacheStatusAfter(sXCacheStatus);
            }

        } catch (TimeoutException e) {
            return "TimeoutException";
        } catch (WebDriverException e) {
            saveXCacheStatusAfter(sXCacheStatus);
            return sXCacheStatus;
        }

        System.out.println(sSite + " X-Cache-Status: " + sXCacheStatus);
        saveXCacheStatusAfter(sXCacheStatus);
        return sXCacheStatus;
    }

    public String getSiteVersionCookie(String sCookie) throws Exception {
        String sVersion = "Not Defined";
        String sSiteFull = Functions.UrlAddSlash(sSite, sSlash, sHttp);

        try {
            phDriver.get(sSiteFull);
            JavascriptExecutor js = (JavascriptExecutor) phDriver;
            js.executeScript("javascript:function createCookie(name,value,days) { var expires = \"\"; if (days) { var date = new Date(); date.setTime(date.getTime() + (days*24*60*60*1000)); expires = \"; expires=\" + date.toUTCString(); } document.cookie = name + \"=\" + value + expires + \"; path=/\"; } var q4VersionCookie = \""+sCookie+"\"; if (q4VersionCookie != null) createCookie(\"Q4VersionCookie\", encodeURIComponent(q4VersionCookie), 1); location.reload();");
            Thread.sleep(DEFAULT_PAUSE);
            phDriver.navigate().refresh();
            sVersion = Functions.GetVersion(phDriver);
        } catch (TimeoutException e) {
            return "TimeoutException";
        } catch (WebDriverException e) {
            return "Not Defined";
        }

        System.out.println(sSite + ": " + sVersion);

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
        jsonObjSite.put("version_before", sVersion);

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

    }

    public void saveXCacheStatus(String sXCacheStatus) throws Exception {
        JSONObject jsonObjSite = new JSONObject();
        //System.out.println(sPathToFile + sSite + ".json");

        try {
            jsonObjSite = (JSONObject) parser.parse(new FileReader(sPathToFile + sSite + ".json"));
        } catch (ParseException e) {
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

        jsonObjSite.put("X_Cache_Status_Before", sXCacheStatus);

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

    }

    public void saveSiteUrl(String sUrl) throws Exception {
        JSONObject jsonObjSite = new JSONObject();
        //System.out.println(sPathToFile + sSite + ".json");

        try {
            jsonObjSite = (JSONObject) parser.parse(new FileReader(sPathToFile + sSite + ".json"));
        } catch (ParseException e) {
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

        jsonObjSite.put("Url_before", sUrl);

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

    }

    public void saveSiteVersionAfter(String sVersion) throws Exception {
        JSONObject jsonObjSite = new JSONObject();
        //System.out.println(sPathToFile + sSite + ".json");

        try {
            jsonObjSite = (JSONObject) parser.parse(new FileReader(sPathToFile + sSite + ".json"));
        } catch (ParseException e) {
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

        jsonObjSite.put("version_after", sVersion);

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

    }

    public void saveSiteUrlAfter(String sUrl) throws Exception {
        JSONObject jsonObjSite = new JSONObject();
        //System.out.println(sPathToFile + sSite + ".json");

        try {
            jsonObjSite = (JSONObject) parser.parse(new FileReader(sPathToFile + sSite + ".json"));
        } catch (ParseException e) {
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

        jsonObjSite.put("Url_after", sUrl);

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

    }

    public void saveXCacheStatusAfter(String sXCacheStatus) throws Exception {
        JSONObject jsonObjSite = new JSONObject();
        //System.out.println(sPathToFile + sSite + ".json");

        try {
            jsonObjSite = (JSONObject) parser.parse(new FileReader(sPathToFile + sSite + ".json"));
        } catch (ParseException e) {
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

        jsonObjSite.put("X_Cache_Status_After", sXCacheStatus);

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

    }

    public boolean getSiteMap() throws Exception {
        final String sPageSitemap = "sitemap.ashx";
        String sURLSitemap = Functions.UrlAddSlash(sSite, sSlash, sHttp) + sPageSitemap;

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
            int i=0;

            List<WebElement> eUrls = phDriver.findElements(By.tagName("loc"));
            for (WebElement eUrl:eUrls) {
                if ((Functions.GetResponseCode(eUrl.getAttribute("textContent")) != 404) && (Functions.GetResponseCode(eUrl.getAttribute("textContent")) != 500)) {
                    jsonListPage.add( eUrl.getAttribute("textContent"));
                    System.out.println(Integer.toString(i++)+" "+eUrl.getAttribute("textContent") + " " + Integer.toString(Functions.GetResponseCode(eUrl.getAttribute("textContent"))));
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


    public boolean mapSiteModule(String sDataModuleJson) throws Exception {

        try {
            JSONObject jsonObjSite = (JSONObject) parser.parse(new FileReader(sPathToFile + sSite + ".json"));
            JSONArray jsonListPage = (JSONArray) jsonObjSite.get("pages");

            JSONObject jsonListModule = new JSONObject();
            JSONArray jsonModuleNames = new JSONArray();
            try {
                jsonObjSite.remove("modules");
            } catch (NullPointerException e) {
            }

            for (int j = 0; j < jsonListPage.size(); j++) {
                String sPageUrl = jsonListPage.get(j).toString();
                //System.out.println(sSite + ": " + sModuleName + ": " + sPageUrl);

                try {
                    phDriver.get(sPageUrl);
                    //Thread.sleep(DEFAULT_PAUSE);

                    JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataModuleJson));
                    JSONArray moduleData = (JSONArray) jsonObject.get("modules");

                    for (int i = 0; i < moduleData.size(); i++) {
                        String sModuleName = ((JSONObject) moduleData.get(i)).get("name").toString();

                        JSONObject pageObj = (JSONObject) moduleData.get(i);
                        JSONObject jsonObjModule = new JSONObject();
                        JSONArray jsonListUrl = new JSONArray();

                        if (Boolean.parseBoolean(pageObj.get("do_assertions").toString())) {

                            By by_xpath = null;
                            try {
                                by_xpath = By.xpath(((JSONObject) moduleData.get(i)).get("xpath").toString());
                                //System.out.println("//div[contains(@class, \""+((JSONObject) moduleData.get(i)).get("class").toString()+"\")]");
                            } catch (NullPointerException e) {
                            }

                            boolean bElement = phDriver.findElements(by_xpath).size() > 0;

                            if (bElement) {
                                System.out.println(Integer.toString(j)+" of "+Integer.toString(jsonListPage.size())+" " + sSite + ": " + sModuleName + ": " + sPageUrl + " - " + bElement);
                                boolean bModuleNameExist = false;
                                try {
                                    JSONObject objTempModules = (JSONObject) jsonObjSite.get("modules");
                                    JSONObject objTempModule = (JSONObject) objTempModules.get(sModuleName);
                                    JSONArray arrTempModuleNames = (JSONArray) objTempModules.get("module_names");

                                    for (Iterator<String> iterator = arrTempModuleNames.iterator(); iterator.hasNext();) {
                                        if (iterator.next().equals(sModuleName) ) {
                                            bModuleNameExist = true;
                                            break;
                                        }
                                    }

                                    jsonListUrl = (JSONArray) objTempModule.get("url");
                                } catch (NullPointerException e) {
                                }

                                jsonListUrl.add(sPageUrl);

                                if (!bModuleNameExist) {
                                    jsonModuleNames.add(sModuleName);
                                }

                                jsonObjModule.put("url", jsonListUrl);
                                jsonObjModule.put("xpath", ((JSONObject) moduleData.get(i)).get("xpath"));
                                jsonListModule.put("module_names", jsonModuleNames);

                                jsonListModule.put(sModuleName, jsonObjModule);
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
                                System.out.println(Integer.toString(j)+" of "+Integer.toString(jsonListPage.size())+" " + sSite + ": " + bElement);
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


    public boolean getSiteModule() throws Exception {
        JSONObject jsonObjSite;
        JSONObject jsonListModule = new JSONObject();
        JSONArray jsonModuleNames = new JSONArray();

        try {
            jsonObjSite = (JSONObject) parser.parse(new FileReader(sPathToFile + sSite + ".json"));
            jsonListModule = (JSONObject) jsonObjSite.get("modules");
            jsonModuleNames = (JSONArray) jsonListModule.get("module_names");
        } catch (ParseException e) {
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

        for (Iterator<String> iterator = jsonModuleNames.iterator(); iterator.hasNext();) {
            String sModuleName = iterator.next();
            System.out.println(sSite+": "+sModuleName);

            JSONObject jsonObjModule = (JSONObject) jsonListModule.get(sModuleName);
            JSONArray jsonListUrl = (JSONArray) jsonObjModule.get("url");
            for (int i=0; i<jsonListUrl.size(); i++) {
                System.out.println(jsonListUrl.get(i));
            }
        }


        return true;
    }
}
