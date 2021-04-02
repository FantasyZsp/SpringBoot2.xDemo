package xyz.mydev.transaction.listener;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.mydev.transaction.domain.Girl;

/**
 * @author ZSP
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GirlDto extends Girl {

  public static GirlDto build(Integer id) {
    GirlDto girl = new GirlDto();
    girl.setId(id);
    girl.setAge(1);
    girl.setCupSize("D");
    return girl;
  }
}
