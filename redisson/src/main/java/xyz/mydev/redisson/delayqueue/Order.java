package xyz.mydev.redisson.delayqueue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ZSP
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Delayed {
  private transient static AtomicInteger counter = new AtomicInteger();

  private String id;
  private String name;
  @NotNull
  private LocalDateTime invalidTime;

  @Override
  public long getDelay(TimeUnit unit) {
    long delay = unit.convert(Duration.between(LocalDateTime.now(), invalidTime));
    return delay > 0 ? delay : 0;
  }

  @Override
  public int compareTo(Delayed o) {
    return 0;
  }

  public static Order ofSeconds(long seconds) {
    return Order.builder()
      .id("id" + counter.incrementAndGet())
      .name("name" + counter.get())
      .invalidTime(LocalDateTime.now().plusSeconds(seconds))
      .build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Order order = (Order) o;
    return getId().equals(order.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }
}
