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
 * 人脸搜索结果
 * 可用于单个和多个搜索结果
 *
 * @author ZSP
 */
@NoArgsConstructor
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SearchSingleResult extends CommonResult {

  private ResultBean result;

  @NoArgsConstructor
  @Data
  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  public static class ResultBean {
    private String faceToken;
    private List<UserListBean> userList;
  }

  public static SearchSingleResult convert(Map<String, Object> map) {
    return JsonUtil.string2Obj(JsonUtil.obj2String(Objects.requireNonNull(map)), SearchSingleResult.class);
  }

  public static SearchSingleResult recoverSearchSingleResult(String errorCode, String errorMsg) {
    SearchSingleResult searchSingleResult = new SearchSingleResult();
    searchSingleResult.setErrorCode(errorCode);
    searchSingleResult.setErrorMsg(errorMsg);
    searchSingleResult.setTimestamp(System.currentTimeMillis());
    return searchSingleResult;
  }
}
