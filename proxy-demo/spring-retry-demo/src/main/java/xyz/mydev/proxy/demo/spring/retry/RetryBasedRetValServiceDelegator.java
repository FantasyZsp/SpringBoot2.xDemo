package xyz.mydev.proxy.demo.spring.retry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import xyz.mydev.proxy.demo.spring.retry.server.CommonResult;

/**
 * @author ZSP
 */
@Service
@Slf4j
public class RetryBasedRetValServiceDelegator {
  private final RetryBasedRetValServiceAdapter retryBasedRetValServiceAdapter;

  public RetryBasedRetValServiceDelegator(RetryBasedRetValServiceAdapter retryBasedRetValServiceAdapter) {
    this.retryBasedRetValServiceAdapter = retryBasedRetValServiceAdapter;
  }


  @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 10L, multiplier = 1), recover = "recover", stateful = false)
  public CommonResult retryTestBasedRetVal(String id) {
    return retryBasedRetValServiceAdapter.retryTestBasedRetVal(id);
  }

  @Recover
  public CommonResult recover(Exception e, String id) {
    log.error("RetryBasedReturnValService recover after retry ex: {}", e.getMessage());
    String recoverResult = "RetryBasedReturnValService recover for id: " + id + "....";
    return CommonResult.builder()
      .errorCode("201")
      .errorMsg(recoverResult)
      .result(null)
      .build();
  }

}
