package pageobjects.api.AdminWeb;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * Created by philipsushkov on 2017-08-02.
 */

public class ResponseDataObj {
    private JSONObject jsonResponse;
    private HttpResponse httpResponse;
    private long responseTime;
    private int responseCode;

    public void setJsonResponse(HttpResponse httpResponse) throws IOException, ParseException {
        JSONObject jsonResponseHar = null;

        HttpEntity httpEntity = httpResponse.getEntity();
        if (httpResponse.getEntity() != null) {
            jsonResponseHar = (JSONObject) new JSONParser().parse(EntityUtils.toString(httpEntity));
        }

        this.jsonResponse = jsonResponseHar;
    }

    public JSONObject getJsonResponse() {
        return jsonResponse;
    }

    public void setResponseTime(HttpClient httpClient, HttpGet httpGet) throws IOException {
        long start = System.currentTimeMillis();
        setHttpResponse(httpClient, httpGet);
        long end = System.currentTimeMillis();
        this.responseTime = end-start;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseCode(HttpResponse httpResponse) {
        this.responseCode = httpResponse.getStatusLine().getStatusCode();
    }

    public long getResponseCode() {
        return responseCode;
    }

    public void setHttpResponse(HttpClient httpClient, HttpGet httpGet) throws IOException {
        this.httpResponse = httpClient.execute(httpGet);
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }

}
