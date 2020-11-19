package xyz.mydev.jdk.stream.parallel;

import xyz.mydev.common.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author ZSP
 */
public class ConcurrentStreamDemo {
  public static void main(String[] args) {
    List<Integer> numbers = IntStream.rangeClosed(1, 300).boxed().collect(Collectors.toList());


    List<Integer> collect = numbers.parallelStream()
      .filter(number -> number % 1 == 0)
      .collect(() -> {
        ArrayList<Integer> list = new ArrayList<>();
        System.out.println(list.hashCode());
        return list;

      }, (list, t) -> {
        System.out.println(list.hashCode());
        System.out.println(Thread.currentThread().getName());
        list.add(t);

      }, List::addAll);

    System.out.println(JsonUtil.obj2String(collect));
  }
}
