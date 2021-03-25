package xyz.mydev.jdk.genericity.complex.port;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import xyz.mydev.jdk.genericity.complex.msg.SerializableMessage;

import java.io.Serializable;

@Slf4j
@Getter
@Setter
public class DefaultPorter extends AbstractPorter<SerializableMessage<? extends Serializable>> {

  @Override
  public void transfer(SerializableMessage<? extends Serializable> serializableMessage) {

  }

  @Override
  public void port(SerializableMessage<? extends Serializable> serializableMessage) {

  }
}