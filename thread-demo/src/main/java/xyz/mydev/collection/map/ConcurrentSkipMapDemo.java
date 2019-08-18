package xyz.mydev.collection.map;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.IntStream;

/**
 * @author ZSP  2019/08/13 14:22
 */
@Slf4j
public class ConcurrentSkipMapDemo {

  private static ConcurrentSkipListMap<String, String> skipListMap = new ConcurrentSkipListMap<>();
  private static ConcurrentHashMap<String, String> hashMap = new ConcurrentHashMap<>();

  public static void main(String[] args) {

    for (int i = 0; i < 10; i++) {
      initHashMap2(1000_0000);
      initSkipListMap2(1000_0000);
    }


  }

  private static void initSkipListMap(int capacity) {
    long start = System.currentTimeMillis();
    IntStream.rangeClosed(1, capacity)
      .parallel()
      .mapToObj(String::valueOf)
      .forEach(str -> skipListMap.put(str, str));
    log.info("初始化skipListMap完毕: {}", System.currentTimeMillis() - start);
    skipListMap.clear();
  }

  private static void initSkipListMap2(int capacity) {
    long start = System.currentTimeMillis();
    for (int i = 0; i < capacity; i++) {
      String s = String.valueOf(i);
      skipListMap.put(s, s);
    }
    log.info("初始化skipListMap完毕: {}", System.currentTimeMillis() - start);
    skipListMap.clear();
  }

  private static void initHashMap(int capacity) {
    long start = System.currentTimeMillis();
    IntStream.rangeClosed(1, capacity)
      .parallel()
      .mapToObj(String::valueOf)
      .forEach(str -> hashMap.put(str, str));
    log.info("初始化hashMap完毕: {}", System.currentTimeMillis() - start);
    hashMap.clear();
  }

  private static void initHashMap2(int capacity) {
    long start = System.currentTimeMillis();
    for (int i = 0; i < capacity; i++) {
      String s = String.valueOf(i);
      hashMap.put(s, s);
    }
    log.info("初始化hashMap完毕: {}", System.currentTimeMillis() - start);
    hashMap.clear();
  }
}
