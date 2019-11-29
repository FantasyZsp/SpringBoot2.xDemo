package xyz.mydev.beans.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author ZSP
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    if (bean.getClass().equals(ExampleBean.class)) {
      System.out.println("MyBeanPostProcessor postProcessBeforeInitialization");
    }
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if (bean.getClass().equals(ExampleBean.class)) {
      System.out.println("MyBeanPostProcessor postProcessAfterInitialization");
    }
    return bean;
  }
}
