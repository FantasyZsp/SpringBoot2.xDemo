package xyz.mydev.juc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureTaskDemo {


  public static void main(String[] args) throws ExecutionException, InterruptedException {

    FutureTask<String> stringFutureTask = new FutureTask<>(() -> "Hello FutureTask");

    ExecutorService executorService = Executors.newFixedThreadPool(1);

    executorService.submit(stringFutureTask);

    String s = stringFutureTask.get();

    System.out.println(s);

    executorService.shutdown();


  }

}
