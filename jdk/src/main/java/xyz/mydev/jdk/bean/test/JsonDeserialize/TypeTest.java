package xyz.mydev.jdk.bean.test.JsonDeserialize;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import xyz.mydev.jdk.bean.DelayMessageTest;
import xyz.mydev.jdk.bean.IDelayMessage;
import xyz.mydev.jdk.bean.SimpleDelayMessage;
import xyz.mydev.jdk.bean.SimpleEntity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 泛型实体的序列化
 *
 * @author ZSP
 */
@Slf4j
public class TypeTest extends BaseTest {


  private SimpleDelayMessage simpleDelayMessage;

  @Before
  public void before() {
    simpleDelayMessage = getSimpleDelayMessage();
  }


  @Test
  public void testT() throws Exception {
    DelayMessageTest<SimpleEntity> delayMessage = new DelayMessageTest<>();
    delayMessage.setStartTime(LocalDateTime.now());

    SimpleEntity simpleEntity = new SimpleEntity();
    simpleEntity.setFlag(false);
    simpleEntity.setId("id");

    delayMessage.setPayload(simpleEntity);
    log.info(delayMessage.getPayloadClassName());

    String className = delayMessage.getPayloadClassName();
    Class<?> aClass = Class.forName(className);
    String messageJson = objectMapper.writeValueAsString(delayMessage);

    DelayMessageTest<?> value = objectMapper.readValue(messageJson, DelayMessageTest.class);
    log.info("反序列化结果：" + value);
    Object payload = value.getPayload();
    log.info(payload.toString());

    log.info("xxx");
    String payloadClassName = value.getPayloadClassName();
    Class<?> payloadClass = Class.forName(payloadClassName);
    Object o = objectMapper.readValue(objectMapper.writeValueAsBytes(payload), payloadClass);
    log.info(o.toString());
  }

  @Test
  public void test() throws Exception {


    String messageJson = objectMapper.writeValueAsString(simpleDelayMessage);
    SimpleDelayMessage value = objectMapper.readValue(messageJson, SimpleDelayMessage.class);
    log.info("反序列化结果：" + value);
    Object payload = value.getPayload();
    log.info(payload.toString());
    log.info("xxx");
  }


  @Test
  public void testMethod() throws Exception {
//    receive(simpleDelayMessage);
    List<SimpleDelayMessage> simpleDelayMessages = new ArrayList<>();
    simpleDelayMessages.add(simpleDelayMessage);
    receiveBatch(simpleDelayMessages);
  }

  private <T> void receive(IDelayMessage<T> tiDelayMessage) throws Exception {

    log.info("receive参数class: {}", tiDelayMessage.getClass());

    ParameterizedType parameterizedType = (ParameterizedType) tiDelayMessage.getClass().getGenericInterfaces()[0];
    log.info("receive参数parameterizedType: {}", parameterizedType);
    Type actualTypeArgument = parameterizedType.getActualTypeArguments()[0];
    log.info("receive参数actualTypeArgument: {}", actualTypeArgument);

    String value = objectMapper.writeValueAsString(tiDelayMessage);


//    Object receive = receive(value, actualTypeArgument);
//    log.info("receive: {}", receive);

  }

  private <T extends IDelayMessage<?>> void receiveBatch(List<T> msgList) throws Exception {

    T t = null;
    receiveBatch(msgList, t);

  }

  private <T extends IDelayMessage<?>> void receiveBatch(List<T> msgList, T t) throws Exception {

    log.info("receive参数class: {}", msgList.getClass());

    t = msgList.get(0);
    System.out.println(t.getClass());


    ParameterizedType parameterizedType = (ParameterizedType) t.getClass().getGenericInterfaces()[0];
    log.info("receive参数parameterizedType: {}", parameterizedType);
    Type actualTypeArgument = parameterizedType.getActualTypeArguments()[0];
    log.info("receive参数actualTypeArgument: {}", actualTypeArgument);

    String value = objectMapper.writeValueAsString(msgList);


    Object receive = receive(value, (Class<? extends Object>) (actualTypeArgument));
    log.info("receive: {}", receive);

  }


  private <T> IDelayMessage<T> receive(String msg, Class<T> tClass) throws Exception {

    log.info("收到字符串[{}]后的处理", msg);
    log.info("{}", tClass.getTypeName());

//    T o = (T) objectMapper.readValue(msg, tTypeReference);

    IDelayMessage<T> iDelayMessage = objectMapper.readValue(msg, new TypeReference<IDelayMessage<T>>() {
    });
    log.info("result: {}", iDelayMessage);

    return iDelayMessage;

  }


  @Test
  public void testSimpleDelayMessage() throws Exception {
    SimpleDelayMessage delayMessage = new SimpleDelayMessage();
    delayMessage.setStartTime(LocalDateTime.now());
    SimpleEntity simpleEntity = new SimpleEntity();
    simpleEntity.setFlag(false);
    simpleEntity.setId("id");
    delayMessage.setPayload(simpleEntity);

    log.info(delayMessage.getClass().getSuperclass().toGenericString());


    log.info("泛型获取测试");
    Class<?>[] interfaces = delayMessage.getClass().getInterfaces();
    for (Class<?> anInterface : interfaces) {
      log.info(anInterface.getName());
    }
    log.info("===============");
    Type[] genericInterfaces = delayMessage.getClass().getGenericInterfaces();
    for (Type genericInterface : genericInterfaces) {
      log.info(genericInterface.getTypeName());
    }

    log.info("===============");


    // 在类的内部这样获取
//    log.info(delayMessage.getTClass());

  }

}