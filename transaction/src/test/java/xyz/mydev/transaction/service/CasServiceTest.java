package xyz.mydev.transaction.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.mydev.transaction.RootTest;

/**
 * @author ZSP
 */
public class CasServiceTest extends RootTest {
  @Autowired
  private CasService casService;

  @Test
  public void compareAndAddAge() {
    Thread threadA = new Thread(() -> casService.compareAndAddAge(1, 1), "threadA");
    Thread threadB = new Thread(() -> casService.compareAndAddAge(1, 1), "threadB");
    threadA.start();
    threadB.start();
    try {
      threadA.join();
      threadB.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("finish..");
  }
}