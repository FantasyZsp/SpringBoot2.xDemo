package xyz.mydev.jdk.util.datatype.string;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * @author zhao
 * @date 2018/09/15 10:36
 * @description
 */
public class StringTest {
  private static ObjectMapper objectMapper = new ObjectMapper();

  public static void main(String[] args) {


    float f1 = 2.00F;
    float f2 = 1.10F;
    System.out.printf("%2f", (f1 - f2));

    String number1 = "";
    String number2 = "";
    String number3 = "ABCD1234567";
    String number4 = "ABCD1234567";
    String number5 = "ABCD1234568";

    String number7 = null;
    String number8 = null;

    System.out.println("空字符串对比" + StringUtils.equalsIgnoreCase(number1, number2));
    System.out.println("空与null对比" + StringUtils.equalsIgnoreCase(number1, number7));
    System.out.println("双null对比" + StringUtils.equalsIgnoreCase(number7, number8));
    System.out.println("含值相等对比" + StringUtils.equalsIgnoreCase(number4, number3));
    System.out.println("含值不等对比" + StringUtils.equalsIgnoreCase(number4, number5));
    System.out.println(StringUtils.equalsIgnoreCase(number7, number2));
    System.out.println(StringUtils.equalsIgnoreCase(number8, number2));


    String number6 = "ABCD1234568";
    System.out.println("===================");
    String[] split = number6.split(",");
    System.out.println(split.length);
    for (String str : split) {
      System.out.println(str);
    }
    System.out.println("===================");


    Random random = new Random();
    StringBuilder str = new StringBuilder();
    for (int i = 0; i < 8; i++) {
      str.append(random.nextInt(10));
    }
    System.out.println(str);

    String stt = "[]";
    try {
      List<String> o = objectMapper.readValue(stt, new TypeReference<List<String>>() {

      });
      System.out.println(o);
      System.out.println(o.getClass());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
