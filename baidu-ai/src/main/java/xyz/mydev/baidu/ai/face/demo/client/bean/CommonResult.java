package xyz.mydev.baidu.ai.face.demo.client.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import xyz.mydev.common.utils.JsonUtil;

import java.util.Map;
import java.util.Objects;

/**
 * @author ZSP
 */

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CommonResult {
  private Integer errorCode;
  private Long logId;
  private String errorMsg;

  private Integer cached;
  private Long timestamp;

  private Object result;

  @JsonIgnore
  public boolean success() {
    return Objects.equals(errorCode, 0);
  }

  public static CommonResult convert(Map<String, Object> map) {
    return JsonUtil.string2Obj(JsonUtil.obj2String(Objects.requireNonNull(map)), CommonResult.class);
  }

}
