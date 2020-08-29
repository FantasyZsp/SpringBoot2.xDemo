package xyz.mydev.mq.delay.port;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * 消息搬运工
 * 负责延迟消息的投递
 * DB -> Cache -> MQ
 * delayMsgQueue 承载了将消息从加载到缓存中的任务，可能包含有去重机制，尽可能防止重复投递
 * runnableFunction 承载了投递到 MQ 的任务
 * <p>
 * TODO 扩展服务接口，或者包装put操作，将对本地缓存的put操作也投递到线程池。因为当load操作批量投递时，可能会阻塞调度线程或者业务线程。
 *
 * @author ZSP
 */
@Slf4j
@Getter
public abstract class AbstractPorter<E> extends Thread {

  private final DelayMsgQueue<E> delayMsgQueue;
  private Executor executor;
  private final Function<E, Runnable> runnableFunction;

  public AbstractPorter(String name,
                        Function<E, Runnable> runnableFunction,
                        DelayMsgQueue<E> delayMsgQueue) {
    super(name);
    this.runnableFunction = runnableFunction;
    this.delayMsgQueue = delayMsgQueue;
  }


  /**
   * 这里并没有在模板流程里解决掉消费的可靠性，如果需要，子类请覆盖此方法。
   */
  @Override
  public void run() {
    log.info("Porter[{}] start working...", getName());

    try {
      getDelayMsgQueue().start();
    } catch (Throwable ex) {
      log.warn("delayMsgQueue start error, Porter will ignore it and continue working ", ex);
    }

    while (!Thread.currentThread().isInterrupted()) {
      try {
        E task = delayMsgQueue.take();
        log.info("QueueItem is publishing: [{}] ", task);
        executor.execute(runnableFunction.apply(task));
      } catch (InterruptedException e) {
        log.error("InterruptedException occur, shutdown the thread: {}", Thread.currentThread().getName(), e);
        Thread.currentThread().interrupt();
        break;
      }
    }
  }

  public void cancel() {
    interrupt();
    delayMsgQueue.destroy();
  }

  public void put(E e) {
    if (e != null) {
      delayMsgQueue.put(e);
    }
  }

  /**
   * TODO 首先提交一个检测consumingQueue的任务
   */
  public void initExecutor() {
    log.info("init porter executor");
    this.executor = new ThreadPoolExecutor(4, 8, 1, TimeUnit.HOURS,
      new LinkedBlockingQueue<>(2000),
      new PrefixNameThreadFactory("PorterPool"),
      new CallerRunsPolicy(this.delayMsgQueue.getTargetQueue())
    );
    Runtime.getRuntime().addShutdownHook((new Thread(AbstractPorter.this::cancel)));
  }
}