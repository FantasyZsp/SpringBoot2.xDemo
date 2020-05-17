package xyz.mydev;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import xyz.mydev.util.Counter;
import xyz.mydev.util.ThreadUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author ZSP
 */
@Slf4j
public class RabbitmqAppTest {

  @Test
  public void produce() throws Exception {

    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.setHost("local_vm_rabbitmq");
    connectionFactory.setPort(5672);
    connectionFactory.setVirtualHost("/");
    connectionFactory.setUsername("guest");
    connectionFactory.setUsername("guest");
    Connection connection = connectionFactory.newConnection();
    Channel channel = connection.createChannel();

    new Thread(() -> {

      Connection connectionInThread = null;
      Channel channel2 = null;
      try {
        connectionInThread = connectionFactory.newConnection();
        channel2 = connectionInThread.createChannel();

        for (; ; ) {
          String msg = "Hello RabbitMQ-" + Counter.getAndIncrement();
          channel2.basicPublish("", "test001", null, msg.getBytes(StandardCharsets.UTF_8));
//          ThreadUtils.join(ThreadLocalRandom.current().nextInt(1, 3));
        }

      } catch (Exception e) {
        e.printStackTrace();
      }

    }).start();

    for (; ; ) {
      String msg = "Hello RabbitMQ-" + Counter.getAndIncrement();
      channel.basicPublish("", "test001", null, msg.getBytes(StandardCharsets.UTF_8));
//      ThreadUtils.join(ThreadLocalRandom.current().nextInt(1, 3));
    }


//    Thread.currentThread().join();
//    channel.close();
//    connection.close();
  }

  @Test
  public void consume() throws Exception {
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.setHost("local_vm_rabbitmq");
    connectionFactory.setPort(5672);
    connectionFactory.setVirtualHost("/");
    connectionFactory.setUsername("guest");
    connectionFactory.setUsername("guest");
    Connection connection = connectionFactory.newConnection();
    Channel channel = connection.createChannel();


    // 声明队列
    String queueName = "test001";
    channel.queueDeclare(queueName, true, false, false, null);
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

    channel.basicConsume(queueName, false, consumer);
    while (true) {
      ThreadUtils.join(50);
    }

//    channel.close();
//    connection.close();
  }

}