package pageobjects.api.AdminWeb.EuroNews;

import net.lightbody.bmp.BrowserMobProxy;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * Created by philipsushkov on 2017-08-03.
 */

public class RequestDataObj {
    private BrowserMobProxy proxy;
    private HttpClient client;

    public RequestDataObj(BrowserMobProxy proxyPage) {
        this.proxy = proxyPage;
        this.client = HttpClientBuilder.create().build();
    }


}
