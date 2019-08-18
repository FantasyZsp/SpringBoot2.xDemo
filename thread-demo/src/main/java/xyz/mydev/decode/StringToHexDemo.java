package xyz.mydev.decode;

import org.apache.commons.codec.binary.Hex;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author ZSP  2019/08/13 18:08
 */
public class StringToHexDemo {

  public static void main(String[] args) throws Exception {
    test2();
  }

  private static void test() throws Exception {
    String charset = "UTF-8";
    String text = "我";
    String hexCode = "0xE68891";

    byte[] bytes = Hex.decodeHex("E68891".toCharArray());

    char[] chars = Hex.encodeHex(hexCode.getBytes(Charset.forName(charset)), false);
    System.out.println(chars);
    System.out.println(Arrays.toString(bytes));
    System.out.println(Arrays.toString(text.getBytes(charset)));
  }

  private static void test2() throws Exception {
    String charset = "UTF-8";
    String text = "我";
    String hexCode = "E68891";

    System.out.println(new String(text.getBytes(StandardCharsets.UTF_8)));
    System.out.println(new String(Hex.decodeHex(hexCode)));
    System.out.println(new String(Hex.encodeHex(text.getBytes(charset))));


  }
}
