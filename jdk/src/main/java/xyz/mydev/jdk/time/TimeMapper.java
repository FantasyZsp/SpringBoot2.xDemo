package xyz.mydev.jdk.time;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import xyz.mydev.common.utils.JsonUtil;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;


/**
 * @author zhao
 * @date 2018/09/07 19:34
 * @description
 */
public class TimeMapper {
  public static void main(String[] args) throws IOException {
    ObjectMapper objectMapper = JsonUtil.objectMapper;

    System.out.println("===============定义时间================");
    Date date = new Date();
    LocalDate localDate = LocalDate.now();
    LocalDateTime localDateTime = LocalDateTime.now();
    ZonedDateTime zonedDateTime = ZonedDateTime.now();
    Instant instant = Instant.now();
    Long instnt2stmp = instant.toEpochMilli();
    Long currentTimeMillis = System.currentTimeMillis();


    Instant fromDate = date.toInstant();

    System.out.println("===============mapper与toString对比================");
    String fmt = "%-20s%-50s%-50s\n";
    System.out.printf(fmt, "date", date, objectMapper.writeValueAsString(date));
    System.out.printf(fmt, "localDate", localDate, objectMapper.writeValueAsString(localDate));
    System.out.printf(fmt, "localDateTime", localDateTime, objectMapper.writeValueAsString(localDateTime));
    System.out.printf(fmt, "zonedDateTime", zonedDateTime, objectMapper.writeValueAsString(zonedDateTime));
    System.out.printf(fmt, "instant", instant, objectMapper.writeValueAsString(instant));
    System.out.printf(fmt, "instnt2stmp", instnt2stmp, objectMapper.writeValueAsString(instnt2stmp));
    System.out.printf(fmt, "instantFromDate", fromDate, objectMapper.writeValueAsString(fromDate));
    System.out.printf(fmt, "currentTimeMillis", currentTimeMillis, objectMapper.writeValueAsString(currentTimeMillis));
    System.out.println();

    System.out.println("===============时间戳1536315503.677转为时间================");
    String timestampx = "1536315503.677";
    Instant instantx = objectMapper.readValue(timestampx, Instant.class);
    System.out.println(timestampx);
    System.out.println(instantx);

    System.out.println();
    System.out.println("===============时间戳currentTimeMillis转为时间================");
//        String strTimestamp = String.valueOf(currentTimeMillis);
//        LocalDateTime timestampLocalDateTime = objectMapper.readValue(strTimestamp, LocalDateTime.class);
//        ZonedDateTime timestampZonedDateTime = objectMapper.readValue(strTimestamp, ZonedDateTime.class);
//        Instant timestampInstant = objectMapper.readValue(strTimestamp, Instant.class);
//        Date timestampDate = objectMapper.readValue(strTimestamp, Date.class);

//        LocalDateTime timestampLocalDateTime = new LocalDateTime(timestamp);
//        ZonedDateTime timestampZonedDateTime = new ZonedDateTime(timestamp);
//        Instant timestampInstant = new Instant(timestamp);
//        Date timestampDate = new Date(timestamp);
//        System.out.println();
//        System.out.println("LocalDateTime               " + timestampLocalDateTime);
//        System.out.println("ZonedDateTime               " + timestampZonedDateTime);
//        System.out.println("Instant                     " + timestampInstant);
//        System.out.println("Date                        " + timestampDate);

    System.out.println();
    System.out.println("===============字符串转时间================");
    String timestamp2 = "\"2018-09-07T10:18:23.677Z\"";
    Instant instant2 = objectMapper.readValue(timestamp2, Instant.class);
    System.out.println(instant2);

    String timestamp3 = "\"2018-09-07T10:18:23.677Z\"";
    Instant instant3 = objectMapper.readValue(timestamp3, Instant.class);
    System.out.println(instant3);
    System.out.println();

    System.out.println("===============时间之间转换（时区）================");
    System.out.println(zonedDateTime.toInstant());
    System.out.println();

    System.out.println("===============mapper配置写时间为时间戳================");
    objectMapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    System.out.printf(fmt, "date", date, objectMapper.writeValueAsString(date));
    System.out.printf(fmt, "localDate", localDate, objectMapper.writeValueAsString(localDate));
    System.out.printf(fmt, "localDateTime", localDateTime, objectMapper.writeValueAsString(localDateTime));
    System.out.printf(fmt, "zonedDateTime", zonedDateTime, objectMapper.writeValueAsString(zonedDateTime));
    System.out.printf(fmt, "instant", instant, objectMapper.writeValueAsString(instant));
    System.out.printf(fmt, "instantFromDate", fromDate, objectMapper.writeValueAsString(fromDate));
    System.out.printf(fmt, "instnt2stmp", instnt2stmp, objectMapper.writeValueAsString(instnt2stmp));
    System.out.printf(fmt, "currentTimeMillis", currentTimeMillis, objectMapper.writeValueAsString(currentTimeMillis));
    System.out.println();
  }
}
