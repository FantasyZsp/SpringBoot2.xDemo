package xyz.mydev.transaction.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import xyz.mydev.transaction.domain.Girl;

/**
 * @author ZSP
 */
@Service
@Slf4j
@EnableAsync
public class TxPhaseDemo {

  @Autowired
  private GirlService girlService;

  @Autowired
  ApplicationEventPublisher applicationEventPublisher;


  @Transactional
  public void save(Integer id) {
    Girl girl = new Girl();
    girl.setId(id);
    girl.setAge(1);
    girl.setCupSize("D");
    girlService.save(girl);
    log.info("publish info ");
    applicationEventPublisher.publishEvent(new GirlEvent(girl));
  }


  @Async
  @TransactionalEventListener
  public void handleGirlSaveEvent(GirlEvent girlEvent) {
    log.info("receive info : {}", girlEvent.getData());
  }


}

@Getter
@Setter
class GirlEvent extends ApplicationEvent {

  private Object data;

  public GirlEvent(Girl girl) {
    super(girl);
    this.data = girl;
  }
}
