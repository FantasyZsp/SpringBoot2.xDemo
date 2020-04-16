package xyz.mydev.juc.completablefuture.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.StopWatch;
import xyz.mydev.util.ThreadUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author ZSP
 */
@Slf4j
public class CompletableFutureBasicMethodTest {


  public static void main(String[] args) {

  }


  /**
   * whenComplete的执行会等待supplyAsync完成，即阻塞等待
   */
  @Test
  public void supplyWhenComplete() throws ExecutionException, InterruptedException {

    CompletableFuture<Cat> supplyAsync = CompletableFuture.supplyAsync(Cat::defaultCat);


    supplyAsync.whenComplete((cat, e) -> {
      print("whenComplete wait...");
      ThreadUtils.sleepSeconds(10);
      print("whenComplete wake up...");
      print(cat);
    });

    print("invoke...");

    print("supplyAsync get: {}", supplyAsync.get());

    ThreadUtils.sleepSeconds(10);

  }

  /**
   * WhenCompleteAsync是异步执行的，不会阻塞调用线程
   */
  @Test
  public void supplyWhenCompleteAsync() throws ExecutionException, InterruptedException {

    CompletableFuture<Cat> supplyAsync = CompletableFuture.supplyAsync(Cat::defaultCat);

    supplyAsync.whenCompleteAsync((cat, e) -> {
      print("whenCompleteAsync wait...");
      ThreadUtils.sleepSeconds(1);
      print("whenCompleteAsync wake up...");
      print(cat);
    });

    print("invoke...");
    print("supplyAsync get: {}", supplyAsync.get());
    ThreadUtils.sleepSeconds(10);

  }

  /**
   * 当CompletableFuture内部发生异常时，如果没有配置异常处理，只要不获取结果，就不会触发异常
   */
  @Test
  public void exOccursWhenCompleteOrAsync() throws ExecutionException, InterruptedException {

    CompletableFuture<Cat> supplyAsync = CompletableFuture.supplyAsync(() -> {
      Cat cat = Cat.defaultCat();
      throw new RuntimeException(" ex when supplyAsync");
    });

    supplyAsync.whenCompleteAsync((cat, e) -> {
      print("whenCompleteAsync wait...");
      ThreadUtils.sleepSeconds(1);
      print("whenCompleteAsync wake up...");

      print(e.getMessage());
      print(cat);
    });

    print("invoke...");
    ///  print("supplyAsync get: {}", supplyAsync.get());
    ThreadUtils.sleepSeconds(10);

  }

  @Test
  public void exOccursWhenCompleteOrAsync2() throws ExecutionException, InterruptedException {

    CompletableFuture<Cat> supplyAsync = CompletableFuture.supplyAsync(() -> {
      Cat cat = Cat.defaultCat();
      return cat;
//      throw new RuntimeException(" ex when supplyAsync");
    });

    supplyAsync.whenCompleteAsync((cat, e) -> {
      print("whenCompleteAsync wait...");
      ThreadUtils.sleepSeconds(1);
      print("whenCompleteAsync wake up...");

      System.out.println(e);
      if (e != null) {
        print(e.getMessage());
      }

      print(cat);
    });

    print("invoke...");
    ///  print("supplyAsync get: {}", supplyAsync.get());
    ThreadUtils.sleepSeconds(10);

  }


  /**
   * 无异常时会阻塞supplyAsync
   */
  @Test
  public void thenApply() throws ExecutionException, InterruptedException {

    CompletableFuture<Cat> supplyAsync = CompletableFuture.supplyAsync(Cat::defaultCat);

//    CompletableFuture<Cat> thenApply =
    supplyAsync.thenApply(cat -> {
      print("thenApply wait...");
      ThreadUtils.sleepSeconds(2);
      print("thenApply wake up...");
      print(cat);
      return cat.setName("thenApply");
    });

    print("invoke...");

    print("supplyAsync get: {}", supplyAsync.get());
//    print("thenApply get: {}", thenApply.get());

    ThreadUtils.sleepSeconds(10);
  }

