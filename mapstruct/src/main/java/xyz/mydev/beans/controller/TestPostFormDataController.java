package xyz.mydev.beans.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

@RestController()
public class TestPostFormDataController {

  @PostMapping(value = "/test", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public Demo createPersonByMap(Demo httpRequest) {

    System.out.println("xxx");
    return httpRequest;
  }

  @PostMapping(value = "/filterFloorplanWhenCopiedPackage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public FilterResponse filterFloorplanWhenCopiedPackage(Demo httpRequest) {

    System.out.println("xxx");
    return new FilterResponse();
  }

  @GetMapping(value = "/test")
  public Object test() {
    String url = "filterFloorplanWhenCopiedPackage";
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
    //接口参数
    map.add("bucket", "1");
    map.add("packageId", "2");
    //头部类型
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    //构造实体对象
    HttpEntity<MultiValueMap<String, Object>> param = new HttpEntity<>(map, headers);
    //发起请求,服务地址，请求参数，返回消息体的数据类型
    ResponseEntity<String> response = restTemplate.postForEntity(url, param, String.class);
    //body
    String body = response.getBody();
    System.out.println(body);
    //JSON格式转为Map类型
    Map result = null;
    try {
      result = new ObjectMapper().readValue(body, Map.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println(result);

    System.out.println("xxx");
    return result;
  }

  @Data
  public static class Demo {
    private String bucket;
    private String packageId;

  }

  @Data
  public static class FilterResponse {
    public Integer code = 1;
    public Object extData;
    public String message = "111";
    public Boolean success = false;
  }
}
