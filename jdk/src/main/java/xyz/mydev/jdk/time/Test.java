package xyz.mydev.jdk.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author ZSP
 */
public class Test {
  public static void main(String[] args) {
    Instant parse = Instant.parse("2020-06-16T10:18:17.431575800Z");
    System.out.println(parse);
    System.out.println(LocalDateTime.ofInstant(parse, ZoneId.systemDefault()));
  }
}
