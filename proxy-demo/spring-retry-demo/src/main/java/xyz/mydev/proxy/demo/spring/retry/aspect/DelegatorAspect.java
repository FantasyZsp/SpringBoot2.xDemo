package xyz.mydev.proxy.demo.spring.retry.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.stereotype.Component;
import xyz.mydev.proxy.demo.spring.retry.server.CommonResult;

/**
 * @author ZSP
 */
@Aspect
@Component
@Order(Ordered.LOWEST_PRECEDENCE - 1)
@Slf4j
public class DelegatorAspect {

  @Pointcut("execution(public * xyz.mydev.proxy.demo.spring.retry.RetryBasedRetValServiceAdapter.*(..))")
  public void delegatorPointCut() {
  }


  @AfterReturning(pointcut = "delegatorPointCut()", returning = "retVal")
  public void after(Object retVal) {
    if (retVal instanceof CommonResult) {

      CommonResult result = (CommonResult) retVal;
      log.info("返回值切面:{}", result);

      if ("500".equals(result.getErrorCode())) {
        log.info("返回值符合重试： {}", result.getErrorCode());
        throw new RemoteAccessException("返回值符合重试");
      } else {
        log.info("返回值不符合重试: {}", retVal);
      }

    }
  }


}
