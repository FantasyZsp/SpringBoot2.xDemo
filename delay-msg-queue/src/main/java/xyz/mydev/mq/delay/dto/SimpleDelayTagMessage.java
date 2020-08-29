package xyz.mydev.mq.delay.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author ZSP
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SimpleDelayTagMessage implements IDelayTagMessage {
  private String businessId;
  private String tag;
  private LocalDateTime time;

  @Override
  public String getTag() {
    return tag;
  }
}
