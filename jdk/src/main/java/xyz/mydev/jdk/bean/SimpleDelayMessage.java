package xyz.mydev.jdk.bean;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ZSP
 */
@Data
public class SimpleDelayMessage implements IDelayMessage<SimpleEntity> {
  private SimpleEntity payload;
  private LocalDateTime startTime;

  public void setPayload(SimpleEntity payload) {
    this.payload = payload;
  }

  @Override
  public LocalDateTime getTime() {
    return startTime;
  }

  public static void main(String[] args) {


  }


}