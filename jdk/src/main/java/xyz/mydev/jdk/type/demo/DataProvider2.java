package xyz.mydev.jdk.type.demo;

import java.util.List;

/**
 * @author ZSP
 */
public class DataProvider2 implements DataProvider<Double, String> {

  @Override
  public List<String> getData(Double yourParameters) {
    return List.of("string");
  }

  @Override
  public String getMarkId() {
    return "DataProvider2";
  }
}
