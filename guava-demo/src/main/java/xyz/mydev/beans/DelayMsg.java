package xyz.mydev.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author ZSP
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DelayMsg implements Delayed {
  private String id;
  private LocalDateTime time;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DelayMsg delayMsg = (DelayMsg) o;
    return id.equals(delayMsg.id) &&
      time.equals(delayMsg.time);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, time);
  }

  @Override
  public long getDelay(TimeUnit unit) {
    long delay = unit.convert(Duration.between(LocalDateTime.now(), getTime()));
    return delay > 0 ? delay : 0;
  }

  @Override
  public int compareTo(Delayed o) {
    return getTime().compareTo(((DelayMsg) o).getTime());
  }
}
