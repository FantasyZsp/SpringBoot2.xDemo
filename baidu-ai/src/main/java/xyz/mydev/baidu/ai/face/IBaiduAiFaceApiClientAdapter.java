package xyz.mydev.baidu.ai.face;

import xyz.mydev.baidu.ai.face.client.bean.AddUserResult;
import xyz.mydev.baidu.ai.face.client.bean.UserFaceInfo;

/**
 * @author ZSP
 */
public interface IBaiduAiFaceApiClientAdapter {
  AddUserResult addUser(UserFaceInfo userFaceInfo);

  AddUserResult updateUser(UserFaceInfo userFaceInfo);
}
