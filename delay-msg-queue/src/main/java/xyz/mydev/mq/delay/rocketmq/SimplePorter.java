package xyz.mydev.mq.delay.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import xyz.mydev.mq.delay.port.AbstractPorter;
import xyz.mydev.mq.delay.port.DelayMsgQueue;
import xyz.mydev.mq.delay.repository.MqMessageDelay;
import xyz.mydev.mq.delay.rocketmq.producer.CommonMqProducer;

/**
 * 消息搬运工
 * 处理缓存、去重、可靠中转等逻辑
 *
 * @author ZSP
 */
@Slf4j
@Component
public class SimplePorter extends AbstractPorter<MqMessageDelay> implements InitializingBean {

  public SimplePorter(CommonMqProducer commonMqProducer,
                      DelayMsgQueue<MqMessageDelay> delayMsgQueue) {

    super("DelayMsgPorter",
      message -> () -> {
        TransactionSendResult transactionSendResult = commonMqProducer.sendDelayMessage(message, message.getBusinessId());
        // 只要执行过投递，就可以删除了。后续流程跟redis缓存无关。
        log.info("send success, remove msg. msgId [{}] transactionSendResult {}", message.getId(), transactionSendResult);
        boolean remove = delayMsgQueue.remove(message);
        if (!remove) {
          log.warn("remove false , maybe already not exists");
        }
      },
      delayMsgQueue);
    log.info("init Porter!");
  }


  @Override
  public void run() {
    log.info("Porter[{}] start working...", getName());

    try {
      getDelayMsgQueue().start();
    } catch (Throwable ex) {
      log.warn("delayMsgQueue start error, Porter will ignore it and continue working ", ex);
    }

    while (!Thread.currentThread().isInterrupted()) {
      try {
        MqMessageDelay task = getDelayMsgQueue().take();
        log.info("QueueItem [{}] is publishing at [{}] ", task.getId(), task.getTime());
        getExecutor().execute(getRunnableFunction().apply(task));
      } catch (InterruptedException e) {
        log.error("InterruptedException occur, shutdown the thread: {}", Thread.currentThread().getName(), e);
        Thread.currentThread().interrupt();
        break;
      } catch (Exception e) {
        log.error("exception occur, bug ignore it: ", e);

      }
    }
  }


  @Override
  public void afterPropertiesSet() throws Exception {
    initExecutor();
    Runtime.getRuntime().addShutdownHook((new Thread(SimplePorter.this::cancel)));
  }


}