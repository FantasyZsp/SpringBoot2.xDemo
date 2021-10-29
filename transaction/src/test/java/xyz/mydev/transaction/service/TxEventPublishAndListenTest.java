package xyz.mydev.transaction.service;

//import jdk.internal.reflect.Reflection;

import xyz.mydev.redis.lock.annotation.RedissonClientAutoConfig;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import xyz.mydev.common.utils.ThreadUtils;
import xyz.mydev.transaction.RootTest;
import xyz.mydev.transaction.listener.TxEventPublishAndListen;

/**
 * @author ZSP
 */
@EnableAutoConfiguration(exclude = RedissonClientAutoConfig.class)
public class TxEventPublishAndListenTest extends RootTest {


  @Autowired
  private TxEventPublishAndListen txEventPublishAndListen;

  @Test
  public void save() {
    txEventPublishAndListen.save(1);
    ThreadUtils.sleepSeconds(5);
  }

  @Test
  public void saveDto() {
    txEventPublishAndListen.saveDto(1);
    ThreadUtils.sleepSeconds(5);
  }

  @Test
  public void saveWithGenericEvent_Girl() {
    txEventPublishAndListen.saveWithGenericEvent_Girl(1);
    ThreadUtils.sleepSeconds(5);
  }

  @Test
  public void saveWithGenericEvent_GirlDto() {
    txEventPublishAndListen.saveWithGenericEvent_GirlDto(1);
    ThreadUtils.sleepSeconds(5);
  }

  @Test
  public void saveWithGenericEvent_Person() {
    txEventPublishAndListen.saveWithGenericEvent_Person(1);
    ThreadUtils.sleepSeconds(5);
  }


  /**
   * java.lang.IllegalAccessError: class xyz.mydev.transaction.service.TxPhaseDemoTest (in unnamed module @0x2f661f7b) cannot access class jdk.internal.reflect.Reflection (in module java.base) because module java.base does not export jdk.internal.reflect to unnamed module @0x2f661f7b
   */
//  @Test
//  public void test() {
//    System.out.println(Reflection.getCallerClass());
//  }
}