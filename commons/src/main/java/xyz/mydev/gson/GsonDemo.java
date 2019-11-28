package xyz.mydev.gson;

import com.google.gson.Gson;
import xyz.mydev.beans.dto.AddressDTO;
import xyz.mydev.beans.dto.PersonDTO;

import java.time.Instant;

/**
 * @author  zhao
 * @date  2018/08/11 09:41
 * @description
 */
public class GsonDemo {


  public static void main(String[] args) {
    Gson gson = new Gson();
    Instant instant = Instant.now();
    System.out.println(instant);
    String instr = gson.toJson(instant);
    System.out.println(instr);
    String str1 = "2018-08-11T02:03:54.605Z";
    String str2 = gson.toJson(str1);
    System.out.println(str2);
    String str3 = gson.toJson(str2);
    System.out.println(str3);
    System.out.println("==========");
    System.out.println(gson.fromJson(str2, Instant.class));

    AddressDTO addressDTO = new AddressDTO("addG", "postG");
    PersonDTO personDTO = new PersonDTO(1L, "xx", addressDTO, Instant.now());
    Object obj = (Object) personDTO;
    System.out.println(personDTO);
    System.out.println(obj);


//        String personStr = objectMapper.writeValueAsString(personDTO);
    String personStr = gson.toJson(personDTO);
    System.out.println(personStr);

    String personObjStr = gson.toJson(obj);
    System.out.println(personObjStr);

    System.out.println(obj.getClass());


    PersonDTO personDTO1 = gson.fromJson(personObjStr, PersonDTO.class);
    PersonDTO personDTO2 = gson.fromJson(gson.toJson(obj), PersonDTO.class);


    PersonDTO personDTO3 = gson.fromJson(gson.toJson(personDTO), PersonDTO.class);
    System.out.println((PersonDTO) obj);
    System.out.println(personDTO1);
    System.out.println(personDTO2);
    System.out.println(personDTO3);
  }


}
