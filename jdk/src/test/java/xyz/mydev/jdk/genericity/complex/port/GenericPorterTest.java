package xyz.mydev.jdk.genericity.complex.port;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.mydev.jdk.genericity.complex.msg.DelayMessage;
import xyz.mydev.jdk.genericity.complex.msg.SerializableMessage;
import xyz.mydev.jdk.genericity.complex.msg.StringMessage;

import java.io.Serializable;
import java.util.Map;

/**
 * @author ZSP
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class GenericPorterTest {

  @Autowired
  private Map<String, Porter<? extends StringMessage>> porterMap;
  @Autowired
  private Map<String, Porter<SerializableMessage<? extends Serializable>>> serializablePorterMap;

  @Autowired
  private Map<String, Porter<StringMessage>> stringPorterMap;

  @Autowired
  private Map<String, Porter<? extends SerializableMessage<? extends Serializable>>> exSerializablePorterMap;
  @Autowired
  private Map<String, Porter<? super SerializableMessage<? extends Serializable>>> suSerializablePorterMap;


  @Test
  public void test() {

    System.out.println(porterMap.size());
    System.out.println(serializablePorterMap.size());
    System.out.println(exSerializablePorterMap.size());
    System.out.println(suSerializablePorterMap.size());
    Porter<StringMessage> porter = stringPorterMap.get("1");
    porter.port(new DelayMessage());

  }


}