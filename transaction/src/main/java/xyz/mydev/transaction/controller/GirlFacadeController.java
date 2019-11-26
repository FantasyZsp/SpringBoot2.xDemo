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
@SuppressWarnings("all")
public class GirlFacadeController {

  @Autowired
  private GirlFacadeService girlFacadeService;


  @GetMapping(value = "/girls")
  public List<?> findAll() {
    return girlFacadeService.findAll();
  }

  @GetMapping(value = "/girls-no-tx")
  public List<?> findAllNoTx() {
    return girlFacadeService.findAllNoTx();
  }

  @GetMapping(value = "/girl/id")
  public List<?> findById(Integer id) {
    return girlFacadeService.findById(id);
  }


}
