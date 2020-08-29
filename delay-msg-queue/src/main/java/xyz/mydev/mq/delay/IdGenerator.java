package xyz.mydev.mq.delay;

import com.sohu.idcenter.IdWorker;
import org.springframework.stereotype.Component;

/**
 * @author ZSP
 */
@Component
public class IdGenerator {

  private IdWorker idWorker;

  public IdGenerator() {
    this.idWorker = new IdWorker(1, 1, 1);
  }

  public String get() {
    return String.valueOf(idWorker.getId());
  }

}
