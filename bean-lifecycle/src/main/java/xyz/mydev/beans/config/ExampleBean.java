package xyz.mydev.beans.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDateTime;

/**
 * 执行顺序：
 * 静态代码块、代码块、构建方法、
 * <p>
 * postConstruct、afterPropertiesSet、initMethod、
 * preDestroy、destroy、destroyMethod
 *
 * @author ZSP
 */
public class ExampleBean implements InitializingBean, DisposableBean {

  static {
    System.out.println("静态代码块... " + LocalDateTime.now());
  }

  {
    System.out.println("代码块1... " + LocalDateTime.now());
  }

  public ExampleBean() {
    System.out.println("构造方法... " + LocalDateTime.now());
  }

  {
    System.out.println("代码块2... " + LocalDateTime.now());
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    System.out.println("afterPropertiesSet...");
  }

  @Override
  public void destroy() throws Exception {
    System.out.println("destroy...");
  }

  @PostConstruct
  public void postConstruct() {
    System.out.println("postConstruct...");
  }

  @PreDestroy
  public void preDestroy() {
    System.out.println("preDestroy...");
  }

  public void initMethod() {
    System.out.println("initMethod...");
  }

  public void destroyMethod() {
    System.out.println("destroyMethod...");
  }
}
