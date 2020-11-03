package xyz.mydev.proxy.demo.spring.retry.server;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.mydev.proxy.demo.spring.retry.ClientService;

/**
 * @author ZSP
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class RetryBasedExServiceTest {
  @Autowired
  ClientService clientService;

  @Test
  public void test() {
    String info = clientService.getInfo("id");
    log.info(info);
  }

  @Test
  public void testRetVal() {
    CommonResult info = clientService.getRetVal("id");
    log.info("info: {}", info);
  }


  @Test
  public void testAddUser() {
    AddUserResult info = clientService.addUser("id");
    log.info("info: {}", info);
  }
}