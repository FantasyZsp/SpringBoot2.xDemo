package xyz.mydev.spring.annotation.repeatable.support;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import xyz.mydev.common.utils.JsonUtil;
import xyz.mydev.spring.annotation.repeatable.DistributedLock;
import xyz.mydev.spring.annotation.repeatable.DistributedLocks;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 拦截RedisLock注解方法切面
 * <p>
 * 排他可重入
 *
 * @author ZSP
 */
@Aspect
@Component
@Slf4j
public class DistributedLockAspect implements Ordered {

  @Pointcut("@annotation(xyz.mydev.spring.annotation.repeatable.DistributedLock)||@annotation(xyz.mydev.spring.annotation.repeatable.DistributedLocks)")
  public void distributedPointCut() {
  }

  @Around("distributedPointCut()")
  public Object distributedLockAround(ProceedingJoinPoint pjp) throws Throwable {
    Method targetMethod = this.getTargetMethod(pjp);
    DistributedLock annotation = AnnotationUtils.findAnnotation(targetMethod, DistributedLock.class);
    if (annotation != null) {
      Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(annotation);
      System.out.println(JsonUtil.obj2StringPretty(annotationAttributes));
    }


    DistributedLocks annotations = AnnotationUtils.findAnnotation(targetMethod, DistributedLocks.class);
    if (annotations != null) {
      Map<String, Object> annotationAttributesSet = AnnotationUtils.getAnnotationAttributes(annotations);
      System.out.println(JsonUtil.obj2StringPretty(annotationAttributesSet));
    }

    return pjp.proceed();
  }

  /**
   * 获取目标方法
   */
  private Method getTargetMethod(ProceedingJoinPoint pjp) throws NoSuchMethodException {
    Signature signature = pjp.getSignature();
    MethodSignature methodSignature = (MethodSignature) signature;
    Method agentMethod = methodSignature.getMethod();
    return pjp.getTarget().getClass().getMethod(agentMethod.getName(), agentMethod.getParameterTypes());
  }


  @Override
  public int getOrder() {
    return Ordered.HIGHEST_PRECEDENCE + 100;
  }

  @PostConstruct
  public void init() {
    log.info("DistributedLockAspect init...");
  }
}
