package xyz.mydev.proxy.demo.spring.retry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.mydev.proxy.demo.spring.retry.server.BasedReturnValService;
import xyz.mydev.proxy.demo.spring.retry.server.CommonResult;

/**
 * @author ZSP
 */
@Service
@Slf4j
public class RetryBasedRetValServiceAdapter {
  private final BasedReturnValService basedReturnValService;

  public RetryBasedRetValServiceAdapter(BasedReturnValService basedReturnValService) {
    this.basedReturnValService = basedReturnValService;
  }

  public CommonResult retryTestBasedRetVal(String id) {
    return basedReturnValService.retryTest(id);
  }


}
