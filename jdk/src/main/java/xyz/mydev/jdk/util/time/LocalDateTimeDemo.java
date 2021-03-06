package xyz.mydev.jdk.util.time;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import xyz.mydev.common.utils.JsonUtil;

import java.time.LocalDateTime;
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
}
