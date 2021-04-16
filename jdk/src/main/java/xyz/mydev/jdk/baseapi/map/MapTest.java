package xyz.mydev.jdk.baseapi.map;

import org.junit.Test;
import xyz.mydev.jdk.bean.Child;
import xyz.mydev.jdk.bean.Parent;

import java.util.HashMap;

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

}
