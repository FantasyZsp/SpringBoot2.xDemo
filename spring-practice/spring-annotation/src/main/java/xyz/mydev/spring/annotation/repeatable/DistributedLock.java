package xyz.mydev.spring.annotation.repeatable;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author ZSP
 */
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(DistributedLocks.class)
public @interface DistributedLock {

  String prefix() default "";

  String key();

  long waitTime() default -1;

  long leaseTime() default -1;

  TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

  Class<? extends RuntimeException> exceptionClass() default RuntimeException.class;

  String exceptionMessage() default "The server is busy!";


}
