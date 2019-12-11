package xyz.mydev.jdk;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.Properties;

/**
 * @author ZSP
 */
public class JdkApplication {
  public static void main(String[] args) throws Exception {
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

    System.out.println(System.getenv("JAVA_HOME"));
//    System.setProperty("javax.net.ssl.trustStore","C:\\Program Files\\Java\\jdk-11.0.2\\lib\\security\\cacerts");

//    SSLContext sc = SSLContext.getInstance("SSL");
//    sc.init(null, trustAllCertificates, new SecureRandom());
//
//    final Properties props = System.getProperties();
//    props.setProperty("jdk.internal.httpclient.disableHostnameVerification", Boolean.TRUE.toString());

//    System.setProperty("javax.net.ssl.trustStore", "C:/Program Files/Java/jdk-11.0.2/lib/security/jssecacerts");
//    System.setProperty("jdk.tls.server.protocols", "TLSv1.3");
    HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofMillis(500000))
//      .sslContext(sc)
      .build();
    HttpRequest requestBuilder = HttpRequest.newBuilder().uri(URI.create("https://www.baidu.com/"))
      .GET().build();
    HttpResponse<String> result = httpClient.send(requestBuilder, HttpResponse.BodyHandlers.ofString());
    System.out.println(result);
  }
}



