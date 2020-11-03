package xyz.mydev.baidu.ai.face.client;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import xyz.mydev.baidu.ai.face.BaiduAiFaceApiClientAdapter;
import xyz.mydev.baidu.ai.face.BaiduAiFaceClientDelegator;
import xyz.mydev.baidu.ai.face.property.BaiduAiFaceApiKeyProperties;
import xyz.mydev.baidu.ai.face.property.BaiduAiFaceQualityControlProperties;

/**
 * @author ZSP
 */
@Configuration
@EnableRetry
public class BaiduAiFaceClientConfig {
  @Bean
  @ConfigurationProperties("mydev.baidu.ai.face.face-quality-control")
  @ConditionalOnMissingBean
  public BaiduAiFaceQualityControlProperties baiduAiFaceQualityControlProperties() {
    return new BaiduAiFaceQualityControlProperties();
  }

  @Bean
  @ConfigurationProperties("mydev.baidu.ai.face.face-api-key")
  @ConditionalOnMissingBean
  public BaiduAiFaceApiKeyProperties baiduAiFaceApiKeyProperties() {
    return new BaiduAiFaceApiKeyProperties();
  }


  @Bean
  public BaiduAiFaceApiClientAdapter baiduAiFaceApiClientAdapter(BaiduAiFaceCollectClientFactory faceCollectClientFactory) {
    return new BaiduAiFaceApiClientAdapter(faceCollectClientFactory.getClient());
  }


  @Bean
  public BaiduAiFaceClientDelegator baiduAiFaceClientDelegator(BaiduAiFaceApiClientAdapter baiduAiFaceApiClientAdapter,
                                                               BaiduAiFaceQualityControlProperties baiduAiFaceQualityControlProperties) {
    return new BaiduAiFaceClientDelegator(baiduAiFaceApiClientAdapter, baiduAiFaceQualityControlProperties);
  }

  @Bean
  public BaiduAiFaceCollectClientFactory baiduAiFaceCollectClientFactory(BaiduAiFaceApiKeyProperties baiduAiFaceApiKeyProperties) {
    return new BaiduAiFaceCollectClientFactory(baiduAiFaceApiKeyProperties);
  }
}
