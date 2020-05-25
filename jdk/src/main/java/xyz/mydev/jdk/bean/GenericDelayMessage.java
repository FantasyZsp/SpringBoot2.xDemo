package xyz.mydev.jdk.bean;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ZSP
 */
@Data
public class GenericDelayMessage<T> implements IDelayMessage<T> {
  private T payload;
  private LocalDateTime time;

  private String payLoadClassName;


  @Override
  public LocalDateTime getTime() {
    return time;
  }

  public void setPayload(T payload) {
    this.payload = payload;
    this.payLoadClassName = getPayloadClassName();
  }
}
