package xyz.mydev.baidu.ai.face.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author ZSP
 */
@Getter
@Setter
@ToString
public class BaiduAiFaceQualityControlProperties implements InitializingBean {
  private ControlProperties defaultControlProperties;
  private ControlProperties addUser;
  private ControlProperties updateUser;
  private ControlProperties searchSingle;
  private ControlProperties searchBatch;
  private ControlProperties userAuth;
  private ControlProperties match;

  public BaiduAiFaceQualityControlProperties() {
    this.defaultControlProperties = new ControlProperties();
    this.addUser = ControlProperties.buildNormal();
    this.updateUser = ControlProperties.buildNormal();
    this.searchSingle = ControlProperties.buildNormal();
    this.searchBatch = ControlProperties.buildLow(80);
    this.userAuth = ControlProperties.buildNormal();
    this.match = ControlProperties.buildNormal();
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    if (defaultControlProperties == null) {
      this.defaultControlProperties = new ControlProperties();
    }

    if (addUser == null) {
      this.addUser = ControlProperties.buildNormal();
    }
    if (updateUser == null) {
      this.updateUser = ControlProperties.buildNormal();
    }
    if (searchSingle == null) {
      this.searchSingle = ControlProperties.buildNormal();
    }
    if (searchBatch == null) {
      searchBatch = ControlProperties.buildLow(80);
    }
    if (userAuth == null) {
      this.userAuth = ControlProperties.buildNormal();
    }
    if (match == null) {
      this.userAuth = ControlProperties.buildNormal();
    }
  }
}