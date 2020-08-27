package xyz.mydev.configuration.externalized.properties;

import lombok.Data;

/**
 * @author zhaosp
 */
@Data
public class ProducerProperties {
  private String nameServer;
  private String group;
  private String instanceName;
}