package xyz.mydev.jdk.genericity.complex;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import xyz.mydev.jdk.genericity.complex.msg.StringMessage;
import xyz.mydev.jdk.genericity.complex.port.Porter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ZSP
 */
@Slf4j
@Getter
public class ScheduleTask implements Runnable {

  private final String targetTableName;
  private final Porter<? super StringMessage> porter;
  private final MessageLoader<? extends StringMessage> messageLoader;

  public ScheduleTask(String targetTableName,
                      Porter<? super StringMessage> porter,
                      MessageLoader<? extends StringMessage> messageLoader) {

    this.targetTableName = targetTableName;
    this.porter = porter;
    this.messageLoader = messageLoader;
  }

  @Override
  public void run() {

    LocalDateTime now = LocalDateTime.now();
    LocalDateTime[] localDateTimes = new LocalDateTime[]{now, now};
    LocalDateTime startTime = localDateTimes[0];
    LocalDateTime endTime = localDateTimes[1];

    List<? extends StringMessage> msgListWillSend = load(startTime, endTime);
    transfer(msgListWillSend);

  }


  private void transfer(List<? extends StringMessage> msgListWillSend) {
    for (StringMessage stringMessage : msgListWillSend) {
      porter.transfer(stringMessage);
    }
  }

  private List<? extends StringMessage> load(LocalDateTime startTime,
                                             LocalDateTime endTime) {
    return messageLoader.load(getTargetTableName(), startTime, endTime);
  }


}
