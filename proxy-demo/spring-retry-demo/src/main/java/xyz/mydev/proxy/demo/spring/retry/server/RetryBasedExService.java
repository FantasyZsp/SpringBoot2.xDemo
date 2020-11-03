package xyz.mydev.proxy.demo.spring.retry.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

/**
 * 基于异常的重试
 *
 * @author zhaosp
 */
@Service
@Slf4j
public class RetryBasedExService {

  @Retryable(value = {RemoteAccessException.class}, maxAttempts = 3, backoff = @Backoff(delay = 10L, multiplier = 1), recover = "recover", stateful = false)
  public String retryTest(String id) {
    log.info("retryTest invoking for id: {}", id);

    if (true) {
      log.info("ex...");
      throw new RemoteAccessException("RuntimeException....");
    }

    return id + " invoke success";
  }

  @Recover
  public String recover(RuntimeException e, String id) {
    String recoverResult = "recover result for " + id;
    log.error("recover invoking after retry ex: {}", e.getMessage());
    return recoverResult;
  }
}