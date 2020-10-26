package xyz.mydev.baidu.ai.face.demo.client.bean;

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
public class SearchBatchResult extends CommonResult {
  private ResultBean result;

  @NoArgsConstructor
  @Data
  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  public static class ResultBean {
    private Integer faceNum;
    private List<FaceListBean> faceList;

    @NoArgsConstructor
    @Data
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class FaceListBean {
      private String faceToken;
      private LocationBean location;
      private List<UserListBean> userList;
    }
  }

  public static SearchBatchResult convert(Map<String, Object> map) {
    return JsonUtil.string2Obj(JsonUtil.obj2String(Objects.requireNonNull(map)), SearchBatchResult.class);
  }
}
