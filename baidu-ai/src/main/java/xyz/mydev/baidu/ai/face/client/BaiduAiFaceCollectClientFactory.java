package xyz.mydev.baidu.ai.face.client;

import com.baidu.aip.face.AipFace;
import xyz.mydev.baidu.ai.face.property.BaiduAiFaceApiKeyProperties;

import java.util.Objects;

/**
 * @author ZSP
 */
public class BaiduAiFaceCollectClientFactory {

  final BaiduAiFaceApiKeyProperties baiduAiFaceApiKeyProperties;

  public BaiduAiFaceCollectClientFactory(BaiduAiFaceApiKeyProperties baiduAiFaceApiKeyProperties) {
    this.baiduAiFaceApiKeyProperties = baiduAiFaceApiKeyProperties;
  }

  public AipFace getClient() {
    AipFace client = new AipFace(baiduAiFaceApiKeyProperties.getApiId(), baiduAiFaceApiKeyProperties.getApiKey(), baiduAiFaceApiKeyProperties.getSecretKey());
    // 可选：设置网络连接参数
    client.setConnectionTimeoutInMillis(2000);
    client.setSocketTimeoutInMillis(60000);
    return client;
  }

  public AipFace getClient(String apiId, String apiKey, String secretKey) {
    Objects.requireNonNull(apiId);
    Objects.requireNonNull(apiKey);
    Objects.requireNonNull(secretKey);
    AipFace client = new AipFace(apiId, apiKey, secretKey);
    client.setConnectionTimeoutInMillis(2000);
    client.setSocketTimeoutInMillis(60000);
    return client;
  }
}
