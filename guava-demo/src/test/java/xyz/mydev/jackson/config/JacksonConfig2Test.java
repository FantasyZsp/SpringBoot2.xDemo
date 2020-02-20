package xyz.mydev.jackson.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.mydev.CommonsApplication;
import xyz.mydev.jackson.serialize.DataDtoImpl;
import xyz.mydev.jackson.serialize.DataImp;
import xyz.mydev.vo.ResultVO;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * @author ZSP
 */
@SpringBootTest(classes = CommonsApplication.class)
@RunWith(SpringRunner.class)
public class JacksonConfig2Test {

  @Autowired
  @Qualifier("serviceObjectMapper")
  private ObjectMapper serviceObjectMapper;


  static {

  }

  @Test
  public void test() throws Exception {
    ResultVO<DataDtoImpl> vo = ResultVO.success(200, "success", new DataDtoImpl());
    String msg = Objects.requireNonNull(serviceObjectMapper.writeValueAsString(vo));
    System.out.println(msg);
    Type t = vo.getClass().getMethod("success", Object.class).getGenericReturnType();
    System.out.println(vo.getClass().getMethod("success", Object.class).getGenericReturnType());
    Object success = serviceObjectMapper.readerFor(serviceObjectMapper.getTypeFactory().constructType(t)).readValue(msg);
    System.out.println(success);
    System.out.println(success.getClass().getGenericSuperclass());

  }


  public static String serialize(ObjectMapper objectMapper, Object o) {
    try {
      return objectMapper.writer().writeValueAsString(o);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public <T> T deserialize(ObjectMapper objectMapper, String json, Type t) {
    try {
      return objectMapper.readerFor(objectMapper.getTypeFactory().constructType(t)).readValue(json);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}