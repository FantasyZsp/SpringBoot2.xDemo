package xyz.mydev.jdk.type.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZSP
 */
public class Test {

  public static void main(String[] args) {

    List<DataProvider<?, ?>> list = new ArrayList<>();
    Map<String, DataProvider> map = new HashMap<>();


    List<DataProvider<?, ?>> list2 = new ArrayList<>();

    list.add(new DataProvider1());
    list.add(new DataProvider2());

    for (DataProvider<?, ?> dataProvider : list) {
      list2.add(dataProvider);
      map.put(dataProvider.getMarkId(), dataProvider);
    }


    DataProvider dataProvider1 = map.get("DataProvider1");
    List data = dataProvider1.getData("1");
    System.out.println(data);
  }

}
