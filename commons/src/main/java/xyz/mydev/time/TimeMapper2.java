package xyz.mydev.time;

import com.fasterxml.jackson.databind.ObjectMapper;
import xyz.mydev.util.JsonUtil;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;


/**
 * @author  zhao
 * @date  2018/09/07 19:34
 * @description
 */
public class TimeMapper2 {
  public static void main(String[] args) throws IOException {
    ObjectMapper objectMapper = JsonUtil.objectMapper;
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    System.out.println(timestamp);

    LocalDateTime localDateTime = LocalDateTime.now();
    System.out.println(localDateTime);

    System.out.println(Timestamp.valueOf(localDateTime));

    System.out.println(timestamp.toLocalDateTime());
  }
}
