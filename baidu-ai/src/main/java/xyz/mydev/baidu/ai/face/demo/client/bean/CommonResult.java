package xyz.mydev.baidu.ai.face.demo.client.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class CommonResult {
  private Integer errorCode;
  private Long logId;
  private String errorMsg;

  @JsonIgnore
  public boolean success() {
    return Objects.equals(errorCode, 0);
  }

  public static CommonResult convert(Map<String, Object> map) {
    return JsonUtil.string2Obj(JsonUtil.obj2String(Objects.requireNonNull(map)), CommonResult.class);
  }

}
