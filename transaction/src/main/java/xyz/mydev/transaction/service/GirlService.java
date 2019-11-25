package xyz.mydev.transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xyz.mydev.transaction.domain.Girl;
import xyz.mydev.transaction.repository.GirlRepository;

import java.util.List;

/**
 * @author ZSP
 */
@Service
public class GirlService {
  @Autowired
  private GirlRepository girlRepository;

  @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
  public List<Girl> findAll() {
    System.out.println(girlRepository.findAll());
    System.out.println(girlRepository.findAll());
    return girlRepository.findAll();
  }

  @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public Girl save(Girl girl) {
    return girlRepository.save(girl);
  }

  @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public Girl findByIdNonNull(Integer id) {
    return girlRepository.findById(id).orElseThrow();
  }

  @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public Girl update(Girl girl) {
    return girlRepository.save(girl);
  }

  @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public void deleteById(Integer id) {
    girlRepository.deleteById(id);
  }
}
