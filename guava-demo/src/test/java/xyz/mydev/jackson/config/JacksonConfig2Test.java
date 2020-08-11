package xyz.mydev.jackson.config;

import org.junit.Test;
import xyz.mydev.common.beans.vo.ResultVO;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author ZSP
 */
public class JacksonConfig2Test {


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

  interface Go {
    <T> ResultVO<T> go(T param);
  }
}