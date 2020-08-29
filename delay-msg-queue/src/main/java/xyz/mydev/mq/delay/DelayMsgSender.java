package xyz.mydev.mq.delay;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.mydev.common.utils.JsonUtil;
import xyz.mydev.mq.delay.constant.MqPlatform;
import xyz.mydev.mq.delay.port.AbstractPorter;
import xyz.mydev.mq.delay.repository.MqMessageDelay;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 依赖Porter的中转
 * TODO 考虑如何根据平台转发。如rocketmq、rabbitmq平台消息分发机制
 *
 * @author ZSP
 */
@Service
@Slf4j
public class DelayMsgSender implements InitializingBean {
  private final IdGenerator idGenerator;
  private final SimpleDelayService simpleDelayService;

  private final static int DEFAULT_MQ_PLAT_FORM_4_DELAY_MSG = MqPlatform.RocketMQ.getCode();

  @Getter
  private final AbstractPorter<MqMessageDelay> porter;

  @Autowired
  public DelayMsgSender(IdGenerator idGenerator,
                        SimpleDelayService simpleDelayService,
                        AbstractPorter<MqMessageDelay> porter) {
    this.idGenerator = idGenerator;
    this.simpleDelayService = simpleDelayService;
    this.porter = porter;
  }

  public void sendDelayMessage(String channel, String tag, String businessId, String messageBody, LocalDateTime time) {

    LocalDateTime now = LocalDateTime.now();
    MqMessageDelay mqMessageDelay = new MqMessageDelay()

      .setId(idGenerator.get())
      .setChannel(channel)
      .setTag(tag == null ? "*" : tag)

      // rocketMQ
      .setMqPlatform(DEFAULT_MQ_PLAT_FORM_4_DELAY_MSG)
      // 此时还没有这种数据
      .setMqPlatformMsgId(null)
      .setBusinessId(businessId)

      .setSystemContext(JsonUtil.obj2String(Map.of("key", "value")))
      .setMessage(messageBody)
      .setTime(time)
      .setStatus(0)

      .setRetryTimesWhenFailed(0)
      .setTotalRetryTimes(0)

      .setMark("created")
      .setCreatedAt(now)
      .setUpdatedAt(now);


    if (TimeUnit.DAYS.convert(Duration.between(now, time)) >= 1) {
      log.warn("may be an expire msg: {}", mqMessageDelay);
    }

    if (simpleDelayService.save(mqMessageDelay)) {
      if (TimeCalculator.shouldPutDirect(mqMessageDelay.getTime())) {
        porter.put(mqMessageDelay);
      }
      return;
    }

    log.info("insert delay msg error: {}", mqMessageDelay);
    throw new RuntimeException("insert delay msg error！");
  }


  @Override
  public void afterPropertiesSet() throws Exception {
    startPorter();
  }

  public void startPorter() {
    porter.start();
  }
}
