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
}
