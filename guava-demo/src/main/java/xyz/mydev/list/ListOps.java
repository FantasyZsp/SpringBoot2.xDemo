package xyz.mydev.list;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author ZSP
 */
public class ListOps {
  public static void main(String[] args) {
    List<String> temp1 = new ArrayList<>();
    temp1.add("111");
    temp1.add("222");
    temp1.add("333");
    temp1.add("444");

    List<String> temp2 = new ArrayList<>();
    temp2.add("111");
    temp2.add("222");
    temp2.add("333");
    temp2.add("333");
    temp2.add("333");
    temp2.add("333");
    temp2.add("444");

    List<String> temp3 = new ArrayList<>(Set.copyOf(temp2));

    System.out.println(CollectionUtils.subtract(temp3, temp2));
    System.out.println(CollectionUtils.subtract(temp2, temp3));
    System.out.println(CollectionUtils.union(temp3, temp2));
  }
}
