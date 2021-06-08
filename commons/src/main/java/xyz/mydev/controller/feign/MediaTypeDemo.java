package xyz.mydev.controller.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xyz.mydev.common.beans.Person;

/**
 * @author ZSP
 */
@FeignClient(name = "localhost:8080", url = "http://127.0.0.1:8080")
public interface MediaTypeDemo {

  @PostMapping(consumes = {"application/x-www-form-urlencoded;charset=UTF-8"}, value = "/test")
  public MultiValueMap<String, ?> create4(@RequestBody MultiValueMap<String, ?> map);

  @PostMapping(value = "/media-type/map", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE,
    MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
  public Person create2(@ModelAttribute MultiValueMap<String, ?> map);
}
