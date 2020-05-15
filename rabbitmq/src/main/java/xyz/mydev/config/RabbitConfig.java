package xyz.mydev.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaosp
 */
@Configuration
public class RabbitConfig {

  /**
   * 支付超时延时交换机
   */
  public static final String DELAY_EXCHANGE_NAME = "delay.exchange";

  /**
   * exchange name
   */
  public static final String DEFAULT_EXCHANGE_NAME = "test.exchange";

  /**
   * 微信支付后，发送支付队列
   */
  public static final String ORDER_PAY_QUEUE_NAME = "order_pay";

  /**
   * 超时订单关闭队列
   */
  public static final String TIMEOUT_TRADE_QUEUE_NAME = "close_trade";

  @Bean
  public Queue orderPayQueue() {
    return new Queue(RabbitConfig.ORDER_PAY_QUEUE_NAME, true);
  }

  @Bean
  public Queue delayPayQueue() {
    return new Queue(RabbitConfig.TIMEOUT_TRADE_QUEUE_NAME, true);
  }


  /**
   * 定义广播模式的延时交换机 无需绑定路由
   */
  @Bean
  FanoutExchange delayExchange() {
    Map<String, Object> args = new HashMap<>(1);
    args.put("x-delayed-type", "direct");
    FanoutExchange topicExchange = new FanoutExchange(RabbitConfig.DELAY_EXCHANGE_NAME, true, false, args);
    topicExchange.setDelayed(true);
    return topicExchange;
  }

  @Bean
  public DirectExchange defaultExchange() {
    return new DirectExchange(RabbitConfig.DEFAULT_EXCHANGE_NAME, true, false);
  }


  /**
   * 绑定延时队列与交换机
   */
  @Bean
  public Binding delayPayBind() {
    return BindingBuilder.bind(delayPayQueue()).to(delayExchange());
  }

  /**
   * 绑定普通消息队列
   */
  @Bean
  public Binding orderPayBind() {
    return BindingBuilder.bind(orderPayQueue()).to(defaultExchange()).with(RabbitConfig.ORDER_PAY_QUEUE_NAME);
  }

  /**
   * 定义消息转换器
   */
  @Bean
  Jackson2JsonMessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  /**
   * 定义消息模板用于发布消息，并且设置其消息转换器
   */
  @Bean
  RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(jsonMessageConverter());
    return rabbitTemplate;
  }

  @Bean
  RabbitAdmin rabbitAdmin(final ConnectionFactory connectionFactory) {
    return new RabbitAdmin(connectionFactory);
  }
}

