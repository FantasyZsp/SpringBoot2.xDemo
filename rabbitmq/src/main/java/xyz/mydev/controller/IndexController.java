package xyz.mydev.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.mydev.config.RabbitConfig;
import xyz.mydev.provider.RabbitmqPublish;

/**
 * @author ZSP
 */
@RestController
public class IndexController {

  private final RabbitmqPublish rabbitmqPublish;

  public IndexController(RabbitmqPublish rabbitmqPublish) {
    this.rabbitmqPublish = rabbitmqPublish;
  }

  @GetMapping("/send")
  public String send() {
    // 发送多个延时消息
    rabbitmqPublish.sendTimeoutMsg("hello1", "routingKey1", 40);
    rabbitmqPublish.sendTimeoutMsg("hello2", "routingKey2", 20);
    rabbitmqPublish.sendTimeoutMsg("hello3", "routingKey3", 60);
    rabbitmqPublish.sendTimeoutMsg("hello4", "routingKey4", 1);
    rabbitmqPublish.sendTimeoutMsg("hello5", "routingKey5", 3);

    // 发送普通消息
    rabbitmqPublish.sendMsg(RabbitConfig.ORDER_PAY_QUEUE_NAME, "weixin");
    rabbitmqPublish.sendMsg(RabbitConfig.ORDER_PAY_QUEUE_NAME, "alipay");

    return "success";
  }
}
