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
}
