package xyz.mydev.jdk.map;

import org.junit.Assert;
import xyz.mydev.jdk.map.beans.DefaultMultiKey;
import xyz.mydev.jdk.map.beans.OrgTask;
import xyz.mydev.jdk.map.beans.RewardToAccumulateValue;
import xyz.mydev.jdk.map.beans.UserTask;
import xyz.mydev.util.JsonUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZSP
 */
public class DefaultPieceRoleEventTaskMultiKeyTest {

  public static void main(String[] args) {
    LocalDateTime now = LocalDateTime.now();
    OrgTask orgTask = buildOrgTask(now);
    UserTask userTask = buildUserTask(now);
    Map<DefaultMultiKey, RewardToAccumulateValue> rewardMap = new HashMap<>();
    rewardMap.put(DefaultMultiKey.extractKey(orgTask), RewardToAccumulateValue.builder()
      .completeCount(1)
      .money(1)
      .experience(1)
      .integral(1)
      .prestige(1)
      .build());

    Assert.assertEquals(DefaultMultiKey.extractKey(orgTask).hashCode(), DefaultMultiKey.extractKey(userTask).hashCode());

    RewardToAccumulateValue rewardToAccumulateValue = rewardMap.get(DefaultMultiKey.extractKey(userTask));
    Assert.assertNotNull(rewardToAccumulateValue);

    RewardToAccumulateValue build = RewardToAccumulateValue.builder()
      .completeCount(1)
      .money(1)
      .experience(1)
      .integral(3)
      .prestige(1)
      .build();

    List<UserTask> userTasks = new ArrayList<>();
    userTasks.add(buildUserTask(now));
    userTasks.add(buildUserTask(now));
    userTasks.add(buildUserTask(now.plusDays(1L)));
    userTasks.add(buildUserTask(now.plusDays(1L)));
    userTasks.add(buildUserTask(now.plusDays(1L)));
    userTasks.add(buildUserTask(now.plusDays(1L)));
    userTasks.add(buildUserTask(now));
    userTasks.add(buildUserTask(now));
    userTasks.add(buildUserTask(now));


    for (UserTask task : userTasks) {
      rewardMap.compute(DefaultMultiKey.extractKey(task), (key, oldValue) -> {
        if (oldValue == null) {
          return RewardToAccumulateValue.builder()
            .completeCount(1)
            .money(1)
            .experience(1)
            .integral(3)
            .prestige(1)
            .build();
        }
        oldValue.accumulate(build);
        return oldValue;
      });
    }

    System.out.println(JsonUtil.obj2StringPretty(rewardMap));

  }

  public static OrgTask buildOrgTask(LocalDateTime now) {
    OrgTask orgTask = new OrgTask();
    String orgId = "orgId";
    String tenantId = "tenantId";

    orgTask.setId("id");
    orgTask.setCycleSequence(0);
    orgTask.setCycleStartTime(now);
    orgTask.setCycleEndTime(now);

    orgTask.setEmpNum(1);

    orgTask.setMoney(1);
    orgTask.setOrgId(orgId);
    orgTask.setTenantId(tenantId);

    return orgTask;
  }

  public static UserTask buildUserTask(LocalDateTime now) {
    UserTask userTask = new UserTask();
    String userId = "userId";
    String orgId = "orgId";
    String tenantId = "tenantId";

    userTask.setId("id");
    userTask.setUserId(userId);
    userTask.setCycleSequence(0);
    userTask.setCycleStartTime(now);
    userTask.setCycleEndTime(now);

    userTask.setOrgId(orgId);
    userTask.setTenantId(tenantId);

    return userTask;
  }

}