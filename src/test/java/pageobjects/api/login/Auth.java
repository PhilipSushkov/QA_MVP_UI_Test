package pageobjects.api.login;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static specs.AbstractSpec.propAPI;

/**
 * Created by philipsushkov on 2017-03-08.
 */

public class Auth extends util.Functions {
    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;
    private static HttpClient client;

    public Auth() throws IOException {
        parser = new JSONParser();

        //To hide warnings logs from execution console.
        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.OFF);

        sPathToFile = System.getProperty("user.dir") + propAPI.getProperty("dataPath_Auth");
        sDataFileJson = propAPI.getProperty("jsonData_Auth");
        client = HttpClientBuilder.create().build();
    }

    public boolean getAccessToken(String environment) throws IOException {
        String access_token = null;
        JSONObject jsonData = new JSONObject();
        JSONObject jsonEnv = new JSONObject();

        try {
            FileReader readFile = new FileReader(sPathToFile + sDataFileJson);
            jsonData = (JSONObject) parser.parse(readFile);
            jsonEnv = (JSONObject) jsonData.get(environment);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println(jsonEnv.toString());

        String urlAuth = jsonEnv.get("url_auth").toString();

        String urlAuthQuery = urlAuth+"/auth";

        //System.out.println(urlAuthQuery);

        HttpPost post = new HttpPost(urlAuthQuery);
        post.setHeader("User-Agent", jsonEnv.get("user_agent").toString());

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("password", jsonEnv.get("password").toString()));
        urlParameters.add(new BasicNameValuePair("product", jsonEnv.get("product").toString()));
        urlParameters.add(new BasicNameValuePair("user", jsonEnv.get("user").toString()));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));


        HttpResponse response = client.execute(post);

        //System.out.println("Response Code: " + response.getStatusLine().getStatusCode());

        if(response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String responseBody = EntityUtils.toString(entity);
                try {
                    JSONObject jsonResponse = (JSONObject) parser.parse(responseBody);
                    JSONObject token = (JSONObject) jsonResponse.get("token");
                    access_token = (String) token.get("access_token");
                    //System.out.println(access_token);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            jsonEnv.put("access_token", access_token);

            try {
                FileWriter writeFile = new FileWriter(sPathToFile + sDataFileJson);
                writeFile.write(jsonData.toJSONString().replace("\\", ""));
                writeFile.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return access_token != null;
    }

}
