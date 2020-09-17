package xyz.mydev.jdk.string;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * trim 只是去掉了前后的空格,原来的值不改变。
 *
 * @author ZSP
 */
public class StringTrimDemo {
  public static void main(String[] args) {
    String test = "    this is a string   ";
    System.out.println(test.trim());
    System.out.println(test);
    System.out.println(StringUtils.trim(test));
    Integer integer = null;
//    System.out.println(1 == integer); NPE
    System.out.println(Objects.equals(integer, 1));
    System.out.println(Objects.equals(1, integer));
    System.out.println(Integer.valueOf(1).equals(null));

  }

}
