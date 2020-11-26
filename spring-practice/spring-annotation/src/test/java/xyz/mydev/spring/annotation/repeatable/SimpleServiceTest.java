package xyz.mydev.spring.annotation.repeatable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ZSP
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SimpleServiceTest {

  @Autowired
  SimpleService simpleService;

  @Test
  public void testAnnotationRead() {

    simpleService.testLockSingle();

    simpleService.testLockGroup();

    simpleService.testLockMulti();

  }

}