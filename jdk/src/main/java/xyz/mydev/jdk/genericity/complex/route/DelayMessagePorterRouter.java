package xyz.mydev.jdk.genericity.complex.route;

import lombok.Setter;
import org.junit.Test;
import xyz.mydev.jdk.genericity.complex.msg.DelayMessage;
import xyz.mydev.jdk.genericity.complex.msg.SerializableMessage;
import xyz.mydev.jdk.genericity.complex.msg.TxMessage;
import xyz.mydev.jdk.genericity.complex.port.DefaultPorter;
import xyz.mydev.jdk.genericity.complex.port.GenericPorter;
import xyz.mydev.jdk.genericity.complex.port.Porter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DelayMessagePorterRouter implements PorterRouter {


  @Setter
  private Map<String, Porter<? extends SerializableMessage<? extends Serializable>>> portersExtends = new HashMap<>();

  private Map<String, Porter<? super SerializableMessage<? extends Serializable>>> portersSuper = new HashMap<>();

  private Map<String, Porter<?>> portersAny = new HashMap<>();
  private Map<String, Porter<SerializableMessage<?>>> portersSerializableMessage = new HashMap<>();


  @Override
  public Porter<? super SerializableMessage<? extends Serializable>> get(String key) {
    return portersSuper.get(key);
  }

  @Test
  public void test() {

    System.out.println("==================extends==================");

    DefaultPorter defaultPorter = new DefaultPorter();
    GenericPorter<PersonMessage> personMessageGenericPorter = new GenericPorter<>();

    portersExtends.put("1", defaultPorter);
    portersExtends.put("2", personMessageGenericPorter);
    Porter<PersonMessage> porter2 = null;
    portersExtends.put("3", porter2);

    personMessageGenericPorter.transfer(new PersonMessage());

    defaultPorter.port(new DelayMessage());
    defaultPorter.port(new TxMessage());
    defaultPorter.port(new PersonMessage());


    // 指针 extends
    Porter<? extends SerializableMessage<? extends Serializable>> thisExtendsPorter = defaultPorter;
    thisExtendsPorter = personMessageGenericPorter;


    System.out.println("==================super==================");

    portersSuper.put("1", defaultPorter);
//    portersSuper.put("2", personMessageGenericPorter);
//    portersSuper.put("3", porter2);

    Porter<? super SerializableMessage<? extends Serializable>> porterSuper = this.get("1");

    porterSuper.transfer(new DelayMessage());
    porterSuper.transfer(new PersonMessage());
    porterSuper.transfer(new TxMessage());


    // 指针 super
    porterSuper = defaultPorter;
    System.out.println(porterSuper.getClass());

    // super 无法指向 extends
//    porterSuper = thisExtendsPorter;


    // extends 无法 指向super
//    thisExtendsPorter = this.portersSuper;


    System.out.println("==================any==================");

    portersAny.put("1", defaultPorter);
    portersAny.put("2", personMessageGenericPorter);
    Porter<?> porter = portersAny.get("1");
//    porter.port(new Object());  // error


    System.out.println("==================SerializableMessage<?>==================");


    portersSerializableMessage.put("1", defaultPorter);
//    portersSerializableMessage.put("2", personMessageGenericPorter);


    Porter<SerializableMessage<?>> serializableMessagePorter = portersSerializableMessage.get("1");
    serializableMessagePorter.port(new PersonMessage());


  }

  public static class SerializablePayload implements Serializable {

  }

  public static class PersonMessage extends DelayMessage {

  }

}
