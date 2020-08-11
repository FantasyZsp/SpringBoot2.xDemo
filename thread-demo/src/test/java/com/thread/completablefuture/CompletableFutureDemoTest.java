package com.thread.completablefuture;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import xyz.mydev.common.utils.ThreadUtils;
import xyz.mydev.juc.completablefuture.CompletionServiceDemo;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
public class CompletableFutureDemoTest {
  @Test
  public void testWhenCompletable() throws ExecutionException, InterruptedException {
    ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(5, new CompletionServiceDemo.MyThreadFactory());

    CompletableFuture<Object> future = CompletableFuture.supplyAsync(() -> {
      Object o = new Object();
      log.info(o.toString());
      return o;
    }, executorService).whenCompleteAsync((o, t) -> log.info(o.toString()), executorService);

    log.info(Thread.currentThread().getName());
    ThreadUtils.sleepSeconds(2);
    List<Runnable> runnableList = executorService.shutdownNow();
    log.info("{}", runnableList.size());

    try {
      Thread.currentThread().join(1000);
      System.out.println("finished");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    Object join = future.join();
    Object o = future.get();
    Assert.assertSame(o, join);
    log.info(join.toString());

  }

}