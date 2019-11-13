package com.thread.pool;

import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import xyz.mydev.juc.executor.Result;
import xyz.mydev.juc.executor.Task;
import xyz.mydev.juc.tea.TeaExceptionTask;
import xyz.mydev.juc.tea.TeaTask;
import xyz.mydev.juc.tea.WaterTask;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * ExecutorService
 *
 * @author ZSP
 */
@Slf4j
public class ExecutorTest {
  private Object tempData = new Object();
  private Object tempData2 = new Object();

  /**
   * <T> Future<T> submit(Runnable task, T result)方法可以在主子线程间传递数据
   */
  @Test
  public void testFuture() throws ExecutionException, InterruptedException {
    ExecutorService executor = Executors.newFixedThreadPool(1);
    Result result = new Result();
    result.setMsg("msg");
    result.setCode(200);
    result.setData(tempData);

    Future<Result> future = executor.submit(new Task(result, tempData2), result);
    Result fr = future.get();

    Assert.assertSame(fr, result);
    Assert.assertSame(fr.getData(), tempData2);
    Assert.assertSame(fr.getMsg(), "Task Msg");
  }

  @Test
  public void testTea() throws ExecutionException, InterruptedException {
    FutureTask<String> teaTask = new FutureTask<>(new TeaTask());
    FutureTask<String> waterTask = new FutureTask<>(new WaterTask(teaTask));

//    ExecutorService executor = Executors.newFixedThreadPool(2);
//    executor.submit(teaTask);
//    executor.submit(waterTask);
    new Thread(teaTask, "teaTask").start();
    new Thread(waterTask, "waterTask").start();

    log.info("什么茶?=====================>{}", teaTask.get());

    Assert.assertThat(teaTask.get(), Matchers.notNullValue());
  }

  @Test
  public void testTeaException() throws ExecutionException, InterruptedException {
    FutureTask<String> teaTask = new FutureTask<>(new TeaExceptionTask());
    FutureTask<String> waterTask = new FutureTask<>(new WaterTask(teaTask));

//    ExecutorService executor = Executors.newFixedThreadPool(2);
//    executor.submit(teaTask);
//    executor.submit(waterTask);
    new Thread(teaTask, "teaTask").start();
    new Thread(waterTask, "waterTask").start();


    TimeUnit.SECONDS.sleep(5);
    System.out.println(teaTask.isDone());
    System.out.println(teaTask.isCancelled());
    System.out.println(waterTask.isDone());
    System.out.println(waterTask.isCancelled());
    TimeUnit.SECONDS.sleep(5);
    waterTask.get();

//    log.info("什么茶?===========>{}", waterTask.get());

//    Assert.assertThat(teaTask.get(), Matchers.notNullValue());
  }


  @Test
  public void testTeaException2() {
    // 任务 1：洗水壶 -> 烧开水
    CompletableFuture<Void> f1 =
      CompletableFuture.runAsync(() -> {
        System.out.println("T1: 洗水壶...");
        sleep(1, TimeUnit.SECONDS);

        System.out.println("T1: 烧开水...");
        sleep(15, TimeUnit.SECONDS);
      });
    // 任务 2：洗茶壶 -> 洗茶杯 -> 拿茶叶
    CompletableFuture<String> f2 =
      CompletableFuture.supplyAsync(() -> {
        System.out.println("T2: 洗茶壶...");
        sleep(1, TimeUnit.SECONDS);

        System.out.println("T2: 洗茶杯...");
        sleep(2, TimeUnit.SECONDS);

        System.out.println("T2: 拿茶叶...");
        sleep(1, TimeUnit.SECONDS);
        return " 龙井 ";
      });
    // 任务 3：任务 1 和任务 2 完成后执行：泡茶
    CompletableFuture<String> f3 =
      f1.thenCombine(f2, (__, tf) -> {
        System.out.println("T1: 拿到茶叶:" + tf);
        System.out.println("T1: 泡茶...");
        return " 上茶:" + tf;
      });
    // 等待任务 3 执行结果
    System.out.println(f3.join());
  }

  void sleep(int t, TimeUnit u) {
    try {
      u.sleep(t);
    } catch (InterruptedException e) {
    }
  }

}
