package xyz.mydev.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.junit.Test;
import xyz.mydev.common.beans.vo.MarkedSerializedTypeStudentVO;
import xyz.mydev.common.beans.vo.NumberVO;
import xyz.mydev.common.beans.vo.StudentVO;

/**
 * @author ZSP
 */
public class JsonUtilTest {
  @Test
  public void test() {
    System.out.println(JsonUtil.obj2String(NumberVO.of(1)));
  }

  public static final ObjectMapper DEFAULT_OBJECT_MAPPER = JsonUtil.objectMapper;
  public static final ObjectMapper UPPER_CAMEL_CASE_OBJECT_MAPPER = JsonUtil.objectMapper.copy().setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
  public static final ObjectMapper LOWER_CAMEL_CASE_OBJECT_MAPPER = JsonUtil.objectMapper.copy().setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);

  @Test
  public void serialization() throws Exception {
    StudentVO studentVO = new StudentVO();

    studentVO.setStudentAddress("地址");
    studentVO.setStudentGrade("年级");
    studentVO.setStudentName("学员姓名");
    studentVO.setTimestamp(1000L);

    System.out.println(DEFAULT_OBJECT_MAPPER.writeValueAsString(studentVO));
    System.out.println(UPPER_CAMEL_CASE_OBJECT_MAPPER.writeValueAsString(studentVO));
    System.out.println(LOWER_CAMEL_CASE_OBJECT_MAPPER.writeValueAsString(studentVO));

    // 将以类上打的注解为主
    MarkedSerializedTypeStudentVO copy = MarkedSerializedTypeStudentVO.copy(studentVO);

    System.out.println(DEFAULT_OBJECT_MAPPER.writeValueAsString(copy));
    System.out.println(UPPER_CAMEL_CASE_OBJECT_MAPPER.writeValueAsString(copy));
    System.out.println(LOWER_CAMEL_CASE_OBJECT_MAPPER.writeValueAsString(copy));


    MarkedSerializedTypeStudentVO markedSerializedTypeStudentVO = UPPER_CAMEL_CASE_OBJECT_MAPPER.readValue(DEFAULT_OBJECT_MAPPER.writeValueAsString(studentVO), MarkedSerializedTypeStudentVO.class);

    System.out.println(markedSerializedTypeStudentVO);
  }

}