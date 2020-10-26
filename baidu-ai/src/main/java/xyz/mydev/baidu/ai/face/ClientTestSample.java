package xyz.mydev.baidu.ai.face;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * https://ai.baidu.com/ai-doc/FACE/8k37c1rqz
 *
 * @author zhaosp
 */
@SpringBootApplication
public class ClientTestSample {
  public static void main(String[] args) {
    ConfigurableApplicationContext applicationContext = SpringApplication.run(ClientTestSample.class);
  }
}