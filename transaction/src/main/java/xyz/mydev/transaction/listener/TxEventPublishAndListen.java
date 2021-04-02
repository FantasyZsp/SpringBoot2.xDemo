package xyz.mydev.transaction.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import xyz.mydev.common.beans.Person;
import xyz.mydev.transaction.domain.Girl;

import java.time.Instant;

/**
 * @author ZSP
 */
@Service
@Slf4j
@EnableAsync
public class TxEventPublishAndListen {


  private final ApplicationEventPublisher applicationEventPublisher;

  public TxEventPublishAndListen(
    ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }


  @Transactional
  public void save(Integer id) {
    Girl girl = Girl.build(1);
    applicationEventPublisher.publishEvent(new GirlEvent(girl));
    applicationEventPublisher.publishEvent(new GirlTagEvent(girl));
  }

  @Transactional
  public void saveDto(Integer id) {
    GirlDto girlDto = GirlDto.build(1);
    applicationEventPublisher.publishEvent(new GirlEvent(girlDto));
    applicationEventPublisher.publishEvent(new GirlTagEvent(girlDto));
  }


  @Transactional
  public void saveWithGenericEvent_Girl(Integer id) {
    Girl girl = Girl.build(2);
    applicationEventPublisher.publishEvent(new MyGenericEvent<>(girl));

  }

  @Transactional
  public void saveWithGenericEvent_GirlDto(Integer id) {
    GirlDto girlDto = GirlDto.build(2);
    applicationEventPublisher.publishEvent(new MyGenericEvent<>(girlDto));

  }

  @Transactional
  public void saveWithGenericEvent_Person(Integer id) {
    applicationEventPublisher.publishEvent(new MyGenericEvent<>(new Person(1L, "1", "1", Instant.now())));

  }


  /**
   * 非ApplicationEvent的类无效
   */
  @Async
  @TransactionalEventListener
  public void handleGirl(Girl girl) {
    log.info("handleGirl: {}", girl);
  }

  /**
   * 非ApplicationEvent的类无效
   */
  @Async
  @EventListener
  public void handleGirlEventListener(Girl girl) {
    log.info("handleGirlEventListener: {}", girl);
  }

  /**
   * 特定事件类型才能处理：GirlEvent以及继承GirlEvent的类
   */
  @Async
  @TransactionalEventListener
  public void handleGirlEvent(GirlEvent girlEvent) {
    log.info("handleGirlEvent : {}", girlEvent.getClass().getSimpleName());
  }

  @Async
  @TransactionalEventListener
  public void handleGirlTagEvent(GirlTagEvent girlTagEvent) {
    log.info("handleGirlTagEvent : {}", girlTagEvent.getClass().getSimpleName());
  }


  @Async
  @TransactionalEventListener
  public void handlePersonGenericEvent(MyGenericEvent<Person> personEvent) {
    log.info("handlePersonGenericEvent: {}", personEvent.getSource().getClass().getSimpleName());
  }


  /**
   * 只能监听object
   */
  @Async
  @TransactionalEventListener
  public void handleObjectGenericEvent(MyGenericEvent<Object> objectMyGenericEvent) {
    log.info("handleObjectGenericEvent: {}", objectMyGenericEvent.getSource().getClass().getSimpleName());
  }

  /**
   * 无法监听到girl的子类
   */
  @Async
  @TransactionalEventListener
  public void handleGirlGenericEvent(MyGenericEvent<Girl> girlEvent) {
    log.info("handleGirlGenericEvent: {}", girlEvent.getSource().getClass().getSimpleName());
  }


  /**
   * 可以监听girl和girl的子类
   */
  @Async
  @TransactionalEventListener
  public void handleGirlAndExGirlGenericEvent(MyGenericEvent<? extends Girl> girlSubEvent) {
    log.info("handleGirlAndExGirlGenericEvent: {}", girlSubEvent.getSource().getClass().getSimpleName());
  }


  /**
   * 可以监听任何MyGenericEvent事件
   */
  @Async
  @TransactionalEventListener
  public void handleAllGenericEvent(MyGenericEvent<?> allEvent) {
    log.info("handleAllGenericEvent: {}", allEvent.getSource().getClass().getSimpleName());
  }


}


