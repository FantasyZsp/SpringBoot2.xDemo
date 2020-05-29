package xyz.mydev.jdk.stream.list;

import com.google.common.collect.Lists;
import xyz.mydev.jdk.stream.bean.VesselContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author ZSP
 */
public class ListOps {

  public static void main(String[] args) {
    List<VesselContainer> list = new ArrayList<>();
    Map<Integer, VesselContainer> collect = list.stream().collect(Collectors.toMap(VesselContainer::getId, Function.identity()));
    System.out.println(collect);
    AtomicLong atomicLong = new AtomicLong();
    long andIncrement = atomicLong.getAndIncrement();
  }

  public static void test() {
    List<String> list = Collections.emptyList();

    list.sort(null);
    Collections.sort(list);

//    List<Integer> integers = List.of(1, 2, 3);
//    integers.sort(Integer::compareTo);

    ArrayList<Integer> integers1 = Lists.newArrayList(1, 2, 3);
    integers1.sort(Integer::compareTo);
  }


}
