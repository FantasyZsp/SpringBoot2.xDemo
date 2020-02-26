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
import xyz.mydev.vo.ResultVO;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
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
    ResultVO<String> vo = ResultVO.success(200, "success", new String("字符串测试"));
    String msg = Objects.requireNonNull(serviceObjectMapper.writeValueAsString(vo));
    System.out.println(msg);
    Type t = this.getClass().getMethod("build", Object.class).getGenericReturnType();
    Object success = serviceObjectMapper.readerFor(serviceObjectMapper.getTypeFactory().constructType(t)).readValue(msg);
    System.out.println(success);
    System.out.println(success.getClass());
    System.out.println(success.getClass().getGenericSuperclass());

  }

  public static void main(String[] args) throws Exception {
    new JacksonConfig2Test().testUseParamBuildReturnT();
  }

  @Test
  public void testUseParamBuildReturnT() throws Exception {
    Go go = (Go) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{Go.class}, new InvocationHandler() {
      @Override
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Type genericReturnType = method.getGenericReturnType();
        System.out.println("return: " + genericReturnType);

        Type[] genericParameterTypes = method.getGenericParameterTypes();
        for (Type genericParameterType : genericParameterTypes) {
          System.out.println("param: " + genericParameterType);
        }
        for (Object arg : args) {
          System.out.println("arg: " + arg);
          System.out.println(Arrays.toString(arg.getClass().getTypeParameters()));
          System.out.println("arg type: " + arg.getClass().getGenericSuperclass());
          System.out.println("arg type: " + arg.getClass());
        }

        System.out.println("...");

        return new ArrayList<>();
      }
    });

    ResultVO<ArrayList<String>> go1 = go.go(new ArrayList<String>());

  }


  public static String serialize(ObjectMapper objectMapper, Object o) {
    try {
      return objectMapper.writer().writeValueAsString(o);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public <T> T deserialize(ObjectMapper objectMapper, String json, Class<T> t) {
    try {
      return objectMapper.readerFor(objectMapper.getTypeFactory().constructType(t)).readValue(json);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public ResultVO<DataDtoImpl> build() {
    return null;
  }

  public <T> ResultVO<T> build(T param) {
    return null;
  }

  interface Go {
    <T> ResultVO<T> go(T param);
  }
}