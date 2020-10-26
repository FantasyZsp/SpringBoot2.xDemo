package xyz.mydev.baidu.ai.face.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.mydev.baidu.ai.face.demo.client.bean.AddUserResult;
import xyz.mydev.baidu.ai.face.demo.client.bean.CommonResult;
import xyz.mydev.baidu.ai.face.demo.client.bean.SearchBatchResult;
import xyz.mydev.baidu.ai.face.demo.client.bean.SearchSingleResult;
import xyz.mydev.baidu.ai.face.demo.client.bean.UserFaceInfo;
import xyz.mydev.baidu.ai.face.demo.client.bean.UserFaceSearchInfo;
import xyz.mydev.baidu.ai.face.demo.constant.Constants;
import xyz.mydev.common.utils.JsonUtil;

import java.util.Optional;

/**
 * @author ZSP
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
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
    Optional.ofNullable(addUserResult.getResult()).ifPresent(x -> System.out.println(x.getFaceToken()));
  }

  @Test
  public void updateUser() {

    // 人脸更新
    AddUserResult addUserResult = delegator.updateUser(UserFaceInfo.builder()
      .groupId(groupIdTest)
      .userId(userId)
      .userInfo(userInfo)
      .image(image)
      .actionType(Constants.ActionType.ACTION_TYPE_UPDATE)
      .build());

    System.out.println(JsonUtil.obj2StringPretty(addUserResult));
    Optional.ofNullable(addUserResult.getResult()).ifPresent(x -> System.out.println(x.getFaceToken()));
  }

  @Test
  public void deleteUserFace() {
    // 人脸删除
    CommonResult commonResult = delegator.deleteUserFace(userId, groupIdTest, "1");
    log.info(JsonUtil.obj2StringPretty(commonResult));

  }

  @Test
  public void searchBatch() {
    SearchBatchResult searchBatchResult = delegator.searchBatch(UserFaceSearchInfo.builder()

      .groupIdList(groupIdTest)
      .image(image)
      .imageType(Constants.ImageType.URL)
      .maxFaceNum(10)

      .build());

    System.out.println(JsonUtil.obj2StringPretty(searchBatchResult));
    Optional.ofNullable(searchBatchResult.getResult()).ifPresent(x -> System.out.println(x.getFaceNum()));
    Optional.ofNullable(searchBatchResult.getResult()).ifPresent(x -> System.out.println(x.getFaceList()));

  }

  @Test
  public void searchSingle() {
    SearchSingleResult searchSingleResult = delegator.searchSingle(UserFaceSearchInfo.builder()

      .groupIdList(groupIdTest)
      .image(image)
      .imageType(Constants.ImageType.URL)
      .build());

    System.out.println(JsonUtil.obj2StringPretty(searchSingleResult));
    Optional.ofNullable(searchSingleResult.getResult()).ifPresent(x -> System.out.println(x.getFaceToken()));
    Optional.ofNullable(searchSingleResult.getResult()).ifPresent(x -> System.out.println(x.getUserList()));
  }

  @Test
  public void userAuth() {
    SearchSingleResult searchSingleResult = delegator.userAuth(UserFaceSearchInfo.builder()

      .groupIdList(groupIdTest)
      .image(image)
      .userId(userId)
      .imageType(Constants.ImageType.URL)
      .build());

    System.out.println(JsonUtil.obj2StringPretty(searchSingleResult));
    Optional.ofNullable(searchSingleResult.getResult()).ifPresent(x -> System.out.println(x.getFaceToken()));
    Optional.ofNullable(searchSingleResult.getResult()).ifPresent(x -> System.out.println(x.getUserList()));
  }
}