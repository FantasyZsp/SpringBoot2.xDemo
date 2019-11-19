package xyz.mydev.datastructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
}
