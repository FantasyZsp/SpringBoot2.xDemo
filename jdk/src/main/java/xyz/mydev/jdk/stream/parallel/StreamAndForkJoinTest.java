package xyz.mydev.jdk.stream.parallel;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZSP
 */
@Slf4j
public class StreamAndForkJoinTest {

  public static void main(String[] args) throws InterruptedException {
    final List<Integer> list = new ArrayList<>(100);
    for (int i = 0; i < 100; i++) {
      list.add(i);
    }
    for (int i = 1; i <= 20; i++) {
      new Thread("T-" + i) {
        String bossThreadName = this.getName();

        @Override
        public void run() {
          list.parallelStream()
            .forEach(number -> {
              log.info("number: {} ,boss thread: {}", number, bossThreadName);
              try {
                Thread.sleep(10);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            });
        }
      }.start();
    }
    Thread.sleep(Integer.MAX_VALUE);
  }
}