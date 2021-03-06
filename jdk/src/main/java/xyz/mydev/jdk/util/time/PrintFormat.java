package xyz.mydev.jdk.util.time;

import com.fasterxml.jackson.databind.ObjectMapper;
import xyz.mydev.common.utils.JsonUtil;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author zhao
 * @date 2018/09/07 19:34
 * 时间打印格式
 */
public class PrintFormat {

  public static void main(String[] args) throws IOException {
    ObjectMapper objectMapper = JsonUtil.objectMapper;

    LocalDate localDate = LocalDate.now();
    LocalDateTime localDateTime = LocalDateTime.now();
    ZonedDateTime zonedDateTime = ZonedDateTime.now();
    Instant instant = Instant.now().plusSeconds(300);
    Instant instant2 = Instant.now().minus(1, ChronoUnit.DAYS).plus(12, ChronoUnit.HOURS).plus(38, ChronoUnit.MINUTES);
    System.out.println("instant2   " + instant2);
    System.out.println("localDate   " + localDate);
    System.out.println("LocalDateTime   " + LocalDateTime.ofInstant(instant2, ZoneId.systemDefault()));
    System.out.println(localDateTime.toInstant(ZoneOffset.UTC));

    System.out.println("LocalDateTime.MIN   " + LocalDateTime.MIN);
    System.out.println("LocalDateTime.MAX   " + LocalDateTime.MAX);
    System.out.println("当天零点   " + LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
    System.out.println("当天零点时间戳   " + LocalDateTime.of(LocalDate.now(), LocalTime.MIN).toInstant(ZoneOffset.UTC));


    Long currentTimeMillis = System.currentTimeMillis();
    Date date = new Date();

    String str = "XXXXXXXXXX";
    String format = "%-15s%-50s%-50s\n";

    System.out.printf(format, "localDateTime", localDateTime, objectMapper.writeValueAsString(localDateTime));
    System.out.printf(format, "zonedDateTime", zonedDateTime, objectMapper.writeValueAsString(zonedDateTime));
    System.out.printf(format, "date", date, objectMapper.writeValueAsString(date));
    System.out.printf(format, "instant", instant, objectMapper.writeValueAsString(instant));
//        System.out.printf("%-8s\n",zonedDateTime);
//        System.out.printf("%-8s\n",instant);
//        System.out.printf("%-8s\n",currentTimeMillis);


    System.out.println("===============mapper与toString对比================");
    System.out.println("localDateTime   " + localDateTime + "        " + objectMapper.writeValueAsString(localDateTime));
    System.out.println("zonedDateTime   " + zonedDateTime + "        " + objectMapper.writeValueAsString(zonedDateTime));
    System.out.println("Date            " + date + "        " + objectMapper.writeValueAsString(date));
    System.out.println("localDate         " + localDate + "        " + objectMapper.writeValueAsString(localDate));
    System.out.println("Instant         " + instant + "        " + objectMapper.writeValueAsString(instant));
    System.out.println("currentTimeMs   " + currentTimeMillis + "        " + objectMapper.writeValueAsString(currentTimeMillis));
    System.out.println();

  }
}
