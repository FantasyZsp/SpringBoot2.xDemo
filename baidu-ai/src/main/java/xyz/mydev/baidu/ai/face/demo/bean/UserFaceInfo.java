package xyz.mydev.baidu.ai.face.demo.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import xyz.mydev.baidu.ai.face.demo.property.ControlProperties;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * 人脸注册、更新用
 *
 * @author ZSP
 */
@Getter
@Setter
@Builder
public class UserFaceInfo {
  @NotEmpty
  private String userId;
  @Size(max = 32)
  private String userInfo;
  @NotEmpty
  private String studentFaceUrl;

  private ControlProperties controlProperties;
}
