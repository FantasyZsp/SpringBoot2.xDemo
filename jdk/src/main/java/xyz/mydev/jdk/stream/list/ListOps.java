package xyz.mydev.jdk.stream.list;

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
  }
}
