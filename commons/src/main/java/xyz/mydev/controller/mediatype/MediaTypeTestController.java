package xyz.mydev.controller.mediatype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.mydev.common.beans.Person;
import xyz.mydev.controller.feign.MediaTypeDemo;

import java.time.Instant;

@RestController
public class MediaTypeTestController {

  @Autowired
  MediaTypeDemo mediaTypeDemo;

  @PostMapping(value = "/media-type/uri")
  public Person getJson(@RequestParam("name") String name, @RequestParam("address") String address) {
    return new Person(1L, name, address, Instant.now());
  }

  @PostMapping(value = "/media-type/body")
  public Person create(@RequestBody Person person) {
//    request.getParameterMap().forEach((k, v) -> {
//      System.out.println(k);
//      System.out.println(v);
//    });
    return person;
  }

  @PostMapping(value = "/media-type/map", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE,
    MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
  public Person create2(@ModelAttribute Person map) {
//    request.getParameterMap().forEach((k, v) -> {
//      System.out.println(k);
//      System.out.println(v);
//    });
//    System.out.println(request);
    return map;
  }

  @PostMapping(consumes = {"application/x-www-form-urlencoded;charset=UTF-8"}, value = "/test")
  public MultiValueMap<String, String> create4(@RequestBody MultiValueMap<String, String> map) {
//    request.getParameterMap().forEach((k, v) -> {
//      System.out.println(k);
//      System.out.println(v);
//    });
//    System.out.println(request);
    Person person = mediaTypeDemo.create2(map);
    System.out.println(person.toString());
    return map;
  }
}
