package xyz.mydev.jdk.genericity.complex.msg;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ZSP
 */
@Data
public class TxMessage implements Message {

  public static final String TARGET_TABLE_NAME = "tx_message";
  public static final Boolean IS_DELAY = false;
  public static final Boolean IS_TX = true;

  private String id;
  private String topic;

  private Integer status;
  private String payload;
  private LocalDateTime time;

  private String traceId;
  private String traceVersion;


  @Override
  public String getTargetTableName() {
    return TARGET_TABLE_NAME;
  }
}
