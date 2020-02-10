package xyz.mydev.jdk.random;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZSP
 */
public class RandomUtils {
  public static void main(String[] args) {
    System.out.println(RandomStringUtils.random(10, true, true));
    System.out.println(RandomStringUtils.random(4, false, true));

    String randomString = RandomStringUtils.random(10, true, true);

    String randomNumber = RandomStringUtils.random(2, false, true);

    Map<String, String> vars = new HashMap<>();
    vars.put("randomString", randomString);
    vars.put("randomNumber", randomNumber);
    System.out.println(vars);

  }
}
