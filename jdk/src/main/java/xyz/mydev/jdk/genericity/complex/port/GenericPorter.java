package xyz.mydev.jdk.genericity.complex.port;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import xyz.mydev.jdk.genericity.complex.msg.SerializableMessage;

import java.io.Serializable;

@Slf4j
@Getter
@Setter
public class GenericPorter<T extends SerializableMessage<? extends Serializable>> extends AbstractPorter<T> {

  @Override
  public void transfer(T t) {

  }

  @Override
  public void port(T t) {

  }

  @Override
  public T fetch(T t) {
    return null;
  }
}