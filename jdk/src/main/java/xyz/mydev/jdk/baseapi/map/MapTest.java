package xyz.mydev.jdk.baseapi.map;

import org.junit.Test;
import xyz.mydev.jdk.bean.Child;
import xyz.mydev.jdk.bean.Parent;

import java.util.*;

/**
 * @author ZSP
 */
public class MapTest {

  @Test
  public void testMap() {

    HashMap<Class<?>, Object> map = new HashMap<>();

    map.put(Child.class, "Child");
    map.put(Parent.class, "Parent");


    Child child = new Child();
    Parent parent = new Parent();

    System.out.println(child.getClass().hashCode());
    System.out.println(parent.getClass().hashCode());
    System.out.println(map.get(child.getClass()));
    System.out.println(map.get(parent.getClass()));


  }

  @Test
  public void testTreeMap() {

    TreeMap<String, String> map = new TreeMap<String, String>();

    map.put("1", "1");
    map.put("2", "2");
    map.put("3", "3");
    map.put("4", "4");
    System.out.println(map);
    TreeMap<Integer, Integer> map2 = new TreeMap<>((o1, o2) -> o1 < o2 ? -1 : 1);

    map2.put(4, 4);
    map2.put(3, 3);
    map2.put(2, 2);
    map2.put(1, 1);
    System.out.println(map2);

    System.out.println(map2.pollLastEntry());
    System.out.println(map2.pollFirstEntry());


  }

  @Test
  public void testSort() {

    List<Integer> integers = new ArrayList<>();
    integers.add(1);
    integers.add(2);
    integers.add(4);
    integers.add(5);
    integers.add(3);
    integers.add(1);
    integers.add(9);
    integers.sort(new Comparator<Integer>() {
      @Override
      public int compare(Integer x, Integer y) {
        return (x < y) ? -1 : ((x == y) ? 0 : 1);
      }
    });
    System.out.println(integers);


  }

}
