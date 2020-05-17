package rabbitmq.practice.local.consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.extern.slf4j.Slf4j;
import rabbitmq.practice.local.commons.MyConnectionFactory;
import xyz.mydev.util.ThreadUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author ZSP
 */
@Slf4j
public class SimpleConsumer {

  private static final String QUEUE_NAME = "test001";

  public static void main(String[] args) throws Exception {
    start();
  }


  public static void start() throws Exception {

    Channel channel = MyConnectionFactory.getConsumerConnection().createChannel();

    // 声明队列
    channel.queueDeclare(QUEUE_NAME, true, false, false, null);

    // 创建一个消费者
    DefaultConsumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String routingKey = envelope.getRoutingKey();
        String contentType = properties.getContentType();
        long deliveryTag = envelope.getDeliveryTag();
        // (process the message components here ...)
        log.info("收到内容:{} ", new String(body, StandardCharsets.UTF_8));
        channel.basicAck(deliveryTag, false);
      }
    };

    channel.basicConsume(QUEUE_NAME, false, consumer);
    while (true) {
      ThreadUtils.join(50);
    }
  }
}
