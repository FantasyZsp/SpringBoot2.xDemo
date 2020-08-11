package xyz.mydev.jdk.bean.test.JsonDeserialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import xyz.mydev.common.utils.JsonUtil;
import xyz.mydev.jdk.bean.GenericDelayMessage;
import xyz.mydev.jdk.bean.SimpleEntity;

import java.time.LocalDateTime;

/**
 * @author ZSP
 */
public class GenericDelayMessageTest {
  public static ObjectMapper objectMapper = JsonUtil.objectMapper;

  public static void main(String[] args) throws Exception {
    GenericDelayMessage<SimpleEntity> genericDelayMessage = new GenericDelayMessage<>();

    SimpleEntity simpleEntity = new SimpleEntity();
    simpleEntity.setFlag(false);
    simpleEntity.setId("id");

    genericDelayMessage.setPayload(simpleEntity);
    genericDelayMessage.setTime(LocalDateTime.now());


//    System.out.println(((ParameterizedType) genericDelayMessage.getClass().getSuperclass().getGenericInterfaces()[0]).getActualTypeArguments()[0].getTypeName());
//    System.out.println(((ParameterizedType) genericDelayMessage.getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName());


    System.out.println(genericDelayMessage.getPayLoadClassName());
    System.out.println(genericDelayMessage.getClass().getName());
    System.out.println(genericDelayMessage.getClassName());

    String content = objectMapper.writeValueAsString(genericDelayMessage);
    GenericDelayMessage genericDelayMessage1 = objectMapper.readValue(content, genericDelayMessage.getClass());
    System.out.println(genericDelayMessage1.getPayload().getClass());

  }
}
