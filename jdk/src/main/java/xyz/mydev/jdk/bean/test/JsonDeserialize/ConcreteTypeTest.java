package xyz.mydev.jdk.bean.test.JsonDeserialize;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import xyz.mydev.jdk.bean.IDelayMessage;
import xyz.mydev.jdk.bean.SimpleDelayMessage;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 泛型信息获取测试
 *
 * @author ZSP
 */
@Slf4j
public class ConcreteTypeTest extends BaseTest {


  @Test
  public void test() throws Exception {
    receive(getSimpleDelayMessage());
  }


  private <T> void receive(IDelayMessage<T> iDelayMessage) throws Exception {
    log.info("receive msg: {}", iDelayMessage);

    Type genericInterfaceParamType = getGenericInterfaceParamType(iDelayMessage);
    log.info("paramType: {}", genericInterfaceParamType);
    log.info("paramType(可作为反射用): {}", genericInterfaceParamType.getTypeName());


    new Temp().processString(objectMapper.writeValueAsString(iDelayMessage));

  }


}

@Slf4j
class Temp extends BaseTest {
  public void process(SimpleDelayMessage message) {
    log.info("处理信息: {}", message);
  }

  public void processBatch(List<SimpleDelayMessage> msgList) {
    log.info("处理信息: {}", msgList);
  }


  public <T> void processT(IDelayMessage<T> message) {
    log.info("处理信息: {}", message);
  }

  public <T> void processString(String message) throws Exception {
    log.info("处理信息: {}", message);

    SimpleDelayMessage delayMessage = objectMapper.readValue(message, SimpleDelayMessage.class);
    log.info("处理信息: {}", delayMessage);
  }

  /**
   * TODO 测试
   */
  public <T> void processStringBatch(String message) throws Exception {
    log.info("处理信息: {}", message);

    SimpleDelayMessage delayMessage = objectMapper.readValue(message, SimpleDelayMessage.class);
    log.info("处理信息: {}", delayMessage);
  }
}

