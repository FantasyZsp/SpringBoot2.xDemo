package xyz.mydev.common.utils;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author ZSP
 */
public class Java8DateUtilsTest {

  @Test
  public void getBetweenDays() {
    System.out.println(Java8DateUtils.getBetweenDays(LocalDate.now(), LocalDate.now().plusDays(1L)));
    System.out.println(LocalDateTime.MAX);
  }
}