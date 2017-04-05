package com.acker.android;

import android.content.Context;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

/**
 * Created by acker on 2017/3/31.
 */

public class HttpsUtil {

    /**
     * HttpURLConnection实现，受信任的CA
     */
    public static void httpsUrl1() {
        HttpURLConnection urlConnection = null;
        try {
            long time = System.currentTimeMillis();
            URL url = new URL("https://www.baidu.com");
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            time = System.currentTimeMillis() - time;
            Log.i("https", "耗时：" + time + " ms");
            Log.i("https", transfer(in));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    /**
     * HttpClient实现，受信任的CA
     */
    public static void httpsUrl2() {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("https://www.baidu.com/");
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = httpResponse.getEntity();
                String response = EntityUtils.toString(entity, "utf-8");
                Log.i("https", response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * HttpURLConnection实现，未知的CA
     *
     * @param context
     */
    public static void httpsUrl3(Context context) {
        InputStream caInput = null;
        Certificate ca = null;
        try {
            // Load CAs from an InputStream
            // (could be from a resource or ByteArrayInputStream or ...)
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            caInput = context.getAssets().open("srca.cer");
            ca = cf.generateCertificate(caInput);
            Log.i("https", "ca=" + ((X509Certificate) ca).getSubjectDN());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                caInput.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            // Create an SSLContext that uses our TrustManager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);

            // Tell the URLConnection to use a SocketFactory from our SSLContext
            URL url = new URL("https://kyfw.12306.cn/otn/");
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setSSLSocketFactory(sslContext.getSocketFactory());
            InputStream in = urlConnection.getInputStream();
            Log.i("https", transfer(in));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * HttpClient实现，未知的CA
     *
     * @param context
     */
    public static void httpsUrl4(Context context) {
        InputStream caInput = null;
        Certificate ca = null;
        try {
            // Load CAs from an InputStream
            // (could be from a resource or ByteArrayInputStream or ...)
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            caInput = context.getAssets().open("srca.cer");
            ca = cf.generateCertificate(caInput);
            Log.i("https", "ca=" + ((X509Certificate) ca).getSubjectDN());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                caInput.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);
            SSLSocketFactory ssl = new SSLSocketFactory(keyStore);
            ssl.setHostnameVerifier(ssl.getHostnameVerifier());
            Scheme scheme = new Scheme("https", ssl, 443);
            HttpClient httpclient = new DefaultHttpClient();
            httpclient.getConnectionManager().getSchemeRegistry().register(scheme);
            HttpGet httpGet = new HttpGet("https://kyfw.12306.cn/otn/");
            HttpResponse httpResponse = httpclient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = httpResponse.getEntity();
                String response = EntityUtils.toString(entity, "utf-8");
                Log.i("https", response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String transfer(InputStream inputStream) {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        try {
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}