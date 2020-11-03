package xyz.mydev.proxy.demo.spring.retry.server;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author ZSP
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommonResult {
  private String errorCode;
  private String errorMsg;
  private Object result;
}
