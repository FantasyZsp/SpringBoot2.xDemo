package xyz.mydev.jdk.stream.list;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ZSP
 */
public class ListOps {

  public static void main(String[] args) {
    List<String> list = Collections.emptyList();

    list.sort(null);
    Collections.sort(list);

//    List<Integer> integers = List.of(1, 2, 3);
//    integers.sort(Integer::compareTo);

    ArrayList<Integer> integers1 = Lists.newArrayList(1, 2, 3);
    integers1.sort(Integer::compareTo);
  }


}
