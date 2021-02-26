package xyz.mydev.transaction.service;

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
}