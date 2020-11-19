package xyz.mydev.spring.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Transactional
@Service(value = "transactionalService")
public @interface TransactionalService {

  /**
   * @return 服务 Bean 名称
   */
  @AliasFor(attribute = "value")
  String name() default "";

  /**
   * 覆盖 {@link Transactional#value()} 默认值
   *
   * @return {@link PlatformTransactionManager} Bean 名称
   */
  @AliasFor("name")
  String value() default "";

  /**
   * 建立 {@link Transactional#transactionManager()} 别名
   *
   * @return {@link PlatformTransactionManager} Bean 名称，默认关联 "txManager" Bean
   */
  @AliasFor(attribute = "transactionManager", annotation = Transactional.class)
  String manager() default "txManager";

}





