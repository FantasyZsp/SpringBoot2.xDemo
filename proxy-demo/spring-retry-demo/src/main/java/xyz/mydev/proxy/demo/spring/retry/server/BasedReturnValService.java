package xyz.mydev.proxy.demo.spring.retry.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 基于返回值的重试
 *
 * @author zhaosp
 */
@Service
@Slf4j
public class BasedReturnValService {

  public CommonResult retryTest(String id) {
    log.info("base server invoke");

    if (ThreadLocalRandom.current().nextBoolean()) {
      log.info("ex...");
      return CommonResult.builder()
        .errorMsg("业务失败")
        .errorCode("500")
        .build();
    }

    return CommonResult.builder()
      .errorCode("0")
      .errorMsg("success")
      .result(null)
      .build();
  }

  public AddUserResult addUser(String id) {
    log.info("base server invoke");

    if (ThreadLocalRandom.current().nextBoolean()) {
      log.info("addUser ex...");
      return AddUserResult.customBuilder()
        .errorMsg("addUser业务失败")
        .errorCode("500")
        .build();
    }

    return AddUserResult.customBuilder()
      .errorCode("0")
      .errorMsg("success")
      .result(null)
      .build();
  }


}