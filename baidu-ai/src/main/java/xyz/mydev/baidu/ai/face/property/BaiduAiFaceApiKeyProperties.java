package xyz.mydev.baidu.ai.face.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author ZSP
 */
@Getter
@Setter
@ToString
public class BaiduAiFaceApiKeyProperties {
  private String apiId;
  private String apiKey;
  private String secretKey;
  private ClientConnectConfig clientConnectConfig;


  @Getter
  @Setter
  @ToString
  public static class ClientConnectConfig {
    // 连接超时设置
    private int connectionTimeoutMillis = 2000;
    private int socketTimeoutMillis = 60000;
  }
}
