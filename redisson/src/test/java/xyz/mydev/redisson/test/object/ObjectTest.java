package xyz.mydev.redisson.test.object;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.redisson.api.RBucket;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.mydev.redisson.RedissonClientTestApp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @author ZSP
 */
@Slf4j
public class ObjectTest extends RedissonClientTestApp {

  @Autowired
  private ObjectMapper objectMapper;


  @Test
  public void set() {
    RBucket<Integer> test = redissonClient.getBucket("test");
    test.set(1, 30, TimeUnit.SECONDS);

    System.out.println(test.isExists());
    System.out.println(test.get());
  }

  @Test
  public void trySet() {
    RBucket<Integer> trySet = redissonClient.getBucket("trySet");
    System.out.println(trySet.trySet(1, 30, TimeUnit.SECONDS));
    System.out.println(trySet.isExists());
    System.out.println(trySet.get());
  }

  @Test(expected = ClassCastException.class)
  public void readLocalDateTime() {
    RBucket<LocalDateTime> cacheHolder = redissonClient.getBucket("localDateTime");

    LocalDateTime firstValue = LocalDateTime.now();
    cacheHolder.set(firstValue, 30, TimeUnit.SECONDS);
    try {
      LocalDateTime localDateTime = cacheHolder.get();
      System.out.println(localDateTime);

    } catch (ClassCastException ex) {
      log.error("ex: ", ex);
      throw ex;
    }
  }

  /**
   * compareAndSet会将key的过期时间去除
   */
  @Test
  public void localDateTimeWithCompareAndSet() {
    RBucket<String> cacheHolder = redissonClient.getBucket("localDateTimeOfString");

    LocalDateTime firstValue = LocalDateTime.now();
    cacheHolder.set(firstValue.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), 30, TimeUnit.SECONDS);

    String localDateTime = cacheHolder.get();
    System.out.println(localDateTime);


    LocalDateTime secondValue = LocalDateTime.now();
    String secondValueString = secondValue.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    System.out.println(cacheHolder.compareAndSet(localDateTime, secondValueString));
    System.out.println(cacheHolder.get().equals(secondValueString));

  }


}

