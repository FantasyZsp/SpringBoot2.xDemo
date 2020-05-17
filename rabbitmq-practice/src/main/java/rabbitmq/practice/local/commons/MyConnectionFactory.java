package rabbitmq.practice.local.commons;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author ZSP
 */
public class MyConnectionFactory {

  private static final ConnectionFactory FACTORY_CONSUMER = new ConnectionFactory();
  private static final ConnectionFactory FACTORY_PRODUCER = new ConnectionFactory();

  static {
    FACTORY_CONSUMER.setHost("local_vm_rabbitmq");
    FACTORY_CONSUMER.setPort(5672);
    FACTORY_CONSUMER.setVirtualHost("/");
    FACTORY_CONSUMER.setUsername("guest");
    FACTORY_CONSUMER.setUsername("guest");

    FACTORY_PRODUCER.setHost("local_vm_rabbitmq");
    FACTORY_PRODUCER.setPort(5672);
    FACTORY_PRODUCER.setVirtualHost("/");
    FACTORY_PRODUCER.setUsername("guest");
    FACTORY_PRODUCER.setUsername("guest");
  }

  public static Connection getConsumerConnection() {
    Connection connection = null;
    try {
      connection = FACTORY_CONSUMER.newConnection();

    } catch (Exception e) {
      e.printStackTrace();
    }
    return connection;
  }

  public static Connection getProducerConnection() {
    Connection connection = null;
    try {
      connection = FACTORY_PRODUCER.newConnection();

    } catch (Exception e) {
      e.printStackTrace();
    }
    return connection;
  }

  public static Connection newConnection() {
    Connection connection = null;
    try {
      connection = newFactory().newConnection();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return connection;
  }

  public static ConnectionFactory newFactory() {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("local_vm_rabbitmq");
    factory.setPort(5672);
    factory.setVirtualHost("/");
    factory.setUsername("guest");
    factory.setUsername("guest");
    return factory;
  }

}
