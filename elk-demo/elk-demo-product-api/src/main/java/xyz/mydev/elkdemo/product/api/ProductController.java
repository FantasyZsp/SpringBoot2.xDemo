package xyz.mydev.elkdemo.product.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author ZSP
 */
@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {

  RestTemplate restTemplate = new RestTemplate();


  @GetMapping("{productId}")
  public String getProduct(@PathVariable String productId) {
    log.info("getProduct by productId id: {}", productId);
    return "getProduct";
  }

}
