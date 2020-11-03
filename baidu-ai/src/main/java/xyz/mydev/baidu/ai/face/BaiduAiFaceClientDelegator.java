package xyz.mydev.baidu.ai.face;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import xyz.mydev.baidu.ai.face.client.bean.AddUserResult;
import xyz.mydev.baidu.ai.face.client.bean.CommonResult;
import xyz.mydev.baidu.ai.face.client.bean.MatchResult;
import xyz.mydev.baidu.ai.face.client.bean.SearchBatchResult;
import xyz.mydev.baidu.ai.face.client.bean.SearchSingleResult;
import xyz.mydev.baidu.ai.face.client.bean.UserFaceInfo;
import xyz.mydev.baidu.ai.face.client.bean.UserFaceMatchInfo;
import xyz.mydev.baidu.ai.face.client.bean.UserFaceSearchInfo;
import xyz.mydev.baidu.ai.face.constant.Constants;
import xyz.mydev.baidu.ai.face.exception.RetryableException;
import xyz.mydev.baidu.ai.face.property.BaiduAiFaceQualityControlProperties;
import xyz.mydev.baidu.ai.face.property.ControlProperties;

import java.util.HashMap;
import java.util.List;
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
@Slf4j
public class BaiduAiFaceClientDelegator {

  private final BaiduAiFaceApiClientAdapter targetClientAdapter;
  private final BaiduAiFaceQualityControlProperties baiduAiFaceQualityControlProperties;

  private static final String RECOVER_CODE = "-200";

  public BaiduAiFaceClientDelegator(BaiduAiFaceApiClientAdapter targetClientAdapter,
                                    BaiduAiFaceQualityControlProperties baiduAiFaceQualityControlProperties) {
    this.targetClientAdapter = targetClientAdapter;
    this.baiduAiFaceQualityControlProperties = baiduAiFaceQualityControlProperties;
  }


  /**
   * 注册人脸
   */
  @Retryable(value = {RetryableException.class}, maxAttempts = 3, backoff = @Backoff(delay = 10L, multiplier = 1), recover = "recoverAddUser", stateful = false)
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
  @Retryable(value = {RetryableException.class}, maxAttempts = 3, backoff = @Backoff(delay = 10L, multiplier = 1), recover = "recoverAddUser", stateful = false)
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
  @Retryable(value = {RetryableException.class}, maxAttempts = 3, backoff = @Backoff(delay = 10L, multiplier = 1), recover = "recoverDeleteUserFace", stateful = false)
  public CommonResult deleteUserFace(String userId, String groupId, String faceToken) {
    Objects.requireNonNull(groupId);
    Objects.requireNonNull(userId);
    Objects.requireNonNull(faceToken);
    return targetClientAdapter.faceDelete(userId, groupId, faceToken);
  }

  /**
   * 多个人脸搜索
   * 仅返回符合阈值的用户信息
   */
  @Retryable(value = {RetryableException.class}, maxAttempts = 3, backoff = @Backoff(delay = 10L, multiplier = 1), recover = "recoverSearchBatch", stateful = false)
  public SearchBatchResult searchBatch(UserFaceSearchInfo searchInfo) {
    if (searchInfo.getControlProperties() == null) {
      searchInfo.setControlProperties(baiduAiFaceQualityControlProperties.getSearchBatch());
    }
    prepareParamForSearchUserFace(searchInfo);
    return targetClientAdapter.searchBatch(searchInfo);
  }

  /**
   * 单人脸搜索
   */
  @Retryable(value = {RetryableException.class}, maxAttempts = 3, backoff = @Backoff(delay = 10L, multiplier = 1), recover = "recoverSearchSingleOrUserAuth", stateful = false)
  public SearchSingleResult searchSingle(UserFaceSearchInfo searchInfo) {
    if (searchInfo.getControlProperties() == null) {
      searchInfo.setControlProperties(baiduAiFaceQualityControlProperties.getSearchSingle());
    }

    prepareParamForSearchUserFace(searchInfo);
    return targetClientAdapter.searchSingle(searchInfo);
  }

  /**
   * 人脸认证
   */
  @Retryable(value = {RetryableException.class}, maxAttempts = 3, backoff = @Backoff(delay = 10L, multiplier = 1), recover = "recoverSearchSingleOrUserAuth", stateful = false)
  public SearchSingleResult userAuth(UserFaceSearchInfo searchInfo) {
    if (StringUtils.isBlank(Objects.requireNonNull(searchInfo).getUserId())) {
      throw new IllegalArgumentException("userAuth requires userId");
    }
    if (searchInfo.getControlProperties() == null) {
      searchInfo.setControlProperties(baiduAiFaceQualityControlProperties.getUserAuth());
    }

    prepareParamForSearchUserFace(searchInfo);
    return targetClientAdapter.searchSingle(searchInfo);
  }


