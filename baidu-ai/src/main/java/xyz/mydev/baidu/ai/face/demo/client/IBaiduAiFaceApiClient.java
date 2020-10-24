package xyz.mydev.baidu.ai.face.demo.client;

import com.baidu.aip.face.FaceVerifyRequest;
import com.baidu.aip.face.MatchRequest;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * @author ZSP
 */
public interface IBaiduAiFaceApiClient {
  /**
   * 人脸检测接口
   */
  JSONObject detect(String image, String imageType, HashMap<String, String> options);

  /**
   * 人脸搜索接口
   */
  JSONObject search(String image, String imageType, String groupIdList, HashMap<String, String> options);

  /**
   * 人脸注册接口
   */
  default JSONObject addUser(String image, String imageType, String groupId, String userId, HashMap<String, String> options) {
    throw new UnsupportedOperationException();
  }


  /**
   * 人脸更新接口
   */
  default JSONObject updateUser(String image, String imageType, String groupId, String userId, HashMap<String, String> options) {
    throw new UnsupportedOperationException();
  }

  /**
   * 人脸删除接口
   */
  JSONObject faceDelete(String userId, String groupId, String faceToken, HashMap<String, String> options);

  /**
   * 用户信息查询接口
   */
  JSONObject getUser(String userId, String groupId, HashMap<String, String> options);

  /**
   * 获取用户人脸列表接口
   */
  JSONObject faceGetlist(String userId, String groupId, HashMap<String, String> options);

  /**
   * 获取用户列表接口
   */
  JSONObject getGroupUsers(String groupId, HashMap<String, String> options);

  /**
   * 复制用户接口
   */
  JSONObject userCopy(String userId, HashMap<String, String> options);

  /**
   * 删除用户接口
   */
  JSONObject deleteUser(String groupId, String userId, HashMap<String, String> options);

  /**
   * 创建用户组接口
   */
  JSONObject groupAdd(String groupId, HashMap<String, String> options);

  /**
   * 删除用户组接口
   */
  JSONObject groupDelete(String groupId, HashMap<String, String> options);

  /**
   * 组列表查询接口
   */
  JSONObject getGroupList(HashMap<String, String> options);

  /**
   * 身份验证接口
   */
  JSONObject personVerify(String image, String imageType, String idCardNumber, String name, HashMap<String, String> options);

  /**
   * 语音校验码接口接口
   */
  JSONObject videoSessioncode(HashMap<String, String> options);

  /**
   * 视频活体检测接口接口
   */
  JSONObject videoFaceliveness(String sessionId, byte[] video, HashMap<String, String> options);

  /**
   * 视频活体检测接口接口
   */
  JSONObject videoFaceliveness(String sessionId, String video, HashMap<String, String> options);


  /**
   * 人脸对比接口
   * 两张人脸图片相似度对比：比对两张图片中人脸的相似度，并返回相似度分值
   */
  JSONObject match(List<MatchRequest> input);

  /**
   * 在线活体检测接口
   */
  JSONObject faceverify(List<FaceVerifyRequest> input);

  /**
   * 身份证与名字比对接口
   */
  JSONObject idMatch(String idCardNum, String name, HashMap<String, Object> options);
}
