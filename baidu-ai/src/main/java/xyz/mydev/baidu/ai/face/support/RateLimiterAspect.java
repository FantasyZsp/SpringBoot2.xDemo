package xyz.mydev.baidu.ai.face.support;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Constructor;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.common.util.concurrent.RateLimiter.create;


/**
 * 外援
 * 杨凯
 *
 * @author yangkai
 */
@Slf4j
@Aspect
@Order
public class RateLimiterAspect {

  private final ConcurrentHashMap<String, RateLimiter> rateLimiterMap = new ConcurrentHashMap<>();

  @Pointcut("@within(RequestLimiter) || @annotation(RequestLimiter)")
  public void pointCut() {
  }

  @Before("pointCut() && @annotation(requestLimiter)")
  public void doBefore(JoinPoint joinPoint, RequestLimiter requestLimiter) throws Exception {
    Class<?> exceptionClass = requestLimiter.exceptionClass();
    RateLimiter orgetRate = this.createOrgetRate(requestLimiter);
    // 获取令牌
    boolean acquire = orgetRate.tryAcquire(requestLimiter.concurrentCount(), requestLimiter.timeout(), requestLimiter.timeunit());
    if (acquire) {
      //获取令牌成功
      log.info("{}获取令牌成功, 共获取令牌数:{}", requestLimiter.name(), requestLimiter.concurrentCount());
    } else {
      Constructor<?> constructor = exceptionClass.getConstructor(String.class);
      throw (RuntimeException) constructor.newInstance(requestLimiter.exceptionMsg());
    }

  }

  public synchronized RateLimiter createOrgetRate(RequestLimiter requestLimiter) {
    RateLimiter rate = rateLimiterMap.get(requestLimiter.name());
    // 判断map集合中是否有创建好的令牌桶
    if (null == rate) {
      // 创建令牌桶,以n r/s往桶中放入令牌
      rate = create(requestLimiter.QPS());
      rateLimiterMap.put(requestLimiter.name(), rate);
    }
    return rate;
  }

}
