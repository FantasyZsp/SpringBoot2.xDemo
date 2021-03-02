package xyz.mydev.transaction.domain;

import lombok.Data;
import lombok.ToString;

/**
 * @author ZSP
 */
@Data
@ToString(callSuper = true)
public class Girl {
  private Integer id;
  private String cupSize;
  private Integer age;


  public static Girl build(Integer id) {
    Girl girl = new Girl();
    girl.setId(id);
    girl.setAge(1);
    girl.setCupSize("D");
    return girl;
  }

  public static Girl build(Integer id, String cupSize, Integer age) {
    Girl girl = new Girl();
    girl.setId(id);
    girl.setAge(age);
    girl.setCupSize(cupSize);
    return girl;
  }
}
