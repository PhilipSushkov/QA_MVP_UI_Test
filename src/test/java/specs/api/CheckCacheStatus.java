package specs.api;


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
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;


public class CheckCacheStatus {
    //private final String sUrl = "http://www.thirdpointre.com/home/default.aspx";
    private final String sUrl = "http://investor.pgecorp.com/";
    private static final long DEFAULT_PAUSE = 1000;
    long startTime;

    @BeforeTest
    public void setUp() {
        startTime = System.currentTimeMillis();
    }

    @Test(threadPoolSize = 1, invocationCount = 40, timeOut = 3000)
    public void checkCacheStatus() throws InterruptedException {
        Long id = Thread.currentThread().getId();
        //System.out.println("Test method executing on thread with id: " + id);

        HttpGet httpGet = new HttpGet(sUrl);

        //httpGet.setHeader(HttpHeaders.HOST, "www.thirdpointre.com");
        httpGet.setHeader(HttpHeaders.CONTENT_TYPE, "text/html; charset=utf-8");
        httpGet.setHeader(HttpHeaders.CONNECTION, "keep-alive");
        httpGet.setHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:45.0) Gecko/20100101 Firefox/45.0");
        httpGet.setHeader(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpGet.setHeader(HttpHeaders.CACHE_CONTROL, "max-age=0");

        BasicCookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookieSessionId = new BasicClientCookie("ASP.NET_SessionId", "0puw3swnsinu3jojxdo55b25"+id);
        cookieSessionId.setDomain("investor.pgecorp.com");
        cookieSessionId.setPath("/");
        cookieSessionId.setAttribute("HTTP", "true");
        cookieStore.addCookie(cookieSessionId);

        //client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
        HttpClient httpClient = HttpClientBuilder.create().build();

        HttpContext localContext = new BasicHttpContext();
        localContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);

        try {

            long start = System.currentTimeMillis();
            HttpResponse response = httpClient.execute(httpGet);
            long end = System.currentTimeMillis();
            long result = end-start;
            System.out.println("Round trip response time = " + result + " millis");

            int statusCode = response.getStatusLine().getStatusCode();

            System.out.println("Thread #"+id+": "+statusCode);

            Header[] headers = response.getAllHeaders();
            for (Header header : headers) {
                if (header.getName().equals("X-Cache-status")) {
                    System.out.println(header.getName() + ": " + header.getValue());
                }
                if (header.getName().equals("X-DIS-Request-ID")) {
                    System.out.println(header.getName() + ": " + header.getValue());
                }
            }

            long endTime = System.currentTimeMillis();

            System.out.println("Time: " + ((endTime - startTime)/1000) + " sec\n");
            Thread.sleep(DEFAULT_PAUSE);

        } catch(IOException e)
        {
            e.printStackTrace();
        }


    }

    @AfterTest
    public void tearDown() {

    }

}
