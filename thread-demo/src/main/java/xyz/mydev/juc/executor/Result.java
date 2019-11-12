package xyz.mydev.juc.executor;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ZSP  2019/09/20 17:58
 */
@Getter
@Setter
public class Result {
  private Integer code;
  private String msg;
  private Object data;
}