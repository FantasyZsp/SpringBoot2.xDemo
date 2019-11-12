package xyz.mydev.juc;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author ZSP  2019/05/27 08:54
 */
@Slf4j
public class DateFormatDemo {
  // 请求总数
  private final static int CLIENT_TOTAL = 500;
  // 并发数
  private final static int CONCURRENT_TOTAL = 20;

  public static LocalDateTime localDateTime = LocalDateTime.of(2019, 5, 20, 12, 0);
  public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
  public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public static void main(String[] args) throws InterruptedException {
    ExecutorService executorService = Executors.newCachedThreadPool();
    final Semaphore semaphore = new Semaphore(CONCURRENT_TOTAL);
    final CountDownLatch countDownLatch = new CountDownLatch(CLIENT_TOTAL);

    for (int i = 0; i < CLIENT_TOTAL; i++) {
      executorService.execute(() -> {
        try {
          semaphore.acquire();
          parse();
          semaphore.release();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        countDownLatch.countDown();
      });
    }
    countDownLatch.await();
    executorService.shutdown();
  }

  public static void parse() {
    String demo = "2019-05-20 12:00:00";

    System.out.println(formatter.parse(demo));
    System.out.println(localDateTime.format(formatter));

//        try {
//            simpleDateFormat.add(demo);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
  }

}
