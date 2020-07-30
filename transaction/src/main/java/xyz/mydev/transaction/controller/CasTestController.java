package xyz.mydev.transaction.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.mydev.transaction.service.CasService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
  public void addOneAge(@PathVariable(value = "id") @Valid @NotNull Integer id) {

    casService.compareAndAddAge(id, 1);
  }

  @PostMapping(value = "/girl")
  public void addOneAge2(@RequestParam(value = "id") @Valid @NotNull Integer id) {

    System.out.println("xxx");
  }

}
