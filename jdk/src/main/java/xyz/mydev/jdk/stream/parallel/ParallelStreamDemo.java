package xyz.mydev.jdk.stream.parallel;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author ZSP
 */
@Slf4j
public class ParallelStreamDemo {

  public static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(4);

  public static void main(String[] args) {
    List<String> numberList = IntStream.rangeClosed(1, 100).boxed().map(String::valueOf).collect(Collectors.toList());
//    testParallelStream(numberList);

    supplyAsync(numberList, EXECUTOR_SERVICE);

    EXECUTOR_SERVICE.shutdown();
  }

  public static void testParallelStream(List<String> numberList) {
    numberList.parallelStream().forEach(num -> {
      try {
        Thread.currentThread().join(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      log.info("num : {}", num);
    });
  }

  public static void supplyAsync(List<String> numberList, ExecutorService executorService) {
    Vector<String> numbersExecuted = new Vector<>(numberList.size());
    Vector<String> numbersErrors = new Vector<>(numberList.size());
    CompletableFuture<String> error = null;

    for (String number : numberList) {
      CompletableFuture<String> async = CompletableFuture.supplyAsync(() -> processDoubleNumber(number), executorService);
      async
//        .handle((r, t) -> {
//          return "-1";
//        })
        .whenComplete((v, e) -> {

          if (e != null) {
            log.info("number : {} finish but error ", number);
            numbersErrors.add(number);
            throw new RuntimeException(e);
          } else {
//            log.info("number : {} finish no error ", number);
            numbersExecuted.add(v);
          }
        });

      if (number.equals("22")) {
        error = async;
      }
    }


    log.info("this code will not be blocked by CompletableFuture");
    // TODO 1.怎么不阻塞在当前，而是等待所有任务执行完毕，自动执行后面的代码
    // TODO 2.如果一个任务出现异常了，对整体任务的影响是怎样的

    while (numbersExecuted.size() + numbersErrors.size() != numberList.size()) {
      join(100);
    }
    log.info("执行结果: {}", numbersExecuted);
    log.info("错误结果: {}", numbersErrors);
    System.out.println(error.join());
  }


  public static String doubleNumber(Number number) {
    return String.valueOf(number.intValue() * 2);
  }

  public static String doubleNumber(String number) {
    return doubleNumber(Integer.parseInt(number));
  }

  public static String processDoubleNumber(String number) {
    try {
//      log.info("wait for number: {} a moment...",number);
      Thread.currentThread().join(100);
      log.info("go to process number: {}...", number);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    if (number.contains("22")) {
      throw new IllegalArgumentException("参数错误");
    }

    return doubleNumber(Integer.parseInt(number));
  }

  public static void join(long millis) {
    try {
      Thread.currentThread().join(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
