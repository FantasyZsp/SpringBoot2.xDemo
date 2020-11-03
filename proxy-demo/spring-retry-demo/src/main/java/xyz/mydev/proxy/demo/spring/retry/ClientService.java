package xyz.mydev.proxy.demo.spring.retry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.mydev.proxy.demo.spring.retry.server.AddUserResult;
import xyz.mydev.proxy.demo.spring.retry.server.CommonResult;
import xyz.mydev.proxy.demo.spring.retry.server.RetryBasedExService;

/**
 * 服务调用者
 *
 * @author ZSP
 */
@Component
@Slf4j
public class ClientService {

  @Autowired
  private RetryBasedExService retryBasedExService;

  @Autowired
  private RetryBasedRetValServiceDelegator retryBasedRetValServiceDelegator;


  public String getInfo(String id) {

    String result = retryBasedExService.retryTest(id);
    log.info(result);
    return result;
  }

  public CommonResult getRetVal(String id) {
    CommonResult result = retryBasedRetValServiceDelegator.retryTestBasedRetVal(id);
    log.info("delegator: {}", result);
    return result;
  }

  public AddUserResult addUser(String id) {
    AddUserResult result = retryBasedRetValServiceDelegator.addUser(id);
    log.info("delegator: {}", result);
    return result;
  }
}
