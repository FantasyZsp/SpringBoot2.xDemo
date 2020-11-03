package xyz.mydev.baidu.ai.face;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.MatchRequest;
import org.json.JSONObject;
import xyz.mydev.baidu.ai.face.client.bean.AddUserResult;
import xyz.mydev.baidu.ai.face.client.bean.CommonResult;
import xyz.mydev.baidu.ai.face.client.bean.MatchResult;
import xyz.mydev.baidu.ai.face.client.bean.SearchBatchResult;
import xyz.mydev.baidu.ai.face.client.bean.SearchSingleResult;
import xyz.mydev.baidu.ai.face.client.bean.UserFaceInfo;
import xyz.mydev.baidu.ai.face.client.bean.UserFaceMatchInfo;
import xyz.mydev.baidu.ai.face.client.bean.UserFaceSearchInfo;
import xyz.mydev.baidu.ai.face.exception.RateLimitException;
import xyz.mydev.baidu.ai.face.support.RequestLimiter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 主要职责：
 * TODO 1. 输入输出替换.较低优先级.后续让业务看到的是结构化的数据。当用到时再改造
 * TODO 2. 限流
 * <p>
 * ING：
 * 去除对 IBaiduAiFaceApiClient的实现
 *
 * @author ZSP
 */
public class BaiduAiFaceApiClientAdapter implements IBaiduAiFaceApiClientAdapter {

  private final AipFace targetClient;

  public BaiduAiFaceApiClientAdapter(AipFace targetClient) {
    this.targetClient = targetClient;
  }

  /**
   * TODO 人脸检测接口
   */
  public JSONObject detect(String image, String imageType, HashMap<String, String> options) {
    return targetClient.detect(image, imageType, options);
  }

  /**
   * 多个人脸搜索接口
   */
  @Override
  @RequestLimiter(name = "BaiduAiFaceApiClientAdapter:searchBatch", exceptionClass = RateLimitException.class)
  public SearchBatchResult searchBatch(UserFaceSearchInfo info) {
    JSONObject jsonObject = targetClient.multiSearch(info.getImage(), info.getImageType(), info.getGroupIdList(), info.getOptions());
    return SearchBatchResult.convert(jsonObject.toMap());
  }

  /**
   * 单个人脸搜索接口/人脸认证
   * 取决于是否在option中指定了userId
   */
  @Override
  @RequestLimiter(name = "BaiduAiFaceApiClientAdapter:searchSingle", exceptionClass = RateLimitException.class)
  public SearchSingleResult searchSingle(UserFaceSearchInfo info) {
    JSONObject jsonObject = targetClient.search(info.getImage(), info.getImageType(), info.getGroupIdList(), info.getOptions());
    return SearchSingleResult.convert(jsonObject.toMap());
  }

  /**
   * 人脸注册接口
   */
  @Override
  @RequestLimiter(name = "BaiduAiFaceApiClientAdapter:addUser", exceptionClass = RateLimitException.class)
  public AddUserResult addUser(UserFaceInfo userFaceInfo) {
    JSONObject jsonObject = targetClient.addUser(userFaceInfo.getImage(), userFaceInfo.getImageType(), userFaceInfo.getGroupId(), userFaceInfo.getUserId(), userFaceInfo.getOptions());
    return AddUserResult.convert(jsonObject.toMap());
  }

  /**
   * 人脸更新接口
   */
  @Override
  @RequestLimiter(name = "BaiduAiFaceApiClientAdapter:updateUser", exceptionClass = RateLimitException.class)
  public AddUserResult updateUser(UserFaceInfo userFaceInfo) {
    JSONObject jsonObject = targetClient.updateUser(userFaceInfo.getImage(), userFaceInfo.getImageType(), userFaceInfo.getGroupId(), userFaceInfo.getUserId(), userFaceInfo.getOptions());
    return AddUserResult.convert(jsonObject.toMap());
  }

  /**
   * 人脸删除接口
   */
  @Override
  @RequestLimiter(name = "BaiduAiFaceApiClientAdapter:faceDelete", exceptionClass = RateLimitException.class)
  public CommonResult faceDelete(String userId, String groupId, String faceToken) {
    return faceDelete(userId, groupId, faceToken, null);
  }

