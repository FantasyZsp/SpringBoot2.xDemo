package xyz.mydev.forkjoin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * https://time.geekbang.org/column/article/92524
 *
 * @author ZSP
 */
@Slf4j
public class Demo {

  public static void main(String[] args) {
    int n = 40;
    StopWatch stopWatch = new StopWatch();
    for (int i = 0; i < 10; i++) {
      //创建分治任务线程池
      ForkJoinPool fjp = new ForkJoinPool(4);
      //创建分治任务

      Fibonacci fib = new Fibonacci(n);
      //启动分治任务
      stopWatch.start("启动任务" + i);
      Integer result = fjp.invoke(fib);
      stopWatch.stop();
      //输出结果
      System.out.println(result);


      FibonacciSimple simple = new FibonacciSimple(n);
      stopWatch.start("单线程任务" + i);
      System.out.println(simple.compute());
      stopWatch.stop();
    }


    System.out.println(stopWatch.prettyPrint());
  }

  /**
   * 递归任务
   */
  static class Fibonacci extends RecursiveTask<Integer> {
    final int n;

    Fibonacci(int n) {
      this.n = n;
    }

    @Override
    protected Integer compute() {

      if (n <= 1) {
        return n;
      }
      Fibonacci f1 = new Fibonacci(n - 1);
      //创建子任务
      f1.fork();
      Fibonacci f2 = new Fibonacci(n - 2);
      //等待子任务结果，并合并结果
      return f2.compute() + f1.join();
    }
  }


  static class FibonacciSimple {
    final int n;

    FibonacciSimple(int n) {
      this.n = n;
    }


    public int compute() {
      if (n <= 1) {
        return n;
      }

      FibonacciSimple f1 = new FibonacciSimple(n - 1);
      FibonacciSimple f2 = new FibonacciSimple(n - 2);
      return f1.compute() + f2.compute();
    }

  }
}
