package xyz.mydev.baidu.ai.face.demo;

import com.baidu.aip.face.AipFace;

import static xyz.mydev.baidu.ai.face.demo.BaiduAiTokenUtils.API_KEY;
import static xyz.mydev.baidu.ai.face.demo.BaiduAiTokenUtils.APP_ID;
import static xyz.mydev.baidu.ai.face.demo.BaiduAiTokenUtils.SECRET_KEY;

/**
 * @author ZSP
 */
public class BaiduAiFaceCollectClientFactory {
  public static AipFace getClient() {
    AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
    // 可选：设置网络连接参数
    client.setConnectionTimeoutInMillis(2000);
    client.setSocketTimeoutInMillis(60000);
    return client;
  }
}
