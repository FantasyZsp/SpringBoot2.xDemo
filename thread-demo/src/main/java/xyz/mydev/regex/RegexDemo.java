package xyz.mydev.regex;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ZSP  2019/08/12 11:43
 */
public class RegexDemo {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static void main(String[] args) throws JsonProcessingException {
    test2();
  }


  private static void test() throws JsonProcessingException {
    System.out.println("test method");
    String text = "abbcc";
    String regex1 = "ab{1,3}bc";
    String regex2 = "ab{1,3}?bc";

    System.out.println(OBJECT_MAPPER.writeValueAsString(text.split(regex1)));
    System.out.println(OBJECT_MAPPER.writeValueAsString(text.split(regex2)));
  }

  private static void test2() throws JsonProcessingException {

    String text = "abbbbbc";
    String regex1 = "ab{1,4}+bc";
    String regex2 = "ab{1,5}?c";
    String regex3 = "ab{1,4}bc";

    System.out.println(OBJECT_MAPPER.writeValueAsString(text.split(regex1)));
    System.out.println(OBJECT_MAPPER.writeValueAsString(text.split(regex2)));
    System.out.println(OBJECT_MAPPER.writeValueAsString(text.split(regex3)));
  }


}
