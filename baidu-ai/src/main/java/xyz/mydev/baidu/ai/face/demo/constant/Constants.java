package xyz.mydev.baidu.ai.face.demo.constant;

/**
 * @author ZSP
 */
public interface Constants {

  String QUALITY_CONTROL_KEY = "quality_control";

  String LIVENESS_CONTROL_KEY = "liveness_control";

  String USER_INFO_KEY = "user_info";

  interface ImageType {
    String BASE64 = "BASE64";
    String URL = "URL";
    String FACE_TOKEN = "FACE_TOKEN";
  }
}
