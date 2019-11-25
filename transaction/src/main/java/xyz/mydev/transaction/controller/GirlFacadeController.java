package xyz.mydev.transaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.mydev.transaction.service.GirlFacadeService;

import java.util.List;

/**
 * @author ZSP
 */
@RestController
@RequestMapping("test")
public class GirlFacadeController {

  @Autowired
  private GirlFacadeService girlFacadeService;

  @GetMapping(value = "/girls")
  public List<?> girlList() {
    return girlFacadeService.findAll();
  }


}
