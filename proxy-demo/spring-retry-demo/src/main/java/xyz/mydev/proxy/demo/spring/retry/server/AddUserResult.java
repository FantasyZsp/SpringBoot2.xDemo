package xyz.mydev.proxy.demo.spring.retry.server;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author ZSP
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class AddUserResult extends CommonResult {

  private Result result;

  @Builder(builderMethodName = "customBuilder")
  public AddUserResult(String errorCode, String errorMsg, Result result) {
    super(errorCode, errorMsg, result);
    this.result = result;
  }


  @Data
  @NoArgsConstructor
  public static class Result {
    private String faceToken;
  }

  @Override
  public Result getResult() {
    return result;
  }
}
