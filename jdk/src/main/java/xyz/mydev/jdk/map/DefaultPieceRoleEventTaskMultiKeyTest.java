package xyz.mydev.jdk.map;

import org.junit.Assert;
import xyz.mydev.common.utils.JsonUtil;
import xyz.mydev.jdk.bean.DefaultMultiKey;
import xyz.mydev.jdk.bean.OrgTask;
import xyz.mydev.jdk.bean.RewardToAccumulateValue;
import xyz.mydev.jdk.bean.UserTask;

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
    String userId = "userId";
    String roleId = "roleId";
    String sourceId = "sourceId";
    String rewardConfigBatchId = "rewardConfigBatchId";
    String pieceEventId = "pieceEventId";
    String pieceRoleEventId = "pieceRoleEventId";
    String pieceEventName = "pieceEventName";
    String pieceEventGroupId = "pieceEventGroupId";
    String pieceRoleGroupId = "pieceRoleGroupId";
    String orgId = "orgId";
    String tenantId = "tenantId";

//    orgTask.setId("id");
//    orgTask.setCycleSequence(0);
//    orgTask.setCycleStartTime(now);
//    orgTask.setCycleEndTime(now);
//
//    orgTask.setCompleteCount(1);
//    orgTask.setEmpNum(1);
//
//    orgTask.setMoney(1);
//    orgTask.setExperience(1);
//    orgTask.setIntegral(1);
//    orgTask.setPrestige(1);
//    orgTask.setRoleId(roleId);
//    orgTask.setSysPieceSourceId(sourceId);
//    orgTask.setCycleType(1);
//
//    orgTask.setWay(1);
//    orgTask.setRewardConfigBatchId(rewardConfigBatchId);
//    orgTask.setPieceEventId(pieceEventId);
//    orgTask.setPieceRoleEventId(pieceRoleEventId);
//    orgTask.setPieceEventName(pieceEventName);
//
//    orgTask.setPieceEventGroupId(pieceEventGroupId);
//    orgTask.setPieceRoleGroupId(pieceRoleGroupId);
//
//    orgTask.setCreatedAt(now);
//    orgTask.setUpdatedAt(now);
//    orgTask.setCreatedBy(userId);
//    orgTask.setUpdatedBy(userId);
//    orgTask.setOrgId(orgId);
//    orgTask.setTenantId(tenantId);

    return orgTask;
  }

  public static UserTask buildUserTask(LocalDateTime now) {
    UserTask userTask = new UserTask();
    String userId = "userId";
    String roleId = "roleId";
    String sourceId = "sourceId";
    String rewardConfigBatchId = "rewardConfigBatchId";
    String pieceEventId = "pieceEventId";
    String pieceRoleEventId = "pieceRoleEventId";
    String pieceEventName = "pieceEventName";
    String pieceEventGroupId = "pieceEventGroupId";
    String pieceRoleGroupId = "pieceRoleGroupId";
    String orgId = "orgId";
    String tenantId = "tenantId";

    userTask.setId("id");
    userTask.setCycleSequence(0);
    userTask.setCycleStartTime(now);
    userTask.setCycleEndTime(now);

//    userTask.setCompleteCount(1);
//
//    userTask.setMoney(1);
//    userTask.setExperience(1);
//    userTask.setIntegral(1);
//    userTask.setPrestige(1);
//    userTask.setRoleId(roleId);
//    userTask.setSysPieceSourceId(sourceId);
//    userTask.setCycleType(1);
//
//    userTask.setWay(1);
//    userTask.setRewardConfigBatchId(rewardConfigBatchId);
//    userTask.setPieceEventId(pieceEventId);
//    userTask.setPieceRoleEventId(pieceRoleEventId);
//    userTask.setPieceEventName(pieceEventName);
//
//    userTask.setPieceEventGroupId(pieceEventGroupId);
//    userTask.setPieceRoleGroupId(pieceRoleGroupId);
//
//    userTask.setCreatedAt(now);
//    userTask.setUpdatedAt(now);
//    userTask.setCreatedBy(userId);
//    userTask.setUpdatedBy(userId);
//    userTask.setOrgId(orgId);
//    userTask.setTenantId(tenantId);

    return userTask;
  }

}