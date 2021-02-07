package xyz.mydev.jdk.type.demo;

import java.util.List;

/**
 * @author ZSP
 */
public class DataProvider1 implements DataProvider<String, Integer> {

  @Override
  public List<Integer> getData(String yourParameters) {
    return List.of(1);
  }

  @Override
  public String getMarkId() {
    return "DataProvider1";
  }
}
