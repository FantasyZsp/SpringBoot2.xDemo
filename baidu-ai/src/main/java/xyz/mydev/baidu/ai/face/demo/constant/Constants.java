package xyz.mydev.baidu.ai.face.demo.constant;

/**
 * @author ZSP
 */
public interface Constants {

  String QUALITY_CONTROL_KEY = "quality_control";

  String LIVENESS_CONTROL_KEY = "liveness_control";

  String USER_ID_KEY = "user_id";

  String USER_INFO_KEY = "user_info";

  String ACTION_TYPE_KEY = "action_type";

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
}
