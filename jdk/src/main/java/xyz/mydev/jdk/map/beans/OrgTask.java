package xyz.mydev.jdk.map.beans;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrgTask implements IMultiKey {

  private String id;
  private Integer cycleSequence;

  private LocalDateTime cycleStartTime;
  private LocalDateTime cycleEndTime;
  private Integer empNum;
  private Integer money;

  private String orgId;
  private String tenantId;


}
