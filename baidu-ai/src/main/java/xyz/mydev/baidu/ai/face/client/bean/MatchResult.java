package xyz.mydev.baidu.ai.face.client.bean;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.mydev.baidu.ai.face.client.codec.internal.JsonUtil;

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
public class MatchResult extends CommonResult {

  private Result result;


  @Data
  @NoArgsConstructor
  public static class Result {
    private Double score;
    private List<FaceTokenString> faceList;

    @Data
    @NoArgsConstructor
    public static class FaceTokenString {
      private String faceToken;
    }

  }

  public static MatchResult convert(Map<String, Object> map) {
    return JsonUtil.string2ObjSnakeCase(JsonUtil.obj2StringSnakeCase(Objects.requireNonNull(map)), MatchResult.class);
  }

  public static MatchResult recoverMatchResult(String errorCode, String errorMsg) {
    MatchResult matchResult = new MatchResult();
    matchResult.setErrorCode(errorCode);
    matchResult.setErrorMsg(errorMsg);
    matchResult.setTimestamp(System.currentTimeMillis());
    return matchResult;
  }
}
