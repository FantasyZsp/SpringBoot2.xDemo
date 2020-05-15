package xyz.mydev.consumer;


import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import xyz.mydev.config.RabbitConfig;

import java.io.IOException;

/**
 * @author zhaosp
 */
@Component
@Slf4j
public class RabbitmqConsumer {

  /**
   * 消费延时消息
   */
  @RabbitListener(queues = RabbitConfig.TIMEOUT_TRADE_QUEUE_NAME)
  public void process(String content, Message message, Channel channel) throws IOException {
    try {
      log.info("延迟队列的内容[{}]", content);
      channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
      log.info("超时信息处理完毕");
    } catch (Exception e) {
      log.error("处理失败:{}", e.getMessage());
      channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
    }
  }

  /**
   * 消费普通消息
   */
  @RabbitListener(queues = RabbitConfig.ORDER_PAY_QUEUE_NAME)
  public void process1(String content, Message message, Channel channel) throws IOException {
    try {
      log.info("普通队列的内容[{}]", content);
      channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    } catch (Exception e) {
      log.error("处理失败:{}", e.getMessage());
      channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
    }
  }
}
