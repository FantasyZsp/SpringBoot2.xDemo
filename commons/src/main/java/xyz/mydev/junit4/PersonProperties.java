package xyz.mydev.junit4;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zhao
 * @date 2018/07/26 16:37
 * @description
 */
@Component
@Getter
@Setter
public class PersonProperties {
  //    @Value("${person.test}")
  private String app = "";

  @Value("${person.name}")
  private String name;

  @Value("${person.age}")
  private String age;

  @Value("${person.address}")
  private String address;

  public PersonProperties() {
  }

  public PersonProperties(String name, String age, String address, String app) {
    this.name = name;
    this.age = age;
    this.address = address;
    this.app = app;
  }
}
