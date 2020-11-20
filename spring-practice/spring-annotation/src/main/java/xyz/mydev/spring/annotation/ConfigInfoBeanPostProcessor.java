package xyz.mydev.spring.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author ZSP
 */
@Component
@Slf4j
public class ConfigInfoBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

  @Override
  public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {

    String packageName = beanClass.getPackageName();
    if (packageName.startsWith("com.sishu")
      || packageName.startsWith("xyz.mydev")
      || packageName.startsWith("org.redisson")
      || packageName.startsWith("java")) {
      log.info("初始化 {}", beanName);
    }
    return null;
  }
}
