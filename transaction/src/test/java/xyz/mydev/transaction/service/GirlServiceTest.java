package xyz.mydev.transaction.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import xyz.mydev.transaction.RootTest;
import xyz.mydev.transaction.domain.Girl;

/**
 * @author ZSP
 */
public class GirlServiceTest extends RootTest {


  @Autowired
  private GirlService girlService;

  @Test
  @Transactional(isolation = Isolation.REPEATABLE_READ)
  public void fetchById() {
    Girl girl = girlService.fetchById(1);
    girlService.fetchById2(1);

  }
}
