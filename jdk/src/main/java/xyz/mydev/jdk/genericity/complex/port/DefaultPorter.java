package xyz.mydev.jdk.genericity.complex.port;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import xyz.mydev.jdk.genericity.bean.Cat;
import xyz.mydev.jdk.genericity.complex.msg.DelayMessage;
import xyz.mydev.jdk.genericity.complex.msg.SerializableMessage;
import xyz.mydev.jdk.genericity.complex.msg.TxMessage;

import java.io.Serializable;

@Slf4j
@Getter
@Setter
public class DefaultPorter extends AbstractPorter<SerializableMessage<? extends Serializable>> {


  private TransferTaskFactory<SerializableMessage<? extends Serializable>> transferTaskFactory;
  private PortTaskFactory<SerializableMessage<? extends Serializable>> portTaskFactory;


  @Override
  public void transfer(SerializableMessage<? extends Serializable> serializableMessage) {
    super.transfer(serializableMessage);
  }

  @Override
  public void port(SerializableMessage<? extends Serializable> serializableMessage) {
    super.port(serializableMessage);
  }

  @Override
  public SerializableMessage<? extends Serializable> fetch(SerializableMessage<? extends Serializable> serializableMessage) {
    return null;
  }


  public static void main(String[] args) {
    DefaultPorter defaultPorter = new DefaultPorter();
    defaultPorter.port(new DefaultPorter.PojoMessage());
    defaultPorter.port(new DelayMessage());
    defaultPorter.port(new TxMessage());
  }

  static class PojoMessage implements SerializableMessage<Cat> {

    @Override
    public String getId() {
      return null;
    }

    @Override
    public Cat getPayload() {
      return null;
    }

    @Override
    public String getTargetTableName() {
      return null;
    }
  }
}