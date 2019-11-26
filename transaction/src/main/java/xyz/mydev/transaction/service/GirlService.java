package xyz.mydev.transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xyz.mydev.transaction.domain.Girl;
import xyz.mydev.transaction.repository.GirlRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author ZSP
 */
@Service
public class GirlService {
  @Autowired
  private GirlRepository girlRepository;
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @SuppressWarnings("all")
  @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.NESTED, rollbackFor = Exception.class)
  public List<Girl> findAllRcNested() {
    System.out.println(girlRepository.selectList(null).size());
    System.out.println(girlRepository.selectList(null).size());
    new Thread(() -> girlRepository.insert(DataUtil.generate())).start();
    System.out.println(girlRepository.selectList(null).size());
    ;


    return girlRepository.selectList(null);
  }


  @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public Girl findByIdNonNull(Integer id) {
    System.out.println(girlRepository.selectById(id));
    System.out.println(girlRepository.selectById(id));
    System.out.println(girlRepository.selectById(id));
    return girlRepository.selectById(id);
  }


  @Transactional(rollbackFor = Exception.class)
  public void save(Girl girl) {
    girlRepository.insert(girl);
  }


  @Transactional(rollbackFor = Exception.class)
  public Girl update(Girl girl) {
    girlRepository.updateById(girl);
    return girl;
  }

  @Transactional(rollbackFor = Exception.class)
  public void deleteById(Integer id) {
    girlRepository.deleteById(id);
  }


  /**
   * 父线程如果无法获取到子线程的异常，就无法进行回滚了。
   * 子线程成功的一定是无法回滚的。
   */
  @SuppressWarnings("all")
  @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.NESTED, rollbackFor = Exception.class)
  public List<Girl> findAllWhenInsertAsynError() {
    Girl generate = DataUtil.generate();
    generate.setId(1);
    Thread thread = new Thread(() -> {
      jdbcTemplate.execute("insert girl(id, age, cup_size) value (1,1,1)");
    });
    thread.start();

    Thread thread2 = new Thread(() -> {
      jdbcTemplate.execute("insert girl( age, cup_size) value (1,1)");
    });
    thread2.start();
    ;
    List<Girl> girls = girlRepository.selectList(null);
    try {
      thread.join();
      thread2.join();
    } catch (InterruptedException e) {
      throw new RuntimeException("异步线程出错");
    }
    return girls;
  }

  @SuppressWarnings("all")
  @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.NESTED, rollbackFor = Exception.class)
  public List<Girl> operateWhenInsertAsynError2() {
    jdbcTemplate.execute("insert girl( age, cup_size) value (1,1)");
    Thread thread2 = new Thread(() -> {
      jdbcTemplate.execute("insert girl(id, age, cup_size) value (1,1,1)");
    });
    thread2.start();
    ;
    List<Girl> girls = girlRepository.selectList(null);
    try {
      thread2.join();
    } catch (InterruptedException e) {
      throw new RuntimeException("异步线程出错");
    }
    return girls;
  }

  @SuppressWarnings("all")
  @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.NESTED, rollbackFor = Exception.class)
  public List<Girl> operateWhenInsertAsynError3() {
    jdbcTemplate.execute("insert girl( age, cup_size) value (1,1)");

    CompletableFuture<Object> threadCompletableFuture = CompletableFuture.supplyAsync(() -> {
      jdbcTemplate.execute("insert girl(id, age, cup_size) value (1,1,1)");
      return new Object();
    });

    List<Girl> girls = girlRepository.selectList(null);
    try {
      threadCompletableFuture.join();
    } catch (Exception e) {
      throw new RuntimeException("异步线程出错", e);
    }
    return girls;
  }
}
