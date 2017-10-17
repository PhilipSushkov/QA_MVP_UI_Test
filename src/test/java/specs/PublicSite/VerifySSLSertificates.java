package specs.PublicSite;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

import javax.net.ssl.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


public class VerifySSLSertificates {

    public static void main(String[] args) throws Exception {
        URL url = new URL("https://investor.fb.com");
        SSLContext sslCtx = SSLContext.getInstance("TLS");

        sslCtx.init(null, new TrustManager[]{new X509TrustManager() {

            private X509Certificate[] accepted;

            @Override
            public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                accepted = xcs;
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return accepted;
            }
        }}, null);

        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

        connection.setHostnameVerifier(new HostnameVerifier() {

            @Override
            public boolean verify(String string, SSLSession ssls) {
                return true;
            }
        });

        connection.setSSLSocketFactory(sslCtx.getSocketFactory());

        if (connection.getResponseCode() == 200) {
            Certificate[] certificates = connection.getServerCertificates();
            for (int i = 0; i < certificates.length; i++) {
                Certificate certificate = certificates[i];
                File file = new File("newcert_" + i + ".crt");
                byte[] buf = certificate.getEncoded();

                FileOutputStream os = new FileOutputStream(file);
                os.write(buf);
                os.close();
            }
        }

        connection.disconnect();

    }

}
