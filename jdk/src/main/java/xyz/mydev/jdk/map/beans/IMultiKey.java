package xyz.mydev.jdk.map.beans;

import java.time.LocalDateTime;

/**
 * @author ZSP
 */
@SuppressWarnings("all")
public interface IMultiKey {

  Integer getCycleSequence();

  LocalDateTime getCycleStartTime();

  LocalDateTime getCycleEndTime();

  String getOrgId();

  String getTenantId();
}
