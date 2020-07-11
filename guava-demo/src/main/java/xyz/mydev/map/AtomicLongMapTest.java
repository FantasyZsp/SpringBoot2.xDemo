package xyz.mydev.map;

import com.google.common.util.concurrent.AtomicLongMap;

/**
 * @author ZSP
 */
public class AtomicLongMapTest {
  public static void main(String[] args) {
    AtomicLongMap<String> map = AtomicLongMap.create();

    long l = map.get("1");
    System.out.println(l);


    System.out.println(map.getAndIncrement("2"));
    System.out.println(map.incrementAndGet("3"));

  }
}
