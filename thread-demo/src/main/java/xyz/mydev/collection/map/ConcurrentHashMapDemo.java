package xyz.mydev.collection.map;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ZSP
 */
public class ConcurrentHashMapDemo {
  public static void main(String[] args) {

    ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
    String key = "key";
    concurrentHashMap.putIfAbsent(key, "vv");

    System.out.println(concurrentHashMap);

  }
}
