package xyz.mydev.transaction.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xyz.mydev.transaction.domain.Girl;
import xyz.mydev.transaction.repository.GirlRepository;

/**
 * @author ZSP
 */
@Service
@Slf4j
public class CasService {
  @Autowired
  private GirlRepository girlRepository;
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
  public void compareAndAddAge(Integer id, Integer toAdd) {
    boolean success = false;

    int retryIndex = 0;
    while (retryIndex < 5) {

      Girl girl = girlRepository.selectById(id);
      log.info("读取到的当前年龄: {}", girl.getAge());
      int currentAge = girl.getAge();
      int newAge = currentAge + toAdd;
      int affectCount = jdbcTemplate.update("update girl set age = " + newAge + " where id = " + id + " and age = " + currentAge);
      if (affectCount > 0) {
        success = true;
        break;
      }
      retryIndex++;
    }

    if (!success) {
      throw new RuntimeException("更新失败");
    }

  }
}
