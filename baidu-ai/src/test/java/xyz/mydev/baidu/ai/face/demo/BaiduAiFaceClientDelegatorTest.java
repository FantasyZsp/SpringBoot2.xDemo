package xyz.mydev.baidu.ai.face.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.mydev.baidu.ai.face.demo.client.bean.AddUserResult;
import xyz.mydev.baidu.ai.face.demo.client.bean.UserFaceInfo;
import xyz.mydev.baidu.ai.face.demo.constant.Constants;
import xyz.mydev.common.utils.JsonUtil;

/**
 * @author ZSP
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class BaiduAiFaceClientDelegatorTest {


  static String image = "xxx";
  static String userId = "testMyUserId";
  static String userInfo = "testMyUserInfo";
  static String groupIdTest = "zspTestGroup";

  @Autowired
  private BaiduAiFaceClientDelegator delegator;

  @Test
  public void addUser() {

    // 人脸注册
    AddUserResult addUserResult = delegator.addUser(UserFaceInfo.builder()
      .groupId(groupIdTest)
      .userId(userId)
      .userInfo(userInfo)
      .image(image)
      .actionType(Constants.ActionType.ACTION_TYPE_APPEND)
      .build());

    System.out.println(JsonUtil.obj2StringPretty(addUserResult));
  }

  @Test
  public void updateUser() {
  }

  @Test
  public void deleteUserFace() {
  }

  @Test
  public void searchBatch() {
  }

  @Test
  public void searchSingle() {
  }

  @Test
  public void userAuth() {
  }

  @Test
  public void afterPropertiesSet() {
  }
}