package xyz.mydev.transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import xyz.mydev.transaction.domain.Girl;
import xyz.mydev.transaction.repository.GirlRepository;

/**
 * @author ZSP
 */
@Service
public class CasService {
  @Autowired
  private GirlRepository girlRepository;
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
  public void compareAndAddAge(Integer id, Integer toAdd, Integer oldAge) {
    int newAge = oldAge + toAdd;
    boolean success = false;

    int retryIndex = 0;
    while (!success && retryIndex < 10) {
      int affectCount = jdbcTemplate.update("update girl set age = " + newAge + " where id = " + id + " and age = " + oldAge);
      if (affectCount < 1) {
        Girl girl = girlRepository.selectById(id);
        Integer currentAge = girl.getAge();
        oldAge = currentAge;
        newAge = currentAge + toAdd;
      } else {
        success = true;
      }
      retryIndex++;
    }

    if (!success) {
      throw new RuntimeException("更新失败");
    }

  }
}
