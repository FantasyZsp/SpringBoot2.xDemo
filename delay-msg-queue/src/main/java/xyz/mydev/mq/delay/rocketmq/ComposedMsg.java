package xyz.mydev.mq.delay.rocketmq;

import lombok.Getter;
import org.apache.rocketmq.common.message.MessageExt;
import xyz.mydev.mq.delay.dto.IDelayTagMessage;

import java.util.Objects;

/**
 * @author ZSP
 */
@Getter
public class ComposedMsg {
  private MessageExt messageExt;
  private IDelayTagMessage businessMsg;

  public ComposedMsg(MessageExt messageExt, IDelayTagMessage businessMsg) {
    this.messageExt = messageExt;
    this.businessMsg = businessMsg;
  }

  public static ComposedMsg of(MessageExt messageExt, IDelayTagMessage iDelayTagMessage) {
    Objects.requireNonNull(messageExt);
    Objects.requireNonNull(iDelayTagMessage);
    return new ComposedMsg(messageExt, iDelayTagMessage);
  }
}