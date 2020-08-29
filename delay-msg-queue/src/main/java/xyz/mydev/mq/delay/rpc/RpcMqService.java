package xyz.mydev.mq.delay.rpc;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public interface RpcMqService {
  void sendDelayMessage(@NotBlank String channel, String tag, String businessId, @NotBlank String messageBody, @NotNull LocalDateTime time);
}
