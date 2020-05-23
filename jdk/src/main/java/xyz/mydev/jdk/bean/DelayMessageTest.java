package xyz.mydev.jdk.bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.junit.Test;
import xyz.mydev.jdk.util.JsonUtil;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author ZSP
 */
@Data
public class DelayMessageTest<T> implements IDelayMessageCase {
  private T payload;
  private LocalDateTime startTime;
  private Map<String, Object> headers;
  private String payloadClassName;

  private void setPayloadClassName(String payloadClassName) {
    this.payloadClassName = payloadClassName;
  }

  public void setPayload(T payload) {
    this.payload = payload;
    this.payloadClassName = payload.getClass().getName();
  }

  @Test
  public void testT2() throws Exception {
    DelayMessageTest<SimpleEntity> delayMessage = new DelayMessageTest<>();
    delayMessage.setStartTime(LocalDateTime.now());
    SimpleEntity simpleEntity = new SimpleEntity();
    simpleEntity.setFlag(false);
    simpleEntity.setId("id");
    delayMessage.setPayload(simpleEntity);

    System.out.println(delayMessage.getClass().getSuperclass());


    System.out.println("泛型获取测试");
    Class<?>[] interfaces = delayMessage.getClass().getInterfaces();
    for (Class<?> anInterface : interfaces) {
      System.out.println(anInterface);
    }
    System.out.println("===============");
    Type genericSuperclass = delayMessage.getClass().getGenericSuperclass();
    System.out.println(genericSuperclass);

    System.out.println("===============");


    // 在类的内部这样获取
//    System.out.println(delayMessage.getTClass());

  }
}

