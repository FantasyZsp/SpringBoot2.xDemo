package xyz.mydev.baidu.ai.face.demo.client.bean;

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

  private Integer errorCode;
  private Long logId;
  private String errorMsg;

  private Result result;
  private Integer cached;
  private Integer timestamp;

  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  @Data
  @NoArgsConstructor
  private static class Result {
    private String faceToken;
    private LocationBean location;
  }

  public static AddUserResult convert(Map<String, Object> map) {
    return JsonUtil.string2Obj(JsonUtil.obj2String(Objects.requireNonNull(map)), AddUserResult.class);
  }
}