  /**
   * 当上游出现异常时，没有任何感知
   * 一旦上游出现异常，thenApply的代码不会执行，看起来像是没有阻塞调用线程
   */
  @Test
  public void thenApplyWhenError() {

    CompletableFuture<Cat> supplyAsync = CompletableFuture.supplyAsync(() -> {
      Cat cat = Cat.defaultCat();
      throw new RuntimeException(" ex when supplyAsync");
    });

    supplyAsync.thenApply(cat -> {
      print("thenApply wait...");
      ThreadUtils.sleepSeconds(1);
      print("thenApply wake up...");
      print(cat);
      return cat.setName("thenApply");
    });

    print("invoke...");

    ThreadUtils.sleepSeconds(10);

  }

  /**
   * 当thenApply出现异常时，只要不获取对应的结果，不会有感知。对应的Future结束
   */
  @Test
  public void thenApplyError() throws ExecutionException, InterruptedException {

    CompletableFuture<Cat> supplyAsync = CompletableFuture.supplyAsync(Cat::defaultCat);

    CompletableFuture<Object> thenApplyEx = supplyAsync.thenApply(cat -> {
      print(supplyAsync.isCompletedExceptionally());
      print(supplyAsync.isDone());
      print("thenApply wait...");
      ThreadUtils.sleepSeconds(1);
      print("thenApply wake up...");
      print(cat);
      throw new RuntimeException("thenApply ex ");
    });

    print("invoke...");
    ThreadUtils.sleepSeconds(2);

    print(thenApplyEx.isCompletedExceptionally());
    print(thenApplyEx.isDone());
    ThreadUtils.sleepSeconds(10);

  }


  @Test
  public void handle() throws ExecutionException, InterruptedException {

    CompletableFuture<Cat> supplyAsync = CompletableFuture.supplyAsync(Cat::defaultCat);

    CompletableFuture<Cat> handle = supplyAsync.handle((cat, e) -> {
      print("handle wait...");
      ThreadUtils.sleepSeconds(1);
      print("handle wake up...");
      return cat.setName("handle");
    });

    print("invoke...");

    print("supplyAsync get: {}", supplyAsync.get());
    print("handle get: {}", handle.get());

    ThreadUtils.sleepSeconds(10);
  }

  /**
   * 上游异常会被记录，传递到入参。如果都不处理异常，对应的任务会结束。
   * 如果不获取结果，异常时不会被感知的(CompletableFuture/Thread体系基本都是如此)
   */
  @Test
  public void handleWhenError() throws ExecutionException, InterruptedException {

    CompletableFuture<Cat> supplyAsync = CompletableFuture.supplyAsync(() -> {
      Cat cat = Cat.defaultCat();
      throw new RuntimeException(" ex when supplyAsync");
    });

    CompletableFuture<Cat> handle = supplyAsync.handle((cat, e) -> {
      print("handle wait...");
      ThreadUtils.sleepSeconds(1);
      print("handle wake up...");
      return cat.setName("handle");
    });

    print("invoke...");

//    print("supplyAsync get: {}", supplyAsync.get());
    print("handle get: {}", handle.get());

    ThreadUtils.sleepSeconds(10);
  }


  @Data
  @Accessors(chain = true)
  @AllArgsConstructor
  static class Cat {
    private String id;
    private String name;


    static Cat defaultCat() {
      return new Cat("default", "default Cat");
    }
  }

  static <T> void print(T t) {
    log.info("object info : {}", t);
  }

  static void print(String msg) {
    log.info(msg);
  }

  static void print(String msg, Object... objects) {
    log.info(msg, objects);
  }

  @Test
  public void stopWatch() {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start("简单测试");
    stopWatch.stop();


    System.out.println("-====");
    System.out.println(stopWatch.prettyPrint());
    System.out.println(stopWatch.getTotalTimeMillis());
    System.out.println(stopWatch.getLastTaskName());
    System.out.println(stopWatch.getLastTaskInfo());
    System.out.println(stopWatch.getTaskCount());

  }

}
