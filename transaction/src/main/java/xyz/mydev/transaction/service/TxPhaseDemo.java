package xyz.mydev.transaction.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;
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
    Girl girl = Girl.build(1);
    girlService.save(girl);
    log.info("publish info ");
    applicationEventPublisher.publishEvent(new GirlEvent(girl));
  }


  @Transactional
  public void saveWithGenericEvent(Integer id) {
    Girl girl = Girl.build(1);
    girlService.save(girl);
    log.info("publish info ");
    applicationEventPublisher.publishEvent(new MyGenericEvent<>(girl));
    applicationEventPublisher.publishEvent(new MyGenericEvent<>(new Object()));
  }


  @Async
  @TransactionalEventListener
  public void handleGirlSaveEvent(GirlEvent girlEvent) {
    log.info("receive info : {}", girlEvent.getData());
  }

  @Async
  @TransactionalEventListener
  public void handleGirlSaveEvent222(Girl girlEvent) {
    log.info("receive Girl info : {}", girlEvent);
  }

  @Async
  @TransactionalEventListener
  public void handleGirlSaveMyGenericEvent(MyGenericEvent<Girl> girlEvent) {
    log.info("handleGirlSaveMyGenericEvent receive Girl info : {}", girlEvent);
  }

  /**
   * 监听到全部的，泛型事件具有继承性
   */
  @Async
  @TransactionalEventListener
  public void handleGirlSaveMyGenericEventObject(MyGenericEvent<Object> girlEvent) {
    log.info("handleGirlSaveMyGenericEventObject receive Girl info : {}", girlEvent);
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

class MyGenericEvent<T> extends ApplicationEvent implements ResolvableTypeProvider {
  public MyGenericEvent(T source) {
    super(source);
  }

  @Override
  public ResolvableType getResolvableType() {
    return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(getSource()));
  }
}

class ThreadTestSleep {

  public static void main(String[] args) {
    Thread thread = new Thread(() -> {


      while (true) {
        long start = System.currentTimeMillis();

        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        try {
          Thread.sleep(1000 * 10);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        long end = System.currentTimeMillis();

        System.out.println("耗时: " + (end - start));


      }


    });
    thread.start();

  }


}
