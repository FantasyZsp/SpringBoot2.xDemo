package xyz.mydev.mq.delay;

import com.sishu.redis.lock.util.ThreadUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.mydev.common.utils.JsonUtil;
import xyz.mydev.common.utils.PrefixNameThreadFactory;
import xyz.mydev.mq.MqApplication;
import xyz.mydev.mq.delay.constant.MqPlatform;
import xyz.mydev.mq.delay.dto.SimpleDelayTagMessage;
import xyz.mydev.mq.delay.repository.MqMessageDelay;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MqApplication.class)
public class MqProducerImplTest {

  @Autowired
  private MqProducerImpl mqProducer;
  @Autowired
  private DelayMsgSender delayMsgSender;

  @Value("${delay-queue.rocketmq.consumers.delay-msg-consumer.topic:common-delay-message-topic}")
  private String topic;


  @Test
  public void sendDelayMessage() throws InterruptedException {
    final String tag = "custom-tag";

    SimpleDelayTagMessage simpleDelayTagMessage = new SimpleDelayTagMessage("id", tag, LocalDateTime.now().plusSeconds(30));
    String messageBody = JsonUtil.obj2String(simpleDelayTagMessage);

    mqProducer.sendDelayMessage(topic, simpleDelayTagMessage.getTag(), simpleDelayTagMessage.getBusinessId(), messageBody, simpleDelayTagMessage.getTime());
    mqProducer.sendDelayMessage(topic, simpleDelayTagMessage.getTag(), simpleDelayTagMessage.getBusinessId(), messageBody, simpleDelayTagMessage.getTime());
    mqProducer.sendDelayMessage(topic, simpleDelayTagMessage.getTag(), simpleDelayTagMessage.getBusinessId(), messageBody, simpleDelayTagMessage.getTime());

    ThreadUtils.sleepSeconds(10);

    SimpleDelayTagMessage simpleDelayTagMessage2 = new SimpleDelayTagMessage("id2", tag, LocalDateTime.now().plusSeconds(30));
    String messageBody2 = JsonUtil.obj2String(simpleDelayTagMessage2);
    mqProducer.sendDelayMessage(topic, simpleDelayTagMessage2.getTag(), simpleDelayTagMessage2.getBusinessId(), messageBody2, simpleDelayTagMessage2.getTime());

    Thread.currentThread().join(100000000);
  }


  @Test
  public void sendDelayMessageMulti() throws InterruptedException {
    final String tag = "custom-tag";

    Thread thread = new Thread(() -> {
      int i = 0;
      while (i++ < 20) {
        SimpleDelayTagMessage simpleDelayTagMessage = new SimpleDelayTagMessage(RandomStringUtils.random(10, false, true), tag, LocalDateTime.now().plusSeconds(30));
        String messageBody = JsonUtil.obj2String(simpleDelayTagMessage);
        mqProducer.sendDelayMessage(topic, simpleDelayTagMessage.getTag(), simpleDelayTagMessage.getBusinessId(), messageBody, simpleDelayTagMessage.getTime());
        ThreadUtils.sleepSeconds(2);
      }
    }, "T-sendDelayMessage");

    ThreadUtils.startAndJoin(thread);
    ThreadUtils.sleepSeconds(1000);
  }

  @Test
  public void sendDelayMessageSingle() throws InterruptedException {
    final String tag = "custom-tag";

    SimpleDelayTagMessage simpleDelayTagMessage = new SimpleDelayTagMessage("id", tag, LocalDateTime.now());
    String messageBody = JsonUtil.obj2String(simpleDelayTagMessage);

    mqProducer.sendDelayMessage(topic, simpleDelayTagMessage.getTag(), simpleDelayTagMessage.getBusinessId(), messageBody, simpleDelayTagMessage.getTime());

    Thread.currentThread().join(100000000);
  }


  @Test
  public void sendSameMessage() throws InterruptedException {
    final String tag = "custom-tag";

    SimpleDelayTagMessage simpleDelayTagMessage = new SimpleDelayTagMessage("id", tag, LocalDateTime.now().plusSeconds(30));
    String messageBody = JsonUtil.obj2String(simpleDelayTagMessage);


    String systemContext = JsonUtil.obj2String(Map.of("key", "value"));

    LocalDateTime now = LocalDateTime.now();
    MqMessageDelay mqMessageDelay = new MqMessageDelay()

      .setId("sameMsgIdConcurrent4")
      .setChannel(topic)
      .setTag(tag)

      // rocketMQ
      .setMqPlatform(MqPlatform.RocketMQ.getCode())

      .setMqPlatformMsgId(null)
      .setBusinessId("businessId")

      .setMessage(messageBody)
      .setTime(simpleDelayTagMessage.getTime())
      .setSystemContext(systemContext)
      .setStatus(0)

      .setRetryTimesWhenFailed(0)
      .setTotalRetryTimes(0)

      .setMark("testPutSameMsg")
      .setCreatedAt(now)
      .setUpdatedAt(now);


//    delayMsgSender.getPorter().put(mqMessageDelay);
//    delayMsgSender.getPorter().put(mqMessageDelay);
//    delayMsgSender.getPorter().put(mqMessageDelay);
//    delayMsgSender.getPorter().put(mqMessageDelay);

    CountDownLatch countDownLatch = new CountDownLatch(1);
    Runnable runnable = () -> {
      try {
        countDownLatch.await();
        log.info("run");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      delayMsgSender.getPorter().put(mqMessageDelay);
    };

    PrefixNameThreadFactory factory = new PrefixNameThreadFactory("T-sendSameMsg");
    factory.newThread(runnable).start();
    factory.newThread(runnable).start();
    factory.newThread(runnable).start();
    factory.newThread(runnable).start();
    factory.newThread(runnable).start();


    log.info("run");
    countDownLatch.countDown();


    Thread.currentThread().join(100000000);

  }
}
