package xyz.mydev.baidu.ai.face.client.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import xyz.mydev.baidu.ai.face.constant.Constants;
import xyz.mydev.baidu.ai.face.property.ControlProperties;

import javax.validation.constraints.NotBlank;

/**
 * 人脸注册、更新用
 *
 * @author ZSP
 */
@Getter
@Setter
@Builder
public class UserFaceMatchInfo {
  @NotBlank
  private String image;
  @Builder.Default
  @NotBlank
  private String imageType = Constants.ImageType.URL;

  @Builder.Default
  private String faceType = Constants.FaceType.FACE_TYPE_LIVE;
  private ControlProperties controlProperties;
}
