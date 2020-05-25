package xyz.mydev.jdk.bean;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author ZSP
 */
@SuppressWarnings("all")
public interface IDelayMessage<T> extends Delayed {

  T getPayload();

  LocalDateTime getTime();

  @Override
  default long getDelay(TimeUnit unit) {
    long delay = unit.convert(Duration.between(LocalDateTime.now(), getTime()));
    return delay > 0 ? delay : 0;
  }

  @Override
  default int compareTo(Delayed o) {
    return (int) (getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
  }

  default String getPayloadClassName() {
    return getPayload().getClass().getName();
  }

  default String getClassName() {
    return getClass().getName();
  }
}