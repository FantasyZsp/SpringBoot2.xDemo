package xyz.mydev.elkdemo.order.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author ZSP
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

  RestTemplate restTemplate = new RestTemplate();


  @GetMapping("{orderId}")
  public String getOrder(@PathVariable String orderId) {

    log.info("getOrder by order id: {}", orderId);
    ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:8081/product/111", String.class);
    log.info("response: {}", entity);
    return entity.getBody();
  }

}
