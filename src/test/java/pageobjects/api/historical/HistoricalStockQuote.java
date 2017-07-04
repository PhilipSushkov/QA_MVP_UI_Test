package pageobjects.api.historical;

import org.apache.commons.lang.ObjectUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import pageobjects.api.login.Auth;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static specs.AbstractSpec.propAPI;

/**
 * Created by easong on 3/23/17.
 */
public class HistoricalStockQuote {

    private int failurecount;
    private String security_name;
    private String securityId;
    private String rawData;
    private boolean individualstockresult = true;
    private boolean requestSuccess;
    private int securityCounter;

    private String host;
    private String app_ver;
    private String access_token;
    private String connection;
    private String user_agent;
    private boolean result = false;
    private boolean dataexists = true;
    private HttpClient client;
    private final String PROTOCOL = "https://";
    private JSONArray securityArray = new JSONArray();
    ArrayList<String> accurateCompanies = new ArrayList<String>();

    private Q4Dataset historicalQuote;
    private HttpResponse response;
    private JSONObject individualdata;

    private List<String> zeroDataList = new ArrayList<>();
    private List<String> inaccurateDataList = new ArrayList<>();
    private List<String> missingDataList = new ArrayList<>();
    private List<String> miscellaneousErrorList = new ArrayList<>();

    private static Auth auth;

    public HistoricalStockQuote(String environment) throws IOException {

        auth = new Auth();

        // gets access token for the desired environment
        Assert.assertTrue(new Auth().getAccessToken("Staging_Env"), "Access Token didn't receive");
        // setup all environment variables. JSON file locations, Q4 API Permissions

        // creating paths to the JSON files
        String sPathToFileAuth, sDataFileAuthJson;

        JSONParser parser = new JSONParser();
        JSONObject jsonEnvData;
        JSONObject jsonEnv = new JSONObject();

        //To hide warnings logs from execution console.
        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.OFF);

        sPathToFileAuth = System.getProperty("user.dir") + propAPI.getProperty("dataPath_Auth");
        sDataFileAuthJson = propAPI.getProperty("jsonData_Auth");
        client = HttpClientBuilder.create().build();

        try {
            // reading in environment variables
            FileReader readAuthFile = new FileReader(sPathToFileAuth + sDataFileAuthJson);
            jsonEnvData = (JSONObject) parser.parse(readAuthFile);
            jsonEnv = (JSONObject) jsonEnvData.get(environment);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        host = jsonEnv.get("host").toString();
        app_ver = jsonEnv.get("app_ver").toString();
        access_token = jsonEnv.get("access_token").toString();
        user_agent = jsonEnv.get("user_agent").toString();
        connection = jsonEnv.get("connection").toString();
        dataValidation();
    }

    public void dataValidation() {

        try {

            sendStockQuoteRequestToQ4DB();

            // ensuring request was successful - if code == 200 it means success
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();

                String responseBody = EntityUtils.toString(entity);
                org.json.JSONObject jsonResponse = new org.json.JSONObject(responseBody);
                // Storing the data object in historicalData
                // Checking if data exists
                if (jsonResponse.length() != 0) {
                    historicalQuote = collectQ4Data();
                } else {
                    // no data was found in the request, storing error in list
                    miscellaneousErrorList.add("Q4 Returned no historical stock data for TXRH");
                    individualstockresult = false;
                }

            } else {
                // initial request to database was not successful, storing error in list
                miscellaneousErrorList.add("Response code from Q4 Database was not 200, it was " + response.getStatusLine().getStatusCode());
                individualstockresult = false;
            }

        } catch (IOException e) {
        }
    }

    public void sendStockQuoteRequestToQ4DB() {
        // To get historical data for other stocks you have to find that stock's value from the q4 api yourself. Ask someone if you don't know how to.
        // E.G TXRH's value is 56e8468870ebdeb9f3b9b247
        try {
            // API Request format: {{url}}/api/stock/historical?appver={{appver}}&securityID={{securityId}}
            String urlHistQuery = PROTOCOL + host + "/api/stock/56ecad3db067373674058454?" + app_ver;
            // System.out.println("Q4 query = " + urlHistQuery);

            HttpGet get = new HttpGet(urlHistQuery);
            // Setting up authentication headers
            get.setHeader("User-Agent", user_agent);
            get.setHeader("Connection", connection);
            get.setHeader("Authorization", "Bearer " + access_token);
            response = client.execute(get);
        } catch (IOException e) {
        }

    }

    private Q4Dataset collectQ4Data() {

        try {

            // Collecting Q4Data for that date
            String q4DataBaseIndividualRequest = PROTOCOL + host + "/api/stock/56ecad3db067373674058454?" + app_ver;
            // Setting up new requests
            HttpGet getIndividual = new HttpGet((q4DataBaseIndividualRequest));
            // Setting up authentication headers for individual get query
            getIndividual.setHeader("User-Agent", user_agent);
            getIndividual.setHeader("Connection", connection);
            getIndividual.setHeader("Authorization", "Bearer " + access_token);
            HttpResponse individualResponse = client.execute(getIndividual);

            // if statuscode == 200 that basically means it's a success and we create an HttpEntity
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity individualEntity = individualResponse.getEntity();
                String individualResponseBody = EntityUtils.toString(individualEntity);
                // Get a JSONObject from the Q4 database
                org.json.JSONObject individualJSONResponse = new org.json.JSONObject(individualResponseBody);
                String tester = individualResponseBody.toString();
                // Get a JSONArray from the JSONObject above
                org.json.JSONObject historicalData =  individualJSONResponse.getJSONObject("data");
                rawData = historicalData.toString();
                return new Q4Dataset(rawData);
            }
        } catch (IOException e) {
        }
        return null;
    }

    public Q4Dataset getHistoricalQuote(){
        return historicalQuote;
    }

}
