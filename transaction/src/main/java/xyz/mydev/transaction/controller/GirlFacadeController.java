package xyz.mydev.transaction.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(description = "测试嵌套隔离级别")
public class GirlFacadeController {

  @Autowired
  private GirlFacadeService girlFacadeService;


  @GetMapping(value = "/girls-rcrc")
  @ApiOperation(value = "同一事务不同方法 RC 调用 RC")
  public List<?> findAllRcRc() {
    return girlFacadeService.findAll();
  }

  @GetMapping(value = "/girls-rrrc")
  @ApiOperation(value = "同一事务不同方法 RR 调用 RC")
  public List<?> findAllRrRc() {
    return girlFacadeService.findAllRR();
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
