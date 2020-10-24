package xyz.mydev.baidu.ai.face.demo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import xyz.mydev.baidu.ai.face.demo.client.bean.AddUserResult;
import xyz.mydev.baidu.ai.face.demo.client.bean.CommonResult;
import xyz.mydev.baidu.ai.face.demo.client.bean.SearchResult;
import xyz.mydev.baidu.ai.face.demo.client.bean.UserFaceInfo;
import xyz.mydev.baidu.ai.face.demo.client.bean.UserFaceSearchInfo;
import xyz.mydev.baidu.ai.face.demo.constant.Constants;
import xyz.mydev.baidu.ai.face.demo.property.BaiduAiFaceQualityControlProperties;
import xyz.mydev.baidu.ai.face.demo.property.ControlProperties;

import java.util.HashMap;
import java.util.Objects;

/**
 * 222204 image_url_download_fail 图片下载失败需要重试
 * 18 Open api qps request limit reached QPS超限额需要重试
 * <p>
 * 主要职责：
 * 1. 参数可靠性校验
 * 2. 个别应该重试的异常 TODO 需要在测试过层中补充，如图片下载失败和QPS超额
 *
 * @author ZSP
 */
public class BaiduAiFaceClientDelegator implements InitializingBean {

  private final BaiduAiFaceApiClientAdapter targetClientAdapter;
  private final BaiduAiFaceQualityControlProperties baiduAiFaceQualityControlProperties;


  public BaiduAiFaceClientDelegator(BaiduAiFaceApiClientAdapter targetClientAdapter,
                                    BaiduAiFaceQualityControlProperties baiduAiFaceQualityControlProperties) {
    this.targetClientAdapter = targetClientAdapter;
    this.baiduAiFaceQualityControlProperties = baiduAiFaceQualityControlProperties;
  }


  /**
   * 注册人脸
   */
  public AddUserResult addUser(UserFaceInfo userFaceInfo) {
    if (userFaceInfo.getControlProperties() == null) {
      userFaceInfo.setControlProperties(baiduAiFaceQualityControlProperties.getAddUser());
    }
    prepareParamForAddUserFace(userFaceInfo);
    return targetClientAdapter.addUser(userFaceInfo);
  }

  /**
   * 更新人脸
   */
  public AddUserResult updateUser(UserFaceInfo userFaceInfo) {
    if (userFaceInfo.getControlProperties() == null) {
      userFaceInfo.setControlProperties(baiduAiFaceQualityControlProperties.getUpdateUser());
    }
    prepareParamForUpdateUserFace(userFaceInfo);
    return targetClientAdapter.updateUser(userFaceInfo);
  }

  /**
   * 删除人脸
   */
  public CommonResult deleteUserFace(String groupId, String userId, String faceToken) {
    Objects.requireNonNull(groupId);
    Objects.requireNonNull(userId);
    Objects.requireNonNull(faceToken);
    return targetClientAdapter.faceDelete(groupId, userId, faceToken);
  }

  /**
   * 多个人脸搜索
   */
  public SearchResult searchBatch(UserFaceSearchInfo userFaceSearchInfo) {
    prepareParamForSearchUserFace(userFaceSearchInfo);
    return targetClientAdapter.searchBatch(userFaceSearchInfo);
  }

  /**
   * 单人脸搜索
   */
  public SearchResult searchSingle(UserFaceSearchInfo userFaceSearchInfo) {
    prepareParamForSearchUserFace(userFaceSearchInfo);
    return targetClientAdapter.searchSingle(userFaceSearchInfo);
  }

  /**
   * 人脸认证
   */
  public SearchResult userAuth(UserFaceSearchInfo userFaceSearchInfo) {
    if (StringUtils.isBlank(Objects.requireNonNull(userFaceSearchInfo).getUserId())) {
      throw new IllegalArgumentException("userAuth requires userId");
    }
    prepareParamForSearchUserFace(userFaceSearchInfo);
    return targetClientAdapter.searchSingle(userFaceSearchInfo);
  }


  private void prepareParamForUpdateUserFace(UserFaceInfo userFaceInfo) {
    prepareParamForAddUserFace(userFaceInfo);
  }

  /**
   * 完善userFaceInfo内部参数状态
   */
  private void prepareParamForAddUserFace(UserFaceInfo userFaceInfo) {
    Objects.requireNonNull(userFaceInfo);

    String userId = userFaceInfo.getUserId();
    String userInfo = userFaceInfo.getUserInfo();
    String image = userFaceInfo.getImage();
    ControlProperties controlProperties = userFaceInfo.getControlProperties();

    if (StringUtils.isBlank(userId)) {
      throw new IllegalArgumentException();
    }
    Objects.requireNonNull(controlProperties);
    String qualityControl = controlProperties.getQualityControl();
    String livenessControl = controlProperties.getLivenessControl();
    Objects.requireNonNull(qualityControl);
    Objects.requireNonNull(livenessControl);
    Objects.requireNonNull(image);

    boolean userInfoExists = StringUtils.isNotBlank(userInfo);
    HashMap<String, String> options = new HashMap<>(userInfoExists ? 4 : 2);

    options.put(Constants.QUALITY_CONTROL_KEY, qualityControl);
    options.put(Constants.LIVENESS_CONTROL_KEY, livenessControl);

    if (userInfoExists) {
      options.put(Constants.USER_INFO_KEY, userInfo);
    }

    if (StringUtils.isNotBlank(userFaceInfo.getActionType())) {
      options.put(Constants.ACTION_TYPE_KEY, userFaceInfo.getActionType());
    }
    userFaceInfo.setOptions(options);
  }


  /**
   * 完善 userFaceSearchBatchInfo 内部参数状态
   */
  private void prepareParamForSearchUserFace(UserFaceSearchInfo searchInfo) {

    Objects.requireNonNull(searchInfo);

    if (searchInfo.getControlProperties() == null) {
      searchInfo.setControlProperties(baiduAiFaceQualityControlProperties.getAddUser());
    }

    String image = searchInfo.getImage();
    ControlProperties controlProperties = searchInfo.getControlProperties();
    Objects.requireNonNull(controlProperties);
    String qualityControl = controlProperties.getQualityControl();
    String livenessControl = controlProperties.getLivenessControl();
    Objects.requireNonNull(qualityControl);
    Objects.requireNonNull(livenessControl);
    Objects.requireNonNull(image);

    HashMap<String, String> options = new HashMap<>(2);

    options.put(Constants.QUALITY_CONTROL_KEY, qualityControl);
    options.put(Constants.LIVENESS_CONTROL_KEY, livenessControl);

    if (StringUtils.isNotBlank(searchInfo.getUserId())) {
      options.put(Constants.USER_ID_KEY, searchInfo.getUserId());
    }
    searchInfo.setOptions(options);
  }

  @Override
  public void afterPropertiesSet() throws Exception {
  }
}
