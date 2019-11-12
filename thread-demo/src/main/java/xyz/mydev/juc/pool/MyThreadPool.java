package xyz.mydev.juc.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 简化的线程池，仅用来说明工作原理
 *
 * @author ZSP
 */
@SuppressWarnings("all")
class MyThreadPool {
  BlockingQueue<Runnable> workQueue;
  List<WorkerThread> threads = new ArrayList<>();

  MyThreadPool(int poolSize, BlockingQueue<Runnable> workQueue) {
    this.workQueue = workQueue;
    for (int idx = 0; idx < poolSize; idx++) {
      WorkerThread work = new WorkerThread();
      work.start();
      threads.add(work);
    }
  }

  void execute(Runnable command) {
    try {
      workQueue.put(command);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  class WorkerThread extends Thread {
    @Override
    public void run() {
      while (true) {
        Runnable task = null;
        try {
          task = workQueue.take();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        task.run();
      }
    }
  }

  public static void main(String[] args) {
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(2);
    MyThreadPool pool = new MyThreadPool(10, workQueue);
    pool.execute(() -> {
      System.out.println("hello");
    });
  }
}



