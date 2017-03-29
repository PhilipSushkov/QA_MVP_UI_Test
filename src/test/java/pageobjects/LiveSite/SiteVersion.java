package pageobjects.LiveSite;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import util.Functions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by philipsushkov on 2017-03-28.
 */

public class SiteVersion {
    private WebDriver phDriver;
    private String sSite;
    private static final String sSlash = "/";
    private static final String sHttp = "http://";
    private JSONParser parser;

    public SiteVersion(WebDriver phDriver, String sSite) {
        this.phDriver = phDriver;
        this.sSite = sSite;

        parser = new JSONParser();
    }

    public String getSiteVersion(String sPathToFile) throws Exception {
        String sSiteFull = Functions.UrlAddSlash(sSite, sSlash, sHttp);
        phDriver.get(sSiteFull);

        String sVersion = Functions.GetVersion(phDriver);
        saveSiteVersion(sVersion, sPathToFile);
        return sVersion;
    }

    public void saveSiteVersion(String sVersion, String sPathToFile) throws Exception {
        JSONObject jsonObjSite = new JSONObject();
        System.out.println(sPathToFile + sSite + ".json");

        try {
            jsonObjSite = (JSONObject) parser.parse(new FileReader(sPathToFile));
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


}
