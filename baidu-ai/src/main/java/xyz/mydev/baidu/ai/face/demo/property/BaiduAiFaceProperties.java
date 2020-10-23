package xyz.mydev.baidu.ai.face.demo.property;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ZSP
 */
@Getter
@Setter
public class BaiduAiFaceProperties {
  private ControlProperties defaultControlProperties;
  private ControlProperties addUser;
  private ControlProperties updateUser;
  private ControlProperties searchSingle;
  private ControlProperties searchBatch;
  private ControlProperties userAuth;

  public BaiduAiFaceProperties() {
    this.defaultControlProperties = new ControlProperties();
    this.addUser = ControlProperties.buildNormal();
    this.updateUser = ControlProperties.buildNormal();
    this.searchSingle = ControlProperties.buildNormal();
    this.searchBatch = ControlProperties.buildLow(80);
    this.userAuth = ControlProperties.buildNormal();
  }
}
