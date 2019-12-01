package xyz.mydev.jdk.stream;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZSP  2019/08/28 14:50
 */
public class MapToString {
  public static void main(String[] args) {
    Map<String, String> map = new HashMap<>(4);
    map.put("msg1", "msg1");
    map.put("msg2", "msg2");
    map.put("msg3", "msg3");
    map.put("msg4", "msg4");

    StringBuilder builder = new StringBuilder();
    map.forEach((k, v) -> builder.append(k).append(v).append(","));

    System.out.println(builder.toString());


    StringBuilder collect = map.entrySet().stream().collect(StringBuilder::new, (container, e) -> container.append(e.getKey()).append(e.getValue()).append(","), StringBuilder::append);
    collect.deleteCharAt(collect.length() - 1);
    System.out.println(collect.toString());



  }

}
