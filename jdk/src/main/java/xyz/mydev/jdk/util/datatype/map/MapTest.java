package xyz.mydev.jdk.util.datatype.map;

import java.util.HashMap;

/**
 * @author ZSP
 */
public class MapTest {

  public static void main(String[] args) {


    HashMap<Object, Object> map = new HashMap<>();
    map.put(1, 1);
    Object o = map.putIfAbsent(1, 2);
    System.out.println(o);
    System.out.println(map.get(1));
  }

}
