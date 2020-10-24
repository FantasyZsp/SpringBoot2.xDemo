package xyz.mydev.baidu.ai.face.demo.client.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import xyz.mydev.baidu.ai.face.demo.constant.Constants;
import xyz.mydev.baidu.ai.face.demo.property.ControlProperties;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;

/**
 * 人脸多个搜索、单个搜索、认证用
 *
 * @author ZSP
 */
@Getter
@Setter
@Builder
public class UserFaceSearchInfo {

  @NotBlank
  private String groupIdList;
  /**
   * 单个搜索场景下，如果有值，即为认证
   */
  private String userId;
  @NotBlank
  private String image;
  @Builder.Default
  private String imageType = Constants.ImageType.URL;

  @Builder.Default
  @Max(10)
  private Integer maxFaceNum = 1;


  private ControlProperties controlProperties;

  /**
   * 调用百度api时采用
   * 一般由前置的参数处理维护
   */
  private HashMap<String, String> options;
}
