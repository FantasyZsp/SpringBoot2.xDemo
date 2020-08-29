package xyz.mydev.mq.delay;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.mydev.mq.delay.rpc.RpcMqService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author ZSP
 */
@Service
@AllArgsConstructor
public class MqProducerImpl implements RpcMqService {

  private final DelayMsgSender delayMsgSender;


  @Override
  public void sendDelayMessage(@NotBlank String channel, String tag, String businessId, @NotBlank String messageBody, @NotNull LocalDateTime time) {
    delayMsgSender.sendDelayMessage(channel, tag, businessId, messageBody, time);
  }

}
