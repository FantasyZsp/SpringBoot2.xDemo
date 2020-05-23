package xyz.mydev.jdk.bean;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserTask implements IMultiKey {

  private String id;
  private String userId;

  private Integer cycleSequence;
  private LocalDateTime cycleStartTime;
  private LocalDateTime cycleEndTime;
  private Integer money;

  private String orgId;
  private String tenantId;


}
