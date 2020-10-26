package xyz.mydev.baidu.ai.face.client.bean;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 识别结果的 user_list内容
 *
 * @author zhaosp
 */
@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserListBean {
  private String groupId;
  private String userId;
  private String userInfo;
  private Double score;
}