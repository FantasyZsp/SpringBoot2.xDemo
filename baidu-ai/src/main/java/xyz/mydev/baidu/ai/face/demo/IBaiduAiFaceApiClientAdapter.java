package xyz.mydev.baidu.ai.face.demo;

import xyz.mydev.baidu.ai.face.demo.client.bean.AddUserResult;
import xyz.mydev.baidu.ai.face.demo.client.bean.UserFaceInfo;

/**
 * @author ZSP
 */
public interface IBaiduAiFaceApiClientAdapter {
  AddUserResult addUser(UserFaceInfo userFaceInfo);

  AddUserResult updateUser(UserFaceInfo userFaceInfo);
}
