package xyz.mydev.jdk.baseapi.time;

import com.fasterxml.jackson.databind.ObjectMapper;
import xyz.mydev.common.utils.JsonUtil;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Date;


/**
 * @author zhao
 * @date 2018/09/07 19:34
 * @description
 */
public class TimeOps {
  public static void main(String[] args) throws IOException {
    ObjectMapper objectMapper = JsonUtil.objectMapper;
    Date date = new Date();
    LocalDate localDate = LocalDate.now();

    System.out.println(LocalDateTime.of(localDate, LocalTime.MIN));


    LocalDateTime localDateTime = LocalDateTime.now();
    ZonedDateTime zonedDateTime = ZonedDateTime.now();
    Instant instant = Instant.now();
    Long currentTimeMillis = System.currentTimeMillis();

    LocalDate today = LocalDate.now().plusDays(1);
    System.out.println(localDate.equals(today));

    System.out.println("===============mapper与toString对比================");
    String fmt = "%-20s%-50s%-50s\n";
    System.out.printf(fmt, "date", date, objectMapper.writeValueAsString(date));
    System.out.printf(fmt, "localDate", localDate, objectMapper.writeValueAsString(localDate));
    System.out.printf(fmt, "localDateTime", localDateTime, objectMapper.writeValueAsString(localDateTime));
    System.out.printf(fmt, "zonedDateTime", zonedDateTime, objectMapper.writeValueAsString(zonedDateTime));
    System.out.printf(fmt, "instant", instant, objectMapper.writeValueAsString(instant));
    System.out.printf(fmt, "currentTimeMillis", currentTimeMillis, objectMapper.writeValueAsString(currentTimeMillis));
    System.out.println();

  }
}
