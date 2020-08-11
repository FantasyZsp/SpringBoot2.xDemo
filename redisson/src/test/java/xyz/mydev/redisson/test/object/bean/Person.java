package xyz.mydev.redisson.test.object.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author ZSP
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Person {
  private String id;
  private String name;
  private String address;

  public static Person of(String id, String name, String address) {
    return new Person(id, name, address);
  }
}
