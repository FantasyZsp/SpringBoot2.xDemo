package xyz.mydev.jdk.bean;

public interface TempInterface {

  public default String getData() {
    return "test";
  }
}