  /**
   * 人脸认证
   */
  @Retryable(value = {RetryableException.class}, maxAttempts = 3, backoff = @Backoff(delay = 10L, multiplier = 1), recover = "recoverMatch", stateful = false)
  public MatchResult match(List<UserFaceMatchInfo> matchInfoList) {
    prepareParamForMatchUser(matchInfoList);
    return targetClientAdapter.match(matchInfoList);
  }

  private void prepareParamForMatchUser(List<UserFaceMatchInfo> matchInfoList) {
    Objects.requireNonNull(matchInfoList);
    if (CollectionUtils.isEmpty(matchInfoList) || matchInfoList.size() != 2) {
      throw new IllegalArgumentException("matchInfoList invalid");
    }

    for (UserFaceMatchInfo info : matchInfoList) {
      Objects.requireNonNull(info.getImage());
      Objects.requireNonNull(info.getImageType());

      if (info.getControlProperties() == null) {
        info.setControlProperties(baiduAiFaceQualityControlProperties.getMatch());
      }

      ControlProperties controlProperties = info.getControlProperties();
      Objects.requireNonNull(controlProperties);
      String qualityControl = controlProperties.getQualityControl();
      String livenessControl = controlProperties.getLivenessControl();
      Objects.requireNonNull(qualityControl);
      Objects.requireNonNull(livenessControl);
    }


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

    if (StringUtils.isBlank(userId)) {
      throw new IllegalArgumentException();
    }
    Objects.requireNonNull(image);

    ControlProperties controlProperties = userFaceInfo.getControlProperties();
    Objects.requireNonNull(controlProperties);
    String qualityControl = controlProperties.getQualityControl();
    String livenessControl = controlProperties.getLivenessControl();
    Objects.requireNonNull(qualityControl);
    Objects.requireNonNull(livenessControl);

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

    // 外部没传，按照默认级别给
    if (searchInfo.getControlProperties() == null) {
      searchInfo.setControlProperties(baiduAiFaceQualityControlProperties.getDefaultControlProperties());
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

    if (controlProperties.getMaxFaceNum() != null) {
      options.put(Constants.MAX_FACE_NUM_KEY, String.valueOf(controlProperties.getMaxFaceNum()));
    }

    if (controlProperties.getMaxUserNum() != null) {
      options.put(Constants.MAX_USER_NUM_KEY, String.valueOf(controlProperties.getMaxUserNum()));
    }

    if (controlProperties.getMatchThreshold() != null) {
      options.put(Constants.MATCH_THRESHOLD_KEY, String.valueOf(controlProperties.getMatchThreshold()));
    }
    searchInfo.setOptions(options);
  }


  @Recover
  public AddUserResult recoverAddUser(Exception e, UserFaceInfo userFaceInfo) {
    log.error("recover after AddUser retry ex: {}", e.getMessage());
    return AddUserResult.recoverResult(RECOVER_CODE, e.getMessage());
  }

  @Recover
  public CommonResult recoverDeleteUserFace(Exception e, String userId, String groupId, String faceToken) {
    log.error("recover after DeleteUserFace retry ex: {}", e.getMessage());
    return CommonResult.recoverCommonResult(RECOVER_CODE, e.getMessage());
  }

  @Recover
  public SearchBatchResult recoverSearchBatch(Exception e, UserFaceSearchInfo searchInfo) {
    log.error("recover after SearchBatch retry ex: {}", e.getMessage());
    return SearchBatchResult.recoverSearchBatchResult(RECOVER_CODE, e.getMessage());
  }

  @Recover
  public SearchSingleResult recoverSearchSingleOrUserAuth(Exception e, UserFaceSearchInfo searchInfo) {
    log.error("recover after SearchSingleOrUserAuth retry ex: {}", e.getMessage());
    return SearchSingleResult.recoverSearchSingleResult(RECOVER_CODE, e.getMessage());
  }

  @Recover
  public MatchResult recoverMatch(Exception e, List<UserFaceMatchInfo> matchInfoList) {
    log.error("recover after Match retry ex: {}", e.getMessage());
    return MatchResult.recoverMatchResult(RECOVER_CODE, e.getMessage());
  }
}
