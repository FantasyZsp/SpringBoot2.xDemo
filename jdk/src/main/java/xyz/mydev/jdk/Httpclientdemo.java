package xyz.mydev.jdk;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.time.Duration;

public class Httpclientdemo {
  public static void main(String[] args) throws NoSuchAlgorithmException, Exception, InterruptedException {
    TrustManager[] trustAllCertificates = new TrustManager[]{new X509TrustManager() {
      @Override
      public X509Certificate[] getAcceptedIssuers() {
        return null;
      }

      @Override
      public void checkClientTrusted(X509Certificate[] arg0, String arg1) {
      }

      @Override
      public void checkServerTrusted(X509Certificate[] arg0, String arg1) {
      }

      HostnameVerifier trustAllHostnames = (hostname, session) -> true;
    }};

    int timeoutInSeconds = 2;
    SSLParameters sslParams = new SSLParameters();
    sslParams.setEndpointIdentificationAlgorithm("");
//    SSLContext sc = SSLContext.getInstance("SSL");
    System.setProperty("jdk.internal.httpclient.disableHostnameVerification", "true");
//    sc.init(null, trustAllCertificates, new SecureRandom());
    HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofMillis(timeoutInSeconds * 1000))
//      .sslContext(sc)
      .sslParameters(sslParams)
      .build();
    HttpRequest requestBuilder = HttpRequest.newBuilder().uri(URI.create("https://www.baidu.com"))
      .GET().build();
    HttpResponse<String> result = httpClient.send(requestBuilder, HttpResponse.BodyHandlers.ofString()); //发送请求
    System.out.println(result);
  }

}