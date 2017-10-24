package pageobjects.api.PressReleaseFilter;

//import com.mashape.unirest.http.JsonNode;
//import com.mashape.unirest.http.Unirest;
//import com.mashape.unirest.http.exceptions.UnirestException;
//import com.mashape.unirest.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;



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

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FiltersAPI {
    public static boolean addFilter () throws Exception {
        /*
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("/Users/victorl/Documents/GitHub/QA-WebCMS-Test/src/test/java/pageobjects/api/PressReleaseFilter/Json/body.json"));
            JSONObject jsonObject = (JSONObject) obj;
            HttpResponse<JsonNode> response = Unirest.post("https://flfa9qo2eb.execute-api.us-east-1.amazonaws.com/dev/v1/filters")
                    .header("x-api-key", "w90bwvYsrg8eEtTbFsPsManZM8aOWLPGKjGhZ0Ue")
                    .header("x-q4org-id", "ea98f2a4d7c94bf294bb929d67c266e5")
                    .header("content-type", "application/json")
                    .body(jsonObject)
                    .asJson();
            System.out.println(response.getBody());

            */


        //JSONParser parser = new JSONParser();
        //Object obj = parser.parse(new FileReader("/Users/victorl/Documents/GitHub/QA-WebCMS-Test/src/test/java/pageobjects/api/PressReleaseFilter/Json/body.json"));
        //JSONObject jsonObject = (JSONObject) obj;

            try {
                HttpClient client = HttpClientBuilder.create().build();
                HttpPost post = new HttpPost("https://flfa9qo2eb.execute-api.us-east-1.amazonaws.com/dev/v1/filters");
                post.setHeader("x-api-key", "w90bwvYsrg8eEtTbFsPsManZM8aOWLPGKjGhZ0Ue");
                post.setHeader("x-q4org-id", "ea98f2a4d7c94bf294bb929d67c266e5");
                post.setHeader("content-type", "application/json");

                //String payload = jsonObject.toString();

                String payload = "data={" +
                        "\"username\": \"admin\", " +
                        "\"first_name\": \"System\", " +
                        "\"last_name\": \"Administrator\"" +
                        "}";

                StringEntity entity = new StringEntity(payload,
                        ContentType.APPLICATION_FORM_URLENCODED);
                post.setEntity(entity);
                HttpResponse response = client.execute(post);

            } catch (IOException ex) {
            }

            return true;
    }

    /*
    public static boolean getAllFilters () throws Exception {
        try {
            HttpResponse<JsonNode> response = Unirest.get("https://flfa9qo2eb.execute-api.us-east-1.amazonaws.com/dev/v1/filters")
                    .header("x-api-key", "w90bwvYsrg8eEtTbFsPsManZM8aOWLPGKjGhZ0Ue")
                    .header("x-q4org-id", "ea98f2a4d7c94bf294bb929d67c266e5")
                    .asJson();
            System.out.println(response.getBody());
            return true;
        }
        catch (UnirestException e)
        {
            e.printStackTrace();
            return false;
        }

    }
    */

}
