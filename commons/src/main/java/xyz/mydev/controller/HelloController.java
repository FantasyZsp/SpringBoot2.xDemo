package xyz.mydev.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@Controller
//@ResponseBody
//以上两行效果同@RestController
@RestController
@RequestMapping("/hello")
public class HelloController {
  //    @RequestMapping(value={"/say/{id}"},method = RequestMethod.GET)
  @GetMapping(value = "/say/{id}")
  public String say(@PathVariable(value = "id", required = false) Integer id) {
    return "PathVariable:" + id;
  }

  @RequestMapping(value = {"/hi"}, method = RequestMethod.GET)
  public String hi(@RequestParam(value = "id", required = false, defaultValue = "1") Integer id) {
    return "RequestParam:" + id;
  }
}

