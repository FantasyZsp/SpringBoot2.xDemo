package xyz.mydev.jdk;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * @author ZSP
 */
public class JdkApplication {
  public static void main(String[] args) throws Exception {

    HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofMillis(1000))
      .build();
    HttpRequest requestBuilder = HttpRequest.newBuilder().uri(URI.create("https://www.baidu.com"))
      .GET().build();
    HttpResponse<String> result = httpClient.send(requestBuilder, HttpResponse.BodyHandlers.ofString());
    System.out.println(result);
  }
}



