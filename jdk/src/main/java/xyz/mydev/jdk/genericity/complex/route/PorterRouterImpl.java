package xyz.mydev.jdk.genericity.complex.route;

import xyz.mydev.jdk.genericity.complex.msg.DelayMessage;
import xyz.mydev.jdk.genericity.complex.msg.Message;
import xyz.mydev.jdk.genericity.complex.msg.SerializableMessage;
import xyz.mydev.jdk.genericity.complex.msg.StringMessage;
import xyz.mydev.jdk.genericity.complex.port.DefaultPorter;
import xyz.mydev.jdk.genericity.complex.port.GenericPorter;
import xyz.mydev.jdk.genericity.complex.port.Porter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaosp
 */
public class PorterRouterImpl implements PorterRouter {

  static Map<String, Porter<? super SerializableMessage<? extends Serializable>>> porterSuMap = new HashMap<>();

  static Map<String, Porter<? extends SerializableMessage<? extends Serializable>>> porterExMap = new HashMap<>();

  static {
    GenericPorter<StringMessage> stringMessageGenericPorter = new GenericPorter<>();
    GenericPorter<Message> messageGenericPorter = new GenericPorter<>();
    GenericPorter<DelayMessage> delayMessageGenericPorter = new GenericPorter<>();
    DefaultPorter defaultPorter = new DefaultPorter();

    porterExMap.put("s", defaultPorter);
    porterExMap.put("s2", stringMessageGenericPorter);
    porterExMap.put("s3", messageGenericPorter);

    // CAST
//    porterSuMap = (Map<String, Porter<? super SerializableMessage<? extends Serializable>>>) porterExMap; // error


//    porterSuMap.put("s3", defaultPorter); // error
//    porterSuMap.put("s2", messageGenericPorter); // error
//    porterSuMap.put("s2", stringMessageGenericPorter); // error
//    porterSuMap.put("s2", delayMessageGenericPorter); // error
    porterSuMap.forEach((k, v) -> {
      v.port(new DelayMessage());
      v.port((StringMessage) null);
      v.transfer((StringMessage) null);
    });


    Porter<SerializableMessage<? extends Serializable>> porter = null;
    porter.port(new DelayMessage());
    porter.port((StringMessage) null);
    porter.port((Message) null);


  }

  @Override
  public Porter<? super SerializableMessage<? extends Serializable>> get(String key) {
    return porterSuMap.get(key);
  }

  @Override
  public <E extends SerializableMessage<? extends Serializable>> Porter<? super SerializableMessage<? extends Serializable>> resolveByMessage(E msg) {
    return porterSuMap.get(msg.getTargetTableName());
  }

  @Override
  public void transfer(SerializableMessage<? extends Serializable> msg) {
    porterSuMap.get(msg.getTargetTableName()).transfer(msg);
  }


  public static void main(String[] args) {
    PorterRouterImpl porterRouter = new PorterRouterImpl();
    System.out.println(porterRouter);

  }
}
