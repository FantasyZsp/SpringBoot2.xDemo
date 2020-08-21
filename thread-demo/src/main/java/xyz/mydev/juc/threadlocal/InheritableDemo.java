package xyz.mydev.juc.threadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 子线程在结束某个任务后没有清空ThreadLocal,在后续的任务中直接复用了自己修改过的缓存值。
 * 主线程在创建子线程后，虽然将缓存值赋给了子线程，但是本质上，缓存还是和线程对应的，只是引用进行了传递，如果子线程整体替换了引用，那么实际上父子引用的缓存就是独立的，不会存在任何影响。
 * 如果子线程没有替换引用，而是拿出引用后，修改了引用中内部值，如集合容器中元素，那么父线程中的缓存也是会被修改。究其原因，是在拷贝缓存时，用的是浅拷贝。
 * <p>
 * 可能的关注点：
 * 在线程池运作模式下，每个任务用到的上下文在子线程中被修改后，在后续的任务执行中无法使用父线程中的上下文。如果每次任务都需要使用父线程中的上下文，那么需要用到阿里封装的线程池和上下文实现。 TtlRunnable
 *
 * @author ZSP
 */
@Slf4j
public class InheritableDemo {

  static ExecutorService GLOBAL_EXECUTOR = Executors.newCachedThreadPool();
  static AtomicLong atomicLong = new AtomicLong();


  public static void main(String[] args) throws Exception {
//    testUpdate();
    testAliBaBaLocal();

    GLOBAL_EXECUTOR.shutdown();

  }


  @Test
  public void testReplace() throws Exception {
    final InheritableThreadLocal<Text> inheritableThreadLocal = new InheritableThreadLocal<>();
    inheritableThreadLocal.set(new Text("from main Thread"));

    System.out.println(inheritableThreadLocal.get());

    Runnable runnable = () -> {
      System.out.println("====runnable====");
      System.out.println("print by sub: " + inheritableThreadLocal.get());
      inheritableThreadLocal.set(new Text("set by Sub Thread"));
      System.out.println("print by sub: " + inheritableThreadLocal.get());
    };

    ExecutorService executorService = Executors.newFixedThreadPool(1);
    executorService.submit(runnable);
    TimeUnit.SECONDS.sleep(1);
    executorService.submit(runnable);
    TimeUnit.SECONDS.sleep(1);
    System.out.println("========");
    System.out.println("print by main : " + inheritableThreadLocal.get());

    executorService.shutdown();
  }

  public static void testUpdate() throws Exception {
    final InheritableThreadLocal<Text> inheritableThreadLocal = new InheritableThreadLocal<>();
    inheritableThreadLocal.set(new Text("from main Thread"));
    Text text = inheritableThreadLocal.get();
    System.out.println(text);

    Runnable runnable = () -> {
      System.out.println("print by sub: " + inheritableThreadLocal.get());
      System.out.println("====update by sub====");
      inheritableThreadLocal.get().setName("update by sub");
      System.out.println("print by sub: " + inheritableThreadLocal.get());
    };

    ExecutorService executorService = Executors.newFixedThreadPool(1);
    executorService.submit(runnable);
    TimeUnit.SECONDS.sleep(1);
    executorService.submit(runnable);
    TimeUnit.SECONDS.sleep(1);
    System.out.println("========");
    System.out.println("print by main : " + inheritableThreadLocal.get());

    executorService.shutdown();
  }

  @SuppressWarnings("Duplicates")
  public static void testAliBaBaLocal() throws Exception {
    TransmittableThreadLocal<Text> local = new TransmittableThreadLocal<>();

    local.set(new Text("test"));

    Runnable runnable = () -> {
      log.info("get before update: " + local.get());
      local.set(new Text("child" + atomicLong.incrementAndGet()));
      log.info("get after update: " + local.get());

      local.remove();
    };

    Runnable runnable2 = () -> {
      log.info("get before update: " + local.get());
      local.get().setName("child");
      log.info("get after update: " + local.get());

//      local.remove();
    };

    TtlRunnable ttlRunnable = TtlRunnable.get(runnable2);

    assert ttlRunnable != null;
    GLOBAL_EXECUTOR.submit(ttlRunnable);
    GLOBAL_EXECUTOR.submit(ttlRunnable);
    GLOBAL_EXECUTOR.submit(ttlRunnable);
    GLOBAL_EXECUTOR.submit(runnable);
    TimeUnit.SECONDS.sleep(1);
    GLOBAL_EXECUTOR.submit(runnable2);
    GLOBAL_EXECUTOR.submit(runnable);
    GLOBAL_EXECUTOR.submit(runnable);
    TimeUnit.SECONDS.sleep(1);
    GLOBAL_EXECUTOR.submit(ttlRunnable);
    GLOBAL_EXECUTOR.submit(runnable2);
    GLOBAL_EXECUTOR.submit(ttlRunnable);

    System.out.println("========");
    System.out.println("print by main : " + local.get());

  }
}
