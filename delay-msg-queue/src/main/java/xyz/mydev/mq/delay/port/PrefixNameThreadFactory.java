package xyz.mydev.mq.delay.port;

import org.springframework.util.Assert;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ZSP
 */
public class PrefixNameThreadFactory implements ThreadFactory {
  private final String prefix;

  public PrefixNameThreadFactory(String prefix) {
    Assert.hasText(prefix, "prefix not be blank");
    this.prefix = prefix;
  }

  private final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);

  @Override
  public Thread newThread(Runnable r) {
    return new Thread(r, prefix + "-" + ATOMIC_INTEGER.getAndIncrement());
  }
}