package xyz.mydev.common.utils;

import org.springframework.core.DefaultParameterNameDiscoverer;
import xyz.mydev.common.beans.Person;

/**
 * @author ZSP
 */
public class DefaultParameterNameDiscovererUtil {
  public static void main(String[] args) {
    DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
    String[] parameterNames = discoverer.getParameterNames(DefaultParameterNameDiscovererUtil.class.getMethods()[1]);
    assert parameterNames != null;
    for (String parameterName : parameterNames) {
      System.out.println(parameterName);
    }
  }

  public void testParam(String name, Integer age, Person person) {

  }

}
