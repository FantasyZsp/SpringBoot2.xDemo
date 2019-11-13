package xyz.mydev.juc.completablefuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author ZSP
 */
@Slf4j
public class SimpleExceptionDemo {
  public static void main(String[] args) {
    CompletableFuture<String> f0 =
      CompletableFuture.supplyAsync(
        () -> {
          log.info("first");
          return "Hello World";
        })
        .thenApply(s -> {
            log.info("second");
            return s + " QQ";
          }
        )
        .thenApply(s -> {
          log.info("third");
          int i = 7 / 0;


          return s.toUpperCase();
        }).exceptionally(t -> {
        log.info("exceptionally");
        return "exceptionally";
      });

    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
//    System.out.println(f0.join());
  }


}
