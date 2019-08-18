package xyz.mydev.collection.list;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author ZSP
 */
@Slf4j
public class LinkedListDemo {
  public static void main(String[] args) {

    List<String> init = init(10_0000);

    long arrayListTime = testArrayList3(Lists.newArrayList(init));
    long linkedListTime = testLinkedList3(Lists.newLinkedList(init));
    log.info("for循环LinkedList完毕: {}", linkedListTime);
    log.info("for循环ArrayList: {}", arrayListTime);

  }


  private static <T> long testLinkedList(LinkedList<T> list) {
    long start2 = System.currentTimeMillis();
    for (T s : list) {
      System.out.println(s);
    }
    return System.currentTimeMillis() - start2;

  }


  private static <T> long testArrayList(ArrayList<T> list) {
    long start2 = System.currentTimeMillis();
    for (T s : list) {
      System.out.println(s);
    }
    return System.currentTimeMillis() - start2;
  }

  private static <T> long testArrayList2(ArrayList<T> list) {
    long start2 = System.currentTimeMillis();
    for (int i = 0; i < list.size(); i++) {
      System.out.println(list.get(i));
    }
    return System.currentTimeMillis() - start2;
  }

  private static <T> long testLinkedList2(LinkedList<T> list) {
    long start2 = System.currentTimeMillis();
    for (int i = 0; i < list.size(); i++) {
      System.out.println(list.get(i));
    }
    return System.currentTimeMillis() - start2;

  }


  private static <T> long testArrayList3(ArrayList<T> list) {
    long start2 = System.currentTimeMillis();
    Iterator<T> iterator = list.iterator();
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
    }
    return System.currentTimeMillis() - start2;
  }

  private static <T> long testLinkedList3(LinkedList<T> list) {
    long start2 = System.currentTimeMillis();
    Iterator<T> iterator = list.iterator();
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
    }
    return System.currentTimeMillis() - start2;

  }

  /**
   * 初始化指定容量的字符串List
   *
   * @param capacity 容量
   * @return 返回ArrayList
   */
  private static List<String> init(int capacity) {
    long start = System.currentTimeMillis();
    List<String> list = IntStream.rangeClosed(1, capacity)
//      .parallel()
      .mapToObj(String::valueOf)
      .collect(Collectors.toList());
    log.info("初始化完毕: {}", System.currentTimeMillis() - start);
    return list;
  }
}
