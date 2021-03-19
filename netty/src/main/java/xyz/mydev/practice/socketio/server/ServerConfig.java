package xyz.mydev.practice.socketio.server;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.Transport;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import static xyz.mydev.practice.socketio.MessageHubConstants.ROOM_KEY;

/**
 * @author ZSP  2019/3/26 16:33
 */
@Component
@Slf4j
public class ServerConfig {
  private final ObjectMapper objectMapper;


  public ServerConfig(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Bean
  public SocketIOServer socketServer() {
    Configuration config = new Configuration();
    config.setPort(9092);
    config.setTransports(Transport.POLLING, Transport.WEBSOCKET);
    config.setOrigin(":*:");

    config.setPingTimeout(600_000);
    config.setFirstDataTimeout(600_000);


    // 必须携带 roomKey 参数
    config.setAuthorizationListener(data -> {
      try {
        log.info("认证监听器==>{} 携带参数: {}", data.getAddress(), objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data.getUrlParams()));
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
      boolean success = StringUtils.isNotBlank(data.getSingleUrlParam(ROOM_KEY));
      log.info("认证结果：{}", success);
      return success;
    });

    return new SocketIOServer(config);
  }

  @Bean
  public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
    return new SpringAnnotationScanner(socketServer);

  }
}
