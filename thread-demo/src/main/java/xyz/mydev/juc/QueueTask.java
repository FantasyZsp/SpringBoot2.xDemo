package xyz.mydev.juc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author ZSP
 * 排队任务: 控制好抢锁后对队列的操作，即放在synchronized代码块中。作业本身可以并发。
 */
public class QueueTask {
  private final static LinkedList<Thread> CONTROLS = new LinkedList();
  private static final Object LOCK = new Object();
  private final static int MAX = 5;

  // 队列中是抢到资源正在作业的线程。最大5个,当有一个线程作业完毕后,剔除先到的。即符合FIFO原则。
  private static final List<Thread> QUEUE = new ArrayList<>();


  public static void main(String[] args) {
    Stream.of("M1", "M2", "M3", "M4", "M5", "M6", "M7", "M8", "M9")
      .map(CreateCaptureThread::create).forEach(t -> {
        QUEUE.add(t);
        t.start();
      }
    );

    QUEUE.forEach(t -> {
      try {
        t.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    System.out.println("finished...");
  }


  @SuppressWarnings("all")
  private static class CreateCaptureThread {
    static Thread create(String name) {
      return new Thread(() -> {
        // 线程抢锁
        synchronized (LOCK) {
          while (CONTROLS.size() > MAX) {
            try {
              LOCK.wait();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }

          }
          // 抢到后进入队列
          System.out.println(Thread.currentThread().getName() + "=>>>抢到了锁,进入作业队尾..." + CONTROLS.size());
          CONTROLS.addLast(Thread.currentThread());
        }

        // 并行执行作业
        System.out.println(Thread.currentThread().getName() + "=>>> is working ....");
        try {
          if (CONTROLS.size() == 1) {
            Thread.sleep(5000L);
          }
          Thread.sleep(1000L);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        synchronized (LOCK) {
          System.out.println(Thread.currentThread().getName() + "=>>>作业完毕,剔除队首..." + CONTROLS.getFirst().getName());
          CONTROLS.removeFirst();
          LOCK.notifyAll();
        }
      }, name);
    }
  }
}


