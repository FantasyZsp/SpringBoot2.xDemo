package xyz.mydev.jdk.genericity.complex.route;

import xyz.mydev.jdk.genericity.complex.msg.SerializableMessage;
import xyz.mydev.jdk.genericity.complex.port.Porter;

import java.io.Serializable;

public interface PorterRouter extends Router<String, Porter<? super SerializableMessage<? extends Serializable>>> {

  default <E extends SerializableMessage<? extends Serializable>> Porter<? super SerializableMessage<? extends Serializable>> resolveByMessage(E msg) {
    return get(msg.getTargetTableName());
  }

  default void transfer(SerializableMessage<? extends Serializable> msg) {
    Porter<? super SerializableMessage<? extends Serializable>> porter = resolveByMessage(msg);
    porter.transfer(msg);
  }

  @Override
  default Porter<? super SerializableMessage<? extends Serializable>> get(String key) {
    return null;
  }
}