package xyz.mydev.baidu.ai.face.constant;

/**
 * @author ZSP
 */
public interface Constants {

  String QUALITY_CONTROL_KEY = "quality_control";

  String LIVENESS_CONTROL_KEY = "liveness_control";

  String USER_ID_KEY = "user_id";

  String USER_INFO_KEY = "user_info";

  String ACTION_TYPE_KEY = "action_type";

  String MAX_FACE_NUM_KEY = "max_face_num";

  String MAX_USER_NUM_KEY = "max_user_num";

  String MATCH_THRESHOLD_KEY = "match_threshold";

  interface ImageType {
    String BASE64 = "BASE64";
    String URL = "URL";
    String FACE_TOKEN = "FACE_TOKEN";
  }

  interface ActionType {
    String ACTION_TYPE_APPEND = "APPEND";
    String ACTION_TYPE_REPLACE = "REPLACE";
    String ACTION_TYPE_UPDATE = "UPDATE";
  }

  /**
   * 人脸的类型 LIVE表示生活照：通常为手机、相机拍摄的人像图片、或从网络获取的人像图片等，IDCARD表示身份证芯片照：二代身份证内置芯片中的人像照片， WATERMARK表示带水印证件照：一般为带水印的小图，如公安网小图 CERT表示证件照片：如拍摄的身份证、工卡、护照、学生证等证件图片 默认LIVE
   */
  interface FaceType {
    String FACE_TYPE_LIVE = "LIVE";
    String FACE_TYPE_ID_CARD = "IDCARD";
    String FACE_TYPE_WATERMARK = "WATERMARK";
    String FACE_TYPE_CERT = "CERT";
  }
}
