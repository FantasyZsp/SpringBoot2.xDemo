package xyz.mydev.juc.threadlocal;

import lombok.Data;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author ZSP
 */
public class ThreadLocalDemo {
  public static void main(String[] args) throws InterruptedException {

    final InheritableThreadLocal<Span> inheritableThreadLocal = new InheritableThreadLocal<>();
    inheritableThreadLocal.set(new Span("first value"));

    System.out.println(inheritableThreadLocal.get());

    Runnable runnable = () -> {
      System.out.println("====runnable====");
      System.out.println(inheritableThreadLocal.get());
      inheritableThreadLocal.set(new Span("new value"));
      System.out.println(inheritableThreadLocal.get());
    };

    ExecutorService executorService = Executors.newFixedThreadPool(1);
    executorService.submit(runnable);
    TimeUnit.SECONDS.sleep(1);
    executorService.submit(runnable);
    TimeUnit.SECONDS.sleep(1);
    System.out.println("========");
    System.out.println(inheritableThreadLocal.get());

  }

  @Data
  static class Span {
    public String name;
    public int age;

    public Span(String name) {
      this.name = name;
    }
  }
}
