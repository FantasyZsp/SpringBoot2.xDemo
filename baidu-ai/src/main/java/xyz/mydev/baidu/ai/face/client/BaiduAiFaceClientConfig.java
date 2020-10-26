package xyz.mydev.baidu.ai.face.client;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.mydev.baidu.ai.face.BaiduAiFaceApiClientAdapter;
import xyz.mydev.baidu.ai.face.BaiduAiFaceClientDelegator;
import xyz.mydev.baidu.ai.face.property.BaiduAiFaceApiKeyProperties;
import xyz.mydev.baidu.ai.face.property.BaiduAiFaceQualityControlProperties;

/**
 * @author ZSP
 */
@Configuration
public class BaiduAiFaceClientConfig {
  @Bean
  @ConfigurationProperties("mydev.baidu.ai.face-quality-control")
  @ConditionalOnMissingBean
  public BaiduAiFaceQualityControlProperties baiduAiFaceQualityControlProperties() {
    return new BaiduAiFaceQualityControlProperties();
  }

  @Bean
  @ConfigurationProperties("mydev.baidu.ai.face-api-key")
  @ConditionalOnMissingBean
  public BaiduAiFaceApiKeyProperties baiduAiFaceApiKeyProperties() {
    return new BaiduAiFaceApiKeyProperties();
  }


  @Bean
  public BaiduAiFaceClientDelegator baiduAiFaceClientDelegator(BaiduAiFaceQualityControlProperties baiduAiFaceQualityControlProperties,
                                                               BaiduAiFaceCollectClientFactory faceCollectClientFactory) {
    return new BaiduAiFaceClientDelegator(new BaiduAiFaceApiClientAdapter(faceCollectClientFactory.getClient()), baiduAiFaceQualityControlProperties);
  }

  @Bean
  public BaiduAiFaceCollectClientFactory baiduAiFaceCollectClientFactory(BaiduAiFaceApiKeyProperties baiduAiFaceApiKeyProperties) {
    return new BaiduAiFaceCollectClientFactory(baiduAiFaceApiKeyProperties);
  }
}
