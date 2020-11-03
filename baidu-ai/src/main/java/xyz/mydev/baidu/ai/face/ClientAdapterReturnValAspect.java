package xyz.mydev.baidu.ai.face;

import com.baidu.aip.error.AipError;
import com.google.common.collect.ImmutableSet;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import xyz.mydev.baidu.ai.face.client.bean.CommonResult;
import xyz.mydev.baidu.ai.face.exception.RetryableException;

import java.util.HashSet;
import java.util.Set;

/**
 * 百度三方客户端适配器返回值切面
 * 用于处理可重试的返回值。重试重点依赖于springRetry给出的recover机制
 *
 * @author ZSP
 */
@Aspect
@Component
@Order(Ordered.LOWEST_PRECEDENCE - 1)
@Slf4j
public class ClientAdapterReturnValAspect {

  static final Set<String> RETRY_CODE;

  static {
    Set<String> set = new HashSet<>();
    // SDK108 超时
    set.add(AipError.NET_TIMEOUT_ERROR.getErrorCode());
    // SDK111
    set.add(AipError.ASYNC_TIMEOUT_ERROR.getErrorCode());
    // SDK112
    set.add(AipError.DOWNLOAD_FILE_ERROR.getErrorCode());
    // link https://cloud.baidu.com/doc/FACE/s/8k37c1rqz
    // QPS超额
    set.add("18");
    // 服务端请求失败
    set.add("222201");
    // image_url_download_fail
    set.add("222204");
    // 服务端请求失败
    set.add("222205");
    // 服务端请求失败
    set.add("222206");
    // 服务端请求失败
    set.add("222302");
    // 系统繁忙
    set.add("222901");
    RETRY_CODE = ImmutableSet.copyOf(set);
  }

  @Pointcut("execution(public * IBaiduAiFaceApiClientAdapter.*(..))")
  public void retValPointCut() {
  }


  @AfterReturning(pointcut = "retValPointCut()", returning = "retVal")
  public void after(Object retVal) {
    if (retVal instanceof CommonResult) {

      CommonResult result = (CommonResult) retVal;

      if (RETRY_CODE.contains(result.getErrorCode()) || "14".equals(result.getErrorCode())) {
        log.warn("retryable error code[{}], msg[{}]", result.getErrorCode(), result.getErrorMsg());
        throw new RetryableException(result.getErrorMsg());
      } else {
        log.warn("normal error code[{}], msg[{}]", result.getErrorCode(), result.getErrorMsg());
      }
    }
  }


}
