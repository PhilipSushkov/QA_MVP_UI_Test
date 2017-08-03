package pageobjects.api.AdminWeb.EuroNews;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.core.har.HarNameValuePair;
import net.lightbody.bmp.core.har.HarRequest;
import net.lightbody.bmp.core.har.HarResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.List;

/**
 * Created by philipsushkov on 2017-08-03.
 */

public class RequestDataObj {
    private BrowserMobProxy proxy;
    private HttpClient httpClient;
    private String method, contentType, urlData;

    public RequestDataObj(BrowserMobProxy sProxy, String sMethod, String sContentType, String sUrlData) {
        this.proxy = sProxy;
        this.method = sMethod;
        this.contentType = sContentType;
        this.urlData = sUrlData;
        this.httpClient = HttpClientBuilder.create().build();
    }

    public HttpGet getHttpGet() {
        HttpGet httpGet = null;

        for (HarEntry harEntry:proxy.getHar().getLog().getEntries()) {

            HarRequest harRequest = harEntry.getRequest();
            HarResponse harResponse = harEntry.getResponse();

            List<HarNameValuePair> harListResponse = harResponse.getHeaders();
            if (harRequest.getUrl().equals(urlData)
                    && harRequest.getMethod().equals(method)
                    && harListResponse.get(0).getValue().contains(contentType)) {

                httpGet = new HttpGet(harRequest.getUrl());

                List<HarNameValuePair> params = harRequest.getHeaders();
                for (HarNameValuePair param:params) {
                    httpGet.setHeader(param.getName(), param.getValue());
                }

            }

        }

        return httpGet;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

}
