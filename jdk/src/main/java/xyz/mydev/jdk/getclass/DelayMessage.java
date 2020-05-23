package xyz.mydev.jdk.getclass;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import xyz.mydev.jdk.bean.IDelayMessage;
import xyz.mydev.jdk.bean.SimpleEntity;
import xyz.mydev.jdk.util.JsonUtil;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class DelayMessage<T> implements IDelayMessage {
  private T payload;
  private LocalDateTime startTime;
  private Map<String, Object> headers;
  private String className;

  private void setClassName(String className) {
    this.className = className;
  }

  public void setPayload(T payload) {
    this.payload = payload;
    this.className = payload.getClass().getName();
  }
}

/**
 * 泛型实体的序列化
 */
class Test {

  public static ObjectMapper objectMapper = JsonUtil.objectMapper;


  public static void main(String[] args) throws Exception {
    DelayMessage<SimpleEntity> delayMessage = new DelayMessage<>();
    delayMessage.setStartTime(LocalDateTime.now());


    SimpleEntity simpleEntity = new SimpleEntity();
    simpleEntity.setFlag(false);
    simpleEntity.setId("id");

    delayMessage.setPayload(simpleEntity);
    System.out.println(delayMessage.getClassName());

    String className = delayMessage.getClassName();
    Class<?> aClass = Class.forName(className);
    String messageJson = objectMapper.writeValueAsString(delayMessage);

    DelayMessage<?> value = objectMapper.readValue(messageJson, DelayMessage.class);
    System.out.println("反序列化结果：" + value);
    Object payload = value.getPayload();
    System.out.println(payload);

    System.out.println("xxx");
    String payloadClassName = value.getClassName();
    Class<?> payloadClass = Class.forName(payloadClassName);
    Object o = objectMapper.readValue(objectMapper.writeValueAsBytes(payload), payloadClass);
    System.out.println(o);


  }
}