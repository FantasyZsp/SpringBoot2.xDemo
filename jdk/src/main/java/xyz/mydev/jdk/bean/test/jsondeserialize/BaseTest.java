package xyz.mydev.jdk.bean.test.jsondeserialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import xyz.mydev.common.utils.JsonUtil;
import xyz.mydev.jdk.bean.SimpleDelayMessage;
import xyz.mydev.jdk.bean.SimpleEntity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;

/**
 * @author ZSP
 */
public class BaseTest {

  public static ObjectMapper objectMapper = JsonUtil.objectMapper;

  protected SimpleDelayMessage getSimpleDelayMessage() {
    SimpleDelayMessage delayMessage = new SimpleDelayMessage();
    delayMessage.setStartTime(LocalDateTime.now());

    SimpleEntity simpleEntity = new SimpleEntity();
    simpleEntity.setFlag(false);
    simpleEntity.setId("id");

    delayMessage.setPayload(simpleEntity);
    return delayMessage;
  }


  protected Type getGenericInterfaceParamType(Object object) {
    return ((ParameterizedType) object.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
  }
}
