package xyz.mydev.baidu.ai.face.client.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import xyz.mydev.baidu.ai.face.constant.Constants;
import xyz.mydev.baidu.ai.face.property.ControlProperties;

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


  /**
   * matchThreshold
   * 匹配阈值（设置阈值后，score低于此阈值的用户信息将不会返回） 最大100 最小0 默认80
   * 此阈值设置得越高，检索速度将会越快，推荐使用默认阈值80
   * <p>
   * <p>
   * maxFaceNum
   * 最多处理人脸的数目
   * 默认值为1(仅检测图片中面积最大的那个人脸) 最大值10
   */
  private ControlProperties controlProperties;

  /**
   * 调用百度api时采用
   * 一般由前置的参数处理维护
   */
  private HashMap<String, String> options;


}
