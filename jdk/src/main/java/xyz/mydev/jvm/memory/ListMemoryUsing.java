package xyz.mydev.jvm.memory;

import org.apache.commons.lang3.RandomStringUtils;
import xyz.mydev.common.utils.ThreadUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZSP
 */
public class ListMemoryUsing {

  public static void main(String[] args) {
    List<String> list = new ArrayList<>();

    for (int i = 0; i < 10000; i++) {
      list.add(RandomStringUtils.random(20));
    }
    System.out.println("finish init");


    List<List<?>> listList = new ArrayList<>(10000);
    for (int i = 0; i < 10; i++) {
      List<String> list2 = new ArrayList<>(list);
      listList.add(list2);
    }
    System.out.println(listList.size());

    ThreadUtils.sleepSeconds(20000);


  }

}