  /**
   * 人脸删除接口
   */
  private CommonResult faceDelete(String userId, String groupId, String faceToken, HashMap<String, String> options) {
    JSONObject jsonObject = targetClient.faceDelete(userId, groupId, faceToken, options);
    return CommonResult.convert(jsonObject.toMap());
  }


  /**
   * 人脸对比接口
   * 两张人脸图片相似度对比：比对两张图片中人脸的相似度，并返回相似度分值
   */
  @Override
  @RequestLimiter(name = "BaiduAiFaceApiClientAdapter:match", exceptionClass = RateLimitException.class)
  public MatchResult match(List<UserFaceMatchInfo> infoList) {

    List<MatchRequest> inputs = new ArrayList<>(2);

    for (UserFaceMatchInfo info : infoList) {
      inputs.add(new MatchRequest(info.getImage(),
        info.getImageType(),
        info.getFaceType(),
        info.getControlProperties().getQualityControl(),
        info.getControlProperties().getLivenessControl()));
    }

    return MatchResult.convert(targetClient.match(inputs).toMap());
  }

//  /**
//   * 用户信息查询接口
//   */
//  public JSONObject getUser(String userId, String groupId, HashMap<String, String> options) {
//    return targetClient.getUser(userId, groupId, options);
//
//  }
//
//  /**
//   * 获取用户人脸列表接口
//   */
//  @Override
//  public JSONObject faceGetlist(String userId, String groupId, HashMap<String, String> options) {
//    return targetClient.faceGetlist(userId, groupId, options);
//  }

//  /**
//   * 获取用户列表接口
//   */
//  @Override
//  public JSONObject getGroupUsers(String groupId, HashMap<String, String> options) {
//    return targetClient.getGroupUsers(groupId, options);
//
//  }

//  /**
//   * 复制用户接口
//   */
//  @Override
//  public JSONObject userCopy(String userId, HashMap<String, String> options) {
//    return targetClient.userCopy(userId, options);
//
//  }
//
//  /**
//   * 删除用户接口
//   */
//  @Override
//  public JSONObject deleteUser(String groupId, String userId, HashMap<String, String> options) {
//    return targetClient.deleteUser(groupId, userId, options);
//
//  }

//  /**
//   * 创建用户组接口
//   */
//  @Override
//  public JSONObject groupAdd(String groupId, HashMap<String, String> options) {
//    return targetClient.groupAdd(groupId, options);
//
//  }

//  /**
//   * 删除用户组接口
//   */
//  @Override
//  public JSONObject groupDelete(String groupId, HashMap<String, String> options) {
//    return targetClient.groupDelete(groupId, options);
//
//  }

//  /**
//   * 组列表查询接口
//   */
//  @Override
//  public JSONObject getGroupList(HashMap<String, String> options) {
//    return targetClient.getGroupList(options);
//
//  }

//  /**
//   * 身份验证接口
//   */
//  @Override
//  public JSONObject personVerify(String image, String imageType, String idCardNumber, String name, HashMap<String, String> options) {
//    return targetClient.personVerify(image, imageType, idCardNumber, name, options);
//
//  }

//  /**
//   * 语音校验码接口接口
//   */
//  @Override
//  public JSONObject videoSessioncode(HashMap<String, String> options) {
//    return targetClient.videoSessioncode(options);
//  }

//  /**
//   * 视频活体检测接口接口
//   */
//  @Override
//  public JSONObject videoFaceliveness(String sessionId, byte[] video, HashMap<String, String> options) {
//    return targetClient.videoFaceliveness(sessionId, video, options);
//
//  }

//  /**
//   * 视频活体检测接口接口
//   */
//  @Override
//  public JSONObject videoFaceliveness(String sessionId, String video, HashMap<String, String> options) {
//    return targetClient.videoFaceliveness(sessionId, video, options);
//  }
//  /**
//   * 在线活体检测接口
//   */
//  @Override
//  public JSONObject faceverify(List<FaceVerifyRequest> input) {
//    return targetClient.faceverify(input);
//  }

//  /**
//   * 身份证与名字比对接口
//   */
//  @Override
//  public JSONObject idMatch(String idCardNum, String name, HashMap<String, Object> options) {
//    return targetClient.idMatch(idCardNum, name, options);
//  }


}
