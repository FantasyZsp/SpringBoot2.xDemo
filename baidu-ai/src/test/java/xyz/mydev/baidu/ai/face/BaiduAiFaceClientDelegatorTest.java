package xyz.mydev.baidu.ai.face;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.mydev.ai.face.baidu.BaiduAiFaceClientDelegator;
import xyz.mydev.ai.face.baidu.client.bean.AddUserResult;
import xyz.mydev.ai.face.baidu.client.bean.CommonResult;
import xyz.mydev.ai.face.baidu.client.bean.MatchResult;
import xyz.mydev.ai.face.baidu.client.bean.SearchBatchResult;
import xyz.mydev.ai.face.baidu.client.bean.SearchSingleResult;
import xyz.mydev.ai.face.baidu.client.bean.UserFaceInfo;
import xyz.mydev.ai.face.baidu.client.bean.UserFaceMatchInfo;
import xyz.mydev.ai.face.baidu.client.bean.UserFaceSearchInfo;
import xyz.mydev.ai.face.baidu.client.constant.Constants;
import xyz.mydev.ai.face.baidu.client.property.BaiduAiFaceQualityControlProperties;
import xyz.mydev.common.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author ZSP
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootApplication
public class BaiduAiFaceClientDelegatorTest implements InitializingBean {


  static String image = "xxx";
  static String userId = "testMyUserId";
  static String userInfo = "testMyUserInfo";
  static String groupIdTest = "studentAttendance";

  @Autowired
  private BaiduAiFaceClientDelegator delegator;

  @Autowired
  private BaiduAiFaceQualityControlProperties baiduAiFaceQualityControlProperties;

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

    baiduAiFaceQualityControlProperties.getSearchBatch().setMatchThreshold(99);
    SearchBatchResult searchBatchResult = delegator.searchBatch(UserFaceSearchInfo.builder()

      .groupIdList(groupIdTest)
      .image(image)
      .imageType(Constants.ImageType.URL)
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

  @Test
  public void match() {
    MatchResult matchResult = delegator.match(List.of(UserFaceMatchInfo.builder()
        .image("xx")
        .imageType(Constants.ImageType.FACE_TOKEN)
        .build(),
      UserFaceMatchInfo.builder()
        .image("xxx")
        .imageType(Constants.ImageType.URL)
        .build()));

    System.out.println(JsonUtil.obj2StringPretty(matchResult));
    Optional.ofNullable(matchResult.getResult()).ifPresent(x -> System.out.println(x.getFaceList()));
  }

  @Test
  public void searchBatchQpsTest() throws InterruptedException {

    ExecutorService executorService = Executors.newFixedThreadPool(20);
    baiduAiFaceQualityControlProperties.getSearchBatch().setMatchThreshold(99);


    List<Callable<SearchBatchResult>> taskList = new ArrayList<>();
    for (int i = 0; i < 30; i++) {
      Callable<SearchBatchResult> callable = () ->
        delegator.searchBatch(UserFaceSearchInfo.builder()
          .groupIdList(groupIdTest)
          .image(image)
          .imageType(Constants.ImageType.URL)
          .build());

      taskList.add(callable);
    }

    List<Future<SearchBatchResult>> futures = executorService.invokeAll(taskList);

    AtomicLong atomicLong = new AtomicLong();
    for (Future<SearchBatchResult> future : futures) {
      try {
        log.info("num:{}, result: {}", atomicLong.incrementAndGet(), future.get().getErrorCode());
      } catch (Exception e) {
        e.printStackTrace();
        break;
      }
    }


  }

  @Override
  public void afterPropertiesSet() throws Exception {
    log.info("识别配置: {}", baiduAiFaceQualityControlProperties);
  }
}