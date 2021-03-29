package xyz.mydev.jdk.util.datatype.map;

import org.junit.Test;

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

  @Test
  public void testMapAddNull() {
    HashMap<String, String> map = new HashMap<>();

    map.put("1", null);
    map.put(null, "1");
  }

}
