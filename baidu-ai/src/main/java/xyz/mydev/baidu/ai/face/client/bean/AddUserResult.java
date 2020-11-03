package xyz.mydev.baidu.ai.face.client.bean;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.mydev.common.utils.JsonUtil;

import java.util.Map;
import java.util.Objects;

/**
 * @author ZSP
 */
@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AddUserResult extends CommonResult {

  private Result result;


  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  @Data
  @NoArgsConstructor
  public static class Result {
    private String faceToken;
    private LocationBean location;
  }

  public static AddUserResult convert(Map<String, Object> map) {
    return JsonUtil.string2Obj(JsonUtil.obj2String(Objects.requireNonNull(map)), AddUserResult.class);
  }

  public static AddUserResult recoverResult(String errorCode, String errorMsg) {
    AddUserResult addUserResult = new AddUserResult();
    addUserResult.setErrorCode(errorCode);
    addUserResult.setErrorMsg(errorMsg);
    addUserResult.setTimestamp(System.currentTimeMillis());
    return addUserResult;
  }
}
