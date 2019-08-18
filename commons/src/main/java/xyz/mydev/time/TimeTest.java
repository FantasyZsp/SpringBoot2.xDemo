package xyz.mydev.time;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

/**
 * @author  zhao
 * @date  2018/07/25:20/05
 * @description
 */
public class TimeTest {
  private static ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);


  public static void main(String[] args) throws Exception {
    Instant instant = Instant.now();
    LocalDateTime localDateTime = LocalDateTime.now();
    System.out.println(localDateTime);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = new Date();
    System.out.println(date.getTime());
    System.out.println(sdf.format(date));
    System.out.println(date.toString());
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
    System.out.println(objectMapper.writeValueAsString(instant));
    System.out.println(instant);

    String tstamp = "1534155550000";
    System.out.println(objectMapper.readValue(tstamp, Instant.class));


    UUID uuid = UUID.randomUUID();

    // 随机数的生成
    int number5 = (int) ((Math.random() * 9 + 1) * 10000);
    System.out.println("number5 " + number5);
    int number8 = (int) ((Math.random() * 9 + 1) * 10000000);
    System.out.println("number8 " + number8);

    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    System.out.println("一位随机大写字母 " + chars.charAt((int) (Math.random() * 26)));


    char az = chars.charAt((int) (Math.random() * 26));
    String str = "测" + az + number5;
    System.out.println("随机车牌  " + str);


//        vars.put("uuid",(uuid.toString()));
//        vars.put("color",(az.toString()));
//        vars.put("number5",(number5));
//        vars.put("number8",(number8));
//        vars.put("license_plate_number",(str));
//        vars.put("start_time",(startTime));


//        LocalDateTime localDateTime = LocalDateTime.now();
//
//        System.out.println(instant);
//        System.out.println(localDateTime);
//
//        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withLocale(Locale.CHINESE).withZone(ZoneId.systemDefault());
//
//        String instantToStr = "2018-07-28T08:14:54.149Z";
//        Instant strToInstant = Instant.parse(instantToStr);
//        System.out.println("字符串转时间  " + strToInstant);
//
//        // formatter时间转字符串
//        String time = formatter.format(instant);
//        System.out.println("formatter时间转字符串  " + time);
//        System.out.println();
//        // toString时间转字符串
//        System.out.println(instant.toString());
//
//        // 使用objectMapper转换
//        System.out.println("======================使用objectMapper转换====================");
//        ObjectMapper objectMapper2 = new ObjectMapper().registerModule(new JavaTimeModule());
//        ObjectMapper objectMapper = new ObjectMapper();
//
////        String instantToString = objectMapper.writeValueAsString(instant);
//        String string = "2018-07-28T10:02:03.789Z";
//        string = Instant.now().toString();
//        System.out.println("toString()方法 " + string);
//
//        Instant stringToInstant = objectMapper.readValue(string, Instant.class);
//        System.out.println(stringToInstant);
//        string = objectMapper.writeValueAsString(instant);
//        Instant stringToInstant2 = objectMapper.readValue(string, Instant.class);
//
//        System.out.println("writeValueAsString()方法 " + string);
//        System.out.println("readValue()方法 " +stringToInstant2);
//
////        1525143001
//
//
////        System.out.println(formatter.getLocale());
////        System.out.println(formatter.getZone());
  }

  public static void writeValueAsStringDemo() throws java.io.IOException {
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    System.out.println(LocalDateTime.now().toString());
    Instant instant = Instant.now();
    System.out.println(instant);
    String str = objectMapper.writeValueAsString("2018-08-10T12:01:06.647Z");
    String str2 = '"' + "2018-08-10T12:01:06.647Z" + '"';
    String str3 = "2018-08-10T12:01:06.647Z";
    System.out.println(str);
    System.out.println(str2);
    System.out.println(str3);
    System.out.println(str.equals(str2));

    Instant intant2 = objectMapper.readValue(str, instant.getClass());
    System.out.println(intant2);
    Instant intant4 = objectMapper.readValue(str2, instant.getClass());
    System.out.println(intant4);

    return;
  }
}
