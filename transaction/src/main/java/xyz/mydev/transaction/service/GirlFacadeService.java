package xyz.mydev.transaction.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import xyz.mydev.transaction.domain.Girl;
import xyz.mydev.transaction.repository.GirlRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZSP
 */
@Service
@Slf4j
@SuppressWarnings("all")
public class GirlFacadeService {
  @Autowired
  private GirlService girlService;
  @Autowired
  private JdbcTemplate jdbcTemplate;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private GirlRepository girlRepository;


  @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public List<?> findAll() {
    String txName = TransactionSynchronizationManager.getCurrentTransactionName();
    log.info("\n  ############## main transaction name is: {}.", txName);

    List<Object> results = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      List<Girl> allRcNested = girlService.findAllRcNested();
      int size = allRcNested.size();
      System.out.println(size);
      results.add(size);
    }
    return results;
  }

  @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public List<?> findAllAsynInsertError() {
    String txName = TransactionSynchronizationManager.getCurrentTransactionName();
    log.info("\n  ############## main transaction name is: {}.", txName);
    List<Object> results = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      List<Girl> allRcNested = girlService.findAllWhenInsertAsynError();
      int size = allRcNested.size();
      System.out.println(size);
      results.add(size);
    }
    return results;
  }

  @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public List<?> operateWhenInsertAsynError2() {
    String txName = TransactionSynchronizationManager.getCurrentTransactionName();
    log.info("\n  ############## main transaction name is: {}.", txName);
    List<Girl> allRcNested = girlService.operateWhenInsertAsynError2();
    return allRcNested;
  }

  @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public List<?> operateWhenInsertAsynError3() {
    String txName = TransactionSynchronizationManager.getCurrentTransactionName();
    log.info("\n  ############## main transaction name is: {}.", txName);
    List<Girl> allRcNested = girlService.operateWhenInsertAsynError3();
    return allRcNested;
  }


  @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public List<?> findAllRR() {
    List<Object> results = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      List<Girl> allRcNested = girlService.findAllRcNested();
      int size = allRcNested.size();
      System.out.println(size);
      results.add(size);
    }
    return results;
  }

  public List<?> findAllNoTx() {

    List<Object> results = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      int size = girlService.findAllRcNested().size();
      System.out.println(size);
      results.add(size);
    }

    return results;
  }

  @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public List<Girl> findById(Integer id) {

    List<Girl> results = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      Girl girl = girlService.findByIdNonNull(id);
      results.add(girl);

      try {
        log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(girl));
      } catch (JsonProcessingException e) {

      }

      Girl girl2 = girlRepository.selectById(id);
      results.add(girl2);

      try {
        log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(girl2));
      } catch (JsonProcessingException e) {

      }
    }

    return results;
  }

}
