package xyz.mydev.baidu.ai.face.demo.client.bean;

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
public class SearchResult extends CommonResult {

  private Integer errorCode;
  private String errorMsg;
  private Long logId;

  private Long timestamp;
  private Integer cached;
  private ResultBean result;

  @NoArgsConstructor
  @Data
  public static class ResultBean {
    private Integer faceNum;
    private List<FaceListBean> faceList;

    @NoArgsConstructor
    @Data
    public static class FaceListBean {
      private String faceToken;
      private LocationBean location;
      private List<UserListBean> userList;


      @NoArgsConstructor
      @Data
      public static class UserListBean {
        private String groupId;
        private String userId;
        private String userInfo;
        private Double score;
      }
    }
  }

  public static SearchResult convert(Map<String, Object> map) {
    return JsonUtil.string2Obj(JsonUtil.obj2String(Objects.requireNonNull(map)), SearchResult.class);
  }
}
