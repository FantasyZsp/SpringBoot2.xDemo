package xyz.mydev.jdk.baseapi.time;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import xyz.mydev.common.utils.JsonUtil;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author ZSP
 */
public class LocalDateTimeDemo {
  public static void main(String[] args) {
    LocalDateTime localDateTime = LocalDateTime.parse("2020-08-12T16:32:42.282217200");
    LocalDateTime localDateTime2 = LocalDateTime.parse("2020-08-12T16:32:42.282217");
    LocalDateTime localDateTime3 = LocalDateTime.parse("2020-08-12T16:32:49", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    System.out.println(localDateTime);
    System.out.println(localDateTime2);
    System.out.println(localDateTime3);
  }

  @Test
  public void deserialize() throws Exception {

    ObjectMapper objectMapper = JsonUtil.objectMapper;

    String timeText = "2020-08-12T16:31:54.9617655";
    System.out.println(LocalDateTime.parse(timeText));
    timeText = "\"" + "2020-08-12T16:31:54.9617655" + "\"";
    LocalDateTime now = LocalDateTime.now();

    String timeText2 = objectMapper.writeValueAsString(now);
    System.out.println(timeText2);

    LocalDateTime localDateTime = objectMapper.readValue(timeText2, LocalDateTime.class);
    System.out.println(localDateTime);
    LocalDateTime localDateTime2 = objectMapper.readValue(timeText, LocalDateTime.class);
    System.out.println(localDateTime2);

  }

  @Test
  public void toInstant() throws Exception {

//    LocalDateTime localDateTime = LocalDateTime.now();
//    System.out.println(localDateTime);
//    System.out.println(localDateTime.toInstant(ZoneOffset.UTC));
//    System.out.println(localDateTime.toInstant(ZoneOffset.of("+8")));
//    System.out.println(localDateTime.atZone(ZoneOffset.UTC));
//    System.out.println(localDateTime.atZone(ZoneOffset.of("+8")));
//    Instant x = Instant.ofEpochMilli(1529476623000L);
//    System.out.println(x);


    Instant instant = Instant.now();
    System.out.println(instant);
    long toEpochMilli = instant.toEpochMilli();
    System.out.println(toEpochMilli);
    System.out.println(Instant.ofEpochMilli(toEpochMilli));

    System.out.println(LocalDateTime.ofInstant(instant, ZoneOffset.UTC));
    System.out.println(LocalDateTime.ofInstant(instant, ZoneOffset.ofHours(8)));
    System.out.println(instant.atZone(ZoneOffset.UTC));
    System.out.println(instant.atZone(ZoneId.of("America/Los_Angeles")));


  }
}
