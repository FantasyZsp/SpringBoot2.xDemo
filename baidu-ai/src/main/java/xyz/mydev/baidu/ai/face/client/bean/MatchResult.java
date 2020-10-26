package xyz.mydev.baidu.ai.face.client.bean;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.mydev.common.utils.JsonUtil;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 人脸对比结果
 *
 * @author ZSP
 */
@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MatchResult extends CommonResult {

  private Result result;


  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  @Data
  @NoArgsConstructor
  public static class Result {
    private Double score;
    private List<String> faceToken;
  }

  public static MatchResult convert(Map<String, Object> map) {
    return JsonUtil.string2Obj(JsonUtil.obj2String(Objects.requireNonNull(map)), MatchResult.class);
  }
}
