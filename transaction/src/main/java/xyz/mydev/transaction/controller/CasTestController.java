package xyz.mydev.transaction.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.mydev.transaction.service.CasService;

/**
 * @author ZSP
 */
@Slf4j
@RestController
@RequestMapping("/cas")
public class CasTestController {

  @Autowired
  private CasService casService;


  @PutMapping(value = "/girl/{id}")
  public void addOneAge(@PathVariable(value = "id") Integer id) {

    casService.compareAndAddAge(id, 1);
  }

}
