package rabbitmq.practice.local.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import rabbitmq.practice.local.commons.MyConnectionFactory;
import xyz.mydev.util.Counter;
import xyz.mydev.util.ThreadUtils;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author ZSP
 */
public class SimpleProducer {
  private static final String ROUTING_KEY = "test001";
  private static final String QUEUE_NAME = "test001";


  private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(1);

  public static void main(String[] args) throws Exception {
    start();
  }

  public static void start() throws Exception {
    Connection connection = MyConnectionFactory.getProducerConnection();
    Channel channel = connection.createChannel();
    EXECUTOR_SERVICE.submit(RUNNABLE);

    for (; ; ) {
      String msg = "Hello RabbitMQ-" + Counter.getAndIncrement();
      channel.basicPublish("", ROUTING_KEY, null, msg.getBytes(StandardCharsets.UTF_8));
      // ThreadUtils.join(ThreadLocalRandom.current().nextInt(1, 3));
    }
  }

  private static final Runnable RUNNABLE = () -> {
    Connection connectionInThread;
    Channel channel2;
    try {
      connectionInThread = MyConnectionFactory.getProducerConnection();
      channel2 = connectionInThread.createChannel();
      for (; ; ) {
        String msg = "Hello RabbitMQ-" + Counter.getAndIncrement();
        channel2.basicPublish("", ROUTING_KEY, null, msg.getBytes(StandardCharsets.UTF_8));
        ThreadUtils.join(ThreadLocalRandom.current().nextInt(1, 3));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  };
}
