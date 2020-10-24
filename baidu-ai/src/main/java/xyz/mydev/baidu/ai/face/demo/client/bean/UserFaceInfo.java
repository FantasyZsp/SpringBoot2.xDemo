package xyz.mydev.baidu.ai.face.demo.client.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import xyz.mydev.baidu.ai.face.demo.constant.Constants;
import xyz.mydev.baidu.ai.face.demo.property.ControlProperties;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashMap;

/**
 * 人脸注册、更新用
 *
 * @author ZSP
 */
@Getter
@Setter
@Builder
public class UserFaceInfo {

  public static final String ACTION_TYPE_APPEND = "APPEND";
  public static final String ACTION_TYPE_REPLACE = "REPLACE";


  @NotBlank
  private String groupId;
  @NotBlank
  private String userId;
  @Size(max = 32)
  private String userInfo;
  @NotBlank
  private String image;
  @Builder.Default
  private String imageType = Constants.ImageType.URL;

  /**
   * 操作方式
   * 注册时：
   * APPEND: 当user_id在库中已经存在时，对此user_id重复注册时，新注册的图片默认会追加到该user_id下
   * REPLACE : 当对此user_id重复注册时,则会用新图替换库中该user_id下所有图片
   * 第三方默认使用APPEND
   * 业务默认使用REPLACE
   * <p>
   * 更新时：
   * UPDATE: 会使用新图替换库中该user_id下所有图片, 若user_id不存在则会报错
   * REPLACE : 当user_id不存在时, 则会注册这个user_id的用户
   * 默认使用UPDATE
   */
  private String actionType = ACTION_TYPE_REPLACE;

  private ControlProperties controlProperties;

  /**
   * 调用百度api时采用
   * 一般由前置的参数处理维护
   */
  private HashMap<String, String> options;
}
