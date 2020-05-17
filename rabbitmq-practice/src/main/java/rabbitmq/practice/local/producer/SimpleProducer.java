package rabbitmq.practice.local.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SimpleProducer {

  private static final String DEFAULT_EXCHANGE_NAME = "";
  private static final String DEFAULT_ROUTING_KEY = "test001";
  private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(1);

  private String exchangeName;
  private String routingKey;
  private Runnable runnable;

  public SimpleProducer() {
    this(DEFAULT_ROUTING_KEY);
  }

  public SimpleProducer(String routingKey) {
    this(DEFAULT_EXCHANGE_NAME, routingKey);
  }

  public SimpleProducer(String exchangeName, String routingKey) {
    this(exchangeName, routingKey, defaultRunnable);
  }

  public SimpleProducer(String exchangeName, String routingKey, Runnable runnable) {
    this.routingKey = routingKey;
    this.exchangeName = exchangeName;
    this.runnable = runnable;
  }


  public void start() {
    EXECUTOR_SERVICE.submit(runnable);

    try (Channel channel = MyConnectionFactory.getProducerConnection().createChannel()) {
      for (; ; ) {
        long count = Counter.getAndIncrement();
        String msg = "Start: Hello RabbitMQ-" + count;
        channel.basicPublish(exchangeName, routingKey, null, msg.getBytes(StandardCharsets.UTF_8));
        ThreadUtils.join(ThreadLocalRandom.current().nextInt(1, 3));
        if (count % 20000 == 0) {
          log.info("Start发送条数: {}", count);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }


  private static Runnable defaultRunnable = () -> {
    Connection connection2;
    Channel channel2;
    try {
      connection2 = MyConnectionFactory.getProducerConnection();
      channel2 = connection2.createChannel();
      for (; ; ) {
        long count = Counter.getAndIncrement();
        String msg = "RUNNABLE: Hello RabbitMQ-" + count;
        channel2.basicPublish(DEFAULT_EXCHANGE_NAME, DEFAULT_ROUTING_KEY, null, msg.getBytes(StandardCharsets.UTF_8));
        ThreadUtils.join(ThreadLocalRandom.current().nextInt(1, 3));

        if (count % 10000 == 0) {
          log.info("发送条数: {}", count);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  };


  public static void main(String[] args) throws Exception {
    new SimpleProducer().start();
  }
}
