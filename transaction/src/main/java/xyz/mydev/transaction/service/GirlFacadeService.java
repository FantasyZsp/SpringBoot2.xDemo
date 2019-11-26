package xyz.mydev.transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZSP
 */
@Service
@SuppressWarnings("all")
public class GirlFacadeService {
  @Autowired
  private GirlService girlService;


  @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public List<?> findAll() {

    List<Object> results = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      int size = girlService.findAllRcNested().size();
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

}
