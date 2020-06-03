package xyz.mydev.beans;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import xyz.mydev.beans.dto.AddressDTO;
import xyz.mydev.beans.dto.PersonDTO;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

//@SpringBootApplication
//@EnableSwagger2
public class MapperDemo {
  public static ObjectMapper objectMapper = new ObjectMapper();

  public static void main(String[] args) throws Exception {
//    typeConverByJackson();
    writeValueAsStringTwice();
  }

  public static void writeValueAsStringTwice() throws Exception {

//        JacksonUtil
    String str1 = "我是一个字符串";
    String time1 = "2018-08-11T02:03:54Z";
//    System.out.println(objectMapper.readValue(objectMapper.writeValueAsString(time1), Instant.class));
    System.out.println(str1);
    System.out.println(objectMapper.writeValueAsString(str1));
    String str2 = objectMapper.writeValueAsString(str1);
    String str3 = objectMapper.writeValueAsString(str2);
    System.out.println(str3);

    System.out.println("====");
    AddressDTO addressDTO = new AddressDTO("add", "post");
    String result = objectMapper.writeValueAsString(addressDTO);
    System.out.println(result);
    String result2 = objectMapper.writeValueAsString(result);
    System.out.println(result2);

    Object addressDTO0 = objectMapper.readValue(result, Object.class);
    System.out.println(addressDTO0);

    String msg = "{\"address\":\"add\",\"postCode\":\"post\"}";
    Object test = objectMapper.readValue(result, AddressDTO.class);
    System.out.println(test);

//    AddressDTO addressDTO1 = objectMapper.readValue(result2, AddressDTO.class);
//    System.out.println(addressDTO1);

//    send(addressDTO0);
//
//    send(LinkedHashMap)


  }

  public static void typeConverByJackson() throws IOException {
    AddressDTO addressDTO = new AddressDTO("add", "post");
    PersonDTO personDTO = new PersonDTO(1L, "xx", addressDTO, Instant.now());
    Object obj = (Object) personDTO;
    System.out.println(personDTO);
    System.out.println(obj);

    System.out.println("将字符串与Instant互转时，需要配置objectMapper");
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);


    String personStr = objectMapper.writeValueAsString(personDTO);
    System.out.println(personStr);
    String personObjStr = objectMapper.writeValueAsString(obj);
    System.out.println(personObjStr);

    System.out.println(obj.getClass());


    PersonDTO personDTO1 = objectMapper.readValue(personObjStr, PersonDTO.class);
    PersonDTO personDTO2 = objectMapper.readValue(objectMapper.writeValueAsString(obj), PersonDTO.class);


    PersonDTO personDTO3 = objectMapper.readValue(objectMapper.writeValueAsString(personDTO), PersonDTO.class);
    System.out.println((PersonDTO) obj);
    System.out.println(personDTO1);
    System.out.println(personDTO2);
    System.out.println(personDTO3);
  }

  public static void containsDemo() {
    String str = "浙G0620N|津G0620N";
    System.out.println(str);
    System.out.println(str.contains("|"));
    System.out.println(str.contains("|"));
    List<String> list = Arrays.asList(str.split("\\|"));
    for (String s : list) {
      System.out.println(s);
    }
  }
}
