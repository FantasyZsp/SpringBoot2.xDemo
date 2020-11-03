package xyz.mydev.baidu.ai.face.support;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 外援
 * 杨凯
 * <Description:>自定义注解接口限流 <br>
 *
 * @author yangkai
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestLimiter {
  /**
   * 令牌桶名称
   */
  String name() default "";

  /**
   * 并发数
   */
  int concurrentCount() default 1;

  /**
   * 每秒创建令牌个数，默认:10
   */
  int QPS() default 10;

  /**
   * 获取令牌等待超时时间 默认:500
   */
  long timeout() default 500;

  /**
   * 超时时间单位 默认:毫秒
   */
  TimeUnit timeunit() default TimeUnit.MILLISECONDS;

  Class<? extends RuntimeException> exceptionClass() default RuntimeException.class;

  /**
   * 无法获取令牌返回提示信息
   */
  String exceptionMsg() default "亲，服务器快被挤爆了，请稍后再试！";
}
