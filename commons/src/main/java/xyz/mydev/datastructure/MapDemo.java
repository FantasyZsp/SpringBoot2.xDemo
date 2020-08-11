package xyz.mydev.datastructure;

import org.junit.Test;
import xyz.mydev.common.utils.JsonUtil;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author ZSP
 */
public class MapDemo {
  public static void main(String[] args) {
    Map<String, String> map = new HashMap<>();

    map.put("1", null);
    map.put(null, null);
    System.out.println(map);

    List<String> strings = List.of("1", "2", "3", "4", "5");
    strings.stream().collect(Collectors.toMap(Function.identity(), t -> null));
    System.out.println(strings);

  }

  @Test
  public void merge() {

    List<String> numbers = IntStream.rangeClosed(1, 10).boxed().map(String::valueOf).collect(Collectors.toList());
    Map<String, Integer> map = new HashMap<>();

    numbers.add(String.valueOf(ThreadLocalRandom.current().nextInt(10)));
    numbers.add(String.valueOf(ThreadLocalRandom.current().nextInt(10)));
    numbers.add(String.valueOf(ThreadLocalRandom.current().nextInt(10)));
    numbers.add(String.valueOf(ThreadLocalRandom.current().nextInt(10)));
    numbers.add(String.valueOf(ThreadLocalRandom.current().nextInt(10)));

    for (String number : numbers) {
      map.merge(number, 1, Integer::sum);
    }

    System.out.println(JsonUtil.obj2StringPretty(map));
  }


}
