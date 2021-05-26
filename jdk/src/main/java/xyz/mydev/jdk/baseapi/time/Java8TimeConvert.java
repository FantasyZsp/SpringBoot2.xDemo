package xyz.mydev.jdk.baseapi.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author zhao
 * @date 2018/09/17 18:06
 * @description
 */
public class Java8TimeConvert {

//    private static final

  public static void main(String[] args) {


    System.out.println("========================Instant=====================");
    Instant startTime = Instant.now();
    long timestamp = startTime.toEpochMilli();
    LocalDateTime localDateTime = LocalDateTime.now();
    System.out.println(localDateTime + "  " + localDateTime.toEpochSecond(ZoneOffset.of("+8")));
    System.out.println(startTime + "  " + startTime.toEpochMilli());


    System.out.println("========================LocalDateTime=====================");
    System.out.println("========================零点=====================");
    LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);

    Instant start_Instant = startOfDay.toInstant(ZoneOffset.of("+0"));
    System.out.println("LocalDateTime  " + startOfDay);
    System.out.println("Instant  " + start_Instant);
    System.out.println("isUTC  " + (start_Instant.until(start_Instant, ChronoUnit.HOURS) == 0));

    System.out.println(startOfDay.toEpochSecond(ZoneOffset.of("+0")));
    System.out.println(start_Instant.toEpochMilli());
    System.out.println(startOfDay.toInstant(ZoneOffset.UTC).toEpochMilli());
    System.out.println(startOfDay.plus(3600, ChronoUnit.DAYS).toInstant(ZoneOffset.UTC).toEpochMilli());


    System.out.println("========================从UTC零点时间获取对应UTC+0当地时间的日期=====================");
    System.out.println("构造一个UTC零点时间start_Instant(实际是利用LocalDataTime构造)  " + start_Instant);
    LocalDate localDate = LocalDateTime.ofInstant(start_Instant, ZoneOffset.UTC).toLocalDate();
    System.out.println("获取LocalDate  " + localDate);
    LocalDate localDate2 = LocalDateTime.ofInstant(start_Instant, ZoneOffset.of("+8")).toLocalDate();
    System.out.println("获取东八区日期  " + localDate2);

    System.out.println("========================从UTC零点时间获取对应UTC+8当地时间的日期=====================");
    LocalDateTime localDateTime2 = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
    System.out.println("构造一个东八区当地零点时间：  " + localDateTime2);
    LocalDateTime localDateTime1 = LocalDateTime.now();
    System.out.println("这是当地钟表时间：  " + localDateTime1);
    ZonedDateTime zonedDateTime = localDateTime1.atZone(ZoneOffset.of("+8"));
    System.out.println("这是当地对应的世界时（带时区）：  " + zonedDateTime);

    Instant instant1 = localDateTime1.toInstant(ZoneOffset.of("+8"));
    System.out.println("获取对应在UTC+0区的Instant时间(从当地钟表时间) " + instant1);
    System.out.println("获取对应在UTC+0区的Instant时间(从当地世界时间) " + zonedDateTime.toInstant() + ",应该与上面相同");

    LocalDateTime localDateTime3 = LocalDateTime.ofInstant(instant1, ZoneOffset.UTC);
    System.out.println("获取对应在UTC+0区的LocalDate(从Instant时间)  " + localDateTime3);
    // LocalDateTime.ofInstant(zonedDateTime.toInstant(),ZoneOffset.UTC)中，第一个参数代表UTC时间，第二个参数代表时区，函数ofInstant指明将第一个参数转到第二个参数所指定的时区，生成一个钟表时间。
    System.out.println("获取对应在UTC+0区的LocalDate(从当地世界时间)  " + LocalDateTime.ofInstant(zonedDateTime.toInstant(), ZoneOffset.UTC));
    System.out.println("  " + LocalDateTime.ofInstant(zonedDateTime.toInstant(), ZoneOffset.UTC));

    LocalDate localDate3 = LocalDateTime.ofInstant(instant1, ZoneOffset.of("+8")).toLocalDate();
    System.out.println("获取东八区日期(从当天零点对应的Instant)  " + localDate3);


    System.out.println("========================UNIX系统时间纪元=====================");
    LocalDateTime starttt = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
    Instant start_Instant2 = starttt.toInstant(ZoneOffset.of("+0"));
    System.out.println(starttt);
    System.out.println(start_Instant2);
    System.out.println(start_Instant2.toEpochMilli());

    System.out.println("isUTCStart  " + (start_Instant2.until(start_Instant, ChronoUnit.HOURS) % 24 == 0));
    System.out.println(Instant.ofEpochSecond(0));

    Instant test = Instant.now();
    Instant test2 = test.plus(1, ChronoUnit.DAYS).plusMillis(1);

    System.out.println(test.until(test2, ChronoUnit.DAYS));

    System.out.println("========================终点=====================");
    LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
    System.out.println(endOfDay);


    System.out.println(LocalTime.of(0, 0, 0));
//        boolean isUTC2 =(LocalDateTime.of(LocalDateTime.now().toLocalDate(),LocalTime.MIN). % (1000*1000*3600))==0;
//        System.out.println(isUTC2);

    Instant startTime2 = startTime.plus(23, ChronoUnit.HOURS);
    Instant endTime = startTime.plus(3L, ChronoUnit.DAYS).plusSeconds(7653);

    System.out.println(startTime.until(startTime2, ChronoUnit.DAYS));
    System.out.println(startTime.until(endTime, ChronoUnit.DAYS));


    System.out.println(Instant.ofEpochSecond(7200).until(Instant.ofEpochSecond(3600), ChronoUnit.HOURS) == 0);
//        System.out.println(startTime.until(LocalDate, ChronoUnit.HOURS) == 0);


  }
}
