package xyz.mydev.jdk.genericity.complex.route;

import lombok.Setter;
import xyz.mydev.jdk.genericity.complex.msg.SerializableMessage;
import xyz.mydev.jdk.genericity.complex.msg.DelayMessage;
import xyz.mydev.jdk.genericity.complex.port.Porter;

import java.io.Serializable;
import java.util.Map;

public class DelayMessagePorterRouter implements PorterRouter {


  @Setter
  private Map<String, Porter<? super SerializableMessage<? extends Serializable>>> porters;


  @Override
  public Porter<? super SerializableMessage<? extends Serializable>> get(String key) {
    return porters.get(key);
  }

  public void test() {

//    porters.put("1", new DefaultPorter<StringMessage>(null, null, null, null));
//    porters.put("2", new DefaultPorter<SerializableMessage<String>>(null, null, null, null));
//    porters.put("3", new DefaultPorter<DelayMessage>(null, null, null, null));
//    porters.put("4", new DefaultPorter<SerializableMessage<Person>>(null, null, null, null));
//    porters.put("5", new DelayMessagePorter<Person2>(null, null, null, null));
//    Porter<? extends SerializableMessage<? extends Serializable>> porter = porters.get("1");
//
//    porter.transfer(new DelayMessage());
//    porter.transfer(new Person2());

  }

  public static class Person implements Serializable {

  }

  public static class Person2 extends DelayMessage {

  }

}
