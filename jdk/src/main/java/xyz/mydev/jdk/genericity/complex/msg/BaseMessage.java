package xyz.mydev.jdk.genericity.complex.msg;

public interface BaseMessage<T> {

  String getId();

  T getPayload();

  String getTargetTableName();
}
