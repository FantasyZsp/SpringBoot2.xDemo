package xyz.mydev.datastructure.serializable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author ZSP
 * @date 2018/11/20 19:53
 * @description
 */
public class JobQueueTest {
  private static ObjectMapper objectMapper = new ObjectMapper();

  public static void main(String[] args) throws JsonProcessingException {
    JobQueueVO jobQueueVO = new JobQueueVO();
    JavaTimeModule javaTimeModule = new JavaTimeModule();
    javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    objectMapper.registerModule(javaTimeModule);

//        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jobQueueVO));
    jobQueueVO.getContainers().add(new JobQueueVO.Container());
    jobQueueVO.getContainers().add(new JobQueueVO.Container());
    System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jobQueueVO));
  }
}
