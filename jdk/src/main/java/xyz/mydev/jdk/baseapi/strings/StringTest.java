package xyz.mydev.jdk.baseapi.strings;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.lang.reflect.Field;

public class StringTest {
  /**
   * <a href="https://blog.wongwongsu.com/detail_article?article_id=33">...</a>
   * 字符串常量池存放引用的理解
   */
  @Test
  public void stringUpdateTest() throws Exception {
    System.out.println(("d".getBytes()[0]));
    String s1 = "abc";
    System.out.println(s1);
    update(s1, 2, "d".getBytes()[0]);
    System.out.println(s1);
    String s2 = new String("abc");
    System.out.println(s2);
  }

  @Test
  public void randomString() throws Exception {
    System.out.println(RandomStringUtils.random(12, true, false));
  }

  static void update(String str, int point, byte c) throws NoSuchFieldException, IllegalAccessException {
    Field field = String.class.getDeclaredField("value");
    field.setAccessible(true);
    byte[] value = (byte[]) field.get(str);
    value[point] = c;
  }

}