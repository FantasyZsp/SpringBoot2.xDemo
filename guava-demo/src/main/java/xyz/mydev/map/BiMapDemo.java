package xyz.mydev.map;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import xyz.mydev.util.JsonUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * @author ZSP
 */
public class BiMapDemo {
  public static void main(String[] args) {
    retainAll();
  }


  private static BiMap<String, String> init() {
    Map<String, String> map = new HashMap<>();
    map.put("1", "first");
    map.put("2", "second");
    map.put("3", "3rd");
    map.put("4", "testSame");
    map.put("5", "testSame2");
    map.put("6", "testSame3");


    BiMap<String, String> biMap = HashBiMap.create(map);
    System.out.println("=====init=======");
    System.out.println(JsonUtil.obj2StringPretty(biMap));
    System.out.println(JsonUtil.obj2StringPretty(biMap.inverse()));
    System.out.println(JsonUtil.obj2StringPretty(biMap));
    return biMap;
  }

  private static void test() {
    BiMap<String, String> biMap = init();
    System.out.println("=====force put same value=======");
    biMap.forcePut("7", "testSame");
    System.out.println(JsonUtil.obj2StringPretty(biMap));
    System.out.println(JsonUtil.obj2StringPretty(biMap.inverse()));
    System.out.println(JsonUtil.obj2StringPretty(biMap));

    System.out.println("=====map not contains list item=======");
    List<String> descriptionList = List.of("not1", "notat2", "notat3", "first", "second");

    System.out.println("=====operate inverse=======");
    BiMap<String, String> inverse = biMap.inverse();
    System.out.println("**retainAll list**");
    inverse.keySet().retainAll(descriptionList);
    System.out.println(inverse.values());
    System.out.println(JsonUtil.obj2StringPretty(inverse));
    System.out.println(JsonUtil.obj2StringPretty(biMap));
    System.out.println(JsonUtil.obj2StringPretty(biMap.inverse()));


    System.out.println("=====operate biMap=======");
    biMap.values().retainAll(Collections.singleton("second"));
    System.out.println(JsonUtil.obj2StringPretty(biMap));
  }

  private static void test2() {
    BiMap<String, String> biMap = init();

    System.out.println("=====map not contains list item=======");
    List<String> descriptionList = List.of("not1", "notat2", "notat3");
    List<String> descriptionsContainsNull = descriptionList.stream().map(biMap.inverse()::get).collect(toList());
    System.out.println(JsonUtil.obj2StringPretty(descriptionsContainsNull));


    System.out.println("**retainAll list with nulls**");
    System.out.println(biMap.keySet().retainAll(descriptionsContainsNull));
    System.out.println(JsonUtil.obj2StringPretty(biMap));
    System.out.println("**retainAll list with nulls end**");


  }

  private static void test3() {
    BiMap<String, String> biMap = init();
    List<String> descriptionList = List.of("not1", "notat2", "notat3", "first", "second");
    List<String> descriptionsSkipNull = descriptionList.stream().map(biMap.inverse()::get).collect(
      ArrayList::new,
      (accumulator, t) -> {
        if (t != null) {
          accumulator.add(t);
        }
      },
      ArrayList::addAll
    );
    System.out.println(JsonUtil.obj2StringPretty(descriptionsSkipNull));


    System.out.println("**retainAll list with nulls2**");
    biMap.keySet().retainAll(descriptionsSkipNull);
    System.out.println(JsonUtil.obj2StringPretty(biMap));
    System.out.println("**retainAll list with nulls end2**");

  }

  private static void retainAll() {
    List<String> list = new ArrayList<>();
    list.add("111");
    list.add("222");
    list.add("333");
    list.add("11441");

    list.retainAll(null);
    System.out.println(list);
  }
}
