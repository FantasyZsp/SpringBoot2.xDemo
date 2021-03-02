package xyz.mydev.transaction.service;

import jdk.internal.reflect.Reflection;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.mydev.common.utils.ThreadUtils;
import xyz.mydev.transaction.RootTest;

/**
 * @author ZSP
 */
public class TxPhaseDemoTest extends RootTest {


  @Autowired
  private TxPhaseDemo txPhaseDemo;

  @Test
  public void save() {
    txPhaseDemo.save(1);

    ThreadUtils.sleepSeconds(2);
  }

  @Test
  public void saveWithGenericEvent() {
    txPhaseDemo.saveWithGenericEvent(1);
    ThreadUtils.sleepSeconds(2);
  }


  /**
   * java.lang.IllegalAccessError: class xyz.mydev.transaction.service.TxPhaseDemoTest (in unnamed module @0x2f661f7b) cannot access class jdk.internal.reflect.Reflection (in module java.base) because module java.base does not export jdk.internal.reflect to unnamed module @0x2f661f7b
   */
  @Test
  public void test() {
    System.out.println(Reflection.getCallerClass());
  }
}