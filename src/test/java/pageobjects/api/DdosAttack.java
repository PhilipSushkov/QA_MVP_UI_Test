package pageobjects.api;

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
import util.Functions;

import java.net.URLEncoder;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by philipsushkov on 2017-07-17.
 */

public class DdosAttack {
    private HttpClient client;
    //private String URL = "facebookrelease.q4web.release";
    private String URL = "chicagotest.q4web.com";
    private String PROTOCOL = "http://";

    public void DdosAttackRequest(String sUrl) {

        //To hide warnings logs from execution console.
        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.OFF);

        try {
            //client = HttpClientBuilder.create().build();

            int randNum = Functions.randInt(5, 50);
            String query = "+{[=-&*#~";
            final String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            final int N = alphabet.length();

            for (int i=0; i<randNum; i++) {
                Random r = new Random();
                query = query + alphabet.charAt(r.nextInt(N));
            }

            System.out.println(URLEncoder.encode(query, "UTF-8"));
            String fullURL = PROTOCOL+URL+sUrl+query;
            HttpGet request = new HttpGet(fullURL+"+");

            request.setHeader(HttpHeaders.HOST, URL);
            request.setHeader(HttpHeaders.CONTENT_TYPE, "text/html; charset=utf-8");
            request.setHeader(HttpHeaders.CONNECTION, "keep-alive");
            request.setHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
            request.setHeader(HttpHeaders.REFERER, sUrl);

            BasicCookieStore cookieStore = new BasicCookieStore();
            BasicClientCookie cookieSessionId = new BasicClientCookie("ASP.NET_SessionId", "jummm0w2v5uugnrnpzodaoba");
            cookieSessionId.setDomain("facebookrelease.q4web.release");
            cookieSessionId.setPath("/");
            cookieSessionId.setAttribute("HTTP", "true");
            cookieStore.addCookie(cookieSessionId);

            //client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
            client = HttpClientBuilder.create().build();

            HttpContext localContext = new BasicHttpContext();
            localContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);

            long start = System.currentTimeMillis();
            HttpResponse response = client.execute(request, localContext);
            long end = System.currentTimeMillis();
            System.out.println("Round trip response time = " + (end-start) + " millis");

            int statusCode = response.getStatusLine().getStatusCode();

            System.out.println(fullURL+": "+statusCode);

            Header[] headers = response.getAllHeaders();
            for (Header header : headers) {
                System.out.println("Key : " + header.getName()
                        + " ,Value : " + header.getValue());
            }
            System.out.println("\n Done");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
